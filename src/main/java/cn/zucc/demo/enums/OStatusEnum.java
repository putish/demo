package cn.zucc.demo.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author hjj
 * @Description TODO
 * @create: 2020-01-23 10:24
 **/

public enum OStatusEnum implements BaseEnum<Integer, OStatusEnum> {
    YU_DINGH(1,"预订"),
    TUI_DING(2,"退订"),
    FINISH(3,"支付完成");



    private Integer value;
    private String content;

    OStatusEnum(Integer value, String content){
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

    public static String getContentByValue(Integer value){
        List<OStatusEnum> list= Arrays.asList(OStatusEnum.class.getEnumConstants());
        for (OStatusEnum stateEnum:list){
            if (stateEnum.getValue()==value){
                return stateEnum.getContent();
            }
        }
        return "";
    }
}
