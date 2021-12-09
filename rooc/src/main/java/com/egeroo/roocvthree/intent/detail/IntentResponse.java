package com.egeroo.roocvthree.intent.detail;

import java.io.Serializable;
import java.util.List;

public class IntentResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String id;
	private String intentName;
	private String subText;
	private int sortId;
	private boolean multipleCondition;
	private List<QuestionResponse> questions;
	private List<OutputResponse> output;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}
	public String getSubText() {
		return subText;
	}
	public void setSubText(String subText) {
		this.subText = subText;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	public boolean isMultipleCondition() {
		return multipleCondition;
	}
	public void setMultipleCondition(boolean multipleCondition) {
		this.multipleCondition = multipleCondition;
	}
	public List<QuestionResponse> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuestionResponse> questions) {
		this.questions = questions;
	}
	public List<OutputResponse> getOutput() {
		return output;
	}
	public void setOutput(List<OutputResponse> output) {
		this.output = output;
	}
	
}
