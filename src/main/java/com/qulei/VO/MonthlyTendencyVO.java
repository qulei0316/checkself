package com.qulei.VO;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */
@Data
public class MonthlyTendencyVO {

    private List<Double> expense_list;

    private List<String> month_list;
}
