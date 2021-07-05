package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardCltTreshold  implements Serializable {
	
	private String periode;
	private int totalanswer;
	private int totalanswerbybot;
	private int totalanswerbyagent;
	private int totalnotanswer;
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public int getTotalanswer() {
		return totalanswer;
	}
	public void setTotalanswer(int totalanswer) {
		this.totalanswer = totalanswer;
	}
	public int getTotalanswerbybot() {
		return totalanswerbybot;
	}
	public void setTotalanswerbybot(int totalanswerbybot) {
		this.totalanswerbybot = totalanswerbybot;
	}
	public int getTotalanswerbyagent() {
		return totalanswerbyagent;
	}
	public void setTotalanswerbyagent(int totalanswerbyagent) {
		this.totalanswerbyagent = totalanswerbyagent;
	}
	public int getTotalnotanswer() {
		return totalnotanswer;
	}
	public void setTotalnotanswer(int totalnotanswer) {
		this.totalnotanswer = totalnotanswer;
	}
	
	

}
