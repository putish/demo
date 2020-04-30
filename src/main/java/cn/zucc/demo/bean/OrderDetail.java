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
public class OrderDetail {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long odId;
//    订单id
    private Long oId;
//    座位详情id
    private Long sdId;
//    播放场次id
    private Long sId;
//    价格
    private BigDecimal price;
//    删除标志
    private Integer deleteFlag;

}
