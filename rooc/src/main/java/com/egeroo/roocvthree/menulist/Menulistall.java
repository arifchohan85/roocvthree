package com.egeroo.roocvthree.menulist;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "ms_app_menulist" )
public class Menulistall {

	private String items;
	
	public String getItems() {
        return items;
    }
	
	public void setItems(String Items) {
        this.items = Items;
    }
}
