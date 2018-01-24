package com.qulei.schedule;

import com.qulei.common.enums.*;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.MailUtil;
import com.qulei.dao.*;
import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.bean.ConsumptionMonthly;
import com.qulei.entity.bean.Dictionary;
import com.qulei.entity.bean.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by Administrator on 2018/1/23.
 */
@Component
@Slf4j
public class ConsumpRemindJob {

    private static final Long ONE_DAY_SECOND = 24*60*60*1000L;
    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private ConsumptionDetailDao detailDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private ConsumptionMonthlyDao monthlyDao;

    @Autowired
    private ConsumptionDailyDao dailyDao;

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    private String cron;

    private Integer method;

    private Integer type;

    private String user_id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public Integer getMethod() {
        return method;
    }

    public String getUser_id() {
        return user_id;
    }

    public void updateJob(String cron){
        this.cron = cron;
        stopCron();
        future = this.threadPoolTaskScheduler.schedule(new Runnable() {

            @Override
            public void run() {

            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                if ("".equals(cron) || cron == null)
                    return null;
                CronTrigger trigger = new CronTrigger(cron);// 定时任务触发，可修改定时任务的执行周期
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
    }

    public void startJob(String cron, String user_id) {
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        this.cron = cron;
        this.user_id = user_id;
        stopCron();
        future = threadPoolTaskScheduler.schedule(new Runnable() {

            @Override
            public void run() {

            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                if ("".equals(cron) || cron == null)
                    return null;
                CronTrigger trigger = new CronTrigger(cron);// 定时任务触发，可修改定时任务的执行周期
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
    }


    public void stopCron() {
        if (future != null) {
            future.cancel(true);//取消任务调度
        }
    }

    //方法选择器
    public void domethod(Integer method,Integer type,String user_id) throws Exception {
        if (type == RemindTypeEnum.DAILY_REMIND.getCode()){
            if (method == RemindMethodEnum.EMAIL.getCode()){
                dailyRemindByMail(user_id);
            }else if (method == RemindMethodEnum.PAGE.getCode()){

            }
        }
    }

    //发送邮件
    public void dailyRemindByMail(String user_id) throws Exception {
        //查询用户邮箱
        SysUser sysUser = sysUserDao.getSysUserByUserid(user_id);
        if (sysUser == null){
            throw new CheckSelfException(ExceptionEnum.ACTIVE_NOTFOUND_ERROR);
        }
        //主题
        String subject = "日消费提醒";
        Long yesterday = CommonUtil.getTodayDate() - ONE_DAY_SECOND;
        //查询昨日消费情况
        ConsumptionDaily daily = dailyDao.getRecordByDay(yesterday);
        String isover = null;
        if (daily.getIs_over()==1){
            isover = "超标";
        }else {
            isover = "未超标";
        }
        //查询预算
        Long first_day = CommonUtil.getThismonthFirstDay();
        Double consumption = detailDao.getThisMonthConsumption(first_day,user_id);
        Double standard = dictionaryDao.getDictionaryNum(DictionaryCodeEnum.CONSUMP_MONTHLY_STANDARD.getDic_code(),user_id);
        Double surplus = standard - consumption;
        //内容
        String text = "昨日总消费:"+daily.getExpense()+",是否超标:"+isover+",本月已消费:"+consumption+",剩余预算:"+surplus;
        mailUtil.sendSimpleMail(sysUser.getMail(),subject,text);
    }


    public void monthlyRemindByMail(String user_id) throws Exception {
        //查询用户邮箱
        SysUser sysUser = sysUserDao.getSysUserByUserid(user_id);
        if (sysUser == null){
            throw new CheckSelfException(ExceptionEnum.ACTIVE_NOTFOUND_ERROR);
        }
        //查询上个月消费
        String month = CommonUtil.getlastmonth();
        ConsumptionMonthly monthly = monthlyDao.getMonthlyRecord(month);
        String subject = "月消费提醒";
        Double standard = dictionaryDao.getDictionaryNum(DictionaryCodeEnum.CONSUMP_MONTHLY_STANDARD.getDic_code(),user_id);
        Double over;
        if (monthly.getIs_over() == 1){
            over = monthly.getExpense() - standard;
        }else {
            over = 0d;
        }
        //内容
        String text = "上个月度总消费:"+monthly.getExpense()+",超标金额:"+String.valueOf(over);
        mailUtil.sendSimpleMail(sysUser.getMail(),subject,text);
    }
}
