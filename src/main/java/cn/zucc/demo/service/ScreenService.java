package cn.zucc.demo.service;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.form.MovieSort;
import cn.zucc.demo.vo.ScreenListVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ScreenService {
    /**
     *添加放映场次
     * @param mId 影院id
     * @param hId
     * @param startTime 放映时间
     * @param tId 影院id
     * @param price 价格
     * @param screenCate 屏幕类型
     * @return
     */
    Screen addScreen(Long mId, Long hId, Date startTime, BigDecimal price, String screenCate, Long tId);

    /**
     * 删除放映场次
     * @param sId
     * @param tId 影院id
     * @return
     */
    boolean deleteScreen(Long sId,Long tId);
    /**
     *编辑放映场次
     * @param sId
     * @param mId 影院id
     * @param hId
     * @param startTime 放映时间
     * @param tId 影院id
     * @param price 价格
     * @param screenCate 屏幕类型
     * @return
     */
    boolean editScreen(Long sId,Long mId, Long hId,BigDecimal price, String screenCate,Date startTime, Long tId);

    /**
     * 放映场次详情
     * @param sId
     * @param tId 影院id
     * @return
     */
    ScreenListVo screenDetail(Long sId, Long tId);

    /**
     * 播放场次列表
     * @param mId 影院id
     * @param hId
     * @param showState 放映状态
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @param tId 影院id
     * @return
     */
    List<ScreenListVo> screenList(Long mId,Long hId,Integer showState
            ,Date startTime,Date endTime,Long tId);

    /**
     * 生成排片表
     * @param tId 影院id
     * @return
     */
    boolean screenSchedule(Long tId);

    /**
     * 得到排优先度的参数
     * @param movies
     * @return
     */
    List<MovieSort> getSort(List<Movie> movies,Date date,Integer scheduel);

    /**
     * 影片类别优先度值
     * @param ficId
     * @param secId
     * @param thcId
     * @param date
     * @return
     */
    Float getCategoryIndex(Long ficId,Long secId, Long thcId,Date date);
    /**
     * 将类别转化成优先度
     * @param cName
     * @param date
     * @return
     */
    Float turnIndex(String cName,Date date);

    /**
     * 黄金时间段放映表生成
     * @param movieSorts
     * @return
     */
    void createGoldScreen(List<MovieSort> movieSorts, List<Hall> halls,Long tId,Date date);
    /**
     * 非黄金时间段放映表生成
     * @param movieSorts
     * @return
     */
    void createUnGoldScreen(List<MovieSort> movieSorts, List<Hall> halls,Long tId,Date date);

    /**
     * 播放场次检查
     */
    void screenCheckTask();
}
