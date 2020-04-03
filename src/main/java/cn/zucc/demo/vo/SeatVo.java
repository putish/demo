package cn.zucc.demo.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: 座位
 * @author: hjj
 * @create: 2020-02-26 13:46
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatVo {
    private Long sdId;
//    行
    private int xAxis;
//  列
    private int yAxis;
//    删除标志
    private int deleteFlag;

}
