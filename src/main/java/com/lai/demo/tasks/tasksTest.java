package com.lai.demo.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class tasksTest {
	
	private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * task定时任务测试
	 * 注解说明：
	 * @Scheduled(fixedRate=5000)//没5秒执行一次,定义一个按一定频率执行的定时任务
	 * @Scheduled(fixedDelay=5000)//没5秒执行一次,定义一个按一定频率执行的定时任务
	 * @Scheduled(cron="cron表达式"):通过表达式来配置任务执行时间
	 * 		页面访问http://cron.qqe2.com/,可获取[秒,分,时,日,月,周,年]7个时间段的表达式
	 * 		每个时间段可根据[周期,循环,指定值]来执行
	 * 		周期:10-50 * * * * ? --每分钟的1-60秒内,从10秒到50秒都会执行
	 * 		循环:10/5 * * * * ?  --每分钟的1-60秒内,从10秒开始,没5秒执行一次
	 * 		指定值:5,10,15 * * * * ?  --每分钟的1-60秒内,第5/10/15秒执行
	 */
	@Scheduled(cron="0 * * * * ? ")
	public void taskShow(){
		//System.out.println("tasksTest.class----当前时间:"+sdf.format(new Date()));
	}
}
