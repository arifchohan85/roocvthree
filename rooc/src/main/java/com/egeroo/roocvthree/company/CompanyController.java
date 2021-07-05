package com.egeroo.roocvthree.company;

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
@RequestMapping("/company")
public class CompanyController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private CompanyService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Company> getIndex(@RequestHeader HttpHeaders headers) {
		List<Company> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Company getView(@RequestHeader HttpHeaders headers,int companyid) {
		Company result = service.getView(headers.get("tenantID").get(0),companyid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public void getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Company obj) {
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
		service.getCreate(headers.get("tenantID").get(0),obj);
		//return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public void getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Company obj) {
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
		service.getUpdate(headers.get("tenantID").get(0),obj);
		//return result;
	}

}
