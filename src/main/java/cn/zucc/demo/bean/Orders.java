package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Orders {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long oId;
//    用户id
    private Long uId;
//    订单状态
    private Integer oStatus;
//    折扣id
    private Long coId;
//    价格
    private BigDecimal price;
//    开始时间
    @CreatedDate
    private Date startTime;
//    结束时间
    @LastModifiedDate
    private Date endTime;
    //    影院id
    private Long tId;
    //    删除标志
    private Integer deleteFlag;
}
