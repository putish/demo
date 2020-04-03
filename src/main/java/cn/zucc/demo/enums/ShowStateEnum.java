package cn.zucc.demo.enums;

/**
 * @description: 上映状态
 * @author: hjj
 * @create: 2020-02-25 10:53
 */
public enum ShowStateEnum implements BaseEnum<Integer,ShowStateEnum>{
    IN_SHOW(1,"即将上映"),
    WILL_SHOW(2,"上映"),
    SOLD_OUT(3,"下架");

    Integer value;
    String content;

    ShowStateEnum(Integer value,String content){
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
