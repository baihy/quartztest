/**
 * @项目名称: JavaTest
 * @文件名称: TestJob.java
 * @日期: 2018年11月11日
 * @版权: 2018 河南中审科技有限公司 Inc. All rights reserved.
 * @开发公司或单位：河南中审科技有限公司研发交付中心
 */
package com.zskj.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zskj.main.Main;

/**
 * @author Lenovo
 *
 */
public class TestJob implements Job {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOGGER.info("job执行时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
