package com.egeroo.roocvthree.interaction;

import java.io.Serializable;

public class InteractionRequest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int intentId;
	private String question;
	
	
	
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
	
	
	

}
