package com.egeroo.roocvthree.answer;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Answer implements Serializable{
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
