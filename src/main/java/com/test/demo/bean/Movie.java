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
public class Movie {
    @Id
    private String mId;
//    电影名称
    private String mName;
//    海报
    private String poster;
//    导演
    private String director;

    private Integer discount;
//    屏幕类型
    private String screenCate;
//    票价
    private float price;
//    时长
    private Integer duration;

    private String ficId;
    private String secId;
    private String thcId;
//    上座率
    private float rating;
//    简介
    private String description;
    @CreatedDate
//    上映时间
    private Date showTime;
//    下架时间
    @LastModifiedDate
    private Date endTime;
    //    影院id
    private String cinemaId;
}

