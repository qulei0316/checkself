package com.qulei.VO;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */
@Data
public class OverStandardVO {

    //超标次数
    private Integer over_num;

    //超标列表
    private List<ConsumpDailyVO> consumpDailyVOList;
}
