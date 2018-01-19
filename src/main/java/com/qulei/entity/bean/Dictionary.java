package com.qulei.entity.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Dictionary {

    //编号
    private String dic_id;

    //字典编号
    private String dic_code;

    //用户编号
    private String user_id;

    //名称
    private String name;

    //说明
    private String description;

    //数值
    private Double value_num;

    //字符值
    private String value_str;

    //修改时间
    private Long update_time;

}
