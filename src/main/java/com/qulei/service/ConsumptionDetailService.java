package com.qulei.service;

import com.qulei.VO.*;
import com.qulei.common.enums.ConsumpTypeEnum;
import com.qulei.common.enums.DictionaryCodeEnum;
import com.qulei.common.utils.*;
import com.qulei.dao.ConsumptionDailyDao;
import com.qulei.dao.ConsumptionDetailDao;
import com.qulei.dao.DictionaryDao;
import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDetailDto;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */
@Service
public class ConsumptionDetailService {

    @Autowired
    private ConsumptionDetailDao detailDao;

    @Autowired
    private DictionaryDao dictionaryDao;

    @Autowired
    private ConsumptionDailyDao dailyDao;

    @Autowired
    private AuthorizeUtil authorizeUtil;

    private static final Long onedaytimestamp = 24*60*60*1000L;


    /**
     * 录入消费记录
     * @param consumptionDetail
     */
    @Transactional
    public void addConsumptionDetail(ConsumptionDetail consumptionDetail,String token) throws ParseException {
        //鉴权
        String user_id = consumptionDetail.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //输入条件判空
        if (consumptionDetail.getConsump_date() == null){
            throw new CheckSelfException(ExceptionEnum.CONSUMP_DATE_EMPTY_ERROR);
        }
        if (consumptionDetail.getConsump_type() == null){
            throw new CheckSelfException(ExceptionEnum.CONSUMP_TYPE_EMPTY_ERROR);
        }
        if (consumptionDetail.getExpense() == null){
            throw new CheckSelfException(ExceptionEnum.CONSUMP_EXPENSE_EMPTY_ERROR);
        }

        //插入主键
        consumptionDetail.setConsump_id(UUIDUtil.createUUID());
        consumptionDetail.setUser_id(user_id);

        //插入数据
        int i = detailDao.addConsumptionDetail(consumptionDetail);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.CONSUMP_RECORD_ADD_ERROR);
        }

        //判断录入记录是否是当天记录
        Long today = CommonUtil.getTodayDate();
        if (!today.equals(consumptionDetail.getConsump_date())){
            //修改日消费记录
            ConsumptionDaily daily = new ConsumptionDaily();
            daily.setUser_id(consumptionDetail.getUser_id());
            List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetailByDay(consumptionDetail.getConsump_date(),consumptionDetail.getUser_id());
            Double daily_total = new Double(0);
            for (ConsumptionDetail detail : consumptionDetailList) {
                daily_total += detail.getExpense();
            }
            daily.setConsump_date(today);
            daily.setExpense(daily_total);
            //查询消费标准
            Double standard = dictionaryDao.getDictionaryNum(DictionaryCodeEnum.CONSUMP_DAILY_STANDARD.getDic_code(),user_id);
            Integer is_over = 0;
            if (daily_total > standard){
                is_over = 1;
            }
            daily.setIs_over(is_over);
            int j = dailyDao.updateConsumpDailyrecord(daily);
            if (j==0){
                throw new CheckSelfException(ExceptionEnum.DAILY_RECORD_CREATE_ERROR);
            }
        }
    }


    /**
     * 按记录查询列表
     * @param detailDto
     * @return
     */
    @Transactional
    public List<ConsumpDetailVO> getConsumpListByrecord(ConsumptionDetailDto detailDto, String token){
        String user_id = detailDto.getUser_id();

        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //判断输入条件
        if (detailDto.getStart_time()!=null && detailDto.getEnd_time()!=null && detailDto.getEnd_time()<detailDto.getStart_time()){
            throw new CheckSelfException(ExceptionEnum.CONSUMP_DATE_RANGE_ERROR);
        }

        //设置分页
        detailDto.setStartIndex((detailDto.getPageIndex()-1)*10);

        //查询列表
        List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetail(detailDto);
        List<ConsumpDetailVO> consumpDetailVOS = new ArrayList<>();
        for (ConsumptionDetail i : consumptionDetailList){
            ConsumpDetailVO vo = new ConsumpDetailVO();
            vo.setConusmp_id(i.getConsump_id());
            vo.setExpense(i.getExpense());
            vo.setConsump_desc(i.getConsump_desc());
            vo.setConsump_type(ConsumpTypeEnum.getTypeName(i.getConsump_type()));
            vo.setConsump_date(CommonUtil.stampToDate(i.getConsump_date()));
            consumpDetailVOS.add(vo);
        }
        return  consumpDetailVOS;
    }


    /**
     * 查询记录总数(按记录查询)
     */
    @Transactional
    public Integer getConsumptionListSize(ConsumptionDetailDto detailDto,String token){
        String user_id = detailDto.getUser_id();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        //查询总数
        Integer totalSize = detailDao.getConsumptionDetailListSize(detailDto);
        return totalSize;
    }


    /**
     * 生成昨天的记录
     */
    @Transactional
    public List<ConsumpDetailVO> getLastDayConsumptionRecord(String user_id,String token) throws ParseException {
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }
        Long lastday = CommonUtil.getTodayDate() - onedaytimestamp;
        List<ConsumpDetailVO> consumpDetailVOS = new ArrayList<>();
        List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetailByDay(lastday,user_id);
        for (ConsumptionDetail i : consumptionDetailList){
            ConsumpDetailVO vo = new ConsumpDetailVO();
            vo.setExpense(i.getExpense());
            vo.setConsump_desc(i.getConsump_desc());
            vo.setConsump_type(ConsumpTypeEnum.getTypeName(i.getConsump_type()));
            consumpDetailVOS.add(vo);
        }
        return consumpDetailVOS;
     }



    /**
     * 生成上月消费占比
     * @return
     */
    @Transactional
    public List<LastweekConsumpTypeProportionVO> getMonthTypeProportion(String user_id,String token) throws ParseException {
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //获取上个月第一天和最后一天时间戳
        Long first_day = CommonUtil.getlastmonthfirstday();
        Long last_day = CommonUtil.getlastmonthlastday();

        List<LastweekConsumpTypeProportionVO> list = new ArrayList<>();
        //遍历查询所有类型金额数
        for (ConsumpTypeEnum consumpTypeEnum : ConsumpTypeEnum.values()){
            LastweekConsumpTypeProportionVO vo = new LastweekConsumpTypeProportionVO();
            Double sum_expense = detailDao.getConumpTypeProportion(first_day,last_day,consumpTypeEnum.getCode(),user_id);
            vo.setValue(sum_expense);
            vo.setName(consumpTypeEnum.getType());
            list.add(vo);
        }
        return list;
    }


    /**
     * 删除记录
     * @param consumptionDetail
     * @param token
     */
    @Transactional
    public void deleteDetailRecord(ConsumptionDetail consumptionDetail, String token) {
        //鉴权
        String user_id = consumptionDetail.getUser_id();
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        int i = detailDao.deleteDetailRecord(consumptionDetail);
        if (i==0){
            throw new CheckSelfException(ExceptionEnum.DELETE_DETAIL_ERROR);
        }
    }


    /**
     * 获取最高消费纪录
     * @param user_id
     * @param token
     * @return
     */
    @Transactional
    public ConsumpDetailVO getHighestRecord(String user_id, String token) throws ParseException {
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //获取上个月第一天和最后一天时间戳
        Long first_day = CommonUtil.getlastmonthfirstday();
        Long last_day = CommonUtil.getlastmonthlastday();

        ConsumptionDetail detail = detailDao.getHighestRecord(user_id,first_day,last_day);
        ConsumpDetailVO vo = new ConsumpDetailVO();
        if (detail != null) {
                vo.setExpense(detail.getExpense());
                vo.setConsump_desc(detail.getConsump_desc());
                vo.setConsump_type(ConsumpTypeEnum.getTypeName(detail.getConsump_type()));
                vo.setConsump_date(CommonUtil.stampToDate(detail.getConsump_date()));
        }

        return vo;
    }


//    /**
//     * 查询上个月占比最大消费类型
//     * @param user_id
//     * @param token
//     * @return
//     */
//    @Transactional
//    public ConsumpDetailVO getHighestProportion(String user_id, String token) throws ParseException {
//        ConsumpDetailVO vo = new ConsumpDetailVO();
//        //鉴权
//        if (!authorizeUtil.verify(user_id,token)){
//            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
//        }
//
//        //获取上个月第一天和最后一天时间戳
//        Long first_day = CommonUtil.getlastmonthfirstday();
//        Long last_day = CommonUtil.getlastmonthlastday();
//
//        //查询
//        ConsumptionDetail detail = detailDao.getHighestProportion(user_id,first_day,last_day);
//        vo.setConsump_type(ConsumpTypeEnum.getTypeName(detail.getConsump_type()));
//        vo.setExpense(detail.getExpense());
//        return vo;
//    }


    /**
     * 查询剩余预算
     * @param user_id
     * @param token
     * @return
     */
    @Transactional
    public ThisMonthSurplusVO getThisMonthConsumption(String user_id, String token) throws ParseException {
        ThisMonthSurplusVO vo = new ThisMonthSurplusVO();
        //鉴权
        if (!authorizeUtil.verify(user_id,token)){
            throw new CheckSelfException(ExceptionEnum.AUTHORIZE_FAIL);
        }

        //获取本月第一天
        Long first_day = CommonUtil.getThismonthFirstDay();
        Double consumption = detailDao.getThisMonthConsumption(first_day,user_id);
        Double standard = dictionaryDao.getDictionaryNum(DictionaryCodeEnum.CONSUMP_MONTHLY_STANDARD.getDic_code(),user_id);
        Double surplus = standard - consumption;
        vo.setConsumption(consumption);
        vo.setSurplus(surplus);
        return vo;
    }
}
