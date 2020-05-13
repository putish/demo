package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.*;
import cn.zucc.demo.dao.*;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.OStatusEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.OrderDetailService;
import cn.zucc.demo.service.OrdersService;
import cn.zucc.demo.vo.HallDetailVo;
import cn.zucc.demo.vo.OrderDetailListVo;
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
 * @create: 2020-01-23 14:11
 **/
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private SeatDetailDao seatDetailDao;

    @Autowired
    private ScreenDao screenDao;

    @Autowired
    private HallDao hallDao;

    @Autowired
    private OrdersService ordersService;

    @Override
    @Transactional
    public OrderDetail addOrderDetail(AddOrderDetailRequest addOrderDetailRequest,Long oId) {
        OrderDetail orderDetail=OrderDetail.builder().oId(oId)
                .sdId(addOrderDetailRequest.getSdId())
                .sId(addOrderDetailRequest.getSId())
                .deleteFlag(DeleteFlagEnum.UN_DELETE.getValue())
                .build();
        SeatDetail seatDetail=seatDetailDao.findOne(orderDetail.getSdId());
        seatDetail.setUseState(UseStateEnum.IN_USE.getValue());
        seatDetailDao.save(seatDetail);
        Screen screen=screenDao.findOne(orderDetail.getSId());
        orderDetail.setPrice(screen.getPrice());
        screen.setTicketCount(screen.getTicketCount()+1);//已售票数加一
        screenDao.save(screen);
        orderDetail.setOStatus(OStatusEnum.YU_DINGH.getValue());

        orderDetailDao.save(orderDetail);
        return orderDetail;
    }

    @Override
    @Transactional
    public boolean deleteOrderDetail(Long odId) {
        OrderDetail orderDetail=orderDetailDao.findOne(odId);
        orderDetail.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());

        Screen screen=screenDao.findOne(orderDetail.getSId());
        screen.setTicketCount(screen.getTicketCount()-1);//已售票数减一
        screenDao.save(screen);
        List<OrderDetail> list=orderDetailDao.findList(orderDetail.getOId());
        if (list.size()==0){
            ordersService.ordersDelete(orderDetail.getOId());
        }
        orderDetailDao.save(orderDetail);
        return true;
    }

    @Override
    public List<OrderDetailListVo> findList(Long oId) {
        List<OrderDetail> orderDetails=orderDetailDao.findList(oId);
        List<OrderDetailListVo> list=new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails){
            OrderDetailListVo vo=new OrderDetailListVo();
            vo.setOId(orderDetail.getOId());
            vo.setOdId(orderDetail.getOdId());
            vo.setPrice(orderDetail.getPrice());

            Screen screen=screenDao.findOne(orderDetail.getSId());
            Movie movie=movieDao.findOne(screen.getMId());
            SeatDetail seatDetail=seatDetailDao.findOne(orderDetail.getSdId());
            Hall hall=hallDao.findOne(screen.getHId());

            vo.setHName(hall.getHName());
            vo.setMName(movie.getMName());
            vo.setStartTime(screen.getStartTime());
            vo.setEndTime(screen.getEndTime());
            vo.setXAxis(seatDetail.getXAxis());
            vo.setYAxis(seatDetail.getYAxis());
            System.out.println(vo.getXAxis()+"排"+vo.getYAxis()+"座");
            list.add(vo);
        }
        return list;
    }
    public boolean unsubscribeDetail(Long odId) {
        OrderDetail orderDetail=orderDetailDao.findOne(odId);
        orderDetail.setOStatus(OStatusEnum.TUI_DING.getValue());
        Screen screen=screenDao.findOne(orderDetail.getSId());
        screen.setTicketCount(screen.getTicketCount()-1);//已售票数减一
        screenDao.save(screen);
        orderDetailDao.save(orderDetail);
        return true;
    }

    @Override
    public boolean payOrderDetail(Long odId) {
        OrderDetail orderDetail=orderDetailDao.findOne(odId);
        if (orderDetail.getOStatus().equals(OStatusEnum.YU_DINGH.getValue())) {
            orderDetail.setOStatus(OStatusEnum.FINISH.getValue());
            orderDetailDao.save(orderDetail);
            return true;
        }
        return false;
    }
}
