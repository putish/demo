package cn.zucc.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Users {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long uId;
//    用户名
    private String uName;
//    密码
    private String pwd;
//    VIP标志
    private Integer isvip;
}
