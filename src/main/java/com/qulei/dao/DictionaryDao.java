package com.qulei.dao;

import com.qulei.entity.bean.Dictionary;
import com.qulei.entity.dto.DictionaryDto;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/1/15.
 */
public interface DictionaryDao {

    //查询字典内容（数字）
    Double getDictionaryNum(@Param("dic_id") String dic_id);

    //设置数字字典
    int setNumvalueDic(DictionaryDto dictionary);
}
