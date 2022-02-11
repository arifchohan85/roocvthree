package com.egeroo.roocvthree.loginform;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.changepasswordcycle.ChangePasswordCycleService;
import com.egeroo.roocvthree.changepasswordcyclelimit.ChangePasswordCycleLimitService;
import com.egeroo.roocvthree.core.jwt.JwtUtil;
import com.egeroo.roocvthree.core.usersource.UserSourceService;
import com.egeroo.roocvthree.loginform.LoginFormService;
import com.egeroo.roocvthree.user.UserService;
import com.egeroo.roocvthree.userprofile.UserProfileService;
import com.egeroo.roocvthree.userprofile.UserProfileView;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.changepasswordcycle.ChangePasswordCycle;
import com.egeroo.roocvthree.changepasswordcyclelimit.ChangePasswordCycleLimit;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.usersource.UserSource;
import com.egeroo.roocvthree.core.util.PasswordGenerator;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.user.User;
import com.egeroo.roocvthree.userprofile.UserProfile;






@Service
public class LoginFormService {
	
private final Logger logger = LogManager.getLogger(LoginFormService.class);
	
	@Autowired
    private ChangePasswordCycleService cpservice;
	
	@Autowired
    private ChangePasswordCycleLimitService cpclservice;
	
	@Autowired
	private UserSourceService usservice;
	
	@Autowired
    private UserService uservice;
	
	@Autowired
    private UserProfileService upservice;
	
	@Autowired
	private EngineCredentialService ecservice;
	
	JwtUtil jwtutils = new JwtUtil();
	
	HttpPostReq hpr = new HttpPostReq();
	
	ValidationJson validatejson = new ValidationJson();
	
	private int ECID =1;
	
	public LoginForm findByUsernameandPassword(String tenant,String uname,String pswd) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsernameandPassword(uname,pswd);  
    }
	
	public LoginFormldap findByUsernameandPasswordldap(String tenant,String uname,String pswd) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsernameandPasswordldap(uname,pswd);  
    }
	
	public LoginFormldap findByUsernameldap(String tenant,String uname) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsernameldap(uname);  
    }
	
	public LoginForm findByUsername(String tenant,String uname) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsername(uname);  
    }
	
	public LoginForm findByUseridandPassword(String tenant,int userid,String pswd) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUseridandPassword(userid,pswd);  
    }
	
	public boolean isUserExist(String tenant,LoginForm user) {
		if(findByUsernameandPassword(tenant,user.getUsername(),user.getPassword()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistid(String tenant,LoginForm user) {
		if(findByUseridandPassword(tenant,user.getUserid(),user.getPassword()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistun(String tenant,LoginFormldap user) {
		if(findByUsername(tenant,user.getUsername()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistunldap(String tenant,LoginFormldap user) {
		if(findByUsernameldap(tenant,user.getUsername()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistunpwdldap(String tenant,LoginFormldap user) {
		if(findByUsernameandPasswordldap(tenant,user.getUsername(),user.getPassword()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public LoginForm findByUserid(String tenant,int userid) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUserid(userid);  
    }
	

	
	@SuppressWarnings("unused")
	public LoginResponse checkLogin(String tenantId, LoginRequest login, Map<String, Object> obj) {
		LoginResponse result = new LoginResponse();
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenantId);
		String postret = "";
		try {
			System.out.println("tenant id checkLogin : " + tenantId);
			
			JSONObject pdcomposer = new JSONObject();

			

			String chnlToken ="";
			String serverChannel ="";
			
			EngineCredential resultec = ecservice.getView(tenantId,this.ECID);
			if (resultec == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
			}
			
			pdcomposer.put("username", resultec.getChannelusername());
			pdcomposer.put("password", resultec.getChannelPassword());
			
			LoginForm userData = this.findByUsername(tenantId,login.getUsername());
			int failedlogAttempt =0;
			if(userData != null)
			{
				failedlogAttempt = userData.getFailedloginattempt();
			}
			
			//boolean isuserExists = this.isUserExist(tenantId,login);
			LoginForm lgf = appMapper.findByUsernameandPassword(login.getUsername(), login.getPassword());
			
			
			Map<String, Object> inputMap = new HashMap<>();
			
			//inputMap.put("FAILEDATTEMPT", userData.getFailedloginattempt());
			inputMap.put("FAILEDATTEMPT", failedlogAttempt);
			
			
			if (lgf != null) {
				
				inputMap.put("USERID", lgf.getUserid());
				inputMap.put("ROLEID", lgf.getRoleid());
				
				
				try {
					postret = hpr.setPostData(resultec.getChannelapi()+"/auth/login",pdcomposer);

					String jsonString = postret;
					JSONObject jsonObject;

					if(validatejson.isJSONValidstandard(jsonString))
					{
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

				System.out.println("channel post return is : "+ postret);

				System.out.println("Channel Token is : " +chnlToken);
				System.out.println("server channel is : " +serverChannel);
				

				ChangePasswordCycle cycle = new ChangePasswordCycle();
				cycle = cpservice.getViewlastpassword(tenantId, lgf.getUserid());
				
				ChangePasswordCycleLimit limit = new ChangePasswordCycleLimit();
				limit = cpclservice.getViewlimit(tenantId);
				
				// checkPassword cycle
				String errorMessage = checkPasswordExpired(cycle, limit);
				
				//getToken(int userid,String keyName,String username,long ttlMillis)
				String accesToken = jwtutils.createJWT(""+ lgf.getUserid(),"roocvthree",lgf.getUsername(),86400000);
				obj.putAll(inputMap);
				//obj.put("userId", lgf.getUserid());
				obj.put("accessToken", accesToken);
				obj.put("errorMessage", errorMessage);
				obj.put("channeltoken", chnlToken);

				// insert user source
				int insertSource = setSource(tenantId, obj);
				if (insertSource > 0) {
					logger.info("User source inserted : {}", obj);
				}
				
				
				
				
				result.setAccessToken(accesToken);
				result.setErrorMessage(errorMessage);
				
				//return result;
			}
			else
			{
				result.setAccessToken("");
				if(userData!=null)
				{
					Map<String, Object> objs = new HashMap<>();
					int failedAttempt = (Integer) inputMap.get("FAILEDATTEMPT") + 1;
					
					User updateuserattempt = new User();
					updateuserattempt.setUserid(userData.getUserid());
					updateuserattempt.setFailedloginattempt(failedAttempt);
					updateuserattempt.setUpdatedby(userData.getUserid());
					if(uservice.Updatefailedattempt(tenantId, updateuserattempt)>0)
					{
						
						result.setErrorMessage("Failed Attempt to login " + failedAttempt);
						
						logger.info("update user attempt : {}",inputMap);
						
						if (failedAttempt > 5) {
							
							User updateuser = new User();
							updateuser.setUserid(userData.getUserid());
							//updateuser.setFailedloginattempt(failedattempt);
							updateuser.setIsactive(0);
							uservice.Updateinactive(tenantId, updateuser);
							
							
							UserProfile userprof = new UserProfile();
							userprof.setIsactive(updateuser.getIsactive());
							userprof.setUserid(updateuser.getUserid());
							upservice.getUpdatefromuserinactive(tenantId, userprof);
						
							
							logger.info("update user active : {}",inputMap);
						}
					
					}
					else
					{
						result.setErrorMessage("Invalid data");
					}
				}
				else
				{
					result.setErrorMessage("Invalid username or password");
					//throw new CoreException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@SuppressWarnings("static-access")
	public UserProfileView changeResetpassword(String tenantId, int userprofileid) {
		
		UserProfileView userprofilereturn = new UserProfileView();

		try {
			ChangePassword obj = new ChangePassword();
			
			String genPassword ="";
			PasswordGenerator pg = new PasswordGenerator();
	        //for (int i = 0; i < 1; i++) {
			genPassword = pg.generateRandomPassword(10);
	        System.out.println("password : " + genPassword);
	        System.out.println("\n");
	        //}
	        @SuppressWarnings("unused")
			String newpasswordobj = genPassword;
	        obj.setNewpassword(genPassword);
	        obj.setRepassword(genPassword);
	        
	        UserProfileView userprofileget = upservice.getView(tenantId,userprofileid);
			if(userprofileget == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user profile");
			}
			
			int userid = userprofileget.getUserId();//trb.Parseuserid(token);
			obj.setUserid(userid);
	        
	        LoginForm resultbefore = this.findByUserid(tenantId,userid);
			obj.setOldpassword(resultbefore.getPassword());
	        
//			LoginForm objl = new LoginForm();
//			objl.setPassword(obj.getOldpassword());
//			objl.setUserid(userid);
			
			
			
			User us = new User();
			us.setUsername(resultbefore.getUsername());
			us.setPassword(obj.getNewpassword());
			us.setUserid(userid);
			//trb.SetTrailRecord(token,us);
			
			logger.info("update user password : {}",us);
			
			//int updatedid =0;
			String resUpdatepassword = uservice.getUpdatepassword(tenantId, us);
			if(Integer.parseInt(resUpdatepassword)>0)
			{
				logger.info("updated user password : {}",us);
				//System.out.println("Userid is :" + updatedid);
				userprofilereturn = userprofileget;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userprofilereturn;
		
	}
	
	private String checkPasswordExpired(ChangePasswordCycle cycle, ChangePasswordCycleLimit cpclimit) {
		String errorMessage = "";
		String format = "yyyy-MM-dd HH:mm:ss.SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		long nowMilliscp = System.currentTimeMillis();
		Timestamp nowcp = new Timestamp(nowMilliscp);

		if (cycle == null) {
			errorMessage = "Change Password";

		} else {
			try {
				Date dateObj1 = sdf.parse(cycle.getUpdatedtime().toString());
				Date dateObj2 = sdf.parse(nowcp.toString());

				long diff = dateObj2.getTime() - dateObj1.getTime();

				int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
				logger.info("difference between days : {}", diffDays);

				int cpclLimitexpire = 90;

				if (cpclimit == null) {
					cpclLimitexpire = 90;
				} else {
					if (cpclimit.getExpirein() <= 0) {
						cpclLimitexpire = 90;
					} else {
						cpclLimitexpire = cpclimit.getExpirein();
					}
				}
				if (diffDays >= cpclLimitexpire) {
					errorMessage = "Password Expired";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return errorMessage;
	}

	private String getBrowserInfo(String information) {
		String browsername = "";
		String browserversion = "";
		String browser = information;
		if (browser.contains("MSIE")) {
			String subsString = browser.substring(browser.indexOf("MSIE"));
			String[] info = (subsString.split(";")[0]).split(" ");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Firefox")) {
			String subsString = browser.substring(browser.indexOf("Firefox"));
			String[] info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Chrome")) {
			String subsString = browser.substring(browser.indexOf("Chrome"));
			String[] info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Opera")) {
			String subsString = browser.substring(browser.indexOf("Opera"));
			String[] info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Safari")) {
			String subsString = browser.substring(browser.indexOf("Safari"));
			String[] info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else {
			browsername = "Other/API/Postman";
			browserversion = "0.0";
		}
		return browsername + "-" + browserversion;
	}
	
	
	
	private int setSource(String tenantId, Map<String, Object> dataMap) {
		long nowMillis = System.currentTimeMillis();
		Timestamp now = new Timestamp(nowMillis);

		long expMillis = nowMillis + 86400000;
		Timestamp exp = new Timestamp(expMillis);

		
		UserSource usr = new UserSource();
		usr.setAuthkey((String) dataMap.get("accessToken"));
		usr.setDeviceid(getBrowserInfo((String) dataMap.get("browserInfo")));
		usr.setUserid((Integer) dataMap.get("USERID"));
		usr.setLogindate(now);
		usr.setExpiredate(exp);
		usr.setLoginfrom("roocvthree");
		usr.setLocaladdr((String) dataMap.get("localAddress"));
		usr.setRemoteaddr((String) dataMap.get("remoteAddress"));
		usr.setChanneltoken((String)dataMap.get("channeltoken"));
		
		String usersourcecreateres = usservice.getCreate(tenantId,usr);
		
		return Integer.parseInt(usersourcecreateres);
	}



}
