package com.qulei.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/18.
 */
public class CommonUtil {

    /**
     * 判断字符是否为空
     * @param str
     * @return
     */
    public static boolean isStringEmpty(String str){
        return str == null || "".equals(str);
    }


    /**
     * 将时间转换为时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static Long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        Long ts = date.getTime();
        return ts;
    }

    /**
     * 获取当前日期的时间戳
     */
    public static Long getTodayDate() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        Date today = sdf.parse(str);
        Long ls = today.getTime();
        return ls;
    }


    /**
     * 将时间戳转换为日期
     */
    public static String stampToDate(Long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为日期（精确到时间）
     */
    public static String stampToTime(Long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 获取当天前星期(周日是1)
     */
    public static int getWeekday(){
        Date today = new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(today);
        int weekday=c.get(Calendar.DAY_OF_WEEK);
        return weekday;
    }

    /**
     * 获取上个月第一天的时间戳
     */
    public static Long getlastmonthfirstday() throws ParseException {
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dateToStamp(stampToDate(calendar.getTimeInMillis()));
    }


    /**
     * 获取上个月最后一天的时间戳
     */
    public static Long getlastmonthlastday() throws ParseException {
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateToStamp(stampToDate(calendar.getTimeInMillis()));
    }
}
