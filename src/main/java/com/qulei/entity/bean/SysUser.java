package com.qulei.entity.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/18.
 */
@Data
public class SysUser {

    //用户id
    private String user_id;

    //用户名
    private String username;

    //密码
    private String password;

    //昵称
    private String nickname;

    //手机号
    private String phonenum;

    //邮箱
    private String mail;

    //头像地址
    private String icon;

    //创建时间
    private Date create_time;

    //激活码
    private Integer code;

    //激活状态：0：未激活，1：激活
    private Integer state;

    //上次登录时间
    private Date last_login_time;

    //pc端登录口令
    private String token_pc;

    //移动端登录口令
    private String token_mobile;

    private int yn;

}
