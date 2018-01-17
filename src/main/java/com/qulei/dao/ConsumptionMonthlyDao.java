package com.qulei.dao;

import com.qulei.entity.bean.ConsumptionMonthly;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/1/16.
 */
public interface ConsumptionMonthlyDao {

    //获取这个月的消费量
    Double getExpenseBymonth(@Param("month") String month,@Param("user_id")String user_id);

    //录入月账单
    int createMonthlyRecord(ConsumptionMonthly consumptionMonthly);
}
