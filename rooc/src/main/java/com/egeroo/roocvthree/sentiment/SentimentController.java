package com.egeroo.roocvthree.sentiment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sentiment")
public class SentimentController {
	
	@Autowired
    private SentimentService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Sentiment> getIndex(@RequestHeader HttpHeaders headers) {
		List<Sentiment> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Sentiment getView(@RequestHeader HttpHeaders headers,int sentimentid) {
		Sentiment result = service.getView(headers.get("tenantID").get(0),sentimentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public void getCreate(@RequestHeader HttpHeaders headers,@RequestBody Sentiment obj) {
		//UserRole result = 
		service.getCreate(headers.get("tenantID").get(0),obj);
		//return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public void getUpdate(@RequestHeader HttpHeaders headers,@RequestBody Sentiment obj) {
		//UserRole result = 
		service.getUpdate(headers.get("tenantID").get(0),obj);
		//return result;
	}

}
