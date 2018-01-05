package com.qulei.common.enums;

/**
 * Created by Administrator on 2017/12/22.
 */
public enum PlanLevelEnum {
    LOW(1,"低"),
    NORMAL(2,"一般"),
    HIGH(3,"高");

    private Integer code;

    private String level;

    PlanLevelEnum(Integer code,String level){
        this.code = code;
        this.level = level;
    }
}
