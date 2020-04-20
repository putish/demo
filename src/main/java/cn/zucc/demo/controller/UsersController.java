package cn.zucc.demo.controller;

import cn.zucc.demo.form.AddTheaterRequest;
import cn.zucc.demo.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-17 20:05
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersService usersService;

    /**
     * 添加用户
     * @param uName
     * @return
     * @throws Exception
     */
    @PostMapping("/toregister")
    public String addTheater(@RequestParam(required = false) String uName,@RequestParam(required = false) String pwd) throws Exception {
        usersService.addUsers(uName, pwd);
        return  "register";
    }
}
