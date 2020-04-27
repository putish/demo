package cn.zucc.demo.enums;

/**
 * @description: 节假日枚举
 * @author: hjj
 * @create: 2020-04-23 14:26
 */
public enum HolidayEnum implements BaseEnum<Integer, HolidayEnum>{
    SPRING_FESTIVAL(0,"春节"),
    VALENTINE(1,"情人节"),
    QIXI(2,"七夕节"),
    All_SAINTS(3,"万圣节"),
    CHILDREN(4,"儿童节"),
    NEW_YEAR(5,"元旦"),
    NATIONAL(6,"国庆"),
    LABOUR(7,"劳动节"),
    WEEKEND(8,"双休日"),
    WORKDAY(9,"工作日");
    private Integer value;
    private String content;

    HolidayEnum(Integer value, String content){
        this.value=value;
        this.content=content;
    }
    @Override
    public Integer getValue() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }
}
