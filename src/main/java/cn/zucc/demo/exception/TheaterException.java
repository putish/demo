package cn.zucc.demo.exception;

import cn.zucc.demo.mapping.ResultMapping;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 系统异常
 * @author: hjj
 * @create: 2020-02-26 13:03
 */

@Getter
@Setter
public class TheaterException extends RuntimeException {
    private Integer code;
    private String message;

    public TheaterException(ResultMapping resultMapping){
//        super(resultMapping.getMessage());

        System.out.println("[ERROR]" + resultMapping.getMessage());
        this.code = resultMapping.getCode();
        this.message = resultMapping.getMessage();
    }

    public TheaterException(Integer code, String message){
//        super(message);
        System.out.println("[ERROR]" + message);
        this.code = code;
        this.message = message;
    }
}
