package com.app.system;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.app.model.SysVolunteerTime;

public class StartVolunteerJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		SysVolunteerTime volunteerTime = (SysVolunteerTime) context
				.getJobDetail().getJobDataMap().get("volunteerTime");

		CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
				.getCurrentExcuteVolunteer();

		currentExcuteVolunteer.setRunning(true);

		currentExcuteVolunteer.setVolunteerTime(volunteerTime);
		

	

	}
}