package cn.zucc.demo.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description: 影片优先度
 * @author: hjj
 * @create: 2020-04-22 23:45
 */
@Getter
@Setter
public class MovieSort {
    private Long mId;
    //    上座率
    private BigDecimal attendence;
    //    票房
    private Long tickets;
    //    上座率
    private Float categoryIndex;
//    优先度
    private Float totalSort;
    //    时长
    private Integer duration;
    //    影院id
    private Long tId;
//    黄金时间排片量
    private Integer GoldSeatCount ;
    //    非黄金时间排片量
    private Integer UnGoldSeatCount;
    //    票价
    private BigDecimal price;
    //    屏幕类型
    private String screenCate;


}
