package com.qulei.entity.dto;

import lombok.Data;

/**
 * Created by Administrator on 2017/12/21.
 */
@Data
public class ConsumptionDailyDto {

    //用户编号
    private String user_id;

    //是否超标
    private Integer is_over;

    //最小金额
    private Integer min_expense;

    //最大金额
    private Integer max_expense;

    //起始时间
    private Long start_time;

    //结束时间
    private Long end_time;

    //页码
    private Integer page;

    //每页条数
    private Integer pageSize;
}
