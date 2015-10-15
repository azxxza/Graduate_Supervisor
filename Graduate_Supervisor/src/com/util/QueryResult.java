package com.util;

import java.util.List;

public class QueryResult<T> {
	private long count;
	private List<T> list;

	public QueryResult(long count, List<T> list) {
		super();
		this.count = count;
		this.list = list;
	}

	public QueryResult() {

	}

	public long getCount() {
		return count;
	}

	public List<T> getList() {
		return list;
	}

}
