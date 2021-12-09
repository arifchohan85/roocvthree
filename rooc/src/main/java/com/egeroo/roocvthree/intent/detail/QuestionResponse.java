package com.egeroo.roocvthree.intent.detail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QuestionResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String question;
	private List<MentionResponse> mentions;
	private Date createdOn;
	private String createdBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<MentionResponse> getMentions() {
		return mentions;
	}
	public void setMentions(List<MentionResponse> mentions) {
		this.mentions = mentions;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
