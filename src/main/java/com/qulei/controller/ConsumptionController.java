package com.qulei.controller;

import com.qulei.VO.*;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDailyDto;
import com.qulei.entity.dto.ConsumptionDetailDto;
import com.qulei.service.ConsumptionDailyService;
import com.qulei.service.ConsumptionDetailService;
import com.qulei.common.utils.ResultVOUtil;
import com.qulei.service.ConsumptionMonthlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
@RestController
@RequestMapping("/consump")
public class ConsumptionController {

    @Autowired
    private ConsumptionDailyService dailyService;

    @Autowired
    private ConsumptionDetailService detailService;

    @Autowired
    private ConsumptionMonthlyService monthlyService;

    /**
     * 用户录入消费记录
     * @param token
     * @param consumptionDetail
     * @return
     */
    @PostMapping("/recordentry")
    public ResultVO recordentry(@RequestBody ConsumptionDetail consumptionDetail,@RequestParam("token") String token) throws ParseException {
        detailService.addConsumptionDetail(consumptionDetail,token);
        return ResultVOUtil.success();
    }


    //删除记录
    @PostMapping("/deletedetail")
    public ResultVO deletedetail(@RequestBody ConsumptionDetail consumptionDetail,@RequestParam("token")String token){
        detailService.deleteDetailRecord(consumptionDetail,token);
        return ResultVOUtil.success();
    }

    //记录查询(按日期)
    @GetMapping("/getdailyrecord")
    public ResultVO getdailyrecord(@RequestBody ConsumptionDailyDto dailyDto,@RequestParam("token") String token){
        ConsumptionDailyListVO vo = new ConsumptionDailyListVO();
        List<ConsumpDailyVO> consumpDailyVOList = dailyService.getConsumpListBydaily(dailyDto, token);
        Integer size = dailyService.getConsumpListSizeBydaily(dailyDto, token);
        vo.setConsumpDailyVOList(consumpDailyVOList);
        vo.setTotalSize(size);
        return ResultVOUtil.success(vo);
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

    //生成昨日的消费记录
    @GetMapping("/getlastdayrecord")
    public ResultVO getLstDayRecord(@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        List<ConsumpDetailVO> consumptionDetailList = detailService.getLastDayConsumptionRecord(user_id,token);
        return ResultVOUtil.success(consumptionDetailList);
    }


    //上周消费列表
    @GetMapping("/getlastmonthlyrecord")
    public ResultVO getlastmonthlyrecord(@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        List<Double> last_week = dailyService.getLastweekTendency(user_id,token);
        return ResultVOUtil.success(last_week);
    }

    //本周消费列表
    @GetMapping("/getthismonthlyrecord")
    public ResultVO getthismonthlyrecord(@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        List<Double> this_week = dailyService.getThisweekTendency(user_id,token);
        return ResultVOUtil.success(this_week);
    }

    //上个月消费占比
    @GetMapping("/getlastmonthproportion")
    public ResultVO getlastmonthproportion(@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        List<LastweekConsumpTypeProportionVO> list = detailService.getMonthTypeProportion(user_id,token);
        return ResultVOUtil.success(list);
    }

    //月账单
    @GetMapping("/getmonthlytendency")
    public ResultVO getmonthlytendency(@RequestParam("user_id")String user_id,@RequestParam("token")String token){
        MonthlyTendencyVO vo = monthlyService.getLastSixmonthExpense(user_id, token);
        return ResultVOUtil.success(vo);
    }

    //查询上个月超标情况
    @GetMapping("/getoverstandard")
    public ResultVO getoverstandard (@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        OverStandardVO vo = dailyService.getOverStandardrecord(user_id,token);
        return ResultVOUtil.success(vo);
    }

    //获取单笔最高金额
    @GetMapping("/gethighestrecord")
    public ResultVO gethighestrecord (@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        List<ConsumpDetailVO> list = detailService.getHighestRecord(user_id,token);
        return ResultVOUtil.success(list);
    }


    //获取消费比重最高
    @GetMapping("/gethighestproportion")
    public ResultVO gethighestproportion(@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        ConsumpDetailVO vo = detailService.getHighestProportion(user_id, token);
        return ResultVOUtil.success(vo);
    }

    //获取本月剩余预算
    @GetMapping("/getthismonthsurplus")
    public ResultVO getthismonthsurplus(@RequestParam("user_id")String user_id,@RequestParam("token")String token) throws ParseException {
        ThisMonthSurplusVO vo = detailService.getThisMonthConsumption(user_id,token);
        return ResultVOUtil.success(vo);
    }
}
