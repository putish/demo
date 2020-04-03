package cn.zucc.demo.service;

import cn.zucc.demo.dao.OrderDetailDao;
import cn.zucc.demo.form.AddOrdersRequest;
import cn.zucc.demo.vo.OrdersDetailVo;
import cn.zucc.demo.vo.OrdersListVo;

import java.util.List;

public interface OrdersService {

    /**
     * 添加订单
     * @param addOrdersRequest
     * @param tId
     * @return
     */
    boolean addOrders(AddOrdersRequest addOrdersRequest, Long tId);

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
    boolean deleteOrders(Long oId,Long uId,Long tId);

    /**
     * 订单列表
     * @param tId
     * @return
     */
    List<OrdersListVo> findList(Long tId,Integer oState);

    /**
     * 订单详情
     * @param oId
     * @param tId
     * @return
     */
    OrdersListVo ordersDetail(Long oId,Long tId);
}
