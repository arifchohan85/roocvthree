package com.egeroo.roocvthree.botrate;

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


@RestController
@RequestMapping("/botrate")
public class BotrateController {
	
TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private BotrateService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Botrate> getIndex(@RequestHeader HttpHeaders headers) {
		List<Botrate> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Botrate getView(@RequestHeader HttpHeaders headers,Integer botrateid) {
		if (botrateid == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		
		if(botrateid <=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		Botrate result = service.getView(headers.get("tenantID").get(0),botrateid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/insertbotrate")
	public Botrate getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Botrate obj) {
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
		
		Botrate result = service.getCreate(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public Botrate getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Botrate obj) {
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
		
		Botrate result = service.getUpdate(headers.get("tenantID").get(0),obj);
		return result;
		
	}

}
