package com.egeroo.roocvthree.botrate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@SuppressWarnings("serial")
@Entity
@Table( name = "tr_eng_botrate" )
public class Botrate extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int botrateid;
	private int userid;
	private int customerchannelid;
	private int rate;
	private String channel;
	
	public int getBotrateid() {
		return botrateid;
	}
	public void setBotrateid(int botrateid) {
		this.botrateid = botrateid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getCustomerchannelid() {
		return customerchannelid;
	}
	public void setCustomerchannelid(int customerchannelid) {
		this.customerchannelid = customerchannelid;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	

}
