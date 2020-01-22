package com.test.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Movie {
    @Id
    private String mId;
    private String mName;
    private String poster;
    private String director;
    private Integer discount;
    private String screenCate;
    private float price;
    private Integer duration;
    private String ficId;
    private String secId;
    private String thcId;
    private float rating;
    private String description;
    @CreatedDate
    private Date showTime;
    @LastModifiedDate
    private Date endTime;
}
