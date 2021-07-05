package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardIntentTrained implements Serializable {
	
	private String intentname;
	private int totalquestion;
	public String getIntentname() {
		return intentname;
	}
	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}
	public int getTotalquestion() {
		return totalquestion;
	}
	public void setTotalquestion(int totalquestion) {
		this.totalquestion = totalquestion;
	}
	
	

}
