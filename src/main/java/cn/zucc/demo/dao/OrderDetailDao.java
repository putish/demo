package cn.zucc.demo.dao;

import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.vo.OrderDetailListVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail,Long> {

    List<OrderDetail> findBySIdAndDeleteFlag(Long sId,Integer deleteFlag);
    /**
     *
     * @param mId
     * @param tId
     * @return
     */
    @Query(nativeQuery = true,value= "select sum (odId) from order_detail left jion screen on screen.s_id=order_detail.s_id where screen.m_id=?1 and screen_t_id=?2")
    Long countOrder(Long mId,Long tId);

    /**
     * 详情列表
     * @param oId
     * @return
     */
    List<OrderDetail> findByOId(Long oId);


    /**
     * 订单详情列表
     * @param oId
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT orders.o_id AS oId,m_name AS mName,screen.start_time AS startTime,screen.end_time AS endTime,movie.price AS price,h_name AS hName" +
            " FROM order_detail LEFT JOIN screen on screen.s_id=order_detail.s_id LEFT JOIN hall on hall.h_id= screen.h_id" +
            " LEFT JOIN movie on movie.m_id=screen.m_id LEFT JOIN seat_detail on seat_detail.sd_id=order_detail.sd_id  WHERE o_id=?1")
    List<OrderDetailListVo> findList(Long oId);


}
