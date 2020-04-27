package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-06 21:13
 */
public interface ScheduleDao extends JpaRepository<Schedule, Long> {

    List<Schedule> findByMIdAndTIdAndDeleteFlag(Long mId,Long tId,Integer deleteFlag);

    List<Schedule> findByTIdAndDeleteFlag(Long tId,Integer deleteFlag);

    Schedule findByMIdAndDeleteFlagAndScreenCate(Long mId,Integer deleteFlag,String screenCate);

}
