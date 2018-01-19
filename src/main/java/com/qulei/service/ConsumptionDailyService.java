package com.qulei.service;

import com.qulei.VO.ConsumpDailyVO;
import com.qulei.VO.ConsumpDetailVO;
import com.qulei.VO.OverStandardVO;
import com.qulei.common.enums.ConsumpTypeEnum;
import com.qulei.common.enums.DictionaryCodeEnum;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.constant.StringConstants;
import com.qulei.dao.ConsumptionDailyDao;
import com.qulei.dao.ConsumptionDetailDao;
import com.qulei.dao.DictionaryDao;
import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDailyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */
@Service
public class ConsumptionDailyService {

    @Autowired
    private ConsumptionDailyDao dailyDao;

    @Autowired
    private ConsumptionDetailDao detailDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private AuthorizeUtil authorizeUtil;

    private final static long ONE_DAY_SECOND = 24*60*60*1000L;

    /**
     * 按天查询消费列表
     * @param dailyDto
     * @return
     */
    @Transactional
    public List<ConsumpDailyVO> getConsumpListBydaily(ConsumptionDailyDto dailyDto, String token){
        String user_id = dailyDto.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //判断输入条件
        if (dailyDto.getStart_time()!=null && dailyDto.getEnd_time()!=null && dailyDto.getEnd_time()<dailyDto.getStart_time()){
            throw new CheckSelfException(ExceptionEnum.CONSUMP_DATE_RANGE_ERROR);
        }
        //设置分页
        dailyDto.setStartIndex((dailyDto.getPageIndex()-1)*10);

        List<ConsumpDailyVO> consumpDailyVOS= new ArrayList<>();

        //查询列表
        List<ConsumptionDaily> consumptionDailyList = dailyDao.getConsumptionDailyList(dailyDto);
        for (ConsumptionDaily i : consumptionDailyList) {
            List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetailByDay(i.getConsump_date(),user_id);
            List<ConsumpDetailVO> consumpDetailVOS = new ArrayList<>();
            for (ConsumptionDetail j : consumptionDetailList) {
                ConsumpDetailVO detailVO = new ConsumpDetailVO();
                detailVO.setExpense(j.getExpense());
                detailVO.setConsump_desc(j.getConsump_desc());
                detailVO.setConsump_date(CommonUtil.stampToDate(j.getConsump_date()));
                detailVO.setConsump_type(ConsumpTypeEnum.getTypeName(j.getConsump_type()));
                consumpDetailVOS.add(detailVO);
            }
            ConsumpDailyVO vo = new ConsumpDailyVO();
            if (i.getIs_over() == 0){
                vo.setIs_over(StringConstants.not_over);
            }else {
                vo.setIs_over(StringConstants.over);
            }
            vo.setConsump_date(CommonUtil.stampToDate(i.getConsump_date()));
            vo.setExpense(i.getExpense());
            vo.setConsumpDetailVOList(consumpDetailVOS);
            consumpDailyVOS.add(vo);
        }
        return consumpDailyVOS;
    }


    /**
     * 记录总数（按天查询）
     * @param dailyDto
     * @param token
     * @return
     */
    @Transactional
    public Integer getConsumpListSizeBydaily(ConsumptionDailyDto dailyDto,String token){
        String user_id = dailyDto.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        Integer totalSize = dailyDao.getConsumpDailyTotalSize(dailyDto);
        return totalSize;
    }

    /**
     * 获取上周消费记录
     * @return
     */
    @Transactional
    public List<Double> getLastweekTendency(String user_id,String token) throws ParseException {
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        //获取当天星期
        int weekday = CommonUtil.getWeekday();
        //获取当天时间戳
        Long today = CommonUtil.getTodayDate();

        //计算上个周末
        int day_num;
        if (weekday == 1){
            day_num = 7;
        }else {
            day_num = weekday - 1;
        }
        Long last_Sunday = today - day_num*ONE_DAY_SECOND;

        //获取上周消费列表
        List<Double> last_week = dailyDao.getLastweekTendency(last_Sunday,user_id);
        //倒叙列表
        Collections.reverse(last_week);

        return last_week;
    }


    /**
     * 获取本周消费记录
     * @return
     */
    @Transactional
    public List<Double> getThisweekTendency(String user_id,String token) throws ParseException {
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        //获取当天星期
        int weekday = CommonUtil.getWeekday();
        //获取当天时间戳
        Long today = CommonUtil.getTodayDate();

        //计算本周周一
        int day_num;
        if (weekday == 1){
            day_num = 6;
        }else {
            day_num = weekday - 2;
        }
        Long this_monday = today - day_num*ONE_DAY_SECOND;

        //获取本周数据
        List<Double> this_week = dailyDao.getThisweekTendency(this_monday,user_id);

        return this_week;
    }


    /**
     * 查询上个月的超标情况
     * @param user_id
     * @param token
     * @return
     */
    @Transactional
    public OverStandardVO getOverStandardrecord(String user_id, String token) throws ParseException {
        OverStandardVO standardVO = null;
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //获取上个月时间戳
        Long first_day = CommonUtil.getlastmonthfirstday();
        Long last_day = CommonUtil.getlastmonthlastday();

        //获取标准
        Double standard = dictionaryDao.getDictionaryNum(DictionaryCodeEnum.CONSUMP_DAILY_STANDARD.getDic_code(),user_id);

        //查询上个月超标次数
        List<ConsumptionDaily> list = dailyDao.getLastMonthOverList(first_day,last_day,user_id,standard);
        if (list.size()!=0) {
            Integer over_num = list.size();
            List<ConsumpDailyVO> consumpDailyVOList = new ArrayList<>();
            for (ConsumptionDaily daily : list) {
                ConsumpDailyVO vo = new ConsumpDailyVO();
                vo.setId(daily.getId());
                if (daily.getIs_over() == 0) {
                    vo.setIs_over(StringConstants.not_over);
                } else {
                    vo.setIs_over(StringConstants.over);
                }
                vo.setConsump_date(CommonUtil.stampToDate(daily.getConsump_date()));
                vo.setExpense(daily.getExpense());
                consumpDailyVOList.add(vo);
            }

            standardVO.setOver_num(over_num);
            standardVO.setConsumpDailyVOList(consumpDailyVOList);
        }
        return standardVO;
    }


    /**
     * 查询上月最高日
     * @param user_id
     * @param token
     * @return
     */
    @Transactional
    public ConsumpDailyVO getLastMonthHighest(String user_id, String token) throws ParseException {
        ConsumpDailyVO vo = null;
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //获取上个月时间戳
        Long first_day = CommonUtil.getlastmonthfirstday();
        Long last_day = CommonUtil.getlastmonthlastday();

        //获取最高日
        ConsumptionDaily daily = dailyDao.getLastMonthHighest(first_day,last_day,user_id);
        if (daily!=null) {
            vo.setId(daily.getId());
            vo.setExpense(daily.getExpense());
            vo.setConsump_date(CommonUtil.stampToDate(daily.getConsump_date()));
        }
        return vo;
    }
}
