package com.test.demo.bean;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data

public class Catergory {
    private String cName;
    @Id
    private String cId;
}
