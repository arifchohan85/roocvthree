package com.egeroo.roocvthree.userrole;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "ms_app_userrole" )
public class UserRole implements Serializable {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int roleid;
	
	@Column(name = "rolename", length = 255, nullable = false)
	private String rolename;
	 
    public int getRoleid() {
        return roleid;
    }
    
    public void setRoleid(int RoleID) {
        this.roleid = RoleID;
    }
    
    public String getRolename() {
        return rolename;
    }
    
    public void setRolename(String RoleName) {
        this.rolename = RoleName;
    }
    
    
    
    

}
