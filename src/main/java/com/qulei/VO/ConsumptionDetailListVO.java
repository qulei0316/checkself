package com.qulei.VO;

import com.qulei.entity.bean.ConsumptionDetail;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
@Data
public class ConsumptionDetailListVO {

    //记录总数
    private Integer totalSize;

    //列表信息
    private List<ConsumpDetailVO> consumptionDetailList;
}
