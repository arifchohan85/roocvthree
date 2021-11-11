package com.egeroo.roocvthree.directory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
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
import com.egeroo.roocvthree.enginecredential.EngineCredential;
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
	private int ECID = 1;
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
	
	/*@RequestMapping(method=RequestMethod.GET,value="/delete")
	public Directory getDelete(@RequestHeader HttpHeaders headers,int directoryid) {
		Directory result = service.getDelete(headers.get("tenantID").get(0),directoryid);
		return result;
	}*/
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
		obj.setToken(token);
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
		
		/*String editRes = result.getChildren();
		//editRes.replace(" \"items\": [],", " ");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(editRes);
		String prettyJsonString = gson.toJson(je);
		
		return prettyJsonString;
		*/
		String getmap = this.mapView(headers.get("tenantID").get(0), directoryid);
		String editRes = "{ \"maps\" : \""+ getmap  +"\" }";
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//JsonParser jp = new JsonParser();
		//JsonElement je = jp.parse(editRes);
		//String prettyJsonString = gson.toJson(editRes);
		
		//"maps" : '" + getmap  +"'
		//Directory result = service.getView(headers.get("tenantID").get(0),directoryid);
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
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/syncdbdirectoryvoice")
	public String getSyncdbdirvoice(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		String headerTenant = headers.get("tenantID").get(0);
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		List<Directory> dirresult = service.getDirectoryvoicenotgenerated(headerTenant);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getVoiceapi();
		
		JSONObject postdata = new JSONObject();
		
		JSONObject faqstring = new JSONObject();
		Directory dirupdate =  new Directory();
		//int MaxIntent =0;
		String hprrettoken = "";
		String engAccesstoken ="";
		try {
			hprrettoken = hpr.ConnectGetToken(result.getVoiceapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
		jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObjecttoken.getString("access_token");
        System.out.println("token is :" + jsonObjecttoken.getString("access_token"));
		
        String engtoken = engAccesstoken;
		dirresult.forEach(item->{
			JSONObject enumstring = new JSONObject();
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headerTenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String hprret = "";
			
			String postret = "";
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			String validUri = validateUri(voiceapi+"/api/category/get/"+getmap);
			System.out.print("validUri :" +validUri);
			try {
				hprret = hpr.ConnectGetnoparam(validUri);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        
	        dirupdate.setDirectoryid(item.getDirectoryid());
			dirupdate.setCategorymode(item.getCategorymode());
			
			int retparID =0;
			if(item.getParentid()>0)
			{
				Directory dirfindone = service.getView(headerTenant,item.getParentid());
				if(dirfindone == null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
				}
				
				retparID = dirfindone.getRetvoiceid();
			}
			else
			{
				retparID =0;
			}
			
			
			if(item.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = service.getView(headerTenant,item.getExtendenumcategoryid());
				if(direnum == null)
				{
					enumstring = null;
				}
				else
				{
					int retEnumID = direnum.getReticategoryid();
					String enumName = direnum.getName();
					String enumDescription = direnum.getDescription();
					String enumCatmode = direnum.getCategorymode();
					
					Directory direnumparent = null;
					direnumparent = service.getView(headerTenant,direnum.getParentid());
					int retEnumparID = direnumparent.getRetvoiceid();
					
					enumstring.put("id", retEnumID);
					enumstring.put("name", enumName);
					enumstring.put("parentid", retEnumparID);
					enumstring.put("description", enumDescription);
					enumstring.put("faq", item.getFaq());
					enumstring.put("mode", enumCatmode);
					enumstring.put("switching", item.getSwitching());
					enumstring.put("extendsEnumCategory", "null");
				}
				
			}
			
			//if(item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || item.getCategorymode().equals("DRILLDOWN") || item.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			//{
				enumstring = null;
			//}
			
			boolean isSBD = false;
			if(item.getCategorymode().equals("STANDARD") || item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("MEMORY"))
			{
				
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", item.getFaq());
				postdata.put("mode", item.getCategorymode());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
			}
			else
			{
				isSBD = true;
				faqstring.put("id", item.getFaq());
				faqstring.put("question", item.getQuestion());
				//faqstring.put("answer", item.getAnswer());
				faqstring.put("answer", ".");
				
				//postdata.put("access_token", engAccesstoken);
				//postdata.put("id", directory.getReticategoryid());
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", item.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
				
			}
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	jsonObject = new JSONObject(jsonString);
				if(jsonObject.getString("name") ==null)
				{
					//create baru ke dalam voice
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
					
					
					try {
						postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
						System.out.println("post return is : "+ postret);
						
						if(validatejson.isJSONValidstandard(postret))
				        {
							jsonObject = new JSONObject(postret);
							
							if(!jsonObject.getBoolean("STATUS"))
							{
								throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
							}
							
							if(jsonObject.getInt("MESSAGE") <=0 )
							{
								throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
							}
							
							
							
							
							if(jsonObject.getBoolean("STATUS"))
							{
								item.setRetvoiceid(jsonObject.getInt("MESSAGE"));
								
								//Directory dirupdate =  new Directory();
								dirupdate.setRetvoiceid(jsonObject.getInt("id"));
								dirupdate.setIsvoicegenerated(1);
								//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
							}
							else
						    {
								dirupdate.setRetvoiceid(0);
								dirupdate.setIsvoicegenerated(0);
						    }
				        }
						else
						{
							dirupdate.setRetvoiceid(0);
							dirupdate.setIsvoicegenerated(0);
						}
						
						
						
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else
				{
					/*Directory dirfindone = service.getView(headers.get("tenantID").get(0),item.getDirectoryid());
					if(dirfindone == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
					}
					*/
					//update voice id in roocvthree datas
					//Directory dirupdate =  new Directory();
					
					dirupdate.setRetvoiceid(jsonObject.getInt("id"));
					dirupdate.setIsvoicegenerated(1);
					
					postdata.put("id", dirupdate.getRetvoiceid());
					
					try {
						postret = hpr.setPostData(voiceapi+"/api/category/update",postdata);
					} catch (KeyManagementException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (KeyStoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("post return is : "+ postret);
					
				}
				//engAccesstoken = jsonObject.getString("access_token");
				System.out.print(jsonObject.getString("name") + " -" + jsonObject.getInt("id"));
	        }
	        else
	        {
	        	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid Category :" + item.getName());
	        	//create baru ke dalam voice
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
	        	
	        	/*boolean isSBD = false;
				if(item.getCategorymode().equals("STANDARD") || item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("MEMORY"))
				{
					//postdata.put("access_token", engAccesstoken);
					postdata.put("name", item.getName());
					postdata.put("parentid", retparID);
					postdata.put("description", item.getDescription());
					postdata.put("faq", item.getFaq());
					postdata.put("mode", item.getCategorymode());
					postdata.put("switching", item.getSwitching());
					postdata.put("extendsEnumCategory", enumstring);
				}
				else
				{
					isSBD = true;
					faqstring.put("id", item.getFaq());
					faqstring.put("question", item.getQuestion());
					faqstring.put("answer", item.getAnswer());
					
					//postdata.put("access_token", engAccesstoken);
					//postdata.put("id", directory.getReticategoryid());
					postdata.put("name", item.getName());
					postdata.put("parentid", retparID);
					postdata.put("description", item.getDescription());
					postdata.put("faq", faqstring);
					postdata.put("mode", item.getCategorymode());
					//postdata.put("switching", directory.getSwitching());
					postdata.put("switching", item);
					postdata.put("extendsEnumCategory", enumstring);
					
				}*/
				
				try {
					postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
					System.out.println("post return is : "+ postret);
					System.out.println("post data is : "+ postdata);
					
					
					if(validatejson.isJSONValidstandard(postret))
			        {
						jsonObject = new JSONObject(postret);
						
						if(!jsonObject.getBoolean("STATUS"))
						{
							dirupdate.setRetvoiceid(0);
							dirupdate.setIsvoicegenerated(0);
						}
						
						if (jsonObject.get("MESSAGE") instanceof String) {
							dirupdate.setRetvoiceid(0);
							dirupdate.setIsvoicegenerated(0);
						}
						
						
						if(jsonObject.getBoolean("STATUS"))
						{
							item.setRetvoiceid(jsonObject.getInt("MESSAGE"));
							
							dirupdate.setRetvoiceid(jsonObject.getInt("MESSAGE"));
							dirupdate.setIsvoicegenerated(1);
							//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
						}
						else
					    {
					    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
							
							dirupdate.setRetvoiceid(0);
							dirupdate.setIsvoicegenerated(0);
					    }
			        }
					else
					{
						dirupdate.setRetvoiceid(0);
						dirupdate.setIsvoicegenerated(0);
					}
					
					
					
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        }
	        service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
	        
		});
		return syncResult;
	}
	
	

	@RequestMapping(method=RequestMethod.GET,value="/syncdbdirectorymlrtsa")
	public String getSyncdbdirmlrtsa(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		String headerTenant = headers.get("tenantID").get(0);
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		//List<Directory> dirresult = service.getDirectoryvoicenotgenerated(headerTenant);
		List<Directory> dirresult = service.getDirectorynotgenml(headerTenant);
		
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getMlapi();
		
		JSONObject postdata = new JSONObject();
		
		JSONObject faqstring = new JSONObject();
		Directory dirupdate =  new Directory();
		//Directory dirlocal =  new Directory();
		//int MaxIntent =0;
		String hprrettoken = "";
		String engAccesstoken ="";
		try {
			hprrettoken = hpr.ConnectGetToken(result.getMlapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
		jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObjecttoken.getString("access_token");
        System.out.println("token is :" + jsonObjecttoken.getString("access_token"));
		
        String engtoken = engAccesstoken;
		dirresult.forEach(item->{
			JSONObject enumstring = new JSONObject();
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headerTenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String hprret = "";
			
			String postret = "";
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			String validUri = validateUri(voiceapi+"/api/category/get/"+getmap);
			System.out.print("validUri :" +validUri);
			try {
				hprret = hpr.ConnectGetnoparam(validUri);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        
	        dirupdate.setDirectoryid(item.getDirectoryid());
			dirupdate.setCategorymode(item.getCategorymode());
			
			System.out.print("Searching for current directoryid :" +item.getDirectoryid());
			Directory dirlocal = service.getView(headerTenant,item.getDirectoryid());
			
			int retparID =0;
			if(item.getParentid()>0)
			{
				Directory dirfindone = service.getView(headerTenant,item.getParentid());
				if(dirfindone == null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
				}
				
				retparID = dirfindone.getRetmlid();
			}
			else
			{
				retparID =0;
			}
			
			
			if(item.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = service.getView(headerTenant,item.getExtendenumcategoryid());
				if(direnum == null)
				{
					enumstring = null;
				}
				else
				{
					int retEnumID = direnum.getReticategoryid();
					String enumName = direnum.getName();
					String enumDescription = direnum.getDescription();
					String enumCatmode = direnum.getCategorymode();
					
					Directory direnumparent = null;
					direnumparent = service.getView(headerTenant,direnum.getParentid());
					int retEnumparID = direnumparent.getRetmlid();
					
					enumstring.put("id", retEnumID);
					enumstring.put("name", enumName);
					enumstring.put("parentid", retEnumparID);
					enumstring.put("description", enumDescription);
					enumstring.put("faq", item.getFaq());
					enumstring.put("mode", enumCatmode);
					enumstring.put("switching", item.getSwitching());
					enumstring.put("extendsEnumCategory", "null");
				}
				
			}
			
			//if(item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || item.getCategorymode().equals("DRILLDOWN") || item.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			//{
				enumstring = null;
			//}
			
			boolean isSBD = false;
			if(item.getCategorymode().equals("STANDARD") || item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("MEMORY"))
			{
				
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", item.getFaq());
				postdata.put("mode", item.getCategorymode());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
			}
			else
			{
				isSBD = true;
				faqstring.put("id", item.getFaq());
				faqstring.put("question", item.getQuestion());
				//faqstring.put("answer", item.getAnswer());
				faqstring.put("answer", ".");
				
				//postdata.put("access_token", engAccesstoken);
				//postdata.put("id", directory.getReticategoryid());
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", item.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
				
			}
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	jsonObject = new JSONObject(jsonString);
				if(jsonObject.getString("name") ==null)
				{
					//create baru ke dalam voice
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
					
					
					try {
						
						if(isSBD && retparID<=0)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
							System.out.print("cannot update : "+item.getName()+" directory");
						}
						else
						{
							postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
							System.out.println("post return is : "+ postret);
							
							if(validatejson.isJSONValidstandard(postret))
					        {
								jsonObject = new JSONObject(postret);
								
								if(!jsonObject.getBoolean("STATUS"))
								{
									//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
									System.out.print("cannot update : "+item.getName()+" directory,something wrong from engine");
								}
								
								if(jsonObject.getInt("MESSAGE") <=0 )
								{
									//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
									System.out.print("cannot update : "+item.getName()+" directory,something wrong from engine");
								}
								
								
								
								
								if(jsonObject.getBoolean("STATUS"))
								{
									item.setRetmlid(jsonObject.getInt("MESSAGE"));
									
									//Directory dirupdate =  new Directory();
									dirupdate.setRetmlid(jsonObject.getInt("id"));
									dirupdate.setIsmlgenerated(1);
									//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
								}
								else
							    {
									if(dirlocal.getRetmlid()>0)
									{
										dirupdate.setRetmlid(dirlocal.getRetmlid());
										dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
									}
									else
									{
										dirupdate.setIsmlgenerated(0);
										dirupdate.setRetmlid(0);
									}
									//dirupdate.setRetmlid(0);
									//dirupdate.setIsmlgenerated(0);
							    }
					        }
							else
							{
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
						}
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else
				{
					/*Directory dirfindone = service.getView(headers.get("tenantID").get(0),item.getDirectoryid());
					if(dirfindone == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
					}
					*/
					//update voice id in roocvthree datas
					//Directory dirupdate =  new Directory();
					
					dirupdate.setRetmlid(jsonObject.getInt("id"));
					dirupdate.setIsmlgenerated(1);
					
					postdata.put("id", dirupdate.getRetmlid());
					
					//check apakah ada perubahan data
					boolean isSame = false;
					if(item.getName().equals(jsonObject.getString("name")) && item.getSwitching().equals(jsonObject.getString("switching")) && item.getDescription().equals(jsonObject.getString("description")) && retparID==jsonObject.getInt("parentid"))
					{
						isSame = true;
					}
					
					
					
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
					else
					{
						if(!isSame)
						{
							try {
								postret = hpr.setPostData(voiceapi+"/api/category/update",postdata);
							} catch (KeyManagementException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (KeyStoreException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("post return is : "+ postret);
						}
						else
						{
							System.out.print("Directory : " + item.getName()+ "no update should be done,because all data is same");
						}
						
					}
					
					
					
				}
				//engAccesstoken = jsonObject.getString("access_token");
				System.out.print(jsonObject.getString("name") + " -" + jsonObject.getInt("id"));
	        }
	        else
	        {
	        	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid Category :" + item.getName());
	        	//create baru ke dalam ml
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
	        	
				try {
					
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
		        	else
		        	{
		        		postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
						System.out.println("post return is : "+ postret);
						System.out.println("post data is : "+ postdata);
						
						
						if(validatejson.isJSONValidstandard(postret))
				        {
							jsonObject = new JSONObject(postret);
							
							if(!jsonObject.getBoolean("STATUS"))
							{
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							if (jsonObject.get("MESSAGE") instanceof String) {
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							
							if(jsonObject.getBoolean("STATUS"))
							{
								item.setRetmlid(jsonObject.getInt("MESSAGE"));
								
								dirupdate.setRetmlid(jsonObject.getInt("MESSAGE"));
								dirupdate.setIsmlgenerated(1);
								//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
							}
							else
						    {
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
								
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
						    }
				        }
						else
						{
							if(dirlocal.getRetmlid()>0)
							{
								dirupdate.setRetmlid(dirlocal.getRetmlid());
								dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
							}
							else
							{
								dirupdate.setIsmlgenerated(0);
								dirupdate.setRetmlid(0);
							}
							//dirupdate.setRetmlid(0);
							//dirupdate.setIsmlgenerated(0);
						}
		        	}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        }
	        service.getUpdatemlrtsaonly(headers.get("tenantID").get(0), dirupdate);
	        
		});
		return syncResult;
	}
	
	

	@RequestMapping(method=RequestMethod.GET,value="/syncdbdirectorymltreeexist")
	public String getSyncdbdirmltreeexist(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		String headerTenant = headers.get("tenantID").get(0);
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		List<Directory> dirresult = service.getDirectoryvoicenotgenerated(headerTenant);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getMlapi();
		
		JSONObject postdata = new JSONObject();
		
		JSONObject faqstring = new JSONObject();
		Directory dirupdate =  new Directory();
		//Directory dirlocal =  new Directory();
		//int MaxIntent =0;
		String hprrettoken = "";
		String engAccesstoken ="";
		try {
			hprrettoken = hpr.ConnectGetToken(result.getMlapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
		jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObjecttoken.getString("access_token");
        System.out.println("token is :" + jsonObjecttoken.getString("access_token"));
		
        String engtoken = engAccesstoken;
		dirresult.forEach(item->{
			JSONObject enumstring = new JSONObject();
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headerTenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String hprret = "";
			
			String postret = "";
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			String validUri = validateUri(voiceapi+"/api/category/get/"+getmap);
			System.out.print("validUri :" +validUri);
			try {
				hprret = hpr.ConnectGetnoparam(validUri);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        
	        dirupdate.setDirectoryid(item.getDirectoryid());
			dirupdate.setCategorymode(item.getCategorymode());
			
			System.out.print("Searching for current directoryid :" +item.getDirectoryid());
			Directory dirlocal = service.getView(headerTenant,item.getDirectoryid());
			
			int retparID =0;
			if(item.getParentid()>0)
			{
				Directory dirfindone = service.getView(headerTenant,item.getParentid());
				if(dirfindone == null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
				}
				
				retparID = dirfindone.getRetmlid();
			}
			else
			{
				retparID =0;
			}
			
			
			if(item.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = service.getView(headerTenant,item.getExtendenumcategoryid());
				if(direnum == null)
				{
					enumstring = null;
				}
				else
				{
					int retEnumID = direnum.getReticategoryid();
					String enumName = direnum.getName();
					String enumDescription = direnum.getDescription();
					String enumCatmode = direnum.getCategorymode();
					
					Directory direnumparent = null;
					direnumparent = service.getView(headerTenant,direnum.getParentid());
					int retEnumparID = direnumparent.getRetmlid();
					
					enumstring.put("id", retEnumID);
					enumstring.put("name", enumName);
					enumstring.put("parentid", retEnumparID);
					enumstring.put("description", enumDescription);
					enumstring.put("faq", item.getFaq());
					enumstring.put("mode", enumCatmode);
					enumstring.put("switching", item.getSwitching());
					enumstring.put("extendsEnumCategory", "null");
				}
				
			}
			
			//if(item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || item.getCategorymode().equals("DRILLDOWN") || item.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			//{
				enumstring = null;
			//}
			
			boolean isSBD = false;
			if(item.getCategorymode().equals("STANDARD") || item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("MEMORY"))
			{
				
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", item.getFaq());
				postdata.put("mode", item.getCategorymode());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
			}
			else
			{
				isSBD = true;
				faqstring.put("id", item.getFaq());
				faqstring.put("question", item.getQuestion());
				//faqstring.put("answer", item.getAnswer());
				faqstring.put("answer", ".");
				
				//postdata.put("access_token", engAccesstoken);
				//postdata.put("id", directory.getReticategoryid());
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", item.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
				
			}
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	jsonObject = new JSONObject(jsonString);
				if(jsonObject.getString("name") ==null)
				{
					//create baru ke dalam voice
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
					
					
					try {
						
						if(isSBD && retparID<=0)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
							System.out.print("cannot update : "+item.getName()+" directory");
						}
						else
						{
							postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
							System.out.println("post return is : "+ postret);
							
							if(validatejson.isJSONValidstandard(postret))
					        {
								jsonObject = new JSONObject(postret);
								
								if(!jsonObject.getBoolean("STATUS"))
								{
									throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								}
								
								if(jsonObject.getInt("MESSAGE") <=0 )
								{
									throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								}
								
								
								
								
								if(jsonObject.getBoolean("STATUS"))
								{
									item.setRetmlid(jsonObject.getInt("MESSAGE"));
									
									//Directory dirupdate =  new Directory();
									dirupdate.setRetmlid(jsonObject.getInt("id"));
									dirupdate.setIsmlgenerated(1);
									//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
								}
								else
							    {
									if(dirlocal.getRetmlid()>0)
									{
										dirupdate.setRetmlid(dirlocal.getRetmlid());
										dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
									}
									else
									{
										dirupdate.setIsmlgenerated(0);
										dirupdate.setRetmlid(0);
									}
									//dirupdate.setRetmlid(0);
									//dirupdate.setIsmlgenerated(0);
							    }
					        }
							else
							{
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
						}
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else
				{
					/*Directory dirfindone = service.getView(headers.get("tenantID").get(0),item.getDirectoryid());
					if(dirfindone == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
					}
					*/
					//update voice id in roocvthree datas
					//Directory dirupdate =  new Directory();
					
					dirupdate.setRetmlid(jsonObject.getInt("id"));
					dirupdate.setIsmlgenerated(1);
					
					//postdata.put("id", dirupdate.getRetmlid());
					
					//check apakah ada perubahan data
					boolean isSame = false;
					if(item.getName().equals(jsonObject.getString("name")) && item.getSwitching().equals(jsonObject.getString("switching")) && item.getDescription().equals(jsonObject.getString("description")) && retparID==jsonObject.getInt("parentid"))
					{
						isSame = true;
					}
					
					
					/*
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
					else
					{
						if(!isSame)
						{
							try {
								postret = hpr.setPostData(voiceapi+"/api/category/update",postdata);
							} catch (KeyManagementException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (KeyStoreException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("post return is : "+ postret);
						}
						else
						{
							System.out.print("Directory : " + item.getName()+ "doesn't need any update,because all data is same");
						}
						
					}*/
					
					
					
				}
				//engAccesstoken = jsonObject.getString("access_token");
				System.out.print(jsonObject.getString("name") + " -" + jsonObject.getInt("id"));
	        }
	        else
	        {
	        	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid Category :" + item.getName());
	        	//create baru ke dalam ml
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
	        	
				try {
					
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
		        	else
		        	{
		        		postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
						System.out.println("post return is : "+ postret);
						System.out.println("post data is : "+ postdata);
						
						
						if(validatejson.isJSONValidstandard(postret))
				        {
							jsonObject = new JSONObject(postret);
							
							if(!jsonObject.getBoolean("STATUS"))
							{
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							if (jsonObject.get("MESSAGE") instanceof String) {
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							
							if(jsonObject.getBoolean("STATUS"))
							{
								item.setRetmlid(jsonObject.getInt("MESSAGE"));
								
								dirupdate.setRetmlid(jsonObject.getInt("MESSAGE"));
								dirupdate.setIsmlgenerated(1);
								//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
							}
							else
						    {
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
								
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
						    }
				        }
						else
						{
							if(dirlocal.getRetmlid()>0)
							{
								dirupdate.setRetmlid(dirlocal.getRetmlid());
								dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
							}
							else
							{
								dirupdate.setIsmlgenerated(0);
								dirupdate.setRetmlid(0);
							}
							//dirupdate.setRetmlid(0);
							//dirupdate.setIsmlgenerated(0);
						}
		        	}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        }
	        service.getUpdatemlrtsaonly(headers.get("tenantID").get(0), dirupdate);
	        
		});
		return syncResult;
	}
	
	

	@RequestMapping(method=RequestMethod.GET,value="/syncdbdirectoryrtsatreeexist")
	public String getSyncdbdirrtsatreeexist(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		String headerTenant = headers.get("tenantID").get(0);
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		List<Directory> dirresult = service.getDirectoryvoicenotgenerated(headerTenant);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getApi();
		
		JSONObject postdata = new JSONObject();
		
		JSONObject faqstring = new JSONObject();
		Directory dirupdate =  new Directory();
		//Directory dirlocal =  new Directory();
		//int MaxIntent =0;
		String hprrettoken = "";
		String engAccesstoken ="";
		try {
			hprrettoken = hpr.ConnectGetToken(voiceapi+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
		jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObjecttoken.getString("access_token");
        System.out.println("token is :" + jsonObjecttoken.getString("access_token"));
		
        String engtoken = engAccesstoken;
		dirresult.forEach(item->{
			JSONObject enumstring = new JSONObject();
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headerTenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String hprret = "";
			
			String postret = "";
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			String validUri = validateUri(voiceapi+"/api/category/get/"+getmap);
			System.out.print("validUri :" +validUri);
			try {
				hprret = hpr.ConnectGetnoparam(validUri);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        
	        dirupdate.setDirectoryid(item.getDirectoryid());
			dirupdate.setCategorymode(item.getCategorymode());
			
			System.out.print("Searching for current directoryid :" +item.getDirectoryid());
			Directory dirlocal = service.getView(headerTenant,item.getDirectoryid());
			
			int retparID =0;
			if(item.getParentid()>0)
			{
				Directory dirfindone = service.getView(headerTenant,item.getParentid());
				if(dirfindone == null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
				}
				
				retparID = dirfindone.getReticategoryid();
			}
			else
			{
				retparID =0;
			}
			
			
			if(item.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = service.getView(headerTenant,item.getExtendenumcategoryid());
				if(direnum == null)
				{
					enumstring = null;
				}
				else
				{
					int retEnumID = direnum.getReticategoryid();
					String enumName = direnum.getName();
					String enumDescription = direnum.getDescription();
					String enumCatmode = direnum.getCategorymode();
					
					Directory direnumparent = null;
					direnumparent = service.getView(headerTenant,direnum.getParentid());
					int retEnumparID = direnumparent.getRetmlid();
					
					enumstring.put("id", retEnumID);
					enumstring.put("name", enumName);
					enumstring.put("parentid", retEnumparID);
					enumstring.put("description", enumDescription);
					enumstring.put("faq", item.getFaq());
					enumstring.put("mode", enumCatmode);
					enumstring.put("switching", item.getSwitching());
					enumstring.put("extendsEnumCategory", "null");
				}
				
			}
			
			//if(item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || item.getCategorymode().equals("DRILLDOWN") || item.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			//{
				enumstring = null;
			//}
			
			boolean isSBD = false;
			if(item.getCategorymode().equals("STANDARD") || item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("MEMORY"))
			{
				
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", item.getFaq());
				postdata.put("mode", item.getCategorymode());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
			}
			else
			{
				isSBD = true;
				faqstring.put("id", item.getFaq());
				faqstring.put("question", item.getQuestion());
				//faqstring.put("answer", item.getAnswer());
				faqstring.put("answer", ".");
				
				//postdata.put("access_token", engAccesstoken);
				//postdata.put("id", directory.getReticategoryid());
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", item.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
				
			}
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	jsonObject = new JSONObject(jsonString);
				if(jsonObject.getString("name") ==null)
				{
					//create baru ke dalam voice
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
					
					
					try {
						
						if(isSBD && retparID<=0)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
							System.out.print("cannot update : "+item.getName()+" directory");
						}
						else
						{
							postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
							System.out.println("post return is : "+ postret);
							
							if(validatejson.isJSONValidstandard(postret))
					        {
								jsonObject = new JSONObject(postret);
								
								if(!jsonObject.getBoolean("STATUS"))
								{
									throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								}
								
								if(jsonObject.getInt("MESSAGE") <=0 )
								{
									throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								}
								
								
								
								
								if(jsonObject.getBoolean("STATUS"))
								{
									//item.setReticategoryid(jsonObject.getInt("MESSAGE"));
									
									//Directory dirupdate =  new Directory();
									dirupdate.setReticategoryid(jsonObject.getInt("id"));
									//dirupdate.setIsmlgenerated(1);
									//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
								}
								else
							    {
									if(dirlocal.getReticategoryid()>0)
									{
										dirupdate.setReticategoryid(dirlocal.getReticategoryid());
										//dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
									}
									else
									{
										//dirupdate.setIsmlgenerated(0);
										dirupdate.setReticategoryid(dirlocal.getReticategoryid());
									}
									//dirupdate.setRetmlid(0);
									//dirupdate.setIsmlgenerated(0);
							    }
					        }
							else
							{
								if(dirlocal.getReticategoryid()>0)
								{
									dirupdate.setReticategoryid(dirlocal.getReticategoryid());
									//dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									//dirupdate.setIsmlgenerated(0);
									dirupdate.setReticategoryid(dirlocal.getReticategoryid());
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
						}
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else
				{
					/*Directory dirfindone = service.getView(headers.get("tenantID").get(0),item.getDirectoryid());
					if(dirfindone == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
					}
					*/
					System.out.print("Directory :" +item.getName() +"only update roocvthree");
					//update voice id in roocvthree datas
					//Directory dirupdate =  new Directory();					
					dirupdate.setReticategoryid(jsonObject.getInt("id"));
					//dirupdate.setIsmlgenerated(1);
					
					//postdata.put("id", dirupdate.getRetmlid());
					
					//check apakah ada perubahan data
					boolean isSame = false;
					if(item.getName().equals(jsonObject.getString("name")) && item.getSwitching().equals(jsonObject.getString("switching")) && item.getDescription().equals(jsonObject.getString("description")) && retparID==jsonObject.getInt("parentid"))
					{
						isSame = true;
					}
					
					
					/*
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
					else
					{
						if(!isSame)
						{
							try {
								postret = hpr.setPostData(voiceapi+"/api/category/update",postdata);
							} catch (KeyManagementException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (KeyStoreException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("post return is : "+ postret);
						}
						else
						{
							System.out.print("Directory : " + item.getName()+ "doesn't need any update,because all data is same");
						}
						
					}*/
					
					
					
				}
				//engAccesstoken = jsonObject.getString("access_token");
				System.out.print(jsonObject.getString("name") + " -" + jsonObject.getInt("id"));
	        }
	        else
	        {
	        	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid Category :" + item.getName());
	        	//create baru ke dalam ml
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
	        	
				try {
					
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
		        	else
		        	{
		        		postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
						System.out.println("post return is : "+ postret);
						System.out.println("post data is : "+ postdata);
						
						
						if(validatejson.isJSONValidstandard(postret))
				        {
							jsonObject = new JSONObject(postret);
							
							if(!jsonObject.getBoolean("STATUS"))
							{
								if(dirlocal.getReticategoryid()>0)
								{
									dirupdate.setReticategoryid(dirlocal.getReticategoryid());
									//dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									//dirupdate.setIsmlgenerated(0);
									dirupdate.setReticategoryid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							if (jsonObject.get("MESSAGE") instanceof String) {
								if(dirlocal.getReticategoryid()>0)
								{
									dirupdate.setReticategoryid(dirlocal.getReticategoryid());
									//dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									//dirupdate.setIsmlgenerated(0);
									dirupdate.setReticategoryid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							
							if(jsonObject.getBoolean("STATUS"))
							{
								item.setReticategoryid(jsonObject.getInt("MESSAGE"));
								
								dirupdate.setReticategoryid(jsonObject.getInt("MESSAGE"));
								//dirupdate.setIsmlgenerated(1);
								//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
							}
							else
						    {
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
								
								if(dirlocal.getReticategoryid()>0)
								{
									dirupdate.setReticategoryid(dirlocal.getReticategoryid());
									//dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									//dirupdate.setIsmlgenerated(0);
									dirupdate.setReticategoryid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
						    }
				        }
						else
						{
							if(dirlocal.getReticategoryid()>0)
							{
								dirupdate.setReticategoryid(dirlocal.getReticategoryid());
								//dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
							}
							else
							{
								//dirupdate.setIsmlgenerated(0);
								dirupdate.setReticategoryid(0);
							}
							//dirupdate.setRetmlid(0);
							//dirupdate.setIsmlgenerated(0);
						}
		        	}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        }
	        service.getUpdatertsaonly(headers.get("tenantID").get(0), dirupdate);
	        
		});
		return syncResult;
	}
	
	
	

	@RequestMapping(method=RequestMethod.GET,value="/syncdbdirectorymlrtsatest")
	public String getSyncdbdirmlrtsatest(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		String headerTenant = headers.get("tenantID").get(0);
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		//List<Directory> dirresult = service.getDirectoryvoicenotgenerated(headerTenant);
		List<Directory> dirresult = service.getDirectorynotgenml(headerTenant);
		
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		
		
		JSONObject postdata = new JSONObject();
		
		
		
		String engAccesstoken ="";
		
		
        
        
        String engtoken = engAccesstoken;
		dirresult.forEach(item->{
			
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headerTenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			
			
			System.out.print("Searching for current directoryid :" +item.getDirectoryid());
			Directory dirlocal = service.getView(headerTenant,item.getDirectoryid());
			System.out.print("Searching for current directory local :" +dirlocal);
			
			
			if(dirlocal ==null)
			{
				System.out.print("dirlocal is empty" );
			}
			
			int retparID =0;
			if(item.getParentid()>0)
			{
				Directory dirfindone = service.getView(headerTenant,item.getParentid());
				if(dirfindone == null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
				}
				
				retparID = dirfindone.getRetmlid();
			}
			else
			{
				retparID =0;
			}
			
			
			
			
			
		});
		return syncResult;
	}
	


	@RequestMapping(method=RequestMethod.GET,value="/syncdbdirectoryroocengine")
	public String getSyncdbdirroocengine(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		String  token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		
		String headerTenant = headers.get("tenantID").get(0);
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		List<Directory> dirresult = service.getDirectoryvoicenotgenerated(headerTenant);
		//List<Directory> dirresult = service.getDirectorynotgenml(headerTenant);
		
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		
		
		JSONObject postdata = new JSONObject();
		
		
		
		String engAccesstoken ="";
        String engtoken = engAccesstoken;
        String internaltoken = token;
		dirresult.forEach(item->{
			
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headerTenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			
			
			System.out.print("Searching for current directoryid :" +item.getDirectoryid());
			Directory dirlocal = service.getView(headerTenant,item.getDirectoryid());
			
			if(dirlocal ==null)
			{
				System.out.print("dirlocal is empty" );
			}
			
			System.out.print("Searching for current directory local :" +dirlocal.getName());
			
			Directory obj = new Directory();
			obj = dirlocal;
			
			trb.SetTrailRecord(internaltoken,obj);
			obj.setToken(internaltoken);
			//System.out.print("object is : "+obj);
			System.out.print("Directory id for Sync is : "+obj.getDirectoryid());
			String retData = service.getSaveroocenginecreatesync(headers.get("tenantID").get(0),obj);
			
			
			
			
			
			
		});
		return syncResult;
	}
	
	
	private String validateUri(String link)
	{
		URL url = null;
		try {
			   url = new URL(link);
			   System.out.println(link);
			} catch(MalformedURLException e) {
			   e.printStackTrace();
			}

			URI uri = null;
			try{
			   uri = new URI(url.toString());
			   System.out.println("uri :" + uri);
			} catch(URISyntaxException e) {
			   try {
			        uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
			                      url.getPort(), url.getPath(), url.getQuery(), 
			                      url.getRef());
			   } catch(URISyntaxException e1) {
			        e1.printStackTrace();
			   }
			}
			
			try {
			   url = uri.toURL();
			   System.out.println("uri to url :" + url.toString());
			} catch(MalformedURLException e) {
			   e.printStackTrace();
			}
		return url.toString();
	}




}
