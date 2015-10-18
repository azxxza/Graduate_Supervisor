package com.model;

import java.util.List;

import com.bean.QueryResultBean;
import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class SysYearTerm extends Model<SysYearTerm> {

	private static final long serialVersionUID = 1L;
	public final static SysYearTerm dao = new SysYearTerm();
	private static final String SYS_YEAR_TERM = TableCommom.SYS_YEAR_TERM;

	public static List<SysYearTerm> getYearTermList() {

		String sql = "select * from " + SYS_YEAR_TERM;

		return dao.find(sql);

	}

	public static QueryResultBean<SysYearTerm> getYearTermResult(int page,
			int rows) {

		String sql = "from " + SYS_YEAR_TERM;

		List<SysYearTerm> list = null;

		Page<SysYearTerm> pageList = SysYearTerm.dao.paginate(page, rows,
				"select * ", sql);

		list = pageList.getList();

		long count = Db.queryLong("select count(1) " + sql);

		return new QueryResultBean<SysYearTerm>(count, list);

	}

	public static SysYearTerm getSysYearTermByYearAndTerm(String year,
			String term) {
		String sql = "select * from " + SYS_YEAR_TERM + " where year = '"
				+ year + "' and term = '" + term + "'";
		return SysYearTerm.dao.findFirst(sql);

	}

}
