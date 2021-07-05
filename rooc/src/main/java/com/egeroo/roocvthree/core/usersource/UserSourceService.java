package com.egeroo.roocvthree.core.usersource;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class UserSourceService {
	
//private static List<UserSource> users;
	
	public List<UserSource> getIndex(String tenant) {
		UserSourceMapper appMapper = new UserSourceMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public String getCreate(String tenant,UserSource user) {
		UserSourceMapper appMapper = new UserSourceMapperImpl(tenant);
		return appMapper.Save(user); 
	}
	
	public UserSource findByAuthkey(String tenant,String authkey) {
		UserSourceMapper appMapper = new UserSourceMapperImpl(tenant);
		return appMapper.findByAuthkey(authkey);
    }
	
	public UserSource findByAuthkeyandUserid(String tenant,String authkey,int userid) {
		UserSourceMapper appMapper = new UserSourceMapperImpl(tenant);
		return appMapper.findByAuthkeyandUserid(authkey, userid);
    }
	
	public UserSource Updatelogout(String tenant,UserSource usersource) {
		UserSourceMapper appMapper = new UserSourceMapperImpl(tenant);
		return appMapper.Updatelogout(usersource);
    }

}
