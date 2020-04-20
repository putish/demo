package cn.zucc.demo.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 添加订单请求
 * @author: hjj
 * @create: 2020-03-02 19:51
 */
@Getter
@Setter
public class AddOrdersRequest {
    //    用户id

    private List<Long> list;
}
