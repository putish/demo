package cn.zucc.demo.mapping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 返回错误原因
 * @author: hjj
 * @create: 2020-02-26 13:04
 */
@Getter
public enum  ResultMapping {
    FALUT_SHOWTIME(1,"错误上映时间"),
    IN_USE(3,"在使用中不可删除更改"),
    NO_HALL(4,"没有该演播厅"),
    NO_MOVIE(5,"没有该电影"),
    NO_SCREEN(5,"没有放映场次"),
    HAVE_ORDER(6,"已经存在订单"),
    HAVE_SCREEN(7,"已经有播放场次"),
    NO_SEAT(8,"座位已经被预定"),
    NO_USER(9,"没有该用户"),
    PHONE_FALUT(10,"手机号格式错误"),
    FAULT_PWD(11,"密码错误"),
    CIN_USE(13,"类别使用中"),
    OUT_OF_RANGE(2,"超出范围"),
    NO_CATERGORY(14,"类别不存在"),
    CHANGE_TO_WILL_EDIT(15,"转为待编辑"),
    FALUT_ENDTIME(16,"下架时间在三日内"),
    REPEAT_USERNAME(12,"用户名重复"),
    REPEAT_CATERGORY(12,"类别名称重复");
    private Integer code;

    private String message;

    ResultMapping(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultMapping(Integer code) {
        this.code = code;
    }
}
