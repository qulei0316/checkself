package com.qulei.service;

import com.qulei.VO.CronVO;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.enums.RemindMethodEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.dao.CronDao;
import com.qulei.entity.bean.Cron;
import com.qulei.entity.dto.ConsumpScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/1/24.
 */
@Service
public class CronService {

    @Autowired
    private CronDao cronDao;

    @Autowired
    private AuthorizeUtil authorizeUtil;

    /**
     * 编辑定时提醒
     */
    @Transactional
    public void setCron(Cron cron,String token){
        //鉴权
        String user_id = cron.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        int i = cronDao.updateCron(cron);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.CRON_EDIT_ERROR);
        }
    }


    /**
     * 获取信息
     * @param dto
     * @param token
     * @return
     */
    @Transactional
    public CronVO getcron(ConsumpScheduleDto dto,String token){
        CronVO vo = new CronVO();
        //鉴权
        String user_id = dto.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Cron cronDto = new Cron();
        cronDto.setUser_id(user_id);
        cronDto.setStatus(1);
        cronDto.setRemind_type(dto.getType());
        Cron cron = cronDao.getCron(cronDto);
        if (cron==null){
            throw new CheckSelfException(ExceptionEnum.CRON_GET_ERROR);
        }
        vo.setCron(cron.getCron_date());
        vo.setMethod_name(RemindMethodEnum.getMethodName(cron.getRemind_method()));
        return vo;
    }
}
