package cn.zucc.demo.service.impl;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.Schedule;
import cn.zucc.demo.dao.MovieDao;
import cn.zucc.demo.dao.ScheduleDao;
import cn.zucc.demo.enums.DeleteFlagEnum;
import cn.zucc.demo.enums.ShowStateEnum;
import cn.zucc.demo.form.AddScheduleRequest;
import cn.zucc.demo.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-06 21:14
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private MovieDao movieDao;
    @Override
    public List<Schedule> getList(Long mId, Long tId) {
        return scheduleDao.findByMIdAndTIdAndDeleteFlag(mId,tId,DeleteFlagEnum.UN_DELETE.getValue());
    }

    @Override
    public boolean addSchedule(AddScheduleRequest request, Long tId) {
        Schedule schedule=new Schedule();
        BeanUtils.copyProperties(request,schedule);
        schedule.setTId(tId);
        schedule.setDeleteFlag(DeleteFlagEnum.UN_DELETE.getValue());
        scheduleDao.save(schedule);
        return true;
    }

    @Override
    public boolean deleteSchedule(Long scId) {
        Schedule schedule=scheduleDao.findOne(scId);
        schedule.setDeleteFlag(DeleteFlagEnum.IS_DELETE.getValue());
        scheduleDao.save(schedule);
        return true;
    }

    @Override
    public Schedule scheduleDetail(Long scId) {
        return scheduleDao.findOne(scId);
    }

    @Override
    public boolean editSchedule(AddScheduleRequest request, Long tId) {
        Schedule schedule=scheduleDao.findOne(request.getScId());
        BeanUtils.copyProperties(request,schedule);
        schedule.setTId(tId);
        scheduleDao.save(schedule);
        return true;
    }

    @Override
    public void scheduleCheck() {
        List<Movie> movies=movieDao.findList(null, ShowStateEnum.IN_SHOW.getValue(),null);
        for (Movie movie:movies){
            List<Schedule> schedules=scheduleDao.findByMIdAndDeleteFlag(movie.getMId(),DeleteFlagEnum.UN_DELETE.getValue());
            for (Schedule schedule:schedules){
                schedule.setFCount(schedule.getSCount());
                schedule.setSCount(schedule.getTCount());
                schedule.setTCount(0);
                scheduleDao.save(schedule);
            }
        }
    }
}
