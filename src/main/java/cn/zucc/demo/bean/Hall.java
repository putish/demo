package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Hall {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long hId;
//    影厅名称
    private String hName;
//    行数
    private Integer rows;
//    列数
    private Integer cols;
//    座位数量
    private Integer seatCount;
//    屏幕类型
    private String screenCate;
    //    影院id
    private Long tId;
//    使用状态
    private Integer useState;
//    删除标志
    private Integer deleteFlag;

}
