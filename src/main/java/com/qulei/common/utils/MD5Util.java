package com.qulei.common.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/12/15.
 */
public class MD5Util {
    /**利用MD5进行加密
     　　* @param str  待加密的字符串
     　　* @return  加密后的字符串
     　　* @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     　　* @throws UnsupportedEncodingException
     　　*/
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }


    /**判断用户密码是否正确
      　* @param newpasswd  用户输入的密码
     　 * @param oldpasswd  数据库中存储的密码－－用户密码的摘要
      　* @return
      　* @throws NoSuchAlgorithmException
      　* @throws UnsupportedEncodingException
      　*/
    public static boolean checkpassword(String newpassword,String oldpassword) throws NoSuchAlgorithmException,UnsupportedEncodingException{
        if (EncoderByMd5(newpassword).equals(oldpassword)){
            return true;
        }else {
            return false;
        }
    }

}
