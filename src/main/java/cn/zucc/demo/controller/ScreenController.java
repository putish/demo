package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.enums.OStatusEnum;
import cn.zucc.demo.enums.ShowStateEnum;
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
     * @param mId 影院id
     * @param model
     * @return
     */
    @GetMapping("/book")
    public String bookList(@RequestParam(required = false) Long mId, @RequestParam(required = false) Long tId, Model model) throws ParseException {
        MovieDetailVo movie=movieService.movieDetail(mId,tId);
        model.addAttribute("movie",movie);
        Date today = null;
        if(movie.getShowState()== ShowStateEnum.IN_SHOW.getValue()) {//影片为上映
            today=DateUtil.initDateByDay();//今日零点
        }else if (movie.getShowState()==ShowStateEnum.WILL_SHOW.getValue()){//影片为预售
            today=DateUtil.toDate(movie.getShowTime());//今日零点
        }Date tommorrow=DateUtil.getEndTime(today, 60 * 24);//明日零点
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
    @GetMapping("/list")
    public String screenList(@RequestParam(required = false) String showState,HttpSession session,@RequestParam(required = false) Long mId,@RequestParam(required = false) Long hId, Model model) throws ParseException {
        Long tId = (Long) session.getAttribute("tId");

        List<ScreenListVo> list = screenService.screenList(mId, hId, !StringUtils.isEmpty(showState)?null: ShowStateEnum.getValueByContent(showState), null, null, tId);
        model.addAttribute("list",list);
        List<MovieOptionVo> movieOptionVos=movieService.optionList(tId);
        List< HallOptionVo> hallOptionVos=hallService.optionList(tId);
        model.addAttribute("movieOption",movieOptionVos);
        model.addAttribute("hallOption",hallOptionVos);
        return "screen";
    }
    @GetMapping("/")
    public String index(){return "book";}
    @ResponseBody
    @PostMapping("/addscreen")
    public RootData screenList(Long mId, Long hId, String startTime, BigDecimal price, String screenCate, HttpSession session, Model model) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        screenService.addScreen(mId,hId,DateUtil.toDate(startTime),price, screenCate, tId);
        return ResultUtil.success("添加成功");

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
