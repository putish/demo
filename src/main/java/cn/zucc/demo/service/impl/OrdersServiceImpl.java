package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.*;
import cn.zucc.demo.dao.*;
import cn.zucc.demo.enums.OStatusEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.form.AddOrdersRequest;
import cn.zucc.demo.service.OrderDetailService;
import cn.zucc.demo.service.OrdersService;
import cn.zucc.demo.vo.OrderDetailListVo;
import cn.zucc.demo.vo.OrdersDetailVo;
import cn.zucc.demo.vo.OrdersListVo;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:12
 **/
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private TheaterDao theaterDao;

    @Override
    @Transactional
    public boolean addOrders(AddOrdersRequest addOrdersRequest, Long tId) {
        Orders orders=new Orders();
        BigDecimal prices=BigDecimal.ZERO;
        if (addOrdersRequest.getCoId()!=null) {
            orders.setCoId(addOrdersRequest.getCoId());
            Coupon coupon=couponDao.findOne(orders.getCoId());
            if (prices.compareTo(BigDecimal.valueOf(coupon.getConditions()))==1){
                prices.subtract(BigDecimal.valueOf(coupon.getQutoa()));
                coupon.setUseState(UseStateEnum.IN_USE.getValue());
            }
        }
        orders.setOStatus(OStatusEnum.YU_DINGH.getValue());
        orders.setStartTime(new Date());
        orders.setUId(addOrdersRequest.getUId());
        orders.setTId(tId);

        for(AddOrderDetailRequest request:addOrdersRequest.getList()){//添加订单详情
            OrderDetail orderDetail=orderDetailService.addOrderDetail(request);
            prices.add(orderDetail.getPrice());
        }


        Users users=usersDao.findOne(addOrdersRequest.getUId());
        if (users.getIsvip()==1){
            Theater theater=theaterDao.findOne(tId);
            prices.multiply(theater.getVipDiscount());
        }
        orders.setPrice(prices);
        return true;
    }

    @Override
    public boolean payOrders(Long oId, Long uId) {
        Orders orders=ordersDao.findOne(oId);
        if (orders.getUId()==uId){
            orders.setOStatus(OStatusEnum.FINISH.getValue());
            ordersDao.save(orders);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrders(Long oId, Long uId, Long tId) {
        Orders orders=ordersDao.findOne(oId);
        if (orders.getUId()==uId){
            orders.setOStatus(OStatusEnum.TUI_DING.getValue());
            List<OrderDetail> details=orderDetailDao.findByOId(oId);
            for(OrderDetail detail:details){//添加订单详情
                orderDetailService.deleteOrderDetail(detail.getOdId());
            }
            if (orders.getCoId()!=null) {
                Coupon coupon = couponDao.findOne(orders.getCoId());
                if (coupon.getEndTime().compareTo(new Date()) >= -1) {
                    coupon.setUseState(UseStateEnum.IN_SPARE.getValue());
                } else {
                    coupon.setUseState(UseStateEnum.OUT_DATE.getValue());
                }
                couponDao.save(coupon);
            }
            ordersDao.save(orders);
            return true;
        }
        return false;
    }

    @Override
    public List<OrdersListVo> findList(Long tId,Integer oStatus) {
        List<OrdersListVo> list=new ArrayList<>();
        List<Orders> orders=new ArrayList<>();
        if (oStatus==null) {
            orders = ordersDao.findByTId(tId);
        }else {
            orders=ordersDao.findByTIdAndOStatus(tId,oStatus);
        }
        for (Orders order:orders){
            OrdersListVo vo=new OrdersListVo();
            vo.setOId(order.getOId());
            vo.setOStatus(order.getOStatus());
            vo.setPrice(order.getPrice());
            vo.setStartTime(order.getStartTime());
            Users users=usersDao.findOne(order.getUId());
            vo.setUName(users.getUName());
            List<OrderDetailListVo> detailListVos=orderDetailService.findList(order.getOId());
            vo.setList(detailListVos);
        }
        return list;
    }

    @Override
    public OrdersListVo ordersDetail(Long oId, Long tId) {
        Orders order=ordersDao.findOne(oId);
        OrdersListVo vo=new OrdersListVo();
        vo.setOId(order.getOId());
        vo.setOStatus(order.getOStatus());
        vo.setPrice(order.getPrice());
        vo.setStartTime(order.getStartTime());
        Users users=usersDao.findOne(order.getUId());
        vo.setUName(users.getUName());
        List<OrderDetailListVo> detailListVos=orderDetailService.findList(order.getOId());
        vo.setList(detailListVos);
        return vo;
    }
}
