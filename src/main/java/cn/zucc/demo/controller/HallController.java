package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.util.ResultUtil;
import cn.zucc.demo.vo.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hall")
public class HallController {
    @Resource
    private HallService hallService;

    @RequestMapping("/")
    public String index(Model model){
        return "hall";
    }

    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    public String findList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,@RequestParam(required = false) String useState,@RequestParam(required = false) String screenCate,
                           @RequestParam(required = false) String startCount,@RequestParam(required = false) String endCount,HttpSession session,Model model) {
        Long tId= (Long) session.getAttribute("tId");
        Integer startCount1= StringUtils.isEmpty(startCount)?null:Integer.valueOf(startCount);
        Integer endCount1=StringUtils.isEmpty(endCount)?null:Integer.valueOf(endCount);
        Integer useState1;
        if (useState=="使用中") {useState1=1;}
        else if (useState=="闲置中") {useState1=2;}
        else if (useState=="已过期") {useState1=3;}
        else if (useState=="故障中"){useState1=4;}
        else {useState1=null;}
        PageHelper.startPage(pageNum,pageSize);
        List<HallListVo> list=hallService.findList(useState1,screenCate,startCount1,endCount1,tId);
        PageInfo pageInfo = new PageInfo<HallListVo>(list,pageSize);
        model.addAttribute("list",pageInfo) ;

        return "hall";

    }

    /**
     * 下拉列表
     * @return
     */
    @GetMapping("/optionList")
    public RootData optionList(HttpSession session){
        Long tId= (Long) session.getAttribute("tId");
        List<HallOptionVo> list=hallService.optionList(tId);
        return ResultUtil.success(list);
    }

    /**
     * 添加放映厅
     * @param request 放映厅请求
     * @return
     */
    @PostMapping( "/add" )
    public String addHall(@RequestBody  AddHallRequest request,HttpSession session ) {
        Long tId= (Long) session.getAttribute("tId");
        hallService.addHall( request,tId);
        return "hall";
    }
    @PostMapping("/delete")
    public String deleteHall(@RequestParam(required = false,value="hId") Long hId
            ,HttpSession session){
        Long tId= (Long) session.getAttribute("tId");
        hallService.deleteHall(hId, tId);
        return "hall";
    }
    @PostMapping("/edit")
    public RootData editHall(Long hId,List<SeatVo> seatVos, AddHallRequest addHallRequest,HttpSession session){
        Long tId= (Long) session.getAttribute("tId");
        hallService.editHall(hId, seatVos, addHallRequest, tId);
        return ResultUtil.success("编辑成功");

    }
    @GetMapping("/timeTable")
    public String findTimeTable(Long hId, HttpSession session,Model model){
        Long tId= (Long) session.getAttribute("tId");
        List<HallTimeTableVo> list=hallService.hallTimeTable(hId, null, null, tId);
        model.addAttribute("timeTableVos",list);
        return "movie";
    }
    @ResponseBody
    @GetMapping("/getSeat")
    public RootData getSeat(@RequestParam(required = false) Long hId, HttpSession session,Model model){
        Long tId= (Long) session.getAttribute("tId");
        List<SeatVo> list=hallService.getSeat(hId, tId);
        return ResultUtil.success(list);
    }
    @ResponseBody
    @GetMapping("/hallDetail")
    public RootData hallDetail(@RequestParam(required = false) Long hId, HttpSession session,Model model){
        Long tId= (Long) session.getAttribute("tId");
        HallDetailVo vo=hallService.hallDetail(hId, tId);
        return ResultUtil.success(vo);
    }
}
