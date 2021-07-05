package com.egeroo.roocvthree.intent;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "tr_eng_maxintent" )
public class MaxIntent extends Base {
	
	private int intentid;
	private int recordid;
	
	public int getIntentid() {
        return intentid;
    }
	
	public void setIntentid(int intentid) {
        this.intentid = intentid;
    }
	
	public int getRecordid() {
        return recordid;
    }
	
	public void setRecordid(int Recordid) {
        this.recordid = Recordid;
    }

}
