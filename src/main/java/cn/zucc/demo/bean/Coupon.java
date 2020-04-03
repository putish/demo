package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Coupon {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private  Long coId;
//    折扣名称
    private String coName;
//    用户id
    private Long uId;
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
