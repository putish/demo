package cn.zucc.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 时间工作类
 * @author: hjj
 * @create: 2020-02-28 21:52
 */
public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static int MILLIONSECOND=60000;//分钟转化毫秒

    private static int INSHOW=60000*60*24*3;//三天在线

    private static int NOSHOW=60000*60*24*10;//十天预售
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

    /**
     * 前后端转换
     * @param date 时间字符串
     * @return
     * @throws ParseException
     */
    public static Date toDate(String date) throws ParseException {
        return format.parse(date);

    }

    /**
     * 在线销售
     * @param date
     * @return
     */
    public static Date inshowDate(Date date){
        return new Date(date.getTime()+INSHOW);
    }

    /**
     * 预售
     * @param date
     * @return
     */
    public static Date noshowDate(Date date){
        return new Date(date.getTime()+NOSHOW);
    }

    /**
     * 获得当天零时零分零秒
     * @return
     */
    public static Date initDateByDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

}
