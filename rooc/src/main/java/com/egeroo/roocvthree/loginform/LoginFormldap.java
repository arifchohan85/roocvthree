package com.egeroo.roocvthree.loginform;

import javax.persistence.Entity;

import javax.persistence.Table;

import javax.validation.constraints.NotNull;



@Entity
@Table( name = "ms_app_user" )
public class LoginFormldap {
	
	
    private int userid;
	
	//@Email(message = "Please provide valid username")
	@NotNull(message ="Username is a required field")
	private String username;
	
	private String encpassword;
	
	private String password;
	
	public int getUserid() {
        return userid;
    }
	
	public void setUserid(int UserID) {
        this.userid = UserID;
    }
	
	public String getUsername() {
        return username;
    }
	
	public void setUsername(String UserName) {
        this.username = UserName;
    }
	
	public String getEncpassword() {
		return encpassword;
	}

	public void setEncpassword(String encpassword) {
		this.encpassword = encpassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
