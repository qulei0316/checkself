package com.qulei.entity.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/30.
 */
@Data
public class PlanDto {

    //编号
    private String plan_id;

    //名称
    private String plan_name;

    //用户编号
    private String user_id;

    //计划内容
    private String content;

    //计划期限
    private Long deadline;

    //计划优先级
    private Integer level;

    //计划状态
    private Integer status;

    //创建时间
    private Long create_time;

    //页码
    private Integer pageIndex;

    //每页个数
    private Integer pageSize;

    //起始数
    private Integer startIndex;

    //开始时间
    private Long start_time;

    //结束时间
    private Long end_time;
}
