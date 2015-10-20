package com.system;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.jfinal.plugin.IPlugin;
import com.model.SysVolunteerTime;

public class QuartzService implements IPlugin {

	private JobDetail startJob;

	private JobDetail endJob;

	protected Scheduler sched;

	public JobDetail getStartJob(SysVolunteerTime volunteerTime) {

		try {
			if (sched.getContext().getKeys().length > 0) {
				System.out.println(sched.getContext());
			}

		} catch (SchedulerException e) {

			e.printStackTrace();
		}

		startJob = JobBuilder.newJob(StartVolunteerJob.class)
				.withIdentity("job1", "group1").build();
		System.out.println("创建开始工作");

		return startJob;
	}

	public JobDetail getEndJob(SysVolunteerTime volunteerTime) {

		endJob = JobBuilder.newJob(EndVolunteerJob.class)
				.withIdentity("job2", "group2").build();
		System.out.println("创建结束工作");

		return endJob;
	}

	public Scheduler getSched() {

		if (sched == null) {
			try {
				sched = StdSchedulerFactory.getDefaultScheduler();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

		return sched;
	}

	public boolean start() {

		getSched();

		System.out.println("插件启动了");

		return true;
	}

	public boolean stop() {
		try {
			sched.shutdown();
			System.out.println("插件关闭了");
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}