package com.egeroo.roocvthree.changepasswordcyclelimit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@SuppressWarnings("serial")
@Entity
@Table( name = "ms_app_changepasswordcyclelimit" )
public class ChangePasswordCycleLimit extends Base {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int changepasswordcyclelimitid;
	private int limitdata;
	private int expirein;
	
	public int getChangepasswordcyclelimitid() {
		return changepasswordcyclelimitid;
	}
	public void setChangepasswordcyclelimitid(int changepasswordcyclelimitid) {
		this.changepasswordcyclelimitid = changepasswordcyclelimitid;
	}
	
	public int getLimitdata() {
		return limitdata;
	}
	public void setLimitdata(int limitdata) {
		this.limitdata = limitdata;
	}
	
	public int getExpirein() {
		return expirein;
	}
	public void setExpirein(int expirein) {
		this.expirein = expirein;
	}
	
	

}
