package com.qulei.VO;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/24.
 */
@Data
public class CronVO {

    private Integer method;

    private Date cron;

    private Integer status;
}
