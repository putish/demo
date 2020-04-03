package cn.zucc.demo.controller;

import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddTheaterRequest;
import cn.zucc.demo.service.impl.TheaterService;
import cn.zucc.demo.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 影院
 * @author: hjj
 * @create: 2020-03-06 21:09
 */
@RestController
@RequestMapping("/theater")
public class TheaterController {

    @Resource
    private TheaterService theaterService;

    /**
     * 添加影院
     * @param addTheaterRequest
     * @return
     */
    @PostMapping("/add")
    RootData addTheater(AddTheaterRequest addTheaterRequest) throws Exception {
        theaterService.addTheater(addTheaterRequest);
        return  ResultUtil.success("success");
    }

}
