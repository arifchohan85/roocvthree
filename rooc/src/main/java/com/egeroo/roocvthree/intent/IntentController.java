package com.egeroo.roocvthree.intent;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.directory.Directory;
import com.egeroo.roocvthree.directory.DirectoryService;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;





@RestController
@RequestMapping("/intent")
public class IntentController {
	
	@Autowired
    private IntentService service;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	EngineCredentialService ecservice = new EngineCredentialService();
	private int ECID = 1;
	HttpPostReq hpr = new HttpPostReq();
	ValidationJson validatejson = new ValidationJson(); 
	DirectoryService dirservice = new DirectoryService();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Intent> getIndex(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}

	@RequestMapping(method=RequestMethod.GET)
	public List<Map> getIndexV3(@RequestHeader HttpHeaders headers,@RequestParam("lite") boolean lite) {
		List<Map> result = service.getIndexV3(headers.get("tenantID").get(0), lite);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/extractintent")
	public List<Intent> getExtractintent(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getExtractintent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/intentdirrecursive")
	public List<Intent> getIntentrecursive(@RequestHeader HttpHeaders headers,int directoryid) {
		List<Intent> result = service.getIntentdirrecursive(headers.get("tenantID").get(0),directoryid);
		if(result == null)
		{
			throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "No Data");
		}
		result.forEach(item->{
			System.out.println(item.getIntentid());
		});
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listintent")
	public List<Intent> getIntent(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getIntent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listintentict")
	public List<Intent> getIntentict(@RequestHeader HttpHeaders headers) {
		List<Intent> result = service.getIntent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listintentdirectory")
	public List<Intent> getIntentdir(@RequestHeader HttpHeaders headers,int directoryid) {
		List<Intent> result = service.getIntentdir(headers.get("tenantID").get(0),directoryid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Intent getView(@RequestHeader HttpHeaders headers,int intentid) {
		Intent result = service.getView(headers.get("tenantID").get(0),intentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/delete")
	public Intent getDelete(@RequestHeader HttpHeaders headers,int intentid) {
		Intent result = service.getDelete(headers.get("tenantID").get(0),intentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/deletebydirectory")
	public List<Intent> getDeletebydirectory(@RequestHeader HttpHeaders headers,int directoryid) {
		List<Intent> result = service.getDeleteintentbydirectory(headers.get("tenantID").get(0),directoryid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/viewquestion")
	public Intent getViewquestion(@RequestHeader HttpHeaders headers,String question) {
		Intent result = service.getViewquestion(headers.get("tenantID").get(0),question);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Intent obj) {
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
		String retData = service.getCreateengine(headers.get("tenantID").get(0),obj);
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Intent obj) {
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
	

	@RequestMapping(method=RequestMethod.GET,value="/syncdbintentvoice")
	public String getSyncdbintentvoice(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		List<Intent> intresult = service.getIntentnotgeneratedvoice(headerTenant);
		//return result;
		if(intresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getVoiceapi();
		
		JSONObject postdata = new JSONObject();
		
		
		//int MaxIntent =0;
		intresult.forEach(item->{
			String lastinsertuserid = "0";
			
			//String engAccesstoken ="";
			System.out.print("Question/Intent :" +item.getQuestion());
			String hprret = "";
			String postret = "";
			
			try {
				hprret = hpr.ConnectGetnoparam(voiceapi+"/api/faq/id/"+item.getIntentid());
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        Intent intentupdate =  new Intent();
	        
	        Directory dirone = new Directory();
	    	dirone = dirservice.getView(headerTenant, item.getDirectoryid());
	    	if(dirone ==  null)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	int retparID = dirone.getRetvoiceid();
	    	
	    	if (retparID<=0)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory ID for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	
	    	//postdata.put("access_token", engAccesstoken);
			postdata.put("id", item.getIntentid());
			//postdata.put("categoryid", retparID);
			postdata.put("question", item.getQuestion());
			//postdata.put("answer", item.getAnswer());
			postdata.put("answer", ".");
			postdata.put("category", retparID);
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	//JSONArray jsonarray = new JSONArray(jsonString);
	        	//for (int i = 0; i < jsonarray.length(); i++) {
				    JSONObject jsonobject = new JSONObject(jsonString);//jsonarray.getJSONObject(i);
				    
				    if(!jsonobject.has("id"))
					{
				    	
						JSONArray ja = new JSONArray();
						ja.put(postdata);
						
						try {
							postret = hpr.setPostDataarray(voiceapi+"/api/faq/add",ja);
							System.out.println("post return is : "+ postret);
							
							//int cntErr =0;
							//String errMessage="";
							if(validatejson.isJSONValidarray(postret))
							{
								JSONArray jsonarrayadd = new JSONArray(postret);
								for (int j = 0; j < jsonarrayadd.length(); j++) {
								    JSONObject jsonobjectadd = jsonarrayadd.getJSONObject(j);
								    
								    if(!jsonobjectadd.has("faqId"))
									{
								    	//cntErr++;
								    	//errMessage = jsonobject.getString("STATUS");
								    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
								    	intentupdate.setRetvoiceid(0);
								    	intentupdate.setIsvoicegenerated(0);
									}
								    else
								    {
								    	String faqId = jsonobjectadd.getString("faqId");
								    	intentupdate.setRetvoiceid(Integer.parseInt(faqId));
								    	intentupdate.setIsvoicegenerated(1);
								    }
								}
							}
						} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
								| IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				    else if(jsonobject.has("id"))
				    {
				    	String faqId = Integer.toString(jsonobject.getInt("id"));
					    //String url = jsonobject.getString("url");
					    if(Integer.parseInt(faqId)>0)
					    {
					    	JSONObject pdcomposer = new JSONObject();
					        
					    	
					    	//intentupdate.setDirectoryid(item.getDirectoryid());
					    	intentupdate.setRetvoiceid(jsonobject.getInt("id"));
					    	intentupdate.setIsvoicegenerated(1);
							
					    }
					    else
					    {
					    	throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
					    }
				    }
				//}
	        }
	        else
	        {
	        	JSONArray ja = new JSONArray();
				ja.put(postdata);
				
				try {
					postret = hpr.setPostDataarray(voiceapi+"/api/faq/add",ja);
					System.out.println("post return is : "+ postret);
					
					//int cntErr =0;
					//String errMessage="";
					if(validatejson.isJSONValidarray(postret))
					{
						JSONArray jsonarrayadd = new JSONArray(postret);
						for (int j = 0; j < jsonarrayadd.length(); j++) {
						    JSONObject jsonobjectadd = jsonarrayadd.getJSONObject(j);
						    
						    if(!jsonobjectadd.has("faqId"))
							{
						    	//cntErr++;
						    	//errMessage = jsonobject.getString("STATUS");
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
						    	intentupdate.setRetvoiceid(0);
						    	intentupdate.setIsvoicegenerated(0);
							}
						    else
						    {
						    	String faqId = jsonobjectadd.getString("faqId");
						    	intentupdate.setRetvoiceid(Integer.parseInt(faqId));
						    	intentupdate.setIsvoicegenerated(1);
						    }
						}
					}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
						| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        //save update only voice
		    intentupdate.setIntentid(item.getIntentid());
	    	lastinsertuserid = service.getUpdatevoiceonly(headerTenant, intentupdate);
	        
	        
		});
		return syncResult;
	}
	


	@RequestMapping(method=RequestMethod.GET,value="/syncdbintentmlrtsa")
	public String getSyncdbintentmlrtsa(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		//List<Intent> intresult = service.getIntentnotgeneratedvoice(headerTenant);
		List<Intent> intresult = service.getIntentnotgenml(headerTenant);
		
		//return result;
		if(intresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getMlapi();
		
		JSONObject postdata = new JSONObject();
		
		
		//int MaxIntent =0;
		intresult.forEach(item->{
			String lastinsertuserid = "0";
			
			//String engAccesstoken ="";
			System.out.print("Question/Intent :" +item.getQuestion());
			String hprret = "";
			String postret = "";
			
			try {
				hprret = hpr.ConnectGetnoparam(voiceapi+"/api/faq/id/"+item.getIntentid());
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        Intent intentupdate =  new Intent();
	        
	        Directory dirone = new Directory();
	    	dirone = dirservice.getView(headerTenant, item.getDirectoryid());
	    	if(dirone ==  null)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	int retparID = dirone.getRetmlid();
	    	
	    	if (retparID<=0)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory ID for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	
	    	//postdata.put("access_token", engAccesstoken);
			postdata.put("id", item.getIntentid());
			//postdata.put("categoryid", retparID);
			postdata.put("question", item.getQuestion());
			//postdata.put("answer", item.getAnswer());
			postdata.put("answer", ".");
			postdata.put("category", retparID);
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	//JSONArray jsonarray = new JSONArray(jsonString);
	        	//for (int i = 0; i < jsonarray.length(); i++) {
				    JSONObject jsonobject = new JSONObject(jsonString);//jsonarray.getJSONObject(i);
				    
				    if(!jsonobject.has("id"))
					{
				    	
						JSONArray ja = new JSONArray();
						ja.put(postdata);
						
						try {
							postret = hpr.setPostDataarray(voiceapi+"/api/faq/add",ja);
							System.out.println("post return is : "+ postret);
							
							//int cntErr =0;
							//String errMessage="";
							if(validatejson.isJSONValidarray(postret))
							{
								JSONArray jsonarrayadd = new JSONArray(postret);
								for (int j = 0; j < jsonarrayadd.length(); j++) {
								    JSONObject jsonobjectadd = jsonarrayadd.getJSONObject(j);
								    
								    if(!jsonobjectadd.has("faqId"))
									{
								    	//cntErr++;
								    	//errMessage = jsonobject.getString("STATUS");
								    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
								    	intentupdate.setRetmlid(0);
								    	intentupdate.setIsmlgenerated(0);
									}
								    else
								    {
								    	String faqId = jsonobjectadd.getString("faqId");
								    	intentupdate.setRetmlid(Integer.parseInt(faqId));
								    	intentupdate.setIsmlgenerated(1);
								    }
								}
							}
						} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
								| IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				    else if(jsonobject.has("id"))
				    {
				    	String faqId = Integer.toString(jsonobject.getInt("id"));
					    //String url = jsonobject.getString("url");
					    if(Integer.parseInt(faqId)>0)
					    {
					    	JSONObject pdcomposer = new JSONObject();
					        
					    	
					    	//intentupdate.setDirectoryid(item.getDirectoryid());
					    	intentupdate.setRetmlid(jsonobject.getInt("id"));
					    	intentupdate.setIsmlgenerated(1);
							
					    }
					    else
					    {
					    	throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
					    }
				    }
				//}
	        }
	        else
	        {
	        	JSONArray ja = new JSONArray();
				ja.put(postdata);
				
				try {
					postret = hpr.setPostDataarray(voiceapi+"/api/faq/add",ja);
					System.out.println("post return is : "+ postret);
					
					//int cntErr =0;
					//String errMessage="";
					if(validatejson.isJSONValidarray(postret))
					{
						JSONArray jsonarrayadd = new JSONArray(postret);
						for (int j = 0; j < jsonarrayadd.length(); j++) {
						    JSONObject jsonobjectadd = jsonarrayadd.getJSONObject(j);
						    
						    if(!jsonobjectadd.has("faqId"))
							{
						    	//cntErr++;
						    	//errMessage = jsonobject.getString("STATUS");
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
						    	intentupdate.setRetmlid(0);
						    	intentupdate.setIsmlgenerated(0);
							}
						    else
						    {
						    	String faqId = jsonobjectadd.getString("faqId");
						    	intentupdate.setRetmlid(Integer.parseInt(faqId));
						    	intentupdate.setIsmlgenerated(1);
						    }
						}
					}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
						| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        //save update only voice
		    intentupdate.setIntentid(item.getIntentid());
	    	lastinsertuserid = service.getUpdatemlrtsaonly(headerTenant, intentupdate);
	        
	        
		});
		return syncResult;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/syncdbintentroocengine")
	public String getSyncintentroocengine(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		List<Intent> intresult = service.getIntentnotgeneratedvoice(headerTenant);
		//return result;
		if(intresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getApi();
		
		JSONObject postdata = new JSONObject();
		
		
		//int MaxIntent =0;
		String internaltoken = token;
		intresult.forEach(item->{
			String lastinsertuserid = "0";
			
			//String engAccesstoken ="";
			System.out.print("Question/Intent :" +item.getQuestion());
			String hprret = "";
			String postret = "";
			
			try {
				hprret = hpr.ConnectGetnoparam(voiceapi+"/intent/"+item.getIntentid());
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        Intent intentupdate =  new Intent();
	        
	        Directory dirone = new Directory();
	    	dirone = dirservice.getView(headerTenant, item.getDirectoryid());
	    	if(dirone ==  null)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	int retparID = dirone.getParentid();
	    	
	    	if (retparID<=0)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory ID for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	
	    	boolean iswantToUpdate = false;
	    	
	    	if(dirone.getCategorymode().equals("STANDARD"))
	    	{
	    		iswantToUpdate = true;
	    	}
	    	else if(dirone.getCategorymode().equals("DRILLDOWN"))
	    	{
	    		if(dirone.getName().equals(item.getQuestion()))
	    		{
	    			iswantToUpdate = false;
	    		}
	    		else
	    		{
	    			iswantToUpdate = true;
	    		}
	    		
	    	}
	    	
	    	//kita lakukan save ke engine,ingat id intent tidak boleh berubah
    		//yang kita save hanya yang standard,karena yang standard dan branching auto ada datanya,waktu sync directory
    		System.out.print("Searching for current intentid :" +item.getIntentid());
			Intent intentlocal = service.getView(headerTenant,item.getIntentid());
			System.out.print("Searching for current intent local :" +intentlocal);
			
			
			if(intentlocal ==null)
			{
				System.out.print("intentlocal is empty" );
			}
			
			Intent obj = new Intent();
			obj = intentlocal;
			
			trb.SetTrailRecord(internaltoken,obj);
			obj.setToken(internaltoken);
			//System.out.print("object is : "+obj);
			System.out.print("Intent id for Sync is : "+obj.getIntentid());
	    	
			boolean isAllowupdate = false;
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	//JSONArray jsonarray = new JSONArray(jsonString);
	        	//for (int i = 0; i < jsonarray.length(); i++) {
	        		
				    JSONObject jsonobject = new JSONObject(jsonString);//jsonarray.getJSONObject(i);
				    if(jsonobject.has("id") && Integer.parseInt(jsonobject.getString("id"))>0)
				    {
				    	//data ada pada engine,kita update engine nya
				    	if(iswantToUpdate)
				    	{
				    		//.getUpdatedatasyncroocengine(headerTenant, obj);
				    		isAllowupdate = true;
				    	}
				    	else
				    	{
				    		System.out.print("Intentid : " + obj.getIntentid() + " is not standard intent or drilldown parent");
				    	}
				    }
				    else if(jsonobject.has("id") && Integer.parseInt(jsonobject.getString("id"))<=0)
					{
						//berarti data tidak ada di engine,kita create
				    	if(iswantToUpdate)
				    	{
				    		//service.getUpdatesavesyncroocengine(headerTenant, obj);
				    		isAllowupdate = true;
				    	}
				    	else
				    	{
				    		System.out.print("Intentid : " + obj.getIntentid() + " is not standard intent or drilldown parent");
				    	}
					}
				    else
				    {
				    	//tidak ada object id,kita create
				    	//berarti data tidak ada di engine
				    	if(iswantToUpdate)
				    	{
				    		//service.getUpdatesavesyncroocengine(headerTenant, obj);
				    		isAllowupdate = true;
				    	}
				    	else
				    	{
				    		System.out.print("Intentid : " + obj.getIntentid() + " is not standard intent or drilldown parent");
				    	}
				    }
				    
				    /*else if(jsonobject.has("id"))
				    {
				    	//jika ada id yang sama pada engine maka kita update engine nya yah
				    	if(iswantToUpdate)
				    	{
				    		service.getUpdatedatasyncroocengine(headerTenant, obj);
				    		boolean isAllowupdate = true;
				    	}
				    	else
				    	{
				    		System.out.print("Intentid : " + obj.getIntentid() + " is not standard intent");
				    	}
				    }*/
				//}
	        }
	        else
	        {
	        	//berarti data tidak ada di engine
		    	if(iswantToUpdate)
		    	{
		    		//service.getUpdatesavesyncroocengine(headerTenant, obj);
		    		isAllowupdate = true;
		    	}
		    	else
		    	{
		    		System.out.print("Intentid : " + obj.getIntentid() + " is not standard intent or drilldown parent");
		    	}
	        }
	        
	        //save update only voice
		    //obj.setIntentid(item.getIntentid());
	        if(isAllowupdate)
	        {
	        	lastinsertuserid = service.getUpdatesavesyncroocengine(headerTenant, obj);
	        }
	        else
	        {
	        	lastinsertuserid = "0,no data save";
	        }
	    	
	        
	        
		});
		return syncResult;
	}
	
	
}
