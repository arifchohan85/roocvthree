package com.egeroo.roocvthree.knowledge;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {
	
	@Autowired
    private KnowledgeService service;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public KnowledgeResponse getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody KnowledgeRequest obj) {
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
		//trb.SetTrailRecord(token,obj);
		//obj.setToken(token);
		KnowledgeResponse kres = service.getCreate(headers.get("tenantID").get(0),obj,token);
		return kres;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public KnowledgeResponse getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody KnowledgeRequest obj) {
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
		//trb.SetTrailRecord(token,obj);
		//obj.setToken(token);
		KnowledgeResponse kres = service.getUpdate(headers.get("tenantID").get(0),obj,token);
		return kres;
	}

}
