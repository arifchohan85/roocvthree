package com.egeroo.roocvthree.directory;


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
import com.egeroo.roocvthree.core.error.ValidationJson;

import com.egeroo.roocvthree.enginecredential.EngineCredentialService;

import com.egeroo.roocvthree.intent.IntentService;

import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;





@RestController
@RequestMapping("/directory")
public class DirectoryController {
	
	@Autowired
    private DirectoryService service;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	private String retDataPath;
	
	EngineCredentialService ecservice = new EngineCredentialService();
	
	HttpPostReq hpr = new HttpPostReq();
	
	IntentService intservice = new IntentService();
	
	ValidationJson validatejson = new ValidationJson(); 
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Directory> getIndex(@RequestHeader HttpHeaders headers) {
		List<Directory> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/extractdirectory")
	public List<Directory> getExtractdirectory(@RequestHeader HttpHeaders headers) {
		List<Directory> result = service.getDirectoryextract(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listtreedirectory")
	public String getListtreedirectory(@RequestHeader HttpHeaders headers) {
		Directorylistall result = service.getListdirectory(headers.get("tenantID").get(0));
		
		System.out.println();
		
		//JSONArray ja = new JSONArray(result.getChildren());
		
		String editRes = result.getChildren();
		//editRes.replace(" \"items\": [],", " ");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(editRes);
		String prettyJsonString = gson.toJson(je);
		
		return prettyJsonString;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdirectoryintent")
	public List<DirectoryIntent> getDirectoryintent(@RequestHeader HttpHeaders headers) {
		List<DirectoryIntent> result = service.getDirectoryintent(headers.get("tenantID").get(0));
		return result;
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/listdirectory")
	public List<Directory> getDirectory(@RequestHeader HttpHeaders headers) {
		List<Directory> result = service.getDirectory(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/checkdirectorymap")
	public Directory getCheckdirectorymap(@RequestHeader HttpHeaders headers,String directorymap) {
		//List<Directory> result = service.getDirectory(headers.get("tenantID").get(0));
		String strMap = directorymap;
		String[] split = strMap.split("->");
		int getparentID =0 ;
		Directory dirparent = new Directory();
		for (int i = 0; i < split.length; i++) {
			if(i==0)
			{
				//the first one query without parentid only name
				dirparent = service.getViewparentbyname(headers.get("tenantID").get(0),split[i]);
			}
			else
			{
				//not the first one query with parentid and name
				dirparent = service.getViewdirectorybynameandparentid(headers.get("tenantID").get(0),split[i],getparentID);
			}
			
			if(dirparent==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
			}
			else
			{
				getparentID = dirparent.getDirectoryid();
			}
			
			System.out.println(split[i] + " \n");
		}
		return dirparent;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdirectoryenum")
	public List<Directory> getDirectoryenum(@RequestHeader HttpHeaders headers) {
		List<Directory> result = service.getDirectoryenum(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Directory getView(@RequestHeader HttpHeaders headers,int directoryid) {
		Directory result = service.getView(headers.get("tenantID").get(0),directoryid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/delete")
	public List<Directory> getDelete(@RequestHeader HttpHeaders headers,HttpServletRequest request,int directoryid) {
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
		List<Directory> result = service.getDelete(headers.get("tenantID").get(0),directoryid,token);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/deleterecursive")
	public List<Directory> getDeleterecursive(@RequestHeader HttpHeaders headers,HttpServletRequest request,int directoryid) {
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
		List<Directory> result = service.getDeleterecursive(headers.get("tenantID").get(0),directoryid,token);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Directory obj) {
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
		String retData = service.getCreate(headers.get("tenantID").get(0),obj);
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Directory obj) {
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
	
	@RequestMapping(method=RequestMethod.GET,value="/viewmapdir")
	public String getViewmapdir(@RequestHeader HttpHeaders headers,int directoryid) {
		this.retDataPath ="";
		
		
		String getmap = this.mapView(headers.get("tenantID").get(0), directoryid);
		String editRes = "{ \"maps\" : \""+ getmap  +"\" }";
		
		return editRes;
	}
	
	private String mapView(String tenantID,int InitcatID)
	{
		Directory dirresult = service.getView(tenantID,InitcatID);
		System.out.print("Category id for map is :" +InitcatID);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent data");
		}
		
		if(dirresult.getParentid() >0)
		{
			 //$this->createCatmapView($modelfindParentretID->ParentID);
			this.mapView(tenantID, dirresult.getParentid());
			this.retDataPath = this.retDataPath +"->"+ dirresult.getName();
		}
		else if(dirresult.getParentid() <=0)
		{
			//$this->retDataPath = $this->retDataPath .$modelfindParentretID->Name;
			this.retDataPath = this.retDataPath + dirresult.getName();
		}
		
		return this.retDataPath;
	}
	
	

}
