package com.egeroo.roocvthree.roocconfig;

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
@RequestMapping("/roocconfig")
public class RoocConfigController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private RoocConfigService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<RoocConfig> getIndex(@RequestHeader HttpHeaders headers) {
		List<RoocConfig> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public RoocConfig getView(@RequestHeader HttpHeaders headers,int roocconfigid) {
		RoocConfig result = service.getView(headers.get("tenantID").get(0),roocconfigid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public RoocConfig getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody RoocConfig obj) {
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
		
		RoocConfig result = service.getCreate(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public RoocConfig getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody RoocConfig obj) {
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
		
		RoocConfig result = service.getUpdate(headers.get("tenantID").get(0),obj);
		return result;
		
		
	}

}
