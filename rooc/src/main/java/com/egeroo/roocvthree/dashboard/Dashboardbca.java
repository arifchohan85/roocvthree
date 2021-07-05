package com.egeroo.roocvthree.dashboard;

import java.io.Serializable;
import java.util.Date;

public class Dashboardbca  implements Serializable{
	
	private Date datefrom;
	private Date dateto;
	
	private String kanwil_name;
	private int chatcount;
	private String kanwil;
	
	private String branch_name;
	private String branch;
	
	private String job_title;
	private String job;
	
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
	public String getKanwil_name() {
		return kanwil_name;
	}
	public void setKanwil_name(String kanwil_name) {
		this.kanwil_name = kanwil_name;
	}
	public int getChatcount() {
		return chatcount;
	}
	public void setChatcount(int chatcount) {
		this.chatcount = chatcount;
	}
	public String getKanwil() {
		return kanwil;
	}
	public void setKanwil(String kanwil) {
		this.kanwil = kanwil;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	
	
	

}
