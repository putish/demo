package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.*;
import cn.zucc.demo.dao.*;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.OStatusEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.form.AddOrderRequest;
import cn.zucc.demo.form.AddOrdersRequest;
import cn.zucc.demo.service.OrderDetailService;
import cn.zucc.demo.service.OrdersService;
import cn.zucc.demo.util.DateUtil;
import cn.zucc.demo.vo.OrderDetailListVo;
import cn.zucc.demo.vo.OrdersDetailVo;
import cn.zucc.demo.vo.OrdersListVo;
import cn.zucc.demo.vo.SeatAddVo;
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
    private ScreenDao screenDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private TheaterDao theaterDao;

    @Override
    @Transactional
    public boolean addOrders(AddOrderRequest request, Long uId) {
        Orders orders=new Orders();
        BigDecimal prices=BigDecimal.ZERO;

        orders.setOStatus(OStatusEnum.YU_DINGH.getValue());
        orders.setStartTime(new Date());
        orders.setUId(uId);
        Screen screen=screenDao.findOne(request.getSId());//获取影院id
        Movie movie=movieDao.findOne(screen.getMId());
        orders.setTitle(movie.getMName());
        orders.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        orders.setTId(screen.getTId());
        orders=ordersDao.save(orders);

        for(AddOrderDetailRequest seatAddVo :request.getSeatVos()){
            //添加订单详情
            OrderDetail orderDetail=orderDetailService.addOrderDetail(seatAddVo,orders.getOId());
            prices.add(orderDetail.getPrice());
            if(orders.getTId()==null){
            }
        }
        orders.setPrice(prices);
        ordersDao.save(orders);//保存价格
        return true;
    }

    @Override
    public boolean payOrders(Long oId, Long uId) {
        Orders orders=ordersDao.findOne(oId);
        if (orders.getOStatus().equals(OStatusEnum.YU_DINGH.getValue())){
            orders.setOStatus(OStatusEnum.FINISH.getValue());
            orders.setEndTime(new Date());
            List<OrderDetail> details=orderDetailDao.findByOIdAndDeleteFlag(oId,DeleteFlagEnum.UN_DELETE.getValue());
            for(OrderDetail detail:details){//添加订单详情
                orderDetailService.payOrderDetail(detail.getOdId());
                ordersDao.save(orders);

            }
            ordersDao.save(orders);
            return true;
        }
        return false;
    }

    @Override
    public boolean unsubscribeOrders(Long oId, Long uId) {
        Orders orders=ordersDao.findOne(oId);
        if (orders.getOStatus().equals(OStatusEnum.FINISH.getValue())){//支付完成才可以退订
            orders.setOStatus(OStatusEnum.TUI_DING.getValue());
            List<OrderDetail> details=orderDetailDao.findByOIdAndDeleteFlag(oId,DeleteFlagEnum.UN_DELETE.getValue());
            for(OrderDetail detail:details){//添加订单详情
                orderDetailService.unsubscribeDetail(detail.getOdId());

            }
            ordersDao.save(orders);
            return true;
        }
        return false;
    }

    @Override
    public List<OrdersListVo> findList(Long tId,Long uId, Integer oState, Date startTime,Date endTime) {
        List<OrdersListVo> list=new ArrayList<>();
        List<Orders> orders=ordersDao.findList(tId, uId, oState, startTime, endTime);
        for (Orders order:orders){
            OrdersListVo vo=new OrdersListVo();
            vo.setOId(order.getOId());
            vo.setTitle(order.getTitle());
            vo.setOStatus(OStatusEnum.getContentByValue(order.getOStatus()));
            vo.setPrice(order.getPrice());
            vo.setStartTime(order.getStartTime());
            Users users=usersDao.findOne(order.getUId());
            vo.setUName(users.getUName());
            List<OrderDetailListVo> detailListVos=orderDetailService.findList(order.getOId());
            vo.setList(detailListVos);
            list.add(vo);
        }
        return list;
    }

    @Override
    public OrdersListVo ordersDetail(Long oId, Long tId) {
        Orders order=ordersDao.findOne(oId);
        OrdersListVo vo=new OrdersListVo();
        vo.setOId(order.getOId());
        vo.setOStatus(OStatusEnum.getContentByValue(order.getOStatus()));
        vo.setPrice(order.getPrice());
        vo.setStartTime(order.getStartTime());
        Users users=usersDao.findOne(order.getUId());
        vo.setUName(users.getUName());
        List<OrderDetailListVo> detailListVos=orderDetailService.findList(order.getOId());
        vo.setList(detailListVos);
        return vo;
    }

    @Override
    public boolean ordersDelete(Long oId) {
        Orders orders=ordersDao.findOne(oId);
        orders.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
        ordersDao.save(orders);
        return true;
    }
    @Override
    public void ordersCheckTask() {
        List<Orders> orders=ordersDao.findByOStatus(OStatusEnum.YU_DINGH.getValue());
        for(Orders order:orders){
            if (DateUtil.getEndTime(order.getStartTime(),30).after(new Date())){
                order.setOStatus(OStatusEnum.OUT_OF_TIME.getValue());
                List<OrderDetail> details=orderDetailDao.findByOIdAndDeleteFlag(order.getOId(),DeleteFlagEnum.UN_DELETE.getValue());
                for(OrderDetail detail:details){//添加订单详情
                    orderDetailService.deleteOrderDetail(detail.getOdId());

                }
                ordersDao.save(order);
            }
        }
    }
}
