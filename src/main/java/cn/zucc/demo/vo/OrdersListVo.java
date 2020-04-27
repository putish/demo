package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单列表
 * @author: hjj
 * @create: 2020-03-01 00:17
 */
@Getter
@Setter
public class OrdersListVo {
    private Long oId;
    //    用户id
    private String uName;
    //    订单状态
    private String oStatus;
    //    价格
    private BigDecimal price;
    //    开始时间
    private Date startTime;
    //订单名称
    private String title;
    //    订单详情
    private List<OrderDetailListVo> list;


}
