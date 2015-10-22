package com.app.service;

import static org.quartz.TriggerKey.triggerKey;

import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.app.bean.MessageBean;
import com.app.model.SysStudentRoundTime;
import com.app.model.SysTeacherRoundTime;
import com.app.model.SysVolunteerTime;
import com.app.system.EndStudentRoundJob;
import com.app.system.StartStudentRoundJob;
import com.app.util.DateUtil;
import com.pluins.quarz.GetScheduler;
import com.pluins.quarz.QuartzManagerUtil;

public class VolunteerParamService {

	/**
	 * 学生开放时间调度服务
	 */

	private void studentRoundService(SysStudentRoundTime studentRoundTime) {

		int r_t_round = studentRoundTime.getInt("r_t_round");

		Date start_time = studentRoundTime.getDate("r_t_start_time");

		Date end_time = studentRoundTime.getDate("r_t_end_time");
		
		

		QuartzManagerUtil.addJob("student_start" + r_t_round, "group1",
				"student_start" + r_t_round, "group1", start_time,
				StartStudentRoundJob.class);

		QuartzManagerUtil.addJob("student_end" + r_t_round, "group2",
				"student_end" + r_t_round, "group2", end_time,
				EndStudentRoundJob.class);

		// Scheduler scheduler = GetScheduler.getInstance();
		//
		// JobDetail startJobDetail = newJob(StartStudentRoundJob.class)
		// .withIdentity("student_start" + r_t_round, "group1").build();
		//
		// JobDetail endJobDetail =
		// newJob(EndStudentRoundJob.class).withIdentity(
		// "student_end" + r_t_round, "group2").build();
		//
		// // 建立触发器
		// Trigger trigger1 = TriggerBuilder.newTrigger()
		// .withIdentity("student_start" + r_t_round, "group1")
		// .startAt(start_time)
		// .withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
		//
		// // 建立触发器
		// Trigger trigger2 = TriggerBuilder.newTrigger()
		// .withIdentity("student_end" + r_t_round, "group2")
		// .startAt(end_time)
		// .withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
		//
		// try {
		// scheduler.scheduleJob(startJobDetail, trigger1);
		// scheduler.scheduleJob(endJobDetail, trigger2);
		// } catch (SchedulerException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 时间调度服务
	 * 
	 * @param volunteerTime
	 */
	private void addQuartzService(SysVolunteerTime volunteerTime) {

		int v_vulunteer = volunteerTime.getInt("v_volunteer");

		int r_t_round = volunteerTime.getInt("r_t_round");

		String v_t_start_time = volunteerTime.getStr("v_t_start_time");

		String v_t_end_time = volunteerTime.getStr("v_t_end_time");

		Date startDate = DateUtil.strToDate(v_t_start_time);

		Date endDate = DateUtil.strToDate(v_t_end_time);

	}

	public static boolean deleteTeacherTime(int id) {
		SysTeacherRoundTime sysOpenTime = SysTeacherRoundTime.dao.findById(id);

		List<SysVolunteerTime> list = SysVolunteerTime
				.findVolunteerTimeByRound(sysOpenTime.getInt("r_t_round"));

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).delete();
			}
		}

		boolean flag = sysOpenTime.delete();

		return flag;
	}

	public static boolean deleteStudentTime(int id) {

		SysStudentRoundTime sysOpenTime = SysStudentRoundTime.dao.findById(id);

		return sysOpenTime.delete();

	}

	public static MessageBean updateTeacherTime(String para) {
		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String r_t_id = elementArray[0];
			String r_t_start_time = elementArray[1];
			String r_t_end_time = elementArray[2];

			SysTeacherRoundTime sysOpenTime = (SysTeacherRoundTime) SysTeacherRoundTime.dao
					.findById(r_t_id);

			sysOpenTime.set("r_t_start_time", r_t_start_time);

			sysOpenTime.set("r_t_end_time", r_t_end_time);

			boolean flag = sysOpenTime.update();

			if (flag) {
				sucessCount++;
			}
		}

		int failCount = pairArray.length - sucessCount;

		messageBean.setFlag(true);
		String message = "成功保存:&nbsp;" + sucessCount + "&nbsp条数据";
		if (failCount != 0) {
			message += ",失败:" + failCount + "条";
		}
		messageBean.setMessage(message);

		return messageBean;

	}

	public static MessageBean updateStudentTime(String para) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String r_t_id = elementArray[0];
			String r_t_start_time = elementArray[1];
			String r_t_end_time = elementArray[2];

			SysStudentRoundTime sysOpenTime = SysStudentRoundTime.dao
					.findById(r_t_id);

			int r_t_round = sysOpenTime.getInt("r_t_round");

			sysOpenTime.set("r_t_start_time", r_t_start_time);

			sysOpenTime.set("r_t_end_time", r_t_end_time);

			try {
				Trigger oldStartTrigger = GetScheduler.getInstance()
						.getTrigger(
								triggerKey("student_start" + r_t_round,
										"group1"));

				Trigger oldEndTrigger = GetScheduler.getInstance().getTrigger(
						triggerKey("student_end" + r_t_round, "group2"));

				TriggerBuilder<?> tb1 = oldStartTrigger.getTriggerBuilder();

				TriggerBuilder<?> tb2 = oldEndTrigger.getTriggerBuilder();

				SimpleTrigger newTrigger1 = (SimpleTrigger) tb1.startAt(
						DateUtil.strToDate(r_t_start_time)).build();

				SimpleTrigger newTrigger2 = (SimpleTrigger) tb2.startAt(
						DateUtil.strToDate(r_t_end_time)).build();

				// 将Trigger放入scheduler；这样在2014-7-4 17:15:30还会去执行你的上面定义的job方法
				GetScheduler.getInstance().rescheduleJob(
						oldStartTrigger.getKey(), newTrigger1);

				GetScheduler.getInstance().rescheduleJob(
						oldEndTrigger.getKey(), newTrigger2);

			} catch (SchedulerException e1) {

				e1.printStackTrace();
			}

			// Scheduler sched = plugin.getSched();
			//
			// JobDetail studentStartJob = plugin.getStudentStartJob();
			//
			// JobDetail studentEndJob = plugin.getStudentEndjob();
			//
			// studentStartJob.getJobDataMap()
			// .put("studentRoundTime", sysOpenTime);
			//
			// studentEndJob.getJobDataMap().put("studentRoundTime",
			// sysOpenTime);
			//
			// String startDate = sysOpenTime.getStr("r_t_start_time");
			//
			// String endDate = sysOpenTime.getStr("r_t_end_time");
			//
			// Date start_date = DateUtil.strToDate(startDate);
			//
			// Date end_date = DateUtil.strToDate(endDate);
			//
			//
			// // 建立触发器
			// Trigger trigger1 = TriggerBuilder
			// .newTrigger()
			// .withIdentity("trigger_student_start" + r_t_round, "group1")
			// .startAt(start_date)
			// .withSchedule(SimpleScheduleBuilder.simpleSchedule())
			// .build();
			//
			// // 建立触发器
			// Trigger trigger2 = TriggerBuilder.newTrigger()
			// .withIdentity("trigger_student_end" + r_t_round, "group1")
			// .startAt(end_date)
			// .withSchedule(SimpleScheduleBuilder.simpleSchedule())
			// .build();
			//
			// try {
			// sched.scheduleJob(studentStartJob, trigger1);
			// sched.scheduleJob(studentEndJob, trigger2);
			// } catch (SchedulerException e) {
			// e.printStackTrace();
			// }

			boolean flag = sysOpenTime.update();

			if (flag) {
				sucessCount++;
			}
		}

		int failCount = pairArray.length - sucessCount;

		messageBean.setFlag(true);
		String message = "成功保存:&nbsp;" + sucessCount + "&nbsp条数据";
		if (failCount != 0) {
			message += ",失败:" + failCount + "条";
		}
		messageBean.setMessage(message);

		return messageBean;

	}

	public boolean saveStudentOpenTime(SysStudentRoundTime studentRoundTime) {

		// QuartzPlugin plugin = QuartzPlugin.getQuartzPlugin();

		int r_t_round = SysStudentRoundTime.getMaxRound();

		studentRoundTime.set("r_t_round", r_t_round);

		// Scheduler sched = plugin.getSched();

		// JobDetail studentStartJob = plugin.getStudentStartJob();
		//
		// JobDetail studentEndJob = plugin.getStudentEndjob();
		//
		// studentStartJob.getJobDataMap().put("studentRoundTime",
		// studentRoundTime);
		//
		// studentEndJob.getJobDataMap().put("studentRoundTime",
		// studentRoundTime);
		//
		// Date startDate = studentRoundTime.getDate("r_t_start_time");
		//
		// Date endDate = studentRoundTime.getDate("r_t_end_time");

		studentRoundService(studentRoundTime);

		// // 建立触发器
		// Trigger trigger1 = TriggerBuilder.newTrigger()
		// .withIdentity("trigger_student_start" + r_t_round, "group1")
		// .startAt(startDate)
		// .withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
		//
		// // 建立触发器
		// Trigger trigger2 = TriggerBuilder.newTrigger()
		// .withIdentity("trigger_student_end" + r_t_round, "group1")
		// .startAt(endDate)
		// .withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
		//
		// try {
		// sched.scheduleJob(studentStartJob, trigger1);
		// sched.scheduleJob(studentEndJob, trigger2);
		// } catch (SchedulerException e) {
		// e.printStackTrace();
		// }

		return studentRoundTime.save();

	}

	public static List<SysTeacherRoundTime> getTeacherTimeList() {

		List<SysTeacherRoundTime> list = SysTeacherRoundTime
				.findTeacherRoundTimeList();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				SysTeacherRoundTime sysOpenTime = list.get(i);

				sysOpenTime.put("r_t_start_time_copy",
						sysOpenTime.getDate("r_t_start_time"));
				sysOpenTime.put("r_t_end_time_copy",
						sysOpenTime.getDate("r_t_end_time"));
			}
		}

		return list;

	}

	public static List<SysStudentRoundTime> getStudentTimeList() {

		List<SysStudentRoundTime> list = SysStudentRoundTime
				.findStudentRoundTimeList();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				SysStudentRoundTime sysOpenTime = list.get(i);

				sysOpenTime.put("r_t_start_time_copy",
						sysOpenTime.getDate("r_t_start_time"));
				sysOpenTime.put("r_t_end_time_copy",
						sysOpenTime.getDate("r_t_end_time"));
			}
		}

		return list;

	}

	public MessageBean updateVolunteersTime(String para) {
		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String id = elementArray[0];
			String v_t_start_time = elementArray[1];
			String v_t_end_time = elementArray[2];

			if (v_t_end_time != null && v_t_start_time != null) {
				SysVolunteerTime volunteerTime = SysVolunteerTime.dao
						.findById(Integer.parseInt(id));

				volunteerTime.set("v_t_start_time", v_t_start_time);
				volunteerTime.set("v_t_end_time", v_t_end_time);
				volunteerTime.set("v_is_end", 0);

				Date currDate = new Date();
				Date startDate = DateUtil.strToDate(v_t_start_time);
				if (startDate.before(currDate)) {
					messageBean.setFlag(false);
					int count = i + 1;
					messageBean.setMessage("第" + count + "志愿保存失败，开始时间已经过了当前时间");
					return messageBean;
				}

				boolean flag = volunteerTime.update();

				if (flag) {
					sucessCount++;
				}

				addQuartzService(volunteerTime);

			}

		}

		int failCount = pairArray.length - sucessCount;

		messageBean.setFlag(true);
		String message = "成功保存:&nbsp;" + sucessCount + "&nbsp条数据";
		if (failCount != 0) {
			message += ",失败:" + failCount + "条";
		}
		messageBean.setMessage(message);

		return messageBean;

	}

	public static boolean saveTeacherOpenTime(
			SysTeacherRoundTime s_round_open_time) {

		int r_t_round = SysTeacherRoundTime.getMaxRound();

		s_round_open_time.set("r_t_round", r_t_round);

		boolean flag = s_round_open_time.save();

		if (flag) {
			for (int i = 0; i < 5; i++) {
				SysVolunteerTime volunteerTime = new SysVolunteerTime();
				volunteerTime.set("r_t_round", r_t_round);
				volunteerTime.set("v_volunteer", i + 1);
				volunteerTime.save();
			}

		}

		return flag;

	}

	public static List<SysVolunteerTime> getVolunteerTimeByRound(int r_t_round) {
		List<SysVolunteerTime> list = SysVolunteerTime
				.findVolunteerTimeByRound(r_t_round);

		for (int i = 0; i < list.size(); i++) {
			SysVolunteerTime sysVolunteerTime = list.get(i);
			sysVolunteerTime.put("v_t_start_time_copy",
					sysVolunteerTime.getDate("v_t_start_time"));
			sysVolunteerTime.put("v_t_end_time_copy",
					sysVolunteerTime.getDate("v_t_end_time"));
		}

		return list;

	}
}
