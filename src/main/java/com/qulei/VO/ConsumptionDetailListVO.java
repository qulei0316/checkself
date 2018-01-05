package com.qulei.VO;

import com.qulei.entity.bean.ConsumptionDetail;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */
@Data
public class ConsumptionDetailListVO {

    private Integer totalSize;

    private List<ConsumpDetailVO> consumptionDetailList;
}
