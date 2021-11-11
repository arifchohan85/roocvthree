package com.egeroo.roocvthree.interaction;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.core.util.Util;
import com.egeroo.roocvthree.directory.Directory;
import com.egeroo.roocvthree.directory.DirectoryService;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.Intent;
import com.egeroo.roocvthree.intent.IntentService;
import com.egeroo.roocvthree.roocconfig.RoocConfig;

import com.egeroo.roocvthree.roocconfig.RoocConfigService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.trainlog.TrainLog;
import com.egeroo.roocvthree.trainlog.TrainLogData;
import com.egeroo.roocvthree.trainlog.TrainLogService;
import com.google.gson.internal.LinkedHashTreeMap;





@RestController
@RequestMapping("/interaction")
public class InteractionController {
	
	@Autowired
    private InteractionService service;
	
	@Autowired
    private DirectoryService dirservice;
	
	@Autowired
    private IntentService intentservice;
	
	@Autowired
    private TrainLogService trainlogservice;
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	
	
	EngineCredentialService ecservice = new EngineCredentialService();
	
	private int ECID = 1;
	HttpPostReq hpr = new HttpPostReq();
	private String retDataPath;
	
	@Autowired
    private RoocConfigService roocconfigservice;
	
	ValidationJson validatejson = new ValidationJson(); 
	Util util= new Util();
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/index")
	public List<Interaction> getIndex(@RequestHeader HttpHeaders headers) {
		//List<Interaction> result = service.getIndex(headers.get("tenantID").get(0));
		List<Interaction> result = service.getIndexjoin(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/indextrain")
	public List<Interaction> getIndextrain(@RequestHeader HttpHeaders headers) {
		List<Interaction> result = service.getIndexjointrain(headers.get("tenantID").get(0));
		return result;
	}

	@RequestMapping(method=RequestMethod.GET,value="/train")
	public List<LinkedHashMap> getIndextrainv3(@RequestHeader HttpHeaders headers) {
		List<LinkedHashMap> result = service.getIndexjointrainv3(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/indexdate")
	public List<InteractionChathistory> getIndexdate(@RequestHeader HttpHeaders headers,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		//List<Interaction> result = service.getIndex(headers.get("tenantID").get(0));
		 Date today = new Date();
 		Date tomorrow = new Date();
		
		if (datefrom == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
			today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	    	tomorrow = DateUtils.addDays(today, 1);
		}
		
		if (dateto == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
			today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	    	tomorrow = DateUtils.addDays(today, 1);
		}
		else
		{
			today = datefrom;
			tomorrow = dateto;
		}
		
		List<InteractionChathistory> result = service.getIndexjoindate(headers.get("tenantID").get(0),today,tomorrow);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<LinkedHashMap> getIndexdatev3(@RequestHeader HttpHeaders headers,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		Date today = new Date();
 		Date tomorrow = new Date();
		
		if (datefrom == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
			today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	    	tomorrow = DateUtils.addDays(today, 1);
		}
		
		if (dateto == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
			today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	    	tomorrow = DateUtils.addDays(today, 1);
		}
		else
		{
			today = datefrom;
			tomorrow = dateto;
		}
		
		List<LinkedHashMap> result = service.getIndexjoindatev3(headers.get("tenantID").get(0),today,tomorrow);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/indextraindate")
	public List<InteractionIndex> getIndextraindate(@RequestHeader HttpHeaders headers,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		//List<Interaction> result = service.getIndex(headers.get("tenantID").get(0));
		 Date today = new Date();
 		Date tomorrow = new Date();
		
		if (datefrom == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
			today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	    	tomorrow = DateUtils.addDays(today, 1);
		}
		
		if (dateto == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
			today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
	    	tomorrow = DateUtils.addDays(today, 1);
		}
		else
		{
			today = datefrom;
			tomorrow = dateto;
		}
		
		List<InteractionIndex> result = service.getIndexjoinistraindate(headers.get("tenantID").get(0),today,tomorrow);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/countindex")
	public Interaction getCountindex(@RequestHeader HttpHeaders headers) {
		//List<Interaction> result = service.getIndex(headers.get("tenantID").get(0));
		Interaction result = service.getIndexjoincount(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/indexpaging")
	public List<Interaction> getIndexpaging(@RequestHeader HttpHeaders headers,Integer page) {
		//List<Interaction> result = service.getIndex(headers.get("tenantID").get(0));
		if (page == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected page in request url");
		}
		
		
		if(page<=0)
		{
			page =1;
		}
		
		List<Interaction> result = service.getIndexjoinpaging(headers.get("tenantID").get(0),page);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listinteraction")
	public List<Interaction> getInteraction(@RequestHeader HttpHeaders headers) {
		List<Interaction> result = service.getInteraction(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listinteractionintent")
	public List<Interaction> getInteractionintent(@RequestHeader HttpHeaders headers,int intentid) {
		List<Interaction> result = service.getInteractionintent(headers.get("tenantID").get(0),intentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/extractinteractionintent")
	public List<Interaction> extractInteractionintent(@RequestHeader HttpHeaders headers) {
		List<Interaction> result = service.extractInteractionintent(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listinteractionintentwithjoin")
	public List<Interaction> getInteractionintentwithjoin(@RequestHeader HttpHeaders headers) {
		List<Interaction> result = service.getInteractionintentwithjoin(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listinteractionexpectedintent")
	public List<Interaction> getInteractionexpectedintent(@RequestHeader HttpHeaders headers,int expectedintentid) {
		List<Interaction> result = service.getInteractionexpectedintentid(headers.get("tenantID").get(0),expectedintentid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/extractinteractionexpectedintent")
	public List<Interaction> extractInteractionexpectedintent(@RequestHeader HttpHeaders headers) {
		List<Interaction> result = service.extractInteractionexpectedintentid(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listinteractionexpectedintentwithjoin")
	public List<Interaction> getInteractionexpectedintentwithjoin(@RequestHeader HttpHeaders headers) {
		List<Interaction> result = service.getInteractionexpectedintentwithjoin(headers.get("tenantID").get(0));
		return result;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/view")
	public Interaction getView(@RequestHeader HttpHeaders headers,int interactionid) {
		Interaction result = service.getView(headers.get("tenantID").get(0),interactionid);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/traindb")
	public String getTraindb(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
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
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		if(result==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine credential problem found.");
		}
		
		String hprret = "";
		/*String engAccesstoken ="";
		
		
		try {
			hprret = hpr.ConnectGetToken(result.getMlapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonString = hprret;
        JSONObject jsonObject;
		
		jsonObject = new JSONObject(jsonString);
		if(jsonObject.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObject.getString("access_token");*/
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		try {
			hprret = hpr.ConnectGetTokenchannel(Engine+"/train/db","");
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		//service.getUpdateistrain(request.getHeader("tenantID"));
		
		return hprret;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/traindbdirectold")
	public String getTraindbdirectold(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		/*String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}*/
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		String engAccesstoken ="";
		String hprret = "";
		
		try {
			hprret = hpr.ConnectGetToken(result.getMlapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonString = hprret;
        JSONObject jsonObject;
		
		jsonObject = new JSONObject(jsonString);
		if(jsonObject.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObject.getString("access_token");
		
		try {
			hprret = hpr.ConnectGetTokenchannel(result.getMlapi()+"/train/db",engAccesstoken);
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		//service.getUpdateistrain(request.getHeader("tenantID"));
		
		jsonString = hprret;
		jsonObject = new JSONObject(jsonString);
		
		if(!jsonObject.has("STATUS"))
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
		}
		else if(jsonObject.getString("STATUS") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
		}
		else if(jsonObject.get("STATUS").equals(""))
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
		}
		
		String statusret = jsonObject.getString("STATUS");
		System.out.println(statusret);
		if(statusret.equals("SUBMIT"))
		{
			service.getUpdateistrain(request.getHeader("tenantID"));
		}
		
		return hprret;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/trainstatus")
	public String getTrainstatus(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		/*String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}*/
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		//check engine
        boolean isUseicontek = true;
        
		RoocConfig roocconfig = new RoocConfig();
		roocconfig = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"isUseiContek");
		if (roocconfig == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
			System.out.print("we will be using default engine (icontek),because no config found");
        }
		else
		{
			if(Integer.parseInt(roocconfig.getConfigvalue())<=0)
			{
				System.out.print("we will be using roocvthree engine");
				isUseicontek = false;
			}
		}
		
		
		String hprret = "";
		
		
		String statusret = "";
		String statusretsync = "";
		
		
			
		if(isUseicontek)
		{
			hprret = this.trainstatusIcontek(Engine, hprret, statusret, request, result, statusretsync);
		}
		else
		{
			hprret = this.trainstatusRoocengine(Engine, hprret, statusret, request, result, statusretsync);
		}
			
			
		
		return hprret;
	}
	
	

	@RequestMapping(method=RequestMethod.GET,value="/trainstatussyncrtsaml")
	public String getTrainstatusrtsaml(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		String hprret = "";
		
		
		String statusret = "";
		
		
		 //do {
			try {
				hprret = hpr.ConnectGetTokenchannel(Engine+"/api/async/status/syncEngine","");
				String jsonString = hprret;
				JSONObject jsonObject = new JSONObject(jsonString);
				
				if(!jsonObject.has("STATUS"))
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
				}
				if(jsonObject.getString("STATUS") ==null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
				}
				
				statusret = jsonObject.getString("STATUS");
				System.out.println(statusret);
				//return hprret;
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
		//}while(statusret.equals("RUNNING"));
		
			
			/*if(statusret.equals("IDLE"))
			{
				
				service.getUpdateistrain(request.getHeader("tenantID"));
			}
			*/
			
		
		return hprret;
	}
	
	

	@SuppressWarnings("null")
	@RequestMapping(method=RequestMethod.GET,value="/traindbrc")
	public String getTraindbrc(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		/*String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}*/
		
		//save log from
		TrainLog obj = new TrainLog();
		
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
		
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		if(result==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine credential problem found.");
		}
		
		System.out.print("Default api is :"+result.getApi());
		System.out.print("MLApi is :"+result.getMlapi());
		
		JSONObject postdatacheck = new JSONObject();
		postdatacheck.put("root", result.getRootcategory());
		
		Interaction resultcounttrain = service.getIndexcountjointrain(headers.get("tenantID").get(0));
		if(resultcounttrain==null)
		{
			resultcounttrain.setCountalldata(0);
		}
		
		obj.setTotaldata(resultcounttrain.getCountalldata());
		
		TrainLog resulttlsave = trainlogservice.getCreate(headers.get("tenantID").get(0),obj);
		//save log up to here
		
		List<Interaction> trainrecord = service.getIndexjointrain(headers.get("tenantID").get(0));
		//return result;
		List<Integer> tempstrainlogdataid = new ArrayList<Integer>();
		List<Integer> tempsinteractionid = new ArrayList<Integer>();
		List<Integer> tempsiretquestionid = new ArrayList<Integer>();
		
		
		
		if(trainrecord == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
			System.out.print("No Data Train to insert");
		}
		else
		{
			String tokendata = token;
			trainrecord.forEach(item->{
				TrainLogData objdata = new TrainLogData();
				objdata.setTrainlogid(resulttlsave.getTrainlogid());
				objdata.setInteractionid(item.getInteractionid());
				objdata.setIntentname(item.getIntentname());
				objdata.setAnswerintentname(item.getAnswerintentname());
				objdata.setConfidencelevelbefore(item.getConfidencelevel());
				objdata.setConfidencelevelafter(0);
				objdata.setStatus("TRAIN");
				trb.SetTrailRecord(tokendata,objdata);
				
				TrainLogData resulttlsavedetail = trainlogservice.getCreatedetail(headers.get("tenantID").get(0),objdata);
				
				//trainlogdataid
				if(resulttlsavedetail.getTrainlogdataid()>0)
				{
					tempstrainlogdataid.add(resulttlsavedetail.getTrainlogdataid());
					tempsinteractionid.add(resulttlsavedetail.getInteractionid());
					tempsiretquestionid.add(item.getIretrespondid());
					postdatacheck.append("ids", item.getIretrespondid());
				}
			});
			
		}
		
		
		
		//check engine
        boolean isUseicontek = true;
        boolean isUsechatbotwar = true;
        
		RoocConfig roocconfig = new RoocConfig();
		roocconfig = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"isUseiContek");
		if (roocconfig == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
			System.out.print("we will be using default engine (icontek),because no config found");
        }
		else
		{
			if(Integer.parseInt(roocconfig.getConfigvalue())<=0)
			{
				System.out.print("we will be using roocvthree engine");
				isUseicontek = false;
			}
		}
		
		//cehck chatbotwar
		roocconfig = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"isUseChatbotWAR");
		if (roocconfig == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
			System.out.print("we will be using chatbot war");
        }
		else
		{
			if(Integer.parseInt(roocconfig.getConfigvalue())<=0)
			{
				System.out.print("we will be using replic chatbot war");
				isUsechatbotwar = false;
			}
		}
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		String hprretcheckstatus = "";
		String hprret = "";
		String hprretvoice = "";
		String checkstatusret ="";
		String hprretpost = "";
		/*String engAccesstoken ="";
		
		
		try {
			hprret = hpr.ConnectGetToken(result.getMlapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		System.out.print(hprret);
		
		
        
		
		jsonObject = new JSONObject(jsonString);
		if(jsonObject.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObject.getString("access_token");
		*/
		
		if(isUseicontek)
		{
			hprret = this.traindbrcIcontek(hprretcheckstatus, Engine, checkstatusret, result
					, request, hprret, hprretvoice,tempstrainlogdataid);
		}
		else
		{
			hprret = this.traindbrcRoocengine(hprretcheckstatus, Engine, checkstatusret, result
					, request, hprretpost, hprretvoice,isUsechatbotwar,tempstrainlogdataid,headers,tempsinteractionid,tempsiretquestionid,postdatacheck);
		}
		
		System.out.println("return get result is : "+ hprret);
		
		System.out.println("===============================================");
		
		System.out.println("Training and getting confidence level after DONE");
		
		System.out.println("===============================================");
		
		System.out.println("Updating training DATA");
		
		service.getUpdateistrain(request.getHeader("tenantID"));
		
		System.out.println("===============================================");
		
		System.out.println("Updating training DATA,DONE");
		
		
			/*
			//delay for one second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		//}while(statusret.equals("BUSY"));
		
		return hprret;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/traindbsyncresponse")
	public String getTraindbsyncresponse(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		if(result==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine credential problem found.");
		}
		
		System.out.print("Default api is :"+result.getApi());
		System.out.print("MLApi is :"+result.getMlapi());
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		String hprret = "";
		String hprretvoice = "";
		
		
		String statusret = "";
		//jika sudah ada envi ml/rtsa baru bisa dicoba
		if(result.getIsusertsaml()==1)
		{
			//jika mlrtsa setelah selesai training lakukan copy/sync
			try {
				hprret = hpr.ConnectGetTokenchannel(Engine+"/api/schedule/ml/remoteSyncJob","");
				System.out.print("ML/RTSA SYNC Response is : " + hprret);
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
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
		}
		
		return hprret;
	}
	


	@RequestMapping(method=RequestMethod.GET,value="/traindbdirect")
	public String getTraindbdirect(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		/*String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}*/
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		String engAccesstoken ="";
		String hprret = "";
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		try {
			hprret = hpr.ConnectGetToken(Engine+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonString = hprret;
        JSONObject jsonObject;
		
		jsonObject = new JSONObject(jsonString);
		if(jsonObject.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObject.getString("access_token");
		
		String statusret = "";
		//do {
			try {
				//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				JSONObject postdata = new JSONObject();
				postdata.put("access_token", engAccesstoken);
				postdata.put("engineId", result.getRootcategory());
				//hprret = hpr.ConnectGetTokenchannel(result.getApi()+"/train/status",engAccesstoken);
				hprret = hpr.setPostData(Engine+"/train/db",postdata);
				jsonString = hprret;
				jsonObject = new JSONObject(jsonString);
				
				if(!jsonObject.has("STATUS"))
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
				}
				else if(jsonObject.getString("STATUS") ==null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
				}
				
				statusret = jsonObject.getString("STATUS");
				System.out.println(statusret);
				//return hprret;
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
		//}while(statusret.equals("BUSY"));
		
		return hprret;
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value="/trainstatusloop")
	public String getTrainstatusloop(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		/*String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}*/
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		String engAccesstoken ="";
		String hprret = "";
		
		try {
			hprret = hpr.ConnectGetToken(Engine+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonString = hprret;
        JSONObject jsonObject;
		
		jsonObject = new JSONObject(jsonString);
		if(jsonObject.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObject.getString("access_token");
		
		/*String statusret = "";
		do {
			try {
				hprret = hpr.ConnectGetTokenchannel(result.getMlapi()+"/train/status",engAccesstoken);
				jsonString = hprret;
				jsonObject = new JSONObject(jsonString);
				
				if(!jsonObject.has("STATUS"))
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
				}
				if(jsonObject.getString("STATUS") ==null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
				}
				
				statusret = jsonObject.getString("STATUS");
				System.out.println(statusret);
				//return hprret;
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
		}while(statusret.equals("RUNNING"));*/
		
		
			
		service.getUpdateistrain(request.getHeader("tenantID"));
		
		
		return hprret;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/traindbrcparam")
	public String getTraindbrcparam(@RequestHeader HttpHeaders headers,HttpServletRequest request,int parentid) {
		String getmap ="";
		
		/*String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = headers.get("Authorization").get(0);
		}
		else
		{
			token = headers.get("access_token").get(0);
		}*/
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(request.getHeader("tenantID"),this.ECID);
		
		String engAccesstoken ="";
		String hprret = "";
		
		String Engine = "";
		if(result.getIsusertsaml()==1)
		{
			boolean isEmptymlapi = result.getMlapi() == null || result.getMlapi().trim().length() == 0;
			if(isEmptymlapi)
			{
				Engine = result.getApi();
			}
			else
			{
				Engine = result.getMlapi();
			}
		}
		else
		{
			Engine = result.getApi();
		}
		
		try {
			hprret = hpr.ConnectGetToken(Engine+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonString = hprret;
        JSONObject jsonObject;
		
		jsonObject = new JSONObject(jsonString);
		if(jsonObject.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObject.getString("access_token");
    	
		this.retDataPath ="";
		getmap = this.mapView(headers.get("tenantID").get(0), parentid);
		
		String statusret = "";
		//do {
			try {
				//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				JSONObject postdata = new JSONObject();
				postdata.put("access_token", engAccesstoken);
				postdata.put("engineId", getmap);
				//hprret = hpr.ConnectGetTokenchannel(result.getApi()+"/train/status",engAccesstoken);
				hprret = hpr.setPostData(Engine+"/train/db",postdata);
				jsonString = hprret;
				jsonObject = new JSONObject(jsonString);
				
				if(!jsonObject.has("STATUS"))
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid engine data.");
				}
				if(jsonObject.getString("STATUS") ==null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid engine data.");
				}
				
				statusret = jsonObject.getString("STATUS");
				System.out.println(statusret);
				//return hprret;
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
		//}while(statusret.equals("RUNNING"));
		
		return hprret;
	}
	
	private String mapView(String tenantID,int InitcatID)
	{
		Directory dirresult = dirservice.getView(tenantID,InitcatID);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent data");
		}
		
		if(dirresult.getParentid() >0)
		{
			 //$this->createCatmapView($modelfindParentretID->ParentID);
			this.mapView(tenantID, dirresult.getParentid());
			this.retDataPath = this.retDataPath +"->"+ dirresult.getName();
		}
		else if(dirresult.getParentid() <=0)
		{
			//$this->retDataPath = $this->retDataPath .$modelfindParentretID->Name;
			this.retDataPath = this.retDataPath + dirresult.getName();
		}
		
		return this.retDataPath;
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public String getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/createonly")
	public String getCreateonly(HttpServletRequest request,@Valid @RequestBody Interaction obj) {
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = request.getHeader("Authorization");
		}
		else
		{
			token = request.getHeader("access_token");
		}
		trb.SetTrailRecord(token,obj);
		String retData = service.getCreateonly(request.getHeader("tenantID"),obj);
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/createinternal")
	public String getCreateinternal(HttpServletRequest request,@Valid @RequestBody Interaction obj) {
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = request.getHeader("Authorization");
		}
		else
		{
			token = request.getHeader("access_token");
		}
		trb.SetTrailRecord(token,obj);
		String retData = service.getCreateinternal(request.getHeader("tenantID"),obj);
		return retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/update")
	public String getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		String retData = service.getUpdate(headers.get("tenantID").get(0),obj);
		return  retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/updateonly")
	public String getUpdateonly(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		String retData = service.getUpdateonly(headers.get("tenantID").get(0),obj);
		return  retData;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/updateinternal")
	public String getUpdateinternal(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		String retData = service.getUpdateinternal(headers.get("tenantID").get(0),obj);
		return  retData;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/countuserchatperday")
	public Interaction getCountinteractionperday(@RequestHeader HttpHeaders headers) {
		//List<Interaction> result = service.getIndex(headers.get("tenantID").get(0));
		//Date today = new Date();
        //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        
        Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		Date tomorrow = DateUtils.addDays(today, 1);
		Interaction result = service.getfindCountinteractionperday(headers.get("tenantID").get(0),today,tomorrow,1);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/sentemailauth")
	public String getSendemailauth(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
		RoocConfig result = new RoocConfig();
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"username");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no username config found");
		}
		String usernamerc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"password");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no password config found");
		}
		String passwordrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"SMTPServer");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no SMTPServer config found");
		}
		String smtpserverrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"SMTPPort");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no SMTPPort config found");
		}
		String smtpportrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"SMTPAuth");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no SMTPAuth config found");
		}
		String smtpauthrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"EmailSender");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no EmailSender config found");
		}
		String emailsenderrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"EmailRecipient");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no EmailRecipient config found");
		}
		String emailrecipientrc = result.getConfigvalue();
		
		InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
		String returndata ="Success";
		final String username = usernamerc;
		final String password = passwordrc;
		System.out.print(username);
		System.out.println("Connecting...");
		Properties props = new Properties();
		props.put("mail.smtp.auth", smtpauthrc);
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpserverrc);//smtp.gmail.com
		props.put("mail.smtp.port", smtpportrc);
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
		
		try {
            fromAddress = new InternetAddress(emailsenderrc);
            //toAddress = new InternetAddress("arif.chohan@egeroo.ai");
        } catch (AddressException e) {
        	System.out.print("Invalid from address");
            e.printStackTrace();
        }
        
        try {
            //fromAddress = new InternetAddress("jane_chatbot@generali.co.id");
            toAddress = new InternetAddress(emailrecipientrc);
        } catch (AddressException e) {
        	System.out.print("Invalid to address");
            e.printStackTrace();
        }
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(fromAddress);
			//message.setRecipients(Message.RecipientType.TO,
			//		toAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler holla from generali,"
				+ "\n\n No spam to my email, please!"
				+ "Visit https://generali.co.id");
			Transport.send(message);
			System.out.println("Done");
			returndata ="Success";
		} catch (MessagingException e) {
			System.out.println("failed");
			returndata ="Failed";
			throw new RuntimeException(e);
		}
		return returndata;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/sentemailauthnoauth")
	public String getSendemailauthnoauth(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		String returndata ="Success";
		
		RoocConfig result = new RoocConfig();
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"username");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no username config found");
		}
		String usernamerc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"password");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no password config found");
		}
		String passwordrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"SMTPServer");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no SMTPServer config found");
		}
		String smtpserverrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"SMTPPort");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no SMTPPort config found");
		}
		String smtpportrc = result.getConfigvalue();
		
		
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"EmailSender");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no EmailSender config found");
		}
		String emailsenderrc = result.getConfigvalue();
		
		result = roocconfigservice.findByconfigkey(request.getHeader("tenantID"),"EmailRecipient");
		if(result ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no EmailRecipient config found");
		}
		String emailrecipientrc = result.getConfigvalue();
		
		Properties props = new Properties();
        props.put("mail.smtp.host", smtpserverrc);
        props.put("mail.smtp.port", smtpportrc);
        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
            fromAddress = new InternetAddress(emailsenderrc);
            //toAddress = new InternetAddress("arif.chohan@egeroo.ai");
        } catch (AddressException e) {
        	System.out.print("Invalid from address");
            e.printStackTrace();
        }
        
        try {
            //fromAddress = new InternetAddress("jane_chatbot@generali.co.id");
            toAddress = new InternetAddress(emailrecipientrc);
        } catch (AddressException e) {
        	System.out.print("Invalid to address");
            e.printStackTrace();
        }

        try {
        	System.out.print("Trying to send");
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(RecipientType.TO, toAddress);
            simpleMessage.setSubject("Testing no auth");
            simpleMessage.setText("Testing generali no auth");    
            Transport.send(simpleMessage);
            System.out.print("send....");
            returndata ="Success";
        } catch (MessagingException e) {
        	returndata ="Failed";
        	System.out.print("failed to send");
            e.printStackTrace();
        }
		return returndata;
	}
	
	private void syncmlrtsaDirectorydata(String headertenant)
	{
		
		
		//String headerTenant = headertenant;
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headertenant,this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		//List<Directory> dirresult = dirservice.getDirectoryvoicenotgenerated(headertenant);
		List<Directory> dirresult = dirservice.getDirectorynotgenml(headertenant);
		//return result;
		if(dirresult == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getMlapi();
		
		JSONObject postdata = new JSONObject();
		
		JSONObject faqstring = new JSONObject();
		Directory dirupdate =  new Directory();
		//int MaxIntent =0;
		String hprrettoken = "";
		String engAccesstoken ="";
		try {
			hprrettoken = hpr.ConnectGetToken(result.getMlapi()+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
		jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		engAccesstoken = jsonObjecttoken.getString("access_token");
        System.out.println("token is :" + jsonObjecttoken.getString("access_token"));
		
        //Util utilurl = new Util();
        String engtoken = engAccesstoken;
		dirresult.forEach(item->{
			JSONObject enumstring = new JSONObject();
			this.retDataPath ="";
			postdata.put("access_token", engtoken);
			String getmap = this.mapView(headertenant, item.getDirectoryid());
			
			//String engAccesstoken ="";
			System.out.print("Map :" +getmap);
			try {
				System.out.print("Encoded Map :" +URLEncoder.encode(getmap,"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String hprret = "";
			
			String postret = "";
			//getmap = getmap.replaceAll(" ","%20");
			//System.out.print("regex Map :" +getmap);
			String validUri = util.validateUri(voiceapi+"/api/category/get/"+getmap);
			System.out.print("validUri :" +validUri);
			try {
				hprret = hpr.ConnectGetnoparam(validUri);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        
	        dirupdate.setDirectoryid(item.getDirectoryid());
			dirupdate.setCategorymode(item.getCategorymode());
			
			System.out.print("Searching for current directoryid :" +item.getDirectoryid());
			Directory dirlocal = dirservice.getView(headertenant,item.getDirectoryid());
			
			int retparID =0;
			if(item.getParentid()>0)
			{
				Directory dirfindone = dirservice.getView(headertenant,item.getParentid());
				if(dirfindone == null)
				{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
				}
				
				retparID = dirfindone.getRetmlid();
			}
			else
			{
				retparID =0;
			}
			
			
			if(item.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = dirservice.getView(headertenant,item.getExtendenumcategoryid());
				if(direnum == null)
				{
					enumstring = null;
				}
				else
				{
					int retEnumID = direnum.getReticategoryid();
					String enumName = direnum.getName();
					String enumDescription = direnum.getDescription();
					String enumCatmode = direnum.getCategorymode();
					
					Directory direnumparent = null;
					direnumparent = dirservice.getView(headertenant,direnum.getParentid());
					int retEnumparID = direnumparent.getRetmlid();
					
					enumstring.put("id", retEnumID);
					enumstring.put("name", enumName);
					enumstring.put("parentid", retEnumparID);
					enumstring.put("description", enumDescription);
					enumstring.put("faq", item.getFaq());
					enumstring.put("mode", enumCatmode);
					enumstring.put("switching", item.getSwitching());
					enumstring.put("extendsEnumCategory", "null");
				}
				
			}
			
			//if(item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || item.getCategorymode().equals("DRILLDOWN") || item.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			//{
				enumstring = null;
			//}
			
			boolean isSBD = false;
			if(item.getCategorymode().equals("STANDARD") || item.getCategorymode().equals("ENUM") || item.getCategorymode().equals("MEMORY"))
			{
				
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", item.getFaq());
				postdata.put("mode", item.getCategorymode());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
			}
			else
			{
				isSBD = true;
				faqstring.put("id", item.getFaq());
				faqstring.put("question", item.getQuestion());
				//faqstring.put("answer", item.getAnswer());
				faqstring.put("answer", ".");
				
				//postdata.put("access_token", engAccesstoken);
				//postdata.put("id", directory.getReticategoryid());
				postdata.put("name", item.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", item.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", item.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				postdata.put("switching", item.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
				
			}
	        
			dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
			dirupdate.setRetmlid(dirlocal.getRetmlid());
			
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	
	        	jsonObject = new JSONObject(jsonString);
				if(jsonObject.getString("name") ==null)
				{
					//create baru ke dalam voice
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
					
					
					try {
						
						if(isSBD && retparID<=0)
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
							System.out.print("cannot update : "+item.getName()+" directory");
						}
						else
						{
							postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
							System.out.println("post return is : "+ postret);
							
							if(validatejson.isJSONValidstandard(postret))
					        {
								jsonObject = new JSONObject(postret);
								
								if(!jsonObject.getBoolean("STATUS"))
								{
									throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								}
								
								if(jsonObject.getInt("MESSAGE") <=0 )
								{
									throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								}
								
								
								
								
								if(jsonObject.getBoolean("STATUS"))
								{
									item.setRetmlid(jsonObject.getInt("MESSAGE"));
									
									//Directory dirupdate =  new Directory();
									dirupdate.setRetmlid(jsonObject.getInt("id"));
									dirupdate.setIsmlgenerated(1);
									//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
								}
								else
							    {
									if(dirlocal.getRetmlid()>0)
									{
										dirupdate.setRetmlid(dirlocal.getRetmlid());
										dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
									}
									else
									{
										dirupdate.setIsmlgenerated(0);
										dirupdate.setRetmlid(0);
									}
									//dirupdate.setRetmlid(0);
									//dirupdate.setIsmlgenerated(0);
							    }
					        }
							else
							{
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
						}
						
					} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else
				{
					/*Directory dirfindone = service.getView(headers.get("tenantID").get(0),item.getDirectoryid());
					if(dirfindone == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory to be update");
					}
					*/
					//update voice id in roocvthree datas
					//Directory dirupdate =  new Directory();
					
					dirupdate.setRetmlid(jsonObject.getInt("id"));
					dirupdate.setIsmlgenerated(1);
					
					postdata.put("id", dirupdate.getRetmlid());
					
					//check apakah ada perubahan data
					boolean isSame = false;
					if(item.getName().equals(jsonObject.getString("name")) && item.getSwitching().equals(jsonObject.getString("switching")) && item.getDescription().equals(jsonObject.getString("description")) && retparID==jsonObject.getInt("parentid"))
					{
						isSame = true;
					}
					
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
					else
					{
						if(!isSame)
						{
							try {
								postret = hpr.setPostData(voiceapi+"/api/category/update",postdata);
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
							System.out.println("post return is : "+ postret);
						}
						else
						{
							System.out.print("Directory : " + item.getName()+ "doesn't need any update,because all data is same");
						}
					}
				}
				//engAccesstoken = jsonObject.getString("access_token");
				System.out.print(jsonObject.getString("name") + " -" + jsonObject.getInt("id"));
	        }
	        else
	        {
	        	//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Invalid Category :" + item.getName());
	        	//create baru ke dalam ml
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no category found.");
				
				try {
					
					if(isSBD && retparID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+item.getName()+" directory");
						System.out.print("cannot update : "+item.getName()+" directory");
					}
					else
					{
						postret = hpr.setPostData(voiceapi+"/api/category/add",postdata);
						System.out.println("post return is : "+ postret);
						System.out.println("post data is : "+ postdata);
						
						
						if(validatejson.isJSONValidstandard(postret))
				        {
							jsonObject = new JSONObject(postret);
							
							if(!jsonObject.getBoolean("STATUS"))
							{
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							if (jsonObject.get("MESSAGE") instanceof String) {
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
							}
							
							
							if(jsonObject.getBoolean("STATUS"))
							{
								item.setRetmlid(jsonObject.getInt("MESSAGE"));
								
								dirupdate.setRetmlid(jsonObject.getInt("MESSAGE"));
								dirupdate.setIsmlgenerated(1);
								//service.getUpdatevoiceonly(headers.get("tenantID").get(0), dirupdate);
							}
							else
						    {
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
								
								if(dirlocal.getRetmlid()>0)
								{
									dirupdate.setRetmlid(dirlocal.getRetmlid());
									dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
								}
								else
								{
									dirupdate.setIsmlgenerated(0);
									dirupdate.setRetmlid(0);
								}
								//dirupdate.setRetmlid(0);
								//dirupdate.setIsmlgenerated(0);
						    }
				        }
						else
						{
							if(dirlocal.getRetmlid()>0)
							{
								dirupdate.setRetmlid(dirlocal.getRetmlid());
								dirupdate.setIsmlgenerated(dirlocal.getIsmlgenerated());
							}
							else
							{
								dirupdate.setIsmlgenerated(0);
								dirupdate.setRetmlid(0);
							}
							//dirupdate.setRetmlid(0);
							//dirupdate.setIsmlgenerated(0);
						}
					}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        
	        }
	        dirservice.getUpdatemlrtsaonly(headertenant, dirupdate);
	        
		});
	}
	
	private void syncmlrtsaIntentdata(String headertenant)
	{
		
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headertenant,this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		//List<Intent> intresult = intentservice.getIntentnotgeneratedvoice(headertenant);
		List<Intent> intresult = intentservice.getIntentnotgenml(headertenant);
		//return result; 
		if(intresult == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getMlapi();
		
		JSONObject postdata = new JSONObject();
		
		
		//int MaxIntent =0;
		intresult.forEach(item->{
			String lastinsertuserid = "0";
			
			//String engAccesstoken ="";
			System.out.print("Question/Intent :" +item.getQuestion());
			String hprret = "";
			String postret = "";
			
			try {
				hprret = hpr.ConnectGetnoparam(voiceapi+"/api/faq/id/"+item.getIntentid());
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        Intent intentupdate =  new Intent();
	        
	        Directory dirone = new Directory();
	    	dirone = dirservice.getView(headertenant, item.getDirectoryid());
	    	if(dirone ==  null)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	int retparID = dirone.getRetmlid();
	    	
	    	if (retparID<=0)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory ID for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	
	    	//postdata.put("access_token", engAccesstoken);
			postdata.put("id", item.getIntentid());
			//postdata.put("categoryid", retparID);
			postdata.put("question", item.getQuestion());
			//postdata.put("answer", item.getAnswer());
			postdata.put("answer", ".");
			postdata.put("category", retparID);
	        
	        if(validatejson.isJSONValidstandard(jsonString))
	        {
	        	//JSONArray jsonarray = new JSONArray(jsonString);
	        	//for (int i = 0; i < jsonarray.length(); i++) {
				    JSONObject jsonobject = new JSONObject(jsonString);//jsonarray.getJSONObject(i);
				    
				    if(!jsonobject.has("id"))
					{
				    	
						JSONArray ja = new JSONArray();
						ja.put(postdata);
						
						try {
							postret = hpr.setPostDataarray(voiceapi+"/api/faq/add",ja);
							System.out.println("post return is : "+ postret);
							
							//int cntErr =0;
							//String errMessage="";
							if(validatejson.isJSONValidarray(postret))
							{
								JSONArray jsonarrayadd = new JSONArray(postret);
								for (int j = 0; j < jsonarrayadd.length(); j++) {
								    JSONObject jsonobjectadd = jsonarrayadd.getJSONObject(j);
								    
								    if(!jsonobjectadd.has("faqId"))
									{
								    	//cntErr++;
								    	//errMessage = jsonobject.getString("STATUS");
								    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
								    	intentupdate.setRetmlid(0);
								    	intentupdate.setIsmlgenerated(0);
									}
								    else
								    {
								    	String faqId = jsonobjectadd.getString("faqId");
								    	intentupdate.setRetmlid(Integer.parseInt(faqId));
								    	intentupdate.setIsmlgenerated(1);
								    }
								}
							}
						} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
								| IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				    else if(jsonobject.has("id"))
				    {
				    	String faqId = Integer.toString(jsonobject.getInt("id"));
					    //String url = jsonobject.getString("url");
					    if(Integer.parseInt(faqId)>0)
					    {
					    	JSONObject pdcomposer = new JSONObject();
					        
					    	
					    	//intentupdate.setDirectoryid(item.getDirectoryid());
					    	intentupdate.setRetmlid(jsonobject.getInt("id"));
					    	intentupdate.setIsmlgenerated(1);
							
					    }
					    else
					    {
					    	throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
					    }
				    }
				//}
	        }
	        else
	        {
	        	JSONArray ja = new JSONArray();
				ja.put(postdata);
				
				try {
					postret = hpr.setPostDataarray(voiceapi+"/api/faq/add",ja);
					System.out.println("post return is : "+ postret);
					
					//int cntErr =0;
					//String errMessage="";
					if(validatejson.isJSONValidarray(postret))
					{
						JSONArray jsonarrayadd = new JSONArray(postret);
						for (int j = 0; j < jsonarrayadd.length(); j++) {
						    JSONObject jsonobjectadd = jsonarrayadd.getJSONObject(j);
						    
						    if(!jsonobjectadd.has("faqId"))
							{
						    	//cntErr++;
						    	//errMessage = jsonobject.getString("STATUS");
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
						    	intentupdate.setRetmlid(0);
						    	intentupdate.setIsmlgenerated(0);
							}
						    else
						    {
						    	String faqId = jsonobjectadd.getString("faqId");
						    	intentupdate.setRetmlid(Integer.parseInt(faqId));
						    	intentupdate.setIsmlgenerated(1);
						    }
						}
					}
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
						| IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        //save update only voice
		    intentupdate.setIntentid(item.getIntentid());
	    	lastinsertuserid = intentservice.getUpdatemlrtsaonly(headertenant, intentupdate);
	        
	        
		});
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/listpaging")
	public HashMap<String, Object> getListpaging(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody InteractionPagingPost obj) {
		String token ="";
    	boolean isEmpty = request.getHeader("access_token") == null || request.getHeader("access_token").trim().length() == 0;
		if(isEmpty)
		{
			token = request.getHeader("Authorization");
		}
		else
		{
			token = request.getHeader("access_token");
		}
		//trb.SetTrailRecord(token,obj);
		
		String whereData = "  ";
		
		String sortData = "  ";
		
		//Gson gson= new Gson();
		//String requiredJson= gson.toJson(obj.getWhere());
		
		System.out.print("Where is : " +obj.getWhere());
		
		JSONArray jsonarraywhere = new JSONArray(obj.getWhere());
		
		System.out.print("JSON Array where is : " +jsonarraywhere);
		
		int cnt =0;
		
		for (int i = 0; i < jsonarraywhere.length(); i++) {
		    JSONObject jsonobjectwhere = jsonarraywhere.getJSONObject(i);
		    
		    String whereclause="";
		    
		    
		    //if(jsonobjectwhere.getString("comparator").equals("between"))
		    //{
		    	//whereclause = util.parseWhereclause(jsonobjectwhere.getString("field"),jsonobjectwhere.getString("comparator"),jsonobjectwhere.getString("value"),jsonobjectwhere.getString("value2"));
		    //}
		    //else
		    //{
		    	//whereclause = util.parseWhereclause(jsonobjectwhere.getString("field"),jsonobjectwhere.getString("comparator"),jsonobjectwhere.getString("value"),"");
		    //}
		    
		    //set field dulu
		    String field = "";
		    /*if(jsonobjectwhere.getString("field").equals("intentid") || jsonobjectwhere.getString("field").equals("expectedintentid"))
		    {
		    	field = "a."+jsonobjectwhere.getString("field");
		    }
		    else*/ 
		    if(jsonobjectwhere.getString("field").equals("intentname") || jsonobjectwhere.getString("field").equals("expectedintentname"))
		    {
		    	field = "b.question";
		    }
		    else if(jsonobjectwhere.getString("field").equals("answerintentname"))
		    {
		    	field = "intict.question";
		    }
		    else if(jsonobjectwhere.getString("field").equals("customername"))
		    {
		    	field = "ch.name";
		    }
		    else
		    {
		    	field = "a."+jsonobjectwhere.getString("field");
		    }
		    
		    
		    if(jsonobjectwhere.has("value2"))
		    {
		    	boolean isEmptyvalue2 = jsonobjectwhere.getString("value2") == null || jsonobjectwhere.getString("value2").trim().length() == 0;
		    	
		    	if(isEmptyvalue2)
		    	{
		    		whereclause = util.parseWhereclause(field,jsonobjectwhere.getString("comparator"),jsonobjectwhere.getString("value"),"");
		    	}
		    	else
		    	{
		    		if(jsonobjectwhere.getString("comparator").equals("between"))
		    		{
		    			whereclause = util.parseWhereclause(field,jsonobjectwhere.getString("comparator"),jsonobjectwhere.getString("value"),jsonobjectwhere.getString("value2"));
		    		}
		    		else
		    		{
		    			whereclause = util.parseWhereclause(field,jsonobjectwhere.getString("comparator"),jsonobjectwhere.getString("value"),"");
		    		}
		    	}
		    }
		    else
		    {
		    	whereclause = util.parseWhereclause(field,jsonobjectwhere.getString("comparator"),jsonobjectwhere.getString("value"),"");
		    }
		    
		    
		    if(cnt<=0)
		    {
		    	//first line
		    	//column operator what = field comparator value e.g : name='jack'
		    	whereData += " WHERE " + whereclause ;
		    }
		    else
		    {
		    	//second and the rest using and
		    	// and column operator what = field comparator value e.g : name='jack'
		    	whereData += " AND " + whereclause ;
		    }
		    cnt++;
		}
		
		JSONArray jsonarraysort = new JSONArray(obj.getSort());
		
		System.out.print("JSON Array sort is : " +jsonarraysort);
		
		int cntsort =0;
		for (int i = 0; i < jsonarraysort.length(); i++) {
		    JSONObject jsonobjectsort = jsonarraysort.getJSONObject(i);
		    
		  //set field dulu
		    String fieldsort = "";
		    /*if(jsonobjectwhere.getString("field").equals("intentid") || jsonobjectwhere.getString("field").equals("expectedintentid"))
		    {
		    	field = "a."+jsonobjectwhere.getString("field");
		    }
		    else*/ 
		    if(jsonobjectsort.getString("field").equals("intentname") || jsonobjectsort.getString("field").equals("expectedintentname"))
		    {
		    	fieldsort = "b.question";
		    }
		    else if(jsonobjectsort.getString("field").equals("answerintentname"))
		    {
		    	fieldsort = "intict.question";
		    }
		    else if(jsonobjectsort.getString("field").equals("customername"))
		    {
		    	fieldsort = "ch.name";
		    }
		    else
		    {
		    	fieldsort = "a."+jsonobjectsort.getString("field");
		    }
		    
		    
		    if(cntsort<=0)
		    {
		    	//first line
		    	//column operator what = field comparator value e.g : name='jack'
		    	sortData += " ORDER BY " + fieldsort + " " + jsonobjectsort.getString("direction")+ " " ;
		    }
		    else
		    {
		    	//second and the rest using and
		    	// and column operator what = field comparator value e.g : name='jack'
		    	sortData += " , " + fieldsort + " " + jsonobjectsort.getString("direction")+ " " ;
		    }
		    cntsort++;
		}
		
		if(obj.getPage()<=0)
		{
			obj.setPage(1);
		}
		
		if(obj.getPagesize()<=0)
		{
			obj.setPagesize(20);
		}
		
		
		InteractionIndex resultcount = service.getIndexjoincount(headers.get("tenantID").get(0),whereData);
		
		
		List<InteractionIndex> result = service.getIndexlistjoinpaging(headers.get("tenantID").get(0),obj.getPage(),obj.getPagesize(),whereData,sortData,resultcount.getCountalldata());
		
		//JSONObject jo = new JSONObject();
		//jo.put("firstName", "John");
		//jo.put("lastName", "Doe");

		JSONArray ja = new JSONArray();
		ja.put(result);

		JSONObject mainObj = new JSONObject();
		mainObj.put("data", ja);
		mainObj.put("pagetotal", resultcount);
		
		HashMap<String, Object> map = new HashMap<>();
	    map.put("data", result);
	    map.put("pagetotal", resultcount.getCountalldata());
		
		return map;
		
		
		//return retData;
	}
	
	private void syncrtsaInteractiondata(String headertenant)
	{	
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headertenant,this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		//List<Intent> intresult = intentservice.getIntentnotgeneratedvoice(headertenant);
		List<Interaction> intresult = service.getIndex(headertenant);
		//return result; 
		if(intresult == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getApi();
		
		JSONObject postdatacheck = new JSONObject();
		JSONObject postdata = new JSONObject();
		
		String hprrettoken = "";
		try {
			hprrettoken = hpr.ConnectGetToken(voiceapi+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
        jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		String engAccesstoken = jsonObjecttoken.getString("access_token");
		
		
		//int MaxIntent =0;
		intresult.forEach(item->{
			String lastinsertuserid = "0";
			
			
			//String engAccesstoken ="";
			System.out.print("Question/Intent faqidstr :" +item.getFaqidstr());
			String hprret = "";
			String postret = "";
			
			postdatacheck.put("responseId", item.getFaqidstr());
			
			//postret = hpr.setPostData(result.getApi()+"/api/category/add",postdata);
			try {
				hprret = hpr.setPostData(voiceapi+"/api/response/search"+"",postdatacheck);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;
	        JSONObject jsonObject;
	        Interaction interactionupdate =  new Interaction();
	        
	        Interaction introne = new Interaction();
	        introne = service.getViewbyfaqidstr(headertenant, item.getFaqidstr());
	    	if(introne ==  null)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}
	    	
	    	String question = introne.getQuestion();
	    	int expectedfaqid = introne.getExpectedintentid();
	    	String faqidstr = introne.getFaqidstr();
	    	String questionid = introne.getQid();
	    	int faqid = introne.getIntentid();
	    	
	    	postdata.put("access_token", engAccesstoken);
			postdata.put("question", question);
			//postdata.put("categoryid", retparID);
			postdata.put("faqid", faqid);
			postdata.put("expectedFaqid", expectedfaqid);
			//postdata.put("answer", item.getAnswer());
			postdata.put("responseId", faqidstr);
			postdata.put("questionId", questionid);
	        
			
			//ini mnegecek apa apakah data nya sudah ada atau belum
	        if(validatejson.isJSONValidarray(jsonString))
	        {
	        	JSONArray jsonarray = new JSONArray(jsonString);
	        	for (int i = 0; i < jsonarray.length(); i++) {
				    //JSONObject jsonobject = new JSONObject(jsonString);//jsonarray.getJSONObject(i);
	        		JSONObject jsonobject = jsonarray.getJSONObject(i);
	        		
				    if(!jsonobject.has("id"))
					{
				    	//berarti data belum ada lalu kita insert
				    	
				    	JSONArray ja = new JSONArray();
						ja.put(postdata);
						
						try {
							postret = hpr.setPostDataarray(voiceapi+"/response/add",ja);
							System.out.println("post return is : "+ postret);
							
							//int cntErr =0;
							//String errMessage="";
							if(validatejson.isJSONValidarray(postret))
							{
								for (int x = 0; x < jsonarray.length(); x++) {
								    JSONObject jsonobjectres = jsonarray.getJSONObject(x);
								    int respId = jsonobjectres.getInt("id");
								    double cl = jsonobjectres.getDouble("confidence");
								    String qid = jsonobjectres.getString("questionId");
								    if(respId>0)
								    {
								    	interactionupdate.setFaqidstr(faqidstr);
								    	interactionupdate.setIretrespondid(respId);
								    	
								    	interactionupdate.setConfidencelevel(cl);
								    	System.out.println("qid from icontek is : " + qid);
								    	interactionupdate.setQid(qid);
								    	interactionupdate.setIsmanual(introne.getIsmanual());
								    	//lastinsertuserid =intMapper.Save(interactionupdate);
								    	//service.getUpdateengineidonly(headertenant, interactionupdate);
								    }
								}
								
								
							}
						} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
								| IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				    else if(jsonobject.has("id"))
				    {
				    	//berarti sudah ada,jadi tinggal update saja
				    	if(introne.getIretrespondid()>0)
			        	{
				    		if(introne.getIretrespondid() == jsonobject.getInt("id"))
				    		{
				    			postdata.put("id", introne.getIretrespondid());
				    		}
			        		
			        	}
				    	JSONArray ja = new JSONArray();
						ja.put(postdata);
						
						try {
							postret = hpr.setPostDataarray(voiceapi+"/response/update/expectedFaqid",ja);
						} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException
								| IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						System.out.println("post return is : "+ postret);
						
						jsonObject = new JSONObject(postret);
						
						//error message from icontek : ERROR
						String errMessage ="Something went wrong";
						if(jsonObject.has("ERROR"))
						{
							errMessage = jsonObject.getString("ERROR");
						}
						
						if(jsonObject.has("STATUS"))
						{
							if(jsonObject.getString("STATUS").equals("OK"))
							{
								interactionupdate.setFaqidstr(faqidstr);
						    	interactionupdate.setIretrespondid(jsonobject.getInt("id"));
						    	//service.getUpdateengineidonly(headertenant, interactionupdate);
							}
							else
							{
								throw new CoreException(HttpStatus.NOT_MODIFIED, errMessage);
							}
						}
						else
						{
							throw new CoreException(HttpStatus.NOT_MODIFIED, errMessage);
						}
				    }
				}
	        }
	        
	        
	        //save update only for engine id
	        service.getUpdateengineidonly(headertenant, interactionupdate);
	        
	        
		});
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/syncrtsaresponse")
	public String getSyncrtsaresponse(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
		this.syncrtsaInteractiondata(request.getHeader("tenantID"));
		
		return "COMPLETE";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/syncroocengineresponse")
	public String getSyncroocengineresponse(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		
		this.syncrtsaInteractiondataroocengine(request.getHeader("tenantID"));
		
		return "COMPLETE";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/updatedelete")
	public String getUpdatedelete(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		String retData = service.getUpdatedelete(headers.get("tenantID").get(0),obj);
		return  retData;
	}
	
	private String traindbrcIcontek(String hprretcheckstatus,String Engine,String checkstatusret
			,EngineCredential result,HttpServletRequest request,String hprret,String hprretvoice,List<Integer> tempstrainlogdataid)
	{
		try {
			hprretcheckstatus = hpr.ConnectGetTokenchannel(Engine+"/train/status","");
			String jsonStringstatus = hprretcheckstatus;
			JSONObject jsonObjectstatus = new JSONObject(jsonStringstatus);
			
			if(!jsonObjectstatus.has("STATUS"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
			}
			if(jsonObjectstatus.getString("STATUS") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
			}
			
			checkstatusret = jsonObjectstatus.getString("STATUS");
			System.out.println(checkstatusret);
			//return hprret;
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		System.out.println("Engine Status is : "+ checkstatusret);
		
		if(checkstatusret.equals("IDLE"))
		{
			//here sync ml rtsa data
			String httpgetsyncdata ="";
			if(result.getIsusertsaml()==1)
			{
				System.out.print("Do Synchronizing Directory Data");
				//sync directory firtst
				this.syncmlrtsaDirectorydata(request.getHeader("tenantID"));
				System.out.print("==================================");
				
				System.out.print("Do Synchronizing Intent Data");
				//sync intent data second
				this.syncmlrtsaIntentdata(request.getHeader("tenantID"));
				System.out.print("==================================");
				
				System.out.print("Synchronizing Response Data using engine api");
				//jika mlrtsa setelah selesai training lakukan copy/sync
				try {
					httpgetsyncdata = hpr.ConnectGetTokenchannel(Engine+"/api/schedule/ml/remoteSyncJob","");
					System.out.print("ML/RTSA SYNC Response is : " + hprret);
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
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
			}
			
			System.out.print("httpgetsyncdata is :" + httpgetsyncdata);
			
			String statusret = "";
			//do {
				try {
					System.out.print("Doing Training...");
					//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
					JSONObject postdata = new JSONObject();
					//postdata.put("access_token", engAccesstoken);
					postdata.put("engineId", result.getRootcategory());
					//hprret = hpr.ConnectGetTokenchannel(result.getApi()+"/train/status",engAccesstoken);
					hprret = hpr.setPostData(Engine+"/train/db",postdata);
					String jsonString = hprret;
					JSONObject jsonObject;
					jsonString = hprret;
					jsonObject = new JSONObject(jsonString);
					
					if(!jsonObject.has("STATUS"))
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
					}
					if(jsonObject.getString("STATUS") ==null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
					}
					
					statusret = jsonObject.getString("STATUS");
					
					
					System.out.println(statusret);
					
					if(result.getIsusevoice()>0)
					{
						hprretvoice = hpr.setPostData(result.getVoiceapi()+"/train/db",postdata);
						jsonString = hprretvoice;
						jsonObject = new JSONObject(jsonString);
					}
					
					//return hprret;
				}catch(Exception ex)
				{
					System.out.println(ex);
				}
				
				
				
		}
		else
		{
			hprret =" {\n" + 
					"\"MESSAGE\": null,\n" + 
					"\"NAME\": \"TrainingHelper\",\n" + 
					"\"RESULT\": \"DONE\",\n" + 
					"\"STATUS\": \"BUSY\"\n" + 
					"} ";
		}
		return hprret;
	}
	
	
	private String traindbrcRoocengine(String hprretcheckstatus,String Engine,String checkstatusret
			,EngineCredential result,HttpServletRequest request,String hprretpost,String hprretvoice,boolean isUsechatbotwar,List<Integer> tempstrainlogdataid,@RequestHeader HttpHeaders headers,List<Integer> tempsinteractionid
			,List<Integer> tempsiretquestionid,JSONObject postdatacheck)
	{
		
		checkstatusret ="IDLE";
		if(checkstatusret.equals("IDLE"))
		{
			//here sync ml rtsa data
			String httppostsyncdata ="";
			String httpposttraindata ="";
			
			
			//System.out.print("httpgetsyncdata is :" + httpgetsyncdata);
			
			boolean statusret =false;
			//do {
				try {
					System.out.print("Doing Training...");
					//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
					JSONObject postdata = new JSONObject();
					//postdata.put("access_token", engAccesstoken);
					//postdata.put("engineId", result.getRootcategory());
					postdata.put("root", result.getRootcategory());
					//hprret = hpr.ConnectGetTokenchannel(result.getApi()+"/train/status",engAccesstoken);
					
					if(isUsechatbotwar)
					{
						httpposttraindata = hpr.setPostData(Engine+"/train",postdata);
					}
					else
					{
						httpposttraindata = hpr.setPostData(Engine+"/retread",postdata);
					}
					
					String jsonString = httpposttraindata;
					JSONObject jsonObject;
					jsonString = httpposttraindata;
					jsonObject = new JSONObject(jsonString);
					
					if(!jsonObject.has("STATUS"))
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
					}
					if(!jsonObject.getBoolean("STATUS"))
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
					}
					
					statusret = jsonObject.getBoolean("STATUS");
					
					
					System.out.println("Status Return is :" +statusret);
					
					if(statusret)
					{
						Integer[] tempsArray = tempstrainlogdataid.toArray(new Integer[0]);
						Integer[] tempsArrayinteractionid = tempsinteractionid.toArray(new Integer[0]);
						Integer[] tempsArrayretrespondid = tempsiretquestionid.toArray(new Integer[0]);
						
						/*JSONObject postdatacheck = new JSONObject();
						postdatacheck.put("root", result.getRootcategory());
						for (Integer s : tempsArrayretrespondid) {
						      System.out.println("Data tempsArrayretrespondid is : " + s);
						      postdatacheck.append("ids", s);
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
						
						/*if(tempsArray.length<=10000)
						{
							httppostsyncdata = hpr.setPostData(Engine+"/reproject",postdatacheck);
							if(validatejson.isJSONValidarray(httppostsyncdata))
							{
								JSONArray jsonarray = new JSONArray(httppostsyncdata);
								
								for (int i = 0; i < jsonarray.length(); i++) {
									double confidencelevelafter =0;
									int idrespond =0;
								    JSONObject jsonobject = jsonarray.getJSONObject(i);
								    
								    if(jsonobject.has("id") && jsonobject.getInt("id")>0)
									{
								    	idrespond = jsonobject.getInt("id");
								    	if(jsonobject.has("confidence"))
										{
											confidencelevelafter = jsonobject.getDouble("confidence");
										}
								    	
								    	Interaction getInteractionID = service.getViewbyrespondid(request.getHeader("tenantID"), idrespond);
										if(getInteractionID == null)
										{
											System.out.println("Data respond id  : " + idrespond + "cannot be found");
										}
										else
										{
											TrainLogData resultfinddata = trainlogservice.getViewlogdatabyinteractionid(request.getHeader("tenantID"),getInteractionID.getInteractionid());
											if(resultfinddata == null)
											{
												System.out.println("Data train log with interaction id  : " + getInteractionID.getInteractionid() + "cannot be found");
											}
											else
											{
												TrainLogData obj = new TrainLogData();
												trb.SetTrailRecord(token,obj);
												
												obj.setConfidencelevelafter(confidencelevelafter);
												obj.setInteractionid(getInteractionID.getInteractionid());
												obj.setTrainlogdataid(resultfinddata.getTrainlogdataid());
												obj.setStatus("REPROJECT");
												
												TrainLogData resultupdatedata = trainlogservice.getUpdateconfidenceafter(request.getHeader("tenantID"),obj);
											}
										}
								    	
									}
								    else
								    {
								    	System.out.println("Json Onject from engine has no column for id ");
								    }
								}
							}
							else
						    {
						    	System.out.println("Invalid Json reply from engine ");
						    }
						}
						else
						{*/
							for (Integer s : tempsArrayretrespondid) {
								
								System.out.print("================ TRANING =============");
								System.out.print("DDDDDDDDDDDDDDDD" + s + "DDDDDDDDDDDDD");
								
								JSONObject postdatacheckonebyone = new JSONObject();
								postdatacheckonebyone.put("root", result.getRootcategory());
								postdatacheckonebyone.append("ids", s);
								
								httppostsyncdata = hpr.setPostData(Engine+"/reproject",postdatacheckonebyone);
								if(validatejson.isJSONValidarray(httppostsyncdata))
								{
									JSONArray jsonarray = new JSONArray(httppostsyncdata);
									
									for (int i = 0; i < jsonarray.length(); i++) {
										double confidencelevelafter =0;
										int idrespond =0;
									    JSONObject jsonobject = jsonarray.getJSONObject(i);
									    
									    if(jsonobject.has("id") && jsonobject.getInt("id")>0)
										{
									    	idrespond = jsonobject.getInt("id");
									    	if(jsonobject.has("confidence"))
											{
												confidencelevelafter = jsonobject.getDouble("confidence");
											}
									    	
									    	Interaction getInteractionID = service.getViewbyrespondid(request.getHeader("tenantID"), idrespond);
											if(getInteractionID == null)
											{
												System.out.println("Data respond id  : " + idrespond + "cannot be found");
											}
											else
											{
												TrainLogData resultfinddata = trainlogservice.getViewlogdatabyinteractionid(request.getHeader("tenantID"),getInteractionID.getInteractionid());
												if(resultfinddata == null)
												{
													System.out.println("Data train log with interaction id  : " + getInteractionID.getInteractionid() + "cannot be found");
												}
												else
												{
													TrainLogData obj = new TrainLogData();
													trb.SetTrailRecord(token,obj);
													
													obj.setConfidencelevelafter(confidencelevelafter);
													obj.setInteractionid(getInteractionID.getInteractionid());
													obj.setTrainlogdataid(resultfinddata.getTrainlogdataid());
													obj.setStatus("REPROJECT");
													
													TrainLogData resultupdatedata = trainlogservice.getUpdateconfidenceafter(request.getHeader("tenantID"),obj);
												}
											}
									    	
										}
									    else
									    {
									    	System.out.println("Json Onject from engine has no column for id ");
									    }
									}
								}
								else
							    {
							    	System.out.println("Invalid Json reply from engine ");
							    }
								
								System.out.print("================ DONE TRANING =============");
								System.out.print("DDDDDDDDDDDDDDDD" + s + "DDDDDDDDDDDDD");
						    }
						//}
						
						
						hprretpost =" {\n" +
								"\"MESSAGE\": null,\n" +
								"\"NAME\": \"TrainingHelper\",\n" +
								"\"RESULT\": \"DONE\",\n" +
								"\"STATUS\": \"SUBMIT\"\n" +
								"} ";
						System.out.println("=================== Finish Training Here ===============");
						System.out.println(hprretpost);
						
						//return hprretpost;
					}
					else
					{
						hprretpost =" {\n" + 
								"\"MESSAGE\": null,\n" + 
								"\"NAME\": \"TrainingHelper\",\n" + 
								"\"RESULT\": \"DONE\",\n" + 
								"\"STATUS\": \"BUSY\"\n" + 
								"} ";
						System.out.println("System BUSY ........");
						System.out.println(hprretpost);
						//return hprretpost;
					}
					
					
				}catch(Exception ex)
				{
					System.out.println(ex);
				}
				
				System.out.println("2nd return out :" + hprretpost);
				
				return hprretpost;
		}
		else
		{
			hprretpost =" {\n" + 
					"\"MESSAGE\": null,\n" + 
					"\"NAME\": \"TrainingHelper\",\n" + 
					"\"RESULT\": \"DONE\",\n" + 
					"\"STATUS\": \"BUSY\"\n" + 
					"} ";
			System.out.print("System BUSY ........");
			System.out.print(hprretpost);
			return hprretpost;
		}
		
		
		
		//return hprretpost;
	}
	
	private String trainstatusIcontek(String Engine,String hprret,String statusret,HttpServletRequest request
			,EngineCredential result,String statusretsync)
	{
		try {
			hprret = hpr.ConnectGetTokenchannel(Engine+"/train/status","");
			String jsonString = hprret;
			JSONObject jsonObject = new JSONObject(jsonString);
			
			if(!jsonObject.has("STATUS"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
			}
			if(jsonObject.getString("STATUS") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
			}
			
			statusret = jsonObject.getString("STATUS");
			System.out.println(statusret);
			//return hprret;
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		if(statusret.equals("IDLE"))
		{
			
			//service.getUpdateistrain(request.getHeader("tenantID"));
			
			//jika sudah ada envi ml/rtsa baru bisa dicoba
			if(result.getIsusertsaml()==1)
			{
				String syncgetrequest ="";
				//jika mlrtsa setelah selesai training lakukan copy/sync
				try {
					syncgetrequest = hpr.ConnectGetTokenchannel(Engine+"/api/engine/sync","");
					System.out.print("ML/RTSA SYNC Response is : " + syncgetrequest);
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
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
				
				do {
					
					try {
						String getenginesyncStatus = hpr.ConnectGetTokenchannel(Engine+"/api/async/status/syncEngine","");
						String jsonStringsync = getenginesyncStatus;
						JSONObject jsonObjectsync = new JSONObject(jsonStringsync);
						
						if(!jsonObjectsync.has("STATUS"))
						{
							throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
						}
						if(jsonObjectsync.getString("STATUS") ==null)
						{
							throw new CoreException(HttpStatus.EXPECTATION_FAILED, "something went wrong.");
						}
						
						statusretsync = jsonObjectsync.getString("MESSAGE");
						System.out.println(statusretsync);
						//return hprret;
					}catch(Exception ex)
					{
						System.out.println(ex);
					}
				
				}while(statusretsync.equals("RUNNING"));
				
				
			}
		}
		return hprret;
	}
	
	private String trainstatusRoocengine(String Engine,String hprret,String statusret,HttpServletRequest request
			,EngineCredential result,String statusretsync)
	{
		
		hprret =" {\n" +
				"\"MESSAGE\": null,\n" + 
				"\"NAME\": \"TrainingHelper\",\n" + 
				"\"RESULT\": \"DONE\",\n" + 
				"\"STATUS\": \"IDLE\"\n" + 
				"} ";
		
		/*String message;
		JSONObject json = new JSONObject();
		json.put("name", "student");*/
		String jsonString = hprret;
		JSONObject jsonObject;
		System.out.print("hprret: " + hprret);
		jsonString = hprret;
		System.out.print("jsonString: " + jsonString);
		jsonObject = new JSONObject(jsonString);
		
		if(jsonObject.has("STATUS"))
		{
			statusret = jsonObject.getString("STATUS");
		}
		
		if(statusret.equals("IDLE"))
		{
			service.getUpdateistrain(request.getHeader("tenantID"));
		}
		return hprret;
	}
	

	private void syncrtsaInteractiondataroocengine(String headertenant)
	{	
		EngineCredential result = new EngineCredential();
		result = ecservice.getView(headertenant,this.ECID);
		if(result == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine not valid");
		}
		
		//List<Intent> intresult = intentservice.getIntentnotgeneratedvoice(headertenant);
		//List<Interaction> intresult = service.getIndex(headertenant);
		//return result; 
		List<Interaction> intresult = service.extractInteractionexpectedintentid(headertenant);
		if(intresult == null)
		{
			//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data to sync");
		}
		
		String syncResult="COMPLETE";
		String voiceapi = result.getApi();
		String rootCategory = result.getRootcategory();
		
		JSONObject postdatacheck = new JSONObject();
		JSONObject postdata = new JSONObject();
		
		String hprrettoken = "";
		/*try {
			hprrettoken = hpr.ConnectGetToken(voiceapi+"/oauth/token",result.getUsername(),result.getPassword());
		}catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		String jsonStringtoken = hprrettoken;
        JSONObject jsonObjecttoken;
		
        jsonObjecttoken = new JSONObject(jsonStringtoken);
		if(jsonObjecttoken.getString("access_token") ==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
		}
		String engAccesstoken = jsonObjecttoken.getString("access_token");*/
		String engAccesstoken = "";
		
		//int MaxIntent =0;
		intresult.forEach(item->{
			String lastinsertuserid = "0";
			
			
			//String engAccesstoken ="";
			System.out.print("Question/Intent faqidstr :" +item.getFaqidstr());
			String hprret = "";
			String postret = "";
			
			postdatacheck.put("responseId", item.getFaqidstr());
			
			//postret = hpr.setPostData(result.getApi()+"/api/category/add",postdata);
			/*try {
				hprret = hpr.setPostData(voiceapi+"/api/response/search"+"",postdatacheck);
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			System.out.print("get return :" +hprret);
			String jsonString = hprret;*/
	        JSONObject jsonObject;
	        Interaction interactionupdate =  new Interaction();
	        Intent intentexp = new Intent();
	        Directory dir = new Directory();
	        Interaction introne = new Interaction();
	        introne = item;
	        /*introne = service.getViewbyfaqidstr(headertenant, item.getFaqidstr());
	    	if(introne ==  null)
	    	{
	    		//throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Invalid Directory for intent : "+item.getQuestion()+" with id : "+item.getIntentid());
	    	}*/
	    	
	    	/*boolean isEmptyexpintentname = introne.getExpectedintentname() == null || introne.getExpectedintentname().trim().length() == 0;
			
			if(isEmptyexpintentname)
			{
				//expected intentname null or not pass,use id
				intentexp = intentservice.getView(headertenant,introne.getExpectedintentid());
			}
			else
			{
				intentexp = intentservice.getViewquestion(headertenant,introne.getExpectedintentname());
			}
	    	
	    	dir = dirservice.getView(headertenant,intentexp.getDirectoryid());
			
			if(dir == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
			}
			
			System.out.println("expected intent directory parentid is : " + dir.getParentid());
	    	*/
	    	postdata.put("Authorization", engAccesstoken);
	        //postdata.put("questionId", result.getRootcategory());
	        //postdata.put("map", getMapview);
	        postdata.put("responseId", introne.getFaqidstr());
			postdata.put("question", introne.getQuestion());
			postdata.put("faqid", introne.getIntentid());
			postdata.put("expectedFaqId", introne.getExpectedintentid());
			
			if(introne.getExpectedintentid()<=0)
			{
				postdata.put("root", rootCategory);
			}
	    	
	    	String question = introne.getQuestion();
	    	int expectedfaqid = introne.getExpectedintentid();
	    	String faqidstr = introne.getFaqidstr();
	    	String questionid = introne.getQid();
	    	int faqid = introne.getIntentid();
	    	
	    	
	    	/*postdata.put("access_token", engAccesstoken);
			postdata.put("question", question);
			//postdata.put("categoryid", retparID);
			postdata.put("faqid", faqid);
			postdata.put("expectedFaqid", expectedfaqid);
			//postdata.put("answer", item.getAnswer());
			postdata.put("responseId", faqidstr);
			postdata.put("questionId", questionid);*/
	        
	    	interactionupdate = introne;
			
	    	JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			if(!introne.getQuestion().trim().equals(""))
			{
			
				try {
					postret = hpr.setPostData(voiceapi+"/question",postdata);
				} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				System.out.println("post return is : "+ postret);
				 Object jsonobjectret = postret;//json.get("URL"); 
				
				 boolean isAllowsave =false;
				 JSONArray jsonarray = new JSONArray(postret);
					for (int i = 0; i < jsonarray.length(); i++) {
					    JSONObject jsonobject = jsonarray.getJSONObject(i);
					    if(jsonobject.has("id"))
					    {
					    	int respId = jsonobject.getInt("id");
						    //double cl = jsonobject.getDouble("confidence");
						    //String qid = jsonobject.getString("questionId");
						    if(respId>0)
						    {
						    	//interactionupdate.setIretrespondid(respId);
						    	introne.setIretrespondid(respId);
						    	//interaction.setConfidencelevel(cl);
						    	//System.out.println("qid from icontek is : " + qid);
						    	//interaction.setQid(qid);
						    	//interactionupdate.setIsmanual(1);
						    	
						    }
						    isAllowsave =true;
					    }
					    else
					    {
					    	introne.setIretrespondid(0);
					    }
					    
					}
		        
					//service.Updatesyncroocengineid(headertenant,interactionupdate);
					service.Updatesyncroocengineid(headertenant,introne);
			}
			else
			{
				System.out.print("Can't update for Question with id : "+introne.getFaqidstr()+" ,the cause  is null or empty ");
			}
	        //save update only for engine id
	       // service.getUpdateengineidonly(headertenant, interactionupdate);
	        
	        
		});
	}
	
	
}
