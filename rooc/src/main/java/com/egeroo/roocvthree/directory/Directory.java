package com.egeroo.roocvthree.directory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_eng_directory" )
public class Directory extends Base {
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int directoryid;
	
	@NotNull(message ="Parent is a required field")
	private int parentid;
	
	@NotNull(message ="Category Mode is a required field")
	private String categorymode;
	
	@NotNull(message ="Name is a required field")
	private String name;
	
	@NotNull(message ="Description is a required field")
	private String description;
	
	private String question;
	private String answer;
	private String faq;
	private String switching;
	private int extendenumcategoryid;
	private int tenantid;
	private int reticategoryid;
	private int active;
	private int countparent;
	
	private String directoryname;
	private String parentname;
	
	private String directorymap;
	
	private String updatekey;
	
	private int countchild;
	
	private int intentid;
	private int switchingid;
	
	private int sentimentid;
	private int intentgroupid;
	
	private String switchingname;
	private String intentname;
	
	private int retvoiceid;
	private int isvoicegenerated;
	
	private int retmlid;
	private int ismlgenerated;
	private int intentparentid;

	private String token;
	
	public int getDirectoryid() {
        return directoryid;
    }
	
	public void setDirectoryid(int directoryid) {
        this.directoryid = directoryid;
    }
	
	public int getParentid() {
        return parentid;
    }
	
	public void setParentid(int parentid) {
        this.parentid = parentid;
    }
	
	public String getCategorymode() {
        return categorymode;
    }
	
	public void setCategorymode(String categorymode) {
        this.categorymode = categorymode;
    }
	
	public String getName() {
        return name;
    }
	
	public void setName(String name) {
        this.name = name;
    }
	
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String description) {
        this.description = description;
    }
	
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
	
	public String getFaq() {
        return faq;
    }
	
	public void setFaq(String faq) {
        this.faq = faq;
    }
	
	public String getSwitching() {
        return switching;
    }
	
	public void setSwitching(String switching) {
        this.switching = switching;
    }
	
	public int getExtendenumcategoryid() {
        return extendenumcategoryid;
    }
	
	public void setExtendenumcategoryid(int extendenumcategoryid) {
        this.extendenumcategoryid = extendenumcategoryid;
    }
	
	public int getTenantid() {
        return tenantid;
    }
	
	public void setTenantid(int tenantid) {
        this.tenantid = tenantid;
    }
	
	public int getReticategoryid() {
        return reticategoryid;
    }
	
	public void setReticategoryid(int reticategoryid) {
        this.reticategoryid = reticategoryid;
    }
	
	public int getActive() {
        return active;
    }
	
	public void setActive(int active) {
        this.active = active;
    }
	
	public int getCountparent() {
        return countparent;
    }
	
	public void setCountparent(int Countparent) {
        this.countparent = Countparent;
    }
	
	public String getDirectoryname() {
        return directoryname;
    }
	
	public void setDirectoryname(String Directoryname) {
        this.directoryname = Directoryname;
    }
	
	public String getParentname() {
        return parentname;
    }
	
	public void setParentname(String Parentname) {
        this.parentname = Parentname;
    }
	
	public String getDirectorymap() {
        return directorymap;
    }
	
	public void setDirectorymap(String Directorymap) {
        this.directorymap = Directorymap;
    }
	
	public String getUpdatekey() {
        return updatekey;
    }
	
	public void setUpdatekey(String Updatekey) {
        this.updatekey = Updatekey;
    }
	
	public int getCountchild() {
        return countchild;
    }
	
	public void setCountchild(int Countchild) {
        this.countchild = Countchild;
    }
	
	public int getIntentid() {
        return intentid;
    }
	
	public void setIntentid(int Intentid) {
        this.intentid = Intentid;
    }
	
	public int getSwitchingid() {
        return switchingid;
    }
	
	public void setSwitchingid(int Switchingid) {
        this.switchingid = Switchingid;
    }
	
	public int getSentimentid() {
        return sentimentid;
    }
	
	public void setSentimentid(int Sentimentid) {
        this.sentimentid = Sentimentid;
    }
	
	public int getIntentgroupid() {
        return intentgroupid;
    }
	
	public void setIntentgroupid(int Intentgroupid) {
        this.intentgroupid = Intentgroupid;
    }

	public String getSwitchingname() {
		return switchingname;
	}

	public void setSwitchingname(String switchingname) {
		this.switchingname = switchingname;
	}

	public String getIntentname() {
		return intentname;
	}

	public void setIntentname(String intentname) {
		this.intentname = intentname;
	}

	public int getRetvoiceid() {
		return retvoiceid;
	}

	public void setRetvoiceid(int retvoiceid) {
		this.retvoiceid = retvoiceid;
	}

	public int getIsvoicegenerated() {
		return isvoicegenerated;
	}

	public void setIsvoicegenerated(int isvoicegenerated) {
		this.isvoicegenerated = isvoicegenerated;
	}

	public int getRetmlid() {
		return retmlid;
	}

	public void setRetmlid(int retmlid) {
		this.retmlid = retmlid;
	}

	public int getIsmlgenerated() {
		return ismlgenerated;
	}

	public void setIsmlgenerated(int ismlgenerated) {
		this.ismlgenerated = ismlgenerated;
	}
	
	public int getIntentparentid() {
		return intentparentid;
	}

	public void setIntentparentid(int intentparentid) {
		this.intentparentid = intentparentid;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
