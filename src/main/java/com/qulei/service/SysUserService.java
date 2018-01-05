package com.qulei.service;

import com.alibaba.fastjson.JSON;
import com.qulei.common.exception.AuthorizeException;
import com.qulei.dao.SysUserDao;
import com.qulei.entity.bean.SysUser;
import com.qulei.entity.dto.SysUserDto;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.*;
import com.qulei.common.utils.constant.CookieConstants;
import com.qulei.common.utils.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/12/18.
 */
@Service
public class SysUserService {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 用户注册
     * @param sysUserDto
     */
    @Transactional
    public void addSysUser(SysUserDto sysUserDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //用户数据判空
        if (CommonUtil.isStringEmpty(sysUserDto.getUsername())){
            throw new CheckSelfException(ExceptionEnum.REGIST_USERNAME_EMPTY_ERROR);
        }
        if (CommonUtil.isStringEmpty(sysUserDto.getPassword())){
            throw new CheckSelfException(ExceptionEnum.REGIST_PASSWORD_EMPTY_ERROR);
        }
        if (CommonUtil.isStringEmpty(sysUserDto.getMail())){
            throw new CheckSelfException(ExceptionEnum.REGIST_MAIL_EMPTY_ERROR);
        }


        //正则化校验
        //   if (RegExpValidatorUtil.isEmail(sysUserDto.getMail())){
        //     throw new CheckSelfException(ExceptionEnum.REGIST_MAILFORMAT_ERROR);
        //}
//        if (RegExpValidatorUtil.isPhoneNum(sysUserDto.getPhonenum())){
//            throw new CheckSelfException(ExceptionEnum.REGIST_PHONENUMFORMAT_ERROR);
//        }


        //md5加密
        String password = sysUserDto.getPassword();
        sysUserDto.setPassword(MD5Util.EncoderByMd5(password));

        sysUserDto.setUser_id(KeyUtil.getUniqueKey());//生成主键

        //生成激活码
        String code = UUIDUtil.createUUID();
        sysUserDto.setCode(code);

        //发送邮件
        String tomail = sysUserDto.getMail();
        mailUtil.sendHtmlMail(tomail,code);

        //添加用户
        int i = sysUserDao.addSysUser(sysUserDto);
        if(i==0){
            throw new CheckSelfException(ExceptionEnum.REGIST_FAIL_ERROR);
        }
    }


    /**
     * 激活用户
     * @param code
     */
    @Transactional
    public void activeSysUser(String code){
        //根据code查询用户
        SysUser sysUser = sysUserDao.getSysUserByCode(code);
        if (sysUser == null){
            throw new CheckSelfException(ExceptionEnum.ACTIVE_NOTFOUND_ERROR);
        }
        if (sysUser.getState() == 1){//若状态为已激活
            throw new CheckSelfException(ExceptionEnum.ACTIVE_HAVEACTIVE_ERROR);
        }

        //将用户状态设置为激活
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setState(1);//设置状态为已激活
        sysUserDto.setUser_id(sysUser.getUser_id());
        int i = sysUserDao.updateSysUser(sysUserDto);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.ACTIVE_FAIL_ERROR);
        }
    }


    /**
     * 用户登录
     * @param sysUserDto
     */
    @Transactional
    public SysUser login(SysUserDto sysUserDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = sysUserDto.getUsername();
        String password = sysUserDto.getPassword();
        SysUser sysUser = new SysUser();

        //判断是用户名还是邮箱还是手机
        String phoneReg = "^\\d{11}$";
        String emailReg = "^\\w+@\\w+\\.\\w+$";
        //去数据库里寻找用户
        if (username.matches(emailReg)){
            sysUser = sysUserDao.getSysUserByMail(username);
        }else {
            sysUser = sysUserDao.getSysUserByUsername(username);
        }
        //判断是否存在用户
        if (sysUser == null) {
            throw new CheckSelfException(ExceptionEnum.LOGIN_FAIL_ERROR);
        }else if (!MD5Util.checkpassword(password,sysUser.getPassword())){
            throw new CheckSelfException(ExceptionEnum.LOGIN_FAIL_ERROR);
        }
        //更新授权
        Date date = new Date();
        String token = UUIDUtil.createUUID();
        sysUserDto.setLast_login_time(date.getTime());
        sysUserDto.setToken_pc(token);
        int i = sysUserDao.updateSysUser(sysUserDto);
        if (i == 0){
            throw new  CheckSelfException(ExceptionEnum.TOKEN_UPDATE_FAIL);
        }

        sysUser.setPassword(null);
        sysUser.setToken_pc(token);
        return sysUser;
    }


    /**
     * 用户登出
     * @param request
     * @param response
     */
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response){
        //从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstants.USERID);
        if (cookie != null){
            //清除redis
            String token = redisTemplate.opsForValue().get(cookie.getValue());
            redisTemplate.opsForValue().getOperations().delete(cookie.getValue());
            redisTemplate.opsForValue().getOperations().delete(token);

            //清除cookie
            CookieUtil.set(response,CookieConstants.USERID,null,0);
        }

        //todo 登出页面
    }


    /**
     * 校验用户名唯一性
     * @param username
     */
    @Transactional
    public void checkunique(String username) {
        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setUsername(username);
        List<SysUser> sysUserList = sysUserDao.getSysUserByParam(sysUserDto);
        if (sysUserList.size()!=0){
            throw new CheckSelfException(ExceptionEnum.REGIST_USERNAME_PEPEAT_ERROR);
        }
    }
}