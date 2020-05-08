package cn.zucc.demo.dao;

import cn.zucc.demo.bean.OrderDetail;
import cn.zucc.demo.bean.Orders;
import cn.zucc.demo.vo.OrderDetailListVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail,Long> {

    List<OrderDetail> findBySIdAndDeleteFlagAndOStatusNot(Long sId,Integer deleteFlag,Integer oStatus);
    /**
     *
     * @param mId 影院id
     * @param tId 影院id
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
    @Query(nativeQuery = true,value = "SELECT *" +
            " FROM order_detail WHERE o_id=?1 AND delete_flag=1")
    List<OrderDetail> findList(Long oId);


}
