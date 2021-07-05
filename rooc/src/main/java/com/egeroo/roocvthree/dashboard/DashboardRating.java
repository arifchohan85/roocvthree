package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;
import java.util.Date;

public class DashboardRating implements Serializable{
	
	private Date datefrom;
	private Date dateto;
	
	
	private int chatcount;
	//private int rate;
	
	private int rate1;
	private int rate2;
	private int rate3;
	private int rate4;
	private int rate5;
	
	public Date getDatefrom() {
		return datefrom;
	}
	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}
	public Date getDateto() {
		return dateto;
	}
	public void setDateto(Date dateto) {
		this.dateto = dateto;
	}
	public int getChatcount() {
		return chatcount;
	}
	public void setChatcount(int chatcount) {
		this.chatcount = chatcount;
	}
	
	/*public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}*/
	
	public int getRate1() {
		return rate1;
	}
	public void setRate1(int rate1) {
		this.rate1 = rate1;
	}
	public int getRate2() {
		return rate2;
	}
	public void setRate2(int rate2) {
		this.rate2 = rate2;
	}
	public int getRate3() {
		return rate3;
	}
	public void setRate3(int rate3) {
		this.rate3 = rate3;
	}
	public int getRate4() {
		return rate4;
	}
	public void setRate4(int rate4) {
		this.rate4 = rate4;
	}
	public int getRate5() {
		return rate5;
	}
	public void setRate5(int rate5) {
		this.rate5 = rate5;
	}
	
	
	
	

}
