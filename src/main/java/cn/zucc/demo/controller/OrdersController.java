package cn.zucc.demo.controller;

import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.service.OrdersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-07 19:06
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Resource
    private OrdersService ordersService;

    @GetMapping("/")
    public String index(){
        return "addmovie";
    }
    @PostMapping( "/add" )
    public String addOrders(@RequestBody List<AddOrderDetailRequest> request, HttpSession session ) {
        Long tId= (Long) session.getAttribute("uId");
        ordersService.addOrders(request,tId);
        return "book";
    }
}
