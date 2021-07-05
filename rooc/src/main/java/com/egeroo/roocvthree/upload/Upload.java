package com.egeroo.roocvthree.upload;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_upload" )
public class Upload extends Base  {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int uploadid;
	
	//@NotNull(message ="Parent is a required field")
	private String uploadfor;
	private String uploadfile;
	private String description;
	private int success;
	private int failed;
	private String username;
	//private String createdBy;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUploadid() {
        return uploadid;
    }
	
	public void setUploadid(int Uploadid) {
        this.uploadid = Uploadid;
    }
	
	public String getUploadfor() {
        return uploadfor;
    }
	
	public void setUploadfor(String Uploadfor) {
        this.uploadfor = Uploadfor;
    }
	
	public String getUploadfile() {
        return uploadfile;
    }
	
	public void setUploadfile(String Uploadfile) {
        this.uploadfile = Uploadfile;
    }
	
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String Description) {
        this.description = Description;
    }
	
	public int getSuccess() {
        return success;
    }
	
	public void setSuccess(int Success) {
        this.success = Success;
    }
	
	public int getFailed() {
        return failed;
    }
	
	public void setFailed(int Failed) {
        this.failed = Failed;
    }

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/
	
	
	
}
