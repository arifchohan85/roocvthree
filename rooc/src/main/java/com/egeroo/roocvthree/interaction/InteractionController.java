package com.egeroo.roocvthree.interaction;


import java.io.IOException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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

import com.egeroo.roocvthree.roocconfig.RoocConfig;

import com.egeroo.roocvthree.roocconfig.RoocConfigService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.trainlog.TrainLog;
import com.egeroo.roocvthree.trainlog.TrainLogData;
import com.egeroo.roocvthree.trainlog.TrainLogService;






@RestController
@RequestMapping("/interaction")
public class InteractionController {
	
	@Autowired
    private InteractionService service;
	
	@Autowired
    private DirectoryService dirservice;
	
	
	
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
		
		
		
		
		String hprret = "";
		
		
		String statusret = "";
		String statusretsync = "";
		
		
			
		
			hprret = this.trainstatusRoocengine(Engine, hprret, statusret, request, result, statusretsync);
		
			
			
		
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
		
		
			hprret = this.traindbrcRoocengine(hprretcheckstatus, Engine, checkstatusret, result
					, request, hprretpost, hprretvoice,isUsechatbotwar,tempstrainlogdataid,headers,tempsinteractionid,tempsiretquestionid,postdatacheck);
		
		
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
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/createv2")
	public Integer getCreatev2(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		Integer retData = service.getCreatev2(headers.get("tenantID").get(0),obj);
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
	public Integer getUpdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		Integer retData = service.getUpdatev2(headers.get("tenantID").get(0),obj);
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
	
	/* v3 starts here */
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public Integer getCreate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@Valid @RequestBody Interaction obj) {
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
		
		//trb.SetTrailRecord(token,obj);
		
		Integer retData = service.getCreatev2(headers.get("tenantID").get(0),obj);
		return retData;
	}
	/* v3 till here */
	
}
