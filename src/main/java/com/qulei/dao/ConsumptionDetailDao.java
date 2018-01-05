package com.qulei.dao;

import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDetailDto;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14.
 */
public interface ConsumptionDetailDao {

    //增加一条消费记录
    int addConsumptionDetail(ConsumptionDetail consumptionDetail);

    //根据条件查询消费记录列表
    List<ConsumptionDetail> getConsumptionDetail(ConsumptionDetailDto consumptionDetailDto);

    //查询记录总数
    Integer getConsumptionDetailListSize(ConsumptionDetailDto detailDto);
}
