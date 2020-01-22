package com.test.demo.data;


import lombok.Data;

@Data
public class RootDate<T> {

    // 自定义状态码
    private Integer code;

    // 状态码的详细解释
    private String message;

    // 具体数据
    private T data;
}