package com.qulei.schedule;

import com.qulei.common.enums.DictionaryCodeEnum;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.UUIDUtil;
import com.qulei.dao.*;
import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.bean.ConsumptionMonthly;
import com.qulei.entity.bean.SysUser;
import com.qulei.entity.dto.SysUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */
@Component
public class ConsumpJobs {

    @Autowired
    private SysUserDao userDao;

    @Autowired
    private ConsumptionDetailDao detailDao;

    @Autowired
    private ConsumptionDailyDao dailyDao;

    @Autowired
    private ConsumptionMonthlyDao monthlyDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    /**
     * 生成日消费记录
     */
    @Scheduled(cron = "0 50 23 * * ?")
    public void createDailyRecord() throws ParseException {
        //获取当天时间戳
        Long today = CommonUtil.getTodayDate();
        //查询所有用户
        SysUserDto sysUserDto = new SysUserDto();
        List<SysUser> sysUserList = userDao.getSysUserByParam(sysUserDto);

        //遍历所有用户，并计算数据
        for (SysUser sysUser : sysUserList) {
            ConsumptionDaily daily = new ConsumptionDaily();
            daily.setId(UUIDUtil.createUUID());
            daily.setUser_id(sysUser.getUser_id());
            List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetailByDay(today,sysUser.getUser_id());
            Double daily_total = new Double(0);
            for (ConsumptionDetail consumptionDetail : consumptionDetailList) {
                daily_total += consumptionDetail.getExpense();
            }
            daily.setConsump_date(today);
            daily.setExpense(daily_total);
            //查询消费标准
            Double standard = dictionaryDao.getDictionaryNum(DictionaryCodeEnum.CONSUMP_DAILY_STANDARD.getDic_code(),sysUser.getUser_id());
            Integer is_over = 0;
            if (daily_total > standard){
                is_over = 1;
            }
            daily.setIs_over(is_over);
            int i = dailyDao.addConsumpDailyRecord(daily);
            if (i==0){
                throw new CheckSelfException(ExceptionEnum.DAILY_RECORD_CREATE_ERROR);
            }
        }
    }



    /**
     * 每月生成消费记录
     */
    @Scheduled(cron = "0 0 1 1 * ?")
    public void createMonthlyRecord() throws ParseException {
        //获取上个月的第一天和最后一天的时间戳
        Long first_day = CommonUtil.getlastmonthfirstday();
        Long last_day = CommonUtil.getlastmonthlastday();

        //查询所有用户
        SysUserDto sysUserDto = new SysUserDto();
        List<SysUser> sysUserList = userDao.getSysUserByParam(sysUserDto);

        for (SysUser sysUser : sysUserList) {
            ConsumptionMonthly monthly = new ConsumptionMonthly();
            monthly.setId(UUIDUtil.createUUID());
            monthly.setUser_id(sysUser.getUser_id());
            monthly.setConsump_month(CommonUtil.getlastmonth());
            Double expense = dailyDao.getThisMonthExpense(first_day,last_day,sysUser.getUser_id());
            monthly.setExpense(expense);
            int i = monthlyDao.createMonthlyRecord(monthly);
            if (i==0){
                throw new CheckSelfException(ExceptionEnum.MONTHLY_RECORD_CREATE_ERROR);
            }
        }
    }



}
