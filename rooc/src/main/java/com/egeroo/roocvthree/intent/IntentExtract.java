package com.egeroo.roocvthree.intent;

import java.io.Serializable;

public class IntentExtract  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String question;
	
	private String answer;
	
	private String directorymap;
	
	private String nodeid;
	private String parentnodeid;
	
	private String type;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDirectorymap() {
		return directorymap;
	}

	public void setDirectorymap(String directorymap) {
		this.directorymap = directorymap;
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
	
	

}
