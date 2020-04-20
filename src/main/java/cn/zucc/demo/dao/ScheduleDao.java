package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @author: hjj
 * @create: 2020-04-06 21:13
 */
public interface ScheduleDao extends JpaRepository<Schedule, Long> {

}
