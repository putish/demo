package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.MovieService;
import cn.zucc.demo.service.ScreenService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.util.ResultUtil;
import cn.zucc.demo.vo.MovieListVo;
import cn.zucc.demo.vo.ScreenListVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    /**
     * 订票列表
     * @param mId 影院id
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/book")
    public String screenList(@RequestParam(required = false) Long mId, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        MovieListVo movie=movieService.movieDetail(mId,tId);
        model.addAttribute("movie",movie);
        Date today=DateUtil.initDateByDay();//今日零点
        Date tommorrow= DateUtil.getEndTime(today,60*24);//明日零点
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
    @GetMapping("/")
    public String index(){return "book";}
    @PostMapping("/addscreen")
    public String screenList(Long mId, Long hId, String startTime,Long tId, Model model) throws ParseException {
//        Long tId= (Long) session.getAttribute("tId");
        screenService.addScreen(mId,hId,DateUtil.toDate(startTime),tId);
        return "book";

    }
}
