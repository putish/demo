package cn.zucc.demo.controller;

import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.service.OrdersService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.vo.OrdersListVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
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
        Long uId= (Long) session.getAttribute("uId");
        ordersService.addOrders(request,uId);
        return "book";
    }
    @GetMapping("/list" )
    public String ordersList(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2") int pageSize,
                             @RequestParam(required = false) String oStatus, @RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime, Model model) throws ParseException {
        Long uId= (Long) session.getAttribute("uId");
        Long tId= (Long) session.getAttribute("tId");
        Integer oStateEnum=null;
        if (oStatus=="预订"){
            oStateEnum=1;
        }else if (oStatus=="退订"){
            oStateEnum=2;
        }else if (oStatus=="支付完成"){
            oStateEnum=3;
        }
        List<OrdersListVo> list=ordersService.findList(tId,uId,oStateEnum,startTime==null?null:DateUtil.toDate(startTime),endTime==null?null:DateUtil.toDate(endTime));
        model.addAttribute("list",list);
        return "ordersList";
    }
    @PostMapping( "/pay" )
    public String payOrders(@RequestParam(value = "oId", defaultValue = "0") Long oId, HttpSession session ) {
        Long uId= (Long) session.getAttribute("uId");
        ordersService.payOrders(oId,uId);
        return "ordersList";
    }
    @PostMapping( "/unsubscribe" )
    public String unsubscribeOrders(@RequestParam(value = "oId", defaultValue = "0") Long oId, HttpSession session ) {
        Long uId= (Long) session.getAttribute("uId");
        ordersService.unsubscribeOrders(oId,uId);
        return "ordersList";
    }
}
