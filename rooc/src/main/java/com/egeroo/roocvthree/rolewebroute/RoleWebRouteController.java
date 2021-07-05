package com.egeroo.roocvthree.rolewebroute;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.menulist.Menulist;
import com.egeroo.roocvthree.menulist.MenulistService;
import com.egeroo.roocvthree.menulistrole.MenuListRole;
import com.egeroo.roocvthree.menulistrole.MenuListRoleService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileService;


@RestController
@RequestMapping("/rolewebroute")
public class RoleWebRouteController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private RoleWebRouteService service;
	
	//@Autowired
    //private UserProfileService upservice;
	UserProfileService upservice = new UserProfileService();
	
	MenulistService mlservice = new MenulistService();
	
	MenuListRoleService mlrservice = new MenuListRoleService();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<RoleWebRoute> getIndex(@RequestHeader HttpHeaders headers) {
		List<RoleWebRoute> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public RoleWebRoute getView(@RequestHeader HttpHeaders headers,int rolewebrouteid) {
		RoleWebRoute result = service.getView(headers.get("tenantID").get(0),rolewebrouteid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public RoleWebRoute getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody RoleWebRoute obj) {
		//UserRole result = 
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		trb.SetTrailRecord(token,obj);
		
		boolean isEmptyroute = obj.getRoute() == null || obj.getRoute().trim().length() == 0;
		if(isEmptyroute)
		{
			if(obj.getMenulistid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid route or menu id");
			}
			else
			{
				Menulist resultml = mlservice.getView(headers.get("tenantID").get(0), obj.getMenulistid());
				if(resultml == null)
				{
					throw new CoreException(HttpStatus.FORBIDDEN, "invalid route");
				}
				
				obj.setRoute(resultml.getRoute());
			}
		}
		else
		{
			
			Menulist resultml = mlservice.findByRoute(headers.get("tenantID").get(0), obj.getRoute());
			if(resultml == null)
			{
				throw new CoreException(HttpStatus.FORBIDDEN, "invalid route");
			}
			
			if(obj.getMenulistid()<=0)
			{
				obj.setMenulistid(resultml.getMenulistid());
			}
			
		}
		
		
		if (service.isrouteroleExist(headers.get("tenantID").get(0),obj)) {	
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Route and Role already mapped");
		}
		else
		{
			RoleWebRoute result = service.getCreate(headers.get("tenantID").get(0),obj);
			return result;
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public RoleWebRoute getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody RoleWebRoute obj) {
		//UserRole result = 
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		trb.SetTrailRecord(token,obj);
		
		
		boolean isEmptyroute = obj.getRoute() == null || obj.getRoute().trim().length() == 0;
		if(isEmptyroute)
		{
			if(obj.getMenulistid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid route or menu id");
			}
			else
			{
				Menulist resultml = mlservice.getView(headers.get("tenantID").get(0), obj.getMenulistid());
				if(resultml == null)
				{
					throw new CoreException(HttpStatus.FORBIDDEN, "invalid route");
				}
				
				obj.setRoute(resultml.getRoute());
			}
		}
		else
		{
			
			Menulist resultml = mlservice.findByRoute(headers.get("tenantID").get(0), obj.getRoute());
			if(resultml == null)
			{
				throw new CoreException(HttpStatus.FORBIDDEN, "invalid route");
			}
			
			if(obj.getMenulistid()<=0)
			{
				obj.setMenulistid(resultml.getMenulistid());
			}
			
		}
		
		if (service.isrouteroleExist(headers.get("tenantID").get(0),obj)) {	
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Route and Role already mapped");
		}
		else
		{
			RoleWebRoute result = service.getUpdate(headers.get("tenantID").get(0),obj);
			return result;
		}
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/delete")
	public RoleWebRoute getDelete(@RequestHeader HttpHeaders headers,int rolewebrouteid) {
		RoleWebRoute result = service.getDelete(headers.get("tenantID").get(0),rolewebrouteid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/checkwebroute")
	public RoleWebRoute getViewwebroute(@RequestHeader HttpHeaders headers,HttpServletRequest request,String route) {
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		int userid = trb.Parseuserid(token);
		UserProfile resultuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), userid);
		if(resultuser == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user");
		}
		
		if (service.isrouteroleExist(headers.get("tenantID").get(0),route,resultuser.getRoleid())) {	
			RoleWebRoute result = service.findByroleandroute(headers.get("tenantID").get(0),route,resultuser.getRoleid());
			return result;
		}
		else
		{
			throw new CoreException(HttpStatus.FORBIDDEN, "Unauthorized Web Url");
		}
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/checkwebroutebymenu")
	public RoleWebRoute getViewwebroutebymenu(@RequestHeader HttpHeaders headers,HttpServletRequest request,String route) {
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		
		int userid = trb.Parseuserid(token);
		UserProfile resultuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), userid);
		if(resultuser == null)
		{
			throw new CoreException(HttpStatus.FORBIDDEN, "invalid user");
		}
		
		Menulist resultml = mlservice.findByRoute(headers.get("tenantID").get(0), route);
		if(resultml == null)
		{
			throw new CoreException(HttpStatus.FORBIDDEN, "invalid route");
		}
		
		MenuListRole obj = new MenuListRole();
		obj.setRoleid(resultuser.getRoleid());
		obj.setMenulistid(resultml.getMenulistid());
		
		if (mlrservice.ismenuroleExist(headers.get("tenantID").get(0),obj)) {		
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Menu and Role already mapped");
			RoleWebRoute result = new RoleWebRoute();
			result.setRoleid(obj.getRoleid());
			result.setRoute(route);
			return result;
		}
		else
		{
			throw new CoreException(HttpStatus.FORBIDDEN, "Unauthorized Web Url");
		}
		
	}

}
