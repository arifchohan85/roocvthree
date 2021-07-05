package com.egeroo.roocvthree.roocconfig;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_cfg_roocconfig" )
public class RoocConfig  extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int roocconfigid;
	private String configkey;
	private String configvalue;
	public int getRoocconfigid() {
		return roocconfigid;
	}
	public void setRoocconfigid(int roocconfigid) {
		this.roocconfigid = roocconfigid;
	}
	public String getConfigkey() {
		return configkey;
	}
	public void setConfigkey(String configkey) {
		this.configkey = configkey;
	}
	public String getConfigvalue() {
		return configvalue;
	}
	public void setConfigvalue(String configvalue) {
		this.configvalue = configvalue;
	}
	
	

}
