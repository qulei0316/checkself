package com.qulei.common.utils;

import com.qulei.dao.SysUserDao;
import com.qulei.entity.bean.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/1/5.
 */
@Component
public class AuthorizeUtil {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 验证用户id和token是否匹配
     * @param user_id
     * @param token
     * @return
     */
    public boolean verify(String user_id,String token){
        SysUser sysUser = sysUserDao.getSysUserByUserid(user_id);
        if (token.equals(sysUser.getToken_pc()) || token.equals(sysUser.getToken_mobile())){
            return true;
        }
        return false;
    }
}
