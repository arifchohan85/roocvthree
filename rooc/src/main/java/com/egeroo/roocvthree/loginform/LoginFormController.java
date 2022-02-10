package com.egeroo.roocvthree.loginform;

import java.io.IOException;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;




import org.apache.commons.codec.digest.DigestUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


import com.egeroo.roocvthree.changepasswordcycle.ChangePasswordCycle;
import com.egeroo.roocvthree.changepasswordcycle.ChangePasswordCycleService;
import com.egeroo.roocvthree.changepasswordcyclelimit.ChangePasswordCycleLimit;
import com.egeroo.roocvthree.changepasswordcyclelimit.ChangePasswordCycleLimitService;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.core.jwt.JwtUtil;
import com.egeroo.roocvthree.core.usersource.UserSource;
import com.egeroo.roocvthree.core.usersource.UserSourceService;


import com.egeroo.roocvthree.core.util.Util;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.user.User;
import com.egeroo.roocvthree.user.UserService;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileView;
import com.egeroo.roocvthree.userprofile.UserProfileService;
import com.egeroo.roocvthree.userrole.UserRole;
import com.egeroo.roocvthree.userrole.UserRoleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.jsonwebtoken.Claims;





@RestController
@RequestMapping("/login")
public class LoginFormController{
	
	@Autowired
    private LoginFormService service;
	
	@Autowired
	private UserSourceService usservice;
	JwtUtil jwtutils = new JwtUtil();
	
	HttpPostReq hpr = new HttpPostReq();
	UserRoleService urservice = new UserRoleService(); 
	private int ECID = 1;
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private UserProfileService upservice;
	
	@Autowired
    private UserService uservice;
	
	
	@Autowired
    private ChangePasswordCycleService cpservice;
	
	@Autowired
    private ChangePasswordCycleLimitService cpclservice;

//	@Autowired
//	private MenulistService menuservice;
	

	
	ValidationJson validatejson = new ValidationJson(); 
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService();
    
    RoocConfigService rcservice = new RoocConfigService();
    
    Util util= new Util();
	
    
    @RequestMapping(method=RequestMethod.GET,value="/dochanneltoken")
    public String getChanneltoken(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		String chnlToken ="";
		String serverChannel ="";
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
			
			if(!jsonObject.has("server"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel server not found.");
			}
			
			if(jsonObject.getString("server") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel server not found.");
			}
			
			
			chnlToken = jsonObject.getString("token");
			serverChannel = jsonObject.getString("server");
	        System.out.println("channel token is :" + jsonObject.getString("token"));
	        System.out.println("server is :" + jsonObject.getString("server"));
    		
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
    	
    	System.out.println("channel post return is : "+ postret);
    	
    	String hprret = "";
    	
    	
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
    	
    	Claims parsejwt = jwtutils.parseJWT(token);
		//int clmid = Integer.parseInt(parsejwt.getId());
		String uname = parsejwt.getSubject();
		
		
		try {
			hprret = hpr.ConnectGetTokenchannel(result.getChannelapi()+"/auth/usertoken/"+uname,chnlToken);
			//hprret = hpr.ConnectGetTokenchannel(serverChannel+"/auth/usertoken/"+uname,chnlToken);
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		System.out.println("user channel post return is : "+ hprret);
		
		String ujsonString = hprret;
        JSONObject ujsonObject;
		
		ujsonObject = new JSONObject(ujsonString);
		
		if(!ujsonObject.has("token"))
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user channel token.");
		}
		
		if(ujsonObject.getString("token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
		}
		
		String uToken = "null";
		if(ujsonObject.has("token"))
		{
			uToken = ujsonObject.getString("token");
		}
		
		//ac = "{ 'channelip' : '"+ result.getChannelapi() +"'  , 'userchnltoken' : '"+ uToken +"'  , 'isValid' : true }";
		ac = "{ 'channelip' : '"+ serverChannel +"'  , 'userchnltoken' : '"+ uToken +"'  , 'isValid' : true }";
//		ac = "{ 'channelUrl' : '"+ serverChannel +"'  , 'channelToken' : '"+ uToken +"' }";
		
		JsonElement je = jp.parse(ac);
		prettyJsonString = gson.toJson(je);
		
		return prettyJsonString;
		
	}

	@RequestMapping(method=RequestMethod.GET,value="/dogetchanneltoken")
	public String getChanneltokenget(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		
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

		
		//int clmid = Integer.parseInt(parsejwt.getId());
		


		UserSource ussrc =  new UserSource();
		ussrc = usservice.findByAuthkey(headers.get("tenantID").get(0),token);

		if (ussrc == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}


		//ac = "{ 'channelip' : '"+ result.getChannelapi() +"'  , 'userchnltoken' : '"+ uToken +"'  , 'isValid' : true }";
		ac = "{  'userchnltoken' : '"+ ussrc.getChanneltoken() +"'  }";
//		ac = "{ 'channelUrl' : '"+ serverChannel +"'  , 'channelToken' : '"+ uToken +"' }";

		JsonElement je = jp.parse(ac);
		prettyJsonString = gson.toJson(je);

		return prettyJsonString;

	}

    @RequestMapping(method=RequestMethod.GET,value="/dochannellogin")
    public String getChannellogin(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		String chnlToken ="";
		String serverChannel ="";
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
			
			if(!jsonObject.has("server"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel server not found.");
			}
			
			if(jsonObject.getString("server") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel server not found.");
			}
			
			
			chnlToken = jsonObject.getString("token");
			serverChannel = jsonObject.getString("server");
	        System.out.println("channel token is :" + jsonObject.getString("token"));
	        System.out.println("server is :" + jsonObject.getString("server"));
    		
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
    	
    	System.out.println("channel post return is : "+ postret);
    	
    	String hprret = "";
    	
    	
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
    	
    	Claims parsejwt = jwtutils.parseJWT(token);
		//int clmid = Integer.parseInt(parsejwt.getId());
		String uname = parsejwt.getSubject();
		
		
		try {
			//hprret = hpr.ConnectGetTokenchannel(result.getChannelapi()+"/auth/usertoken/"+uname,chnlToken);
			hprret = hpr.ConnectGetTokenchannel(serverChannel+"/auth/usertoken/"+uname,chnlToken);
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		System.out.println("user channel post return is : "+ hprret);
		
		String ujsonString = hprret;
        JSONObject ujsonObject;
		
		ujsonObject = new JSONObject(ujsonString);
		
		if(!ujsonObject.has("token"))
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user channel token.");
		}
		
		if(ujsonObject.getString("token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "channel access token not found.");
		}
		
		String uToken = "null";
		if(ujsonObject.has("token"))
		{
			uToken = ujsonObject.getString("token");
		}
		
		//ac = "{ 'channelip' : '"+ result.getChannelapi() +"'  , 'userchnltoken' : '"+ uToken +"'  , 'isValid' : true }";
		ac = "{ 'channelip' : '"+ serverChannel +"'  , 'userchnltoken' : '"+ uToken +"'  , 'isValid' : true }";
		
		
		JsonElement je = jp.parse(ac);
		prettyJsonString = gson.toJson(je);
		
		return prettyJsonString;
		
	}
	
    

	@RequestMapping(method=RequestMethod.POST,value="/dologin")
	public LoginResponse getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginRequest obj) {
		
		LoginResponse loginResponse = null;
		String tenantId = headers.get("tenantID").get(0);
		
		String message="";
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			message = "User is empty";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, message);
		}
		
		if(isEmptypassword)
		{
			message = "Password is empty";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, message);
		}
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("localAddress", request.getLocalAddr());
		requestMap.put("remoteAddress", request.getRemoteAddr());
		requestMap.put("browserInfo", headers.get("User-Agent").get(0));
		
		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		System.out.println("tenantId : " + tenantId);
		
		//LoginForm userData = service.findByUsername(tenantId,obj.getUsername());
		
		loginResponse = service.checkLogin(tenantId,obj,requestMap);
		
		boolean isEmptyaccesstoken = loginResponse.getAccessToken() == null || loginResponse.getAccessToken().trim().length() == 0;
			
		if(isEmptyaccesstoken)
		{
			throw new CoreException(HttpStatus.UNAUTHORIZED,loginResponse.getErrorMessage() );
		}
		else 
		{
			return loginResponse;
		}
			
		
	}
	
	
  
	@SuppressWarnings("static-access")
	@RequestMapping(method=RequestMethod.POST,value="/changepassword")
	public UserProfile getChangepassword(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody ChangePassword obj) {
		
		String token ="";
		
		String newpasswordobj ="";
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
		obj.setUserid(userid);
		
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
			String decrypted_byte_newpwd_only ="";
			String decrypted_byte_repwd_only ="";
			
			byte[] hex2Byteoldpwd = util.hexToBytes(obj.getOldpassword());
			byte[] hex2Bytenewpwd = util.hexToBytes(obj.getNewpassword());
			byte[] hex2Byterepwd = util.hexToBytes(obj.getRepassword());
	        try {
	        	decrypted_byte_oldpwd_only=util.doDecryptiononly(hex2Byteoldpwd,encryptionkey);
	        	decrypted_byte_newpwd_only=util.doDecryptiononly(hex2Bytenewpwd,encryptionkey);
	        	decrypted_byte_repwd_only=util.doDecryptiononly(hex2Byterepwd,encryptionkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        boolean isEmptydecrpassword = decrypted_byte_oldpwd_only == null || decrypted_byte_oldpwd_only.trim().length() == 0;
	        boolean isEmptydecrnewpassword = decrypted_byte_newpwd_only == null || decrypted_byte_newpwd_only.trim().length() == 0;
	        boolean isEmptydecrrepassword = decrypted_byte_repwd_only == null || decrypted_byte_repwd_only.trim().length() == 0;
	        if(!isEmptydecrpassword)
	        {
	        	obj.setOldpassword(decrypted_byte_oldpwd_only);
	        }
	        
	        if(!isEmptydecrnewpassword)
	        {
	        	obj.setNewpassword(decrypted_byte_newpwd_only);
	        }
	        
	        if(!isEmptydecrrepassword)
	        {
	        	obj.setRepassword(decrypted_byte_repwd_only);
	        }
		}
		
		
		newpasswordobj = obj.getNewpassword();
		
		LoginForm objl = new LoginForm();
		objl.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getOldpassword())));
		objl.setUserid(userid);
		
		ChangePasswordCycle cpc = new ChangePasswordCycle();
		ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
		cpc = cpservice.getCountdata(headers.get("tenantID").get(0), userid);
		
		cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));
		
		cpc.setUserid(userid);
		cpc.setPassword(obj.getNewpassword());
		
		int countdatapassword=0;
		int cpclLimitcnt = 0;
		
		if(cpclimit == null)
		{
			cpclLimitcnt = 6;
		}
		else
		{
			if(cpclimit.getLimitdata()<=0)
			{
				cpclLimitcnt = 6;
			}
			else
			{
				cpclLimitcnt = cpclimit.getLimitdata();
			}
			
		}
		
		if (service.isUserExistid(headers.get("tenantID").get(0),objl)) {	
			if(obj.getOldpassword().equals(obj.getNewpassword()))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Old Password and New Password can't be same");
			}
			
			//count berapa kali change password
			countdatapassword = cpc.getCountdata();
			if(countdatapassword<=cpclLimitcnt)
			{
				//check is passing new password exist in db
				if(cpservice.isUserPassword(headers.get("tenantID").get(0),cpc))
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change New Password");
				}
			}
			
			System.out.println("Count password is :"+countdatapassword);
			System.out.println("Pass Limit is :"+cpclLimitcnt);
			
			if(obj.getNewpassword().equals(obj.getRepassword()))
			{
				System.out.println(this.isStrongpassword(obj.getNewpassword()));
				if(this.isStrongpassword(obj.getNewpassword()))
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password not strong enough");
					UserProfile resultbefore = upservice.getViewbyuserid(headers.get("tenantID").get(0),userid);
					
					UserProfile up = new UserProfile();
					up.setUsername(resultbefore.getUsername());
					up.setEmailaddress(resultbefore.getEmailaddress());
					//up.setPassword(obj.getNewpassword());
					up.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getNewpassword())));
					up.setUserid(userid);
					up.setUserprofileid(resultbefore.getUserprofileid());
					trb.SetTrailRecord(token,up);
					upservice.getUpdatepassword(headers.get("tenantID").get(0), up);
					
					User us = new User();
					us.setUsername(resultbefore.getUsername());
					us.setPassword(obj.getNewpassword());
					us.setUserid(userid);
					trb.SetTrailRecord(token,us);
					String updatedid =  uservice.getUpdatepassword(headers.get("tenantID").get(0), us);
					System.out.println("Userid is :" + updatedid);
					
					if(countdatapassword==cpclLimitcnt)
					{
						//lakukan delete all data lama
						List<ChangePasswordCycle> rescpdel =  cpservice.getDeletebyuser(headers.get("tenantID").get(0), userid);
						System.out.println(rescpdel);
					}
					
					trb.SetTrailRecord(token,cpc);
					cpc = cpservice.getCreate(headers.get("tenantID").get(0), cpc);
					
					
					UserProfile result = upservice.getViewbyuserid(headers.get("tenantID").get(0),userid);
					
					String chnlToken ="";
					String postret = "";
					JSONObject pdcomposer = new JSONObject();
			        
					EngineCredential resulteng = new EngineCredential();
					resulteng = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
					
			    	pdcomposer.put("username", resulteng.getChannelusername());
			    	pdcomposer.put("password", resulteng.getChannelPassword());
			    	try {
			    		postret = hpr.setPostData(resulteng.getChannelapi()+"/auth/login",pdcomposer);
			    		
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
					
					
					
					UserRole ur = new UserRole();
					ur = urservice.getView(headers.get("tenantID").get(0),resultbefore.getRoleid());
					
					if(ur == null){
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no valid user role found.");
					}
					
					JSONObject userinfopost = new JSONObject();
			        
					userinfopost.put("namalengkap", resultbefore.getName());
					userinfopost.put("alamat", resultbefore.getAddress());
					userinfopost.put("email", resultbefore.getEmailaddress());
					//userinfopost.put("password", resultbefore.getPassword());
					
					//postdata.put("access_token", channel_token);
					postdata.put("username", resultbefore.getEmailaddress());
					//postdata.put("password", result.getPassword());
					postdata.put("password", newpasswordobj);
					postdata.put("email", resultbefore.getEmailaddress());
					postdata.put("name", resultbefore.getName());
					postdata.put("usertype", ur.getRolename().toLowerCase());
					postdata.put("info", userinfopost);
					postdata.put("active", 1);
					
					try {
						//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user/syncronize",headers.get("channel_token").get(0),postdata);
						//postret = hpr.setPostDatachannel(result.getChannelapi()+"/user",chnlToken,postdata);
						postret = hpr.setPostDatachannel(resulteng.getChannelapi()+"/user/syncronize",chnlToken,postdata);
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
					System.out.println("User Chanell ID is : "+usrchnlID);
					
					return result;
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password not strong enough");
				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password confirmation not valid");
			}
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid Password");
		}
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/changeresetpassword")
	public UserProfileView getChangeresetpassword(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer userprofileid) {
		
		String token ="";
		
		String tenantId = headers.get("tenantId").get(0);
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}
		
		
		System.out.println(token);
        
        UserProfileView userprofileget = service.changeResetpassword(tenantId, userprofileid);
        		//upservice.getView(headers.get("tenantID").get(0),userprofileid);
		if(userprofileget == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user profile");
		}
		
		return userprofileget;
		
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(method=RequestMethod.POST,value="/checkpassword")
	public String getCheckpassword(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody ChangePassword obj) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		String checkpoint = "{ 'StrongValue' : '0'  }";
		
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
			//String decrypted_byte_oldpwd_only ="";
			String decrypted_byte_newpwd_only ="";
			
			//byte[] hex2Byteoldpwd = util.hexToBytes(obj.getOldpassword());
			byte[] hex2Bytenewpwd = util.hexToBytes(obj.getNewpassword());
	        try {
	        	//decrypted_byte_oldpwd_only=util.doDecryptiononly(hex2Byteoldpwd,encryptionkey);
	        	decrypted_byte_newpwd_only=util.doDecryptiononly(hex2Bytenewpwd,encryptionkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //boolean isEmptydecrpassword = decrypted_byte_oldpwd_only == null || decrypted_byte_oldpwd_only.trim().length() == 0;
	        boolean isEmptydecrnewpassword = decrypted_byte_newpwd_only == null || decrypted_byte_newpwd_only.trim().length() == 0;
	        //if(!isEmptydecrpassword)
	        //{
	        	//obj.setOldpassword(decrypted_byte_oldpwd_only);
	        //}
	        
	        if(!isEmptydecrnewpassword)
	        {
	        	obj.setNewpassword(decrypted_byte_newpwd_only);
	        }
		}
		
		
		
		if(this.isStrongpassword(obj.getNewpassword()))
		{
			checkpoint = "{ 'StrongValue' : '100'  }";
		}
		else
		{
			checkpoint = "{ 'StrongValue' : '0'  }";
		}
		JsonElement je = jp.parse(checkpoint);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
	
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/dologout")
	public UserSource getLogouttime(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		UserSource obj = new UserSource();
		long nowMillis = System.currentTimeMillis();
        Timestamp now = new Timestamp(nowMillis);//(Timestamp) new Date(nowMillis);
		obj.setAuthkey(token);
        obj.setLogouttime(now);
		
		UserSource result = usservice.Updatelogout(headers.get("tenantID").get(0),obj);
		return result;
	}
	
	private boolean isStrongpassword(String password){
	    return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=.])(?=\\S+$).{8,}$");
	    
	}
	
	
	
	
	
	
	@SuppressWarnings("unused")
	private static Node Authenticate2(Document doc, String keyvalue, String valuedata) {
        
		Element node = doc.createElement(keyvalue);
        node.appendChild(doc.createTextNode(valuedata));

        return node;
       
    }
	
	


	/*
		V3 Start Here
	*/

	@RequestMapping(method=RequestMethod.POST,value="/")
	public LoginResponse getCreatevthree(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginRequest obj) {
		
		LoginResponse loginResponse = null;
		String tenantId = headers.get("tenantID").get(0);
		
		String message="";
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			message = "User is empty";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, message);
		}
		
		if(isEmptypassword)
		{
			message = "Password is empty";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, message);
		}
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("localAddress", request.getLocalAddr());
		requestMap.put("remoteAddress", request.getRemoteAddr());
		requestMap.put("browserInfo", headers.get("User-Agent").get(0));
		
		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		System.out.println("tenantId : " + tenantId);
		
		//LoginForm userData = service.findByUsername(tenantId,obj.getUsername());
		
		loginResponse = service.checkLogin(tenantId,obj,requestMap);
		
		boolean isEmptyaccesstoken = loginResponse.getAccessToken() == null || loginResponse.getAccessToken().trim().length() == 0;
			
		if(isEmptyaccesstoken)
		{
			throw new CoreException(HttpStatus.UNAUTHORIZED,loginResponse.getErrorMessage() );
		}
		else 
		{
			return loginResponse;
		}
			
		
	}

	@RequestMapping(method=RequestMethod.POST)
	public LoginResponse getLogin(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginRequest obj) {
		
		LoginResponse loginResponse = null;
		String tenantId = headers.get("tenantID").get(0);
		
		String message="";
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			message = "User is empty";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, message);
		}
		
		if(isEmptypassword)
		{
			message = "Password is empty";
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, message);
		}
		
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("localAddress", request.getLocalAddr());
		requestMap.put("remoteAddress", request.getRemoteAddr());
		requestMap.put("browserInfo", headers.get("User-Agent").get(0));
		
		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		System.out.println("tenantId : " + tenantId);
		
		//LoginForm userData = service.findByUsername(tenantId,obj.getUsername());
		
		loginResponse = service.checkLogin(tenantId,obj,requestMap);
		
		boolean isEmptyaccesstoken = loginResponse.getAccessToken() == null || loginResponse.getAccessToken().trim().length() == 0;
			
		if(isEmptyaccesstoken)
		{
			throw new CoreException(HttpStatus.UNAUTHORIZED,loginResponse.getErrorMessage() );
		}
		else 
		{
			return loginResponse;
		}
			
		
	}


	/*
		V3 Ends Here
	*/

}