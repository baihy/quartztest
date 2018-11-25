package com.zskj.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zskj.jobs.TestJob;
import com.zskj.utils.JobUtils;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		runJob();
	}

	public static void runJob() {
		JobUtils.newInstance().startJob("jobName", "jobGroupName", "triggerName", "triggerGroupName", TestJob.class, "*/1 * * * * ?");
	}

	public static void run() {
		JobUtils.newInstance().startJob("jobName", "jobGroupName", "triggerName", "triggerGroupName", TestJob.class, "*/5 * * * * ?");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("********************移除job**********************");
		JobUtils.newInstance().removeJob("jobName", "jobGroupName", "triggerName", "triggerGroupName");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("********************停止job**********************");
		JobUtils.newInstance().stop();
	}
}
