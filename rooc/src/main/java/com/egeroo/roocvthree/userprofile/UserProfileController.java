package com.egeroo.roocvthree.userprofile;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
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
import com.egeroo.roocvthree.core.util.Util;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;

import com.egeroo.roocvthree.userrole.UserRole;
import com.egeroo.roocvthree.userrole.UserRoleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;




//@RequestMapping("/userprofile")
@RestController
@RequestMapping("/users")
public class UserProfileController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private UserProfileService service;
	
	RoocConfigService rcservice = new RoocConfigService();
	
	UserRoleService urservice = new UserRoleService(); 
	private int ECID = 1;
	HttpPostReq hpr = new HttpPostReq();
	ValidationJson validatejson = new ValidationJson();
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    
    Util util= new Util();
    
    
	@RequestMapping(method=RequestMethod.GET,value="/role")
	public List<Map> getRolev3(@RequestHeader HttpHeaders headers) {
		List<Map> result = urservice.getIndexv3(headers.get("tenantID").get(0));
		return result;
	}

	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<UserProfileIndex> getIndex(@RequestHeader HttpHeaders headers) {
		List<UserProfileIndex> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}

	@RequestMapping(method=RequestMethod.GET)
	public List<LinkedHashMap> getIndexv3(@RequestHeader HttpHeaders headers) {
		List<LinkedHashMap> result = service.getIndexv3(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/indexother")
	public List<UserProfile> getIndexother(@RequestHeader HttpHeaders headers) {
		List<UserProfile> result = service.getIndexother(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public UserProfileView getView(@RequestHeader HttpHeaders headers,int userprofileid) {
		UserProfileView result = service.getView(headers.get("tenantID").get(0),userprofileid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/viewldapdata")
	public UserProfile getViewldapdata(@RequestHeader HttpHeaders headers,HttpServletRequest request,String username)
	{
		UserProfile obj = new UserProfile();
		//get data user
		String hprret = "";
		String domain_id ="";
		String display_name ="";
		String job_title ="";
		String company_code ="";
		String kanwil_name ="";
		String branch_name ="";
		String kcp_name ="";
		String department ="";
		String area ="";
		
		obj.setUsername(username);
		
		try {
			//hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
			hprret = hpr.getGetdataldapbca(username,headers.get("tenantID").get(0));
			
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		System.out.println("return is get request :" +hprret);
		
		if(validatejson.isJSONValidstandard(hprret))
        {
			JSONObject jsonObjectget = new JSONObject(hprret);
			
		    if(!jsonObjectget.has("output_schema"))
			{
		    	System.out.println("! Schema");
		    	throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
			}
		    else
		    {
		    	System.out.println("Has Schema");
		    	
		    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
		    	for(int i = 0; i < jsonarray .length(); i++)
		    	{
		    	   JSONObject object3 = jsonarray.getJSONObject(i);
		    	   domain_id = object3.getString("domain_id");
		    	   display_name =object3.getString("display_name");
		    	   job_title =object3.getString("job_title");
		   		   company_code =object3.getString("company_code");
		   		   kanwil_name =object3.getString("kanwil_name");
		   		   branch_name =object3.getString("branch_name");
		   		   kcp_name =object3.getString("kcp_name");
		   		   department =object3.getString("departement");
		   		   area =object3.getString("area");
		    	  
		    	   System.out.println(domain_id);	
		    	}
		    }
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
		}

        obj.setName(display_name);

		//obj.setUsername(domain_id);
		obj.setAddress(branch_name);

		
		return obj;
	}
	
	

	@RequestMapping(method=RequestMethod.GET,value="/viewldapdatacoba")
	public UserProfile getViewldapdatacoba(@RequestHeader HttpHeaders headers,HttpServletRequest request,String username)
	{
		UserProfile obj = new UserProfile();
		//get data user
		/*String hprret = "";
		String domain_id ="";
		String display_name ="";
		String job_title ="";
		String company_code ="";
		String kanwil_name ="";
		String branch_name ="";
		String kcp_name ="";
		String department ="";
		String area ="";*/
		
		obj.setUsername(username);
		
		

        obj.setName("coba_display_name");

		//obj.setUsername(domain_id);
		obj.setAddress("coba_branch_name");

		return obj;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/viewprofile")
	public UserProfile getViewprofile(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
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
		UserProfile result = service.getViewbyuserid(headers.get("tenantID").get(0),userid);
		return result;
		
	}
		
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public UserProfile getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody UserProfile obj) {
		//String retMessage = "";
		//String prettyJsonString ="";
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//JsonParser jp = new JsonParser();
		String postret = "";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		/*if(headers.get("channel_token").get(0) == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no channel token found.");
		}
		
		if(headers.get("channel_token").get(0).equals("")) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no channel token found.");
		}*/
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "username is empty.");
		}
		
		//int roleid =0;
		boolean isEmptyrolename = obj.getRolename() == null || obj.getRolename().trim().length() == 0;
		
		if(isEmptyrolename)
		{
			if(obj.getRoleid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "role is empty.");
			}
			/*else
			{
				roleid = obj.getRoleid();
			}*/
			
		}
		else
		{
			UserRole urbyname = new UserRole();
			urbyname = urservice.getViewbyName(headers.get("tenantID").get(0),obj.getRolename());
			if (urbyname == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid role name.");
			}
			else
			{
				//roleid = urbyname.getRoleid();
				obj.setRoleid(urbyname.getRoleid());
			}
			
		}
		
		/*if(roleid<=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "role is empty.");
		}
		else
		{
			obj.setRoleid(roleid);
		}*/
		
		
		obj.setSource("rooc");
		
		int isUse3des =0;
		RoocConfig roocconfig = new RoocConfig();
		roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isUse3des");
		if (roocconfig == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
        }
		else
		{
			isUse3des = Integer.parseInt(roocconfig.getConfigvalue());
		}
		
		if(isUse3des==1)
		{
			roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"encryptionkey");
			if (roocconfig == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for encryptionkey found.");
	        }
			String encryptionkey = roocconfig.getConfigvalue();
			String decrypted_byte_oldpwd_only ="";
			
			byte[] hex2Byteoldpwd = util.hexToBytes(obj.getPassword());
			
	        try {
	        	decrypted_byte_oldpwd_only=util.doDecryptiononly(hex2Byteoldpwd,encryptionkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        boolean isEmptydecrpassword = decrypted_byte_oldpwd_only == null || decrypted_byte_oldpwd_only.trim().length() == 0;
	        if(!isEmptydecrpassword)
	        {
	        	obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(decrypted_byte_oldpwd_only)));
	        }
		}
		else
		{
			obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		}
		
		if (service.isUserExist(headers.get("tenantID").get(0),obj.getEmailaddress())) {
			//retMessage = "{" + "'message' : " + "'Username is already taken'" + "}";
			//return "{" + "message : " + "Username is already taken" + "}";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User already Exist.");
		}
		else
		{
			UserRole ur = new UserRole();
			ur = urservice.getView(headers.get("tenantID").get(0),obj.getRoleid());
			
			if(ur == null){
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
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
			
			
			
			JSONObject postdata = new JSONObject();
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			String userinfo = "{  'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
			JsonElement je = jp.parse(userinfo);
			String prettyJsonString = gson.toJson(je);
			
			//JSONObject userinfopost = new JSONObject();
	        
			//userinfopost.put("namalengkap", obj.getName());
			JSONObject namalengkap = new JSONObject();
			namalengkap.put("name", "namalengkap");
			namalengkap.put("secret", false);
			namalengkap.put("value", obj.getName());
			
			
			//userinfopost.put("alamat", obj.getAddress());
			JSONObject alamat = new JSONObject();
			alamat.put("name", "alamat");
			alamat.put("secret", false);
			alamat.put("value", obj.getAddress());
			
			
			//userinfopost.put("email", obj.getEmailaddress());
			JSONObject email = new JSONObject();
			email.put("name", "email");
			email.put("secret", false);
			email.put("value", obj.getEmailaddress());
			
			
			//userinfopost.put("active", obj.getIsactive());
			JSONObject active = new JSONObject();
			active.put("name", "active");
			active.put("secret", false);
			active.put("value", obj.getIsactive());
			
			//postdata.put("access_token", channel_token);
			postdata.put("username", obj.getEmailaddress());
			JSONObject username = new JSONObject();
			username.put("name", "username");
			username.put("secret", false);
			username.put("value", obj.getEmailaddress());
			
			
			postdata.put("password", obj.getPassword());
			JSONObject password = new JSONObject();
			password.put("name", "password");
			password.put("secret", false);
			password.put("value", obj.getPassword());
			
			
			postdata.put("email", obj.getEmailaddress());
			
			postdata.put("name", obj.getName());
			JSONObject name = new JSONObject();
			name.put("name", "name");
			name.put("secret", false);
			name.put("value", obj.getName());
			
			postdata.put("usertype", ur.getRolename().toLowerCase());
			JSONObject usertype = new JSONObject();
			usertype.put("name", "usertype");
			usertype.put("secret", false);
			usertype.put("value", ur.getRolename().toLowerCase());
			
			postdata.put("active", obj.getIsactive());
			
			JSONArray ja = new JSONArray();
			ja.put(namalengkap);
			ja.put(alamat);
			ja.put(email);
			ja.put(active);
			ja.put(username);
			ja.put(password);
			ja.put(name);
			ja.put(usertype);
			
			JSONObject userdata = new JSONObject();
			userdata.put("data", ja);
			
			postdata.put("info", userdata);
			//postdata.put("active", 1);
			System.out.print("Post data is :" + postdata);
			
			try {
				//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
				//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user",chnlToken,postdata);
				postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",chnlToken,postdata);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
			}
			
			
			System.out.println("post return is : "+ postret);
			
			String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			if(!jsonObject.has("id"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "error saving channel data.");
			}
			
			int usrchnlID = 0;
			usrchnlID = jsonObject.getInt("id");
			
			
			
			if(usrchnlID>0)
			{
				obj.setUserchannelid(usrchnlID);
				
				int isNeeduserapproval =0;
				//RoocConfig roocconfig = new RoocConfig();
				roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isNeeduserapproval");
				if (roocconfig == null) {
		            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
		        }
				else
				{
					isNeeduserapproval = Integer.parseInt(roocconfig.getConfigvalue());
				}
				
				int isApproved=0;
				if(isNeeduserapproval==1)
				{
					isApproved=0;
				}
				else
				{
					isApproved=1;
				}

				obj.setIsapproved(isApproved);
				
				
				UserProfile retData = service.getCreate(headers.get("tenantID").get(0),obj);
				if(retData.getUserprofileid()>0)
				{
					//throw new CoreException(HttpStatus.OK, "Data Saved");
//					int isNeeduserapproval =0;
//					//RoocConfig roocconfig = new RoocConfig();
//					roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isNeeduserapproval");
//					if (roocconfig == null) {
//			            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
//			        }
//					else
//					{
//						isNeeduserapproval = Integer.parseInt(roocconfig.getConfigvalue());
//					}
//					
//					int allowuserapproval=0;
//					if(isNeeduserapproval==1)
//					{
//						allowuserapproval=1;
//					}
//					UserProfileAttribute upattr = new UserProfileAttribute();
//					upattr.setIsapproved(allowuserapproval);
//					upattr.setUserprofileid(retData.getUserprofileid());
//					
//					UserProfileAttribute retDataattr = service.getCreateattr(headers.get("tenantID").get(0),upattr);
//					System.out.println(retDataattr);
					
					
					return retData;
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "error saving data.");
				}
				//trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "UserProfile", "Create");
				//retMessage = "{" + "'message' : " + "'" + retData + "'" + "}";
			}
			else
			{
				//retMessage = "{" + "'message' : " + "'Something went wrong'" + "}";
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
				
			}
			
			//return retData;
		}
		//JsonElement je = jp.parse(retMessage);
		//prettyJsonString = gson.toJson(je);
		//return retMessage;
		//UserRole result = 
		//service.getCreate(headers.get("tenantID").get(0),obj);
		
	}
	

	//create yang untuk bca
	@RequestMapping(method=RequestMethod.POST,value="/createldap")
	public UserProfile getCreateldap(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody UserProfile obj) {
		//String retMessage = "";
		//String prettyJsonString ="";
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//JsonParser jp = new JsonParser();
		String postret = "";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain cannot be empty.");
		}
		
		if(obj.getUsername().trim().length()<7)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found.");
		}
		
		if(obj.getUsername().trim().length()>7)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found.");
		}
		
		
		//int roleid =0;
		boolean isEmptyrolename = obj.getRolename() == null || obj.getRolename().trim().length() == 0;
		
		if(isEmptyrolename)
		{
			if(obj.getRoleid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Role cannot be empty.");
			}
			/*else
			{
				roleid = obj.getRoleid();
			}*/
			
		}
		else
		{
			UserRole urbyname = new UserRole();
			urbyname = urservice.getViewbyName(headers.get("tenantID").get(0),obj.getRolename());
			if (urbyname == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid role name.");
			}
			else
			{
				//roleid = urbyname.getRoleid();
				obj.setRoleid(urbyname.getRoleid());
			}
			
		}
		
		/*if(roleid<=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "role is empty.");
		}
		else
		{
			obj.setRoleid(roleid);
		}*/
		
		obj.setSource("ldapbca");
		
		
		
		int isUse3des =0;
		RoocConfig roocconfig = new RoocConfig();
		roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isUse3des");
		if (roocconfig == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
        }
		else
		{
			isUse3des = Integer.parseInt(roocconfig.getConfigvalue());
		}
		
		String pswd = "9335cc2fc785c26b";
		if(isUse3des==1)
		{
			//String pswd = "";
			//boolean isEmptypswd = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
			
			
			boolean isEmptypswd = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
			
			/*if(!isEmptypswd)
			{
				pswd = obj.getPassword();
			}*/
			
			if(!isEmptypswd)
			{
				roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"encryptionkey");
				if (roocconfig == null) {
		            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for encryptionkey found.");
		        }
				String encryptionkey = roocconfig.getConfigvalue();
				String decrypted_byte_oldpwd_only ="";
				
				byte[] hex2Byteoldpwd = util.hexToBytes(obj.getPassword());
				
		        try {
		        	decrypted_byte_oldpwd_only=util.doDecryptiononly(hex2Byteoldpwd,encryptionkey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        boolean isEmptydecrpassword = decrypted_byte_oldpwd_only == null || decrypted_byte_oldpwd_only.trim().length() == 0;
		        if(!isEmptydecrpassword)
		        {
		        	obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(decrypted_byte_oldpwd_only)));
		        }
			}
			else
			{
				//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
				roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"encryptionkey");
				if (roocconfig == null) {
		            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for encryptionkey found.");
		        }
				String encryptionkey = roocconfig.getConfigvalue();
				String decrypted_byte_oldpwd_only ="";
				
				byte[] hex2Byteoldpwd = util.hexToBytes(pswd);
				
		        try {
		        	decrypted_byte_oldpwd_only=util.doDecryptiononly(hex2Byteoldpwd,encryptionkey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        boolean isEmptydecrpassword = decrypted_byte_oldpwd_only == null || decrypted_byte_oldpwd_only.trim().length() == 0;
		        if(!isEmptydecrpassword)
		        {
		        	obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(decrypted_byte_oldpwd_only)));
		        }
			}
		}
		else
		{
			obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		}
		
		//check password to safe is it null
		boolean isEmptypswdsave = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		if(isEmptypswdsave)
		{
			obj.setPassword(pswd);
		}
		
		//get data user
		String hprret = "";
		String domain_id ="";
		String display_name ="";
		String job_title ="";
		String company_code ="";
		String kanwil_name ="";
		String branch_name ="";
		String kcp_name ="";
		String department ="";
		String area ="";
		
		try {
			hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		System.out.println("return is get request :" +hprret);
		
		if(validatejson.isJSONValidstandard(hprret))
        {
			JSONObject jsonObjectget = new JSONObject(hprret);
			
		    if(!jsonObjectget.has("output_schema"))
			{
		    	System.out.println("! Schema");
		    	throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found");
			}
		    else
		    {
		    	System.out.println("Has Schema");
		    	
		    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
		    	for(int i = 0; i < jsonarray .length(); i++)
		    	{
		    	   JSONObject object3 = jsonarray.getJSONObject(i);
		    	   domain_id = object3.getString("domain_id");
		    	   display_name =object3.getString("display_name");
		    	   job_title =object3.getString("job_title");
		   		   company_code =object3.getString("company_code");
		   		   kanwil_name =object3.getString("kanwil_name");
		   		   branch_name =object3.getString("branch_name");
		   		   kcp_name =object3.getString("kcp_name");
		   		   department =object3.getString("departement");
		   		   area =object3.getString("area");
		    	  
		    	   System.out.println(domain_id);	
		    	}
		    }
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found");
		}

        obj.setName(display_name);

		//obj.setUsername(domain_id);
		obj.setAddress(branch_name);

		
		//set email for ldap
		obj.setEmailaddress(obj.getUsername() +"@roocvthree.ai");
		
		String checkusername ="";
		boolean isEmptyun = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		//check data username or email
		if(isEmptyun)
		{
			checkusername = obj.getEmailaddress();
		}
		else
		{
			checkusername = obj.getUsername();
		}
		
		if (service.isUserExistun(headers.get("tenantID").get(0),checkusername)) {
			//retMessage = "{" + "'message' : " + "'Username is already taken'" + "}";
			//return "{" + "message : " + "Username is already taken" + "}";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User already Exist.");
		}
		else
		{
			UserRole ur = new UserRole();
			ur = urservice.getView(headers.get("tenantID").get(0),obj.getRoleid());
			
			if(ur == null){
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
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
			
			
			
			JSONObject postdata = new JSONObject();
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			//String userinfo = "{  'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
			String userinfo = "{  'display_name' : '" + display_name + "' , 'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
			
			JsonElement je = jp.parse(userinfo);
			String prettyJsonString = gson.toJson(je);
			
			//JSONObject userinfopost = new JSONObject();
			
			JSONObject namalengkap = new JSONObject();
			namalengkap.put("name", "namalengkap");
			namalengkap.put("secret", false);
			namalengkap.put("value", display_name);
			
			
			//userinfopost.put("alamat", obj.getAddress());
			JSONObject alamat = new JSONObject();
			alamat.put("name", "alamat");
			alamat.put("secret", false);
			alamat.put("value", branch_name);
			
			
			//userinfopost.put("email", obj.getEmailaddress());
			/*JSONObject email = new JSONObject();
			email.put("name", "email");
			email.put("secret", false);
			email.put("value", obj.getEmailaddress());*/
			
			
			//userinfopost.put("active", obj.getIsactive());
			postdata.put("active", obj.getIsactive());
			JSONObject active = new JSONObject();
			active.put("name", "active");
			active.put("secret", false);
			active.put("value", obj.getIsactive());
			
			//postdata.put("access_token", channel_token);
			postdata.put("username", obj.getUsername());
			JSONObject username = new JSONObject();
			username.put("name", "username");
			username.put("secret", false);
			username.put("value", obj.getUsername());
			
			
			/*postdata.put("password", obj.getPassword());
			JSONObject password = new JSONObject();
			password.put("name", "password");
			password.put("secret", false);
			password.put("value", obj.getPassword());*/
			
			
			//postdata.put("email", obj.getEmailaddress());
			
			postdata.put("name", obj.getName());
			JSONObject name = new JSONObject();
			name.put("name", "name");
			name.put("secret", false);
			//name.put("value", obj.getName());
			name.put("value", display_name);
			
			postdata.put("usertype", ur.getRolename().toLowerCase());
			JSONObject usertype = new JSONObject();
			usertype.put("name", "usertype");
			usertype.put("secret", false);
			usertype.put("value", ur.getRolename().toLowerCase());
			
			JSONObject jo_domain_id = new JSONObject();
			jo_domain_id.put("name", "domain_id");
			jo_domain_id.put("secret", false);
			jo_domain_id.put("value", domain_id);
			
			JSONObject jo_job_title = new JSONObject();
			jo_job_title.put("name", "job_title");
			jo_job_title.put("secret", false);
			jo_job_title.put("value", job_title);
			
			JSONObject jo_kanwil_name = new JSONObject();
			jo_kanwil_name.put("name", "kanwil_name");
			jo_kanwil_name.put("secret", false);
			jo_kanwil_name.put("value", kanwil_name);
			
			JSONObject jo_branch_name = new JSONObject();
			jo_branch_name.put("name", "branch_name");
			jo_branch_name.put("secret", false);
			jo_branch_name.put("value", branch_name);
			
			JSONObject jo_kcp_name = new JSONObject();
			jo_kcp_name.put("name", "kcp_name");
			jo_kcp_name.put("secret", false);
			jo_kcp_name.put("value", kcp_name);
			
			JSONObject jo_display_name = new JSONObject();
			jo_display_name.put("name", "display_name");
			jo_display_name.put("secret", false);
			jo_display_name.put("value", display_name);
			
			JSONObject jo_company_code = new JSONObject();
			jo_company_code.put("name", "company_code");
			jo_company_code.put("secret", false);
			jo_company_code.put("value", company_code);
			
			JSONObject jo_department = new JSONObject();
			jo_department.put("name", "department");
			jo_department.put("secret", false);
			jo_department.put("value", department);
			
			JSONObject jo_area = new JSONObject();
			jo_area.put("name", "area");
			jo_area.put("secret", false);
			jo_area.put("value", area);
			
			/*
			 obj.setName(display_name);
			obj.setCompany_code(company_code); c
		    obj.setBranch_name(branch_name); c
		    obj.setDisplay_name(display_name); c
		    obj.setDomain_id(domain_id); c
		    obj.setJob_title(job_title); c
		    obj.setKanwil_name(kanwil_name); c
		    obj.setKcp_name(kcp_name); c
		    obj.setDepartment(department);
			obj.setUsername(domain_id);
			obj.setAddress(branch_name);
			 * userinfobca.put("domain_id", domain_id);
			userinfobca.put("job_title", job_title);
			userinfobca.put("kanwil_name", kanwil_name);
			userinfobca.put("kcp_name", kcp_name);
			userinfobca.put("job_title", job_title);
			userinfobca.put("username", domain_id);*/
			
			//userinfobcaarray.put(userinfobca);
			//userinfopost.put("data", userinfobcaarray);
			
			
			JSONArray ja = new JSONArray();
			ja.put(namalengkap);
			ja.put(alamat);
			//ja.put(email);
			ja.put(active);
			ja.put(username);
			//ja.put(password);
			ja.put(name);
			ja.put(usertype);
			ja.put(jo_company_code);
			ja.put(jo_branch_name);
			ja.put(jo_display_name);
			ja.put(jo_domain_id);
			ja.put(jo_kanwil_name);
			ja.put(jo_kcp_name);
			ja.put(jo_department);
			ja.put(jo_job_title);
			ja.put(jo_area);
			
			JSONObject userdata = new JSONObject();
			userdata.put("data", ja);
			
			postdata.put("info", userdata);
			
			
			System.out.println("postdata is : " + postdata);
			
			try {
				//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
				//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user",chnlToken,postdata);
				postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",chnlToken,postdata);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
			}
			
			
			System.out.println("post return is : "+ postret);
			
			String jsonString = postret;
	        JSONObject jsonObject;
			
			jsonObject = new JSONObject(jsonString);
			if(!jsonObject.has("id"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "error saving channel data.");
			}
			
			int usrchnlID = 0;
			usrchnlID = jsonObject.getInt("id");
			
			
			
			if(usrchnlID>0)
			{
				obj.setUserchannelid(usrchnlID);
				UserProfile retData = service.getCreate(headers.get("tenantID").get(0),obj);
				if(retData.getUserprofileid()>0)
				{
					//throw new CoreException(HttpStatus.OK, "Data Saved");
					return retData;
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "error saving data.");
				}
				//trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "UserProfile", "Create");
				//retMessage = "{" + "'message' : " + "'" + retData + "'" + "}";
			}
			else
			{
				//retMessage = "{" + "'message' : " + "'Something went wrong'" + "}";
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
				
			}
			
			//return retData;
		}
		//JsonElement je = jp.parse(retMessage);
		//prettyJsonString = gson.toJson(je);
		//return retMessage;
		//UserRole result = 
		//service.getCreate(headers.get("tenantID").get(0),obj);
		
	}
	
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public UserProfile getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody UserProfile obj) {
		//UserRole result = 
		String postret = "";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		/*if(headers.get("channel_token").get(0) == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no channel token found.");
		}
		
		if(headers.get("channel_token").get(0).equals("")) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no channel token found.");
		}*/
		
		
		//int roleid =0;
		boolean isEmptyrolename = obj.getRolename() == null || obj.getRolename().trim().length() == 0;
		
		if(isEmptyrolename)
		{
			if(obj.getRoleid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "role is empty.");
			}
			/*else
			{
				roleid = obj.getRoleid();
			}*/
			
		}
		else
		{
			UserRole urbyname = new UserRole();
			urbyname = urservice.getViewbyName(headers.get("tenantID").get(0),obj.getRolename());
			if (urbyname == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid role name.");
			}
			else
			{
				//roleid = urbyname.getRoleid();
				obj.setRoleid(urbyname.getRoleid());
			}
			
		}
		
		/*if(roleid<=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "role is empty.");
		}
		else
		{
			obj.setRoleid(roleid);
		}*/
		
		obj.setSource("rooc");
		
		boolean isEmptypswd = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		RoocConfig roocconfig = new RoocConfig();
		if(!isEmptypswd)
		{
			int isUse3des =0;
			
			roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isUse3des");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
	        }
			else
			{
				isUse3des = Integer.parseInt(roocconfig.getConfigvalue());
			}
			
			
			if(isUse3des==1)
			{
				roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"encryptionkey");
				if (roocconfig == null) {
		            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for encryptionkey found.");
		        }
				String encryptionkey = roocconfig.getConfigvalue();
				String decrypted_byte_oldpwd_only ="";
				
				byte[] hex2Byteoldpwd = util.hexToBytes(obj.getPassword());
				
		        try {
		        	decrypted_byte_oldpwd_only=util.doDecryptiononly(hex2Byteoldpwd,encryptionkey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        boolean isEmptydecrpassword = decrypted_byte_oldpwd_only == null || decrypted_byte_oldpwd_only.trim().length() == 0;
		        if(!isEmptydecrpassword)
		        {
		        	obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(decrypted_byte_oldpwd_only)));
		        }
			}
			else
			{
				obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
			}
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
		
		
		int isNeeduserapproval =0;
		//RoocConfig roocconfig = new RoocConfig();
		roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isNeeduserapproval");
		if (roocconfig == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
        }
		else
		{
			isNeeduserapproval = Integer.parseInt(roocconfig.getConfigvalue());
		}
		
		int isApproved=0;
		if(isNeeduserapproval==1)
		{
			//isApproved=0;
			isApproved=obj.getIsapproved();
		}
		else
		{
			isApproved=1;
		}

		obj.setIsapproved(isApproved);
		
		UserProfile retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		if(retData.getUserprofileid()>0)
		{
			UserRole ur = new UserRole();
			ur = urservice.getView(headers.get("tenantID").get(0),obj.getRoleid());
			
			if(ur == null){
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
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
			
			//trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "UserProfile", "Update");
			
			JSONObject postdata = new JSONObject();
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			//String userinfo = "{ 'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' }";
			String userinfo = "{  'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
			JsonElement je = jp.parse(userinfo);
			String prettyJsonString = gson.toJson(je);
			
			JSONObject namalengkap = new JSONObject();
			namalengkap.put("name", "namalengkap");
			namalengkap.put("secret", false);
			namalengkap.put("value", obj.getName());
			
			
			//userinfopost.put("alamat", obj.getAddress());
			JSONObject alamat = new JSONObject();
			alamat.put("name", "alamat");
			alamat.put("secret", false);
			alamat.put("value", obj.getAddress());
			
			
			//userinfopost.put("email", obj.getEmailaddress());
			JSONObject email = new JSONObject();
			email.put("name", "email");
			email.put("secret", false);
			email.put("value", retData.getEmailaddress());
			
			
			//userinfopost.put("active", obj.getIsactive());
			JSONObject active = new JSONObject();
			active.put("name", "active");
			active.put("secret", false);
			active.put("value", obj.getIsactive());
			
			//postdata.put("access_token", channel_token);
			postdata.put("username", obj.getUsername());
			JSONObject username = new JSONObject();
			username.put("name", "username");
			username.put("secret", false);
			username.put("value", obj.getUsername());
			
			
			postdata.put("password", retData.getPassword());
			JSONObject password = new JSONObject();
			password.put("name", "password");
			password.put("secret", false);
			password.put("value", retData.getPassword());
			
			
			postdata.put("email", obj.getEmailaddress());
			
			postdata.put("name", obj.getName());
			JSONObject name = new JSONObject();
			name.put("name", "name");
			name.put("secret", false);
			name.put("value", obj.getName());
			
			postdata.put("usertype", ur.getRolename().toLowerCase());
			JSONObject usertype = new JSONObject();
			usertype.put("name", "usertype");
			usertype.put("secret", false);
			usertype.put("value", ur.getRolename().toLowerCase());
			
			postdata.put("active", obj.getIsactive());
			
			JSONArray ja = new JSONArray();
			ja.put(namalengkap);
			ja.put(alamat);
			ja.put(email);
			ja.put(active);
			ja.put(username);
			//ja.put(password);
			ja.put(name);
			ja.put(usertype);
			
			JSONObject userdata = new JSONObject();
			userdata.put("data", ja);
			
			postdata.put("info", userdata);
			//postdata.put("active", 1);
			System.out.print("Post data is :" + postdata);
			
			try {
				//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
				postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",chnlToken,postdata);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
			}
			
			
			System.out.println("post return is : "+ postret);
			
			//throw new CoreException(HttpStatus.OK, "Data Saved");
			return retData;
		}
		else
		{
			//retMessage = "{" + "'message' : " + "'Something went wrong'" + "}";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
			
		}
		//return retData;
	}


	//update yang untuk bca
	@RequestMapping(method=RequestMethod.POST,value="/updateldap")
	public UserProfile getUpdateldap(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody UserProfile obj) {
		//UserRole result = 
		String postret = "";
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		
		//int roleid =0;
		boolean isEmptyrolename = obj.getRolename() == null || obj.getRolename().trim().length() == 0;
		
		if(isEmptyrolename)
		{
			if(obj.getRoleid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Role cannot be empty.");
			}
			/*else
			{
				roleid = obj.getRoleid();
			}*/
			
		}
		else
		{
			UserRole urbyname = new UserRole();
			urbyname = urservice.getViewbyName(headers.get("tenantID").get(0),obj.getRolename());
			if (urbyname == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid role name.");
			}
			else
			{
				//roleid = urbyname.getRoleid();
				obj.setRoleid(urbyname.getRoleid());
			}
			
		}
		
		/*if(roleid<=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "role is empty.");
		}
		else
		{
			obj.setRoleid(roleid);
		}*/
		
		obj.setSource("ldapbca");
		//get data user
		String hprret = "";
		String domain_id ="";
		String display_name ="";
		String job_title ="";
		String company_code ="";
		String kanwil_name ="";
		String branch_name ="";
		String kcp_name ="";
		String department ="";
		String area ="";
		
		//set email for ldap
		obj.setEmailaddress(obj.getUsername() +"@roocvthree.ai");
		
		
		try {
			hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		System.out.println(hprret);
		
		if(validatejson.isJSONValidstandard(hprret))
        {
			JSONObject jsonObjectget = new JSONObject(hprret);
			
		    if(!jsonObjectget.has("output_schema"))
			{
		    	System.out.println("! Schema");
		    	throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
			}
		    else
		    {
		    	System.out.println("Has Schema");
		    	
		    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
		    	for(int i = 0; i < jsonarray .length(); i++)
		    	{
		    	   JSONObject object3 = jsonarray.getJSONObject(i);
		    	   domain_id = object3.getString("domain_id");
		    	   display_name =object3.getString("display_name");
		    	   job_title =object3.getString("job_title");
		   		   company_code =object3.getString("company_code");
		   		   kanwil_name =object3.getString("kanwil_name");
		   		   branch_name =object3.getString("branch_name");
		   		   kcp_name =object3.getString("kcp_name");
		   		   department =object3.getString("departement");
		   		   area =object3.getString("area");
		    	  
		    	   System.out.println(domain_id);	
		    	}
		    }
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found");
		}
		
		obj.setName(display_name);

		//obj.setUsername(domain_id);
		obj.setAddress(branch_name);

		
		/*if(validatejson.isJSONValidstandard(hprret))
        {
			JSONObject jsonObjectget = new JSONObject(hprret);
			
			System.out.println(hprret);
		    
		    if(!jsonObjectget.has("output_schema"))
			{
		    	System.out.println("! Schema");
		    	throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
			}
		    else
		    {
		    	System.out.println("Has Schema");
		    	
		    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
		    	for(int i = 0; i < jsonarray .length(); i++)
		    	{
		    	   JSONObject object3 = jsonarray.getJSONObject(i);
		    	   domain_id = object3.getString("domain_id");
		    	   display_name =object3.getString("display_name");
		    	   job_title =object3.getString("job_title");
		   		   company_code =object3.getString("company_code");
		   		   kanwil_name =object3.getString("kanwil_name");
		   		   branch_name =object3.getString("branch_name");
		   		   kcp_name =object3.getString("kcp_name");
		   		   department =object3.getString("departement");
		   		   area =object3.getString("area");
		    	  
		    	   System.out.println(domain_id);	
		    	}

		    	obj.setName(display_name);
		    	obj.setCompany_code(company_code);
			    obj.setBranch_name(branch_name);
			    obj.setDisplay_name(display_name);
			    obj.setDomain_id(domain_id);
			    obj.setJob_title(job_title);
			    obj.setKanwil_name(kanwil_name);
			    obj.setKcp_name(kcp_name);
			    obj.setDepartment(department);
				obj.setUsername(domain_id);
				obj.setArea(area);
		    }
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not Found");
		}*/
		
		
		
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
		UserProfile retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		if(retData.getUserprofileid()>0)
		{
			UserRole ur = new UserRole();
			ur = urservice.getView(headers.get("tenantID").get(0),obj.getRoleid());
			
			if(ur == null){
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
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
			
			//trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "UserProfile", "Update");
			
			JSONObject postdata = new JSONObject();
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			//String userinfo = "{ 'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' }";
			String userinfo = "{  'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
			JsonElement je = jp.parse(userinfo);
			String prettyJsonString = gson.toJson(je);
			
			JSONObject namalengkap = new JSONObject();
			namalengkap.put("name", "namalengkap");
			namalengkap.put("secret", false);
			namalengkap.put("value", obj.getName());
			
			
			//userinfopost.put("alamat", obj.getAddress());
			JSONObject alamat = new JSONObject();
			alamat.put("name", "alamat");
			alamat.put("secret", false);
			alamat.put("value", branch_name);
			
			
			//userinfopost.put("email", obj.getEmailaddress());
			/*JSONObject email = new JSONObject();
			email.put("name", "email");
			email.put("secret", false);
			email.put("value", obj.getEmailaddress());*/
			
			
			//userinfopost.put("active", obj.getIsactive());
			postdata.put("active", obj.getIsactive());
			JSONObject active = new JSONObject();
			active.put("name", "active");
			active.put("secret", false);
			active.put("value", obj.getIsactive());
			
			//postdata.put("access_token", channel_token);
			postdata.put("username", obj.getUsername());
			JSONObject username = new JSONObject();
			username.put("name", "username");
			username.put("secret", false);
			username.put("value", obj.getUsername());
			
			
			/*postdata.put("password", obj.getPassword());
			JSONObject password = new JSONObject();
			password.put("name", "password");
			password.put("secret", false);
			password.put("value", obj.getPassword());*/
			
			
			//postdata.put("email", obj.getEmailaddress());
			
			postdata.put("name", obj.getName());
			JSONObject name = new JSONObject();
			name.put("name", "name");
			name.put("secret", false);
			name.put("value", obj.getName());
			
			postdata.put("usertype", ur.getRolename().toLowerCase());
			JSONObject usertype = new JSONObject();
			usertype.put("name", "usertype");
			usertype.put("secret", false);
			usertype.put("value", ur.getRolename().toLowerCase());
			
			JSONObject jo_domain_id = new JSONObject();
			jo_domain_id.put("name", "domain_id");
			jo_domain_id.put("secret", false);
			jo_domain_id.put("value", domain_id);
			
			JSONObject jo_job_title = new JSONObject();
			jo_job_title.put("name", "job_title");
			jo_job_title.put("secret", false);
			jo_job_title.put("value", job_title);
			
			JSONObject jo_kanwil_name = new JSONObject();
			jo_kanwil_name.put("name", "kanwil_name");
			jo_kanwil_name.put("secret", false);
			jo_kanwil_name.put("value", kanwil_name);
			
			JSONObject jo_branch_name = new JSONObject();
			jo_branch_name.put("name", "branch_name");
			jo_branch_name.put("secret", false);
			jo_branch_name.put("value", branch_name);
			
			JSONObject jo_kcp_name = new JSONObject();
			jo_kcp_name.put("name", "kcp_name");
			jo_kcp_name.put("secret", false);
			jo_kcp_name.put("value", kcp_name);
			
			JSONObject jo_display_name = new JSONObject();
			jo_display_name.put("name", "display_name");
			jo_display_name.put("secret", false);
			jo_display_name.put("value", display_name);
			
			JSONObject jo_company_code = new JSONObject();
			jo_company_code.put("name", "company_code");
			jo_company_code.put("secret", false);
			jo_company_code.put("value", company_code);
			
			JSONObject jo_department = new JSONObject();
			jo_department.put("name", "department");
			jo_department.put("secret", false);
			jo_department.put("value", department);
			
			JSONObject jo_area = new JSONObject();
			jo_area.put("name", "area");
			jo_area.put("secret", false);
			jo_area.put("value", area);
			
			
			JSONArray ja = new JSONArray();
			ja.put(namalengkap);
			ja.put(alamat);
			//ja.put(email);
			ja.put(active);
			ja.put(username);
			//ja.put(password);
			ja.put(name);
			ja.put(usertype);
			ja.put(jo_company_code);
			ja.put(jo_branch_name);
			ja.put(jo_display_name);
			ja.put(jo_domain_id);
			ja.put(jo_kanwil_name);
			ja.put(jo_kcp_name);
			ja.put(jo_department);
			ja.put(jo_job_title);
			ja.put(jo_area);
			
			JSONObject userdata = new JSONObject();
			userdata.put("data", ja);
			
			postdata.put("info", userdata);
			
			
			System.out.println("postdata is : " + postdata);
			
			try {
				//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
				postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",chnlToken,postdata);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
			}
			
			
			System.out.println("post return is : "+ postret);
			
			//throw new CoreException(HttpStatus.OK, "Data Saved");
			return retData;
		}
		else
		{
			//retMessage = "{" + "'message' : " + "'Something went wrong'" + "}";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
			
		}
		//return retData;
	}
	
	//update yang untuk bca
	@RequestMapping(method=RequestMethod.GET,value="/updateldapsync")
	public String getUpdateldapsync(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
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
			
		String isComplete ="COMPLETE";
		
		String headerTenant = headers.get("tenantID").get(0);
		List<UserProfile> upresult = service.getIndexother(headerTenant);
		if(upresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String tokenUpdate =token;
		upresult.forEach(item->{
			
			EngineCredential result = null;
			 result = new EngineCredential();
			result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
	        }
			
			//get data user
			UserProfile obj = new UserProfile();
			String domain_id ="";
			String display_name ="";
			String job_title ="";
			String company_code ="";
			String kanwil_name ="";
			String branch_name ="";
			String kcp_name ="";
			String department ="";
			String area ="";
			
			
			String hprret = "";
			String postret ="";
			boolean isLdapFound = false;
			try {
				hprret = hpr.getGetdataldapbca(item.getUsername(),headerTenant);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			
			System.out.println(hprret);
			
			if(validatejson.isJSONValidstandard(hprret))
	        {
				JSONObject jsonObjectget = new JSONObject(hprret);
				
			    if(!jsonObjectget.has("output_schema"))
				{
			    	System.out.println("! Schema found for user : " + item.getUsername());
			    	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
				}
			    else
			    {
			    	isLdapFound =true;
			    	System.out.println("Has Schema");
			    	
			    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
			    	for(int i = 0; i < jsonarray .length(); i++)
			    	{
			    	   JSONObject object3 = jsonarray.getJSONObject(i);
			    	   domain_id = object3.getString("domain_id");
			    	   display_name =object3.getString("display_name");
			    	   job_title =object3.getString("job_title");
			   		   company_code =object3.getString("company_code");
			   		   kanwil_name =object3.getString("kanwil_name");
			   		   branch_name =object3.getString("branch_name");
			   		   kcp_name =object3.getString("kcp_name");
			   		   department =object3.getString("departement");
			   		   area =object3.getString("area");
			    	  
			    	   System.out.println(domain_id);	
			    	}
			    }
	        }
			else
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found");
				System.out.println("! User Domain not found for user : " + item.getUsername());
			}
			
			if(isLdapFound)
			{
				obj.setName(display_name);

				//obj.setUsername(domain_id);
				obj.setAddress(branch_name);

				
				//set email for ldap

				obj.setUsername(item.getUsername());
				obj.setUserprofileid(item.getUserprofileid());
				obj.setRoleid(item.getRoleid());
				//obj.setEmailaddress(item.getUsername() +"@roocvthree.ai");
				obj.setEmailaddress(item.getEmailaddress());
				obj.setAddress(item.getAddress());
				obj.setIsactive(item.getIsactive());
				obj.setSource(item.getSource());
				
				trb.SetTrailRecord(tokenUpdate,obj);
				UserProfile retData = service.getUpdate(headers.get("tenantID").get(0),obj);
				
				if(retData.getUserprofileid()>0)
				{
					UserRole ur = new UserRole();
					ur = urservice.getView(headers.get("tenantID").get(0),obj.getRoleid());
					
					if(ur == null){
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
						System.out.println("! no valid user role found for user : " + item.getUsername());
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
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
							System.out.println("channel access token not found : ");
						}
						
						if(jsonObject.getString("token") ==null)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
							System.out.println("channel access token not found : ");
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
					
					//trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "UserProfile", "Update");
					
					JSONObject postdata = new JSONObject();
					
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					//String userinfo = "{ 'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' }";
					String userinfo = "{  'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
					JsonElement je = jp.parse(userinfo);
					String prettyJsonString = gson.toJson(je);
					
					JSONObject namalengkap = new JSONObject();
					namalengkap.put("name", "namalengkap");
					namalengkap.put("secret", false);
					namalengkap.put("value", obj.getName());
					
					
					//userinfopost.put("alamat", obj.getAddress());
					JSONObject alamat = new JSONObject();
					alamat.put("name", "alamat");
					alamat.put("secret", false);
					alamat.put("value", obj.getAddress());
					
					
					//userinfopost.put("email", obj.getEmailaddress());
					/*JSONObject email = new JSONObject();
					email.put("name", "email");
					email.put("secret", false);
					email.put("value", obj.getEmailaddress());*/
					
					
					//userinfopost.put("active", obj.getIsactive());
					postdata.put("active", obj.getIsactive());
					JSONObject active = new JSONObject();
					active.put("name", "active");
					active.put("secret", false);
					active.put("value", obj.getIsactive());
					
					//postdata.put("access_token", channel_token);
					postdata.put("username", obj.getUsername());
					JSONObject username = new JSONObject();
					username.put("name", "username");
					username.put("secret", false);
					username.put("value", obj.getUsername());
					
					
					/*postdata.put("password", obj.getPassword());
					JSONObject password = new JSONObject();
					password.put("name", "password");
					password.put("secret", false);
					password.put("value", obj.getPassword());*/
					
					
					//postdata.put("email", obj.getEmailaddress());
					
					postdata.put("name", obj.getName());
					JSONObject name = new JSONObject();
					name.put("name", "name");
					name.put("secret", false);
					name.put("value", obj.getName());
					
					postdata.put("usertype", ur.getRolename().toLowerCase());
					JSONObject usertype = new JSONObject();
					usertype.put("name", "usertype");
					usertype.put("secret", false);
					usertype.put("value", ur.getRolename().toLowerCase());
					
					JSONObject jo_domain_id = new JSONObject();
					jo_domain_id.put("name", "domain_id");
					jo_domain_id.put("secret", false);
					jo_domain_id.put("value", domain_id);
					
					JSONObject jo_job_title = new JSONObject();
					jo_job_title.put("name", "job_title");
					jo_job_title.put("secret", false);
					jo_job_title.put("value", job_title);
					
					JSONObject jo_kanwil_name = new JSONObject();
					jo_kanwil_name.put("name", "kanwil_name");
					jo_kanwil_name.put("secret", false);
					jo_kanwil_name.put("value", kanwil_name);
					
					JSONObject jo_branch_name = new JSONObject();
					jo_branch_name.put("name", "branch_name");
					jo_branch_name.put("secret", false);
					jo_branch_name.put("value", branch_name);
					
					JSONObject jo_kcp_name = new JSONObject();
					jo_kcp_name.put("name", "kcp_name");
					jo_kcp_name.put("secret", false);
					jo_kcp_name.put("value", kcp_name);
					
					JSONObject jo_display_name = new JSONObject();
					jo_display_name.put("name", "display_name");
					jo_display_name.put("secret", false);
					jo_display_name.put("value", display_name);
					
					JSONObject jo_company_code = new JSONObject();
					jo_company_code.put("name", "company_code");
					jo_company_code.put("secret", false);
					jo_company_code.put("value", company_code);
					
					JSONObject jo_department = new JSONObject();
					jo_department.put("name", "department");
					jo_department.put("secret", false);
					jo_department.put("value", department);
					
					JSONObject jo_area = new JSONObject();
					jo_area.put("name", "area");
					jo_area.put("secret", false);
					jo_area.put("value", area);
					
					
					JSONArray ja = new JSONArray();
					ja.put(namalengkap);
					ja.put(alamat);
					//ja.put(email);
					ja.put(active);
					ja.put(username);
					//ja.put(password);
					ja.put(name);
					ja.put(usertype);
					ja.put(jo_company_code);
					ja.put(jo_branch_name);
					ja.put(jo_display_name);
					ja.put(jo_domain_id);
					ja.put(jo_kanwil_name);
					ja.put(jo_kcp_name);
					ja.put(jo_department);
					ja.put(jo_job_title);
					ja.put(jo_area);
					
					JSONObject userdata = new JSONObject();
					userdata.put("data", ja);
					
					postdata.put("info", userdata);
					
					
					System.out.println("postdata is : " + postdata);
					
					try {
						//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
						postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",chnlToken,postdata);
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
						System.out.println("update user channel failed for user : "+ obj.getName());
					}
					
					
					System.out.println("post return is : "+ postret);
					
					System.out.print("User Profile with Name : " + obj.getName() + " and with User Profile ID : "+obj.getUserprofileid()+" has been update successfullyy");
					//throw new CoreException(HttpStatus.OK, "Data Saved");
					//return retData;
					
				}
				else
				{
					//retMessage = "{" + "'message' : " + "'Something went wrong'" + "}";
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
					System.out.print("User Profile with Name : " + obj.getName() + " and with User Profile ID : "+obj.getUserprofileid()+"  update data failed");
					
				}
			}
			else
			{
				System.out.println("! User Domain not found for user : " + item.getUsername());
			}
			
		});
		return isComplete;
		
		
		//return retData;
	}
	
	//update yang untuk bca coba
	@RequestMapping(method=RequestMethod.GET,value="/updateldapsynccoba")
	public String getUpdateldapsynccoba(@RequestHeader HttpHeaders headers,HttpServletRequest request,int userprofileid) {
		
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
			
		String isComplete ="COMPLETE";
		
		String headerTenant = headers.get("tenantID").get(0);
		UserProfileView upresult = service.getView(headerTenant,userprofileid);
		if(upresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String tokenUpdate =token;
		//upresult.forEach(item->{
			
			EngineCredential result = null;
			 result = new EngineCredential();
			result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
	        }
			
			//get data user
			UserProfile obj = new UserProfile();
			String domain_id ="coba_u_";
			String display_name ="coba_dn_";
			String job_title ="coba_jt_";
			String company_code ="coba_cc_";
			String kanwil_name ="coba_kn_";
			String branch_name ="coba_bn_";
			String kcp_name ="coba_kn_";
			String department ="coba_d_";
			String area ="coba_a_";
			
			
			String hprret = "";
			String postret ="";
			boolean isLdapFound = true;
			
			/*
			try {
				hprret = hpr.getGetdataldapbca(item.getUsername(),headerTenant);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			
			System.out.println(hprret);
			
			if(validatejson.isJSONValidstandard(hprret))
	        {
				JSONObject jsonObjectget = new JSONObject(hprret);
				
			    if(!jsonObjectget.has("output_schema"))
				{
			    	System.out.println("! Schema found for user : " + item.getUsername());
			    	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
				}
			    else
			    {
			    	isLdapFound =true;
			    	System.out.println("Has Schema");
			    	
			    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
			    	for(int i = 0; i < jsonarray .length(); i++)
			    	{
			    	   JSONObject object3 = jsonarray.getJSONObject(i);
			    	   domain_id = object3.getString("domain_id");
			    	   display_name =object3.getString("display_name");
			    	   job_title =object3.getString("job_title");
			   		   company_code =object3.getString("company_code");
			   		   kanwil_name =object3.getString("kanwil_name");
			   		   branch_name =object3.getString("branch_name");
			   		   kcp_name =object3.getString("kcp_name");
			   		   department =object3.getString("departement");
			   		   area =object3.getString("area");
			    	  
			    	   System.out.println(domain_id);	
			    	}
			    }
	        }
			else
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "User Domain not found");
				System.out.println("! User Domain not found for user : " + item.getUsername());
			}*/
			
			if(isLdapFound)
			{
				
				domain_id = domain_id+upresult.getUserName();
	    	    display_name =display_name+upresult.getUserName();
	    	    job_title =job_title+upresult.getUserName();
	   		    company_code =company_code+upresult.getUserName();
	   		    kanwil_name =kanwil_name+upresult.getUserName();
	   		    branch_name =branch_name+upresult.getUserName();
	   		    kcp_name =kcp_name+upresult.getUserName();
	   		    department =department+upresult.getUserName();
	   		    area =area+upresult.getUserName();
				
				obj.setName(display_name+upresult.getUserName());

				//obj.setUsername(domain_id);
				//obj.setAddress(upresult.getAddress());

				
				//set email for ldap

				obj.setUsername(upresult.getUserName());
				obj.setUserprofileid(upresult.getUserProfileid());
				obj.setRoleid(upresult.getRoleId());
				obj.setEmailaddress(upresult.getEmailAddress());
				//obj.setAddress(upresult.getAddress());
				obj.setIsactive(upresult.getIsActive());
				obj.setSource(upresult.getSource());
				
				trb.SetTrailRecord(tokenUpdate,obj);
				UserProfile retData = service.getUpdate(headers.get("tenantID").get(0),obj);
				
				if(retData.getUserprofileid()>0)
				{
					UserRole ur = new UserRole();
					ur = urservice.getView(headers.get("tenantID").get(0),obj.getRoleid());
					
					if(ur == null){
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
						System.out.println("! no valid user role found for user : " + upresult.getUserName());
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
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
							System.out.println("channel access token not found : ");
						}
						
						if(jsonObject.getString("token") ==null)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
							System.out.println("channel access token not found : ");
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
					
					//trb.SaveTrail(headers.get("tenantID").get(0), headers.get("access_token").get(0), Integer.parseInt(retData), "UserProfile", "Update");
					
					JSONObject postdata = new JSONObject();
					
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					//String userinfo = "{ 'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' }";
					String userinfo = "{  'namalengkap' : '" + obj.getName() + "' , 'alamat' : '" + obj.getAddress() + "' , 'email' : '" + obj.getEmailaddress() + "' }";
					JsonElement je = jp.parse(userinfo);
					String prettyJsonString = gson.toJson(je);
					
					JSONObject namalengkap = new JSONObject();
					namalengkap.put("name", "namalengkap");
					namalengkap.put("secret", false);
					namalengkap.put("value", obj.getName());
					
					
					//userinfopost.put("alamat", obj.getAddress());
					JSONObject alamat = new JSONObject();
					alamat.put("name", "alamat");
					alamat.put("secret", false);
					alamat.put("value", branch_name);
					
					
					//userinfopost.put("email", obj.getEmailaddress());
					/*JSONObject email = new JSONObject();
					email.put("name", "email");
					email.put("secret", false);
					email.put("value", obj.getEmailaddress());*/
					
					
					//userinfopost.put("active", obj.getIsactive());
					postdata.put("active", obj.getIsactive());
					JSONObject active = new JSONObject();
					active.put("name", "active");
					active.put("secret", false);
					active.put("value", obj.getIsactive());
					
					//postdata.put("access_token", channel_token);
					postdata.put("username", obj.getUsername());
					JSONObject username = new JSONObject();
					username.put("name", "username");
					username.put("secret", false);
					username.put("value", obj.getUsername());
					
					
					/*postdata.put("password", obj.getPassword());
					JSONObject password = new JSONObject();
					password.put("name", "password");
					password.put("secret", false);
					password.put("value", obj.getPassword());*/
					
					
					//postdata.put("email", obj.getEmailaddress());
					
					postdata.put("name", obj.getName());
					JSONObject name = new JSONObject();
					name.put("name", "name");
					name.put("secret", false);
					name.put("value", obj.getName());
					
					postdata.put("usertype", ur.getRolename().toLowerCase());
					JSONObject usertype = new JSONObject();
					usertype.put("name", "usertype");
					usertype.put("secret", false);
					usertype.put("value", ur.getRolename().toLowerCase());
					
					JSONObject jo_domain_id = new JSONObject();
					jo_domain_id.put("name", "domain_id");
					jo_domain_id.put("secret", false);
					jo_domain_id.put("value", domain_id);
					
					JSONObject jo_job_title = new JSONObject();
					jo_job_title.put("name", "job_title");
					jo_job_title.put("secret", false);
					jo_job_title.put("value", job_title);
					
					JSONObject jo_kanwil_name = new JSONObject();
					jo_kanwil_name.put("name", "kanwil_name");
					jo_kanwil_name.put("secret", false);
					jo_kanwil_name.put("value", kanwil_name);
					
					JSONObject jo_branch_name = new JSONObject();
					jo_branch_name.put("name", "branch_name");
					jo_branch_name.put("secret", false);
					jo_branch_name.put("value", branch_name);
					
					JSONObject jo_kcp_name = new JSONObject();
					jo_kcp_name.put("name", "kcp_name");
					jo_kcp_name.put("secret", false);
					jo_kcp_name.put("value", kcp_name);
					
					JSONObject jo_display_name = new JSONObject();
					jo_display_name.put("name", "display_name");
					jo_display_name.put("secret", false);
					jo_display_name.put("value", display_name);
					
					JSONObject jo_company_code = new JSONObject();
					jo_company_code.put("name", "company_code");
					jo_company_code.put("secret", false);
					jo_company_code.put("value", company_code);
					
					JSONObject jo_department = new JSONObject();
					jo_department.put("name", "department");
					jo_department.put("secret", false);
					jo_department.put("value", department);
					
					JSONObject jo_area = new JSONObject();
					jo_area.put("name", "area");
					jo_area.put("secret", false);
					jo_area.put("value", area);
					
					
					JSONArray ja = new JSONArray();
					ja.put(namalengkap);
					ja.put(alamat);
					//ja.put(email);
					ja.put(active);
					ja.put(username);
					//ja.put(password);
					ja.put(name);
					ja.put(usertype);
					ja.put(jo_company_code);
					ja.put(jo_branch_name);
					ja.put(jo_display_name);
					ja.put(jo_domain_id);
					ja.put(jo_kanwil_name);
					ja.put(jo_kcp_name);
					ja.put(jo_department);
					ja.put(jo_job_title);
					ja.put(jo_area);
					
					JSONObject userdata = new JSONObject();
					userdata.put("data", ja);
					
					postdata.put("info", userdata);
					
					
					System.out.println("postdata is : " + postdata);
					
					try {
						//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
						postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",chnlToken,postdata);
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
						System.out.println("update user channel failed for user : "+ obj.getName());
					}
					
					
					System.out.println("post return is : "+ postret);
					
					System.out.print("User Profile with Name : " + obj.getName() + " and with User Profile ID : "+obj.getUserprofileid()+" has been update successfullyy");
					//throw new CoreException(HttpStatus.OK, "Data Saved");
					//return retData;
					
				}
				else
				{
					//retMessage = "{" + "'message' : " + "'Something went wrong'" + "}";
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something went wrong.");
					System.out.print("User Profile with Name : " + obj.getName() + " and with User Profile ID : "+obj.getUserprofileid()+"  update data failed");
					
				}
			}
			else
			{
				System.out.println("! User Domain not found for user : " + upresult.getUserName());
			}
			
		//});
		return isComplete;
		
		
		//return retData;
	}
	
	
}