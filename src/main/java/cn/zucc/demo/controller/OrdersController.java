package cn.zucc.demo.controller;

import cn.zucc.demo.data.RootData;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.form.AddOrderRequest;
import cn.zucc.demo.service.OrdersService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.util.ResultUtil;
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
    @ResponseBody
    @PostMapping( "/add" )
    public RootData addOrders(@RequestBody AddOrderRequest request, HttpSession session ) {
        Long uId= (Long) session.getAttribute("uId");
        ordersService.addOrders(request,uId);
        return ResultUtil.success("添加成功");
    }
    @GetMapping("/list" )
    public String ordersList(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2") int pageSize,
                             @RequestParam(required = false) String oStatus, @RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime, Model model) throws ParseException {
        Long uId= (Long) session.getAttribute("uId");
        Integer oStateEnum=null;
        if (oStatus!=null) {
            if (oStatus.equals("预订")) {
                oStateEnum = 1;
            } else if (oStatus.equals("退订")) {
                oStateEnum = 2;
            } else if (oStatus.equals("支付完成")) {
                oStateEnum = 3;
            }
        }
        List<OrdersListVo> list=ordersService.findList(null,uId,oStateEnum,startTime==null?null:DateUtil.toDate(startTime),endTime==null?null:DateUtil.toDate(endTime));
        model.addAttribute("list",list);
        return "ordersList";
    }
    @GetMapping("/theaterlist" )
    public String orderstheaterList(HttpSession session,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2") int pageSize,
                             @RequestParam(required = false) String oStatus, @RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime, Model model) throws ParseException {
        Long tId= (Long) session.getAttribute("tId");
        Integer oStateEnum=null;
        if (oStatus!=null) {
            if (oStatus.equals("预订")) {
                oStateEnum = 1;
            } else if (oStatus.equals("退订")) {
                oStateEnum = 2;
            } else if (oStatus.equals("支付完成")) {
                oStateEnum = 3;
            }
        }
        List<OrdersListVo> list=ordersService.findList(tId,null,oStateEnum,startTime==null?null:DateUtil.toDate(startTime),endTime==null?null:DateUtil.toDate(endTime));
        model.addAttribute("list",list);
        return "orderlist";
    }
    @ResponseBody
    @PostMapping( "/pay" )
    public String payOrders(@RequestParam(value = "oId", defaultValue = "0") Long oId, HttpSession session ) {
        Long uId= (Long) session.getAttribute("uId");
        ordersService.payOrders(oId,uId);
        return "ordersList";
    }
    @ResponseBody
    @PostMapping( "/unsubscribe" )
    public RootData unsubscribeOrders(@RequestParam(value = "oId", defaultValue = "0") Long oId, HttpSession session ) {
        Long uId= (Long) session.getAttribute("uId");
        ordersService.unsubscribeOrders(oId,uId);
        return ResultUtil.success("退订成功");
    }
}
