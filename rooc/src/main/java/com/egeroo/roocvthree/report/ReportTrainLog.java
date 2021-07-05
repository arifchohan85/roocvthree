package com.egeroo.roocvthree.report;

import java.util.Date;

public class ReportTrainLog {
	
	private Date createdtime;
	private int totaldata;
	private String username;
	private String rolename;
	
	private String intentname;
	private String answerintentname;
	private double confidencelevelbefore;
	private double confidencelevelafter;
	private String question;
	private String expectedintentname;
	
	private String activityname;
	private String valuebefore;
	private String valueafter;
	
	public Date getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}
	public int getTotaldata() {
		return totaldata;
	}
	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getIntentname() {
		return intentname;
	}
	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}
	public String getAnswerintentname() {
		return answerintentname;
	}
	public void setAnswerintentname(String answerintentname) {
		this.answerintentname = answerintentname;
	}
	public double getConfidencelevelbefore() {
		return confidencelevelbefore;
	}
	public void setConfidencelevelbefore(double confidencelevelbefore) {
		this.confidencelevelbefore = confidencelevelbefore;
	}
	public double getConfidencelevelafter() {
		return confidencelevelafter;
	}
	public void setConfidencelevelafter(double confidencelevelafter) {
		this.confidencelevelafter = confidencelevelafter;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getExpectedintentname() {
		return expectedintentname;
	}
	public void setExpectedintentname(String expectedintentname) {
		this.expectedintentname = expectedintentname;
	}
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public String getValuebefore() {
		return valuebefore;
	}
	public void setValuebefore(String valuebefore) {
		this.valuebefore = valuebefore;
	}
	public String getValueafter() {
		return valueafter;
	}
	public void setValueafter(String valueafter) {
		this.valueafter = valueafter;
	}
	
	
	
	
	

}
