package com.qulei.entity.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/24.
 */
@Data
public class Cron {

    private String cron_id;

    private Integer remind_type;

    private Integer remind_method;

    private String plan_id;

    private String user_id;

    private Date cron_date;

    private Integer status;
}

