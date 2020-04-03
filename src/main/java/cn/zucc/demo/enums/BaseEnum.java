package cn.zucc.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 10:21
 **/

public interface BaseEnum<T, E extends Enum<E>> {
    @JsonValue
    T getValue();

    String getContent();
}
