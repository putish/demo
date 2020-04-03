package cn.zucc.demo.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: 添加座位
 * @author: hjj
 * @create: 2020-03-25 23:16
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatAddVo {
    //    行
    private Integer xAxis;
    //  列
    private Integer yAxis;
}
