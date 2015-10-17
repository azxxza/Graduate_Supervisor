package com.common;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.system.CurrentExcuteVolunteer;

public class VolunteerJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		CurrentExcuteVolunteer volunteerTime = CurrentExcuteVolunteer
				.getCurrentExcuteVolunteer();

//		Date date = volunteerTime.getDate();

//		Date currentDate = new Date();
//
//		if (currentDate.after(date)) {
//
//			JobKey jobKey = context.getJobDetail().getKey();
//
//			System.out.println(jobKey);
//
//			try {
//				context.getScheduler().deleteJob(jobKey);
//
//				System.out.println("系统已经结束运行");
//
//			} catch (SchedulerException e) {
//
//				e.printStackTrace();
//
//			}
//		}
	}
}