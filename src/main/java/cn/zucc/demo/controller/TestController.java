package cn.zucc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @RequestMapping("test")
    public String test(Model model){
        return "test";
    }
    @RequestMapping("/hi")
     public String sayHello(@ModelAttribute(value = "one") Integer one, Model model) {
//        int two=one+3;
        model.addAttribute("two",one);
        model.addAttribute("key", 12345);

        //System.out.println("test");

        return "test";
    }
//    @RequestMapping("/hi")
//    public String hall(Model model) {
//
//
////        modelAndView.setViewName("test");
//
//        model.addAttribute("key", 12345);
//
//        //System.out.println("test");
//
//        return "hall";
//    }
}
