package cn.zucc.demo.form;

import cn.zucc.demo.vo.SeatAddVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description: 添加订单详情请求
 * @author: hjj
 * @create: 2020-02-29 23:04
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderDetailRequest {
    //    座位详情id
    private Long sdId;
    //    播放场次id
    private Long sId;


}
