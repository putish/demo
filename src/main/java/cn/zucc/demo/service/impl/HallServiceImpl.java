package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.bean.SeatDetail;
import cn.zucc.demo.dao.HallDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.service.SeatDetailService;
import cn.zucc.demo.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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



    @Override
    @Transactional
    public boolean addHall( AddHallRequest addHallRequest,Long tId) {
        Hall hall=new Hall();
        BeanUtils.copyProperties(addHallRequest,hall);
        hall.setTId(tId);
        hall.setUseState(UseStateEnum.IN_SPARE.getValue());
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
        if (hall.getUseState()==UseStateEnum.IN_USE.getValue()){
            throw new TheaterException(ResultMapping.IN_USE);//使用中
        }
        List<HallTimeTableVo> list=hallDao.hallTimeTableList(hId,tId);
        if (list.size()==0){//判断放映列表长度
            hall.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
            return true;
        }
        else {
            throw new TheaterException(ResultMapping.IN_USE);//使用中
        }

    }

    @Override
    public List<HallListVo> findList(Integer useState, String screenCate, Integer startCount, Integer endCount, Long tId) {
        List<Hall> list=hallDao.findList(useState,screenCate,startCount,endCount,tId);
        List<HallListVo> vos=new ArrayList<>();
        for (Hall hall:list){
            HallListVo vo=new HallListVo();
            vo.setCols(hall.getCols());
            vo.setHId(hall.getHId());
            vo.setHName(hall.getHName());
            vo.setUseState(UseStateEnum.getContentByValue(hall.getUseState()));//得到使用状态
            vo.setTId(hall.getTId());
            vo.setRows(hall.getRows());
            vo.setSeatCount(hall.getSeatCount());
            vo.setScreenCate(hall.getScreenCate());
            vo.setDeleteFlag(hall.getDeleteFlag());
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<HallOptionVo> optionList(Long tId) {
        List<Hall> list=hallDao.findByTIdAndDeleteFlag(tId,DeleteFlagEnum.UN_DELETE.getValue());
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
        List<SeatAddVo> vos=new ArrayList<>();
        for (SeatDetail seat:seats){
            SeatAddVo vo=new SeatAddVo();
            vo.setXAxis(seat.getXAxis());
            vo.setYAxis(seat.getYAxis());
            vos.add(vo);
        }
        HallDetailVo hallDetail=new HallDetailVo();
        BeanUtils.copyProperties(hall,hallDetail);
        hallDetail.setSeatVos(vos);
        return hallDetail;
    }

    @Override
    @Transactional
    public boolean editHall(Long hId,List<SeatVo> seatVos,AddHallRequest addHallRequest, Long tId) {
        Hall hall=hallDao.findOne(hId);
        if (hall.getUseState()==UseStateEnum.IN_USE.getValue()){
            throw new TheaterException(ResultMapping.IN_USE);
        }
        List<HallTimeTableVo> list=hallDao.hallTimeTableList(hId,tId);
        if (list.size()==0){
            for(SeatVo seatVo:seatVos){
                if(seatVo.getXAxis()<hall.getRows()&&seatVo.getYAxis()<hall.getCols()){
                    SeatDetail seatDetail=SeatDetail.builder()
                            .hId(hId)
                            .sdId(seatVo.getSdId())
                            .xAxis(seatVo.getXAxis())
                            .yAxis(seatVo.getYAxis())
                            .useState(seatVo.getUseState())
                            .tId(tId)
                            .build();
                    seatDetailService.editSeatDetail(seatDetail);
                }
                else{
                    throw new TheaterException(ResultMapping.OUT_OF_RANGE);
                }
            }
            return true;
        }


        return false;
    }

    @Override
    public List<HallTimeTableVo> hallTimeTable(Long hId, Date startTime, Date endTime, Long tId) {
//        List<Map<String,Object>> objects=hallDao.findhallTimeTable(hId, startTime, endTime, tId);
//        List<HallTimeTableVo> list=new ArrayList<>();
//        for(Map<String,Object> object :objects){
//            list.add(HallTimeTableVo.ObjectToVO(object));
//        }

        return null;
    }

    @Override
    public List<SeatVo> getSeat(Long hId,Long tId) {
        List<SeatDetail> seatDetails=seatDetailService.findList(hId,tId);
        List<SeatVo> list=new ArrayList<>();
        for (SeatDetail seatDetail:seatDetails){
            SeatVo vo=new SeatVo(seatDetail.getSdId(),seatDetail.getXAxis(),seatDetail.getYAxis(),seatDetail.getUseState());
            list.add(vo);
        }

        return list;
    }
}
