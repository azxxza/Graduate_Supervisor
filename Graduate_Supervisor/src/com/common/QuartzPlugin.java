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

public class QuartzPlugin
  implements IPlugin
{
  private Logger logger = LoggerFactory.getLogger(getClass());
  private SchedulerFactory sf = null;
  private Scheduler sched = null;

  public boolean start() {
    this.sf = new StdSchedulerFactory();
    try {
      this.sched = this.sf.getScheduler();
    } catch (SchedulerException e) {
      new RuntimeException(e);
    }
    this.sf = new StdSchedulerFactory();
    Scheduler sched = null;
    try {
      sched = this.sf.getScheduler();
    }
    catch (SchedulerException e1) {
      e1.printStackTrace();
    }
    try {
      sched.start();
    }
    catch (SchedulerException e1) {
      e1.printStackTrace();
    }

    JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1")
      .build();

    Trigger trigger = TriggerBuilder.newTrigger()
      .withIdentity("myTrigger", "group1")
      .startNow()
      .withSchedule(
      SimpleScheduleBuilder.simpleSchedule()
      .withIntervalInSeconds(40).repeatForever())
      .build();
    try
    {
      sched.scheduleJob(job, trigger);
    }
    catch (SchedulerException e) {
      e.printStackTrace();
    }
    return true;
  }

  public boolean stop()
  {
    try {
      this.sched.shutdown();
    } catch (SchedulerException e) {
      this.logger.error("shutdown error", e);
      return false;
    }
    return true;
  }
}