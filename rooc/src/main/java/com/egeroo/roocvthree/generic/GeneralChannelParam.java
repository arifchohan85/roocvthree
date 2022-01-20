package com.egeroo.roocvthree.generic;

import java.io.Serializable;
import java.util.Map;

public class GeneralChannelParam implements Serializable{
	private static final long serialVersionUID = 1L;
	private String key;
	private String params;
	private Map<String, Object> data;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
