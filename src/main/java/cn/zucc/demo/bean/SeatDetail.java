package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class SeatDetail {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long sdId;
//    影厅id
    private Long hId;
//    x坐标
    private Integer xAxis;
//    y坐标
    private Integer yAxis;
    //    影院id
    private Long tId;
//    删除标志
    private Integer deleteFlag;
//    使用状态
    private Integer useState;

}
