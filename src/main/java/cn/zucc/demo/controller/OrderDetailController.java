package cn.zucc.demo.controller;

import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.service.OrderDetailService;
import cn.zucc.demo.service.SeatDetailService;
import cn.zucc.demo.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: hjj
 * @create: 2020-05-02 11:13
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Resource
    private OrderDetailService orderDetailService;

    @ResponseBody
    @PostMapping( "/delete" )
    public RootData payOrders(@RequestParam(value = "odId", defaultValue = "0") Long odId, HttpSession session ) {
        Long uId= (Long) session.getAttribute("uId");
        orderDetailService.deleteOrderDetail(odId);
        return ResultUtil.success("删除成功");
    }
}
