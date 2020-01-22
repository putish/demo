package com.test.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class OrderDetail {
    @Id
    private String odId;
    private String oId;
    private String sdId;
    private String sId;
    private float rating;

}
