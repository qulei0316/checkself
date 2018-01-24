package com.qulei.controller;

import com.qulei.VO.CronVO;
import com.qulei.VO.ResultVO;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.ResultVOUtil;
import com.qulei.common.utils.UUIDUtil;
import com.qulei.entity.bean.Cron;
import com.qulei.entity.dto.ConsumpScheduleDto;
import com.qulei.schedule.ConsumpRemindJob;
import com.qulei.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    List<ConsumpRemindJob> consumpRemindJobs = new ArrayList<>();

    //消费日提醒开启任务
    @PostMapping("/startconsumpremind")
    @Transactional
    public ResultVO startconsumpremind(@RequestBody ConsumpScheduleDto consumpScheduleDto, @RequestParam("token")String token){
        //鉴权
        String user_id = consumpScheduleDto.getUser_id();
        String cron = consumpScheduleDto.getCron();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        ConsumpRemindJob job = new ConsumpRemindJob();
        //选择调用的方法
        Integer type = consumpScheduleDto.getType();
        Integer method = consumpScheduleDto.getMethod();
        job.setType(type);
        job.setMethod(method);
        job.startJob(cron,user_id);
        consumpRemindJobs.add(job);
        //数据库
        Cron dto = new Cron();
        dto.setCron_date(consumpScheduleDto.getCron_date());
        dto.setRemind_method(method);
        dto.setRemind_type(type);
        dto.setStatus(1);
        dto.setUser_id(user_id);
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }


    //修改消费提醒时间
    @PostMapping("/updateconsumpremind")
    @Transactional
    public ResultVO updateconsumpremind(@RequestBody ConsumpScheduleDto consumpScheduleDto, @RequestParam("token")String token) {
        //鉴权
        String user_id = consumpScheduleDto.getUser_id();
        String cronstr = consumpScheduleDto.getCron();
        if (!authorizeUtil.verify(user_id, token)) {
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Integer method = consumpScheduleDto.getMethod();
        Integer type = consumpScheduleDto.getType();
        //查找并修改
        for (ConsumpRemindJob consumpRemindJob : consumpRemindJobs) {
            if (user_id.equals(consumpRemindJob.getUser_id())
                    && method == consumpRemindJob.getMethod()
                        && type == consumpRemindJob.getType()){//选择调用的方法
                consumpRemindJob.updateJob(cronstr);
                break;
            }
        }
        //数据库
        Cron dto = new Cron();
        dto.setCron_date(consumpScheduleDto.getCron_date());
        dto.setRemind_method(method);
        dto.setRemind_type(type);
        dto.setStatus(1);
        dto.setUser_id(user_id);
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }


    //停止提醒任务
    @PostMapping("/cancelconsumpremind")
    @Transactional
    public ResultVO cancelconsumpremind(@RequestBody ConsumpScheduleDto consumpScheduleDto,@RequestParam("token")String token){
        //鉴权
        String user_id = consumpScheduleDto.getUser_id();
        if (!authorizeUtil.verify(user_id, token)) {
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Integer method = consumpScheduleDto.getMethod();
        Integer type = consumpScheduleDto.getType();
        //查找并停止
        for (ConsumpRemindJob consumpRemindJob : consumpRemindJobs) {
            if (user_id.equals(consumpRemindJob.getUser_id())
                    && method == consumpRemindJob.getMethod()
                        && type == consumpRemindJob.getType()){
                consumpRemindJob.stopCron();
                consumpRemindJobs.remove(consumpRemindJob);
                break;
            }
        }
        //数据库
        Cron dto = new Cron();
        dto.setRemind_method(method);
        dto.setRemind_type(type);
        dto.setStatus(0);
        dto.setUser_id(user_id);
        cronService.setCron(dto,token);
        return ResultVOUtil.success();
    }


    /**
     * 获取定时任务的信息
     */
    @PostMapping("/getscheduleinfo")
    public ResultVO getscheduleinfo(@RequestBody ConsumpScheduleDto consumpScheduleDto,@RequestParam("token")String token){
        CronVO vo = cronService.getcron(consumpScheduleDto,token);
        return ResultVOUtil.success(vo);
    }
}
