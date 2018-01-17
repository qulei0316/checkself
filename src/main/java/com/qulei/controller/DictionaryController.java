package com.qulei.controller;

import com.qulei.VO.ResultVO;
import com.qulei.common.utils.ResultVOUtil;
import com.qulei.dao.DictionaryDao;
import com.qulei.entity.dto.DictionaryDto;
import com.qulei.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/1/17.
 */
@RestController
@RequestMapping("/dic")
public class DictionaryController {


    @Autowired
    private DictionaryService dictionaryService;

    //设置数字字典
    @PostMapping("/setvaluenumdic")
    public ResultVO setvaluenumdic(DictionaryDto dictionaryDto, @RequestParam("token")String token){
        dictionaryService.setValueNumDic(dictionaryDto, token);
        return ResultVOUtil.success();
    }



}
