package com.egeroo.roocvthree.menulist;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class MenulistService {
	
	public List<Menulist> getIndex(String tenant) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	/*public Menulistall getListmenu(String tenant) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findAllmenu();	 
	}*/
	
	public List<Menulist> getListmenu(String tenant) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findAllmenu();	 
	}
	
	public List<Menulist> getListmenuwithrole(String tenant,int roleid) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findAllmenuwithrole(roleid);	 
	}
	
	public List<Menulist> getListmenuparent(String tenant) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findParent();	 
	}
	
	public List<Menulist> getListmenuchild(String tenant,int parentid) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findChild(parentid);	 
	}
	
	public Menulist getView(String tenant,int menulistid) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findOne(menulistid);	 
	}
	
	public String getCreate(String tenant,Menulist ml) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.Save(ml); 
		//appMapper.Save(user);
		//return user.getUserid();
	}
	
	public String getUpdate(String tenant,Menulist ml) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.Update(ml);
	}
	
	public Menulist getDelete(String tenant,int menulistid) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.deleteOne(menulistid);
	}
	
	public Menulist findByRoute(String tenant,String route) {
		MenulistMapper appMapper = new MenulistMapperImpl(tenant);
		return appMapper.findByRoute(route);	 
	}

}
