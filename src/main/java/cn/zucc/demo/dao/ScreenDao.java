package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Screen;
import cn.zucc.demo.vo.ScreenListVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ScreenDao extends JpaRepository<Screen, Long> {

    /**
     * 取得所有未下映的播放场次
     * @param showState
     * @param deleteFlag
     * @return
     */
    List<Screen> findByShowStateNotAndAndDeleteFlag(Integer showState,Integer deleteFlag);
    /**
     * 统计票数
     * @param mId 影院id
     * @param tId 影院id
     * @return
     */
    @Query(nativeQuery = true,value= "select sum(ticket_count) from screen where m_id=?1 and t_id=?2 and delete_flag=1")
    Long countTicket(Long mId,Long tId);


    /**
     * 统计座位数
     * @param mId 影院id
     * @param tId 影院id
     * @return
     */
    @Query(nativeQuery = true,value= "select sum(seat_count) from screen LEFT JOIN hall on hall.h_id=screen.h_id where m_id= ?1 and screen.t_id= ?2 and hall.delete_flag=1 and screen.delete_flag=1")
    Long countSeat(Long mId,Long tId);


    /**
     * 播放场次列表
     * @param mId 影院id
     @param mId 播放厅id
     * @param showState 播放状态
     * @param startTime 查询起始时间
     * @param endTime 查询结束时间
     * @param tId 影院id
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM screen WHERE screen.delete_flag=1 AND (screen.m_id=?1 OR ?1 is null) " +
            "AND (screen.h_id=?2 OR ?2 is null) AND screen.t_id=?3  AND (screen.show_state=?4 OR ?4 is null) And (screen.start_time >?5 OR ?5 is null)  " +
            "And (screen.end_time <?6 OR ?6 is null) ")
    List<Screen> findList(Long mId, Long hId, Long tId, Integer showState, Date startTime, Date endTime);

    /**
     * 查看播放厅的放映时刻表
     * @param showState 上映状态
     * @param hId 播放厅id
     * @param deleteFlag 删除标志
     * @return
     */
    List<Screen> findByShowStateNotAndHIdAndDeleteFlag(Integer showState,Long hId,Integer deleteFlag);

}

