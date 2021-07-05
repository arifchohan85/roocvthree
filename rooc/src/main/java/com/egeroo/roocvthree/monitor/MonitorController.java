package com.egeroo.roocvthree.monitor;



import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;




@RestController
@RequestMapping("/monitor")
public class MonitorController {
	private int ECID = 1;
	EngineCredentialService ecservice = new EngineCredentialService(); 
	HttpPostReq hpr = new HttpPostReq();
	ValidationJson validatejson = new ValidationJson(); 
	@Autowired
    private MonitorService service;

	@RequestMapping(method=RequestMethod.GET,value="/listmtronqueue")
	public Monitor getMtronqueue(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		Monitor result = service.getQueue(headers.get("tenantID").get(0));
		return result;
		/*Monitor obj = new Monitor();
		String postret = "";
		String getret ="";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		String chnlToken ="";
		JSONObject pdcomposer = new JSONObject();
        
    	pdcomposer.put("username", result.getChannelusername());
    	pdcomposer.put("password", result.getChannelPassword());
		
    	try {
    		postret = hpr.setPostData(result.getChannelapi()+"/auth/login",pdcomposer);
    		
    		String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("token"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			
			if(jsonObject.getString("token") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			chnlToken = jsonObject.getString("token");
	        System.out.println("channel token is :" + jsonObject.getString("token"));
    		
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
    	
    	boolean isEmptychnltoken = chnlToken == null || chnlToken.trim().length() == 0;
    	if(isEmptychnltoken)
    	{
    		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid channel access token.");
    	}
    	
    	String uri = result.getChannelapi() +"/monitor/onqueue"+"?token="+chnlToken;
    	
    	try {
			getret = hpr.ConnectGetnoparam(uri);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String jsonString = getret;
    	JSONObject jsonObject;
		if(validatejson.isJSONValidstandard(jsonString))
		{
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("count"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to process.");
			}
			else
			{
				obj.setCount(jsonObject.getInt("count"));
			}
		}
		
		
		//Monitor result = null;
		return obj;
		*/
	}



	@RequestMapping(method=RequestMethod.GET,value="/listmtronhandle")
	public Monitor getMtronhandle(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		Monitor result = service.getHandle(headers.get("tenantID").get(0));
		return result;
		/*Monitor obj = new Monitor();
		String postret = "";
		String getret ="";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		String chnlToken ="";
		JSONObject pdcomposer = new JSONObject();
        
    	pdcomposer.put("username", result.getChannelusername());
    	pdcomposer.put("password", result.getChannelPassword());
		
    	try {
    		postret = hpr.setPostData(result.getChannelapi()+"/auth/login",pdcomposer);
    		
    		String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("token"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			
			if(jsonObject.getString("token") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			chnlToken = jsonObject.getString("token");
	        System.out.println("channel token is :" + jsonObject.getString("token"));
    		
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
    	
    	boolean isEmptychnltoken = chnlToken == null || chnlToken.trim().length() == 0;
    	if(isEmptychnltoken)
    	{
    		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid channel access token.");
    	}
    	
    	String uri = result.getChannelapi() +"/monitor/onhandle"+"?token="+chnlToken;
    	
    	try {
			getret = hpr.ConnectGetnoparam(uri);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String jsonString = getret;
    	JSONObject jsonObject;
		if(validatejson.isJSONValidstandard(jsonString))
		{
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("count"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to process.");
			}
			else
			{
				obj.setCount(jsonObject.getInt("count"));
			}
		}
		
		
		//Monitor result = null;
		return obj;
		*/
	}



	@RequestMapping(method=RequestMethod.GET,value="/listmtragentavailable")
	public Monitor getMtragentavailable(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		Monitor obj = new Monitor();
		String postret = "";
		String getret ="";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		String chnlToken ="";
		JSONObject pdcomposer = new JSONObject();
        
    	pdcomposer.put("username", result.getChannelusername());
    	pdcomposer.put("password", result.getChannelPassword());
		
    	try {
    		postret = hpr.setPostData(result.getChannelapi()+"/auth/login",pdcomposer);
    		
    		String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("token"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			
			if(jsonObject.getString("token") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			chnlToken = jsonObject.getString("token");
	        System.out.println("channel token is :" + jsonObject.getString("token"));
    		
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
    	
    	boolean isEmptychnltoken = chnlToken == null || chnlToken.trim().length() == 0;
    	if(isEmptychnltoken)
    	{
    		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid channel access token.");
    	}
    	
    	String uri = result.getChannelapi() +"/monitor/agentavailable"+"?token="+chnlToken;
    	
    	try {
			getret = hpr.ConnectGetnoparam(uri);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String jsonString = getret;
    	JSONObject jsonObject;
		if(validatejson.isJSONValidstandard(jsonString))
		{
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("count"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to process.");
			}
			else
			{
				obj.setCount(jsonObject.getInt("count"));
			}
		}
		
		
		//Monitor result = null;
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listmtragentonchat")
	public Monitor getMtragentonchat(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		Monitor obj = new Monitor();
		String postret = "";
		String getret ="";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		String chnlToken ="";
		JSONObject pdcomposer = new JSONObject();
        
    	pdcomposer.put("username", result.getChannelusername());
    	pdcomposer.put("password", result.getChannelPassword());
		
    	try {
    		postret = hpr.setPostData(result.getChannelapi()+"/auth/login",pdcomposer);
    		
    		String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("token"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			
			if(jsonObject.getString("token") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
			}
			chnlToken = jsonObject.getString("token");
	        System.out.println("channel token is :" + jsonObject.getString("token"));
    		
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
    	
    	boolean isEmptychnltoken = chnlToken == null || chnlToken.trim().length() == 0;
    	if(isEmptychnltoken)
    	{
    		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid channel access token.");
    	}
    	
    	String uri = result.getChannelapi() +"/monitor/agentonchat"+"?token="+chnlToken;
    	
    	try {
			getret = hpr.ConnectGetnoparam(uri);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String jsonString = getret;
    	JSONObject jsonObject;
		if(validatejson.isJSONValidstandard(jsonString))
		{
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("count"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to process.");
			}
			else
			{
				obj.setCount(jsonObject.getInt("count"));
			}
		}
		
		
		//Monitor result = null;
		return obj;
	}
	

	
	



}
