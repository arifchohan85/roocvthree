package com.egeroo.roocvthree.directory;

import java.io.Serializable;





public class DirectoryIntent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String parent;
	private String previousId;
	private String type;
	private boolean multipleCondition;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public boolean isMultipleCondition() {
		return multipleCondition;
	}
	public void setMultipleCondition(boolean multipleCondition) {
		this.multipleCondition = multipleCondition;
	}

	

}
