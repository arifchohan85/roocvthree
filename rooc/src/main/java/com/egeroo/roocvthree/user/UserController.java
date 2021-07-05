package com.egeroo.roocvthree.user;

import java.io.IOException;
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
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;


@RestController
@RequestMapping("/user")
public class UserController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	private int ECID = 1;
	HttpPostReq hpr = new HttpPostReq();
	
	@Autowired
    private UserService service;
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService();
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<User> getIndex(@RequestHeader HttpHeaders headers) {
		List<User> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public User getView(@RequestHeader HttpHeaders headers,int userid) {
		User result = service.getView(headers.get("tenantID").get(0),userid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody User obj) {
		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {
            //System.out.println("A User with name " + user.getName() + " already exist");
            //return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			//return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			/*JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("message","Username is already taken");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			return "{" + "message : " + "Username is already taken" + "}";
        }
		else
		{
			
			//Base objcred = 
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
			/*if(Integer.parseInt(retData)>0)
			{
				trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "User", "Create");
			}*/
			return retData;
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	

	@RequestMapping(method=RequestMethod.POST,value="/createldap")
	public String getCreateldap(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody User obj) {
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		JSONObject ldapcomposer = new JSONObject();
        
		ldapcomposer.put("UserID", obj.getUsername());
		//ldapcomposer.put("Password", obj.getEncpassword());
		ldapcomposer.put("ApplicationID", result.getChannelPassword());
		try {
			
			String ldapApi = result.getLdapapi();
    		String clientID = result.getClientid();
    		postret = hpr.setPostDataldap(ldapApi+"/ad-gateways/full-ldap-path",clientID,ldapcomposer);
    		
    		//http://10.20.200.140:9407/ad-gateways/verify3
    		
    		String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("OutputSchema"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username from ldap");
			}
			
			System.out.println("Has Schema");
	    	JSONObject jsonObjectins = jsonObject.getJSONObject("OutputSchema");
	    	if(jsonObjectins.has("Status"))
	    	{
	    		System.out.println("Has Status");
	    		System.out.println(jsonObjectins.getString("Status"));
	    		if(!jsonObjectins.getString("Status").equals("0"))
	    		{
	    			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username from ldap");
	    		}
	    	}
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
		
		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {
            //System.out.println("A User with name " + user.getName() + " already exist");
            //return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			//return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			/*JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("message","Username is already taken");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			return "{" + "message : " + "Username is already taken" + "}";
        }
		else
		{
			
			//Base objcred = 
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
			/*if(Integer.parseInt(retData)>0)
			{
				trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "User", "Create");
			}*/
			return retData;
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/login")
	public String getLogin(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody User obj) {
		if (service.isLoginExist(headers.get("tenantID").get(0),obj)) {
			return "{" + "message : " + "Valid!!" + "}";
        }
		else
		{
			//return service.getCreate(headers.get("tenantID").get(0),obj);
			return "{" + "message : " + "Bad Credential!!" + "}";
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody User obj) {
		//UserRole result = 
		//System.out.println("token is :" + headers.get("access_token").get(0));
		//Base objcred = 
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
		
		/*obj.setCreatedby(objcred.getCreatedby());
		obj.setCreatedtime(objcred.getCreatedtime());
		obj.setUpdatedby(objcred.getUpdatedby());
		obj.setUpdatedtime(objcred.getUpdatedtime());*/
		
		String retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		/*if(Integer.parseInt(retData)>0)
		{
			trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "User", "Update");
		}*/
		return  retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/updateldap")
	public String getUpdateldap(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody User obj) {
		//UserRole result = 
		//System.out.println("token is :" + headers.get("access_token").get(0));
		//Base objcred = 
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		JSONObject ldapcomposer = new JSONObject();
        
		ldapcomposer.put("UserID", obj.getUsername());
		//ldapcomposer.put("Password", obj.getEncpassword());
		ldapcomposer.put("ApplicationID", result.getChannelPassword());
		try {
			
			String ldapApi = result.getLdapapi();
    		String clientID = result.getClientid();
    		postret = hpr.setPostDataldap(ldapApi+"/ad-gateways/full-ldap-path",clientID,ldapcomposer);
    		
    		//http://10.20.200.140:9407/ad-gateways/verify3
    		
    		String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("OutputSchema"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username from ldap");
			}
			
			System.out.println("Has Schema");
	    	JSONObject jsonObjectins = jsonObject.getJSONObject("OutputSchema");
	    	if(jsonObjectins.has("Status"))
	    	{
	    		System.out.println("Has Status");
	    		System.out.println(jsonObjectins.getString("Status"));
	    		if(!jsonObjectins.getString("Status").equals("0"))
	    		{
	    			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username from ldap");
	    		}
	    	}
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
		
		/*obj.setCreatedby(objcred.getCreatedby());
		obj.setCreatedtime(objcred.getCreatedtime());
		obj.setUpdatedby(objcred.getUpdatedby());
		obj.setUpdatedtime(objcred.getUpdatedtime());*/
		
		String retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		/*if(Integer.parseInt(retData)>0)
		{
			trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "User", "Update");
		}*/
		return  retData;
	}

}
