package com.qulei.service;

import com.qulei.VO.MonthlyTendencyVO;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.dao.ConsumptionMonthlyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */
@Service
public class ConsumptionMonthlyService {

    @Autowired
    private ConsumptionMonthlyDao monthlyDao;

    @Autowired
    private AuthorizeUtil authorizeUtil;

    /**
     * 获取前六个月消费(包括本月)
     * @return
     */
    @Transactional
    public MonthlyTendencyVO getLastSixmonthExpense(String user_id, String token){
        MonthlyTendencyVO vo = new MonthlyTendencyVO();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        //判别月份
        List<Double> amountlist = new ArrayList<>();
        List<Integer> monthList = new ArrayList();
        List<String> lastsixmonth = new ArrayList();
        Calendar now = Calendar.getInstance();
        String lastmonth = null;
        int nowMonth = now.get(Calendar.MONTH);
        if (nowMonth <= 5 ) {
            int count = 0;
            while (nowMonth > 0) {
                monthList.add(nowMonth--);
                count++;
            }
            nowMonth = 12;
            while (count < 6) {
                monthList.add(nowMonth--);
                count++;
            }
            for (int month : monthList) {
                if (month<=12 && month >=10){
                    lastmonth = now.get(Calendar.YEAR-1) +"-"+month;
                    lastsixmonth.add(lastmonth);
                }else if (month>=7 && month<10){
                    lastmonth = now.get(Calendar.YEAR-1) +"-0"+month;
                    lastsixmonth.add(lastmonth);
                }else if (month>0 && month<=6){
                    lastmonth = now.get(Calendar.YEAR) +"-0"+month;
                    lastsixmonth.add(lastmonth);
                }
            }
        }else if (nowMonth>5 && nowMonth<=11){
            int count = 0;
            while (count<6){
                monthList.add(nowMonth--);
                count++;
            }
            for (int month : monthList){
                if (month<=12 && month>=10){
                    lastmonth = now.get(Calendar.YEAR) +"-"+month;
                    lastsixmonth.add(lastmonth);
                }else if (month>=1 && month<=9){
                    lastmonth = now.get(Calendar.YEAR) +"-0"+month;
                    lastsixmonth.add(lastmonth);
                }
            }
        }

        //排序
        Collections.sort(lastsixmonth);
        for (String month : lastsixmonth){
            Double amount = monthlyDao.getExpenseBymonth(month,user_id);
            amountlist.add(amount);
        }

        vo.setExpense_list(amountlist);
        vo.setMonth_list(lastsixmonth);
        return vo;
    }
}
