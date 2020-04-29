package cn.zucc.demo.service;

import cn.zucc.demo.dao.OrderDetailDao;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.form.AddOrdersRequest;
import cn.zucc.demo.vo.OrdersDetailVo;
import cn.zucc.demo.vo.OrdersListVo;

import java.util.Date;
import java.util.List;

public interface OrdersService {

    /**
     * 添加订单
     * @param requests
     * @param uId 用户id
     * @return
     */
    boolean addOrders(List<AddOrderDetailRequest> requests, Long uId);

    /**
     * 支付订单
     * @param oId
     * @return
     */
    boolean payOrders(Long oId,Long uId);

    /**
     * 退订
     * @param oId
     * @param uId
     * @return
     */
    boolean unsubscribeOrders(Long oId,Long uId);

    /**
     * 订单列表
     * @param tId 影院id
     * @return
     */
    List<OrdersListVo> findList(Long tId,Long uId, Integer oState, Date startTime,Date endTime);

    /**
     * 订单详情
     * @param oId
     * @param tId 影院id
     * @return
     */
    OrdersListVo ordersDetail(Long oId,Long tId);
}
