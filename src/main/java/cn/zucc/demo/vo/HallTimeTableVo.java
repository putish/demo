package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description: 播放厅时刻表
 * @author: hjj
 * @create: 2020-02-26 20:06
 */
@Getter
@Setter
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

    public static HallTimeTableVo ObjectToVO(Map<String,Object> object){
        HallTimeTableVo vo=new HallTimeTableVo();
        vo.setHId((Long) object.get("hId"));
        vo.setHName((String) object.get("hName"));
        vo.setMName((String) object.get("mName"));
        vo.setStartTime((Date) object.get("startTime"));
        vo.setEndTime((Date) object.get("endTime"));
        return vo;
    }
}
