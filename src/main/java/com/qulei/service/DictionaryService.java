package com.qulei.service;

import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.dao.DictionaryDao;
import com.qulei.entity.dto.DictionaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018/1/17.
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private AuthorizeUtil authorizeUtil;


    /**
     * 设置数字(浮点型)字典
     * @param dictionaryDto
     * @param token
     */
    @Transactional
    public void setValueNumDic(DictionaryDto dictionaryDto, String token){
        //鉴权
        String user_id = dictionaryDto.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //判空
        if (dictionaryDto.getValue_num() == null){
            throw new CheckSelfException(ExceptionEnum.NO_VALUE_NUM_ERROR);
        }

        dictionaryDto.setDic_id(dictionaryDto.getDictionaryCodeEnum().getDic_code());
        int i = dictionaryDao.setNumvalueDic(dictionaryDto);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.DICTIONARY_SET_ERROR);
        }
    }
}
