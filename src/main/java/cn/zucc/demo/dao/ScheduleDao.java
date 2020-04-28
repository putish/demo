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

    /**
     * 播放厅场次列表
     * @param mId
     * @param tId
     * @param deleteFlag
     * @return
     */
    List<Schedule> findByMIdAndTIdAndDeleteFlag(Long mId,Long tId,Integer deleteFlag);


    /**
     * 获得影片的排片表
     * @param mId 影院id
     * @param deleteFlag 删除标志
     * @return
     */
    List<Schedule> findByMIdAndDeleteFlag(Long mId,Integer deleteFlag);

}
