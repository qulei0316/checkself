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
@Slf4j
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 用户登录
     * @param sysUserDto
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody SysUserDto sysUserDto,HttpServletRequest request) throws Exception{
        log.info("正在进行用户登录......");
        SysUser sysUser = new SysUser();
        try {
            sysUser = sysUserService.login(sysUserDto);
        }catch (CheckSelfException e){
            e.printStackTrace();
            return ResultVOUtil.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error(1001, StringConstants.SYSTEM_ERROR);
        }
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
        log.info("用户登出中......");
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
        log.info("正在进行用户注册......");
        try {
            sysUserService.addSysUser(sysUserDto);
        }catch (CheckSelfException e){
            e.printStackTrace();
            return ResultVOUtil.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error(1001, StringConstants.SYSTEM_ERROR);
        }
        return ResultVOUtil.success();
    }


    /**
     * 校验用户名唯一性
     * @param username
     * @return
     */
    @PostMapping("/checkunique")
    public ResultVO checkunique(@RequestParam("username")String username){
        log.info("正在校验用户名唯一性......");
        sysUserService.checkunique(username);
        return ResultVOUtil.success();
    }


    /**
     * 激活用户
     * @param code
     * @return
     */
    @PostMapping("/active")
    public ResultVO active(@RequestParam("code") String code){
        log.info("正在激活用户......");
        sysUserService.activeSysUser(code);
        return ResultVOUtil.success();
    }

}
