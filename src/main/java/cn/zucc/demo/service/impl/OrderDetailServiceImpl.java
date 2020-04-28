package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.bean.SeatDetail;
import cn.zucc.demo.dao.*;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddOrderDetailRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.OrderDetailService;
import cn.zucc.demo.vo.OrderDetailListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    @Override
    @Transactional
    public OrderDetail addOrderDetail(AddOrderDetailRequest addOrderDetailRequest,Long oId) {
        OrderDetail orderDetail=OrderDetail.builder().oId(oId)
                .sdId(addOrderDetailRequest.getSdId())
                .sId(addOrderDetailRequest.getSId())
                .rating(BigDecimal.ZERO)
                .deleteFlag(DeleteFlagEnum.UN_DELETE.getValue())
                .build();
        SeatDetail seatDetail=seatDetailDao.findOne(orderDetail.getSdId());
        if(seatDetail.getUseState()==UseStateEnum.IN_USE.getValue()) {
            throw new TheaterException(ResultMapping.NO_SEAT);
        }
        seatDetail.setUseState(UseStateEnum.IN_USE.getValue());
        seatDetailDao.save(seatDetail);
        Screen screen=screenDao.findOne(orderDetail.getSId());
        Movie movie=movieDao.findOne(screen.getMId());
        orderDetail.setPrice(movie.getPrice());
        screen.setTicketCount(screen.getTicketCount()+1);//已售票数加一
        screenDao.save(screen);
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
        orderDetailDao.save(orderDetail);
        return true;
    }

    @Override
    public List<OrderDetailListVo> findList(Long oId) {
        return orderDetailDao.findList(oId);
    }
}
