package com.qulei.schedule;

import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.UUIDUtil;
import com.qulei.dao.ConsumptionDetailDao;
import com.qulei.dao.SysUserDao;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.bean.SysUser;
import com.qulei.entity.dto.ConsumptionDailyDto;
import com.qulei.entity.dto.SysUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
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

    private final static long ONE_SECOND = 1000;

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
        for (SysUser sysUser : sysUserList) {
            ConsumptionDailyDto dailyDto = new ConsumptionDailyDto();
            dailyDto.setId(UUIDUtil.createUUID());
            dailyDto.setUser_id(sysUser.getUser_id());
            List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetailByDay(today);
            BigDecimal daily_expense = consumptionDetailList.stream().;
        }

        //遍历所有用户，并计算数据
    }


    /**
     * 每周生成消费记录
     */
    @Scheduled(cron = "0 0 0 ? * MON")
    public void createWeeklyRecord(){
    }


    /**
     * 每月生成消费记录
     */
    @Scheduled(cron = "0 0 24 L * ?")
    public void createMonthlyRecord(){

    }
}
