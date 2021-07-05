package com.egeroo.roocvthree.roleapiroute;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_roleapiroute" )
public class RoleApiRoute extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int roleapirouteid;
	
	@NotNull(message ="Role is a required field")
	private int roleid;
	
	@NotNull(message ="Route is a required field")
	private String route;
	
	public int getRoleapirouteid() {
        return roleapirouteid;
    }
	
	public void setRoleapirouteid(int Roleapirouteid) {
        this.roleapirouteid = Roleapirouteid;
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

}
