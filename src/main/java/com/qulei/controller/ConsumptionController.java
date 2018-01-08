package com.qulei.controller;

import com.qulei.VO.ConsumpDetailVO;
import com.qulei.VO.ConsumptionDetailListVO;
import com.qulei.VO.ResultVO;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.constant.StringConstants;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDetailDto;
import com.qulei.service.ConsumptionDetailService;
import com.qulei.common.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
@RestController
@RequestMapping("/consump")
public class ConsumptionController {


    @Autowired
    private ConsumptionDetailService detailService;

    /**
     * 用户录入消费记录
     * @param token
     * @param consumptionDetail
     * @return
     */
    @PostMapping("/recordentry")
    public ResultVO recordentry(@RequestBody ConsumptionDetail consumptionDetail,@RequestParam("token") String token){
        detailService.addConsumptionDetail(consumptionDetail,token);
        return ResultVOUtil.success();
    }


    //记录查询(按日期)
    @GetMapping("/getdailyrecord")
    public void getdailyrecord(){

    }


    //记录查询(按记录查询)
    @PostMapping("/getdetailrecord")
    public ResultVO getdetailrecord(@RequestBody ConsumptionDetailDto consumptionDetailDto,@RequestParam ("token") String token){
        ConsumptionDetailListVO vo = new ConsumptionDetailListVO();
        List<ConsumpDetailVO> consumptionDetailList = detailService.getConsumpListByrecord(consumptionDetailDto,token);
        Integer size = detailService.getConsumptionListSize(consumptionDetailDto,token);
        vo.setConsumptionDetailList(consumptionDetailList);
        vo.setTotalSize(size);
        return ResultVOUtil.success(vo);
    }


    //月消费趋势
    @GetMapping("/getmonthlyrecord")
    public void getmonthlyrecord(){

    }


    //上月消费类型占比
    @GetMapping("/getlastmonthproportionoftype")
    public void getlastmonthproportionoftype(){

    }


    //累计消费类型占比
    @GetMapping("/gethistoryproportionoftype")
    public void historyproportionoftype(){

    }


    //本周消费记录
    @GetMapping("/getthisweekrecord")
    public void getthisweekrecord(){

    }

}
