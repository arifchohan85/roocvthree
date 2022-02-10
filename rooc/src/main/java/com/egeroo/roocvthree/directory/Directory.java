package com.egeroo.roocvthree.directory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_eng_directory" )
public class Directory extends Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int directoryid;
	
	@NotNull(message ="Parent is a required field")
	private int parentid;
	
	
	
	@NotNull(message ="Name is a required field")
	private String name;
	
	@NotNull(message ="Description is a required field")
	private String description;
	
	
	
	private String directoryname;
	private String parentname;
	
	private String directorymap;
	
	private String updatekey;
	
	private int countchild;
	
	private int reticategoryid;
	
	private String categorymode;
	
	
	public int getDirectoryid() {
        return directoryid;
    }
	
	public void setDirectoryid(int directoryid) {
        this.directoryid = directoryid;
    }
	
	public int getParentid() {
        return parentid;
    }
	
	public void setParentid(int parentid) {
        this.parentid = parentid;
    }
	
	
	
	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
        this.description = description;
    }
	
	
	
	public String getDirectoryname() {
        return directoryname;
    }
	
	public void setDirectoryname(String Directoryname) {
        this.directoryname = Directoryname;
    }
	
	public String getParentname() {
        return parentname;
    }
	
	public void setParentname(String Parentname) {
        this.parentname = Parentname;
    }
	
	public String getDirectorymap() {
        return directorymap;
    }
	
	public void setDirectorymap(String Directorymap) {
        this.directorymap = Directorymap;
    }
	
	public String getUpdatekey() {
        return updatekey;
    }
	
	public void setUpdatekey(String Updatekey) {
        this.updatekey = Updatekey;
    }
	
	public int getCountchild() {
        return countchild;
    }
	
	public void setCountchild(int Countchild) {
        this.countchild = Countchild;
    }

	public int getReticategoryid() {
		return reticategoryid;
	}

	public void setReticategoryid(int reticategoryid) {
		this.reticategoryid = reticategoryid;
	}

	public String getCategorymode() {
		return categorymode;
	}

	public void setCategorymode(String categorymode) {
		this.categorymode = categorymode;
	}
	
	
	
	
}
