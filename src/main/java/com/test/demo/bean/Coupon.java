package com.test.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Coupon {
    @Id
    private  String coId;
    private String uId;
    private Integer conditions;
    private Integer qutoa;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date endTime;

}
