package com.egeroo.roocvthree.roleapiroute;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class RoleApiRouteService {
	
	public List<RoleApiRoute> getIndex(String tenant) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public RoleApiRoute getView(String tenant,int roleapirouteid) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.findOne(roleapirouteid);
	}
	
	public RoleApiRoute getCreate(String tenant,RoleApiRoute roleapiroute) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.Save(roleapiroute);
	}
	
	public RoleApiRoute getUpdate(String tenant,RoleApiRoute roleapiroute) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.Update(roleapiroute);
	}
	
	public RoleApiRoute getDelete(String tenant,int RoleApiRouteid) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.deleteOne(RoleApiRouteid);
	}
	
	public RoleApiRoute findByroleandroute(String tenant,String route,int roleid) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.findByroleandroute(route,roleid);  
    }
	
	public boolean isrouteroleExist(String tenant,RoleApiRoute mr) {
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
	
	public List<RoleApiRoute> getDeletebyroleid(String tenant,int roleid) {
		RoleApiRouteMapper appMapper = new RoleApiRouteMapperImpl(tenant);
		return appMapper.deleteByroleid(roleid);	 
	}

}
