package com.qulei.VO;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */
@Data
public class WeeklyRecordVO {

    private List<Double> lastweektd;

    private List<Double> thisweektd;
}
