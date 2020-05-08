package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Movie;
import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.bean.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrdersDao extends JpaRepository<Orders,Long> {

    /**
     * 根据支付状态查询
     * @param oStatus
     * @return
     */

    List<Orders> findByOStatus(Integer oStatus);
    /**
     * 订单列表
     * @param tId
     * @param uId
     * @param oState
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * from orders WHERE (t_id= ?1 OR ?1 is null) AND (u_id= ?2 OR ?2 is null) AND (o_status= ?3 OR ?3 is null)  " +
            " AND delete_flag=1 And (start_time >?4 OR ?4 is null)  And (end_time <?5 OR ?5 is null) ")
    List<Orders> findList(Long tId, Long uId, Integer oState, Date startTime, Date endTime);
}
