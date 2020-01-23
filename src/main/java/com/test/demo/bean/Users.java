package com.test.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {
    @Id
    private String uId;
//    用户名
    private String uName;
//    密码
    private String pwd;
//    VIP标志
    private Boolean isvip;
}
