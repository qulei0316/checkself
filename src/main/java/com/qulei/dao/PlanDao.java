package com.qulei.dao;

import com.qulei.entity.bean.Plan;
import com.qulei.entity.dto.PlanDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */
public interface PlanDao {

    //查询计划列表（进行中）
    List<Plan> getPlanlistInProcess(@Param("user_id")String user_id);

    //新增计划
    int addPlan (Plan plan);

    //获取计划详情
    Plan getPlan(Plan plan);

    //修改计划
    int updatePlan(Plan plan);

    //获取计划列表
    List<Plan> getPlanListByPage(PlanDto planDto);

    //获取列表总数
    Integer getTotalSize(PlanDto planDto);

    //删除计划
    int deletePlan(@Param("plan_id") String plan_id);
}
