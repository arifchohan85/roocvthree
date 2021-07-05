package com.egeroo.roocvthree.loginform;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



@Entity
@Table( name = "ms_app_user" )
public class ChangePassword {
	
	private int userid;
	
	@NotNull(message ="Old Password is a required field")
	private String oldpassword;
	
	@NotNull(message ="New Password is a required field")
	private String newpassword;
	
	@NotNull(message ="Re Password is a required field")
	private String repassword;
	
	public int getUserid() {
        return userid;
    }
	
	public void setUserid(int UserID) {
        this.userid = UserID;
    }
	
	public String getOldpassword() {
        return oldpassword;
    }
	
	public void setOldpassword(String Oldpassword) {
        this.oldpassword = Oldpassword;
    }
	
	public String getNewpassword() {
        return newpassword;
    }
	
	public void setNewpassword(String Newpassword) {
        this.newpassword = Newpassword;
    }
	
	public String getRepassword() {
        return repassword;
    }
	
	public void setRepassword(String Repassword) {
        this.repassword = Repassword;
    }

}
