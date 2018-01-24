package com.qulei.controller;

import com.qulei.VO.ResultVO;
import com.qulei.common.enums.DictionaryCodeEnum;
import com.qulei.common.utils.ResultVOUtil;
import com.qulei.dao.DictionaryDao;
import com.qulei.entity.dto.DictionaryDto;
import com.qulei.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/12/22.
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private DictionaryService dictionaryService;

    //修改日消费标准
    @PostMapping("/setdailystandard")
    public ResultVO setdailystandard(@RequestBody DictionaryDto dictionaryDto,@RequestParam("token")String token){
        dictionaryDto.setDictionaryCodeEnum(DictionaryCodeEnum.CONSUMP_DAILY_STANDARD);
        dictionaryService.setValueNumDic(dictionaryDto, token);
        return ResultVOUtil.success();
    }

    //修改月消费标准
    @PostMapping("/setmonthlystandard")
    public ResultVO setmonthlystandard(@RequestBody DictionaryDto dictionaryDto,@RequestParam("token")String token){
        dictionaryDto.setDictionaryCodeEnum(DictionaryCodeEnum.CONSUMP_MONTHLY_STANDARD);
        dictionaryService.setValueNumDic(dictionaryDto, token);
        return ResultVOUtil.success();
    }
}
