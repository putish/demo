package com.test.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Orders {
    @Id
    private String oId;
    private String uId;
    private Integer oStatus;
    private String coId;
    @CreatedDate
    private Date startTime;
    @LastModifiedDate
    private Date endTime;
}
