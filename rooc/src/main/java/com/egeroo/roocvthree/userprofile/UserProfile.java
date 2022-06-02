package com.egeroo.roocvthree.userprofile;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;


@Entity
@Table( name = "ms_app_userprofile" )
public class UserProfile extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int userprofileid;
	private int userid;

	private int roleid;
	
	//@NotNull(message ="Username is a required field")
    //@Size(min=10, max=50)
	//@Email(message = "Please provide valid email address for username")
	private String username;
	
	//@NotNull(message ="Password is a required field")
    //@Size(min=8, max=255)
	private String password;
	
	//@NotNull
    //@Size(min=2, max=255)
	private String name;

	private String address;
	

	
	private String source;
	
	
	private int isactive;
	
	private String generatedpassword;
	
	//@Email(message = "Please provide valid email address")
	private String emailaddress;
	
	private int userchannelid;
	
	private String rolename;
	

	private int isapproved;

	
	public String getPassword() {
        return password;
    }
	
	public void setPassword(String Password) {
        //this.password = DigestUtils.md5Hex(DigestUtils.md5Hex(Password));
		this.password = Password;
    }
	
	public void setPasswordfromuser(String Password) {
        this.password = Password;
    }
	
	public String getName() {
        return name;
    }
	
	public void setName(String Name) {
        this.name = Name;
    }
	
	public String getAddress() {
        return address;
    }
	
	public void setAddress(String Address) {
        this.address = Address;
    }
	

	public int getUserchannelid() {
        return userchannelid;
    }
	
	public void setUserchannelid(int Userchannelid) {
        this.userchannelid = Userchannelid;
    }



	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getGeneratedpassword() {
		return generatedpassword;
	}

	public void setGeneratedpassword(String generatedpassword) {
		this.generatedpassword = generatedpassword;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public int getUserprofileid() {
		return userprofileid;
	}

	public void setUserprofileid(int userprofileid) {
		this.userprofileid = userprofileid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public int getIsapproved() {
		return isapproved;
	}

	public void setIsapproved(int isapproved) {
		this.isapproved = isapproved;
	}
	
	
	
}