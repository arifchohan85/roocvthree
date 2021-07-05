package com.egeroo.roocvthree.userchannellink;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_userchannellink" )
public class UserChannelLink extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;
	private int userid;
	private int userchannelid;
	private String userchannelcode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getUserchannelid() {
		return userchannelid;
	}
	public void setUserchannelid(int userchannelid) {
		this.userchannelid = userchannelid;
	}
	public String getUserchannelcode() {
		return userchannelcode;
	}
	public void setUserchannelcode(String userchannelcode) {
		this.userchannelcode = userchannelcode;
	}
	
	
}
