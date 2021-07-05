package com.egeroo.roocvthree.interaction;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_eng_intent" )
public class Training  extends Base{
	
	private int intentid;
	private int directoryid;
	
	public int getIntentid() {
        return intentid;
    }
	
	public void setIntentid(int intentid) {
        this.intentid = intentid;
    }
	
	public int getDirectoryid() {
        return directoryid;
    }
	
	public void setDirectoryid(int Directoryid) {
        this.directoryid = Directoryid;
    }

}
