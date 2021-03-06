package cn.zucc.demo.service;

import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.vo.OrderDetailListVo;

import java.util.List;

public interface OrderDetailService {
    /**
     * 添加订单详情
     * @param addOrderDetailRequest
     * @return
     */
    OrderDetail addOrderDetail(AddOrderDetailRequest addOrderDetailRequest,Long oId);

    /**
     * 删除订单详情
     * @param odId
     * @return
     */
    boolean deleteOrderDetail(Long odId);

    /**
     * 退订
     * @param odId
     * @return
     */
    boolean unsubscribeDetail(Long odId);

    /**
     * 支付
     * @param odId
     * @return
     */
    boolean payOrderDetail(Long odId);
    /**
     *订单详情列表
     * @param oId
     * @return
     */
    List<OrderDetailListVo> findList(Long oId);


}
