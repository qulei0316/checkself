package com.qulei.controller;

import com.qulei.VO.CronVO;
import com.qulei.VO.ResultVO;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.enums.RemindTypeEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.ResultVOUtil;
import com.qulei.entity.bean.Cron;
import com.qulei.entity.dto.ScheduleDto;
import com.qulei.schedule.RemindJob;
import com.qulei.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/23.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private AuthorizeUtil authorizeUtil;

    @Autowired
    private CronService cronService;

    List<RemindJob> remindJobs = new ArrayList<>();
    Map<String,RemindJob> planJobs = new HashMap();

    //消费日提醒开启任务
    @PostMapping("/startremind")
    @Transactional
    public ResultVO startremind(@RequestBody ScheduleDto scheduleDto, @RequestParam("token")String token){
        //鉴权
        String user_id = scheduleDto.getUser_id();
        String cron = CommonUtil.timestampTocron(scheduleDto.getCron());
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        RemindJob job = new RemindJob();
        //选择调用的方法
        Integer type = scheduleDto.getType();
        Integer method = scheduleDto.getMethod();
        job.setType(type);
        job.setMethod(method);
        job.startJob(cron,user_id);
        remindJobs.add(job);
        //数据库
        Cron dto = new Cron();
        dto.setCron_date(scheduleDto.getCron_date());
        dto.setRemind_method(method);
        dto.setRemind_type(type);
        dto.setStatus(1);
        dto.setUser_id(user_id);
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }


    //修改消费提醒时间
    @PostMapping("/updateremind")
    @Transactional
    public ResultVO updateremind(@RequestBody ScheduleDto scheduleDto, @RequestParam("token")String token) {
        //鉴权
        String user_id = scheduleDto.getUser_id();
        String cronstr = CommonUtil.timestampTocron(scheduleDto.getCron());
        if (!authorizeUtil.verify(user_id, token)) {
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Integer method = scheduleDto.getMethod();
        Integer type = scheduleDto.getType();
        //查找并修改
        for (RemindJob remindJob : remindJobs) {
            if (user_id.equals(remindJob.getUser_id())
                        && type == remindJob.getType()){//选择调用的方法
                remindJob.setMethod(method);
                remindJob.updateJob(cronstr);
                break;
            }
        }
        //数据库
        Cron dto = new Cron();
        dto.setCron_date(scheduleDto.getCron_date());
        dto.setRemind_method(method);
        dto.setRemind_type(type);
        dto.setStatus(1);
        dto.setUser_id(user_id);
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }


    //停止提醒任务
    @PostMapping("/cancelremind")
    @Transactional
    public ResultVO cancelremind(@RequestBody ScheduleDto scheduleDto, @RequestParam("token")String token){
        //鉴权
        String user_id = scheduleDto.getUser_id();
        if (!authorizeUtil.verify(user_id, token)) {
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Integer type = scheduleDto.getType();
        //查找并停止
        for (RemindJob remindJob : remindJobs) {
            if (user_id.equals(remindJob.getUser_id())
                        && type == remindJob.getType()){
                remindJob.stopCron();
                remindJobs.remove(remindJob);
                break;
            }
        }
        //数据库
        Cron dto = new Cron();
        dto.setRemind_type(type);
        dto.setStatus(0);
        dto.setUser_id(user_id);
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }


    /**
     * 增加任务提醒
     */
    @PostMapping("/createplanremind")
    public ResultVO createplanremind(@RequestBody ScheduleDto scheduleDto, @RequestParam("token")String token){
        //鉴权
        String user_id = scheduleDto.getUser_id();
        String cron = CommonUtil.timestampToplancron(scheduleDto.getCron());
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        RemindJob job = new RemindJob();
        //选择调用的方法
        Integer type = RemindTypeEnum.PLAN_REMIND.getCode();
        job.setType(type);
        job.startJob(cron,user_id);
        planJobs.put(scheduleDto.getPlan_id(),job);
        //数据库
        Cron dto = new Cron();
        dto.setCron_date(scheduleDto.getCron_date());
        dto.setRemind_type(type);
        dto.setStatus(1);
        dto.setUser_id(user_id);
        dto.setPlan_id(scheduleDto.getPlan_id());
        cronService.addCron(dto,token);
        return ResultVOUtil.success();
    }



    //修改计划提醒时间
    @PostMapping("/updateplanremind")
    @Transactional
    public ResultVO updateplanremind(@RequestBody ScheduleDto scheduleDto, @RequestParam("token")String token) {
        //鉴权
        String user_id = scheduleDto.getUser_id();
        String cronstr = CommonUtil.timestampToplancron(scheduleDto.getCron());
        if (!authorizeUtil.verify(user_id, token)) {
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        //查找并修改
        for (Map.Entry<String,RemindJob> entry : planJobs.entrySet()){
            if (entry.getKey().equals(scheduleDto.getPlan_id())){
                entry.getValue().updateJob(cronstr);
                break;
            }
        }
        //数据库
        Cron dto = new Cron();
        dto.setCron_date(scheduleDto.getCron_date());
        dto.setPlan_id(scheduleDto.getPlan_id());
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }

    /**
     * 获取定时任务的信息
     */
    @PostMapping("/getscheduleinfo")
    public ResultVO getscheduleinfo(@RequestBody ScheduleDto scheduleDto, @RequestParam("token")String token){
        CronVO vo = cronService.getcron(scheduleDto,token);
        return ResultVOUtil.success(vo);
    }
}
