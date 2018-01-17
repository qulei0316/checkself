package com.qulei.entity.dto;

import com.qulei.common.enums.DictionaryIdEnum;
import lombok.Data;

/**
 * Created by Administrator on 2018/1/17.
 */
@Data
public class DictionaryDto {

    //编号
    private String dic_id;

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

    private DictionaryIdEnum dictionaryIdEnum;
}
