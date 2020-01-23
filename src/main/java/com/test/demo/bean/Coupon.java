package com.test.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    @Id
    private  String coId;
//    用户id
    private String uId;
//    条件
    private Integer conditions;
//    价值
    private Integer qutoa;
    @CreatedDate
//    开始时间
    private Date createTime;
    @LastModifiedDate
//    结束时间
    private Date endTime;

}
