package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Catergory {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cId")
    @Id
    private Long cId;
//    类别名称
    private String cName;

//    影院id
    private Long tId;
//    删除标志
    private Integer deleteFlag;
}
