package com.test.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Screen {
    @Id
    private String sId;
    private String mId;
    private Integer ticketCount;
    @CreatedDate
    private Date startTime;
}
