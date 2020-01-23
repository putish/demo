package com.test.demo.bean;

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
public class Screen {
    @Id
    private String sId;
//    电影id
    private String mId;
//    影院id
    private String hid;
//    票数
    private Integer ticketCount;
//    放映时间
    @CreatedDate
    private Date startTime;
//    散场时间
    @LastModifiedDate
    private Date endTime;
    //    影院id
    private String cinemaId;
}
