package com.egeroo.roocvthree.enginecredential;

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

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


@RestController
@RequestMapping("/enginecreds")
public class EngineCredentialController {
	
	@Autowired
    private EngineCredentialService service;
	HttpPostReq hpr = new HttpPostReq();
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<EngineCredential> getIndex(@RequestHeader HttpHeaders headers) {
		List<EngineCredential> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	/*@RequestMapping(method=RequestMethod.GET,value="/view")
	public String getView(@RequestHeader HttpHeaders headers,int ecid) {
		EngineCredential result = service.getView(headers.get("tenantID").get(0),ecid);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		System.out.println(result);
		
		String hprret = "";
		
		try {
			hprret = hpr.ConnectGetToken(result.getApi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		return hprret;
	}*/
	
	public EngineCredential getView(@RequestHeader HttpHeaders headers,int ecid) {
		EngineCredential result = service.getView(headers.get("tenantID").get(0),ecid);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody EngineCredential obj) {
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
		String retData = service.getCreate(headers.get("tenantID").get(0),obj);
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		if(Integer.parseInt(retData)>0)
		{
			postmessage = "{ 'type' : 'engcreds' ,'menulistid' : " + retData + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
		}
		else
		{
			throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
		}
		//return  retData;
		return prettyJsonString;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody EngineCredential obj) {
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
		String retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		if(Integer.parseInt(retData)>0)
		{
			postmessage = "{ 'type' : 'engcreds' ,'menulistid' : " + retData + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
		}
		else
		{
			throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
		}
		//return  retData;
		return prettyJsonString;
	}

}
