package com.qulei.daoTest;


import com.qulei.common.utils.UUIDUtil;
import com.qulei.dao.ConsumptionDetailDao;
import com.qulei.entity.bean.ConsumptionDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumptionDetailDaoTest {


    @Autowired
    private ConsumptionDetailDao detailDao;

    @Test
    public void addConsumptionDetailTest(){
        ConsumptionDetail consumptionDetail = new ConsumptionDetail();
        consumptionDetail.setConsump_id(UUIDUtil.createUUID());
        consumptionDetail.setConsump_desc("s");
        consumptionDetail.setConsump_type(1);
        consumptionDetail.setExpense(new BigDecimal(5.2));
        consumptionDetail.setUser_id("1");
        int i = detailDao.addConsumptionDetail(consumptionDetail);
        Assert.assertEquals(1,i);
    }
}
