package com.service;

import java.util.ArrayList;
import java.util.List;

import com.model.LogicStudentVolunteer;
import com.util.ItemBean;
import com.util.QueryResult;

public class VolunteerService {
	public List<ItemBean> getVolunteerJson(String s_id) {

		List<ItemBean> treeList = new ArrayList<ItemBean>();

		QueryResult<LogicStudentVolunteer> queryResult = LogicStudentVolunteer
				.getVolunteerResultBySId(s_id);

		if (queryResult != null) {
			
			List<LogicStudentVolunteer> list = queryResult.getList();
			
			List<String> volunteerList = new ArrayList<String>();

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					LogicStudentVolunteer logicStudentVolunteer = list.get(i);
					String s_t_volunteer = logicStudentVolunteer
							.get("s_t_volunteer");

					volunteerList.add(s_t_volunteer);

				}
			}

			for (int i = 1; i <= 5; i++) {
				if (volunteerList.contains(i + "")) {
					continue;
				}
				treeList.add(new ItemBean(i + "", "第 " + i + " 志愿"));

			}
		}

		return treeList;

	}
}
