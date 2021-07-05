package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardTotalIntent implements Serializable {
	
	private int totalintentnew;
	private int totalintent;
	public int getTotalintentnew() {
		return totalintentnew;
	}
	public void setTotalintentnew(int totalintentnew) {
		this.totalintentnew = totalintentnew;
	}
	public int getTotalintent() {
		return totalintent;
	}
	public void setTotalintent(int totalintent) {
		this.totalintent = totalintent;
	}
	
	

}
