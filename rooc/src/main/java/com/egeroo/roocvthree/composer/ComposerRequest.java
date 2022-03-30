package com.egeroo.roocvthree.composer;

import java.io.Serializable;

public class ComposerRequest  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private ComposerDataRequest data;
	private int intentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ComposerDataRequest getData() {
		return data;
	}
	public void setData(ComposerDataRequest data) {
		this.data = data;
	}
	public int getIntentId() {
		return intentId;
	}
	public void setIntentId(int intentId) {
		this.intentId = intentId;
	}
	
	

}
