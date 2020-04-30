package cn.zucc.demo.vo;

import com.lly835.bestpay.rest.type.Get;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 订单详情列表
 * @author: hjj
 * @create: 2020-02-29 23:14
 */
@Getter
@Setter
public class OrderDetailListVo {
//    订单id
    private Long oId;
//    订单详情id
    private Long odId;
//    影片名称
    private String mName;
//    播放时间
    private Date startTime;
//    散场时间
    private Date endTime;
//    播放厅名称
    private String hName;
//    排数
    private Integer xAxis;
//    座数
    private Integer yAxis;
//    价格
    private BigDecimal price;
}
