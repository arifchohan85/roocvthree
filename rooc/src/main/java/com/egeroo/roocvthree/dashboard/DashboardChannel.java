package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DashboardChannel implements Serializable{
	
	private String channelname;
	private int totalchat;
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	public int getTotalchat() {
		return totalchat;
	}
	public void setTotalchat(int totalchat) {
		this.totalchat = totalchat;
	}
	
	

}
