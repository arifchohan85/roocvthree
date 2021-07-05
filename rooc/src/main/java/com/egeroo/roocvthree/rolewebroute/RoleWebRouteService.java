package com.egeroo.roocvthree.rolewebroute;

import java.util.List;

import org.springframework.stereotype.Service;





@Service
public class RoleWebRouteService {
	
	public List<RoleWebRoute> getIndex(String tenant) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public RoleWebRoute getView(String tenant,int rolewebrouteid) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.findOne(rolewebrouteid);
	}
	
	public RoleWebRoute getCreate(String tenant,RoleWebRoute rolewebroute) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.Save(rolewebroute);
	}
	
	public RoleWebRoute getUpdate(String tenant,RoleWebRoute rolewebroute) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.Update(rolewebroute);
	}
	
	public RoleWebRoute getDelete(String tenant,int rolewebrouteid) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.deleteOne(rolewebrouteid);
	}
	
	public RoleWebRoute findByroleandroute(String tenant,String route,int roleid) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.findByroleandroute(route,roleid);  
    }
	
	public boolean isrouteroleExist(String tenant,RoleWebRoute mr) {
		if(findByroleandroute(tenant,mr.getRoute(),mr.getRoleid()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isrouteroleExist(String tenant,String route,int roleid) {
		if(findByroleandroute(tenant,route,roleid) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public List<RoleWebRoute> getDeletebyroleid(String tenant,int roleid) {
		RoleWebRouteMapper appMapper = new RoleWebRouteMapperImpl(tenant);
		return appMapper.deleteByroleid(roleid);	 
	}

}
