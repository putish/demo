package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @description: 排片
 * @author: hjj
 * @create: 2020-04-06 21:07
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Schedule {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long scId;
//    影片id
    private Long mId;
//    第一排片量
    private Integer fCount;
//    第二排片量
    private Integer sCount;
//    第三排片量
    private Integer tCount;
//    屏幕类型
    private String screenCate;
//    影院id
    private Long tId;
//    删除标志
    private Integer deleteFlag;
    //    票价
    private BigDecimal price;


}
