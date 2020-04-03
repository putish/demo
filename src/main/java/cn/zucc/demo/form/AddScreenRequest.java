package cn.zucc.demo.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

/**
 * @description: 添加放映场次
 * @author: hjj
 * @create: 2020-02-26 19:54
 */
@Getter
@Setter
public class AddScreenRequest {
//    影片id
    private String mId;
    //    影院id
    private String hId;
    //    票数
    private Integer ticketCount;
    //    放映时间
    @CreatedDate
    private Date startTime;
//    散场时间
}
