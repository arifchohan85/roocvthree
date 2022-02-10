package com.egeroo.roocvthree.intent;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;

import com.egeroo.roocvthree.directory.DirectoryService;

import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;





@RestController
@RequestMapping("/intent")
public class IntentController {
	
	@Autowired
    private IntentService service;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	EngineCredentialService ecservice = new EngineCredentialService();
	
	HttpPostReq hpr = new HttpPostReq();
	ValidationJson validatejson = new ValidationJson(); 
	DirectoryService dirservice = new DirectoryService();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Intent> getIndex(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.GET)
	public List<Map> getIndexV3(@RequestHeader HttpHeaders headers,@RequestParam("lite") boolean lite) {
		List<Map> result = service.getIndexV3(headers.get("tenantID").get(0), lite);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/extractintent")
	public List<Intent> getExtractintent(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getExtractintent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/intentdirrecursive")
	public List<Intent> getIntentrecursive(@RequestHeader HttpHeaders headers,int directoryid) {
		List<Intent> result = service.getIntentdirrecursive(headers.get("tenantID").get(0),directoryid);
		if(result == null)
		{
			throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "No Data");
		}
		result.forEach(item->{
			System.out.println(item.getIntentid());
		});
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listintent")
	public List<Intent> getIntent(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getIntent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listintentict")
	public List<Intent> getIntentict(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getIntent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listintentdirectory")
	public List<Intent> getIntentdir(@RequestHeader HttpHeaders headers,int directoryid) {
		List<Intent> result = service.getIntentdir(headers.get("tenantID").get(0),directoryid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Intent getView(@RequestHeader HttpHeaders headers,int intentid) {
		Intent result = service.getView(headers.get("tenantID").get(0),intentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/delete")
	public Intent getDelete(@RequestHeader HttpHeaders headers,int intentid) {
		Intent result = service.getDelete(headers.get("tenantID").get(0),intentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/deletebydirectory")
	public List<Intent> getDeletebydirectory(@RequestHeader HttpHeaders headers,int directoryid) {
		List<Intent> result = service.getDeleteintentbydirectory(headers.get("tenantID").get(0),directoryid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/viewquestion")
	public Intent getViewquestion(@RequestHeader HttpHeaders headers,String question) {
		Intent result = service.getViewquestion(headers.get("tenantID").get(0),question);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Intent obj) {
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
		//obj.setToken(token);
		String retData = service.getCreateengine(headers.get("tenantID").get(0),obj);
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Intent obj) {
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
		return  retData;
	}
	

	
}
