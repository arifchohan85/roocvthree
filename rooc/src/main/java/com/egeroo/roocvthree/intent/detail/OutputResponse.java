package com.egeroo.roocvthree.intent.detail;

import java.io.Serializable;
import java.util.List;

public class OutputResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<ConditionResponse> condition;
	private List<Response> response;
	public List<ConditionResponse> getCondition() {
		return condition;
	}
	public void setCondition(List<ConditionResponse> condition) {
		this.condition = condition;
	}
	public List<Response> getResponse() {
		return response;
	}
	public void setResponse(List<Response> response) {
		this.response = response;
	}
	
}
