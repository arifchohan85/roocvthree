package com.egeroo.roocvthree.core.usersource;

import java.sql.Timestamp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "ms_app_usersource" )
public class UserSource {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int usersourceid;
	
	@NotNull(message ="UserID is a required field")
	private int userid;
	
	private Timestamp logindate;
	private Timestamp expiredate;
	
	@NotNull(message ="AuthKey is a required field")
	private String authkey;
	
	@NotNull(message ="DeviceID is a required field")
	private String deviceid;
	
	@NotNull(message ="isActive is a required field")
	private int isactive;
	
	private String localaddr;
	private String remoteaddr;
	private String username;
	
	private Timestamp logouttime;
	
	public int getUsersourceid() {
        return usersourceid;
    }
	
	public void setUsersourceid(int UsersourceID) {
        this.usersourceid = UsersourceID;
    }
	
	public int getUserid() {
        return userid;
    }
	
	public void setUserid(int UserID) {
        this.userid = UserID;
    }
	
	public Timestamp getLogindate() {
        return logindate;
    }
	
	public void setLogindate(Timestamp Logindate) {
        this.logindate = Logindate;
    }
	
	public Timestamp getExpiredate() {
        return expiredate;
    }
	
	public void setExpiredate(Timestamp Expiredate) {
        this.expiredate = Expiredate;
    }
	
	public String getAuthkey() {
        return authkey;
    }
	
	public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }
	
	public String getDeviceid() {
        return deviceid;
    }
	
	public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
	
	public int getIsactive() {
        return isactive;
    }
	
	public void setIsactive(int isactive) {
        this.isactive = isactive;
    }

	public String getLocaladdr() {
		return localaddr;
	}

	public void setLocaladdr(String localaddr) {
		this.localaddr = localaddr;
	}

	public String getRemoteaddr() {
		return remoteaddr;
	}

	public void setRemoteaddr(String remoteaddr) {
		this.remoteaddr = remoteaddr;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getLogouttime() {
		return logouttime;
	}

	public void setLogouttime(Timestamp logouttime) {
		this.logouttime = logouttime;
	}
	
	
	

}
