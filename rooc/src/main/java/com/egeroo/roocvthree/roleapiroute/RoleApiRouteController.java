package com.egeroo.roocvthree.roleapiroute;

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
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileService;

@RestController
@RequestMapping("/roleapiroute")
public class RoleApiRouteController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private RoleApiRouteService service;
	
	//@Autowired
    //private UserProfileService upservice;
	UserProfileService upservice = new UserProfileService();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<RoleApiRoute> getIndex(@RequestHeader HttpHeaders headers) {
		List<RoleApiRoute> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public RoleApiRoute getView(@RequestHeader HttpHeaders headers,int roleapirouteid) {
		RoleApiRoute result = service.getView(headers.get("tenantID").get(0),roleapirouteid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public RoleApiRoute getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody RoleApiRoute obj) {
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
		if (service.isrouteroleExist(headers.get("tenantID").get(0),obj)) {	
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Route and Role already mapped");
		}
		else
		{
			RoleApiRoute result = service.getCreate(headers.get("tenantID").get(0),obj);
			return result;
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public RoleApiRoute getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody RoleApiRoute obj) {
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
		if (service.isrouteroleExist(headers.get("tenantID").get(0),obj)) {	
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Route and Role already mapped");
		}
		else
		{
			RoleApiRoute result = service.getUpdate(headers.get("tenantID").get(0),obj);
			return result;
		}
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/checkapiroute")
	public RoleApiRoute getViewwebroute(@RequestHeader HttpHeaders headers,HttpServletRequest request,String route) {
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
			RoleApiRoute result = service.findByroleandroute(headers.get("tenantID").get(0),route,resultuser.getRoleid());
			return result;
		}
		else
		{
			throw new CoreException(HttpStatus.FORBIDDEN, "Unauthorized api Url");
		}
		
	}

}
