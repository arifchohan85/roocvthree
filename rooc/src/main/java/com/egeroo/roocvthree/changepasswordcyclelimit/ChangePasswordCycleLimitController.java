package com.egeroo.roocvthree.changepasswordcyclelimit;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;


@RestController
@RequestMapping("/cpcl")
public class ChangePasswordCycleLimitController {
	
TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private ChangePasswordCycleLimitService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<ChangePasswordCycleLimit> getIndex(@RequestHeader HttpHeaders headers) {
		List<ChangePasswordCycleLimit> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public ChangePasswordCycleLimit getView(@RequestHeader HttpHeaders headers,int ChangePasswordCycleLimitid) {
		ChangePasswordCycleLimit result = service.getView(headers.get("tenantID").get(0),ChangePasswordCycleLimitid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/viewlimit")
	public ChangePasswordCycleLimit getViewlimit(@RequestHeader HttpHeaders headers) {
		ChangePasswordCycleLimit result = service.getViewlimit(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public ChangePasswordCycleLimit getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody ChangePasswordCycleLimit obj) {
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
		ChangePasswordCycleLimit result = service.getCreate(headers.get("tenantID").get(0),obj);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public ChangePasswordCycleLimit getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody ChangePasswordCycleLimit obj) {
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
		ChangePasswordCycleLimit result = service.getUpdate(headers.get("tenantID").get(0),obj);
		return result;
	}

}
