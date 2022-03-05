package com.egeroo.roocvthree.knowledge;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.egeroo.roocvthree.directory.DirectoryTree;
import com.egeroo.roocvthree.directory.IntentTree;
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
	
	@RequestMapping(method=RequestMethod.GET,value="/knowledgetreebak05032022")
	public KnowledgeTree getDirectoryintentbak05032022(@RequestHeader HttpHeaders headers) {
		List<DirectoryTree> resultdirtree = service.getDirectorytree(headers.get("tenantID").get(0));
		List<IntentTree> resultintenttree = service.getIntenttree(headers.get("tenantID").get(0));
		
		KnowledgeTree ktr = new KnowledgeTree();
		ktr.setDirtree(resultdirtree);
		ktr.setIntenttree(resultintenttree);
		
		return ktr;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/knowledgetree")
	public String getKnowledgetree(@RequestHeader HttpHeaders headers) {
		JSONArray resulttree = service.getAlltree(headers.get("tenantID").get(0));
		System.out.println("====FINISH resulttree====");
		System.out.println(resulttree.toString());
		return resulttree.toString();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/tree")
	public ResponseEntity<List<List<? extends Object>>> tree(@RequestHeader HttpHeaders headers) {
		List<DirectoryTree> resultdirtree = service.getDirectorytree(headers.get("tenantID").get(0));
		List<IntentTree> resultintenttree = service.getIntenttree(headers.get("tenantID").get(0));
		return ResponseEntity.ok(Arrays.asList(resultdirtree, resultintenttree));
	}
	
//	@GetMapping("/multiple")
//	  public ResponseEntity<List<Pojo>> multiple() {
//	    return ResponseEntity.ok(Arrays.asList(new Pojo("one"), new Pojo("two")));
//	  }
	

}
