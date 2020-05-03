package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 播放厅时刻表
 * @author: hjj
 * @create: 2020-02-26 20:06
 */

@Value
public class HallTimeTableVo {
//    播放厅id
    private Long hId;
    //    影厅名称
    private String hName;
//    影片名称
    private String mName;
//    播放开始时间
    private Date startTime;
//    播放结束时间
    private Date endTime;


}
