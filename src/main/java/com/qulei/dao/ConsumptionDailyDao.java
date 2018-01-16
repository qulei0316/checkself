package com.qulei.dao;

import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.dto.ConsumptionDailyDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */
public interface ConsumptionDailyDao {

    //查询记录列表
    List<ConsumptionDaily> getConsumptionDailyList(ConsumptionDailyDto dailyDto);

    //增加日消费记录
    Integer addConsumpDailyRecord(ConsumptionDaily daily);

    //查询记录总数
    Integer getConsumpDailyTotalSize(ConsumptionDailyDto dailyDto);

    //查询上周消费金额
    List<Double> getLastweekTendency(@Param("last_sunday") Long last_sunday,@Param("user_id")String user_id);

    //查询本周数据
    List<Double> getThisweekTendency(@Param("this_monday") Long this_monday,@Param("user_id")String user_id);
}
