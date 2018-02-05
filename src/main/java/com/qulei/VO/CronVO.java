package com.qulei.VO;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/24.
 */
@Data
public class CronVO {

    private Boolean isplan_remind;

    private Integer method;

    private Date cron;

    private Integer status;

    private Integer update_status;

    private Integer update_method;

    private Date update_cron;
}
