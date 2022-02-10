package com.egeroo.roocvthree.knowledge;

import java.io.Serializable;

public class KnowledgeRequest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String intentName;
	private String folderName;
	private boolean multipleCondition;
	private String parentId;
	private String previousId;
	private String type;
	private String parent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntentName() {
		return intentName;
	}
	public void setIntentName(String IntentName) {
		this.intentName = IntentName;
	}
	public boolean isMultipleCondition() {
		return multipleCondition;
	}
	public void setMultipleCondition(boolean multipleCondition) {
		this.multipleCondition = multipleCondition;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPreviousId() {
		return previousId;
	}
	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	

}
