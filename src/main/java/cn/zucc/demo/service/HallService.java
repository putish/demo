package cn.zucc.demo.service;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.form.AddHallRequest;
import cn.zucc.demo.form.EditHallRequest;
import cn.zucc.demo.vo.*;
import org.springframework.data.domain.Page;

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
     @param mId 播放厅id
     * @param tId 影院id
     * @return
     */
    boolean deleteHall(Long hId,Long tId);

    /**
     *播放厅列表
     * @param useState 使用状态
     * @param screenCate 屏幕类型
     * @param tId 影院id
     * @param startCount 查询座位数目起始
     * @param endCount 查询座位数结束
     * @return
     */
    List<HallListVo> findList(Integer useState, String screenCate, Integer startCount, Integer endCount, Long tId);
    /**
     * 播放厅下拉列表
     * @param tId 影院id
     * @return
     */
    List<HallOptionVo> optionList(Long tId);
    /**
     *播放厅详情
     @param mId 播放厅id
     * @param tId 影院id
     * @return
     */
    HallDetailVo hallDetail(Long hId,Long tId);

    /**
     *编辑播放厅
     * @param request 编辑播放厅请求
     * @param tId 影院id
     * @return
     */
    boolean editHall(EditHallRequest request, Long tId);

     /**
     * 获得座位表
     @param mId 播放厅id
     * @return
     */
    List<BookSeatVo> getSeat( Long tId, Long sId);

    /**
     * 定期检查
     */
    void HallCheackTask();

    /**
     * 播放厅时刻表
     @param mId 播放厅id
     * @return
     */
    List<HallTimeTableVo> getHallSreenList(Long hId, Long tId);

    void willEdit(Long hId);
}
