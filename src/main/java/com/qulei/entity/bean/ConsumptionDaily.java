package com.qulei.entity.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/20.
 */
@Data
public class ConsumptionDaily {

    //编号
    private String id;

    //用户id
    private String user_id;

    //消费金额
    private Double expense;

    //消费日期
    private Long consump_date;

    //是否超标
    private int is_over;

    //是否有效
    private int yn;
}
