package cn.zucc.demo.controller;

import cn.zucc.demo.data.RootData;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.CatergoryService;
import cn.zucc.demo.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: 电影类别
 * @author: hjj
 * @create: 2020-03-09 21:28
 */
@RestController
@RequestMapping("/catergory")
public class CatergoryController {
    @Resource
    private CatergoryService catergoryService;

    /**
     * 新增类别
     * @param cName 类别名称
     * @param tId
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public RootData addCatergory(@RequestParam(value = "cName",required = true) String cName,@RequestParam(value = "tId",required=true) Long tId){
        catergoryService.addCatergory(cName,tId);
        return ResultUtil.success("新增成功");
    }
}
