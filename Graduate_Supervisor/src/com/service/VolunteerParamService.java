package com.service;

import java.util.List;

import com.bean.MessageBean;
import com.model.SysOpenTime;
import com.model.VolunteerTime;

public class VolunteerParamService {

	public boolean deleteOpenTime(int id) {
		SysOpenTime sysOpenTime = SysOpenTime.dao.findById(id);

		List<VolunteerTime> list = VolunteerTime
				.getVolunteerTimeByRound(sysOpenTime.getInt("r_t_round"));

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).delete();
			}
		}

		boolean flag = sysOpenTime.delete();

		return flag;
	}

	public MessageBean updateOpenTime(String para) {
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
				VolunteerTime volunteerTime = VolunteerTime.dao
						.findById(Integer.parseInt(id));

				volunteerTime.set("v_t_start_time", v_t_start_time);
				volunteerTime.set("v_t_end_time", v_t_end_time);

				boolean flag = volunteerTime.update();

				if (flag) {
					sucessCount++;
				}

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

	public boolean saveOpenTime(SysOpenTime s_round_open_time) {

		int r_t_round = SysOpenTime.getMaxRound();

		s_round_open_time.set("r_t_round", r_t_round);

		boolean flag = s_round_open_time.save();

		if (flag) {
			for (int i = 0; i < 5; i++) {
				VolunteerTime volunteerTime = new VolunteerTime();
				volunteerTime.set("r_t_round", r_t_round);
				volunteerTime.set("v_volunteer", i + 1);
				volunteerTime.save();
			}

		}

		return flag;

	}

}
