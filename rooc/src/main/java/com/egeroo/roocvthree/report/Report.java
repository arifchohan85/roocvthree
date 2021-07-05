package com.egeroo.roocvthree.report;


import java.util.Date;

import com.egeroo.roocvthree.core.base.Base;



public class Report extends Base {
	
	private Date datefrom;
	private Date dateto;
	private String username;
	
	private Date logindate;
	private Date expiredate;
	
	private String authkey;
	private String deviceid;
	private int isactive;
	
	private String localaddr;
	private String remoteaddr;
	
	private String question;
	private String answer;
	
	private String dirname;
	
	private int interactionid;
	private String intentname;
	private int answerbybot;
	private int answerbyagent;
	private int notanswer;
	private int answerintentid;
	
	private String categoryname;
	
	//private Date periode;
	private String periode;
	private int totalanswer;
	private int totalanswerbybot;
	private int totalanswerbyagent;
	private int totalnotanswer;
	
	private String channel;
	
	private String channelparam;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLogindate() {
		return logindate;
	}
	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}
	public Date getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}
	public String getAuthkey() {
		return authkey;
	}
	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getIsactive() {
		return isactive;
	}
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}
	public String getLocaladdr() {
		return localaddr;
	}
	public void setLocaladdr(String localaddr) {
		this.localaddr = localaddr;
	}
	public String getRemoteaddr() {
		return remoteaddr;
	}
	public void setRemoteaddr(String remoteaddr) {
		this.remoteaddr = remoteaddr;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getDirname() {
		return dirname;
	}
	public void setDirname(String dirname) {
		this.dirname = dirname;
	}
	public int getInteractionid() {
		return interactionid;
	}
	public void setInteractionid(int interactionid) {
		this.interactionid = interactionid;
	}
	public String getIntentname() {
		return intentname;
	}
	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}
	public int getAnswerbybot() {
		return answerbybot;
	}
	public void setAnswerbybot(int answerbybot) {
		this.answerbybot = answerbybot;
	}
	public int getAnswerbyagent() {
		return answerbyagent;
	}
	public void setAnswerbyagent(int answerbyagent) {
		this.answerbyagent = answerbyagent;
	}
	public int getNotanswer() {
		return notanswer;
	}
	public void setNotanswer(int notanswer) {
		this.notanswer = notanswer;
	}
	public int getAnswerintentid() {
		return answerintentid;
	}
	public void setAnswerintentid(int answerintentid) {
		this.answerintentid = answerintentid;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public int getTotalanswer() {
		return totalanswer;
	}
	public void setTotalanswer(int totalanswer) {
		this.totalanswer = totalanswer;
	}
	public int getTotalanswerbybot() {
		return totalanswerbybot;
	}
	public void setTotalanswerbybot(int totalanswerbybot) {
		this.totalanswerbybot = totalanswerbybot;
	}
	public int getTotalanswerbyagent() {
		return totalanswerbyagent;
	}
	public void setTotalanswerbyagent(int totalanswerbyagent) {
		this.totalanswerbyagent = totalanswerbyagent;
	}
	public int getTotalnotanswer() {
		return totalnotanswer;
	}
	public void setTotalnotanswer(int totalnotanswer) {
		this.totalnotanswer = totalnotanswer;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelparam() {
		return channelparam;
	}
	public void setChannelparam(String channelparam) {
		this.channelparam = channelparam;
	}
	
	
	
	

}
