package com.test.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Hall {
    @Id
    private String hId;
//    影厅名称
    private String hName;
//    行数
    private Integer rows;
//    列数
    private Integer cols;
//    座位数量
    private Integer seatCount;
//    屏幕类型
    private Integer screenCate;
    //    影院id
    private String cinemaId;

}
