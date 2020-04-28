package cn.zucc.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-04-28 14:56
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDelVo {
    private Long sdId;
    //    行
    private Integer xAxis;
    //  列
    private Integer yAxis;
}
