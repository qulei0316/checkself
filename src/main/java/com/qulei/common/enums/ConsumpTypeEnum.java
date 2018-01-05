package com.qulei.common.enums;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/5.
 */
public enum ConsumpTypeEnum {
    FOOD(1,"餐饮"),
    TRANSPORT(2,"交通"),
    MEDICAL(3,"医药"),
    OTHER(99,"其他");

    private Integer code;

    private String type;

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    ConsumpTypeEnum(Integer code, String type){
        this.code = code;
        this.type = type;
    }

    /**
     * 根据code返回desc
     */
    public static String getTypeName(int code){
        String type = null;
        for (ConsumpTypeEnum consumpTypeEnum : values()){
            if (consumpTypeEnum.getCode() == code){
                type = consumpTypeEnum.getType();
                break;
            }
        }
        return type;
    }
}
