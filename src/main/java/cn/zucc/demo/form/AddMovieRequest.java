package cn.zucc.demo.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieRequest {
    //    电影名称
    private String mName;
    //    海报
    private String poster;
    //    导演
    private String director;
    //    演员
    private String actor;

    //    票价
    private BigDecimal price;
    //    时长
    private Integer duration;
//    分类
    private String catergory;
    //    简介
    private String description;
//    上映时间
    private String showTime;
    //    下架时间
    private String endTime;


}
