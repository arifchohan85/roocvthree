package com.egeroo.roocvthree.menulist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_app_menulist" )
public class Menulist  extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int menulistid;
	
	private int parentid;
	
	@NotNull(message ="Title is a required field")
	private String title;
	
	@NotNull(message ="Icon is a required field")
	private String icon;
	
	//@NotNull(message ="Route is a required field")
	private String route;
	
	@NotNull(message ="Sort is a required field")
	//@Size(min=1)
	private int sort;
	
	@OneToMany(mappedBy = "parentid", cascade = CascadeType.REMOVE , orphanRemoval = true)
	//@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Menulist> child = new ArrayList<Menulist>();
	
	private String items;
	private String parentname;
	
	public List<Menulist> getChild() {
        return child;
    }
	
	public void setChild(List<Menulist> Child) {
        this.child = Child;
    }
	
	public int getMenulistid() {
        return menulistid;
    }
	
	public void setMenulistid(int Menulistid) {
        this.menulistid = Menulistid;
    }
	
	public int getParentid() {
        return parentid;
    }
	
	public void setParentid(int Parentid) {
        this.parentid = Parentid;
    }
	
	public String getTitle() {
        return title;
    }
	
	public void setTitle(String Title) {
        this.title = Title;
    }
	
	public String getIcon() {
        return icon;
    }
	
	public void setIcon(String Icon) {
        this.icon = Icon;
    }
	
	public String getRoute() {
        return route;
    }
	
	public void setRoute(String Route) {
        this.route = Route;
    }
	
	public int getSort() {
        return sort;
    }
	
	public void setSort(int Sort) {
        this.sort = Sort;
    }
	
	public String getItems() {
        return items;
    }
	
	public void setItems(String Items) {
        this.items = Items;
    }
	
	public String getParentname() {
        return parentname;
    }
	
	public void setParentname(String Parentname) {
        this.parentname = Parentname;
    }

}
