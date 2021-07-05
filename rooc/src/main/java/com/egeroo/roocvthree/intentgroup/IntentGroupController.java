package com.egeroo.roocvthree.intentgroup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/intentgroup")
public class IntentGroupController {
	
	@Autowired
    private IntentGroupService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<IntentGroup> getIndex(@RequestHeader HttpHeaders headers) {
		List<IntentGroup> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public IntentGroup getView(@RequestHeader HttpHeaders headers,int intentgroupid) {
		IntentGroup result = service.getView(headers.get("tenantID").get(0),intentgroupid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public void getCreate(@RequestHeader HttpHeaders headers,@RequestBody IntentGroup obj) {
		//UserRole result = 
		service.getCreate(headers.get("tenantID").get(0),obj);
		//return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public void getUpdate(@RequestHeader HttpHeaders headers,@RequestBody IntentGroup obj) {
		//UserRole result = 
		service.getUpdate(headers.get("tenantID").get(0),obj);
		//return result;
	}

}
