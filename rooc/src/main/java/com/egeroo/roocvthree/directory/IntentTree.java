package com.egeroo.roocvthree.directory;



public class IntentTree {
	
	/**
	 * 
	 */
	
	private String intentName;
	private String id;
	private String parent;
	private String previousId;
	private String type;
	private boolean multipleCondition;
	private int intentId;
	
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPreviousId() {
		return previousId;
	}
	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}
	public boolean getMultipleCondition() {
		return multipleCondition;
	}
	public void setMultipleCondition(boolean multipleCondition) {
		this.multipleCondition = multipleCondition;
	}
	public int getIntentId() {
		return intentId;
	}
	public void setIntentId(int intentId) {
		this.intentId = intentId;
	}
	
	
	

}
