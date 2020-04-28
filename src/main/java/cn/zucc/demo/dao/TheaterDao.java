package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: 影院
 * @author: hjj
 * @create: 2020-02-28 22:30
 */
public interface TheaterDao extends JpaRepository<Theater,Long> {
    /**
     * 根据用户名得到账号
     * @param tName
     * @return
     */
    Theater findByTName(String tName);

    /**
     * 获取所有影院
     * @param deleteFlag
     * @return
     */
    List<Theater> findByDeleteFlag(Integer deleteFlag);
}
