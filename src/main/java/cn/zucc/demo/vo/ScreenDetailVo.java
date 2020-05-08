package cn.zucc.demo.vo;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjj
 * @create: 2020-05-01 20:58
 */
public class ScreenDetailVo {
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Long sId;
    //    电影id
    private Long mId;
    //    影院id
    private Long hId;
    //    票数
    private Integer ticketCount;
    //    放映时间
    private Date startTime;
    //    散场时间
    private Date endTime;
    //    影院id
    private Long tId;

    //    上映状态
    private Integer showState;
    //    删除标志
    private Integer deleteFlag;
    //    票价
    private BigDecimal price;
    //    屏幕类型
    private String screenCate;
}
