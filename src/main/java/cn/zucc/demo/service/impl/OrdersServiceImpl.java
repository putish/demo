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
    private ScreenDao screenDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private TheaterDao theaterDao;

    @Override
    @Transactional
    public boolean addOrders(List<AddOrderDetailRequest> requests, Long uId) {
        Orders orders=new Orders();
        BigDecimal prices=BigDecimal.ZERO;

        orders.setOStatus(OStatusEnum.YU_DINGH.getValue());
        orders.setStartTime(new Date());
        orders.setUId(uId);
        Screen screen=screenDao.findOne(requests.get(0).getSId());//获取影院id
        orders=ordersDao.save(orders);
        orders.setTId(screen.getTId());
        for(AddOrderDetailRequest request:requests){//添加订单详情
            OrderDetail orderDetail=orderDetailService.addOrderDetail(request,orders.getOId());
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
        if (orders.getUId()==uId){
            orders.setOStatus(OStatusEnum.FINISH.getValue());
            ordersDao.save(orders);
            return true;
        }
        return false;
    }

    @Override
    public boolean unsubscribeOrders(Long oId, Long uId) {
        Orders orders=ordersDao.findOne(oId);
        if (orders.getUId()==uId){
            orders.setOStatus(OStatusEnum.TUI_DING.getValue());
            List<OrderDetail> details=orderDetailDao.findByOId(oId);
            for(OrderDetail detail:details){//添加订单详情
                orderDetailService.deleteOrderDetail(detail.getOdId());
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
            vo.setOStatus(OStatusEnum.getContentByValue(order.getOStatus()));
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
        vo.setOStatus(OStatusEnum.getContentByValue(order.getOStatus()));
        vo.setPrice(order.getPrice());
        vo.setStartTime(order.getStartTime());
        Users users=usersDao.findOne(order.getUId());
        vo.setUName(users.getUName());
        List<OrderDetailListVo> detailListVos=orderDetailService.findList(order.getOId());
        vo.setList(detailListVos);
        return vo;
    }
}
