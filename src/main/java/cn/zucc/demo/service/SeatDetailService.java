package cn.zucc.demo.service;

import cn.zucc.demo.bean.SeatDetail;
import cn.zucc.demo.vo.SeatAddVo;

import java.util.List;

public interface SeatDetailService {

    /**
     * 添加座位
     * @param seatVo
     @param mId 播放厅id
     * @param tId 影院id
     * @return
     */
    boolean addSeatDetail(SeatAddVo seatVo, Long hId, Long tId);

    /**
     * 删除座位
     * @param sdId
     * @param tId 影院id
     * @return
     */
    boolean deleteSeatDetail(Long sdId,Long tId);

    /**
     * 座位列表
     @param mId 播放厅id
     * @param tId 影院id
     * @return
     */
    List<SeatDetail> findList(Long hId,Long tId);

    /**
     * 编辑座位
     * @param seatDetail
     * @return
     */
    boolean editSeatDetail(SeatDetail seatDetail);

    /**
     * 座位详情
     * @param sdId
     * @param tId 影院id
     * @return
     */
    SeatDetail SeatDetailDetil(Long sdId,Long tId);
}
