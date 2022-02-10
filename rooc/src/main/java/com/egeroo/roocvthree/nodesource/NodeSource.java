package com.egeroo.roocvthree.nodesource;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_eng_nodesource" )
public class NodeSource extends Base {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nodesourceid;
	private String type;
	private int directoryid;
	private int intentid;
	private String nodeid;
	private String parentnodeid;
	private String previousnodeid;
	public int getNodesourceid() {
		return nodesourceid;
	}
	public void setNodesourceid(int nodesourceid) {
		this.nodesourceid = nodesourceid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDirectoryid() {
		return directoryid;
	}
	public void setDirectoryid(int directoryid) {
		this.directoryid = directoryid;
	}
	public int getIntentid() {
		return intentid;
	}
	public void setIntentid(int intentid) {
		this.intentid = intentid;
	}
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getParentnodeid() {
		return parentnodeid;
	}
	public void setParentnodeid(String parentnodeid) {
		this.parentnodeid = parentnodeid;
	}
	public String getPreviousnodeid() {
		return previousnodeid;
	}
	public void setPreviousnodeid(String previousnodeid) {
		this.previousnodeid = previousnodeid;
	}
	
	
	

}
