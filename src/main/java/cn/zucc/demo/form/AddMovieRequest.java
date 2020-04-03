package cn.zucc.demo.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class AddMovieRequest {
    //    电影名称
    private String mName;
    //    海报
    private String poster;
    //    导演
    private String director;
    //    屏幕类型
    private String screenCate;
    //    票价
    private BigDecimal price;
    //    时长
    private Integer duration;
    private Long ficId;
    private Long secId;
    private Long thcId;
    //    评分
    private BigDecimal rating;
    //    简介
    private String description;
//    上映时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date showTime;
    //    下架时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
