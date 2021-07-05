package com.egeroo.roocvthree.core.base;

import java.io.Serializable;
import java.sql.Timestamp;


@SuppressWarnings("serial")
public class Base implements Serializable{

	private Timestamp createdTime;
	private int createdBy;
	private Timestamp updatedTime;
	private int updateBy;
	/*private String authoriser;
	private Timestamp authoriserTime;
	private String status;
	private int deleted;
	private String token;*/
	/**
	 * @return the createdTime
	 */
	public Timestamp getCreatedtime() {
		return this.createdTime;
	}
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedtime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * @return the createdBy
	 */
	public int getCreatedby() {
		return this.createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedby(int createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedTime
	 */
	public Timestamp getUpdatedtime() {
		return this.updatedTime;
	}
	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedtime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
	/**
	 * @return the updateBy
	 */
	public int getUpdatedby() {
		return this.updateBy;
	}
	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdatedby(int updateBy) {
		this.updateBy = updateBy;
	}

	/*public String getAuthoriser() {
		return this.authoriser;
	}

	public void setAuthoriser(String authoriser) {
		this.authoriser = authoriser;
	}

	public Timestamp getAuthoriserTime() {
		return this.authoriserTime;
	}

	public void setAuthoriserTime(Timestamp authoriserTime) {
		this.authoriserTime = authoriserTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}*/
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/*public String getToken() {
		return this.token;
	}

	public void setToken(String Token) {
		this.token = Token;
	}*/

}
