package com.qulei.common.utils;

import java.util.Random;

/**
 * Created by Administrator on 2017/12/14.
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;

        return System.currentTimeMillis()+String.valueOf(number);

    }

}
