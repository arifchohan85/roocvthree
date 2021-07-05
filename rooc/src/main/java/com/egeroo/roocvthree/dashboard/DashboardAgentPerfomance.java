package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardAgentPerfomance implements Serializable{
	
	private int agentid;
	private String agentname;
	private int totalanswer;
	public int getAgentid() {
		return agentid;
	}
	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	public int getTotalanswer() {
		return totalanswer;
	}
	public void setTotalanswer(int totalanswer) {
		this.totalanswer = totalanswer;
	}
	
	

}
