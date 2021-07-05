package com.egeroo.roocvthree.core.usersource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/usersource")
public class UserSourceController {
	
	@Autowired
    private UserSourceService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<UserSource> getIndex(@RequestHeader HttpHeaders headers) {
		List<UserSource> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}

}
