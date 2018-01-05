package com.qulei.aop;

import com.alibaba.fastjson.JSON;
import com.qulei.common.utils.CommonUtil;
import com.qulei.entity.bean.SysUser;
import com.qulei.common.exception.AuthorizeException;
import com.qulei.common.utils.CookieUtil;
import com.qulei.common.utils.constant.CookieConstants;
import com.qulei.common.utils.constant.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/12/14.
 */
@Aspect
@Component
@Slf4j
public class AuthorizeAspect {

//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    @Pointcut("execution(public * com.qulei.controller.*.*(..))"+
//    "&& !execution(public * com.qulei.controller.UserController.*(..))")
//    public void verify(){}
//
//    @Before("verify()")
//    public void doverify(){
//        //获取request
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        //查询cookie
//        Cookie cookie = CookieUtil.get(request, CookieConstants.USERID);
//        if (cookie == null){
//            throw new AuthorizeException();
//        }
//
//        //去redis查询
//        String token = redisTemplate.opsForValue().get(cookie.getValue());
//        if (token == null){
//            throw new AuthorizeException();
//        }
//        SysUser sysUser = JSON.parseObject(redisTemplate.opsForValue().get(token),SysUser.class);
//        if (sysUser == null){
//            throw new AuthorizeException();
//        }
//    }
}
