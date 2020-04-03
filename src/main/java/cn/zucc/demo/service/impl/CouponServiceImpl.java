package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Coupon;
import cn.zucc.demo.bean.Users;
import cn.zucc.demo.dao.CouponDao;
import cn.zucc.demo.dao.UsersDao;
import cn.zucc.demo.enums.UseStateEnum;
import cn.zucc.demo.exception.TheaterException;
import cn.zucc.demo.mapping.ResultMapping;
import cn.zucc.demo.service.CouponService;
import cn.zucc.demo.vo.CouponListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 14:10
 **/
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private CouponDao couponDao;

    @Override
    @Transactional
    public Coupon addCoupon(String coName, Long uId, Integer conditions, Integer qutoa,Date startTime,Date endTime) {
        Coupon coupon=new Coupon();
        coupon.setCoName(coName);
        coupon.setConditions(conditions);
        coupon.setQutoa(qutoa);
        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);
        Users users=usersDao.findOne(uId);
        if (users==null){
            throw new TheaterException(ResultMapping.NO_USER);
        }
        coupon.setUId(uId);
        coupon.setUseState(UseStateEnum.IN_SPARE.getValue());
        return couponDao.save(coupon);
    }

    @Override
    @Transactional
    public boolean deleteCoupon(Long coId, Long uId) {
        Coupon coupon=couponDao.findOne(coId);
        coupon.setUseState(UseStateEnum.IN_USE.getValue());
        couponDao.save(coupon);
        return false;
    }

    @Override
    public List<CouponListVo> findList(String coName, Long uId, Integer useState) {
        if (coName.length()!=0&&coName!=null&&coName!=""){
            coName="%"+coName+"%";
        }
        return couponDao.findList(coName,uId,useState);
    }

    @Override
    public CouponListVo couponDetail(Long coId) {
        CouponListVo couponListVo=new CouponListVo();
        Coupon coupon=couponDao.findOne(coId);
        BeanUtils.copyProperties(coupon,couponListVo);
        Users users=usersDao.findOne(coupon.getUId());
        couponListVo.setUName(users.getUName());
        return couponListVo;
    }
}
