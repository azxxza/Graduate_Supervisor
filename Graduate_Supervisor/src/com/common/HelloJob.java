package com.common;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.util.VolunteerTimeUtil;

public class HelloJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		VolunteerTimeUtil volunteerTime = VolunteerTimeUtil.getVolunteerTime();

		Date date = volunteerTime.getDate();

		Date currentDate = new Date();

		if (currentDate.after(date)) {
			JobKey jobKey = context.getJobDetail().getKey();
			try {
				context.getScheduler().deleteJob(jobKey);

				System.out.println("系统已经结束运行");
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
}