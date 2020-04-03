package cn.zucc.demo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: hjj
 * @create: 2020-03-06 21:24
 */
@Getter

public enum CommonMapping {

    SUCCESS(0, "成功"), FAILED(1, "失败");

    private Integer code;

    private String message;

    CommonMapping(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
