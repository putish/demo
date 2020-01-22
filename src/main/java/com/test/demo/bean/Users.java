package com.test.demo.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Users {
    @Id
    private String uId;
    private String sName;
    private String pwd;
    private Integer vip;
}
