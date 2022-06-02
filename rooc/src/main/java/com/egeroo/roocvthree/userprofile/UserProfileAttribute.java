package com.egeroo.roocvthree.userprofile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "ms_app_userprofileattribute" )
public class UserProfileAttribute {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int userprofileattributeid;
	private int userprofileid;
	private int isapproved;
	public int getUserprofileattributeid() {
		return userprofileattributeid;
	}
	public void setUserprofileattributeid(int userprofileattributeid) {
		this.userprofileattributeid = userprofileattributeid;
	}
	public int getUserprofileid() {
		return userprofileid;
	}
	public void setUserprofileid(int userprofileid) {
		this.userprofileid = userprofileid;
	}
	public int getIsapproved() {
		return isapproved;
	}
	public void setIsapproved(int isapproved) {
		this.isapproved = isapproved;
	}
	
	

}
