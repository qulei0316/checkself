package com.qulei.dao;

import com.qulei.entity.bean.Cron;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/1/24.
 */
public interface CronDao {

    int insertCron(@Param("user_id") String user_id);

    Cron getCron(Cron cron);

    int updateCron(Cron cron);
}
