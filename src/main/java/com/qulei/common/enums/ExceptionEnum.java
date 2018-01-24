package com.qulei.common.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2017/12/14.
 */
@Getter
public enum ExceptionEnum {

    //数据库操作异常



    //用户模块异常
    REGIST_USERNAME_EMPTY_ERROR(1001,"请填写用户姓名"),
    REGIST_PASSWORD_EMPTY_ERROR(1002,"请设置用户密码"),
    REGIST_MAIL_EMPTY_ERROR(1003,"请填写用户邮箱"),
    REGIST_PHONENUM_EMPTY_ERROR(1004,"请填写手机号码"),
    REGIST_USERNAME_PEPEAT_ERROR(1005,"用户名重复，请重新设置"),
    REGIST_FAIL_ERROR(1006,"抱歉，注册失败"),
    REGIST_MAILFORMAT_ERROR(1,"邮箱格式错误"),
    REGIST_PHONENUMFORMAT_ERROR(1,"手机号格式错误"),
    ACTIVE_NOTFOUND_ERROR(1007,"未找到用户信息"),
    ACTIVE_HAVEACTIVE_ERROR(1008,"用户已激活，请登录"),
    ACTIVE_FAIL_ERROR(1009,"用户激活失败"),
    LOGIN_FAIL_ERROR(1010,"登陆失败，请重新输入"),
    LOGIN_NOTACTIVE_ERROR(1,"您未激活您的邮箱,请激活"),
    TOKEN_UPDATE_FAIL(1,"授权失败" ),
    AUTHORIZE_FAIL(1,"鉴权失败，请重新登录"),


    //消费模块异常
    CONSUMP_DATE_EMPTY_ERROR(1,"请填写消费日期"),
    CONSUMP_TYPE_EMPTY_ERROR(1,"请选择消费类型"),
    CONSUMP_EXPENSE_EMPTY_ERROR(1,"请填写消费金额"),
    CONSUMP_RECORD_ADD_ERROR(1,"增添记录失败"),
    CONSUMP_EXPENSE_RANGE_ERROR(1,"请输入正确的金额区间"),
    CONSUMP_DATE_RANGE_ERROR(1,"请输入正确的时间区间"),
    DAILY_RECORD_CREATE_ERROR(1,"每日记录生成失败" ),
    MONTHLY_RECORD_CREATE_ERROR(1,"月账单生成成失败" ),
    DELETE_DETAIL_ERROR(0,"记录删除失败" ),

    //计划模块异常
    PLAN_NAME_EMPTY_ERROR(1,"请填写计划名称"),
    PLAN_CONTENT_EMPTY_ERROR(1,"请填写计划内容"),
    PLAN_DEADLINE_EMPTY_ERROR(1,"请填写计划期限"),
    PLAN_LEVEL_EMPTY_ERROR(1,"请选择计划等级"),
    PLAN_CREATE_ERROR(1,"创建任务失败"),
    PLAN_UPDATE_ERROR(1,"修改任务失败"),
    PLAN_DEADLINE_BEFORE_ERROR(1,"请设置正确的截止日期"),


    //定时模块
    CRON_EDIT_ERROR(1,"定时数据更新失败"),
    CRON_GET_ERROR(1,"定时数据查询失败" ),
    CRON_INSERT_ERROR(11,"定时信息初始化失败" ),

    //字典模块
    DICTIONARY_SET_ERROR(1,"设置字典值失败" ),
    NO_VALUE_NUM_ERROR(1,"请输入数值" ),
    DICTIONARY_CREATE_ERROR(1,"新增字典失败" );

    //错误码
    private Integer code;

    //错误或明
    private String message;

    ExceptionEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
