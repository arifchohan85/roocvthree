package com.egeroo.roocvthree.menulistrole;

import java.util.List;

import org.springframework.stereotype.Service;




@Service
public class MenuListRoleService {
	
	public List<MenuListRole> getIndex(String tenant) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public MenuListRole getView(String tenant,int menulistroleid) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.findOne(menulistroleid);
		 
	}
	
	public String getCreate(String tenant,MenuListRole mlr) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.Save(mlr); 
		//appMapper.Save(user);
		//return user.getUserid();
	}
	
	public String getUpdate(String tenant,MenuListRole mlr) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.Update(mlr);
	}
	
	public MenuListRole getDelete(String tenant,int menulistroleid) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.deleteOne(menulistroleid);
	}
	
	public MenuListRole findByroleandmenu(String tenant,int menulistid,int roleid) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.findByroleandmenu(menulistid,roleid);  
    }
	
	public boolean ismenuroleExist(String tenant,MenuListRole mr) {
		if(findByroleandmenu(tenant,mr.getMenulistid(),mr.getRoleid()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public List<MenuListRole> getDeletebymenulistid(String tenant,int menulistid) {
		MenuListRoleMapper appMapper = new MenuListRoleMapperImpl(tenant);
		return appMapper.deleteBymenulistid(menulistid);	 
	}

}
