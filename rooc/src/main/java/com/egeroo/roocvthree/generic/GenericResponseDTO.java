package com.egeroo.roocvthree.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * @author vebbi
 *
 */

public class GenericResponseDTO implements Serializable{
	private String key;
	private Map<String, Object> listField;
	private List<Map<String, Object>> listData;
	private String status;
	private String errorMessage;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public Map<String, Object> getListField() {
		return listField;
	}
	public void setListField(Map<String, Object> listField) {
		this.listField = listField;
	}
	
	public List<Map<String, Object>> getListData() {
		return listData;
	}
	public void setListData(List<Map<String, Object>> listData) {
		this.listData = listData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

