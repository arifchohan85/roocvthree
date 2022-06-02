package com.egeroo.roocvthree.interaction;

import java.io.Serializable;

public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int questionid;
	private String question;
	private int intentid;
	private int hasdetail;
	
	public int getQuestionid() {
		return questionid;
	}
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getIntentid() {
		return intentid;
	}
	public void setIntentid(int intentid) {
		this.intentid = intentid;
	}
	public int getHasdetail() {
		return hasdetail;
	}
	public void setHasdetail(int hasdetail) {
		this.hasdetail = hasdetail;
	}
	
	

}
