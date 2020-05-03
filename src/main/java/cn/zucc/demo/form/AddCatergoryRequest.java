package cn.zucc.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description:
 * @author: hjj
 * @create: 2020-05-02 17:52
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCatergoryRequest {
    private Long cId;
    private String cName;
}
