package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 影院
 * @author: hjj
 * @create: 2020-02-21 21:37
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Theater {
//    影院id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tId")
    @Id
    private Long tId;
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
//    创建时间
    @CreatedDate
    private Date createTime;
//间隔时间
    private Integer intervalTime;
//    vip折扣力度
    private BigDecimal vipDiscount;
    //    删除标志
    private Integer deleteFlag;
//    密码
    private String pwd;
    //    营业开始时间
    private String startTime;
    //    营业结束时间
    private String endTime;

}
