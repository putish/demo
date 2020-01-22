package com.test.demo.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Hall {
    @Id
    private String hId;
    private String hName;
    private Integer rows;
    private Integer cols;
    private Integer seatCount;
    private Integer screenCate;

}
