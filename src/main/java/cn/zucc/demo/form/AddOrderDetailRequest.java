package cn.zucc.demo.form;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 添加订单详情请求
 * @author: hjj
 * @create: 2020-02-29 23:04
 */
@Getter
@Setter
public class AddOrderDetailRequest {
    //    订单id
    private Long oId;
    //    座位详情id
    private Long sdId;
    //    播放场次id
    private Long sId;
}
