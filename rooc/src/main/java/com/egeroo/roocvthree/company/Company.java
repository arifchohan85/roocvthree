package com.egeroo.roocvthree.company;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;

@SuppressWarnings("serial")
@Entity
@Table( name = "ms_app_company" )
public class Company extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int companyid;
	private String companyname;
	private String address;
	private String emailaddress;
	private String phoneno;
	private String ext;
	
	public int getCompanyid() {
        return companyid;
    }
	
	public void setCompanyid(int CompanyID) {
        this.companyid = CompanyID;
    }
	
	public String getCompanyname() {
        return companyname;
    }
	
	public void setCompanyname(String CompanyName) {
        this.companyname = CompanyName;
    }
	
	public String getAddress() {
        return address;
    }
	
	public void setAddress(String Address) {
        this.address = Address;
    }
	
	public String getEmailaddress() {
        return emailaddress;
    }
	
	public void setEmailaddress(String Emailaddress) {
        this.emailaddress = Emailaddress;
    }
	
	public String getPhoneno() {
        return phoneno;
    }
	
	public void setPhoneno(String Phoneno) {
        this.phoneno = Phoneno;
    }
	
	public String getExt() {
        return ext;
    }
	
	public void setExt(String Ext) {
        this.ext = Ext;
    }

}
