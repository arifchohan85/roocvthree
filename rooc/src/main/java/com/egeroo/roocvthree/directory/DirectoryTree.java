package com.egeroo.roocvthree.directory;


public class DirectoryTree{
	
	/**
	 * 
	 */
	
	private String folderName;
	private String id;
	private String parent;
	private String previousId;
	private String type;
	private int folderId;
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPreviousId() {
		return previousId;
	}
	public void setPreviousId(String previousId) {
		this.previousId = previousId;
	}
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	
	
	

}
