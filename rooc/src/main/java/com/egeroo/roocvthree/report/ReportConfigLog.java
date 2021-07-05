package com.egeroo.roocvthree.report;

import java.util.Date;

public class ReportConfigLog {
	
	private Date updated_at;
	private String code;
	private String beforevalue;
	private String aftervalue;
	private String username;
	
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBeforevalue() {
		return beforevalue;
	}
	public void setBeforevalue(String beforevalue) {
		this.beforevalue = beforevalue;
	}
	public String getAftervalue() {
		return aftervalue;
	}
	public void setAftervalue(String aftervalue) {
		this.aftervalue = aftervalue;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
