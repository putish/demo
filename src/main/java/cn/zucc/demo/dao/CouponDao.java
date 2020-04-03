package cn.zucc.demo.dao;

import cn.zucc.demo.bean.Catergory;
import cn.zucc.demo.bean.Coupon;
import cn.zucc.demo.vo.CouponListVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponDao extends JpaRepository<Coupon, Long> {

    @Query(nativeQuery = true,value = "SELECT co_id AS coId,co_name AS coName, u_name AS uName,conditions AS conditions" +
            " qutoa AS qutoa,start_time AS startTime,end_time AS endTime,use_state AS useState" +
            "FROM coupon LEFT JOIN users on users.u_id=coupon.u_id WHERE (co_name LIKE ?1 OR ?1=null) AND u_id= ?2 OR ?2=null) AND (use_state=?3 OR ?3=null)")
    List<CouponListVo> findList(String coName, Long uId, Integer useState);
}
