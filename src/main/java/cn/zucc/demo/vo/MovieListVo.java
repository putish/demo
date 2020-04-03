package cn.zucc.demo.vo;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 电影列表
 * @author: hjj
 * @create: 2020-02-24 23:45
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieListVo {

    private Long mId;
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
    //    电影类别
    private String ficId;
    private String secId;
    private String thcId;
    //    评分
    private BigDecimal rating;
    //    简介
    private String description;
    @CreatedDate
//    上映时间
    private Date showTime;
    //    下架时间
    @LastModifiedDate
    private Date endTime;
//    票房
    private Long tickets;
//    上座率
    private BigDecimal attendence;

}
