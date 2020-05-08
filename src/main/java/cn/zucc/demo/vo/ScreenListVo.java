package cn.zucc.demo.vo;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 播放场次列表
 * @author: hjj
 * @create: 2020-02-28 23:04
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreenListVo {
//    播放场次id
    private Long sId;
    //    影片名称
    private String mName;

    private Long hId;

    //    播放厅名称
    private String hName;
    //      票数
    private Integer ticketCount;
    //    票价
    private BigDecimal price;
    //    放映时间
    private Date startTime;
    //    散场时间
    private Date endTime;
    //    上映状态
    private String showState;
}
