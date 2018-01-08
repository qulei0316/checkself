package com.qulei.service;

import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.enums.PlanStateEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.KeyUtil;
import com.qulei.dao.PlanDao;
import com.qulei.entity.bean.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */
@Service
public class PlanService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PlanDao planDao;

    //查询计划列表(进行中)
    @Transactional
    public List getPlanList(String user_id){
        //获取计划列表
        List<Plan> planList = planDao.getPlanlistInProcess(user_id);
        return planList;
    }


    //新增计划
    @Transactional
    public void addPlan(HttpServletRequest request,Plan plan){

        //数据判断
        if (CommonUtil.isStringEmpty(plan.getPlan_name())){
            throw new CheckSelfException(ExceptionEnum.PLAN_NAME_EMPTY_ERROR);
        }
        if (CommonUtil.isStringEmpty(plan.getContent())){
            throw new CheckSelfException(ExceptionEnum.PLAN_CONTENT_EMPTY_ERROR);
        }
        if (plan.getDeadline() == null){
            throw new CheckSelfException(ExceptionEnum.PLAN_DEADLINE_EMPTY_ERROR);
        }
        if (plan.getLevel() == null){
            throw new CheckSelfException(ExceptionEnum.PLAN_LEVEL_EMPTY_ERROR);
        }

        //设置参数
        plan.setPlan_id(KeyUtil.getUniqueKey());
        plan.setState(PlanStateEnum.INPROCESS.getCode());
      //  plan.setUser_id();

        //插入
        int i = planDao.addPlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_CREATE_ERROR);
        }
    }



    //计划延期
    @Transactional
    public void relayPlan(Plan plan){
        Plan plan1 = planDao.getPlan(plan);

        //参数判别
        if (plan.getDeadline() <= plan1.getDeadline()){
            throw new CheckSelfException(ExceptionEnum.PLAN_DEADLINE_BEFORE_ERROR);
        }

        int i = planDao.updatePlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_UPDATE_ERROR);
        }
    }



    //计划完成
    @Transactional
    public void finishPlan(Plan plan){
        plan.setState(PlanStateEnum.FINISHED.getCode());
        int i = planDao.updatePlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_UPDATE_ERROR);
        }
    }


    //计划暂缓
    @Transactional
    public void pausePlan(Plan plan){
        plan.setState(PlanStateEnum.RELAY.getCode());
        int i = planDao.updatePlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_UPDATE_ERROR);
        }
    }


    //放弃计划
    @Transactional
    public void abandonPlan(Plan plan){
        plan.setState(PlanStateEnum.ABANDON.getCode());
        int i = planDao.updatePlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_UPDATE_ERROR);
        }
    }

}
