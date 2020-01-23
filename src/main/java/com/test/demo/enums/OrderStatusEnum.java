package com.test.demo.enums;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 10:24
 **/

public enum OrderStatusEnum implements BaseEnum<Integer,OrderStatusEnum> {
    YU_DINGH(1,"预订状态"),
    TUI_DING(2,"退订"),
    UN_PAY(3,"未支付"),
    FINISH(4,"完成");



    private Integer value;
    private String content;

    OrderStatusEnum(Integer value,String content){
        this.value=value;
        this.content=content;
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
