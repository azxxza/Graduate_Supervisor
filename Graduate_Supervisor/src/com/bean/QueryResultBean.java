package com.bean;

import java.util.List;

public class QueryResultBean<T> {
	private long count;
	private List<T> list;

	public QueryResultBean(long count, List<T> list) {
		super();
		this.count = count;
		this.list = list;
	}

	public QueryResultBean() {

	}

	public long getCount() {
		return count;
	}

	public List<T> getList() {
		return list;
	}

}
