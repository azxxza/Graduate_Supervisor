package com.service;

import com.model.InfoTeacherBasic;
import com.util.MessageBean;

public class TeacherBasicService {

	private MessageBean messageBean = new MessageBean();

	public boolean saveTeacherNumber(String t_work_id, int t_number) {
		InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
				.getTmsTeacher(t_work_id);
		boolean flag = false;

		if (infoTeacherBasic != null) {
			infoTeacherBasic.set("t_number", t_number);
			flag = infoTeacherBasic.update();
			return flag;
		}

		return flag;

	}

	public MessageBean saveTeacherBasic() {
		return messageBean;
	}

	public void delete() {

	}

	public void getById() {

	}

	public void getList() {

	}
}
