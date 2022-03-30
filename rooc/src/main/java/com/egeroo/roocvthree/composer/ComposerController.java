package com.egeroo.roocvthree.composer;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/composer")
public class ComposerController {
	
	@Autowired
	private ComposerService service;
	
	/*
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public ComposerRequest getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody ComposerRequest obj) {
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
		
		ComposerRequest kres = service.getCreate(headers.get("tenantID").get(0),obj,token);
		
		return kres;
	}
	*/
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody String obj) {
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
		
		System.out.println("==== JSONObject is ========");
		System.out.println(obj);
		System.out.println("==== JSONObject ========");
		
		JSONObject jsonObjectlocal = new JSONObject(obj);
		
		JSONObject kres = service.getCreate(headers.get("tenantID").get(0),jsonObjectlocal,token);
		
		return kres.toString();
	}
	
	/*
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public ComposerRequest getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody ComposerRequest obj) {
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
		
		ComposerRequest kres = service.getUpdate(headers.get("tenantID").get(0),obj,token);
		
		return kres;
	}
	*/
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody String obj) {
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
		
		System.out.println("==== JSONObject is ========");
		System.out.println(obj);
		System.out.println("==== JSONObject ========");
		
		JSONObject jsonObjectlocal = new JSONObject(obj);
		
		JSONObject kres = service.getUpdate(headers.get("tenantID").get(0),jsonObjectlocal,token);
		
		return kres.toString();
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/delete")
	public String getDelete(@RequestHeader HttpHeaders headers,HttpServletRequest request,String id) {
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
		
		JSONArray kres = service.getDelete(headers.get("tenantID").get(0),id,token);
		
		return kres.toString();
	}

}
