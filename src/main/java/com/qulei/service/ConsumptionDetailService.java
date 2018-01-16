package com.qulei.service;

import com.qulei.VO.*;
import com.qulei.common.enums.ConsumpTypeEnum;
import com.qulei.common.utils.*;
import com.qulei.common.utils.constant.StringConstants;
import com.qulei.dao.ConsumptionDailyDao;
import com.qulei.dao.ConsumptionDetailDao;
import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDailyDto;
import com.qulei.entity.dto.ConsumptionDetailDto;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.constant.CookieConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private AuthorizeUtil authorizeUtil;

    private static final Long onedaytimestamp = 24*60*60*1000L;


    /**
     * 录入消费记录
     * @param consumptionDetail
     */
    @Transactional
    public void addConsumptionDetail(ConsumptionDetail consumptionDetail,String token){
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


}
