package com.common;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.plugin.IPlugin;

public class QuartzPlugin implements IPlugin {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Scheduler sched = null;

	public boolean start() {

		Scheduler sched = null;
		try {
			//从工厂中获取实例
			sched = StdSchedulerFactory.getDefaultScheduler();
			//启动实例
			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		// define the job and tie it to our VolunteerJob class
		JobDetail job = JobBuilder.newJob(VolunteerJob.class)
				.withIdentity("job1", "group1").build();

		// Trigger the job to run now, and then repeat every 40 seconds
//		Trigger trigger = TriggerBuilder
//				.newTrigger()
//				.withIdentity("trigger1", "group1").startAt(triggerStartTime);
//				.startNow()
//				.withSchedule(
//						SimpleScheduleBuilder.simpleSchedule()
//								.withIntervalInSeconds(40).repeatForever())
//				.build();
//		try {
//			sched.scheduleJob(job, trigger);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		return true;
	}

	public boolean stop() {
		try {
			this.sched.shutdown();
		} catch (SchedulerException e) {
			this.logger.error("shutdown error", e);
			return false;
		}
		return true;
	}
}