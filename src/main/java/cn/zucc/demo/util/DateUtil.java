package cn.zucc.demo.util;

import cn.zucc.demo.enums.HolidayEnum;

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
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static double GOLDRATE=3.4/14.5;//黄金时间与营业时间比例

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
     * 根据结束放映结束时间获取开始时间
     * @param endTime 放映结束时间
     * @param duration 影片时长
     * @return
     */
    public static Date getStartTime(Date endTime,Integer duration){
        return new Date(endTime.getTime()-duration*MILLIONSECOND);
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
     * 前后端转换
     * @param date 时间字符串
     * @return
     * @throws ParseException
     */
    public static String toString(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;

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

    /**
     * 黄金时间开场
     * @return
     */
    public static Date startGold(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 黄金时间结束
     * @return
     */
    public static Date endGold(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 判断节假日
     * @param date
     * @return
     */
    public static HolidayEnum judgeDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(calendar.get(Calendar.MONTH)==1&&calendar.get(Calendar.DAY_OF_MONTH)==1){
                return HolidayEnum.NEW_YEAR;//元旦
        }else if (calendar.get(Calendar.MONTH)==2&&calendar.get(Calendar.DAY_OF_MONTH)==14){
                return HolidayEnum.VALENTINE;//情人节
        } else if (calendar.get(Calendar.MONTH)==5&&calendar.get(Calendar.DAY_OF_MONTH)==1){
                return HolidayEnum.LABOUR;//劳动节
        }else if (calendar.get(Calendar.MONTH)==6&&calendar.get(Calendar.DAY_OF_MONTH)==1){
                return HolidayEnum.CHILDREN;//儿童节
        }else if (calendar.get(Calendar.MONTH)==10&&calendar.get(Calendar.DAY_OF_MONTH)>=1&&calendar.get(Calendar.DAY_OF_MONTH)<=7){
                return HolidayEnum.NATIONAL;//国庆节
        }else if (calendar.get(Calendar.MONTH)==11&&calendar.get(Calendar.DAY_OF_MONTH)==1){
                return HolidayEnum.All_SAINTS;//万圣节
        } else {
            if(calendar.get(Calendar.DAY_OF_WEEK)<6){
                return HolidayEnum.WORKDAY;//工作日
            }
            else {
                return HolidayEnum.WEEKEND;//双休日
            }
        }
    }
    public static Date getOpenHours(String dateString){
        String[] date=dateString.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getEndTime(new Date(),60*24));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(date[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(date[1]));
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
