package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Catergory;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddCatergoryRequest;
import cn.zucc.demo.service.CatergoryService;
import cn.zucc.demo.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.List;

/**
 * @description: 电影类别
 * @author: hjj
 * @create: 2020-03-09 21:28
 */
@Controller
@RequestMapping("/catergory")
public class CatergoryController {
    @Resource
    private CatergoryService catergoryService;

    @RequestMapping("/")
    public String index(Model model){
        return "catergory";
    }

    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    public String findList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                           HttpSession session,Model model) {
        Long tId = (Long) session.getAttribute("tId");
        PageHelper.startPage(pageNum, pageSize);
        List<Catergory> list = catergoryService.findList(tId);
        PageInfo<Catergory> pageInfo = new PageInfo<Catergory>(list, pageSize);
        model.addAttribute("list", pageInfo);

        return "catergory";
    }
    @ResponseBody
    @PostMapping("/add")
    public RootData addCatergory(@RequestBody AddCatergoryRequest request,HttpSession session, Model model) {
        Long tId = (Long) session.getAttribute("tId");
        catergoryService.addCatergory(request.getCName(),tId);
       return ResultUtil.success("添加成功");
    }
    @ResponseBody
    @PostMapping("/delete")
    public RootData delCatergory(@RequestParam(value = "cId", required = false) Long cId,HttpSession session, Model model) {
        Long tId = (Long) session.getAttribute("tId");
        catergoryService.deteleCatergory(cId,tId);
        return ResultUtil.success("删除成功");
    }
    @ResponseBody
    @PostMapping("/edit")
    public RootData editCatergory(@RequestBody AddCatergoryRequest request, HttpSession session, Model model) {
        Long tId = (Long) session.getAttribute("tId");
        catergoryService.editCatergory(request.getCId(),request.getCName(),tId);
        return ResultUtil.success("编辑成功");
    }
    @ResponseBody
    @GetMapping("/detail")
    public RootData catergoryDetail(@RequestParam(value = "cId", required = false) Long cId,HttpSession session, Model model) {
        Long tId = (Long) session.getAttribute("tId");
        catergoryService.catergoryDetail(cId,tId);
        return ResultUtil.success(catergoryService.catergoryDetail(cId,tId));
    }
}
