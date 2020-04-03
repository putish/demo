package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.bean.SeatDetail;
import cn.zucc.demo.dao.HallDao;
import cn.zucc.demo.dao.SeatDetailDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.HallService;
import cn.zucc.demo.service.SeatDetailService;
import cn.zucc.demo.vo.HallOptionVo;
import cn.zucc.demo.vo.HallTimeTableVo;
import cn.zucc.demo.vo.SeatAddVo;
import cn.zucc.demo.vo.SeatVo;
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
    public boolean addHall( AddHallRequest addHallRequest) {
        Hall hall=new Hall();
        BeanUtils.copyProperties(addHallRequest,hall);
        hall.setTId(addHallRequest.getTId());
        hall.setUseState(UseStateEnum.IN_SPARE.getValue());
        hall.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        hall= hallDao.save(hall);
        for(SeatAddVo seatVo:addHallRequest.getSeatVos()){
            if(seatVo.getXAxis()<=hall.getRows()&&seatVo.getYAxis()<=hall.getCols()){
                seatDetailService.addSeatDetail(seatVo,hall.getHId(),addHallRequest.getTId());
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
        List<Hall> halls=hallDao.findByHIdAndTIdAndDeleteFlag(hId, tId,DeleteFlagEnum.UN_DELETE.getValue());
        if(halls.size()==0){//是否有该播放厅
            throw new TheaterException(ResultMapping.NO_HALL);
        }
        else {
            Hall hall=halls.get(0);

            if (hall.getUseState()==UseStateEnum.IN_USE.getValue()){
                throw new TheaterException(ResultMapping.IN_USE);
            }
            List<HallTimeTableVo> list=hallDao.hallTimeTableList(hId,tId);
            if (list.size()==0){
                hall.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Hall> findList(Integer useState, String screenCate, Integer startCount, Integer endCount, Long tId) {
        List<Hall> list=hallDao.findList(useState,screenCate,startCount,endCount,tId);

        return list;
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
    public Hall hallDetail(Long hId, Long tId) {
        List<Hall> list=hallDao.findByHIdAndTIdAndDeleteFlag(hId, tId,DeleteFlagEnum.UN_DELETE.getValue());
        if (list.size()==0){
            throw new TheaterException(ResultMapping.NO_HALL);
        }
        return list.get(0);
    }

    @Override
    @Transactional
    public boolean editHall(Long hId,List<SeatVo> seatVos,AddHallRequest addHallRequest, Long tId) {
        List<Hall> halls=hallDao.findByHIdAndTIdAndDeleteFlag(hId, tId,DeleteFlagEnum.UN_DELETE.getValue());
        if(halls.size()==0){//是否有该播放厅
            throw new TheaterException(ResultMapping.NO_HALL);
        }
        else {
            Hall hall=halls.get(0);

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
                                .useState(seatVo.getDeleteFlag())
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
        }

        return false;
    }

    @Override
    public List<HallTimeTableVo> hallTimeTable(Long hId, Date startTime, Date endTime, Long tId) {
        return hallDao.findhallTimeTable(hId, startTime, endTime, tId);
    }
}
