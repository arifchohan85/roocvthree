package com.egeroo.roocvthree.rolewebroute;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_rolewebroute" )
public class RoleWebRoute extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int rolewebrouteid;
	
	@NotNull(message ="Role is a required field")
	private int roleid;
	
	@NotNull(message ="Route is a required field")
	private String route;
	
	@NotNull(message ="Menu List is a required field")
	private int menulistid;
	
	public int getRolewebrouteid() {
        return rolewebrouteid;
    }
	
	public void setRolewebrouteid(int Rolewebrouteid) {
        this.rolewebrouteid = Rolewebrouteid;
    }
	
	public int getRoleid() {
        return roleid;
    }
	
	public void setRoleid(int RoleID) {
        this.roleid = RoleID;
    }
	
	public String getRoute() {
        return route;
    }
	
	public void setRoute(String Route) {
        this.route = Route;
    }

	public int getMenulistid() {
		return menulistid;
	}

	public void setMenulistid(int menulistid) {
		this.menulistid = menulistid;
	}
	
	

}
