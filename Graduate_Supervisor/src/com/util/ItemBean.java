package com.util;

/**
 * 数据字典项
 * 
 * @author azx
 *
 */
public class ItemBean {
	private String value;
	private String text;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ItemBean(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

}
