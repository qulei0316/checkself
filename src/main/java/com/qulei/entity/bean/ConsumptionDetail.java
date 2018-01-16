package com.qulei.entity.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */
@Data
public class ConsumptionDetail {

    //编号
    private String consump_id;

    //关联用户id
    private String user_id;

    //消费金额
    private Double expense;

    //消费类型
    private Integer consump_type;

    //详细说明
    private String consump_desc;

    //消费日期 （2017-08-09）
    private Long consump_date;

    //创建时间
    private Date create_time;

    //是否有效
    private int yn;
}
