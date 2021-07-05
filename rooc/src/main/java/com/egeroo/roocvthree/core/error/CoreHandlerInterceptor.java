package com.egeroo.roocvthree.core.error;





import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import com.egeroo.roocvthree.core.jwt.JwtUtil;
import com.egeroo.roocvthree.core.usersource.UserSource;
import com.egeroo.roocvthree.core.usersource.UserSourceMapper;
import com.egeroo.roocvthree.core.usersource.UserSourceMapperImpl;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.user.User;
import com.egeroo.roocvthree.user.UserMapper;
import com.egeroo.roocvthree.user.UserMapperImpl;


import io.jsonwebtoken.Claims;



@Component
public class CoreHandlerInterceptor implements HandlerInterceptor {
	//private TenantMapper tenantMapper;
	private UserSourceMapper usersourceMapper;
	private UserMapper userMapper;
	JwtUtil jwtutils = new JwtUtil();
	private static final Logger log = Logger.getLogger(CoreHandlerInterceptor.class);	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		String header = request.getHeader("tenantID");
		String access_token = request.getHeader("Authorization");
		//String token ="";
		
		if(request.getHeader("access_token")==null)
		{
			access_token = request.getHeader("Authorization");
		}
		else
		{
			access_token = request.getHeader("access_token");
		}

		
		String uri = request.getRequestURI();
		System.out.println(uri);
		boolean isFound = false;//uri.contains("login/dologin");
		if(uri.contains("login/dologin"))
		{
			isFound = true;
		}
		else if(uri.contains("roocvthree/error"))
		{
			isFound = true;
		}
		/*else if(uri.contains("login/dologinldapwithpwd"))
		{
			isFound = true;
		}
		else if(uri.contains("login/dologinldapwithtoken"))
		{
			isFound = true;
		}
		else if(uri.contains("login/dologinresetpassword"))
		{
			isFound = true;
		}*/
		
		/*
		log.info("lheader :" +header);
		log.info("ltoken :" +access_token);
		System.out.println("header :" +header);
		System.out.println("access_token :" +access_token);
		*/
		
        /*if (header == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "header tenant-identifier not found.");
        }*/
        
        //jika yang di post bukan login,kita check access_token
        /*if(!isFound)
        {
        	
	        	if(access_token == null)
	        	{
	        		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "access token not found.");
	        	}
	        	
	        	boolean isEmpty = access_token == null || access_token.trim().length() == 0;
				//if(!Strings.isNullOrEmpty(String str)) {
	        	if (isEmpty) {
				   // Do your stuff here 
	        		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "access token not found.");
				}
        	
        	
        	
        }*/
        
    	try {
	    		
    		
	    		if(!isFound)
	    		{
	    			/* SqlSessionFactory session = BaseDAO.getInstance(header);
		    		if(session == null) {
		    			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "tenant "+header+" not found.");
		    		} */
	    			
	    			/*if(access_token == null)
	            	{
	            		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "access token not found.");
	            	}*/
	            	
	            	boolean isEmpty = access_token == null || access_token.trim().length() == 0;
	    			//if(!Strings.isNullOrEmpty(String str)) {
	            	if (isEmpty) {
	    			   // Do your stuff here 
	            		throw new CoreException(HttpStatus.EXPECTATION_FAILED, "access token not found.");
	    			}
		    		
	    			try {
	            		usersourceMapper = new UserSourceMapperImpl(header);
	            		UserSource usersource = usersourceMapper.findByAuthkey(access_token);
	            		if(usersource == null)
	            		{
	            			System.out.println("User get auth is incorrect ");
	            			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid access token.");
	            		}
	            		else
	            		{
	            			//check lagi apakah sama dengan jwt nya
	            			Claims parsejwt = jwtutils.parseJWT(access_token);
	            			int clmid = Integer.parseInt(parsejwt.getId());
	            			System.out.println("ClmID : " + clmid);
	            			System.out.println("UserID : " + usersource.getUserid());
	            			
	            			if(clmid != usersource.getUserid())
	            			{
	            				System.out.println("User get id is incorrect ");
	            				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid access token.");
	            			}
	            			
	            			userMapper = new UserMapperImpl(header);
	            			User lgf = userMapper.findByUsername(parsejwt.getSubject());
	            			if(lgf == null)
	            			{
	            				System.out.println("username is incorrect ");
	            				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid access token.");
	            			}
	            			
	            			SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            			Timestamp ts=usersource.getExpiredate();
							//Date date=new Date(ts.getTime());
							Date date1 = new Date(ts.getTime());
							Date date2 = new Date(System.currentTimeMillis());//(Date) myFormat.parse(inputString2);
							System.out.println("Expire date is : " + myFormat.format(date1));
							System.out.println("Current date is : " + myFormat.format(date2));
							long diff = date1.getTime() - date2.getTime();
							
							long diffSeconds = diff / 1000 % 60;
							long diffMinutes = diff / (60 * 1000) % 60;
							long diffHours = diff / (60 * 60 * 1000) % 24;
							//long diffHoursru = (diff / (60 * 60 * 1000) % 24)+1;
							long diffDays = diff / (24 * 60 * 60 * 1000);

							System.out.print(diffDays + " days, ");
							System.out.print(diffHours + " hours, ");
							System.out.print(diffMinutes + " minutes, ");
							System.out.print(diffSeconds + " seconds.");
							//System.out.print(diffHoursru + " hours round up. ");
							
							System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	            			
							if(diffHours<=0 && diffMinutes<=0)
							{
								//if(diffMinutes<=0)
								//{
									throw new CoreException(HttpStatus.UNAUTHORIZED, "Token Expire");
								//}
								
							}
	            			
	            		}
	            		
	            	}catch(Exception e) {
	            		throw new CoreException(HttpStatus.UNAUTHORIZED, "" + e.getMessage());
	            	}
	    		}
    		
    	} catch (Exception e) {
    		throw new CoreException(HttpStatus.UNAUTHORIZED, e.getMessage());
    	}
    	

    	/*BufferedInputStream in = new BufferedInputStream(request.getInputStream());
		byte[] contents = new byte[1024];
		
		int bytesRead = 0;
		String strFileContents = ""; 
		while((bytesRead = in.read(contents)) != -1) { 
		    strFileContents += new String(contents, 0, bytesRead);              
		}
		
		
		
		//System.out.print(strFileContents);
    	String resultbody = convertInputStreamToString(request.getInputStream());
		System.out.println("request body :" + resultbody);
		System.out.println("request local address :" + request.getLocalAddr());
		System.out.println("request ip address :" + request.getRemoteAddr());
		System.out.println("request query string :" + request.getQueryString());
		*/

    	//ServletInputStream bodydata = request.getInputStream();
    	if(!isFound)
    	{
    		trb.TrailAll(header,access_token,request);
    	}
    	
		
    	
    	/*if(!isFound)
        {
    		RoleApiRouteService service = new RoleApiRouteService();
    		TrailingRecordBase trb = new TrailingRecordBase();
    		
    		int userid = trb.Parseuserid(access_token);
    		UserProfileMapperImpl userprofileMapper = new UserProfileMapperImpl(header);
    		UserProfile up = userprofileMapper.findByuserid(userid);
    		
    		RoleApiRoute rar = new RoleApiRoute();
    		RoleApiRouteMapperImpl roleapiroute = new RoleApiRouteMapperImpl(header);
    		rar = roleapiroute.findByroleandroute(uri, up.getRoleid());
    		
    		if(rar == null)
    		{
    			throw new CoreException(HttpStatus.FORBIDDEN, "Unauthorized");
    		}
        }
    	*/
    	/*log.info("Paramaters Name" + java.util.Arrays.asList(request.getParameterNames()));
    	log.info("[preHandle][" + request + "]" + "[" + request.getMethod()
        + "]" + request.getRequestURI() + getParameters(request));
    	*/
        return true;
    }
	

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	
    	//log.info("[postHandle][" + request + "]");
    		
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
    	
    	/*if (ex != null){
            ex.printStackTrace();
        }
        log.info("[afterCompletion][" + request + "][exception: " + ex + "]");*/
    	
    }
    
    private String getParameters(HttpServletRequest request) {
        StringBuffer posted = new StringBuffer();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        //System.out.println("E : "+ e);
        while (e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr + "=");
            if (curr.contains("password") 
              || curr.contains("pass")
              || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }
        
        /*String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (ipAddr!=null && !ipAddr.equals("")) {
            posted.append("&_psip=" + ipAddr); 
        }*/
        
        return posted.toString();
    }
    
    
}
