package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.*;
import cn.zucc.demo.dao.HallDao;
import cn.zucc.demo.dao.OrderDetailDao;
import cn.zucc.demo.dao.ScreenDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.OStatusEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.form.EditHallRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.service.ScreenService;
import cn.zucc.demo.service.SeatDetailService;
import cn.zucc.demo.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:11
 **/
@Service
public class HallServiceImpl implements HallService {
    @Autowired
    private HallDao hallDao;

   @Autowired
   private SeatDetailService seatDetailService;

    @Autowired
    private ScreenService screenService;

   @Autowired
    private OrderDetailDao orderDetailDao;

   @Autowired
   private ScreenDao screenDao;


    @Override
    @Transactional
    public boolean addHall( AddHallRequest addHallRequest,Long tId) {
        Hall hall=new Hall();
        BeanUtils.copyProperties(addHallRequest,hall);
        hall.setTId(tId);
        hall.setUseState(UseStateEnum.IN_USE.getValue());
        hall.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        hall= hallDao.save(hall);
        for(SeatAddVo seatVo:addHallRequest.getSeatVos()){
            if(seatVo.getXAxis()<=hall.getRows()&&seatVo.getYAxis()<=hall.getCols()){
                seatDetailService.addSeatDetail(seatVo,hall.getHId(),tId);
            }
            else{
                throw new TheaterException(ResultMapping.OUT_OF_RANGE);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteHall(Long hId, Long tId) {
        Hall hall=hallDao.findOne(hId);

        List<Screen> screens=screenDao.findByShowStateNotAndHIdAndDeleteFlag(ShowStateEnum.SOLD_OUT.getValue(),hId,DeleteFlagEnum.UN_DELETE.getValue());//查看播放厅时刻表
        if (screens.size()==0 ){//判断放映列表长度
            hall.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
            hallDao.save(hall);
            return true;
        }
        else {
            hall.setUseState(UseStateEnum.WILL_EDIT.getValue());
            hallDao.save(hall);
            throw new TheaterException(ResultMapping.CHANGE_TO_WILL_EDIT);
        }

    }

    @Override
    public List<HallListVo> findList(Integer pageNum, Integer pageSize,Integer useState, String screenCate, Integer startCount, Integer endCount, Long tId) {

        List<Hall> halls=hallDao.findList(useState,screenCate,startCount,endCount,tId);
        List<HallListVo> list=new ArrayList<>();
        for (Hall hall:halls){
            HallListVo vo=new HallListVo();
            BeanUtils.copyProperties(hall,vo);
            vo.setUseState(UseStateEnum.getContentByValue(hall.getUseState()));
            List<ScreenListVo> listVos=screenService.screenList(null,hall.getHId(),null,null,null,null);
            vo.setScreenListVos(listVos);
            list.add(vo);
        }

        return list;
    }

    @Override
    public List<HallOptionVo> optionList(Long tId) {
        List<Hall> list=hallDao.findByTIdAndDeleteFlagAndUseStateOrderBySeatCount(tId,DeleteFlagEnum.UN_DELETE.getValue(),UseStateEnum.IN_USE.getValue());
        List<HallOptionVo> optionVos=new ArrayList<>();
        for(Hall hall:list){
            HallOptionVo optionVo=new HallOptionVo();
            optionVo.setHId(hall.getHId());
            optionVo.setHName(hall.getHName());
            optionVos.add(optionVo);
        }
        return optionVos;
    }

    @Override
    public HallDetailVo hallDetail(Long hId, Long tId) {
        Hall hall=hallDao.findOne(hId);
        List<SeatDetail> seats=seatDetailService.findList(hId, tId);
        List<SeatDelVo> vos=new ArrayList<>();
        for (SeatDetail seat:seats){
            SeatDelVo vo=new SeatDelVo();
            vo.setXAxis(seat.getXAxis());
            vo.setYAxis(seat.getYAxis());
            vo.setSdId(seat.getSdId());
            vos.add(vo);
        }
        HallDetailVo hallDetail=new HallDetailVo();
        BeanUtils.copyProperties(hall,hallDetail);
        hallDetail.setSeatVos(vos);
        return hallDetail;
    }

    @Override
    @Transactional
    public boolean editHall(EditHallRequest request, Long tId) {
        Hall hall=hallDao.findOne(request.getHId());
//        BeanUtils.copyProperties(hall,request);

        List<Screen> screens=screenDao.findByShowStateNotAndHIdAndDeleteFlag(ShowStateEnum.SOLD_OUT.getValue(),hall.getHId(),DeleteFlagEnum.UN_DELETE.getValue());//查看播放厅时刻表
        if (screens.size()==0 ){//判断放映列表长度
            BeanUtils.copyProperties(request,hall);
            for(SeatAddVo seatVo:request.getAddSeatVos()){//新增座位
                if(seatVo.getXAxis()<=hall.getRows()&&seatVo.getYAxis()<=hall.getCols()){
                    seatDetailService.addSeatDetail(seatVo,hall.getHId(),tId);
                }
                else{
                    throw new TheaterException(ResultMapping.OUT_OF_RANGE);
                }
            }
            for(Long sdId:request.getDelSeatVos()){//删除座位
                seatDetailService.deleteSeatDetail(sdId,tId);

            }
            return true;
        }else {
            willEdit(hall.getHId());
            throw new TheaterException(ResultMapping.CHANGE_TO_WILL_EDIT);
        }


    }



    @Override
    public List<BookSeatVo> getSeat(Long tId, Long sId) {
        Screen screen=screenDao.findOne(sId);
        Hall hall=hallDao.findOne(screen.getHId());
        List<SeatDetail> seatDetails=seatDetailService.findList(hall.getHId(),tId);
        List<OrderDetail> orderDetails=orderDetailDao.findBySIdAndDeleteFlagAndOStatusNot(sId,DeleteFlagEnum.UN_DELETE.getValue(),
                OStatusEnum.TUI_DING.getValue());
        List<Long> useSDId=new ArrayList<>();//已经预定的座位
        for(OrderDetail orderDetail:orderDetails){
            useSDId.add(orderDetail.getSdId());
        }
        List<BookSeatVo> list=new ArrayList<>();
        for (SeatDetail seatDetail:seatDetails){
            BookSeatVo vo=new BookSeatVo(seatDetail.getSdId(),seatDetail.getXAxis(),seatDetail.getYAxis(),UseStateEnum.IN_SPARE.getValue());
            if(useSDId.contains(vo.getSdId())){
                vo.setUseState(UseStateEnum.IN_USE.getValue());
            }
            list.add(vo);
        }

        return list;
    }

    @Override
    public void HallCheackTask() {
        List<Hall> halls=hallDao.findByDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        for (Hall hall:halls){
            if(hall.getUseState()==UseStateEnum.WILL_EDIT.getValue()){
                List<Screen> screens=screenDao.findByShowStateNotAndHIdAndDeleteFlag(ShowStateEnum.SOLD_OUT.getValue(),hall.getHId(),DeleteFlagEnum.UN_DELETE.getValue());//查看播放厅时刻表
                if (screens.size()==0 ){//判断放映列表长度
                   hall.setUseState(UseStateEnum.CAN_EDIT.getValue());
                   hallDao.save(hall);
                }
            }

        }
    }

    @Override
    public List<HallTimeTableVo> getHallSreenList(Long hId, Long tId) {
        return null;
    }

    @Override
    public void willEdit(Long hId) {
        Hall hall=hallDao.findOne(hId);
        hall.setUseState(UseStateEnum.WILL_EDIT.getValue());
        hallDao.save(hall);
    }
}
