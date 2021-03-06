package cn.zucc.demo.dao;

import cn.zucc.demo.bean.SeatDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatDetailDao extends JpaRepository<SeatDetail,Long> {

    List<SeatDetail> findByHIdAndDeleteFlag(Long hId,Integer deleteFlag);
}
