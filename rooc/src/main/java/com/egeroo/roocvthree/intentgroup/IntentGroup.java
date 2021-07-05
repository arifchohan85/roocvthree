package com.egeroo.roocvthree.intentgroup;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "ms_eng_intentgroup" )
public class IntentGroup  implements Serializable  {
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int intentgroupid;
	
	@Column(name = "intentgroupname", length = 255, nullable = false)
	private String intentgroupname;
	
	public int getIntentgroupid() {
        return intentgroupid;
    }
    
    public void setIntentgroupid(int Intentgroupid) {
        this.intentgroupid = Intentgroupid;
    }
    
    public String getIntentgroupname() {
        return intentgroupname;
    }
    
    public void setIntentgroupname(String Intentgroupname) {
        this.intentgroupname = Intentgroupname;
    }

}
