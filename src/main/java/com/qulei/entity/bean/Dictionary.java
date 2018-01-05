package com.qulei.entity.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Dictionary {

    //编号
    private String dic_id;

    //名称
    private String name;

    //说明
    private String description;

    //数值
    private BigDecimal value_num;

    //字符值
    private String value_str;

    //创建时间
    private Long create_time;

    //修改时间
    private Long update_time;

    private int yn;
}
