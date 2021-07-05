package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardIncomingMessage implements Serializable{
	
	private int totalanswer;
	private int totalanswerbybot;
	private int totalanswerbyagent;
	private int totalnotanswer;
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
