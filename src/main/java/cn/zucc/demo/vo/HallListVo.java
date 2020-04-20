package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 播放厅列表
 * @author: hjj
 * @create: 2020-04-04 20:51
 */
@Getter
@Setter
public class HallListVo {
    private Long hId;
    //    影厅名称
    private String hName;
    //    行数
    private Integer rows;
    //    列数
    private Integer cols;
    //    座位数量
    private Integer seatCount;
    //    屏幕类型
    private String screenCate;
    //    影院id
    private Long tId;
    //    使用状态
    private String useState;
    //    删除标志
    private Integer deleteFlag;
}
