package cn.zucc.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-05-02 10:23
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderRequest {

    //    播放场次id
    private Long sId;


    private List<AddOrderDetailRequest> seatVos;
}
