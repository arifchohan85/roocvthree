package com.egeroo.roocvthree.trailingrecord;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;



import com.egeroo.roocvthree.core.base.Base;
import com.egeroo.roocvthree.core.jwt.JwtUtil;

import io.jsonwebtoken.Claims;

public class TrailingRecordBase {
	
	JwtUtil jwtutils = new JwtUtil();
	Base base = new Base();
	public void SaveTrail(String tenant,String access_token,int recordid,String classname,String actionfor)
	{
		Claims parsejwt = jwtutils.parseJWT(access_token);
		int clmid = Integer.parseInt(parsejwt.getId());
		
		long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Timestamp ts =  new Timestamp(nowMillis);
		
		TrailingRecord tr = new TrailingRecord();
		tr.setCreatedby(String.valueOf(clmid));
		tr.setRecordid(recordid);
		tr.setClassname(classname);
		tr.setActionfor(actionfor);
		tr.setCreateddate(ts);
		TrailingRecordMapper trMapper =  new TrailingRecordMapperImpl(tenant);
		trMapper.Save(tr);
	}
	
	@SuppressWarnings("resource")
	public void TrailAll(String tenant,String access_token,HttpServletRequest request)
	{
		//BufferedInputStream in;
		int clmid =0;
		boolean isEmpty = access_token == null || access_token.trim().length() == 0;
		if(!isEmpty)
		{
			Claims parsejwt = jwtutils.parseJWT(access_token);
			clmid = Integer.parseInt(parsejwt.getId());
		}
		
		
		
		long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Timestamp ts =  new Timestamp(nowMillis);
		try {
			
			/*in = new BufferedInputStream(request.getInputStream());
		
			byte[] contents = new byte[1024];
		
			int bytesRead = 0;
			String strFileContents = ""; 
			while((bytesRead = in.read(contents)) != -1) { 
			    strFileContents += new String(contents, 0, bytesRead);              
			}*/
			
			//System.out.print(strFileContents);
			
	    	//String resultbody = request.getInputStream().toString();
			//Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A");
			//String result = s.hasNext() ? s.next() : "";
			//String requestBody= IOUtils.toString(request.getInputStream());
			//String resultbody = convertInputStreamToString(request.getInputStream());
			System.out.println("request body :" + request.getInputStream());
			System.out.println("request local address :" + request.getLocalAddr());
			System.out.println("request ip address :" + request.getRemoteAddr());
			System.out.println("request query string :" + request.getQueryString());
			
			TrailingRecord tr = new TrailingRecord();
			tr.setCreatedby(String.valueOf(clmid));
			tr.setRecordid(0);
			tr.setClassname(request.getRequestURI());
			tr.setActionfor(request.getMethod());
			//tr.setRequestbody(request.getInputStream().toString());
			tr.setRequestbody("");
			tr.setRequestquerystring(request.getQueryString());
			tr.setCreateddate(ts);
			TrailingRecordMapper trMapper =  new TrailingRecordMapperImpl(tenant);
			trMapper.Save(tr);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Pure Java
    @SuppressWarnings("unused")
	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString(StandardCharsets.UTF_8.name());

    }
	
	public Base SetTrailRecord(String access_token,Base obj)
	{
		try 
		{
			Claims parsejwt = jwtutils.parseJWT(access_token);
			int clmid = Integer.parseInt(parsejwt.getId());
			
			long nowMillis = System.currentTimeMillis();
	        Timestamp now = new Timestamp(nowMillis);//(Timestamp) new Date(nowMillis);
			
	        obj.setCreatedby(clmid);
	        obj.setUpdatedby(clmid);
	        obj.setCreatedtime(now);
	        obj.setUpdatedtime(now);
		}catch (Exception ex)
		{
			System.out.println("Exception is : " + ex.toString());
		}
		return base;
	}
	
	public int Parseuserid(String access_token)
	{
		int userid=0;
		try 
		{
			Claims parsejwt = jwtutils.parseJWT(access_token);
			System.out.println("Parse User id is :"+Integer.parseInt(parsejwt.getId()));
			int clmid = Integer.parseInt(parsejwt.getId());
			userid = clmid;
		}catch (Exception ex)
		{
			System.out.println("Exception is : " + ex.toString());
		}
		return userid;
	}
}
