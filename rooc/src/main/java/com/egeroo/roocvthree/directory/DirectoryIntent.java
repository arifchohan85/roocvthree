package com.egeroo.roocvthree.directory;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table( name = "ms_eng_directory" )
public class DirectoryIntent {
	
	private int theId;
	private String title;
	private int parentId;
	private String type;
	private String categorymode;
	private String switching;
	private int intentid;
	private int switchingid;
	private int grandparentid;
	private String faq;
	
	public int getTheId() {
        return theId;
    }
	
	public void setTheId(int TheId) {
        this.theId = TheId;
    }
	
	public String getTitle() {
        return title;
    }
	
	public void setTitle(String Title) {
        this.title = Title;
    }
	
	public int getParentId() {
        return parentId;
    }
	
	public void setParentId(int ParentId) {
        this.parentId = ParentId;
    }
	
	public String getType() {
        return type;
    }
	
	public void setType(String Type) {
        this.type = Type;
    }
	
	public String getCategorymode() {
        return categorymode;
    }
	
	public void setCategorymode(String Categorymode) {
        this.categorymode = Categorymode;
    }
	
	public String getSwitching() {
        return switching;
    }
	
	public void setSwitching(String Switching) {
        this.switching = Switching;
    }
	
	public int getIntentid() {
        return intentid;
    }
	
	public void setIntentid(int Intentid) {
        this.intentid = Intentid;
    }
	
	public int getSwitchingid() {
        return switchingid;
    }
	
	public void setSwitchingid(int Switchingid) {
        this.switchingid = Switchingid;
    }

	public int getGrandparentid() {
		return grandparentid;
	}

	public void setGrandparentid(int Grandparentid) {
		this.grandparentid = Grandparentid;
	}
	
	public String getFaq() {
        return faq;
    }
	
	public void setFaq(String faq) {
        this.faq = faq;
    }
	

}
