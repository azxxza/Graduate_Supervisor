package com.service;

import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.bean.MessageBean;
import com.model.SysOpenTime;
import com.model.SysVolunteerTime;
import com.system.QuartzService;
import com.util.DateUtil;

public class VolunteerParamService extends QuartzService {

	
	
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

		Scheduler sched = getSched();

		JobDetail startJob = getStartJob(volunteerTime);

		JobDetail endJob = getEndJob(volunteerTime);

		startJob.getJobDataMap().put("volunteerTime", volunteerTime);

		endJob.getJobDataMap().put("volunteerTime", volunteerTime);

		// 建立触发器
		Trigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger_start" + v_vulunteer + r_t_round,
						"group1").startAt(startDate)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();

		// 建立触发器
		Trigger trigger2 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger_end" + v_vulunteer + r_t_round, "group2")
				.startAt(endDate)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();

		try {
			sched.scheduleJob(startJob, trigger1);
			sched.scheduleJob(endJob, trigger2);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public static boolean deleteOpenTime(int id) {
		SysOpenTime sysOpenTime = SysOpenTime.dao.findById(id);

		List<SysVolunteerTime> list = SysVolunteerTime
				.getVolunteerTimeByRound(sysOpenTime.getInt("r_t_round"));

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).delete();
			}
		}

		boolean flag = sysOpenTime.delete();

		return flag;
	}

	public static MessageBean updateOpenTime(String para) {
		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String r_t_id = elementArray[0];
			String r_t_start_time = elementArray[1];
			String r_t_end_time = elementArray[2];

			SysOpenTime sysOpenTime = (SysOpenTime) SysOpenTime.dao
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

	public static boolean saveOpenTime(SysOpenTime s_round_open_time) {

		int r_t_round = SysOpenTime.getMaxRound();

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

	public static List<SysOpenTime> getOpenTimeList() {

		List<SysOpenTime> list = SysOpenTime.getOpenTimeList();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				SysOpenTime sysOpenTime = list.get(i);

				sysOpenTime.put("r_t_start_time_copy",
						sysOpenTime.getDate("r_t_start_time"));
				sysOpenTime.put("r_t_end_time_copy",
						sysOpenTime.getDate("r_t_end_time"));
			}
		}

		return list;

	}

	public static List<SysVolunteerTime> getVolunteerTimeByRound(int r_t_round) {
		List<SysVolunteerTime> list = SysVolunteerTime
				.getVolunteerTimeByRound(r_t_round);

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
