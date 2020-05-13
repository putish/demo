package cn.zucc.demo.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 添加放映场次
 * @author: hjj
 * @create: 2020-02-26 19:54
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddScreenRequest {
//    播放场次id
    private Long sId;
//    影片id
    private Long mId;
    //    影院id
    private Long hId;
    //    放映时间
    private String startTime;
//    散场时间
    private String screenCate;
//    价格
    private BigDecimal price;
}
