package cn.zucc.demo.form;

import cn.zucc.demo.vo.SeatAddVo;
import cn.zucc.demo.vo.SeatDelVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-04-28 14:39
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditHallRequest {

    private Long hId;
    //  影厅名称
    private String hName;
    //    行数
    private int rows;
    //    列数
    private int cols;
    //    座位数量
    private int seatCount;
    //    屏幕类型
    private String screenCate;
    //    删除座位表
    private List<Long> delSeatVos;
    //    新增座位表
    private List<SeatAddVo> addSeatVos;
}
