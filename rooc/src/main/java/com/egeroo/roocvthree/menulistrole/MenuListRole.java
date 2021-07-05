package com.egeroo.roocvthree.menulistrole;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_menulistrole" )
public class MenuListRole  extends Base {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int menulistroleid;
	
	@NotNull(message ="Role is a required field")
	private int roleid;
	
	@NotNull(message ="Menu List is a required field")
	private int menulistid;
	
	private String title;
	private String rolename;
	
	
	public int getMenulistroleid() {
        return menulistroleid;
    }
	
	public void setMenulistroleid(int Menulistroleid) {
        this.menulistroleid = Menulistroleid;
    }
	
	public int getRoleid() {
        return roleid;
    }
	
	public void setRoleid(int RoleID) {
        this.roleid = RoleID;
    }
	
	public int getMenulistid() {
        return menulistid;
    }
	
	public void setMenulistid(int Menulistid) {
        this.menulistid = Menulistid;
    }
	
	public String getTitle() {
        return title;
    }
	
	public void setTitle(String Title) {
        this.title = Title;
    }
	
	public String getRolename() {
        return rolename;
    }
    
    public void setRolename(String RoleName) {
        this.rolename = RoleName;
    }

}
