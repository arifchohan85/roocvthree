package com.egeroo.roocvthree.loginform;

import java.io.IOException;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import com.egeroo.roocvthree.menulist.Menulist;
import com.egeroo.roocvthree.menulist.MenulistService;
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
import com.egeroo.roocvthree.core.util.PasswordGenerator;
import com.egeroo.roocvthree.core.util.Testtripledes;
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

	@Autowired
	private MenulistService menuservice;
	

	
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
		EngineCredential result = new EngineCredential();


		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";


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
	
    

	@RequestMapping(method=RequestMethod.POST,value="/dologinencrypt")
	public String getCreateencrypt(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginForm obj) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}
		
		if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}
		
		
		RoocConfig roocconfig = new RoocConfig();
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
			String decrypted_byte_only ="";
			
			byte[] hex2Byte = util.hexToBytes(obj.getPassword());
	        try {
				 decrypted_byte_only=util.doDecryptiononly(hex2Byte,encryptionkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        boolean isEmptydecrpassword = decrypted_byte_only == null || decrypted_byte_only.trim().length() == 0;
	        if(!isEmptydecrpassword)
	        {
	        	obj.setPassword(decrypted_byte_only);
	        }
		}
		else
		{
			obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		}
		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {			
			LoginForm lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
			
			String browserType = headers.get("User-Agent").get(0);
			
			String keyName = result.getRootcategory();
			
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);
			
			
			String did = getBrowserInfo(browserType);
			
			
			
			long nowMillis = System.currentTimeMillis();
	        Timestamp now = new Timestamp(nowMillis);
	        
	        long expMillis = nowMillis + 86400000;
	        Timestamp exp = new Timestamp(expMillis);
            
			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			
			usservice.getCreate(headers.get("tenantID").get(0), ussrc);
			
			JSONObject pdcomposer = new JSONObject();
	        
	    	pdcomposer.put("username", result.getChannelusername());
	    	pdcomposer.put("password", result.getChannelPassword());
	    	
	    	String chnlToken ="";
	    	
			
			String uToken = "null";
			chnlToken = "null";
			
			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());
			
			String errorMessage ="";
			if(cpc ==null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
				//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
				errorMessage="Change Password";
			}
			else
			{
				String format = "yyyy-MM-dd HH:mm:ss.SSS";
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				long nowMilliscp = System.currentTimeMillis();
		        Timestamp nowcp = new Timestamp(nowMilliscp);
				 
				try {
					Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
					Date dateObj2 = sdf.parse(nowcp.toString());
					System.out.println(dateObj1);
					System.out.println(dateObj2 + "\n");
					
					long diff = dateObj2.getTime() - dateObj1.getTime();
					 
					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println("difference between days: " + diffDays);
					
					int cpclLimitexpire = 90;
					
					ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
					cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));
					
					if(cpclimit == null)
					{
						cpclLimitexpire = 90;
					}
					else
					{
						if(cpclimit.getExpirein()<=0)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							cpclLimitexpire = cpclimit.getExpirein();
						}
						
					}
					
					if(diffDays>=cpclLimitexpire)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
						errorMessage="Password Expired";
					}
					/*else
					{
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true }";
					}*/
					
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	    	
			
			
			ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			JsonElement je = jp.parse(ac);
			prettyJsonString = gson.toJson(je);
			
			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);
			
			return prettyJsonString;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang
			
			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password");
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	
    
	
	@RequestMapping(method=RequestMethod.POST,value="/dologinbak24042021")
	public String getCreatebak24042021(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginForm obj) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		EngineCredential result = new EngineCredential();
		
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}
		
		if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}
		
		
		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		
		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {			
			LoginForm lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
			
			String browserType = headers.get("User-Agent").get(0);
			
			String keyName = result.getRootcategory();
			
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);
			
			
			String did = getBrowserInfo(browserType);
			
			
			
			long nowMillis = System.currentTimeMillis();
	        Timestamp now = new Timestamp(nowMillis);
	        
	        long expMillis = nowMillis + 86400000;
	        Timestamp exp = new Timestamp(expMillis);
            
			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			
			usservice.getCreate(headers.get("tenantID").get(0), ussrc);
			
			JSONObject pdcomposer = new JSONObject();
	        
	    	pdcomposer.put("username", result.getChannelusername());
	    	pdcomposer.put("password", result.getChannelPassword());
	    	
	    	String chnlToken ="";
	    	
			
			String uToken = "null";
			chnlToken = "null";
			
			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());
			
			String errorMessage ="";
			if(cpc ==null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
				//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
				errorMessage="Change Password";
			}
			else
			{
				String format = "yyyy-MM-dd HH:mm:ss.SSS";
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				long nowMilliscp = System.currentTimeMillis();
		        Timestamp nowcp = new Timestamp(nowMilliscp);
				 
				try {
					Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
					Date dateObj2 = sdf.parse(nowcp.toString());
					System.out.println(dateObj1);
					System.out.println(dateObj2 + "\n");
					
					long diff = dateObj2.getTime() - dateObj1.getTime();
					 
					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println("difference between days: " + diffDays);
					
					int cpclLimitexpire = 90;
					
					ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
					cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));
					
					if(cpclimit == null)
					{
						cpclLimitexpire = 90;
					}
					else
					{
						if(cpclimit.getExpirein()<=0)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							cpclLimitexpire = cpclimit.getExpirein();
						}
						
					}
					
					if(diffDays>=cpclLimitexpire)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
						errorMessage="Password Expired";
					}
					/*else
					{
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true }";
					}*/
					
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	    	
			
			
			ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			JsonElement je = jp.parse(ac);
			prettyJsonString = gson.toJson(je);
			
			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);
			
			return prettyJsonString;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang
			
			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password");
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}

	@RequestMapping(method=RequestMethod.POST,value="/dologin")
	public LoginFormData getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginForm obj) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();

		EngineCredential result = new EngineCredential();
		//result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";



		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;

		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}

		if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}


		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));


		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		if (result == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}

		JSONObject pdcomposer = new JSONObject();

		pdcomposer.put("username", result.getChannelusername());
		pdcomposer.put("password", result.getChannelPassword());

		String chnlToken ="";

		if (result == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}


		String serverChannel ="";


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

		System.out.println("Channel Token is : " +chnlToken);
		System.out.println("server channel is : " +serverChannel);

		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));

		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {
			LoginForm lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());

			String browserType = headers.get("User-Agent").get(0);

			String keyName = result.getRootcategory();

			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);


			String did = getBrowserInfo(browserType);



			long nowMillis = System.currentTimeMillis();
			Timestamp now = new Timestamp(nowMillis);

			long expMillis = nowMillis + 86400000;
			Timestamp exp = new Timestamp(expMillis);

			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			ussrc.setChanneltoken(chnlToken);

			usservice.getCreate(headers.get("tenantID").get(0), ussrc);




			String uToken = "null";
			chnlToken = "null";

			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());

			String errorMessage ="";
			if(cpc ==null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
				//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
				errorMessage="Change Password";
			}
			else
			{
				String format = "yyyy-MM-dd HH:mm:ss.SSS";
				SimpleDateFormat sdf = new SimpleDateFormat(format);

				long nowMilliscp = System.currentTimeMillis();
				Timestamp nowcp = new Timestamp(nowMilliscp);

				try {
					Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
					Date dateObj2 = sdf.parse(nowcp.toString());
					System.out.println(dateObj1);
					System.out.println(dateObj2 + "\n");

					long diff = dateObj2.getTime() - dateObj1.getTime();

					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println("difference between days: " + diffDays);

					int cpclLimitexpire = 90;

					ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
					cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));

					if(cpclimit == null)
					{
						cpclLimitexpire = 90;
					}
					else
					{
						if(cpclimit.getExpirein()<=0)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							cpclLimitexpire = cpclimit.getExpirein();
						}

					}

					if(diffDays>=cpclLimitexpire)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
						errorMessage="Password Expired";
					}
					/*else
					{
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true }";
					}*/


				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


			LoginFormData response = new LoginFormData();
			//response.setUserchnltoken(uToken);
			//response.setChnltoken(chnlToken);
			//response.setAccess_token(access_token);
			response.setAccessToken(access_token);

			response.setErrorMessage(errorMessage);

			UserProfile upsearchbyuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), lgf.getUserid());

			List<Menulist> resultmenu = null;
			if(upsearchbyuser == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid user profileid");
				System.out.println("==================================");
				System.out.println("Invalid user profileid");
				System.out.println("==================================");
			}
			else
			{
				resultmenu = menuservice.getListmenuwithrole(headers.get("tenantID").get(0),upsearchbyuser.getRoleid());
			}


			//response.setDataMenu(resultmenu);


			ac = "{ 'userchnltoken' : '"+ uToken +"' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			JsonElement je = jp.parse(ac);
			prettyJsonString = gson.toJson(je);

			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);


			//return prettyJsonString;
			return response;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang

			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
		}
		else
		{
			throw new CoreException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}
		//return 0;

		//service.getCreate(headers.get("tenantID").get(0),obj);
	}




	@RequestMapping(method=RequestMethod.POST,value="/dologinresetpassword")
	public String getCreateresetpassword(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginFormldap obj) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String passwordobject ="";
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		//boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}
		
		/*if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}*/
		
		passwordobject = obj.getPassword();
		RoocConfig roocconfig = new RoocConfig();
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
			String decrypted_byte_only ="";
			
			byte[] hex2Byte = util.hexToBytes(obj.getPassword());
	        try {
				 decrypted_byte_only=util.doDecryptiononly(hex2Byte,encryptionkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        boolean isEmptydecrpassword = decrypted_byte_only == null || decrypted_byte_only.trim().length() == 0;
	        if(!isEmptydecrpassword)
	        {
	        	obj.setPassword(decrypted_byte_only);
	        }
		}
		else
		{
			obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
		}
		
		
		
		if (service.isUserExistun(headers.get("tenantID").get(0),obj)) {			
			LoginFormldap lgf = service.findByUsernameldap(headers.get("tenantID").get(0), obj.getUsername());
			
			String browserType = headers.get("User-Agent").get(0);
			
			String keyName = result.getRootcategory();
			
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);
			
			String did = getBrowserInfo(browserType);
			
			long nowMillis = System.currentTimeMillis();
	        Timestamp now = new Timestamp(nowMillis);
	        
	        long expMillis = nowMillis + 86400000;
	        Timestamp exp = new Timestamp(expMillis);
            
            JSONObject pdcomposer = new JSONObject();
	        
	    	pdcomposer.put("username", result.getChannelusername());
	    	pdcomposer.put("password", result.getChannelPassword());
	    	
	    	String chnlToken ="";
	    	
			
			String uToken = "null";
			chnlToken = "null";
			
			String errorMessage ="Reset Password";
			
            
            
            
			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			
			usservice.getCreate(headers.get("tenantID").get(0), ussrc);
			
			
			ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			JsonElement je = jp.parse(ac);
			prettyJsonString = gson.toJson(je);
			
			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);
			
			return prettyJsonString;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang
			
			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password");
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	
    
	//ini yang mau di pakai di bca
	@RequestMapping(method=RequestMethod.POST,value="/dologinldapwithpwd")
	public String getCreateldapwithpwd(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginFormldap obj) {
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
		
		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;
		
		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}
		
		if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}
		
		int isUseLdap =0;
		RoocConfig roocconfig = new RoocConfig();
		roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isUseldap");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for ldap found.");
        }
		//else
		//{
			isUseLdap = Integer.parseInt(roocconfig.getConfigvalue());
		//}
			
			
		
			
		
		boolean isValiduserldap = true;
		String domain_id ="";
		String display_name ="";
		String job_title ="";
		String company_code ="";
		String kanwil_name ="";
		String branch_name ="";
		String kcp_name ="";
		String department ="";
		if(isUseLdap==1)
		{
			RoocConfig resultappid = new RoocConfig();
			resultappid = rcservice.findByconfigkey(headers.get("tenantID").get(0),"applicationid");
			if (resultappid == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
			}

			RoocConfig resultldappostapi = new RoocConfig();
			resultldappostapi = rcservice.findByconfigkey(headers.get("tenantID").get(0),"ldappostapi");

			if (resultldappostapi == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post api uri found.");
			}



			RoocConfig resultposturl = new RoocConfig();
			resultposturl = rcservice.findByconfigkey(headers.get("tenantID").get(0),"ldaploginposturl");

			if (resultposturl == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post uri found.");
			}
			//password yang kita terima sudah di encrypt dalam bentuk 3DASH
			JSONObject ldapcomposer = new JSONObject();
	        
			
			
			
			int isUse3des =0;
			roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isUse3des");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for isUse3des found.");
	        }
			else
			{
				isUse3des = Integer.parseInt(roocconfig.getConfigvalue());
			}
			
			String decrypted_byte_only ="";
			String passwordbca ="";
			if(isUse3des==1)
			{
				roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"encryptionkey");
				if (roocconfig == null) {
		            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for encryptionkey found.");
		        }
				String encryptionkey = roocconfig.getConfigvalue();
				
				
				byte[] hex2Byte = util.hexToBytes(obj.getPassword());
		        try {
					 decrypted_byte_only=util.doDecryptiononly(hex2Byte,encryptionkey);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        boolean isEmptydecrpassword = decrypted_byte_only == null || decrypted_byte_only.trim().length() == 0;
		        if(!isEmptydecrpassword)
		        {
		        	obj.setPassword(decrypted_byte_only);
		        	try {
						passwordbca = Testtripledes.cryptBC(decrypted_byte_only,encryptionkey);
						System.out.println("Hasil : " + passwordbca);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
			
			ldapcomposer.put("UserID", obj.getUsername());
			ldapcomposer.put("Password", passwordbca);
			ldapcomposer.put("ApplicationID", resultappid.getConfigvalue());
			
			try {
				String ldapApi = resultldappostapi.getConfigvalue();
	    		postret = hpr.setPostDataldapbca(ldapApi+""+resultposturl.getConfigvalue(),headers.get("tenantID").get(0),ldapcomposer);
	    		
	    		//http://10.20.200.140:9407/ad-gateways/verify3
	    		
	    		String jsonString = postret;
		        JSONObject jsonObject;
				
		        if(validatejson.isJSONValidstandard(jsonString))
		        {
					jsonObject = new JSONObject(jsonString);
					
					if(!jsonObject.has("OutputSchema"))
					{
						isValiduserldap = false;
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password from ldap");
						System.out.println("No Schema");
					}
					else
					{
						System.out.println("Has Schema");
				    	JSONObject jsonObjectins = jsonObject.getJSONObject("OutputSchema");
				    	if(jsonObjectins.has("Status"))
				    	{
				    		System.out.println("Has Status");
				    		System.out.println(jsonObjectins.getString("Status"));
				    		if(!jsonObjectins.getString("Status").equals("0"))
				    		{
				    			isValiduserldap = false;
				    			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password from ldap");
				    		}
				    		else
				    		{
				    			if(jsonObjectins.has("Status"))
				    	    	{
				    				//get data user
				    	    		String hprret = "";
				    				
				    				try {
				    					//hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
				    					hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
				    					
				    					System.out.println(hprret);
				    					if(validatejson.isJSONValidstandard(hprret))
				    			        {
				    						JSONObject jsonObjectget = new JSONObject(hprret);
					    					
					    				    if(!jsonObjectget.has("output_schema"))
					    					{
					    				    	isValiduserldap = false;
					    						System.out.println("! Schema");
					    						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
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
					    				    	  
					    				    	   System.out.println(domain_id);	
					    				    	}
					    				    }
				    			        }
				    					else
				    					{
				    						isValiduserldap = false;
				    					}
				    					
				    				}catch(Exception ex)
				    				{
				    					isValiduserldap = false;
				    					System.out.println(ex);
				    				}
				    	    	}
				    			else
				    			{
				    				isValiduserldap = false;
				    			}
				    		}
				    	}
						
					}
		        }
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				isValiduserldap = false;
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				isValiduserldap = false;
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				isValiduserldap = false;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				isValiduserldap = false;
				e.printStackTrace();
			}
		}
		else
		{
			isValiduserldap = false;
		}
		
		
		if (!service.isUserExistun(headers.get("tenantID").get(0),obj)) {
			//add user to user profile
		}
		/*else
		{
			if(isValiduserldap)
			{
				
			}
		}*/
		
		boolean isUserexist=true;
		if(isValiduserldap)
		{
			if (!service.isUserExistunldap(headers.get("tenantID").get(0),obj)) {
				isUserexist =false;
			}
		}
		else
		{
			//set password
			//decrypt 3des password
			
			//ntar set password disini yah
			
			//DigestUtils.md5Hex(DigestUtils.md5Hex(Password));
			LoginForm lgfchk = new LoginForm();
			lgfchk.setUsername(obj.getUsername());
			lgfchk.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
			if (!service.isUserExist(headers.get("tenantID").get(0),lgfchk)) {
				isUserexist =false;
			}
		}
		
		//if (service.isUserExistun(headers.get("tenantID").get(0),obj)) {	
		if(isUserexist) {
			LoginForm lgf = new LoginForm();
			if(isValiduserldap)
			{
				lgf = service.findByUsername(headers.get("tenantID").get(0), obj.getUsername());
			}
			else
			{
				lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(),DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
			}
			
			
			if(lgf == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password");
			}
			
			/*if(isValiduserldap)
			{
				UserProfile upsearchbyuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), lgf.getUserid());
				
				if(upsearchbyuser == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid user profileid");
				}
				
				long nowMillis = System.currentTimeMillis();
		        Timestamp now = new Timestamp(nowMillis);
				//berarti update content
				UserProfile userprof = new UserProfile();
				userprof.setUserprofileid(upsearchbyuser.getUserprofileid());
				userprof.setUsername(obj.getUsername());
				//userprof.setPassword(obj.getPassword());
				userprof.setName(display_name);
				userprof.setAddress(company_code);
				userprof.setEmailaddress(display_name+"@roocvthree.ai");
				userprof.setRoleid(upsearchbyuser.getRoleid());
				userprof.setCompany_code(company_code);
				userprof.setBranch_name(branch_name);
				userprof.setDisplay_name(display_name);
				userprof.setDomain_id(domain_id);
				userprof.setJob_title(job_title);
				userprof.setKanwil_name(kanwil_name);
				userprof.setKcp_name(kcp_name);
				userprof.setDepartment(department);
				
				userprof.setCreatedby(1);
				userprof.setUpdatedby(1);
				userprof.setCreatedtime(now);
				userprof.setUpdatedtime(now);
				upservice.getUpdate(headers.get("tenantID").get(0), userprof);
			}*/
			
			String browserType = headers.get("User-Agent").get(0);
			
			String keyName = result.getRootcategory();
			
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);
			
			String did = getBrowserInfo(browserType);
			
			long nowMillis = System.currentTimeMillis();
	        Timestamp now = new Timestamp(nowMillis);
	        
	        long expMillis = nowMillis + 86400000;
	        Timestamp exp = new Timestamp(expMillis);
            
			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			
			usservice.getCreate(headers.get("tenantID").get(0), ussrc);
			
			JSONObject pdcomposer = new JSONObject();
	        
	    	pdcomposer.put("username", result.getChannelusername());
	    	pdcomposer.put("password", result.getChannelPassword());
	    	
	    	String chnlToken ="";
	    	
			
			String uToken = "null";
			chnlToken = "null";
			
			String errorMessage ="";
			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());
			if(!isValiduserldap)
			{
				if(cpc ==null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
					//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
					errorMessage="Change Password";
				}
				else
				{
					String format = "yyyy-MM-dd HH:mm:ss.SSS";
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					
					long nowMilliscp = System.currentTimeMillis();
			        Timestamp nowcp = new Timestamp(nowMilliscp);
					 
					try {
						Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
						Date dateObj2 = sdf.parse(nowcp.toString());
						System.out.println(dateObj1);
						System.out.println(dateObj2 + "\n");
						
						long diff = dateObj2.getTime() - dateObj1.getTime();
						 
						int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
						System.out.println("difference between days: " + diffDays);
						
						int cpclLimitexpire = 90;
						
						ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
						cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));
						
						if(cpclimit == null)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							if(cpclimit.getExpirein()<=0)
							{
								cpclLimitexpire = 90;
							}
							else
							{
								cpclLimitexpire = cpclimit.getExpirein();
							}
							
						}
						
						if(diffDays>=cpclLimitexpire)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
							//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
							errorMessage="Password Expired";
						}
						
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
			
			
			
			ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			JsonElement je = jp.parse(ac);
			prettyJsonString = gson.toJson(je);
			
			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);
			
			return prettyJsonString;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang
			
			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password");
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	
	
	//ini yang mau di pakai di bca,validate token
	@RequestMapping(method=RequestMethod.POST,value="/dologinldapwithtoken")
	public String getCreateldapwithtoken(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginFormldap obj) {
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
		
		int isUseLdap =0;
		RoocConfig roocconfig = new RoocConfig();
		roocconfig = rcservice.findByconfigkey(headers.get("tenantID").get(0),"isUseldap");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no configuration for ldap found.");
        }
		//else
		//{
			isUseLdap = Integer.parseInt(roocconfig.getConfigvalue());
		//}
		
		
		boolean isValiduserldap = true;
		boolean isValidtoken = false;
		String domain_id ="";
		String display_name ="";
		String job_title ="";
		String company_code ="";
		String kanwil_name ="";
		String branch_name ="";
		String kcp_name ="";
		String department ="";
		if(isUseLdap==1)
		{
			
			
			//get data user
			String hprret = "";
			
			String passtokenbca ="";
			boolean isEmptytokenbcaheader = request.getHeader("tokenBCA") == null || request.getHeader("tokenBCA").trim().length() == 0;
			
			if(isEmptytokenbcaheader)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "No Token to validate");
			}
			else
			{
				passtokenbca = headers.get("tokenBCA").get(0);
			}
			
		
		
			if(passtokenbca.equals(""))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "No Token to validate");
			}
			
			try {
				hprret = hpr.getGetdatatokenbca(passtokenbca,headers.get("tenantID").get(0));
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
			    	//isValiduserldap = false;
					System.out.println("! Schema");
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
				}
			    else
			    {
			    	System.out.println("Has Schema");
			    	
			    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
			    	for(int i = 0; i < jsonarray .length(); i++)
			    	{
			    	   JSONObject object3 = jsonarray.getJSONObject(i);
			    	   if(object3.has("isValidate"))
			    	   {
			    		   //isValiduserldap = object3.getBoolean("isValidate");
			    		   isValidtoken = object3.getBoolean("isValidate");
			    	   }
			    	   
			    	   System.out.println(isValidtoken);	
			    	}
			    }
	        }
			else
			{
				//isValiduserldap = false;
			}
			
			if(isValidtoken)
			{
				try {
					//hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
					hprret = hpr.getGetdataldapbca(obj.getUsername(),headers.get("tenantID").get(0));
					
					System.out.println(hprret);
					if(validatejson.isJSONValidstandard(hprret))
			        {
						JSONObject jsonObjectget = new JSONObject(hprret);
    					
    				    if(!jsonObjectget.has("output_schema"))
    					{
    				    	isValiduserldap = false;
    						System.out.println("! Schema");
    						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
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
    				    	  
    				    	   System.out.println(domain_id);	
    				    	}
    				    }
			        }
					else
					{
						isValiduserldap = false;
					}
					
				}catch(Exception ex)
				{
					isValiduserldap = false;
					System.out.println(ex);
				}
			}
			else
			{
				isValiduserldap = false;
			}
		}
		else
		{
			isValiduserldap = false;
			isValidtoken = false;
		}
		
		
		if (!service.isUserExistun(headers.get("tenantID").get(0),obj)) {
			//add user to user profile
		}
		/*else
		{
			
		}*/
		
		boolean isUserexist=true;
		if(isValiduserldap)
		{
			if (!service.isUserExistunldap(headers.get("tenantID").get(0),obj)) {
				isUserexist =false;
			}
		}
		else
		{
			if(!isValiduserldap)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username ldap");
			}
		}
		
		if(!isValidtoken)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid token");
		}
		
		//if (service.isUserExistun(headers.get("tenantID").get(0),obj)) {	
		if(isUserexist) {
			LoginForm lgf = new LoginForm();
			if(isValiduserldap)
			{
				lgf = service.findByUsername(headers.get("tenantID").get(0), obj.getUsername());
			}
			else
			{
				//lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(),DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username ldap");
			}
			
			
			if(lgf == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username ");
			}
			
			/*if(isValiduserldap)
			{
				UserProfile upsearchbyuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), lgf.getUserid());
				
				if(upsearchbyuser == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid user profileid");
				}
				
				long nowMillis = System.currentTimeMillis();
		        Timestamp now = new Timestamp(nowMillis);
				//berarti update content
				UserProfile userprof = new UserProfile();
				userprof.setUserprofileid(upsearchbyuser.getUserprofileid());
				userprof.setUsername(obj.getUsername());
				//userprof.setPassword(obj.getPassword());
				userprof.setName(display_name);
				userprof.setAddress(company_code);
				userprof.setEmailaddress(display_name+"@roocvthree.ai");
				userprof.setRoleid(upsearchbyuser.getRoleid());
				userprof.setCompany_code(company_code);
				userprof.setBranch_name(branch_name);
				userprof.setDisplay_name(display_name);
				userprof.setDomain_id(domain_id);
				userprof.setJob_title(job_title);
				userprof.setKanwil_name(kanwil_name);
				userprof.setKcp_name(kcp_name);
				userprof.setDepartment(department);
				
				userprof.setCreatedby(1);
				userprof.setUpdatedby(1);
				userprof.setCreatedtime(now);
				userprof.setUpdatedtime(now);
				upservice.getUpdate(headers.get("tenantID").get(0), userprof);
			}*/
			
			String browserType = headers.get("User-Agent").get(0);
			
			String keyName = result.getRootcategory();
			
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);
			
			String did = getBrowserInfo(browserType);
			
			long nowMillis = System.currentTimeMillis();
	        Timestamp now = new Timestamp(nowMillis);
	        
	        long expMillis = nowMillis + 86400000;
	        Timestamp exp = new Timestamp(expMillis);
            
			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			
			usservice.getCreate(headers.get("tenantID").get(0), ussrc);
			
			JSONObject pdcomposer = new JSONObject();
	        
	    	pdcomposer.put("username", result.getChannelusername());
	    	pdcomposer.put("password", result.getChannelPassword());
	    	
	    	String chnlToken ="";
	    	
			
			String uToken = "null";
			chnlToken = "null";
			
			String errorMessage ="";
			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());
			if(!isValiduserldap)
			{
				if(cpc ==null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
					//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
					errorMessage="Change Password";
				}
				else
				{
					String format = "yyyy-MM-dd HH:mm:ss.SSS";
					SimpleDateFormat sdf = new SimpleDateFormat(format);
					
					long nowMilliscp = System.currentTimeMillis();
			        Timestamp nowcp = new Timestamp(nowMilliscp);
					 
					try {
						Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
						Date dateObj2 = sdf.parse(nowcp.toString());
						System.out.println(dateObj1);
						System.out.println(dateObj2 + "\n");
						
						long diff = dateObj2.getTime() - dateObj1.getTime();
						 
						int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
						System.out.println("difference between days: " + diffDays);
						
						int cpclLimitexpire = 90;
						
						ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
						cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));
						
						if(cpclimit == null)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							if(cpclimit.getExpirein()<=0)
							{
								cpclLimitexpire = 90;
							}
							else
							{
								cpclLimitexpire = cpclimit.getExpirein();
							}
							
						}
						
						if(diffDays>=cpclLimitexpire)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
							//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
							errorMessage="Password Expired";
						}
						
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
			
			
			
			ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			JsonElement je = jp.parse(ac);
			prettyJsonString = gson.toJson(je);
			
			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);
			
			return prettyJsonString;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang
			
			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
        }
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password");
		}
		//return 0;
		
		//service.getCreate(headers.get("tenantID").get(0),obj);
	}
	

    
	@RequestMapping(method=RequestMethod.POST,value="/changepassword")
	public UserProfile getChangepassword(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody ChangePassword obj) {
		
		String token ="";
		String oldpasswordobj ="";
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
		/*else
		{
			obj.setOldpassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getOldpassword())));
			obj.setNewpassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getNewpassword())));
			obj.setRepassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getRepassword())));
		}*/
		
		oldpasswordobj = obj.getOldpassword();
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
					
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					//String userinfo = "{  'namalengkap' : '" + resultbefore.getName() + "' , 'alamat' : '" + resultbefore.getAddress() + "' , 'email' : '" + resultbefore.getEmailaddress() + "' }";
					//JsonElement je = jp.parse(userinfo);
					//String prettyJsonString = gson.toJson(je);
					
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
	public UserProfile getChangeresetpassword(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer userprofileid) {
		
		String token ="";
		String oldpasswordobj ="";
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
		
		
		if(userprofileid == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user profileid");
		}
		
		if(userprofileid <=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user profileid");
		}
		
		ChangePassword obj = new ChangePassword();
		
		
		
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
		
		//untuk using 3des aku tutup sementara
		/*if(isUse3des==1)
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
		*/
		/*else
		{
			obj.setOldpassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getOldpassword())));
			obj.setNewpassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getNewpassword())));
			obj.setRepassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getRepassword())));
		}*/
		
		// generate 5 random password
		String genPassword ="";
		PasswordGenerator pg = new PasswordGenerator();
        //for (int i = 0; i < 1; i++) {
		genPassword = pg.generateRandomPassword(10);
        System.out.println("password : " + genPassword);
        System.out.println("\n");
        //}
        newpasswordobj = genPassword;
        obj.setNewpassword(genPassword);
        obj.setRepassword(genPassword);
        
        UserProfileView userprofileget = upservice.getView(headers.get("tenantID").get(0),userprofileid);
		if(userprofileget == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user profile");
		}
		
		int userid = userprofileget.getUserId();//trb.Parseuserid(token);
		obj.setUserid(userid);
        
        UserProfile resultbefore = upservice.getPasswordviewbyuserid(headers.get("tenantID").get(0),userid);
		obj.setOldpassword(resultbefore.getPassword());
        
		LoginForm objl = new LoginForm();
		objl.setPassword(obj.getOldpassword());
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
				//if(this.isStrongpassword(obj.getNewpassword()))
				//{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password not strong enough");
					
					
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
					}
					
					trb.SetTrailRecord(token,cpc);
					cpc = cpservice.getCreate(headers.get("tenantID").get(0), cpc);
					
					
					UserProfile result = upservice.getViewbyuserid(headers.get("tenantID").get(0),userid);
					result.setGeneratedpassword(genPassword);
					
					
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
					
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					JsonParser jp = new JsonParser();
					//String userinfo = "{  'namalengkap' : '" + resultbefore.getName() + "' , 'alamat' : '" + resultbefore.getAddress() + "' , 'email' : '" + resultbefore.getEmailaddress() + "' }";
					//JsonElement je = jp.parse(userinfo);
					//String prettyJsonString = gson.toJson(je);
					
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
				//}
				//else
				//{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password not strong enough");
				//}
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
	
	@RequestMapping(method=RequestMethod.GET,value="/ldapgeturi")
	public String getLdapgeturi(@RequestHeader HttpHeaders headers,HttpServletRequest request) {

		/*RoocConfig resultldapgetapi = new RoocConfig();
		resultldapgetapi = rcservice.findByconfigkey(headers.get("tenantID").get(0),"ldapgetapi");

		if (resultldapgetapi == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no get api uri found.");
        }

		RoocConfig resultgeturl = new RoocConfig();
		resultgeturl = rcservice.findByconfigkey(headers.get("tenantID").get(0),"ldaplogingeturl");

		if (resultgeturl == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post uri found.");
        }
		*/
		//get data user
		String hprret = "";

		//U052368
		//ibofdev01
		try {
			hprret = hpr.getGetdataldapbca("ibofdev01",headers.get("tenantID").get(0));
		}catch(Exception ex)
		{
			System.out.println(ex);
		}


		return hprret;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/dologinldappostbca")
	public String getCreateldappostbca(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginFormldap obj) {
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

		RoocConfig resultappid = new RoocConfig();
		resultappid = rcservice.findByconfigkey(headers.get("tenantID").get(0),"applicationid");


		if (resultappid == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
		}

		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));

		RoocConfig resultldappostapi = new RoocConfig();
		resultldappostapi = rcservice.findByconfigkey(headers.get("tenantID").get(0),"ldappostapi");

		if (resultldappostapi == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post api uri found.");
		}



		RoocConfig resultposturl = new RoocConfig();
		resultposturl = rcservice.findByconfigkey(headers.get("tenantID").get(0),"ldaploginposturl");

		if (resultposturl == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post uri found.");
		}




		//password yang kita terima sudah di encrypt dalam bentuk 3DASH
		JSONObject ldapcomposer = new JSONObject();

		ldapcomposer.put("UserID", obj.getUsername());
		ldapcomposer.put("Password", obj.getPassword());
		ldapcomposer.put("ApplicationID", resultappid.getConfigvalue());

		boolean isValiduserldap = true;
		try {

			String ldapApi = resultldappostapi.getConfigvalue();

			postret = hpr.setPostDataldapbca(ldapApi+""+resultposturl.getConfigvalue(),headers.get("tenantID").get(0),ldapcomposer);

			//http://10.20.200.140:9407/ad-gateways/verify3

			String jsonString = postret;
			JSONObject jsonObject;

			jsonObject = new JSONObject(jsonString);

			if(!jsonObject.has("OutputSchema"))
			{
				isValiduserldap = false;
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password from ldap");
			}

			System.out.println("Has Schema");
			JSONObject jsonObjectins = jsonObject.getJSONObject("OutputSchema");
			if(jsonObjectins.has("Status"))
			{
				System.out.println("Has Status");
				System.out.println(jsonObjectins.getString("Status"));
				if(!jsonObjectins.getString("Status").equals("0"))
				{
					isValiduserldap = false;
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid username or password from ldap");
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


		return postret;

	}
	
	@RequestMapping(method=RequestMethod.GET,value="/validatetokenbca")
	public boolean getValidatetokenbca(@RequestHeader HttpHeaders headers,HttpServletRequest request,String tokenbca) {

		boolean isEmptytokenbca = tokenbca == null || tokenbca.trim().length() == 0;
		
		String passtokenbca ="";
		if(isEmptytokenbca)
		{
			boolean isEmptytokenbcaheader = request.getHeader("tokenBCA") == null || request.getHeader("tokenBCA").trim().length() == 0;
			
			if(isEmptytokenbcaheader)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "No Token to validate");
			}
			else
			{
				passtokenbca = headers.get("tokenBCA").get(0);
			}
			
		}
		else
		{
			passtokenbca = tokenbca;
		}
		
		if(passtokenbca.equals(""))
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "No Token to validate");
		}
		
		//get data user
		String hprret = "";
		//String tokenbca = headers.get("tokenBCA").get(0);
		try {
			hprret = hpr.getGetdatatokenbca(passtokenbca,headers.get("tenantID").get(0));
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		System.out.println(hprret);
		boolean isValidate = false;
		if(validatejson.isJSONValidstandard(hprret))
        {
			JSONObject jsonObjectget = new JSONObject(hprret);
			
		    if(!jsonObjectget.has("output_schema"))
			{
		    	
				System.out.println("! Schema");
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid domain id or something when wrong");
			}
		    else
		    {
		    	System.out.println("Has Schema");
		    	
		    	JSONArray jsonarray = jsonObjectget.getJSONArray("output_schema");
		    	for(int i = 0; i < jsonarray .length(); i++)
		    	{
		    	   JSONObject object3 = jsonarray.getJSONObject(i);
		    	   if(object3.has("isValidate"))
		    	   {
		    		   isValidate = object3.getBoolean("isValidate");
		    	   }
		    	   
		    	   System.out.println(isValidate);	
		    	}
		    }
        }


		return isValidate;
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
	    //^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$
	    //^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z])
	    //^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z])$
	    //(^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,32}$)?(^(?=.*\d)(?=.*[a-z])(?=.*[@#$%^&+=]).{8,32}$)?(^(?=.*\d)(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,32}$)?(^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,32}$)?
	}
	
	
	
	
	private String  getBrowserInfo(String Information)
	{
	    String browsername = "";
	    String browserversion = "";
	    String browser = Information;
	    if (browser.contains("MSIE"))
	    {
	      String subsString = browser.substring(browser.indexOf("MSIE"));
	      String info[] = (subsString.split(";")[0]).split(" ");
	      browsername = info[0];
	      browserversion = info[1];
	    } else if (browser.contains("Firefox"))
	    {
	
	      String subsString = browser.substring(browser.indexOf("Firefox"));
	      String info[] = (subsString.split(" ")[0]).split("/");
	      browsername = info[0];
	      browserversion = info[1];
	    } else if (browser.contains("Chrome"))
	    {
	
	      String subsString = browser.substring(browser.indexOf("Chrome"));
	      String info[] = (subsString.split(" ")[0]).split("/");
	      browsername = info[0];
	      browserversion = info[1];
	    } else if (browser.contains("Opera"))
	    {
	
	      String subsString = browser.substring(browser.indexOf("Opera"));
	      String info[] = (subsString.split(" ")[0]).split("/");
	      browsername = info[0];
	      browserversion = info[1];
	    } else if (browser.contains("Safari"))
	    {
	
	      String subsString = browser.substring(browser.indexOf("Safari"));
	      String info[] = (subsString.split(" ")[0]).split("/");
	      browsername = info[0];
	      browserversion = info[1];
	    }
	    else
	    {
	    	browsername = "Other/API/Postman";
		    browserversion = "0.0";
	    }
	    return browsername + "-" + browserversion;
	}
	
	@SuppressWarnings("unused")
	private static Node Authenticate2(Document doc, String keyvalue, String valuedata) {
        
		Element node = doc.createElement(keyvalue);
        node.appendChild(doc.createTextNode(valuedata));

        return node;
        /*Element user = doc.createElement("user");

        //user.setAttribute("id", "1");
        user.appendChild(createUserElement(doc, keyvalue, valuedata));
        

        return user;*/
    }
	
	/*private static Node createUserElement(Document doc, String name, 
            String value) {

        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }*/


	/*
		V3 Start Here
	*/

	@RequestMapping(method=RequestMethod.POST,value="/")
	public LoginFormData getCreatev3(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginForm obj) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();

		EngineCredential result = new EngineCredential();
		//result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";



		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;

		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}

		if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}


		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));


		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		if (result == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}



		JSONObject pdcomposer = new JSONObject();

		pdcomposer.put("username", result.getChannelusername());
		pdcomposer.put("password", result.getChannelPassword());

		String chnlToken ="";

		if (result == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}


		String serverChannel ="";


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

		System.out.println("Channel Token is : " +chnlToken);
		System.out.println("server channel is : " +serverChannel);

		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));

		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {
			LoginForm lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());

			String browserType = headers.get("User-Agent").get(0);

			String keyName = result.getRootcategory();

			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);


			String did = getBrowserInfo(browserType);



			long nowMillis = System.currentTimeMillis();
			Timestamp now = new Timestamp(nowMillis);

			long expMillis = nowMillis + 86400000;
			Timestamp exp = new Timestamp(expMillis);

			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			ussrc.setChanneltoken(chnlToken);
			//ussrc.setChanneltoken("channeltoken");

			usservice.getCreate(headers.get("tenantID").get(0), ussrc);




			String uToken = "null";
			//chnlToken = "null";

			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());

			String errorMessage ="";
			if(cpc ==null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
				//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
				errorMessage="Change Password";
			}
			else
			{
				String format = "yyyy-MM-dd HH:mm:ss.SSS";
				SimpleDateFormat sdf = new SimpleDateFormat(format);

				long nowMilliscp = System.currentTimeMillis();
				Timestamp nowcp = new Timestamp(nowMilliscp);

				try {
					Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
					Date dateObj2 = sdf.parse(nowcp.toString());
					System.out.println(dateObj1);
					System.out.println(dateObj2 + "\n");

					long diff = dateObj2.getTime() - dateObj1.getTime();

					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println("difference between days: " + diffDays);

					int cpclLimitexpire = 90;

					ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
					cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));

					if(cpclimit == null)
					{
						cpclLimitexpire = 90;
					}
					else
					{
						if(cpclimit.getExpirein()<=0)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							cpclLimitexpire = cpclimit.getExpirein();
						}

					}

					if(diffDays>=cpclLimitexpire)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
						errorMessage="Password Expired";
					}
					/*else
					{
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true }";
					}*/


				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


			LoginFormData response = new LoginFormData();
			//response.setUserchnltoken(uToken);
			//response.setChnltoken(chnlToken);
			//response.setAccess_token(access_token);
			response.setAccessToken(access_token);

			response.setErrorMessage(errorMessage);

			/*UserProfile upsearchbyuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), lgf.getUserid());

			List<Menulist> resultmenu = null;
			if(upsearchbyuser == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid user profileid");
				System.out.println("==================================");
				System.out.println("Invalid user profileid");
				System.out.println("==================================");
			}
			else
			{
				resultmenu = menuservice.getListmenuwithrole(headers.get("tenantID").get(0),upsearchbyuser.getRoleid());
			}*/


			//response.setDataMenu(resultmenu);


			//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			//ac = "{  'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			//JsonElement je = jp.parse(ac);
			//prettyJsonString = gson.toJson(je);

			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);


			//return prettyJsonString;
			return response;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang

			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
		}
		else
		{
			throw new CoreException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}
		//return 0;

		//service.getCreate(headers.get("tenantID").get(0),obj);
	}

	@RequestMapping(method=RequestMethod.POST)
	public LoginFormData getLogin(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody LoginForm obj) {
		String prettyJsonString ="";
		String ac="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();

		EngineCredential result = new EngineCredential();
		//result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		String postret = "";



		boolean isEmptyusername = obj.getUsername() == null || obj.getUsername().trim().length() == 0;
		boolean isEmptypassword = obj.getPassword() == null || obj.getPassword().trim().length() == 0;

		if(isEmptyusername)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Username cannot be empty");
		}

		if(isEmptypassword)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Password cannot be empty");
		}


		obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));


		result = ecservice.getView(headers.get("tenantID").get(0),this.ECID);
		if (result == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}

		JSONObject pdcomposer = new JSONObject();

		pdcomposer.put("username", result.getChannelusername());
		pdcomposer.put("password", result.getChannelPassword());

		String chnlToken ="";

		if (result == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
		}


		String serverChannel ="";


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

		System.out.println("Channel Token is : " +chnlToken);
		System.out.println("server channel is : " +serverChannel);

		//obj.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(obj.getPassword())));

		if (service.isUserExist(headers.get("tenantID").get(0),obj)) {
			LoginForm lgf = service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());

			String browserType = headers.get("User-Agent").get(0);

			String keyName = result.getRootcategory();

			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			//String access_token = jwtutils.createJWT(""+ lgf.getUserid(), "roocvthree", lgf.getUsername(), 86400000);
			String access_token = jwtutils.createJWT(""+ lgf.getUserid(), keyName, lgf.getUsername(), 86400000);


			String did = getBrowserInfo(browserType);



			long nowMillis = System.currentTimeMillis();
			Timestamp now = new Timestamp(nowMillis);

			long expMillis = nowMillis + 86400000;
			Timestamp exp = new Timestamp(expMillis);

			UserSource ussrc = new UserSource();
			ussrc.setAuthkey(access_token);
			ussrc.setDeviceid(did);
			ussrc.setUserid(lgf.getUserid());
			ussrc.setIsactive(1);
			ussrc.setLogindate(now);
			ussrc.setExpiredate(exp);
			ussrc.setLocaladdr(request.getLocalAddr());
			ussrc.setRemoteaddr(request.getRemoteAddr());
			ussrc.setChanneltoken(chnlToken);

			usservice.getCreate(headers.get("tenantID").get(0), ussrc);




			String uToken = "null";
			chnlToken = "null";

			// check password expiration
			ChangePasswordCycle cpc = new ChangePasswordCycle();
			cpc = cpservice.getViewlastpassword(headers.get("tenantID").get(0), lgf.getUserid());

			String errorMessage ="";
			if(cpc ==null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,first password must be change ." );
				//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Change Password' }";
				errorMessage="Change Password";
			}
			else
			{
				String format = "yyyy-MM-dd HH:mm:ss.SSS";
				SimpleDateFormat sdf = new SimpleDateFormat(format);

				long nowMilliscp = System.currentTimeMillis();
				Timestamp nowcp = new Timestamp(nowMilliscp);

				try {
					Date dateObj1 = sdf.parse(cpc.getUpdatedtime().toString());
					Date dateObj2 = sdf.parse(nowcp.toString());
					System.out.println(dateObj1);
					System.out.println(dateObj2 + "\n");

					long diff = dateObj2.getTime() - dateObj1.getTime();

					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println("difference between days: " + diffDays);

					int cpclLimitexpire = 90;

					ChangePasswordCycleLimit cpclimit = new ChangePasswordCycleLimit();
					cpclimit = cpclservice.getViewlimit(headers.get("tenantID").get(0));

					if(cpclimit == null)
					{
						cpclLimitexpire = 90;
					}
					else
					{
						if(cpclimit.getExpirein()<=0)
						{
							cpclLimitexpire = 90;
						}
						else
						{
							cpclLimitexpire = cpclimit.getExpirein();
						}

					}

					if(diffDays>=cpclLimitexpire)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Password,Expire already after :" +cpclLimitexpire+ " day(s) " );
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true , 'errorMessage' : 'Password Expired' }";
						errorMessage="Password Expired";
					}
					/*else
					{
						//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'chnltoken' :'" + chnlToken + "' , 'access_token' : '" + access_token + "' , 'isValid' : true }";
					}*/


				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


			LoginFormData response = new LoginFormData();
			//response.setUserchnltoken(uToken);
			//response.setChnltoken(chnlToken);
			//response.setAccess_token(access_token);
			response.setAccessToken(access_token);

			response.setErrorMessage(errorMessage);

			/*UserProfile upsearchbyuser = upservice.getViewbyuserid(headers.get("tenantID").get(0), lgf.getUserid());

			List<Menulist> resultmenu = null;
			if(upsearchbyuser == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid user profileid");
				System.out.println("==================================");
				System.out.println("Invalid user profileid");
				System.out.println("==================================");
			}
			else
			{
				resultmenu = menuservice.getListmenuwithrole(headers.get("tenantID").get(0),upsearchbyuser.getRoleid());
			}*/


			//response.setDataMenu(resultmenu);


			//ac = "{ 'userchnltoken' : '"+ uToken +"' , 'access_token' : '" + access_token + "' , 'isValid' : true  , 'errorMessage' : '"+errorMessage+"' }";
			//JsonElement je = jp.parse(ac);
			//prettyJsonString = gson.toJson(je);

			//trb.TrailAll(headers.get("tenantID").get(0),access_token,request);


			//return prettyJsonString;
			return response;
			//return postret;
			//cari token dulu jika ada di gunakan dan expiredate nya di perpanjang,logindatenya di update
			//jika tidak ada maka di create ulang

			//return service.findByUsernameandPassword(headers.get("tenantID").get(0), obj.getUsername(), obj.getPassword());
		}
		else
		{
			throw new CoreException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
		}
		//return 0;

		//service.getCreate(headers.get("tenantID").get(0),obj);
	}


	/*
		V3 Ends Here
	*/

}