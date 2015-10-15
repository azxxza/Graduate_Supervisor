package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.util.QueryResult;

@SuppressWarnings("serial")
public class SysYearTerm extends Model<SysYearTerm> {
	public final static SysYearTerm dao = new SysYearTerm();

	public static QueryResult<SysYearTerm> getYearTermResult(int page, int rows) {
		String sql = "from s_year_term";

		List<SysYearTerm> list = null;
		try {

			if (page == 0 && rows == 0) {
				list = SysYearTerm.dao.find("select * " + sql);
			} else {
				Page<SysYearTerm> pageList = SysYearTerm.dao.paginate(page,
						rows, "Select * ", sql);

				list = pageList.getList();
			}

			long count = Db.queryLong("select count(1) " + sql);

			return new QueryResult<SysYearTerm>(count, list);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SysYearTerm getSysYearTermByYearAndTerm(String year, String term) {
		String sql = "select * from s_year_term where year = '" + year.trim()
				+ "' and term = '" + term.trim() + "'";
		List<SysYearTerm> list = SysYearTerm.dao.find(sql);

		System.out.println(sql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
