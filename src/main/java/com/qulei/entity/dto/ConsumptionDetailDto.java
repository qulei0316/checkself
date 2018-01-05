package com.qulei.entity.dto;

import lombok.Data;

/**
 * Created by Administrator on 2017/12/21.
 */
@Data
public class ConsumptionDetailDto {

    //用户编号
    private String user_id;

    //消费类型
    private int consump_type;

    //最小金额
    private Integer min_expense;

    //最大金额
    private Integer max_expense;

    //初始时间
    private Long start_time;

    //结束时间
    private Long end_time;

    //页码
    private Integer pageIndex;

    //每页个数
    private Integer pageSize;

    //起始数
    private Integer startIndex;
}
