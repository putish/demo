package cn.zucc.demo.service;

import cn.zucc.demo.bean.Schedule;
import cn.zucc.demo.form.AddScheduleRequest;

import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-06 21:14
 */
public interface ScheduleService {

    /**
     * 排片表列表
     * @param mId 影院id
     * @param tId 影院id
     * @return
     */
    List<Schedule> getList(Long mId,Long tId);

    /**
     * 新增排片表
     * @param request
     * @param tId 影院id
     * @return
     */
    boolean addSchedule(AddScheduleRequest request,Long tId);

    /**
     * 删除排片表
     * @param scId
     * @return
     */
    boolean deleteSchedule(Long scId);

    /**
     * 排片表详情
     * @param scId
     * @return
     */
    Schedule scheduleDetail(Long scId);

    /**
     * 编辑排片表
     * @param request
     * @param tId 影院id
     * @return
     */
    boolean editSchedule(AddScheduleRequest request,Long tId);

    /**
     *
     */
    void scheduleCheck();

}
