package cn.zucc.demo.vo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-27 13:00
 */
public class MovieVo {


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
    private String catergory;
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
    //    放映状态
    private String showState;
    //    上座率
    private BigDecimal attendence;
    //    影院下拉选项
    private List<TheatherOptionVo> optionVo;
}
