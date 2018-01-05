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
@Slf4j
public class ConsumptionController {


    @Autowired
    private ConsumptionDetailService detailService;

    /**
     * 用户录入消费记录
     * @param request
     * @param consumptionDetail
     * @return
     */
    @PostMapping("/recordentry")
    public ResultVO recordentry(HttpServletRequest request, ConsumptionDetail consumptionDetail){
        log.info("正在录入消费记录......");
        detailService.addConsumptionDetail(consumptionDetail);
        return ResultVOUtil.success();
    }


    //记录查询(按日期)
    @GetMapping("/getdailyrecord")
    public void getdailyrecord(){

    }


    //记录查询(按记录查询)
    @PostMapping("/getdetailrecord")
    public ResultVO getdetailrecord(@RequestBody ConsumptionDetailDto consumptionDetailDto,@RequestParam ("token") String token){
        log.info("正在生成列表......");
        ConsumptionDetailListVO vo = new ConsumptionDetailListVO();
        try {
            List<ConsumpDetailVO> consumptionDetailList = detailService.getConsumpListByrecord(consumptionDetailDto,token);
            Integer size = detailService.getConsumptionListSize(consumptionDetailDto,token);
            vo.setConsumptionDetailList(consumptionDetailList);
            vo.setTotalSize(size);
        }catch (CheckSelfException e){
            e.printStackTrace();
            return ResultVOUtil.error(e.getCode(),e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResultVOUtil.error(1001, StringConstants.SYSTEM_ERROR);
        }
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
