package com.qulei.VO;

import lombok.Data;

/**
 * Created by Administrator on 2018/1/30.
 */
@Data
public class PlanVO {

    private String plan_id;

    private String plan_name;

    private String user_id;

    private String content;

    private String deadline;

    private String level;

    private String status;

    private String create_time;

    private Integer update_status;

    private Integer update_level;
}
