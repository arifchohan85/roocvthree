package com.egeroo.roocvthree.userrole;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
	
	public List<UserRole> getIndex(String tenant) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public UserRole getView(String tenant,int roleid) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		return appMapper.findOne(roleid);
	}
	
	public UserRole getViewbyName(String tenant,String rolename) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		return appMapper.findOnebyname(rolename);
	}
	
	public void getCreate(String tenant,UserRole userrole) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		//return 
		appMapper.Save(userrole);
	}
	
	public void getUpdate(String tenant,UserRole userrole) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		//return 
		appMapper.Update(userrole);
	}

}
