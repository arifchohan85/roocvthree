package com.egeroo.roocvthree.enginecredential;

import java.util.List;

import org.springframework.stereotype.Service;





@Service
public class EngineCredentialService {
	
	public List<EngineCredential> getIndex(String tenant) {
		EngineCredentialMapper appMapper = new EngineCredentialMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public EngineCredential getView(String tenant,int ecid) {
		EngineCredentialMapper appMapper = new EngineCredentialMapperImpl(tenant);
		return appMapper.findOne(ecid);
	}
	
	public String getCreate(String tenant,EngineCredential ml) {
		EngineCredentialMapper appMapper = new EngineCredentialMapperImpl(tenant);
		return appMapper.Save(ml); 
		//appMapper.Save(user);
		//return user.getUserid();
	}
	
	public String getUpdate(String tenant,EngineCredential ml) {
		EngineCredentialMapper appMapper = new EngineCredentialMapperImpl(tenant);
		return appMapper.Update(ml);
	}

}
