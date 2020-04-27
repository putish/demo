package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Schedule;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddScheduleRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.ScheduleService;
import cn.zucc.demo.util.ResultUtil;
import com.lly835.bestpay.rest.type.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-06 21:15
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource
    private ScheduleService scheduleService;

    @GetMapping("/list")
    public String getList(@RequestParam(value = "mId",required = false) Long mId, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        List<Schedule> list=scheduleService.getList(mId,tId);
        model.addAttribute("list",list);
        return "movie";
    }
    @PostMapping("/delete")
    public String deleteSchedule(@RequestParam(value = "scId",required = false) Long scId, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        scheduleService.deleteSchedule(scId);
        return "movie";
    }
    @PostMapping("/add")
    public String addSchedule(@RequestBody AddScheduleRequest request, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        scheduleService.addSchedule(request,tId);
        return "movie";
    }
    @PostMapping("/edit")
    public String editSchedule(@RequestParam(value = "scId",required = false) Long scId,@RequestBody AddScheduleRequest request, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        scheduleService.editSchedule(request,tId,scId);
        return "movie";
    }
    @ResponseBody
    @GetMapping("/detail")
    public RootData scheduleDetail(@RequestParam(value = "mId",required = false) Long scId, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        Schedule schedule=scheduleService.scheduleDetail(scId);
        return ResultUtil.success(schedule);
    }


}
