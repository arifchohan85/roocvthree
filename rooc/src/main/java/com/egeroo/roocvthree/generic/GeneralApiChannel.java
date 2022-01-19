package com.egeroo.roocvthree.generic;

import java.io.Serializable;

public class GeneralApiChannel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String keyvalue;
	private String params;
	private String methodvalue;
	private String urlvalue;
	public String getKeyvalue() {
		return keyvalue;
	}
	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getMethodvalue() {
		return methodvalue;
	}
	public void setMethodvalue(String methodvalue) {
		this.methodvalue = methodvalue;
	}
	public String getUrlvalue() {
		return urlvalue;
	}
	public void setUrlvalue(String urlvalue) {
		this.urlvalue = urlvalue;
	}
	
	
}
