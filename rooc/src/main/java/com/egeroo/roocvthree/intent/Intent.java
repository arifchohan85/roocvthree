package com.egeroo.roocvthree.intent;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_eng_intent" )
public class Intent extends Base {
	
	/*@Id
    @GeneratedValue( strategy = GenerationType.AUTO )*/
    private int intentid;
	
	@NotNull(message ="Directory is a required field")
	private int directoryid;
	
	@NotNull(message ="Question is a required field")
	private String question;
	
	@NotNull(message ="Answer is a required field")
	private String answer;
	
	private int havechildern;
	private int sort;
	private String faqtags;
	private int active;
	private int iretquestionid;
	private int maxintentid;
	
	private int isgenerated;
	
	private int sentimentid;
	private int intentgroupid;
	
	private String description;
	
	private String directoryname;
	private String directorymap;
	
	private String updatekey;
	private String oldintentname;
	
	private int retvoiceid;
	private int isvoicegenerated;
	
	private int retmlid;
	private int ismlgenerated;
	
	private int parentid;
	private int intentparentid;

	private String token;
	
	public int getIntentid() {
        return intentid;
    }
	
	public void setIntentid(int intentid) {
        this.intentid = intentid;
    }
	
	public int getDirectoryid() {
        return directoryid;
    }
	
	public void setDirectoryid(int directoryid) {
        this.directoryid = directoryid;
    }
	
	public String getQuestion() {
        return question;
    }
	
	public void setQuestion(String question) {
        //this.question = question.trim();
		this.question = question;
    }
	
	public String getAnswer() {
        return answer;
    }
	
	public void setAnswer(String answer) {
        //this.answer = answer.trim();
		this.answer = answer;
    }
	
	public int getHavechildren() {
        return havechildern;
    }
	
	public void setHavechildren(int havechildern) {
        this.havechildern = havechildern;
    }
	
	public int getSort() {
        return sort;
    }
	
	public void setSort(int sort) {
        this.sort = sort;
    }
	
	public String getFaqtags() {
        return faqtags;
    }
	
	public void setFaqtags(String faqtags) {
        this.faqtags = faqtags;
    }
	
	public int getActive() {
        return active;
    }
	
	public void setActive(int active) {
        this.active = active;
    }
	
	public int getIretquestionid() {
        return iretquestionid;
    }
	
	public void setIretquestionid(int iretquestionid) {
        this.iretquestionid = iretquestionid;
    }
	
	public int getMaxintentid() {
        return maxintentid;
    }
	
	public void setMaxintentid(int maxintentid) {
        this.maxintentid = maxintentid;
    }
	
	public int getIsgenerated() {
        return isgenerated;
    }
	
	public void setIsgenerated(int Isgenerated) {
        this.isgenerated = Isgenerated;
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
	
	public String getDescription() {
        return description;
    }
	
	public void setDescription(String Description) {
        //this.description = Description.trim();
		this.description = Description;
    }
	
	public String getDirectoryname() {
        return directoryname;
    }
	
	public void setDirectoryname(String Directoryname) {
        this.directoryname = Directoryname;
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
	
	public String getOldintentname() {
        return oldintentname;
    }
	
	public void setOldintentname(String Oldintentname) {
        this.oldintentname = Oldintentname;
    }

	public int getHavechildern() {
		return havechildern;
	}

	public void setHavechildern(int havechildern) {
		this.havechildern = havechildern;
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

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
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
