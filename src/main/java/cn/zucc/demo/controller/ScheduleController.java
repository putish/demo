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
        return "schedule";
    }
    @ResponseBody
    @PostMapping("/delete")
    public RootData deleteSchedule(@RequestParam(value = "scId",required = false) Long scId, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        scheduleService.deleteSchedule(scId);
        return ResultUtil.success("删除成功");
    }
    @ResponseBody
    @PostMapping("/add")
    public RootData addSchedule(@RequestBody AddScheduleRequest request, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        scheduleService.addSchedule(request,tId);
        return ResultUtil.success("添加成功");
    }
    @ResponseBody
    @PostMapping("/edit")
    public RootData editSchedule(@RequestBody AddScheduleRequest request, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        scheduleService.editSchedule(request,tId);
        return ResultUtil.success("编辑成功");
    }
    @ResponseBody
    @GetMapping("/detail")
    public RootData scheduleDetail(@RequestParam(value = "scId",required = false) Long scId, HttpSession session, Model model){
        Long tId= (Long) session.getAttribute("tId");
        Schedule schedule=scheduleService.scheduleDetail(scId);
        return ResultUtil.success(schedule);
    }
    @ResponseBody
    @GetMapping("/check")
    public RootData check(){
        scheduleService.scheduleCheck();
        return ResultUtil.success("");
    }

}
