package com.egeroo.roocvthree.knowledge;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.egeroo.roocvthree.directory.DirectoryTree;
import com.egeroo.roocvthree.directory.IntentTree;

import com.egeroo.roocvthree.interaction.InteractionRequest;
import com.egeroo.roocvthree.interaction.InteractionResponse;
import com.egeroo.roocvthree.interaction.InteractionService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {
	
	@Autowired
    private KnowledgeService service;
	
	@Autowired
	private InteractionService intservice;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody KnowledgeRequest obj) {
	//public KnowledgeResponse getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody KnowledgeRequest obj) {
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
		JSONObject kres = service.getCreate(headers.get("tenantID").get(0),obj,token);
		return kres.toString();
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/update")
	//public KnowledgeResponse getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody KnowledgeRequest obj) {
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody KnowledgeRequest obj) {
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
		JSONObject kres = service.getUpdate(headers.get("tenantID").get(0),obj,token);
		return kres.toString();
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
	public String getKnowledgetree(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		JSONArray resulttree = service.getAlltree(headers.get("tenantID").get(0),token);
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
	
	@RequestMapping(method=RequestMethod.POST,value="/question")
	public InteractionResponse getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody InteractionRequest obj) {
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
		InteractionResponse retData = intservice.getCreate(headers.get("tenantID").get(0),obj,token);
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/question/update")
	public InteractionResponse getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody InteractionRequest obj) {
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
		InteractionResponse retData = intservice.getUpdate(headers.get("tenantID").get(0),obj,token);
		return retData;
	}
	
	//@RequestMapping(method=RequestMethod.DELETE,value="/question/delete")
	@RequestMapping(method=RequestMethod.DELETE,value="/question/{questionId}")
	public InteractionResponse getDelete(@RequestHeader HttpHeaders headers,HttpServletRequest request,@PathVariable int questionId) {
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
		InteractionResponse retData = intservice.getDelete(headers.get("tenantID").get(0),questionId,token);
		return retData;
	}
	
	
	//
	//@GetMapping(path = "/questions", consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(method=RequestMethod.GET,value="/questions")
	public List<InteractionResponse> getQuestions(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		List<InteractionResponse> retData = intservice.getListquestions(headers.get("tenantID").get(0));
		return retData;
	}
	
	//
	//@GetMapping(path = "/questions", consumes = MediaType.APPLICATION_JSON_VALUE)
	//@RequestMapping(method=RequestMethod.GET,value="/questionsbyintent")
	//@RequestMapping(method=RequestMethod.GET ,value = "/questions", params = "intentId")
	//@RequestMapping(method=RequestMethod.GET ,value = "/questions", params = "intentId")
	@RequestMapping(method=RequestMethod.GET,value="/questions/{intentId}")
	public List<InteractionResponse> getQuestions(@RequestHeader HttpHeaders headers,HttpServletRequest request,@PathVariable int intentId) {
		List<InteractionResponse> retData = intservice.getListquestionsbyexpectedintentid(headers.get("tenantID").get(0),intentId);
		return retData;
	}
	

	

}
