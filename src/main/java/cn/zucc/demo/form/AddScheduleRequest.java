package cn.zucc.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    //    影片id
    private Long mId;
    //    第一排片量
    private Long fCount;
    //    第二排片量
    private Long sCount;
    //    第三排片量
    private Long tCount;
    //    屏幕类型
    private String screenCate;

}
