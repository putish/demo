package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MovieDao extends JpaRepository<Movie, Long> {
    /**
     *
     * @param showState 上映状态
     * @param tId 影院id
     * @return
     */
    List<Movie> findByShowStateAndTId(Integer showState,Long tId);

    /**
     *
     * @param tId 影院id
     * @return
     */
    List<Movie> findByTId(Long tId);


}
