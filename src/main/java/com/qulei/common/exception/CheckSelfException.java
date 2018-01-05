package com.qulei.common.exception;

import com.qulei.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * Created by Administrator on 2017/12/14.
 */
@Data
public class CheckSelfException extends RuntimeException {

    //错误码
    private int code;

    public CheckSelfException(int code,String message){
        super(message);
        this.code = code;
    }

    public CheckSelfException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }
}
