package com.test.demo.bean;

import com.test.demo.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    private String oId;
//    用户id
    private String uId;
//    订单状态
    private OrderStatusEnum oStatus;
//    折扣id
    private String coId;
//    价格
    private Float price;
//    开始时间
    @CreatedDate
    private Date startTime;
//    结束时间
    @LastModifiedDate
    private Date endTime;

    //    影院id
    private String cinemaId;
}
