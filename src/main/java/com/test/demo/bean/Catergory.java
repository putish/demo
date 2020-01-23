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
public class Catergory {
//    类别名称
    private String cName;
    @Id
    private String cId;
//    影院id
    private String cinemaId;
}
