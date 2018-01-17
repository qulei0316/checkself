package com.qulei.dao;

import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDetailDto;
import org.apache.ibatis.annotations.Param;

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

    //查询当前记录
    List<ConsumptionDetail> getConsumptionDetailByDay(@Param("consump_date")Long consump_date,@Param("user_id")String user_id);

    //查询消费类型比重
    Double getConumpTypeProportion(@Param("first_day") Long first_day, @Param("last_day")Long last_day,@Param("code") Integer code,@Param("user_id")String user_id);

    //删除记录
    int deleteDetailRecord(ConsumptionDetail consumptionDetail);

    //获取最高记录
    List<ConsumptionDetail> getHighestRecord(@Param("user_id") String user_id,@Param("first_day") Long first_day, @Param("last_day")Long last_day);

    //获取最高占比
    ConsumptionDetail getHighestProportion(@Param("user_id") String user_id,@Param("first_day") Long first_day, @Param("last_day")Long last_day);

    //获取本月已消费金额
    Double getThisMonthConsumption(Long first_day, String user_id);
}
