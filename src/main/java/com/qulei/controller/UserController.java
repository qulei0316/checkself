package com.qulei.controller;

import com.qulei.VO.ResultVO;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.constant.StringConstants;
import com.qulei.entity.bean.SysUser;
import com.qulei.entity.dto.SysUserDto;
import com.qulei.service.SysUserService;
import com.qulei.common.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/12/15.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 用户登录
     * @param sysUserDto
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody SysUserDto sysUserDto) throws Exception{
        SysUser sysUser = new SysUser();
        sysUser = sysUserService.login(sysUserDto);
        return ResultVOUtil.success(sysUser);
    }


    /**
     * 用户登出
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request ,HttpServletResponse response){
        sysUserService.logout(request,response);
        return ResultVOUtil.success();
    }


    /**
     * 用户注册
     * @param sysUserDto
     * @return
     */
    @PostMapping("/regist")
    public ResultVO regist(@RequestBody SysUserDto sysUserDto) throws Exception{
        sysUserService.addSysUser(sysUserDto);
        return ResultVOUtil.success();
    }


    /**
     * 校验用户名唯一性
     * @param username
     * @return
     */
    @PostMapping("/checkunique")
    public ResultVO checkunique(@RequestParam("username")String username){
        sysUserService.checkunique(username);
        return ResultVOUtil.success();
    }


//    /**
//     * 发送验证码
//     * @param code
//     * @return
//     */
//    @PostMapping("/active")
//    public ResultVO active(@RequestParam("code") String code){
//        sysUserService.activeSysUser(code);
//        return ResultVOUtil.success();
//    }

}
