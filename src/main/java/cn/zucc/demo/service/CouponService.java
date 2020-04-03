package cn.zucc.demo.service;

import cn.zucc.demo.bean.Coupon;
import cn.zucc.demo.vo.CouponListVo;

import java.util.Date;
import java.util.List;

public interface CouponService {
    /**
     *添加优惠券
     * @param coName 优惠券名称
     * @param uId
     * @param conditions 使用条件
     * @param qutoa 价值
     * @return
     */
    Coupon addCoupon(String coName, Long uId, Integer conditions, Integer qutoa,Date startTime, Date endTime);

    /**
     * 删除优惠券
     * @param coId
     * @param uId
     * @return
     */
    boolean deleteCoupon(Long coId,Long uId);

    /**
     * 优惠券列表
     * @param coName 优惠券名称
     * @param uId
     * @param useState 使用状态
     * @return
     */
    List<CouponListVo> findList(String coName,Long uId,Integer useState);

    /**
     * 优惠券详情
     * @param coId
     * @return
     */
    CouponListVo couponDetail(Long coId);



}
