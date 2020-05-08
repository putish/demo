package cn.zucc.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description: 添加排片表
 * @author: hjj
 * @create: 2020-04-06 22:42
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddScheduleRequest {
    Long scId;
    //    影片id
    private Long mId;
    //    第一排片量
    private Integer fCount;
    //    第二排片量
    private Integer sCount;
    //    第三排片量
    private Integer tCount;
    //    屏幕类型
    private String screenCate;
    //    票价
    private BigDecimal price;

}
