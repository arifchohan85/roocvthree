package com.egeroo.roocvthree.userchannellink;

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
@RequestMapping("/userchannellink")
public class UserChannelLinkController {
	
TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private UserChannelLinkService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<UserChannelLink> getIndex(@RequestHeader HttpHeaders headers) {
		List<UserChannelLink> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public UserChannelLink getView(@RequestHeader HttpHeaders headers,Integer id) {
		if (id == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		
		if(id <=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		UserChannelLink result = service.getView(headers.get("tenantID").get(0),id);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/viewuserchannellinkbyuser")
	public List<UserChannelLink> getViewByUser(@RequestHeader HttpHeaders headers,Integer userid) {
		if (userid == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user id");
		}
		
		if(userid <=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user id");
		}
		List<UserChannelLink> result = service.getViewByUser(headers.get("tenantID").get(0),userid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/insertuserchannellink")
	public UserChannelLink getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody UserChannelLink obj) {
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
		
		UserChannelLink result = service.getCreate(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public UserChannelLink getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody UserChannelLink obj) {
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
		
		UserChannelLink result = service.getUpdate(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/removeuserchannelink")
	public UserChannelLink getDelete(@RequestHeader HttpHeaders headers,Integer id) {
		if (id == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		
		if(id <=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		
		UserChannelLink result = service.getDelete(headers.get("tenantID").get(0),id);
		return result;
	}

}
