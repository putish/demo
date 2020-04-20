package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Schedule;
import cn.zucc.demo.form.AddScheduleRequest;
import cn.zucc.demo.service.ScheduleService;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-06 21:14
 */
public class ScheduleServiceImpl implements ScheduleService {
    @Override
    public boolean addSchedule(AddScheduleRequest request, Long tId) {
        return false;
    }

    @Override
    public boolean deleteSchedule(Long scId) {
        return false;
    }

    @Override
    public Schedule scheduleDetail(Long scId) {
        return null;
    }

    @Override
    public boolean editSchedule(AddScheduleRequest request, Long tId) {
        return false;
    }
}
