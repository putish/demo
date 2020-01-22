package com.test.demo.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SeatDetail {
    @Id
    private String sdId;
    private String hId;
    private Integer xAxis;
    private Integer yAxis;

}
