package com.qulei.dao;

import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.dto.ConsumptionDailyDto;

import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */
public interface ConsumptionDailyDao {

    //查询记录列表
    List<ConsumptionDaily> getConsumptionDailyList(ConsumptionDailyDto dailyDto);
}
