package com.test.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatDetail {
    @Id
    private String sdId;
//    影厅id
    private String hId;
//    x坐标
    private Integer xAxis;
//    y坐标
    private Integer yAxis;
    //    影院id
    private String cinemaId;


}
