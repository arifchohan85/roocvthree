package com.egeroo.roocvthree.interaction;

import java.io.Serializable;
import java.util.List;



public class InteractionPagingPost implements Serializable{
	

	private String select;
	private List<InteractionPagingPost> where;
	private List<InteractionPagingPost> sort;
	
	private String field;
	private String comparator;
	private String value;
	private String value2;
	private String direction; 
	
	private int page;
	
	private int pagesize;
	

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	/*
	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}*/

	public List<InteractionPagingPost> getSort() {
		return sort;
	}

	public void setSort(List<InteractionPagingPost> sort) {
		this.sort = sort;
	}

	public List<InteractionPagingPost> getWhere() {
		return where;
	}

	public void setWhere(List<InteractionPagingPost> where) {
		this.where = where;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getComparator() {
		return comparator;
	}
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	
	
	
	
}
