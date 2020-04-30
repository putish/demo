package cn.zucc.demo.controller;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.bean.Users;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddTheaterRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.TheaterService;
import cn.zucc.demo.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 影院
 * @author: hjj
 * @create: 2020-03-06 21:09
 */
@Controller
@RequestMapping("/theater")
public class TheaterController {

    @Resource
    private TheaterService theaterService;

    @Resource
    private UsersService usersService;

    Logger LOG = LoggerFactory.getLogger(TheaterService.class);

    @RequestMapping("/")
    public String index(Model model){
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        return "register";
    }
    /**
     * 添加影院
     * @param addTheaterRequest
     * @return
     */
    @PostMapping("/toregister")
    public String addTheater(@RequestBody AddTheaterRequest addTheaterRequest) throws Exception {
        theaterService.addTheater(addTheaterRequest);
        return  "forward:/theater/";//跳转到登录界面
    }

    /**
     * 登入
     * @param tName 用户名
     * @param pwd 密码
     * @param type 用户类型
     * @param model
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/inlogin")
    public String addTheater(String tName, String pwd, Model model,Integer type,
                             HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        if (type == 1) {
            Theater theater = theaterService.findByTName(tName);
            if (theater == null) {
                throw new TheaterException(ResultMapping.NO_USER);
            } else {
                if (theater.getPwd().equals(pwd)) {
                    request.getSession().setAttribute("tId", theater.getTId());//用户名存入该用户的session 中
                    return "forward:/hall/list";
                } else {
                    throw new TheaterException(ResultMapping.FAULT_PWD);
                }
            }
        }else{
            Users users = usersService.findByUName(tName);
            if (users == null) {
                throw new TheaterException(ResultMapping.NO_USER);
            } else {
                if (users.getPwd().equals(pwd)) {
                    request.getSession().setAttribute("uId", users.getUId());//用户名存入该用户的session 中
                    return "forward:/movie/";
                } else {
                    throw new TheaterException(ResultMapping.FAULT_PWD);
                }
            }
        }
    }


}
