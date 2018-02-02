package com.qulei.controller;

import com.qulei.VO.PlanListVO;
import com.qulei.VO.PlanVO;
import com.qulei.VO.ResultVO;
import com.qulei.common.utils.ResultVOUtil;
import com.qulei.entity.bean.Plan;
import com.qulei.entity.dto.PlanDto;
import com.qulei.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    //获取列表
    @PostMapping("/getplanlist")
    public ResultVO getPlanlist(@RequestBody PlanDto planDto , @RequestParam("token")String token){
        PlanListVO vo = new PlanListVO();
        Integer totalSize = planService.getTotalSize(planDto, token);
        List<PlanVO> planVOList = planService.getPlanList(planDto, token);
        if (planVOList!=null) {
            vo.setPlanVOList(planVOList);
        }
        if (totalSize!=null) {
            vo.setTotalSize(totalSize);
        }
        return ResultVOUtil.success(vo);
    }

    //计划录入
    @PostMapping("/addplan")
    public ResultVO addplan(@RequestBody Plan plan,@RequestParam("token")String token){
        planService.addPlan(plan,token);
        return ResultVOUtil.success();
    }

    //修改计划
    @PostMapping("/editplan")
    public ResultVO editplan(@RequestBody Plan plan,@RequestParam("token")String token){
        planService.editPlan(plan,token);
        return ResultVOUtil.success();
    }

    //删除记录
    @GetMapping("/deleteplan")
    public ResultVO deleteplan(@RequestParam("plan_id")String plan_id,@RequestParam("user_id")String user_id,@RequestParam("token")String token){
        planService.deletePlan(plan_id,user_id,token);
        return ResultVOUtil.success();
    }

}
