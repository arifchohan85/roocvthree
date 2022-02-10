package com.egeroo.roocvthree.user;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.digest.DigestUtils;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_user" )
public class User extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int userid;
	
	@NotNull(message ="Role is a required field")
	private int roleid;
	
	@NotNull(message ="Username is a required field")
    //@Size(min=10, max=50)
	//@Email(message = "Please provide valid email address for username")
	private String username;
	
	//@NotNull(message ="Password is a required field")
    //@Size(min=8, max=255)
	private String password;
	//private Date createddate;
	//private Date updateddate;
    private int isactive;
    
    private int failedloginattempt;
	
	public int getUserid() {
        return userid;
    }
	
	public void setUserid(int UserID) {
        this.userid = UserID;
    }
	
	public int getRoleid() {
        return roleid;
    }
	
	public void setRoleid(int RoleID) {
        this.roleid = RoleID;
    }
	
	public String getUsername() {
        return username;
    }
	
	public void setUsername(String UserName) {
        this.username = UserName.toLowerCase();
    }
	
	public String getPassword() {
        return password;
    }
	
	public void setPassword(String Password) {
        this.password = DigestUtils.md5Hex(DigestUtils.md5Hex(Password));
    }
	
	public void setPasswordfromprofile(String Password) {
        this.password = Password;
    }
	
	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public int getFailedloginattempt() {
		return failedloginattempt;
	}

	public void setFailedloginattempt(int failedloginattempt) {
		this.failedloginattempt = failedloginattempt;
	}
	
	
	
}