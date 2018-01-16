package com.qulei.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/1/15.
 */
public interface DictionaryDao {

    //查询字典内容（数字）
    int getDictionaryNum(@Param("dic_id") String dic_id);
}
