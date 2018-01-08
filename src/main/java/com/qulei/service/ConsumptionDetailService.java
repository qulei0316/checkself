package com.qulei.service;

import com.qulei.VO.ConsumpDailyVO;
import com.qulei.VO.ConsumpDetailVO;
import com.qulei.VO.ConsumptionDailyListVO;
import com.qulei.VO.ResultVO;
import com.qulei.common.enums.ConsumpTypeEnum;
import com.qulei.common.utils.AuthorizeUtil;
import com.qulei.common.utils.constant.StringConstants;
import com.qulei.dao.ConsumptionDailyDao;
import com.qulei.dao.ConsumptionDetailDao;
import com.qulei.entity.bean.ConsumptionDaily;
import com.qulei.entity.bean.ConsumptionDetail;
import com.qulei.entity.dto.ConsumptionDailyDto;
import com.qulei.entity.dto.ConsumptionDetailDto;
import com.qulei.common.enums.ExceptionEnum;
import com.qulei.common.exception.CheckSelfException;
import com.qulei.common.utils.CommonUtil;
import com.qulei.common.utils.CookieUtil;
import com.qulei.common.utils.KeyUtil;
import com.qulei.common.utils.constant.CookieConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private ConsumptionDailyDao dailyDao;

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
        consumptionDetail.setConsump_id(KeyUtil.getUniqueKey());
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

        List<ConsumpDailyVO> consumpDailyVOS= new ArrayList<>();

        //查询列表
        List<ConsumptionDaily> consumptionDailyList = dailyDao.getConsumptionDailyList(dailyDto);
        for (ConsumptionDaily i : consumptionDailyList) {
            List<ConsumptionDetail> consumptionDetailList = detailDao.getConsumptionDetailByDay(i.getConsump_date());
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

        return null;
    }
}
