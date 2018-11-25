/**
 * @项目名称: JavaTest
 * @文件名称: JobUtils.java
 * @日期: 2018年11月11日
 * @版权: 2018 河南中审科技有限公司 Inc. All rights reserved.
 * @开发公司或单位：河南中审科技有限公司研发交付中心
 */
package com.zskj.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lenovo
 *
 */
public class JobUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobUtils.class);

	private Scheduler scheduler;

	private static class SingletonInstance {
		private static final JobUtils jobUtils = new JobUtils();
	}

	private JobUtils() {
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			LOGGER.error("创建调度器异常:" + e.getMessage());
		}
	}

	/**
	 * 获取JobUtils对象实例
	 * 
	 * @return
	 */
	public static JobUtils newInstance() {
		return SingletonInstance.jobUtils;
	}

	/**
	 * 启动一个job
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @param jobClass
	 * @param cron
	 */
	public void startJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass, String cron) {
		try {
			// 创建定时任务
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
			// 创建任务计划
			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			LOGGER.error("job启动失败:" + e.getMessage());
		}
	}

	/**
	 * 移除一个job
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
		} catch (SchedulerException e) {
			LOGGER.error("移除job失败:" + e.getMessage());
		}
	}

	/**
	 * 停止任务调度器
	 */
	public void stop() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			LOGGER.error("停止调度器异常:" + e.getMessage());
		}
	}

}
