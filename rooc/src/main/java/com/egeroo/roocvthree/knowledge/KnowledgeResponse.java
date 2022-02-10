package com.egeroo.roocvthree.knowledge;

import java.io.Serializable;

public class KnowledgeResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private int knowledgeId;
	private int intentId;
	private int folderId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(int knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public int getIntentId() {
		return intentId;
	}
	public void setIntentId(int intentId) {
		this.intentId = intentId;
	}
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	
	

}
