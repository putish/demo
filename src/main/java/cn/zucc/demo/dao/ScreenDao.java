package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.vo.ScreenListVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ScreenDao extends JpaRepository<Screen, Long> {


    /**
     * 统计票数
     * @param mId
     * @param tId
     * @return
     */
    @Query(nativeQuery = true,value= "select sum(ticket_count) from screen where m_id=?1 and t_id=?2 and delete_flag=1")
    Long countTicket(Long mId,Long tId);


    /**
     * 统计座位数
     * @param mId
     * @param tId
     * @return
     */
    @Query(nativeQuery = true,value= "select sum(seat_count) from screen LEFT JOIN hall on hall.h_id=screen.h_id where m_id= ?1 and screen.t_id= ?2 and hall.delete_flag=1 and screen.delete_flag=1")
    Long countSeat(Long mId,Long tId);

    /**
     *寻找时间段空闲播放厅
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT h_id, FROM hall where h_id in(" +
            "SELECT COUNT(s_id) AS screenCount,h_id  FROM screen WHERE (start_time>?1 OR ?1=null) AND (end_time<?2 OR ?2=null) GROUP BY h_id) " +
            "AND screenCount=0 AND hall.delete_flag=1 AND screen.delete_flag=1 AND (screen.t_id=?3 OR ?3=null)")
    List<Long> findSpareHall(Date startTime, Date endTime ,Long tId);

    /**
     * 播放场次列表
     * @param mId
     * @param hId
     * @param showState 播放状态
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @param tId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM screen WHERE screen.delete_flag=1 AND (screen.m_id=?1 OR ?1 is null) AND (screen.h_id=?2 OR ?2 is null) AND screen.t_id=?3  AND (screen.show_state=?4 OR ?4 is null) And (screen.start_time >?5 OR ?5 is null)  And (screen.end_time <?6 OR ?6 is null) ")
    List<Screen> findList(Long mId, Long hId, Long tId, Integer showState, Date startTime, Date endTime);

}

