package com.egeroo.roocvthree.directory;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "ms_eng_directory" )
public class Directorylistall {
	private String children;
	
	public String getChildren() {
        return children;
    }
	
	public void setChildren(String Children) {
        this.children = Children;
    }

}
