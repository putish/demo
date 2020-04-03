package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Screen {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long sId;
//    电影id
    private Long mId;
//    影院id
    private Long hId;
//    票数
    private Integer ticketCount;
//    放映时间
    @CreatedDate
    private Date startTime;
//    散场时间
    @LastModifiedDate
    private Date endTime;
    //    影院id
    private Long tId;
//    上映状态
    private Integer showState;
//    删除标志
    private Integer deleteFlag;

}
