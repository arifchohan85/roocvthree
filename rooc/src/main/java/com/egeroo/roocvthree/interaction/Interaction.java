package com.egeroo.roocvthree.interaction;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "tr_eng_interaction" )
public class Interaction extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
	private int interactionid;
    private int intentid;
    private String intentname;
	
	//@NotNull(message ="FaqIDStr is a required field")
	private String faqidstr;
	
	private int expectedintentid;
	private String expectedintentname;
	
	//@NotNull(message ="Question is a required field")
	private String question;
	
	private int minconfidence;
	private int maxconfidence;
	private int active;
	private int iretrespondid;
	private int isupdated;
	private int istrain;
	private double confidencelevel;
	private String qid;
	
	private int maxinteractionid;
	
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
	
	private String username;
	private String customername;
	private String customer;
	
	private int answerintentid;
	
	private String answerintentname;
	
	private int countalldata;
	
	private String messagetype;
	
	private int count;
	
	private String anyid;
	private int id;
	
	private String commitedby;
	
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
	
	
	public String getFaqidstr() {
        return faqidstr;
    }
	
	public void setFaqidstr(String faqidstr) {
        this.faqidstr = faqidstr;
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
	
	public double getConfidencelevel() {
        return confidencelevel;
    }
	
	public void setConfidencelevel(double confidencelevel) {
        this.confidencelevel = confidencelevel;
    }
	
	public String getQid() {
        return qid;
    }
	
	public void setQid(String qid) {
        this.qid = qid;
    }
	
	public int getMaxinteractionid() {
        return maxinteractionid;
    }
	
	public void setMaxinteractionid(int maxinteractionid) {
        this.maxinteractionid = maxinteractionid;
    }
	
	public String getChannel() {
        return channel;
    }
	
	public void setChannel(String Channel) {
        this.channel = Channel;
    }
	
	public String getAnswerby() {
        return answerby;
    }
	
	public void setAnswerby(String Answerby) {
        this.answerby = Answerby;
    }
	
	public String getIdcustomerchannel() {
        return idcustomerchannel;
    }
	
	public void setIdcustomerchannel(String Idcustomerchannel) {
        this.idcustomerchannel = Idcustomerchannel;
    }
	
	public int getRoomid() {
        return roomid;
    }
	
	public void setRoomid(int Roomid) {
        this.roomid = Roomid;
    }
	
	public int getChatid() {
        return chatid;
    }
	
	public void setChatid(int Chatid) {
        this.chatid = Chatid;
    }
	
	public int getUserchannelid() {
        return userchannelid;
    }
	
	public void setUserchannelid(int Userchannelid) {
        this.userchannelid = Userchannelid;
    }
	
	public int getCustomerchannelid() {
        return customerchannelid;
    }
	
	public void setCustomerchannelid(int Customerchannelid) {
        this.customerchannelid = Customerchannelid;
    }
	
	public int getResponsechatid() {
        return responsechatid;
    }
	
	public void setResponsechatid(int Responsechatid) {
        this.responsechatid = Responsechatid;
    }
	
	public Timestamp getStartchattime() {
        return startchattime;
    }
	
	public void setStartchattime(Timestamp Startchattime) {
        this.startchattime = Startchattime;
    }
	
	public Timestamp getResponsechattime() {
        return responsechattime;
    }
	
	public void setResponsechatid(Timestamp Responsechattime) {
        this.responsechattime = Responsechattime;
    }
	
	public int getIsmanual() {
        return ismanual;
    }
	
	public void setIsmanual(int Ismanual) {
        this.ismanual = Ismanual;
    }
	
	public String getUsername() {
        return username;
    }
	
	public void setUsername(String Username) {
        this.username = Username;
    }
	
	public String getCustomername() {
        return customername;
    }
	
	public void setCustomername(String Customername) {
        this.customername = Customername;
    }
	
	public String getCustomer() {
        return customer;
    }
	
	public void setCustomer(String Customer) {
        this.customer = Customer;
    }
	
	public int getAnswerintentid() {
        return answerintentid;
    }
	
	public void setAnswerintentid(int Answerintentid) {
        this.answerintentid = Answerintentid;
    }
	
	public String getAnswerintentname() {
        return answerintentname;
    }
	
	public void setAnswerintentname(String Answerintentname) {
        this.answerintentname = Answerintentname;
    }
	
	public int getCountalldata() {
        return countalldata;
    }
	
	public void setCountalldata(int Countalldata) {
        this.countalldata = Countalldata;
    }

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setResponsechattime(Timestamp responsechattime) {
		this.responsechattime = responsechattime;
	}

	public String getAnyid() {
		return anyid;
	}

	public void setAnyid(String anyid) {
		this.anyid = anyid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommitedby() {
		return commitedby;
	}

	public void setCommitedby(String commitedby) {
		this.commitedby = commitedby;
	}
	
	
	

}
