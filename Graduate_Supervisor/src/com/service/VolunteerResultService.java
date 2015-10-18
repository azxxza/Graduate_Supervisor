package com.service;

import java.util.ArrayList;
import java.util.List;

import com.bean.ItemBean;
import com.model.InfoTeacherBasic;
import com.model.LogicDoVolunteer;
import com.model.LogicVolunteerResult;

public class VolunteerResultService {

	public List<ItemBean> getHasRestTeacherJson() {

		List<InfoTeacherBasic> list = TeacherBasicService.getTeacherBaseList();

		List<ItemBean> treeList = new ArrayList<ItemBean>();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				InfoTeacherBasic infoTeacherBasic = list.get(i);
				String t_work_id = infoTeacherBasic.get("t_work_id");
				if (t_work_id != null && !t_work_id.equals("")) {
					long count = TeacherBasicService
							.getTeacherRestNumberByWorkId(t_work_id);
					if (count > 0) {
						treeList.add(new ItemBean(t_work_id, infoTeacherBasic
								.getStr("t_name")));
					}
				}

			}

		}

		return treeList;

	}

	public static boolean hasSelected(String s_id) {
		List<LogicVolunteerResult> list = LogicVolunteerResult
				.getVolunteerResultBySId(s_id);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean exitVolunteer(String s_id, String s_t_volunteer) {
		int count = LogicDoVolunteer.getVolunteerCount(s_id, s_t_volunteer);
		if (count > 0) {
			return true;
		}
		return false;
	}

}
