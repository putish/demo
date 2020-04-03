package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.util.ResultUtil;
import cn.zucc.demo.vo.HallOptionVo;
import cn.zucc.demo.vo.HallTimeTableVo;
import cn.zucc.demo.vo.SeatAddVo;
import cn.zucc.demo.vo.SeatVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    public String hall(Model model){
        return "hall";
    }

    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    public RootData findList(@ModelAttribute(value = "useState")int useState,@ModelAttribute(value = "screenCate")String screenCate,
                           @ModelAttribute(value = "startCount") String startCount,@ModelAttribute(value = "endCount")String endCount,Long tId) {
        Integer startCount1= StringUtils.isEmpty(startCount)?0:Integer.valueOf(startCount);
        Integer endCount1=StringUtils.isEmpty(endCount)?0:Integer.valueOf(endCount);
        List<Hall> list=hallService.findList(useState,screenCate,startCount1,endCount1,tId);
        return ResultUtil.success(list);

    }
    @GetMapping("/optionList")
    public RootData optionList(Long tId){
        List<HallOptionVo> list=hallService.optionList(tId);
        return ResultUtil.success(list);
    }

    /**
     * 添加放映厅
     * @param request 放映厅请求
     * @return
     */
    @PostMapping( "/add" )
    public String addHall(@RequestBody  AddHallRequest request ) {
        hallService.addHall( request);
        return "hall";
    }
    @PostMapping("/delete")
    public RootData deleteHall(Long hId,Long tId){
        hallService.deleteHall(hId, tId);
        return ResultUtil.success("删除成功");
    }
    @PostMapping("/edit")
    public RootData editHall(Long hId,List<SeatVo> seatVos, AddHallRequest addHallRequest,Long tId){
        hallService.editHall(hId, seatVos, addHallRequest, tId);
        return ResultUtil.success("编辑成功");

    }
    @GetMapping("/timeTable")
    public RootData findTimeTable(Long hId, Date startTime, Date endTime, Long tId){
        List<HallTimeTableVo> list=hallService.hallTimeTable(hId, startTime, endTime, tId);
        return ResultUtil.success(list);
    }
}
