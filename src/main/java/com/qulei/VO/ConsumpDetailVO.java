package com.qulei.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/1/5.
 */
@Data
public class ConsumpDetailVO {

    //关联用户id
    private String user_id;

    //消费金额
    private BigDecimal expense;

    //消费类型
    private String consump_type;

    //详细说明
    private String consump_desc;

    //消费日期 （2017-08-09）
    private String consump_date;

}