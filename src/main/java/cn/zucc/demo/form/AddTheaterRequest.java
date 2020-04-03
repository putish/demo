package cn.zucc.demo.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @description: 添加影院请求
 * @author: hjj
 * @create: 2020-03-06 17:40
 */
@Getter
@Setter
public class AddTheaterRequest {
    //    影院名称
    private String tName;
    //    影院联系人名称
    private String tLegalName;
    //    影院联系人电话
    private String tLegalPhone;
    //    影院法人名称
    private String tContactName;
    //    影院法人电话
    private String tContactPhone;
    //    影院地址
    private String tAddress;
    //    社会统一信用代码
    private String tCreditCode;
    //间隔时间
    private Integer intervalTime;
    //    vip折扣力度
    private BigDecimal vipDiscount;
}
