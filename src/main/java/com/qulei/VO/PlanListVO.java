package com.qulei.VO;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */
@Data
public class PlanListVO {

    private Integer totalSize;

    private List<PlanVO> planVOList;
}

