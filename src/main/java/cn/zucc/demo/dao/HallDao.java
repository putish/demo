package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Hall;
import cn.zucc.demo.vo.HallTimeTableVo;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface HallDao extends JpaRepository<Hall,Long> {

    /**
     *
     * @param hId
     * @param tId
     * @return
     */
    List<Hall> findByHIdAndTIdAndDeleteFlag(Long hId, Long tId, Integer deleteFlag);

    List<Hall> findByTIdAndDeleteFlag(Long tId,Integer deletFlag);
    /**
     * 播放厅列表
     * @param useState 使用状态
     * @param screenCate 屏幕类型
     * @param tId
     * @param startCount 查询座位数目起始
     * @param endCount 查询座位数结束
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * from hall WHERE (use_state= ?1 OR ?1=null) AND (screen_cate= ?2 OR ?2=null) AND (seat_count= ?3 OR ?3=null) AND (seat_count= ?4 OR ?4=null) AND delete_flag=1 AND t_id=?5")
    List<Hall> findList(Integer useState, String screenCate, Integer startCount, Integer endCount, Long tId);

    /**
     * 播放厅时刻表
     * @param hId
     * @param tId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT screen.h_id AS hId,hall.h_name AS hNAme,movie.m_name " +
            "AS mName,start_time AS startTime,end_time AS endTime" +
            " FROM screen LEFT JOIN hall on hall.h_id=screen.h_id LEFT JOIN movie on movie.m_id=screen.m_id " +
            "WHERE (hall.h_id= ?1 OR ?1=null) AND (screen.t_id=?2 OR ?2=null) AND screen.show_state!=3 AND hall.delete_flag=1 AND screen.delete_flag=1")
    List<HallTimeTableVo> hallTimeTableList(Long hId, Long tId);
    /**
     * 播放厅特定时刻表
     * @param hId
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @param tId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT screen.h_id AS hId,hall.h_name AS hNAme,movie.m_name " +
            "AS mName,start_time AS startTime,end_time AS endTime" +
            " FROM screen LEFT JOIN hall on hall.h_id=screen.h_id LEFT JOIN movie on movie.m_id=screen.m_id " +
            "WHERE (all.h_id= ?1 OR ?1=null) AND (stat_time<?2 OR ?2=null) AND (end_time<?3 OR ?3=null) AND (screen.t_id=4 OR ?4=null) AND hall.delete_flag=1 AND screen.delete_flag=1")
    List<HallTimeTableVo> findhallTimeTable(Long hId, Date startTime,Date endTime,Long tId);
}
