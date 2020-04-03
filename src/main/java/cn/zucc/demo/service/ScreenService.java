package cn.zucc.demo.service;

import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.vo.ScreenListVo;

import java.util.Date;
import java.util.List;

public interface ScreenService {
    /**
     *添加放映场次
     * @param mId
     * @param hId
     * @param startTime 放映时间
     * @param tId
     * @return
     */
    boolean addScreen(Long mId, Long hId, Date startTime, Long tId);

    /**
     * 删除放映场次
     * @param sId
     * @param tId
     * @return
     */
    boolean deleteScreen(Long sId,Long tId);
    /**
     *编辑放映场次
     * @param sId
     * @param mId
     * @param hId
     * @param startTime 放映时间
     * @param tId
     * @return
     */
    boolean editScreen(Long sId,Long mId, Long hId, Date startTime, Long tId);

    /**
     * 放映场次详情
     * @param sId
     * @param tId
     * @return
     */
    ScreenListVo screenDetail(Long sId, Long tId);

    /**
     * 播放场次列表
     * @param mId
     * @param hId
     * @param showState 放映状态
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @param tId
     * @return
     */
    List<ScreenListVo> screenList(Long mId,Long hId,Integer showState
            ,Date startTime,Date endTime,Long tId);



}
