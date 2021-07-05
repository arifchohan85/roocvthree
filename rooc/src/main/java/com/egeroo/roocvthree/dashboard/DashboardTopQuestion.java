package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardTopQuestion implements Serializable {
	
	private int topintentid;
	private String topquestion;
	private int totalintent;
	public int getTopintentid() {
		return topintentid;
	}
	public void setTopintentid(int topintentid) {
		this.topintentid = topintentid;
	}
	public String getTopquestion() {
		return topquestion;
	}
	public void setTopquestion(String topquestion) {
		this.topquestion = topquestion;
	}
	public int getTotalintent() {
		return totalintent;
	}
	public void setTotalintent(int totalintent) {
		this.totalintent = totalintent;
	}
	
	

}
