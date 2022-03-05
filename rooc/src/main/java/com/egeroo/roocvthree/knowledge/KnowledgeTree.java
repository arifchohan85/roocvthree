package com.egeroo.roocvthree.knowledge;

import java.io.Serializable;
import java.util.List;

import com.egeroo.roocvthree.directory.DirectoryTree;
import com.egeroo.roocvthree.directory.IntentTree;

public class KnowledgeTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<DirectoryTree> dirtree;
	List<IntentTree> intenttree;
	public List<DirectoryTree> getDirtree() {
		return dirtree;
	}
	public void setDirtree(List<DirectoryTree> dirtree) {
		this.dirtree = dirtree;
	}
	public List<IntentTree> getIntenttree() {
		return intenttree;
	}
	public void setIntenttree(List<IntentTree> intenttree) {
		this.intenttree = intenttree;
	}
	
	
}
