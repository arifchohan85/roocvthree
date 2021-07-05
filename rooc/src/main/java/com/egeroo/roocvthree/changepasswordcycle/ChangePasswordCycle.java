package com.egeroo.roocvthree.changepasswordcycle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@SuppressWarnings("serial")
@Entity
@Table( name = "ms_app_changepasswordcycle" )
public class ChangePasswordCycle extends Base  {
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int changepasswordcycleid;
	private int userid;
	private String password;
	
	private int countdata;
	
	public int getCountdata() {
		return countdata;
	}
	public void setCountdata(int countdata) {
		this.countdata = countdata;
	}
	public int getChangepasswordcycleid() {
		return changepasswordcycleid;
	}
	public void setChangepasswordcycleid(int changepasswordcycleid) {
		this.changepasswordcycleid = changepasswordcycleid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
