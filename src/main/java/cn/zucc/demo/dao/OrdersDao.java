package cn.zucc.demo.dao;

import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.bean.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersDao extends JpaRepository<Orders,Long> {

    List<Orders> findByTIdAndOStatus(Long tId,Integer oStatuse);

    List<Orders> findByTId(Long tId);
}
