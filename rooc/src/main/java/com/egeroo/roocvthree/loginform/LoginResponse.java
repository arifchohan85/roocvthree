package com.egeroo.roocvthree.loginform;

import java.io.Serializable;

public class LoginResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String accessToken;
	private String errorMessage;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
