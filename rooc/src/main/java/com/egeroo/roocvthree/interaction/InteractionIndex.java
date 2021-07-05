package com.egeroo.roocvthree.interaction;

import java.sql.Timestamp;
import java.util.Date;

import com.egeroo.roocvthree.core.base.Base;

public class InteractionIndex extends Base{
	private Date datefrom;
	private Date dateto;
	
	private int interactionid;
    private int intentid;
    private String intentname;
    
    private int expectedintentid;
	private String expectedintentname;
	
	private String question;
	
	private int minconfidence;
	private int maxconfidence;
	private int active;
	private int iretrespondid;
	private int isupdated;
	private int istrain;
	private String qid;
	
	private String channel;
	private String answerby;
	private String idcustomerchannel;
	private int roomid;
	private int chatid;
	private int userchannelid;
	private int customerchannelid;
	private int responsechatid;
	private Timestamp startchattime;
	private Timestamp responsechattime;
	private int ismanual;
	
	private double confidencelevel;
	
	private int answerintentid;
	
	private String answerintentname;

	private String select;
	private String where;
	private String sort;
	
	private int countalldata;
	
	private String username;
	private String customername;
	private String customer;
	
	
	
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

	public int getInteractionid() {
		return interactionid;
	}

	public void setInteractionid(int interactionid) {
		this.interactionid = interactionid;
	}

	public int getIntentid() {
		return intentid;
	}

	public void setIntentid(int intentid) {
		this.intentid = intentid;
	}

	public String getIntentname() {
		return intentname;
	}

	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}

	public int getExpectedintentid() {
		return expectedintentid;
	}

	public void setExpectedintentid(int expectedintentid) {
		this.expectedintentid = expectedintentid;
	}

	public String getExpectedintentname() {
		return expectedintentname;
	}

	public void setExpectedintentname(String expectedintentname) {
		this.expectedintentname = expectedintentname;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswerby() {
		return answerby;
	}

	public void setAnswerby(String answerby) {
		this.answerby = answerby;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public double getConfidencelevel() {
		return confidencelevel;
	}

	public void setConfidencelevel(double confidencelevel) {
		this.confidencelevel = confidencelevel;
	}

	public int getAnswerintentid() {
		return answerintentid;
	}

	public void setAnswerintentid(int answerintentid) {
		this.answerintentid = answerintentid;
	}

	public String getAnswerintentname() {
		return answerintentname;
	}

	public void setAnswerintentname(String answerintentname) {
		this.answerintentname = answerintentname;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getCountalldata() {
		return countalldata;
	}

	public void setCountalldata(int countalldata) {
		this.countalldata = countalldata;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getMinconfidence() {
		return minconfidence;
	}

	public void setMinconfidence(int minconfidence) {
		this.minconfidence = minconfidence;
	}

	public int getMaxconfidence() {
		return maxconfidence;
	}

	public void setMaxconfidence(int maxconfidence) {
		this.maxconfidence = maxconfidence;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getIretrespondid() {
		return iretrespondid;
	}

	public void setIretrespondid(int iretrespondid) {
		this.iretrespondid = iretrespondid;
	}

	public int getIsupdated() {
		return isupdated;
	}

	public void setIsupdated(int isupdated) {
		this.isupdated = isupdated;
	}

	public int getIstrain() {
		return istrain;
	}

	public void setIstrain(int istrain) {
		this.istrain = istrain;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getIdcustomerchannel() {
		return idcustomerchannel;
	}

	public void setIdcustomerchannel(String idcustomerchannel) {
		this.idcustomerchannel = idcustomerchannel;
	}

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public int getChatid() {
		return chatid;
	}

	public void setChatid(int chatid) {
		this.chatid = chatid;
	}

	public int getUserchannelid() {
		return userchannelid;
	}

	public void setUserchannelid(int userchannelid) {
		this.userchannelid = userchannelid;
	}

	public int getCustomerchannelid() {
		return customerchannelid;
	}

	public void setCustomerchannelid(int customerchannelid) {
		this.customerchannelid = customerchannelid;
	}

	public int getResponsechatid() {
		return responsechatid;
	}

	public void setResponsechatid(int responsechatid) {
		this.responsechatid = responsechatid;
	}

	public Timestamp getStartchattime() {
		return startchattime;
	}

	public void setStartchattime(Timestamp startchattime) {
		this.startchattime = startchattime;
	}

	public Timestamp getResponsechattime() {
		return responsechattime;
	}

	public void setResponsechattime(Timestamp responsechattime) {
		this.responsechattime = responsechattime;
	}

	public int getIsmanual() {
		return ismanual;
	}

	public void setIsmanual(int ismanual) {
		this.ismanual = ismanual;
	}
	
	
	
	

}
