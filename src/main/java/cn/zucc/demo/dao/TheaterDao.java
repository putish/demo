package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: 影院
 * @author: hjj
 * @create: 2020-02-28 22:30
 */
public interface TheaterDao extends JpaRepository<Theater,Long> {
    Theater findByTName(String tName);
}
