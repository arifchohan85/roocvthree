package com.egeroo.roocvthree.trailingrecord;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table( name = "ms_app_trailingrecord" )
public class TrailingRecord {

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int trailingrecordid;
	
	@NotNull(message ="Created By is a required field")
	private String createdBy;
	
	@NotNull(message ="RecordID is a required field")
	private int recordid;
	
	@NotNull(message ="Class Name is a required field")
    @Size(max=255)
	private String classname;
	
	@NotNull(message ="Action For is a required field")
    @Size(max=10)
	private String actionfor;
	
	private Timestamp createddate;
	
	private String requestbody;
	private String requestquerystring;
	
	private String username;
	
	public int getTrailingrecordid() {
		return this.trailingrecordid;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setTrailingrecordid(int trailingrecordid) {
		this.trailingrecordid = trailingrecordid;
	}
	
	public String getCreatedby() {
		return this.createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedby(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public int getRecordid() {
		return this.recordid;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}
	
	public String getClassname() {
		return this.classname;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public String getActionfor() {
		return this.actionfor;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setActionfor(String actionfor) {
		this.actionfor = actionfor;
	}
	
	public Timestamp getCreateddate() {
		return this.createddate;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	
	public String getRequestbody() {
		return requestbody;
	}
	public void setRequestbody(String requestbody) {
		this.requestbody = requestbody;
	}
	public String getRequestquerystring() {
		return requestquerystring;
	}
	public void setRequestquerystring(String requestquerystring) {
		this.requestquerystring = requestquerystring;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
}
