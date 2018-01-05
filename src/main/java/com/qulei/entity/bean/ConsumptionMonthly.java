package com.qulei.entity.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/12/20.
 */
@Data
public class ConsumptionMonthly {

    //编号
    private String id;

    //用户编号
    private String user_id;

    //消费金额
    private BigDecimal expense;

    //消费月份
    private String consump_month;

    //是否超标
    private int is_over;

    private int yn;
}
