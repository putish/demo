package cn.zucc.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-20 09:35
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HallDetailVo {
    private Long hId;
    //    影厅名称
    private String hName;
    //    行数
    private Integer rows;
    //    列数
    private Integer cols;
    //    屏幕类型
    private String screenCate;
    //    影院id
    private Long tId;
    //    使用状态
    private Integer useState;
//座位表
    private List<SeatDelVo> seatVos;
}
