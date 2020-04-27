package cn.zucc.demo.service;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.vo.*;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface HallService {
    /**
     *添加播放厅

     * @param addHallRequest 添加播放厅请求
     * @return
     */
    boolean addHall( AddHallRequest addHallRequest,Long tId);

    /**
     *删除播放厅
     * @param hId
     * @param tId
     * @return
     */
    boolean deleteHall(Long hId,Long tId);

    /**
     *播放厅列表
     * @param useState 使用状态
     * @param screenCate 屏幕类型
     * @param tId
     * @param startCount 查询座位数目起始
     * @param endCount 查询座位数结束
     * @return
     */
    Page<Hall> findList(Integer pageNum,Integer pageSize,Integer useState, String screenCate, Integer startCount, Integer endCount, Long tId);
    /**
     * 影片下拉列表
     * @param tId
     * @return
     */
    List<HallOptionVo> optionList(Long tId);
    /**
     *播放厅详情
     * @param hId
     * @param tId
     * @return
     */
    HallDetailVo hallDetail(Long hId,Long tId);

    /**
     *编辑播放厅
     * @param hId
     * @param seatVos 座位表
     * @param addHallRequest 添加播放厅请求
     * @param tId
     * @return
     */
    boolean editHall(Long hId,List<SeatVo> seatVos, AddHallRequest addHallRequest,Long tId);

    /**
     * 播放厅时刻表
     * @param hId
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @param tId
     */
    List<HallTimeTableVo> hallTimeTable(Long hId, Date startTime, Date endTime, Long tId);

    /**
     * 获得座位表
     * @param hId
     * @return
     */
    List<SeatVo> getSeat(Long hId,Long tId,Long sId);
}
