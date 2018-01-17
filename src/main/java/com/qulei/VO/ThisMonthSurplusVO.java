package com.qulei.VO;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/17.
 */
@Data
public class ThisMonthSurplusVO {

    //已消费
    private Double consumption;

    //剩余预算
    private Double surplus;
}
