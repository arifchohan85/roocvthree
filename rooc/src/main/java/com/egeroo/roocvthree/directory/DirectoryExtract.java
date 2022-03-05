package com.egeroo.roocvthree.directory;

import java.io.Serializable;






public class DirectoryExtract implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String name;
	private String description;
	
	
	
	
	private String path;
	private String directorymap;
	
	private String nodeid;
	private String parentnodeid;
	
	private String type;
	
	
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
	
	public void setDirectorymap(String Directorymap) {
        this.directorymap = Directorymap;
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirectorymap() {
		return directorymap;
	}
	


	
	
	
}

