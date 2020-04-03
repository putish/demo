package cn.zucc.demo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * @description: 播放场次列表
 * @author: hjj
 * @create: 2020-02-28 23:04
 */
@Getter
@Setter
@Builder
public class ScreenListVo {
//    播放场次id
    private Long sId;
    //    影片名称
    private String mName;
//    播放厅名称
    private String hName;
    //      票数
    private Integer ticketCount;
    //    放映时间
    private Date startTime;
    //    散场时间
    private Date endTime;
    //    影院id
    private Long tId;
    //    上映状态
    private Integer showState;
}
