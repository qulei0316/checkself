package com.qulei.service;

import com.qulei.VO.PlanVO;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.enums.PlanLevelEnum;
import com.qulei.common.enums.PlanStateEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.UUIDUtil;
import com.qulei.dao.PlanDao;
import com.qulei.entity.bean.Plan;
import com.qulei.entity.dto.PlanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */
@Service
public class PlanService {

    private static final Long ONE_DAY_SECOND = 3600*24*1000L;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private AuthorizeUtil authorizeUtil;

    //查询计划列表(进行中)
    @Transactional
    public List<PlanVO> getPlanList(PlanDto planDto, String token){
        List<PlanVO> planVOList = new ArrayList<>();
        String user_id = planDto.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //参数设置
        Long start = planDto.getStart_time();
        if (start!=null){
            planDto.setEnd_time(start+ONE_DAY_SECOND);
        }
        //设置分页
        planDto.setStartIndex((planDto.getPageIndex()-1)*10);

        //获取计划列表
        List<Plan> planList = planDao.getPlanListByPage(planDto);
        if (planList!=null){
            for (Plan plan : planList) {
                PlanVO vo = new PlanVO();
                vo.setPlan_id(plan.getPlan_id());
                if (plan.getContent()!=null) {
                    vo.setContent(plan.getContent());
                }
                vo.setPlan_name(plan.getPlan_name());
                vo.setUser_id(plan.getUser_id());
                vo.setCreate_time(CommonUtil.stampToTime(plan.getCreate_time()));
                if(plan.getDeadline()!=null) {
                    vo.setDeadline(CommonUtil.stampToTime(plan.getDeadline()));
                }
                vo.setLevel(PlanLevelEnum.getLevelName(plan.getLevel()));
                vo.setStatus(PlanStateEnum.getStatusName(plan.getStatus()));
                vo.setUpdate_level(plan.getLevel());
                vo.setUpdate_status(plan.getStatus());
                planVOList.add(vo);
            }
        }
        return planVOList;
    }


    //新增计划
    @Transactional
    public void addPlan(Plan plan,String token){
        String user_id = plan.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //数据判断
        if (CommonUtil.isStringEmpty(plan.getPlan_name())){
            throw new CheckSelfException(ExceptionEnum.PLAN_NAME_EMPTY_ERROR);
        }
//        if (CommonUtil.isStringEmpty(plan.getContent())){
//            throw new CheckSelfException(ExceptionEnum.PLAN_CONTENT_EMPTY_ERROR);
//        }
//        if (plan.getDeadline() == null){
//            throw new CheckSelfException(ExceptionEnum.PLAN_DEADLINE_EMPTY_ERROR);
//        }
//        if (plan.getLevel() == null){
//            throw new CheckSelfException(ExceptionEnum.PLAN_LEVEL_EMPTY_ERROR);
//        }

        //设置参数
        plan.setPlan_id(UUIDUtil.createUUID());
        plan.setStatus(PlanStateEnum.INPROCESS.getCode());
        plan.setCreate_time(System.currentTimeMillis());

        //插入
        int i = planDao.addPlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_CREATE_ERROR);
        }
    }



    /**
     * 获取记录总数
     * @param planDto
     * @param token
     * @return
     */
    @Transactional
    public Integer getTotalSize(PlanDto planDto,String token) {
        String user_id = planDto.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        //参数设置
        Long start = planDto.getStart_time();
        if (start!=null){
            planDto.setEnd_time(start+ONE_DAY_SECOND);
        }

        Integer size = planDao.getTotalSize(planDto);
        return size;
    }

    /**
     * 修改计划
     * @param plan
     * @param token
     */
    public void editPlan(Plan plan, String token) {
        String user_id = plan.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        int i = planDao.updatePlan(plan);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_UPDATE_ERROR);
        }
    }

    /**
     * 删除记录
     * @param plan_id
     * @param token
     */
    public void deletePlan(String plan_id,String user_id, String token) {
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        int i = planDao.deletePlan(plan_id);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.PLAN_DELETE_ERROR);
        }
    }
}
