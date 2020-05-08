package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: hjj
 * @create: 2020-05-01 20:42
 */
@Getter
@Setter
public class MovieDetailVo {
    private Long mId;
    //    电影名称
    private String mName;
    //    海报
    private String poster;
    //    导演
    private String director;
    //    演员
    private String actor;
    //    时长
    private Integer duration;
    //    电影类别
    private String catergory;
    //    简介
    private String description;
//    上映时间
    private String showTime;
    //    下架时间
    private String endTime;
    //    影院id
    private Long tId;
    //    上映状态
    private Integer showState;
    //    删除标志
    private Integer deleteFlag;
}
