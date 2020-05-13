package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.enums.OStatusEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddScreenRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.service.ScreenService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.util.ResultUtil;
import cn.zucc.demo.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-11 14:16
 */
@Controller
@RequestMapping("/screen")
public class ScreenController {
    @Resource
    private ScreenService screenService;

    @Resource
    private MovieService movieService;

    @Resource
    private HallService hallService;
    /**
     * 订票列表
     * @param mId 影片id
     * @param tId 影院id
     * @param model
     * @return
     */
    @GetMapping("/book")
    public String bookList(@RequestParam(required = false) Long mId, @RequestParam(required = false) Long tId, Model model) throws ParseException {
        MovieDetailVo movie=movieService.movieDetail(mId,tId);
        model.addAttribute("movie",movie);
        Date today = null;
        if(ShowStateEnum.IN_SHOW.getValue().equals(movie.getShowState())) {//影片为上映
            today=DateUtil.initDateByDay();//今日零点
        }else if (ShowStateEnum.WILL_SHOW.getValue().equals(movie.getShowState())){//影片为预售
            today=DateUtil.toDate(movie.getShowTime());//今日零点
        }
        Date tommorrow=DateUtil.getEndTime(today, 60 * 24);//明日零点
        Date afterTommorrow=DateUtil.getEndTime(tommorrow,60*24);//后日零点
        Date endTime=DateUtil.getEndTime(afterTommorrow,60*24);//后日结束
        List<ScreenListVo> tdlist=screenService.screenList(mId,null,null,today,tommorrow,tId);
        List<ScreenListVo> tmlist=screenService.screenList(mId,null,null,tommorrow,afterTommorrow,tId);
        List<ScreenListVo> aftlist=screenService.screenList(mId,null,null,afterTommorrow,endTime,tId);

        model.addAttribute("tdlist",tdlist);
        model.addAttribute("tmlist",tmlist);
        model.addAttribute("aftlist",aftlist);
        model.addAttribute("movie",movie);
        return "book";
    }

    /**
     * 影院端播放场次列表
     * @param showState 放映状态
     * @param session
     * @param mId 影院id
     * @param model
     @param mId 播放厅id
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    public String screenList(@RequestParam(required = false) String showState,HttpSession session,@RequestParam(required = false) Long mId,Model model,
                             @RequestParam(required = false) Long hId,@RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime ) throws ParseException {
        Long tId = (Long) session.getAttribute("tId");

        List<ScreenListVo> list = screenService.screenList(mId, hId, !StringUtils.isEmpty(showState)?null: ShowStateEnum.getValueByContent(showState), null, null, tId);
        model.addAttribute("list",list);
        List<MovieOptionVo> movieOptionVos=movieService.optionList(tId);
        List< HallOptionVo> hallOptionVos=hallService.optionList(tId);
        List<MovieOptionVo> movieScreenOptionVos=movieService.ScreenMovieOption(tId);
        model.addAttribute("movieOption",movieOptionVos);
        model.addAttribute("movieSceenOption",movieScreenOptionVos);//新增影片的影片下拉框
        model.addAttribute("hallOption",hallOptionVos);
        return "screen";
    }

    @GetMapping("/")
    public String index(){return "book";}

    /**
     *  新增播放场次
     * @param request
     * @param session
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @PostMapping("/add")
    public RootData addScreen(@RequestBody AddScreenRequest request, HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        Screen screen= screenService.addScreen(request.getMId(),request.getHId(),DateUtil.toDate(request.getStartTime()),request.getPrice(),
                request.getScreenCate(), tId);
        return screen==null? ResultUtil.error(new TheaterException(ResultMapping.SOLD_OUT)): ResultUtil.success("添加成功");

    }

    /**
     * 删除播放场次
     * @param sId
     * @param session
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @PostMapping("/del")
    public RootData delSceen(Long sId, HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        screenService.deleteScreen(sId,tId);
        return ResultUtil.success("删除成功");

    }

    /**
     * 编辑播放场次
     * @param request
     * @param session
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @PostMapping("/edit")
    public RootData delSceen(@RequestBody AddScreenRequest request, HttpSession session) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        screenService.editScreen(request.getSId(),request.getMId(),request.getHId(),DateUtil.toDate(request.getStartTime()),request.getPrice(),
                request.getScreenCate(), tId);
        return ResultUtil.success("编辑成功");

    }

    /**
     * 播放场次详情
     * @param sId
     * @param session
     * @return
     */
    @ResponseBody
    @GetMapping("/detail")
    public RootData hallDetail(@RequestParam(required = false) Long sId, HttpSession session){
        Long tId= (Long) session.getAttribute("tId");
        ScreenListVo vo=screenService.screenDetail(sId,tId);
        return ResultUtil.success(vo);
    }


    @GetMapping("/schedual")
    @ResponseBody
    public void schedual(){
        screenService.screenSchedule(Long.valueOf(1));
    }

    @ResponseBody
    @GetMapping("/check")
    public RootData check(){
        screenService.screenCheckTask();
        return ResultUtil.success("");
    }
}
