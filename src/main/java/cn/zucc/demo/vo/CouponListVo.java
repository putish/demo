package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import java.util.Date;

/**
 * @description: 优惠券列表
 * @author: hjj
 * @create: 2020-03-04 16:23
 */
@Setter
@Getter
public class CouponListVo {
    private  Long coId;
    //    折扣名称
    private String coName;
    //    用户id
    private String uName;
    //    条件
    private Integer conditions;
    //    价值
    private Integer qutoa;
    @CreatedDate
//    开始时间
    private Date startTime;
    @LastModifiedDate
//    结束时间
    private Date endTime;
    //    使用状态
    private Integer useState;
}
