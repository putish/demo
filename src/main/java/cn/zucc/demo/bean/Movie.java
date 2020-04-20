package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.annotations.DynamicUpdate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Movie {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mId")
    @Id
    private Long mId;
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
//    电影类别
    private Long ficId;
    private Long secId;
    private Long thcId;
    //屏幕类型
    private String screenCate;
//    简介
    private String description;
    @CreatedDate
//    上映时间
    private Date showTime;
//    下架时间
    @LastModifiedDate
    private Date endTime;
    //    影院id
    private Long tId;
//    上映状态
    private Integer showState;
}

