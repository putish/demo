package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单详情
 * @author: hjj
 * @create: 2020-03-01 00:21
 */
@Getter
@Setter
public class OrdersDetailVo {
    private Long odId;
    //    用户id
    private String uName;
    //    订单状态
    private Integer oStatus;
    //    价格
    private BigDecimal price;
    //    开始时间
    private Date startTime;
    //    折扣名称
    private String coName;
//    订单详情列表
    List<OrderDetailListVo> list;

}
