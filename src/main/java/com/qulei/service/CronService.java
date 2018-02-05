package com.qulei.service;

import com.qulei.VO.CronVO;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.enums.RemindMethodEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.dao.CronDao;
import com.qulei.entity.bean.Cron;
import com.qulei.entity.dto.ScheduleDto;
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
    public CronVO getcron(Cron dto, String token){
        CronVO vo = new CronVO();
        //鉴权
        String user_id = dto.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Cron cron = cronDao.getCron(dto);
        if (cron !=null) {
            vo.setCron(cron.getCron_date());
            vo.setUpdate_cron(cron.getCron_date());
            if (cron.getRemind_method()!=null) {
                vo.setUpdate_method(cron.getRemind_method());
                vo.setMethod(cron.getRemind_method());
            }
            vo.setStatus(cron.getStatus());
            vo.setUpdate_status(cron.getStatus());
            if (cron.getStatus() == 1){
                vo.setIsplan_remind(true);
            }else {
                vo.setIsplan_remind(false);
            }
        }
        return vo;
    }


    /**
     * 新增cron
     * @param cron
     * @param token
     */
    @Transactional
    public void addCron(Cron cron, String token) {
        //鉴权
        String user_id = cron.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        int i = cronDao.addCron(cron);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.CRON_EDIT_ERROR);
        }
    }

    /**
     * 删除
     * @param dto
     * @param token
     */
    @Transactional
    public void delete(Cron dto, String token) {
        //鉴权
        String user_id = dto.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        int i = cronDao.deleteCron(dto);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.DELETE_CRON_ERROR);
        }
    }
}
