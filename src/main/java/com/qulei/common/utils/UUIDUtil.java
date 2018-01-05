package com.qulei.common.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2017/12/18.
 */
public class UUIDUtil {

    /**
     * 随机生成UUID
     * @return
     */
    public static synchronized String createUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
