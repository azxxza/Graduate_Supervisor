package com.system;

import com.model.VolunteerTime;

/**
 * 系统志愿执行模型
 * 
 * @author Administrator
 *
 */
public class CurrentExcuteVolunteer {

	// 系统是否开放
	private boolean running = false;

	// 系统单例
	private static CurrentExcuteVolunteer currentExcuteVolunteer;

	private VolunteerTime volunteerTime;

	/**
	 * 系统如果开放，开放时间
	 * 
	 * @return
	 */
	public VolunteerTime getVolunteerTime() {

		return volunteerTime;
	}

	public void setVolunteerTime(VolunteerTime volunteerTime) {
		this.volunteerTime = volunteerTime;
	}

	/**
	 * 获取系统志愿执行单例
	 * 
	 * @return
	 */
	public static CurrentExcuteVolunteer getCurrentExcuteVolunteer() {

		if (currentExcuteVolunteer == null) {
			currentExcuteVolunteer = new CurrentExcuteVolunteer();
		}

		return currentExcuteVolunteer;
	}

	/**
	 * 系统是否开放
	 * 
	 * @return
	 */
	public boolean isRunning() {

		return running;
	}

}
