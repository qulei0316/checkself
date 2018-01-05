package com.qulei.dao;


import com.qulei.entity.bean.SysUser;
import com.qulei.entity.dto.SysUserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */
public interface SysUserDao {

    //增加一个用户
    int addSysUser (SysUserDto dto);

    //修改用户的信息
    int updateSysUser(SysUserDto dto);

    //根据条件查询用户列表
    List<SysUser> getSysUserByParam(SysUserDto dto);

    //根据code查询用户
    SysUser getSysUserByCode(@Param("code")String code);

    //根据用户名查询用户
    SysUser getSysUserByUsername(@Param("username")String username);

    //根据mail查询用户
    SysUser getSysUserByMail(@Param("mail")String mail);

    //根据id查询用户
    SysUser getSysUserByUserid(@Param("user_id") String user_id);
}
