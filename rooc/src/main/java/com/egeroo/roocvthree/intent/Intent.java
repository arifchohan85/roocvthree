package com.egeroo.roocvthree.intent;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.egeroo.roocvthree.core.base.Base;

@SuppressWarnings("serial")
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
	
	
	private int iretquestionid;
	
	
	private int isgenerated;
	
	
	private String directoryname;
	private String directorymap;
	
	private String updatekey;
	private String oldintentname;
	
	
	
	
	private int intentparentid;
	
	private int maxintentid;

	
	
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
	
	
	
	
	public int getIretquestionid() {
        return iretquestionid;
    }
	
	public void setIretquestionid(int iretquestionid) {
        this.iretquestionid = iretquestionid;
    }
	
	
	
	public int getIsgenerated() {
        return isgenerated;
    }
	
	public void setIsgenerated(int Isgenerated) {
        this.isgenerated = Isgenerated;
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

	
	public int getIntentparentid() {
		return intentparentid;
	}

	public void setIntentparentid(int intentparentid) {
		this.intentparentid = intentparentid;
	}

	public int getMaxintentid() {
		return maxintentid;
	}

	public void setMaxintentid(int maxintentid) {
		this.maxintentid = maxintentid;
	}
	
	

	
}
