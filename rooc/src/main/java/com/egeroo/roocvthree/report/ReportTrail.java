package com.egeroo.roocvthree.report;

import java.util.Date;

import com.egeroo.roocvthree.core.base.Base;

public class ReportTrail extends Base  {
	private String method;
	private String controller;
	private String classname;
	private String username;
	
	private Date createddate;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	
	
	
	

}
