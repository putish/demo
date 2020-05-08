package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.SeatDetail;
import cn.zucc.demo.dao.SeatDetailDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.service.SeatDetailService;
import cn.zucc.demo.vo.SeatAddVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:13
 **/
@Service
public class SeatDetailServiceImpl implements SeatDetailService {
    @Autowired
    private SeatDetailDao seatDetailDao;

    @Override
    @Transactional
    public boolean addSeatDetail(SeatAddVo seatVo, Long hId, Long tId) {
        SeatDetail seatDetail=new SeatDetail();
        seatDetail.setHId(hId);
        seatDetail.setXAxis(seatVo.getXAxis());
        seatDetail.setYAxis(seatVo.getYAxis());
        seatDetail.setTId(tId);
        seatDetail.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        seatDetail.setUseState(UseStateEnum.IN_USE.getValue());
        seatDetailDao.save(seatDetail);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteSeatDetail(Long sdId, Long tId) {
        SeatDetail seatDetail=seatDetailDao.findOne(sdId);
        seatDetail.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
        seatDetailDao.save(seatDetail);
        return true;
    }

    @Override
    public List<SeatDetail> findList(Long hId, Long tId) {
        return seatDetailDao.findByHIdAndDeleteFlag(hId,DeleteFlagEnum.UN_DELETE.getValue());
    }

    @Override
    @Transactional
    public boolean editSeatDetail(SeatDetail seatDetail) {
        seatDetailDao.save(seatDetail);
        return true;
    }

    @Override
    public SeatDetail SeatDetailDetil(Long sdId, Long tId) {
        return seatDetailDao.findOne(sdId);
    }
}
