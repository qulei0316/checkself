package com.qulei.VO;

import lombok.Data;

import java.util.List;

@Data
public class ConsumptionDailyListVO {

    //记录数量
    private Integer totalSize;

    //列表信息
    private List<ConsumpDailyVO> consumpDailyVOList;
}
