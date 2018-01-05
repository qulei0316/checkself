package com.qulei.dao;

import com.qulei.entity.bean.PlanDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
public interface PlanDetailDao {

    //新增子计划
    int addPlanDetail(PlanDetail planDetail);

    //查询子计划
    List<PlanDetail> getPlanDetail(@Param("plan_id")String plan_id);
}
