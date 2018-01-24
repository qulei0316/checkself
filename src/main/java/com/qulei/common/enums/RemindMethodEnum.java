package com.qulei.common.enums;

/**
 * Created by Administrator on 2018/1/24.
 */
public enum RemindMethodEnum {
    WECHAT(1,"微信"),
    PAGE(2,"页面"),
    EMAIL(3,"邮箱");

    private Integer code;

    private String method;

    public Integer getCode() {
        return code;
    }

    public String getMethod() {
        return method;
    }

    RemindMethodEnum(Integer code, String method){
        this.code = code;
        this.method = method;
    }

    /**
     * 根据code返回desc
     */
    public static String getMethodName(int code){
        String method = null;
        for (RemindMethodEnum methodEnum : values()){
            if (methodEnum.getCode() == code){
                method = methodEnum.getMethod();
                break;
            }
        }
        return method;
    }
}
