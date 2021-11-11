package com.egeroo.roocvthree.userrole;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userrole")
public class UserRoleController {
	@Autowired
    private UserRoleService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<UserRole> getIndex(@RequestHeader HttpHeaders headers) {
		List<UserRole> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public UserRole getView(@RequestHeader HttpHeaders headers,int roleid) {
		UserRole result = service.getView(headers.get("tenantID").get(0),roleid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public void getCreate(@RequestHeader HttpHeaders headers,@RequestBody UserRole obj) {
		//UserRole result = 
		service.getCreate(headers.get("tenantID").get(0),obj);
		//return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public void getUpdate(@RequestHeader HttpHeaders headers,@RequestBody UserRole obj) {
		//UserRole result = 
		service.getUpdate(headers.get("tenantID").get(0),obj);
		//return result;
	}
	

}
