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

    public Integer getCode() {
        return code;
    }

    public String getLevel() {
        return level;
    }

    PlanLevelEnum(Integer code, String level){
        this.code = code;
        this.level = level;
    }


    public static String getLevelName(int code){
        String level = null;
        for (PlanLevelEnum planLevelEnum: values()){
            if (planLevelEnum.getCode() == code){
                level = planLevelEnum.getLevel();
                break;
            }
        }
        return level;
    }
}
