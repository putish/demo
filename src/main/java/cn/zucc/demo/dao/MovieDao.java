package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MovieDao extends JpaRepository<Movie, Long> {
    /**
     *
     * @param showState 上映状态
     * @param tId 影院id 影院id
     * @return
     */
    List<Movie> findByShowStateAndTId(Integer showState,Long tId);

    @Query(nativeQuery = true,value = "SELECT * from movie "+
            "WHERE (show_state= ?2 OR ?2 is null) AND (m_name like ?3 OR ?3 is null)  AND t_id=?1 AND delete_flag=1")
    List<Movie> findList(Long tId, Integer showState,String mName);
    /**
     *
     * @param tId 影院id 影院id
     * @return
     */
    List<Movie> findByTIdAndDeleteFlag(Long tId,Integer deleteFlag);

    /**
     * 获取所有为下架的影片
     * @param showState
     * @param deleteFlag
     * @return
     */
    List<Movie> findByShowStateNotAndDeleteFlag(Integer showState,Integer deleteFlag);

}
