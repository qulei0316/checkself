package com.qulei.entity.bean;

import lombok.Data;

@Data
public class PlanDetail {

    //编号
    private String id;

    //关联计划编号
    private String plan_id;

    //计划排序
    private Integer plan_sort;

    //计划内容
    private String content;

    //计划期限
    private long deadline;

    //状态
    private Integer state;

    //创建时间
    private long create_time;

    private int yn;
}
