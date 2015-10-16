package com.service;

import com.model.InfoTeacherBasic;
import com.util.MessageBean;

public class TeacherBasicService {

	public MessageBean saveTeacherNumber(String para) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String t_work_id = elementArray[0];
			String t_number = elementArray[1];

			InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
					.getTmsTeacher(t_work_id);

			infoTeacherBasic.set("t_number", t_number);

			boolean flag = infoTeacherBasic.update();

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

}
