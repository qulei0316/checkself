package com.qulei.common.enums;

/**
 * Created by Administrator on 2017/12/22.
 */
public enum PlanStateEnum {
    INPROCESS(1,"进行中"),
    FINISHED(2,"完成"),
    RELAY(3,"暂缓"),
    ABANDON(4,"放弃");

    private Integer code;

    private String state;

    public Integer getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    PlanStateEnum(Integer code, String state){
        this.code = code;
        this.state = state;
    }
}
