package com.egeroo.roocvthree.menulistrole;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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


@RestController
@RequestMapping("/menulistrole")
public class MenuListRoleController {
	
	@Autowired
    private MenuListRoleService service;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<MenuListRole> getIndex(@RequestHeader HttpHeaders headers) {
		List<MenuListRole> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public MenuListRole getView(@RequestHeader HttpHeaders headers,int menulistroleid) {
		MenuListRole result = service.getView(headers.get("tenantID").get(0),menulistroleid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody MenuListRole obj) {
		String token ="";
		String retData ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		
		if(obj.getRoleid()<=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Role is empty");
		}
		
		if(obj.getMenulistid()<=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Menu is empty");
		}
		
		if (service.ismenuroleExist(headers.get("tenantID").get(0),obj)) {		
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Menu and Role already mapped");
		}
		else
		{
			trb.SetTrailRecord(token,obj);
			retData = service.getCreate(headers.get("tenantID").get(0),obj);
		}
		
		
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody MenuListRole obj) {
		String token ="";
		String retData = "";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		
		if (service.ismenuroleExist(headers.get("tenantID").get(0),obj)) {		
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Menu and Role already mapped");
		}
		else
		{
			trb.SetTrailRecord(token,obj);
			retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		}
		
		return  retData;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/delete")
	public MenuListRole getDelete(@RequestHeader HttpHeaders headers,int menulistroleid) {
		MenuListRole result = service.getDelete(headers.get("tenantID").get(0),menulistroleid);
		return result;
	}

}
