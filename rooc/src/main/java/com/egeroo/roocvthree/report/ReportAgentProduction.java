package com.egeroo.roocvthree.report;

import java.io.Serializable;
import java.util.Date;

public class ReportAgentProduction  implements Serializable{
	
	private Date datefrom;
	private Date dateto;
	
	private Date periode1;
	private String username;
	
	private int chatroomcount;
	private int assignedchatcount;
	private int bubblechatcount;
	private int freetextchatcount;
	private int sendtextchatcount;
	private int traintextchatcount;
	private int avgresponsetime;
	private int minresponsetime;
	private int maxresponsetime;
	private int avgresolutiontime;
	private int minresolutiontime;
	private int maxresolutiontime;
	private int avglastresolutiontime;
	private int staffedtime;
	
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
	public Date getPeriode1() {
		return periode1;
	}
	public void setPeriode1(Date periode1) {
		this.periode1 = periode1;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getChatroomcount() {
		return chatroomcount;
	}
	public void setChatroomcount(int chatroomcount) {
		this.chatroomcount = chatroomcount;
	}
	public int getAssignedchatcount() {
		return assignedchatcount;
	}
	public void setAssignedchatcount(int assignedchatcount) {
		this.assignedchatcount = assignedchatcount;
	}
	public int getBubblechatcount() {
		return bubblechatcount;
	}
	public void setBubblechatcount(int bubblechatcount) {
		this.bubblechatcount = bubblechatcount;
	}
	public int getFreetextchatcount() {
		return freetextchatcount;
	}
	public void setFreetextchatcount(int freetextchatcount) {
		this.freetextchatcount = freetextchatcount;
	}
	public int getSendtextchatcount() {
		return sendtextchatcount;
	}
	public void setSendtextchatcount(int sendtextchatcount) {
		this.sendtextchatcount = sendtextchatcount;
	}
	public int getTraintextchatcount() {
		return traintextchatcount;
	}
	public void setTraintextchatcount(int traintextchatcount) {
		this.traintextchatcount = traintextchatcount;
	}
	public int getAvgresponsetime() {
		return avgresponsetime;
	}
	public void setAvgresponsetime(int avgresponsetime) {
		this.avgresponsetime = avgresponsetime;
	}
	public int getMinresponsetime() {
		return minresponsetime;
	}
	public void setMinresponsetime(int minresponsetime) {
		this.minresponsetime = minresponsetime;
	}
	public int getMaxresponsetime() {
		return maxresponsetime;
	}
	public void setMaxresponsetime(int maxresponsetime) {
		this.maxresponsetime = maxresponsetime;
	}
	public int getAvgresolutiontime() {
		return avgresolutiontime;
	}
	public void setAvgresolutiontime(int avgresolutiontime) {
		this.avgresolutiontime = avgresolutiontime;
	}
	public int getMinresolutiontime() {
		return minresolutiontime;
	}
	public void setMinresolutiontime(int minresolutiontime) {
		this.minresolutiontime = minresolutiontime;
	}
	public int getMaxresolutiontime() {
		return maxresolutiontime;
	}
	public void setMaxresolutiontime(int maxresolutiontime) {
		this.maxresolutiontime = maxresolutiontime;
	}
	public int getAvglastresolutiontime() {
		return avglastresolutiontime;
	}
	public void setAvglastresolutiontime(int avglastresolutiontime) {
		this.avglastresolutiontime = avglastresolutiontime;
	}
	public int getStaffedtime() {
		return staffedtime;
	}
	public void setStaffedtime(int staffedtime) {
		this.staffedtime = staffedtime;
	}
	
	
	
	

}
