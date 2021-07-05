package com.egeroo.roocvthree.generic;

import java.io.Serializable;
/**
 * 
 * @author vebbi
 *
 */
public class RequestMappingDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String keys;
	private String keysValue;
	private boolean parent;
	private int parentId;
	private int tagLevel;
	private int tagOrder;
	private String dataType;
	private String dataValue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getKeysValue() {
		return keysValue;
	}
	public void setKeysValue(String keysValue) {
		this.keysValue = keysValue;
	}
	public boolean isParent() {
		return parent;
	}
	public void setParent(boolean parent) {
		this.parent = parent;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getTagLevel() {
		return tagLevel;
	}
	public void setTagLevel(int tagLevel) {
		this.tagLevel = tagLevel;
	}
	public int getTagOrder() {
		return tagOrder;
	}
	public void setTagOrder(int tagOrder) {
		this.tagOrder = tagOrder;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
