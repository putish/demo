package cn.zucc.demo.util;

import cn.zucc.demo.bean.Theater;
import cn.zucc.demo.data.RootData;
import cn.zucc.demo.enums.CommonMapping;
import cn.zucc.demo.exception.TheaterException;

/**
 * @description: 返回结果工具类
 * @author: hjj
 * @create: 2020-03-06 21:20
 */
public class ResultUtil {
    public static RootData success(Object data){
        RootData rootData = new RootData();
        rootData.setCode(CommonMapping.SUCCESS.getCode());
        rootData.setMessage(CommonMapping.SUCCESS.getMessage());
        rootData.setData(data);
        return rootData;
    }

    public static RootData error(TheaterException exception) {
        RootData rootData = new RootData();
        rootData.setCode(exception.getCode());
        rootData.setMessage(exception.getMessage());
        return rootData;
    }
}
