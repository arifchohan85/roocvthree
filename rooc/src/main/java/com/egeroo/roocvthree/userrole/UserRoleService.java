package com.egeroo.roocvthree.userrole;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.intent.Intent;

@Service
public class UserRoleService {
	
	public List<UserRole> getIndex(String tenant) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	public List<Map> getIndexv3(String tenant) {
		UserRoleMapper appMapper = new UserRoleMapperImpl(tenant);
		List<UserRole> userList = appMapper.findAll();
		List<Map> result = new ArrayList<Map>();
		for (UserRole intent : userList) {
			Map inputMap = new LinkedHashMap();
			int roleId = intent.getRoleid();
			String roleName = intent.getRolename();
			inputMap.put("roleId", roleId);
			inputMap.put("roleName", roleName);
			result.add(inputMap);
		}
		return result;	 	
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
