package cn.zucc.demo.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @description: 影院下拉框
 * @author: hjj
 * @create: 2020-04-27 12:57
 */
@Getter
@Setter
public class TheaterOptionVo {
//    影院id
    private Long tId;
//    影院名称
    private String tName;
//    影片id
    private Long mId;
}
