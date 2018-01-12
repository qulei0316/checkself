package com.qulei.entity.dto;

import lombok.Data;

/**
 * Created by Administrator on 2017/12/21.
 */
@Data
public class ConsumptionDailyDto {

    //编号
    private String id;

    //用户编号
    private String user_id;

    //是否超标
    private Integer is_over;

    //金额范围
    private Integer expense_range;

    //起始时间
    private Long start_time;

    //结束时间
    private Long end_time;

    //页码
    private Integer page;

    //每页条数
    private Integer pageSize;

    //起始位置
    private Integer startIndex;
}
