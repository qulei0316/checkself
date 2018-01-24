package com.qulei.common.enums;

/**
 * Created by Administrator on 2018/1/24.
 */
public enum RemindTypeEnum {
    DAILY_REMIND(1,"日消费提醒"),
    MONTHLY_REMIND(2,"月消费提醒");

    private Integer code;

    private String type;

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    RemindTypeEnum(Integer code, String type){
        this.code = code;
        this.type = type;
    }
}
