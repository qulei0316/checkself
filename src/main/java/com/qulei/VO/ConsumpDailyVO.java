package com.qulei.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ConsumpDailyVO {

    //编号
    private String id;

    //消费金额
    private Double expense;

    //是否超标
    private String is_over;

    //消费日期 （2017-08-09）
    private String consump_date;

    //消费详情
    private List<ConsumpDetailVO> consumpDetailVOList;
}
