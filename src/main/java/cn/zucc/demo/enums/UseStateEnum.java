package cn.zucc.demo.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 使用状态
 * @author: hjj
 * @create: 2020-02-25 10:42
 */
public enum UseStateEnum implements BaseEnum<Integer,UseStateEnum>{
    IN_USE(1,"使用中"),
    IN_SPARE(2,"闲置中"),
    OUT_DATE(4,"已过期"),
    WILL_EDIT(5,"待编辑"),
    CAN_EDIT(6,"可编辑"),
    IN_FALUT(3,"故障中");

    private Integer value;
    private String content;

    UseStateEnum(Integer value,String content){
        this.content=content;
        this.value=value;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public static String getContentByValue(Integer value){
        List<UseStateEnum> list= Arrays.asList(UseStateEnum.class.getEnumConstants());
        for (UseStateEnum stateEnum:list){
            if (stateEnum.getValue()==value){
                return stateEnum.content;
            }
        }
        return "";
    }
    public static Integer getValueByContent(String content){
        List<UseStateEnum> list= Arrays.asList(UseStateEnum.class.getEnumConstants());
        for (UseStateEnum stateEnum:list){
            if (stateEnum.getContent()==content){
                return stateEnum.getValue();
            }
        }
        return null;
    }
    public static void main(String[] args){
        System.out.println(UseStateEnum.getContentByValue(1));
    }
}
