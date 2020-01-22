package com.test.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @RequestMapping("/hi")
     public String sayHello() {

        ModelAndView modelAndView = new ModelAndView();

//        modelAndView.setViewName("test");

        modelAndView.addObject("key", 12345);

        //System.out.println("test");

        return "test";
    }
}
