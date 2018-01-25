package com.qulei.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/23.
 */
@Data
public class ScheduleDto {
    private Integer method;

    private Integer type;

    private String user_id;

    private String cron;

    private Date cron_date;
}
