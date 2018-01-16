package com.qulei.common.enums;

/**
 * Created by Administrator on 2018/1/15.
 */
public enum DictionaryIdEnum {

    CONSUMP_DAILY_STANDARD("1000","每日消费标准");

    private String dic_id;

    private String name;

    public String getDic_id() {
        return dic_id;
    }

    public String getName() {
        return name;
    }

    DictionaryIdEnum(String dic_id, String name){
        this.dic_id = dic_id;
        this.name = name;
    }
}
