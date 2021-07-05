package com.egeroo.roocvthree.enginecredential;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.egeroo.roocvthree.core.base.Base;

@Entity
@Table( name = "ms_eng_enginecredential" )
public class EngineCredential extends Base{
	
	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int enginecredentialid;
	
	@NotNull(message ="api is a required field")
	private String api;
	
	@NotNull(message ="channel api is a required field")
	private String channelapi;
	
	@NotNull(message ="local api is a required field")
	private String localapi;
	
	@NotNull(message ="Username is a required field")
    @Size(min=3, max=255)
	private String username;
	
	@NotNull(message ="Password is a required field")
    @Size(min=3, max=255)
	private String password;
	
	@NotNull(message ="channel Username is a required field")
    @Size(min=3, max=255)
	private String channelusername;
	
	@NotNull(message ="channel Password is a required field")
    @Size(min=3, max=255)
	private String channelpassword;
	
	private int initialincrement;
	
	@NotNull(message ="root category is a required field")
    @Size(max=255)
	private String rootcategory;
	
	private String xmluri;
	private String xmlurireturn;
	
	private String rtsaapi;
	private String mlapi;
	
	
	private String clientid;
	private String applicationid;
	private String passwordencryptionkey;
	private String bearerkey;
	private String ldapapi;
	
	private String voiceapi;
	private int isusevoice;
	
	private int isusertsaml;
	
	public int getEnginecredentialid() {
        return enginecredentialid;
    }
	
	public void setEnginecredentialid(int enginecredentialid) {
        this.enginecredentialid = enginecredentialid;
    }
	
	public String getApi() {
        return api;
    }
	
	public void setApi(String Api) {
        this.api = Api;
    }
	
	public String getChannelapi() {
        return channelapi;
    }
	
	public void setChannelapi(String Channelapi) {
        this.channelapi = Channelapi;
    }
	
	public String getLocalapi() {
        return localapi;
    }
	
	public void setLocalapi(String Localapi) {
        this.localapi = Localapi;
    }
	
	public String getUsername() {
        return username;
    }
	
	public void setUsername(String UserName) {
        this.username = UserName;
    }
	
	public String getChannelusername() {
        return channelusername;
    }
	
	public void setChannelusername(String ChanneluserName) {
        this.channelusername = ChanneluserName;
    }
	
	public String getPassword() {
        return password;
    }
	
	public void setPassword(String Password) {
		this.password = Password;
    }
	
	public String getChannelPassword() {
        return channelpassword;
    }
	
	public void setChannelPassword(String Channelpassword) {
		this.channelpassword = Channelpassword;
    }
	
	public int getInitialincrement() {
        return initialincrement;
    }
	
	public void setInitialincrement(int initialincrement) {
        this.initialincrement = initialincrement;
    }
	
	public String getRootcategory() {
        return rootcategory;
    }
	
	public void setRootcategory(String Rootcategory) {
        this.rootcategory = Rootcategory;
    }

	public String getChannelpassword() {
		return channelpassword;
	}

	public void setChannelpassword(String channelpassword) {
		this.channelpassword = channelpassword;
	}

	public String getXmluri() {
		return xmluri;
	}

	public void setXmluri(String xmluri) {
		this.xmluri = xmluri;
	}

	public String getXmlurireturn() {
		return xmlurireturn;
	}

	public void setXmlurireturn(String xmlurireturn) {
		this.xmlurireturn = xmlurireturn;
	}

	public String getRtsaapi() {
		return rtsaapi;
	}

	public void setRtsaapi(String rtsaapi) {
		this.rtsaapi = rtsaapi;
	}

	public String getMlapi() {
		return mlapi;
	}

	public void setMlapi(String mlapi) {
		this.mlapi = mlapi;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getApplicationid() {
		return applicationid;
	}

	public void setApplicationid(String applicationid) {
		this.applicationid = applicationid;
	}

	public String getPasswordencryptionkey() {
		return passwordencryptionkey;
	}

	public void setPasswordencryptionkey(String passwordencryptionkey) {
		this.passwordencryptionkey = passwordencryptionkey;
	}

	public String getBearerkey() {
		return bearerkey;
	}

	public void setBearerkey(String bearerkey) {
		this.bearerkey = bearerkey;
	}

	public String getLdapapi() {
		return ldapapi;
	}

	public void setLdapapi(String ldapapi) {
		this.ldapapi = ldapapi;
	}

	public String getVoiceapi() {
		return voiceapi;
	}

	public void setVoiceapi(String voiceapi) {
		this.voiceapi = voiceapi;
	}

	public int getIsusevoice() {
		return isusevoice;
	}

	public void setIsusevoice(int isusevoice) {
		this.isusevoice = isusevoice;
	}

	public int getIsusertsaml() {
		return isusertsaml;
	}

	public void setIsusertsaml(int isusertsaml) {
		this.isusertsaml = isusertsaml;
	}
	
	
	
	

}
