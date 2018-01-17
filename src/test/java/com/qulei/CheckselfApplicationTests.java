package com.qulei;

import com.qulei.common.utils.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.CommunicationException;
import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckselfApplicationTests {

	private final static Long one_day = 24*60*60*1000L;

	@Test
	public void contextLoads() throws ParseException {
		Long today = CommonUtil.getTodayDate()-one_day;
		String date = CommonUtil.stampToDate(today);
		int weekday = CommonUtil.getWeekday();
		System.out.print(date +" " + weekday);
	}

	@Test
	public void testday() throws ParseException {
		Long ls = CommonUtil.getThismonthFirstDay();
		System.out.print(CommonUtil.stampToDate(ls));

	}

}
