package cn.zucc.demo.enums;

/**
 * @description: 使用状态
 * @author: hjj
 * @create: 2020-02-25 10:42
 */
public enum UseStateEnum implements BaseEnum<Integer,UseStateEnum>{
    IN_USE(1,"使用中"),
    IN_SPARE(2,"闲置中"),
    OUT_DATE(4,"已过期"),
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
}
