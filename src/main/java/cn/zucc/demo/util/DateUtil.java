package cn.zucc.demo.util;

import java.util.Date;

/**
 * @description: 时间工作类
 * @author: hjj
 * @create: 2020-02-28 21:52
 */
public class DateUtil {

    private static int MILLIONSECOND=60000;//分钟转化毫秒

    /**
     * 获取放映结束时间
     * @param startTime 放映开始时间
     * @param duration 影片时长
     * @return
     */
    public static Date getEndTime(Date startTime,Integer duration){
        return new Date(startTime.getTime()+duration*MILLIONSECOND);
    }

    /**
     * 获取播放影片间隔后时间
     * @param endTime 放映结束时间
     * @param intervalTime 间隔时间
     * @return
     */
    public static Date getSpareTime(Date endTime,Integer intervalTime) {
        return new Date(endTime.getTime()+intervalTime*MILLIONSECOND);

    }
}
