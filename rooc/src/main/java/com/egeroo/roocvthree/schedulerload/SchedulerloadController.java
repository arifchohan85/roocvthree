package com.egeroo.roocvthree.schedulerload;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;

@RestController
@RequestMapping("/schedulerload")
public class SchedulerloadController {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired
    private SchedulerloadService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Schedulerload> getIndex(@RequestHeader HttpHeaders headers) {
		List<Schedulerload> result = service.getIndex(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Schedulerload getView(@RequestHeader HttpHeaders headers,Integer schedulerid) {
		if (schedulerid == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		
		if(schedulerid <=0)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid id");
		}
		Schedulerload result = service.getView(headers.get("tenantID").get(0),schedulerid);
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,value="/insert")
	public Schedulerload getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Schedulerload obj) {
		//UserRole result = 
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
		
		ObjectMapper objm = new ObjectMapper();
		
		String jsonStrdata="";
		String jsonStrheader="";
		
		try {
			jsonStrheader = objm.writeValueAsString(obj.getDataheaderreq());
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Json Header is : " + jsonStrheader); 
		obj.setDataheader(jsonStrheader);
		
		
		if(obj.getMethod().equals("POST"))
		{
			try {
				jsonStrdata = objm.writeValueAsString(obj.getDataparameterreq());
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  
	        // Displaying JSON String 
	        System.out.println("Json Data is : " + jsonStrdata); 
	        obj.setDataparameter(jsonStrdata);
		}
		
		//LocalDateTime datetimeWithoutZone = LocalDateTime.parse(obj.getDatetime().toString(),
          //      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		//Timestamp orig = (Timestamp) obj.getDatetime();
	      //Timestamp ts = new Timestamp( orig.getTime() );
	      //ts.setNanos( orig.getNanos() );
	      //System.out.println("Timestamp new : "+ts);
		
		SimpleDateFormat dt= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dt.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateStringInUTC = dt.format(new Date(obj.getDatetime().getTime()));
		System.out.println("String Converted date : "+dateStringInUTC);  
		
		Date date1 = new Date();
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStringInUTC);
			System.out.println("New Date Data is : "+date1);  
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		obj.setDatetime(date1);
		System.out.println("Timestamp datetime: "+obj.getDatetime());  
		System.out.println("Timestamp datetime String: "+obj.getDatetime().toString());
		
		
		if(obj.getEnddatetime() != null)
		{
			String enddateStringInUTC = dt.format(new Date(obj.getEnddatetime().getTime()));
			System.out.println("String getEnddatetime Converted date : "+enddateStringInUTC);  
			
			Date date2 = new Date();
			try {
				date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(enddateStringInUTC);
				System.out.println("New End Date Data is : "+date2);  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			obj.setEnddatetime(date2);
			System.out.println("Timestamp getEnddatetime: "+obj.getEnddatetime());  
			System.out.println("Timestamp getEnddatetime String: "+obj.getEnddatetime().toString());
		}
		
		
		
		//System.out.print("LocalDateTime : " + obj.getDatetime());
		//Schedulerload result =null;
		Schedulerload result = service.getCreate(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public Schedulerload getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Schedulerload obj) {
		//UserRole result = 
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
		
		Schedulerload result = service.getUpdate(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/updatestatus")
	public Schedulerstatusload getUpdatestatus(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestBody Schedulerstatusload obj) {
		//UserRole result = 
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
		
		Schedulerstatusload result = service.getUpdatestatus(headers.get("tenantID").get(0),obj);
		return result;
		
	}
	
	

}
