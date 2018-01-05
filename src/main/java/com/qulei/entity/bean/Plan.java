package com.qulei.entity.bean;

import lombok.Data;

@Data
public class Plan {

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
    private Integer state;

    //创建时间
    private Long create_time;

    private int yn;
}
