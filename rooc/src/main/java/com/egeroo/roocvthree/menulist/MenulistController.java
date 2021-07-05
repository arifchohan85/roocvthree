package com.egeroo.roocvthree.menulist;

import java.util.ArrayList;

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

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.menulistrole.MenuListRole;
import com.egeroo.roocvthree.menulistrole.MenuListRoleService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/menulist")
public class MenulistController {
	
	@Autowired
    private MenulistService service;
	
	@Autowired
    private MenuListRoleService mrservice;
	
	@Autowired
    private UserProfileService upservice;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Menulist> getIndex(@RequestHeader HttpHeaders headers) {
		List<Menulist> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listparent")
	public List<Menulist> getListparent(@RequestHeader HttpHeaders headers) {
		List<Menulist> result = service.getListmenuparent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Menulist getView(@RequestHeader HttpHeaders headers,int menulistid) {
		Menulist result = service.getView(headers.get("tenantID").get(0),menulistid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listmenuall")
	public String getListmenuall(@RequestHeader HttpHeaders headers) {
		List<Menulist> result = service.getListmenu(headers.get("tenantID").get(0));
		//String getResultdata = result.toString();
		
		//String editRes = "{ \"items\" : " + getResultdata +" }";
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//JsonParser jp = new JsonParser();
		//JsonElement je = jp.parse(resul);
		String prettyJsonString = "{ \"items\" : " +  gson.toJson(result) +" }";
		
		String modifyJson = prettyJsonString.replace("\\", "");
		//JsonElement je = jp.parse(prettyJsonString);
		modifyJson = modifyJson.replaceAll("\\\" \\\"","\\\"\\\"");
		
		modifyJson = modifyJson.replaceAll(" n ","");
		modifyJson = modifyJson.replace("\"[","[");
		modifyJson = modifyJson.replace("]\"","]");
		modifyJson = modifyJson.replace("\"items\": [],","");
		modifyJson = modifyJson.replace(",\"items\":[]","");
		modifyJson = modifyJson.replace("\"route\": \"\",","");
		modifyJson = modifyJson.replace("\"route\": \" \",","");
		//"route": " ",
		
		// "items": [],
		
		 
		
		return modifyJson;
		
		//return result;
		/*System.out.println();
		
		
		
		String editRes = "{ \"items\" : " + result.getItems() +" }";
		//editRes.replace(" \"items\": [],", " ");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(editRes);
		String prettyJsonString = gson.toJson(je);
		
		return prettyJsonString;
		*/
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listmenu")
	public String getListmenu(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
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
		
		int userid = trb.Parseuserid(token);
		
		UserProfile resup = upservice.getViewbyuserid(headers.get("tenantID").get(0),userid);
		int uproleid=0;
		if(resup == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user found.");
		}
		
		uproleid= resup.getRoleid();
		
		List<Menulist> result = service.getListmenuwithrole(headers.get("tenantID").get(0),uproleid);
		//String getResultdata = result.toString();
		
		//String editRes = "{ \"items\" : " + getResultdata +" }";
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//JsonParser jp = new JsonParser();
		//JsonElement je = jp.parse(resul);
		String prettyJsonString = "{ \"items\" : " +  gson.toJson(result) +" }";
		
		String modifyJson = prettyJsonString.replace("\\", "");
		//JsonElement je = jp.parse(prettyJsonString);
		modifyJson = modifyJson.replaceAll("\\\" \\\"","\\\"\\\"");
		
		modifyJson = modifyJson.replaceAll(" n ","");
		modifyJson = modifyJson.replace("\"[","[");
		modifyJson = modifyJson.replace("]\"","]");
		modifyJson = modifyJson.replace("\"items\": [],","");
		modifyJson = modifyJson.replace(",\"items\":[]","");
		modifyJson = modifyJson.replace("\"route\": \"\",","");
		modifyJson = modifyJson.replace("\"route\": \" \",","");
		return modifyJson;
	}
	/*public String getListmenu(@RequestHeader HttpHeaders headers) {
		Menulistall result = service.getListmenu(headers.get("tenantID").get(0));
		
		System.out.println();
		
		
		
		String editRes = "{ \"items\" : " + result.getItems() +" }";
		//editRes.replace(" \"items\": [],", " ");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(editRes);
		String prettyJsonString = gson.toJson(je);
		
		return prettyJsonString;
	}*/
	
	/*
	@RequestMapping(method=RequestMethod.GET,value="/listmenu")
	public HashMap<String, List> getListmenu(@RequestHeader HttpHeaders headers) {
		List<Menulist> result = service.getListmenuparent(headers.get("tenantID").get(0));
		
		map.put("items",result);
		Node root = new Node("root");
		for (Menulist cat : result) {
			
			//System.out.println(cat.getTitle());
	        //System.out.println(cat.getMenulistid());
		    recursiveTree(cat,headers.get("tenantID").get(0),root);
		}
		
		//menuall.put("items",map);
		
		System.out.println(root);
		
		return map;
	}
	
	
	
	public void recursiveTree(Menulist ml, String headerTenant,Node root) {
		System.out.println(ml.getTitle());
        //List<Menulist> resChild = service.getListmenuchild(headerTenant,ml.getMenulistid());
        if (ml.getChild().size() > 0) {
            for (Menulist c : ml.getChild()) {
            	//root.addChild(new Node(c.getTitle()));
            	//map.put("title",c.getTitle());
    			//map.put("icon",c.getIcon());
    			//map.put("route",c.getRoute());
            	
            	//System.out.println(c.getTitle());
                recursiveTree(c,headerTenant,root);
            }
        }
        //menuall.putAll(map);
        //menuchild.putAll(mapChild);
        
        //return menuchild;
    }*/
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Menulist obj) {
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
		String retData = service.getCreate(headers.get("tenantID").get(0),obj);
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		if(Integer.parseInt(retData)>0)
		{
			postmessage = "{ 'type' : 'menulist' ,'menulistid' : " + retData + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
		}
		else
		{
			throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
		}
		//return  retData;
		return prettyJsonString;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Menulist obj) {
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
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		if(Integer.parseInt(retData)>0)
		{
			postmessage = "{ 'type' : 'menulist' ,'menulistid' : " + retData + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
		}
		else
		{
			throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
		}
		//return  retData;
		return prettyJsonString;
	}
	
	public class Node
	{
	    private List<Node> children = null;
	    @SuppressWarnings("unused")
		private String value;

	    public Node(String value)
	    {
	        this.children = new ArrayList<>();
	        this.value = value;
	    }

	    public void addChild(Node child)
	    {
	        children.add(child);
	    }

	}
	
	@RequestMapping(method=RequestMethod.GET,value="/delete")
	public Menulist getDelete(@RequestHeader HttpHeaders headers,int menulistid) {
		Menulist result = service.getDelete(headers.get("tenantID").get(0),menulistid);
		if(result == null)
		{
			
		}
		else
		{
			List<MenuListRole> res = mrservice.getDeletebymenulistid(headers.get("tenantID").get(0),menulistid);
		}
		return result;
	}
	

}


