package com.egeroo.roocvthree.trailingrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/trailing")
public class TrailingRecordController {
	
	@Autowired
    private TrailingRecordService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<TrailingRecord> getIndex(@RequestHeader HttpHeaders headers) {
		List<TrailingRecord> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}

}
