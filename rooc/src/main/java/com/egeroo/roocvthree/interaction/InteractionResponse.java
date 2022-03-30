package com.egeroo.roocvthree.interaction;

import java.io.Serializable;
import java.sql.Timestamp;

public class InteractionResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int intentId;
	private String question;
	private Timestamp createdOn;
	private String createdBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIntentId() {
		return intentId;
	}
	public void setIntentId(int intentId) {
		this.intentId = intentId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
}
