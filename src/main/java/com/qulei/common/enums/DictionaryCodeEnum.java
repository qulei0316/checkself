package com.qulei.common.enums;

/**
 * Created by Administrator on 2018/1/15.
 */
public enum DictionaryCodeEnum {

    CONSUMP_DAILY_STANDARD("1000","每日消费标准"),
    CONSUMP_MONTHLY_STANDARD("1001","每月消费标准");

    private String dic_code;

    private String name;

    public String getDic_code() {
        return dic_code;
    }

    public String getName() {
        return name;
    }

    DictionaryCodeEnum(String dic_code, String name){
        this.dic_code = dic_code;
        this.name = name;
    }
}
