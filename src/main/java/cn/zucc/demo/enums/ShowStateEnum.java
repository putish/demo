package cn.zucc.demo.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @description: 上映状态
 * @author: hjj
 * @create: 2020-02-25 10:53
 */
public enum ShowStateEnum implements BaseEnum<Integer,ShowStateEnum>{
    NO_SHOW(1,"即将上映"),
    WILL_SHOW(1,"预售"),
    IN_SHOW(2,"上映"),
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

    public static String getContentByValue(Integer value){
        List<ShowStateEnum> list= Arrays.asList(ShowStateEnum.class.getEnumConstants());
        for (ShowStateEnum stateEnum:list){
            if (stateEnum.getValue()==value){
                return stateEnum.getContent();
            }
        }
        return "";
    }
    public static void main(String[] args){
        System.out.println(ShowStateEnum.getContentByValue(1));
    }

}
