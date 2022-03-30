package com.egeroo.roocvthree.composer;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;




@Service
public class ComposerService {
	
	HttpPostReq hpr = new HttpPostReq();
	private int ECID = 1;
	ValidationJson validatejson = new ValidationJson(); 
	
	@Autowired
	private EngineCredentialService ecservice;

	/*
	public ComposerRequest getCreate(String tenant,ComposerRequest obj,String token) 
	{
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(tenant,this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data creds found.");
        }
		
		JSONObject jsonobj = new JSONObject();
		
		jsonobj.put("id", obj.getId());
		jsonobj.put("type", obj.getType());
		
		JSONObject jsonobjdata = new JSONObject();
		jsonobjdata.put("customChannel", obj.getData().getCustomChannel());
		jsonobjdata.put("useCustomChannel", obj.getData().getUseCustomChannel());
		jsonobjdata.put("responseText", obj.getData().getResponseText());
		jsonobjdata.put("variation", obj.getData().getVariation());
		
		jsonobj.put("data", jsonobjdata);
    	
    	String locpostret = "";
		try {
			locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/response/"+obj.getIntentId(),jsonobj,tenant,token);
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
		
		
		System.out.println("composer post return is : "+ locpostret);
		
		if(validatejson.isJSONValidstandard(locpostret))
		{
			JSONObject jsonObjectlocal = new JSONObject(locpostret);
			
			if(jsonObjectlocal.has("id"))
			{
//				if(jsonObjectlocal.getString("status").equals("failed."))
//				{
//					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not created.");
//				}
//				else
//				{
					//throw new CoreException(HttpStatus.OK, "data save successfully");
					return obj;
//				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not saved : " + jsonObjectlocal.getString("message"));
			}
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid return");
		}
	}

	*/
	

	
	public JSONObject getCreate(String tenant,JSONObject obj,String token) 
	{
		System.out.println("==== JSONObject is ========");
		System.out.println(obj.toString());
		System.out.println("==== JSONObject ========");
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(tenant,this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data creds found.");
        }
		
		if(!obj.has("intentId"))
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no intentId key found.");
		}
		
		if(obj.has("intentId"))
		{
			String locpostret = "";
			try {
				locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/response/"+obj.get("intentId"),obj,tenant,token);
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
			
			System.out.println("composer post return is : "+ locpostret);
			
			if(validatejson.isJSONValidstandard(locpostret))
			{
				JSONObject jsonObjectlocal = new JSONObject(locpostret);
				
				if(jsonObjectlocal.has("id"))
				{
						return obj;
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not saved : " + jsonObjectlocal.getString("message"));
				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid return");
			}
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no intentId key found.");
		}
    	
	}

	
	/*
	public ComposerRequest getUpdate(String tenant,ComposerRequest obj,String token) 
	{
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(tenant,this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data creds found.");
        }
		
		JSONObject jsonobj = new JSONObject();
		
		jsonobj.put("id", obj.getId());
		jsonobj.put("type", obj.getType());
		
		JSONObject jsonobjdata = new JSONObject();
		jsonobjdata.put("customChannel", obj.getData().getCustomChannel());
		jsonobjdata.put("useCustomChannel", obj.getData().getUseCustomChannel());
		jsonobjdata.put("responseText", obj.getData().getResponseText());
		jsonobjdata.put("variation", obj.getData().getVariation());
		
		jsonobj.put("data", jsonobjdata);
    	
    	String locpostret = "";
		try {
			locpostret = hpr.setPutDataJSONObject(result.getLocalapi()+"/response/update",jsonobj,tenant,token);
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
		
		
		System.out.println("composer post return is : "+ locpostret);
		
		if(validatejson.isJSONValidstandard(locpostret))
		{
			JSONObject jsonObjectlocal = new JSONObject(locpostret);
			
			if(jsonObjectlocal.has("id"))
			{
//				if(jsonObjectlocal.getString("status").equals("failed."))
//				{
//					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not created.");
//				}
//				else
//				{
					//throw new CoreException(HttpStatus.OK, "data save successfully");
					return obj;
//				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not saved : " + jsonObjectlocal.getString("message"));
			}
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid return");
		}
	}
	*/
	
	
	
	public JSONObject getUpdate(String tenant,JSONObject obj,String token) 
	{
		
		System.out.println("==== JSONObject is ========");
		System.out.println(obj.toString());
		System.out.println("==== JSONObject ========");
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(tenant,this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data creds found.");
        }
		
		
		
		
    	if(obj.has("id"))
    	{
    		String locpostret = "";
    		try {
    			locpostret = hpr.setPutDataJSONObject(result.getLocalapi()+"/response/update",obj,tenant,token);
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
    		
    		System.out.println("composer post return is : "+ locpostret);
    		
    		if(validatejson.isJSONValidstandard(locpostret))
    		{
    			JSONObject jsonObjectlocal = new JSONObject(locpostret);
    			
    			if(jsonObjectlocal.has("id"))
    			{
    					
    					return obj;
    			}
    			else
    			{
    				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not saved : " + jsonObjectlocal.getString("message"));
    			}
    		}
    		else
    		{
    			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid return");
    		}
    	}
    	else
    	{
    		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no id key found.");
    	}
    	
		
		
		
	}

	
	
	public JSONArray getDelete(String tenant,String id,String token) 
	{
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(tenant,this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data creds found.");
        }
		
		
    	
    	String locpostret = "";
		try {
			locpostret = hpr.setGetlocaldata(result.getLocalapi()+"/response/"+id,tenant,token);
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
		
		
		System.out.println("composer post return is : "+ locpostret);
		
		
		if(validatejson.isJSONValidarray(locpostret))
		{
			boolean hasError = false;
			JSONArray jsonarray = new JSONArray(locpostret);
			for (int i = 0; i < jsonarray.length(); i++) {
			    JSONObject jsonobject = jsonarray.getJSONObject(i);
			    
			    if(!jsonobject.has("id"))
			    {
			    	hasError = true;
			    }
			}
			
			if(hasError)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not properly delete ");
			}
			else
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "data not saved : " + jsonObjectlocal.getString("message"));
				return jsonarray;
			}
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid return");
		}
	}

	
}
