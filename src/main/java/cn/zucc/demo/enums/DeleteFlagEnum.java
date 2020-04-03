package cn.zucc.demo.enums;

/**
 * @description: 删除标志
 * @author: hjj
 * @create: 2020-02-25 11:04
 */
public enum DeleteFlagEnum implements BaseEnum<Integer, DeleteFlagEnum>{
    IS_DELETE(2,"已删除"),
    UN_DELETE(1,"未删除");

    Integer value;
    String content;

    DeleteFlagEnum(Integer value, String content){
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
