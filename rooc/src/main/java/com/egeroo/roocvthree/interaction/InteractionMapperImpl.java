package com.egeroo.roocvthree.interaction;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.core.util.SendEmail;
import com.egeroo.roocvthree.directory.Directory;
import com.egeroo.roocvthree.directory.DirectoryMapper;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.Intent;
import com.egeroo.roocvthree.intent.IntentMapper;
import com.egeroo.roocvthree.menulist.MenulistMapper;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigMapper;
import com.egeroo.roocvthree.roocconfig.RoocConfigService;
import com.egeroo.roocvthree.trainlogactivity.TrainLogActivity;
import com.egeroo.roocvthree.trainlogactivity.TrainLogActivityService;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileMapper;
import com.egeroo.roocvthree.userprofile.UserProfileService;




public class InteractionMapperImpl  extends BaseDAO implements InteractionMapper{
	
	private static final Logger log = Logger.getLogger(InteractionMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	private int ECID = 1;
	private String retDataPath;
	
	private int retquestionid;
    private String postret;
    private String lastinsertuserid;
	
	InteractionService intservice = new InteractionService();
	UserProfileService usrpservice = new UserProfileService();
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService();
    
    RoocConfigService rcservice = new RoocConfigService();
    
    ValidationJson validatejson = new ValidationJson();
    
    TrainLogActivityService trainlogactivityservice = new TrainLogActivityService();
	
	public InteractionMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Interaction> findAll() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAll();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	
	@Override
	public List<Interaction> findInteraction() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findInteraction();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Interaction findOne(Integer interactionid) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findOne(interactionid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}

	@Override
	public List<Interaction> findInteractionintent(Integer intentid) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findInteractionintent(intentid);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findInteractionexpectedintentid(expectedintentid);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Interaction findMaxinteractionid() {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findMaxinteractionid();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}


	public String Savev2(Interaction interaction) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Integer lastinsertuserid=0;
		String postret = "";
		Intent intent = new Intent();
		Intent intentexp = new Intent();
		Directory dir = new Directory();
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			//userrole = 
			
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			DirectoryMapper dirMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			
			
			boolean isEmpty = interaction.getIntentname() == null || interaction.getIntentname().trim().length() == 0;
			
			if(isEmpty)
			{
				//intentname tidak di passing..query berdasarkan intentid..
				intent = ecMapper.findOne(interaction.getIntentid());
			}
			else
			{
				intent = ecMapper.findIntentquestion(interaction.getIntentname());
			}
			
			
			
			int getintentid = -1;
			int getreturnintentid =-1;
			String getintentquestion ="";
			
			int getexpintentid = -1;
			int getexpreturnintentid =-1;
			String getexpintentquestion ="";
			
			if(intent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent name or id.");
			}
			else
			{
				getintentid = intent.getIntentid();
				getreturnintentid = intent.getIretquestionid();
				getintentquestion = intent.getQuestion();
			}
			
			
			
			interaction.setIntentid(getintentid);
			
			
			//check engine
	        boolean isUseicontek = true;
	        RoocConfigMapper rcMapper = sqlSession.getMapper(RoocConfigMapper.class);
			RoocConfig roocconfig = new RoocConfig();
			roocconfig = rcMapper.findByconfigkey("isUseiContek");
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
			
			//RoocConfigMapper rcMapper = sqlSession.getMapper(RoocConfigMapper.class);
			//RoocConfig roocconfig = new RoocConfig();
			roocconfig = rcMapper.findByconfigkey("isEmailingchat");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
				System.out.print("System not using email");
	        }
			else
			{
				if(Integer.parseInt(roocconfig.getConfigvalue())>0)
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			        Date datefobj = new Date();
			        System.out.println("Date from : " +df.format(datefobj));
			        //Date datefrom=new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(df.format(datefobj));  
			        
			        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			        Date datetobj = new Date();
			        System.out.println("Date to : "+dt.format(datetobj));
			        //Date dateto=new SimpleDateFormat("yyyy-MM-dd 23:59:59").parse(dt.format(datetobj));
			        
			        DateFormat cd = new SimpleDateFormat("yyyy-MM-dd");
			        Date datecobj = new Date();
			        System.out.println("Chat Date : " +cd.format(datecobj));
			        
			        //boolean isEmpty = interaction.getCustomerchannelid() == null || interaction.getCustomerchannelid().trim().length() == 0;
					
			        if(interaction.getCustomerchannelid()>0)
			        {
			        	//Date today = new Date();
			            //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
			            
			            Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
			    		Date tomorrow = DateUtils.addDays(today, 1);
			            
			        	Interaction interactiongetcountchat = intMapper.findCountinteractionperday(today,tomorrow,interaction.getCustomerchannelid());
			        	if(interactiongetcountchat.getCount()<=0)
			        	{
			        		//from user with customerchannelid,findphone number
			        		Interaction useranyid = intMapper.findAnyidbycustomerchannelid(interaction.getCustomerchannelid());
			        		
			        		if(useranyid == null)
			        		{
			        			System.out.print("User Any ID : "+interaction.getCustomerchannelid() +" : Not Found");
			        		}
			        		else
			        		{
			        			String phonenumber =useranyid.getAnyid();
			        			phonenumber = phonenumber.replace("whatsapp2", "");
			        			phonenumber = phonenumber.replace("whatsapp", "");
			        			phonenumber = phonenumber.replace("roocvthree", "");
			        			phonenumber = phonenumber.replace(":", "");
			        			
			        			//String subject = "Chatbot : "+interaction.getCustomerchannelid() + cd.format(datecobj);
			        			String subject = "Chatbot : "+phonenumber + " "+ cd.format(datecobj);
			        			
				        		//send email
				        		SendEmail sendEmail = new SendEmail();
				        		sendEmail.sendMailauth(roocconfig,rcMapper,subject, interaction.getQuestion());
			        		}
			        		
			    			
			        	}
			        
			        }
				}
			}
			
			
			boolean isEmptyexpintentname = interaction.getExpectedintentname() == null || interaction.getExpectedintentname().trim().length() == 0;
			
			if(isEmptyexpintentname)
			{
				//expected intentname null or not pass,use id
				intentexp = ecMapper.findOne(interaction.getExpectedintentid());
			}
			else
			{
				intentexp = ecMapper.findIntentquestion(interaction.getExpectedintentname());
			}
			
			
			if(intentexp == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid expected intent name or id.");
				/*interaction.setExpectedintentid(getintentid);
				
				getexpintentid = getintentid;
				getexpreturnintentid = getreturnintentid;
				getexpintentquestion = getintentquestion;*/
			}
			else
			{
				interaction.setExpectedintentid(intentexp.getIntentid());
				
				getexpintentid = intentexp.getIntentid();
				getexpreturnintentid = intentexp.getIretquestionid();
				getexpintentquestion = intentexp.getQuestion();
			}
			
			String getMapview ="";
			System.out.println("expected intent id is : " + intentexp.getIntentid());
			System.out.println("expected intent directoryid is : " + intentexp.getDirectoryid());
			dir = dirMapper.findOne(intentexp.getDirectoryid());
			
			if(dir == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
			}
			
			System.out.println("expected intent directory parentid is : " + dir.getParentid());
			
			/*
			if(dir.getCategorymode().equals("STANDARD") || dir.getCategorymode().equals("ENUM") || dir.getCategorymode().equals("MEMORY"))
			{	
				getMapview = this.mapView(intentexp.getDirectoryid(),dirMapper);
			}
			else
			{
				getMapview = this.mapView(dir.getParentid(),dirMapper);//ini masih menggunakan ini sampai tanggal 11 oct 2019
				//getMapview = this.mapView(dir.getDirectoryid(),dirMapper);
			}
			System.out.println("QID is : " + getMapview);
			getMapview = getMapview.replaceAll("null", "");
			getMapview = getMapview.replaceAll(" null", "");
			System.out.println("QID after fix is : " + getMapview);
			interaction.setQid(getMapview);
			*/
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			if(dir.getParentid()>0)
			{
				
				Directory dirparent = dirMapper.findOne(dir.getParentid());
				
				//if(dirparent == null)
				//{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
				//}
				
				if(dirparent.getCategorymode().equals("STANDARD"))
				{
					if(dir.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || dir.getCategorymode().equals("DRILLDOWN"))
					{
						getMapview = result.getRootcategory();
						interaction.setQid(result.getRootcategory());
					}
					else
					{
						getMapview = this.mapView(dir.getParentid(),dirMapper);
						System.out.println("QID is : " + getMapview);
						getMapview = getMapview.replaceAll("null", "");
						getMapview = getMapview.replaceAll(" null", "");
						System.out.println("QID after fix is : " + getMapview);
						
						interaction.setQid(getMapview);
					}
					
				}
				else
				{
					getMapview = this.mapView(dir.getParentid(),dirMapper);
					System.out.println("QID is : " + getMapview);
					getMapview = getMapview.replaceAll("null", "");
					getMapview = getMapview.replaceAll(" null", "");
					System.out.println("QID after fix is : " + getMapview);
					
					interaction.setQid(getMapview);
				}
				
				/*getMapview = this.mapView(dir.getParentid(),dirMapper);
				System.out.println("QID is : " + getMapview);
				getMapview = getMapview.replaceAll("null", "");
				getMapview = getMapview.replaceAll(" null", "");
				System.out.println("QID after fix is : " + getMapview);
				
				interaction.setQid(getMapview);*/
	        	
			}
			else
			{
				//getMapview = dir.getDirectoryname();
				//interaction.setQid(dir.getDirectoryname());
				//getMapview = dir.getName();
				//interaction.setQid(dir.getName());
				getMapview = result.getRootcategory();
				interaction.setQid(result.getRootcategory());
			}
			
			
			
			
			
			
			
			int MaxIntent =0;
			if(interaction.getFaqidstr() == null || "".equals(interaction.getFaqidstr()))
			{
				
		        MaxIntent =result.getInitialincrement();
				Interaction resultintentmax = new Interaction();
				resultintentmax = intservice.getMaxinteraction(this.tenantIdentifier);
				
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}
				
				if(resultintentmax.getMaxinteractionid() <=0)
				{
					MaxIntent = MaxIntent + 1;
				}
				else
				{
					MaxIntent = resultintentmax.getMaxinteractionid() +1;
				}
				
				//interaction.setFaqidstr("RSPD" + String.valueOf(MaxIntent));
				long nowMillis = System.currentTimeMillis();
		        //Date now = new Date(nowMillis);
				interaction.setFaqidstr("RSPD" + String.valueOf(nowMillis));
			}
			
			
			
			String serverUrl =result.getApi();
			if(result.getIsusevoice()>0)
			{
				boolean isEmptymessagetype = interaction.getMessagetype() == null || interaction.getMessagetype().trim().length() == 0;
				if(isEmptymessagetype)
				{
					interaction.setMessagetype("text");
				}
				
				if(interaction.getMessagetype().equals("audio"))
				{
					serverUrl =result.getVoiceapi();
				}
				else
				{
					serverUrl =result.getApi();
				}
			}
			else
			{
				serverUrl =result.getApi();
			}
			
			String engAccesstoken ="";
			String hprret = "";
			
			/*try {
				hprret = hpr.ConnectGetToken(serverUrl+"/oauth/token",result.getUsername(),result.getPassword());
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
	        System.out.println("token is :" + jsonObject.getString("access_token"));*/
			
			
	        
	        JSONObject postdata = new JSONObject();
	        
	        
	        lastinsertuserid = this.saveRoocenginecreate(postdata, engAccesstoken, getMapview, interaction
	        			, getreturnintentid, getexpreturnintentid, postret, serverUrl, lastinsertuserid
	        			, intMapper);
	        
	        
			
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return postret;
	}
	
	
	
	
	/*public static boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}*/

	@Override
	public Integer Update(Interaction interaction) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Integer lastinsertuserid=0;
		Intent intent = new Intent();
		String postret = "";
		Directory dir = new Directory();
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			DirectoryMapper dirMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			
			Interaction interactiongetfaqidstr = intMapper.findOne(interaction.getInteractionid());
			
			if(interactiongetfaqidstr == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid interaction id.");
			}
			
			
			
			
			int getexpreturnintentid =-1;
			int getexpintentid =-1;
			if(interaction.getExpectedintentid()>0)
			{
				intent = ecMapper.findOne(interaction.getExpectedintentid());
				if(intent == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent id.");
				}
				getexpreturnintentid = intent.getIretquestionid();
				getexpintentid = intent.getIntentid();
			}
			else
			{
				boolean isEmpty = interaction.getExpectedintentname() == null || interaction.getExpectedintentname().trim().length() == 0;
				if(!isEmpty)
				{
					//intentname tidak di passing..query berdasarkan intentid..
					//intent = ecMapper.findOne(interaction.getIntentid());
					intent = ecMapper.findIntentquestion(interaction.getExpectedintentname());
					if(intent == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent id.");
					}
					getexpreturnintentid = intent.getIretquestionid();
					getexpintentid = intent.getIntentid();
				}
				
			}	
			interaction.setExpectedintentid(getexpintentid);
			
			
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			
			
			String engAccesstoken ="";
			String hprret = "";
			
			
			
			String getMapview ="";
        	this.retDataPath ="";
			if(getexpreturnintentid>0)
	        {
				
				System.out.println("expected intent id is : " + intent.getIntentid());
				System.out.println("expected intent directoryid is : " + intent.getDirectoryid());
				dir = dirMapper.findOne(intent.getDirectoryid());
				
				if(dir == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
				}
				
				System.out.println("expected intent directory parentid is : " + dir.getParentid());
				
				
				
				//getMapview = this.mapView(intent.getDirectoryid(),dirMapper);
				if(dir.getParentid()>0)
				{
					Directory dirparent = dirMapper.findOne(dir.getParentid());
					
					//if(dirparent == null)
					//{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
					//}
					
					if(dirparent.getCategorymode().equals("STANDARD"))
					{
						if(dir.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || dir.getCategorymode().equals("DRILLDOWN"))
						{
							getMapview = result.getRootcategory();
							interaction.setQid(result.getRootcategory());
						}
						else
						{
							getMapview = this.mapView(dir.getParentid(),dirMapper);
							System.out.println("QID is : " + getMapview);
							getMapview = getMapview.replaceAll("null", "");
							getMapview = getMapview.replaceAll(" null", "");
							System.out.println("QID after fix is : " + getMapview);
							
							interaction.setQid(getMapview);
						}
						
					}
					else
					{
						getMapview = this.mapView(dir.getParentid(),dirMapper);
						System.out.println("QID is : " + getMapview);
						getMapview = getMapview.replaceAll("null", "");
						getMapview = getMapview.replaceAll(" null", "");
						System.out.println("QID after fix is : " + getMapview);
						
						interaction.setQid(getMapview);
					}
					
					/*getMapview = this.mapView(dir.getParentid(),dirMapper);
					System.out.println("QID is : " + getMapview);
					getMapview = getMapview.replaceAll("null", "");
					getMapview = getMapview.replaceAll(" null", "");
					System.out.println("QID after fix is : " + getMapview);
					
					interaction.setQid(getMapview);*/
		        	
				}
				else
				{
					//getMapview = dir.getDirectoryname();
					//interaction.setQid(dir.getDirectoryname());
					//getMapview = dir.getName();
					//interaction.setQid(dir.getName());
					getMapview = result.getRootcategory();
					interaction.setQid(result.getRootcategory());
				}
	        }
			
			
			boolean isEmptymessagetype = interactiongetfaqidstr.getMessagetype() == null || interactiongetfaqidstr.getMessagetype().trim().length() == 0;
			if(isEmptymessagetype)
			{
				interaction.setMessagetype("text");
				interactiongetfaqidstr.setMessagetype("text");
			}
			
			String serverUrl =result.getApi();
			if(result.getIsusevoice()>0)
			{
				if(interactiongetfaqidstr.getMessagetype().equals("audio"))
				{
					serverUrl =result.getVoiceapi();
				}
				else
				{
					serverUrl =result.getApi();
				}
			}
			else
			{
				serverUrl =result.getApi();
			}
			
			log.info("saving activity - update");
			//karena baru,maka di insert disini log activity nya
			this.saveTrainActivity("EDIT",interaction,interactiongetfaqidstr.getInteractionid(),interactiongetfaqidstr
					,interaction.getExpectedintentid());
			
			int getreturnintentid=0;
			JSONObject jsonObject = null;
	        JSONObject postdata = new JSONObject();
	        
	        
	        lastinsertuserid = this.saveRoocengineupdate(postdata, engAccesstoken, getMapview, interaction
	        			, getreturnintentid, getexpreturnintentid, postret, serverUrl
	        			, lastinsertuserid, intMapper, interactiongetfaqidstr, dir, dirMapper
	        			, intent, jsonObject);
	       
	        
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	
	@Override
	public String Saveonly(Interaction interaction) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			//userrole = 
			
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			Intent intent = ecMapper.findIntentiretquestionid(interaction.getIntentid());
			
			int getintentid = -1;
			int getreturnintentid =-1;
			String getintentquestion ="";
			
			int getexpintentid = -1;
			int getexpreturnintentid =-1;
			String getexpintentquestion ="";
			if(intent == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent id.");
			}
			else
			{
				getintentid = intent.getIntentid();
				getreturnintentid = intent.getIretquestionid();
				getintentquestion = intent.getQuestion();
			}
			
			
			
			interaction.setIntentid(getintentid);
			
			RoocConfigMapper rcMapper = sqlSession.getMapper(RoocConfigMapper.class);
			RoocConfig roocconfig = new RoocConfig();
			roocconfig = rcMapper.findByconfigkey("isEmailingchat");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
				System.out.print("System not using email");
	        }
			else
			{
				if(Integer.parseInt(roocconfig.getConfigvalue())>0)
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			        Date datefobj = new Date();
			        System.out.println("Date from : " +df.format(datefobj));
			        //Date datefrom=new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(df.format(datefobj));  
			        
			        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			        Date datetobj = new Date();
			        System.out.println("Date to : "+dt.format(datetobj));
			        //Date dateto=new SimpleDateFormat("yyyy-MM-dd 23:59:59").parse(dt.format(datetobj));
			        
			        DateFormat cd = new SimpleDateFormat("yyyy-MM-dd");
			        Date datecobj = new Date();
			        System.out.println("Chat Date : " +cd.format(datecobj));
			        
			        //boolean isEmpty = interaction.getCustomerchannelid() == null || interaction.getCustomerchannelid().trim().length() == 0;
					
			        if(interaction.getCustomerchannelid()>0)
			        {
			        	//Interaction interactiongetcountchat = intMapper.findCountinteractionperday(df.format(datefobj),dt.format(datetobj),interaction.getCustomerchannelid());
			        	//Date today = new Date();
			            //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
			            
			            Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
			    		Date tomorrow = DateUtils.addDays(today, 1);
			        	Interaction interactiongetcountchat = intMapper.findCountinteractionperday(today,tomorrow,interaction.getCustomerchannelid());
			        	if(interactiongetcountchat.getCount()<=0)
			        	{
			        		//from user with customerchannelid,findphone number
			        		Interaction useranyid = intMapper.findAnyidbycustomerchannelid(interaction.getCustomerchannelid());
			        		
			        		if(useranyid == null)
			        		{
			        			System.out.print("User Any ID : "+interaction.getCustomerchannelid() +" : Not Found");
			        		}
			        		else
			        		{
			        			String phonenumber =useranyid.getAnyid();
			        			phonenumber = phonenumber.replace("whatsapp2", "");
			        			phonenumber = phonenumber.replace("whatsapp", "");
			        			phonenumber = phonenumber.replace("roocvthree", "");
			        			phonenumber = phonenumber.replace(":", "");
			        			
			        			//String subject = "Chatbot : "+interaction.getCustomerchannelid() + cd.format(datecobj);
			        			String subject = "Chatbot : "+phonenumber + " "+ cd.format(datecobj);
			        			
				        		//send email
				        		SendEmail sendEmail = new SendEmail();
				        		sendEmail.sendMailauth(roocconfig,rcMapper,subject, interaction.getQuestion());
			        		}
			        		
			    			
			        	}
			        
			        }
				}
			}
			
			
			
			
			
			
			intent = ecMapper.findIntentiretquestionid(interaction.getExpectedintentid());
			if(intent == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid expected intent name.");
				//interaction.setExpectedintentid(getintentid);
				
				//getexpintentid = getintentid;
				//getexpreturnintentid = getreturnintentid;
				//getexpintentquestion = getintentquestion;
				getexpintentid = -1;
				getexpreturnintentid =-1;
				getexpintentquestion ="";
			}
			else
			{
				interaction.setExpectedintentid(intent.getIntentid());
				
				getexpintentid = intent.getIntentid();
				getexpreturnintentid = intent.getIretquestionid();
				getexpintentquestion = intent.getQuestion();
			}
			
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			int MaxIntent =0;
			if(interaction.getFaqidstr() == null || "".equals(interaction.getFaqidstr()))
			{
				
		        MaxIntent =result.getInitialincrement();
				Interaction resultintentmax = new Interaction();
				resultintentmax = intservice.getMaxinteraction(this.tenantIdentifier);
				
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}
				
				if(resultintentmax.getMaxinteractionid() <=0)
				{
					MaxIntent = MaxIntent + 1;
				}
				else
				{
					//MaxIntent = MaxIntent + resultintentmax.getMaxinteractionid() +1;
					MaxIntent = resultintentmax.getMaxinteractionid() +1;
				}
				
				//interaction.setFaqidstr("RSPD" + String.valueOf(MaxIntent));
				//interaction.setFaqidstr("RSPD" + String.valueOf(MaxIntent));
				long nowMillis = System.currentTimeMillis();
		        //Date now = new Date(nowMillis);
				interaction.setFaqidstr("RSPD" + String.valueOf(nowMillis));
			}
			interaction.setIsmanual(0);
			
			boolean isEmptymessagetype = interaction.getMessagetype() == null || interaction.getMessagetype().trim().length() == 0;
			
			if(isEmptymessagetype)
			{
				interaction.setMessagetype("text");
			}
			
			lastinsertuserid =intMapper.Saveonly(interaction);
			
			log.info("saving activity - NEW");
			//karena baru,maka di insert disini log activity nya
			Interaction interactiongetfaqidstr = new Interaction();
			this.saveTrainActivity("NEW",interaction,Integer.parseInt(lastinsertuserid),interactiongetfaqidstr
					,0);
			
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}


	@Override
	public String Updateonly(Interaction interaction) {
		/*
		 Just fot notes,untuk function Updateonly column expectedintentid dari icontek,bukan punya internal db.
		 Dapat value nya dari engine chat roocvthree
		 */
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		int retquestionid=0;
		Directory dir = new Directory();
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			DirectoryMapper dirMapper = sqlSession.getMapper(DirectoryMapper.class);
			//interaction.getExpectedintentid() --> dapat dari andy,dimana sudah dalam id icontek
			/*Intent intent = ecMapper.findIntentiretquestionid(interaction.getExpectedintentid());
			
			int getexpintentid =-1;
			
			if(intent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent id.");
			}*/
			
			Interaction interactiongetfaqidstr = intMapper.findOnebyfaqidstr(interaction.getFaqidstr());
			
			if(interactiongetfaqidstr == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid faq id str.");
			}
			
			retquestionid=interactiongetfaqidstr.getIretrespondid();
			
			//int getexpreturnintentid =-1;
			int getexpintentid =-1;
			Intent intent = new Intent();
			if(interaction.getExpectedintentid()>0)
			{
				intent = ecMapper.findIntentiretquestionid(interaction.getExpectedintentid());
				//intent = ecMapper.findOne(interaction.getExpectedintentid());
				
				
				if(intent == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid eng expected intent id.");
				}
				getexpintentid = intent.getIntentid();
			}
			/*
			else
			{
				getexpintentid = intent.getIntentid();
			}*/
			
			
			
			interaction.setExpectedintentid(getexpintentid);
			interaction.setIsmanual(0);
			
			 //interaction.setIstrain(0);
			interaction.setIsupdated(1);
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			
			
			String engAccesstoken ="";
			String hprret = "";
			JSONObject jsonObject = null;
			
			if(interactiongetfaqidstr.getIretrespondid()>0)
			{
				/*try {
					hprret = hpr.ConnectGetToken(result.getApi()+"/oauth/token",result.getUsername(),result.getPassword());
				}catch(Exception ex)
				{
					System.out.println(ex);
				}
				
				String jsonString = hprret;
		        
				
				jsonObject = new JSONObject(jsonString);
				if(jsonObject.getString("access_token") ==null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
				}
				engAccesstoken = jsonObject.getString("access_token");
		        System.out.println("token is :" + jsonObject.getString("access_token"));
				*/
			}
			
			String getMapview ="";
			this.retDataPath ="";
			System.out.println("expected intent id is : " + intent.getIntentid());
			System.out.println("expected intent directoryid is : " + intent.getDirectoryid());
			
			
			/*dir = dirMapper.findOne(intent.getDirectoryid());
			
			if(dir == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
			}*/
			
	        /*if(dir.getCategorymode().equals("STANDARD") || dir.getCategorymode().equals("ENUM") || dir.getCategorymode().equals("MEMORY"))
				{
					getMapview = this.mapView(intent.getDirectoryid(),dirMapper);
				}
				else
				{
					getMapview = this.mapView(dir.getParentid(),dirMapper);
				}*/

			//getMapview = this.mapView(intent.getDirectoryid(),dirMapper);
			/*
			if(dir.getParentid()>0)
			{
			
				Directory dirparent = dirMapper.findOne(dir.getParentid());
				
				//if(dirparent == null)
				//{
					//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
				//}
				
				if(dirparent.getCategorymode().equals("STANDARD"))
				{
					if(dir.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || dir.getCategorymode().equals("DRILLDOWN"))
					{
						getMapview = result.getRootcategory();
						interaction.setQid(result.getRootcategory());
					}
					else
					{
						getMapview = this.mapView(dir.getParentid(),dirMapper);
						System.out.println("QID is : " + getMapview);
						getMapview = getMapview.replaceAll("null", "");
						getMapview = getMapview.replaceAll(" null", "");
						System.out.println("QID after fix is : " + getMapview);
						
						interaction.setQid(getMapview);
					}
					
				}
				else
				{
					getMapview = this.mapView(dir.getParentid(),dirMapper);
					System.out.println("QID is : " + getMapview);
					getMapview = getMapview.replaceAll("null", "");
					getMapview = getMapview.replaceAll(" null", "");
					System.out.println("QID after fix is : " + getMapview);
					
					interaction.setQid(getMapview);
				}
				
				//getMapview = this.mapView(dir.getParentid(),dirMapper);
				//System.out.println("QID is : " + getMapview);
				//getMapview = getMapview.replaceAll("null", "");
				//getMapview = getMapview.replaceAll(" null", "");
				//System.out.println("QID after fix is : " + getMapview);

				//interaction.setQid(getMapview);

			}
			else
			{
				getMapview = dir.getDirectoryname();
				interaction.setQid(dir.getDirectoryname());
			}
			*/
			
			log.info("saving activity - update");
			//karena baru,maka di insert disini log activity nya
			this.saveTrainActivity("EDIT",interaction,interactiongetfaqidstr.getInteractionid(),interactiongetfaqidstr
					,interaction.getExpectedintentid());
			
			
	        
	        JSONObject postdata = new JSONObject();
	        
	        String serverUrl =result.getApi();
			if(result.getIsusevoice()>0)
			{
				if(interactiongetfaqidstr.getMessagetype().equals("audio"))
				{
					serverUrl =result.getVoiceapi();
				}
				else
				{
					serverUrl =result.getApi();
				}
			}
			else
			{
				serverUrl =result.getApi();
			}
	        
			
				postret = this.saveRoocengineupdateonly(postdata, engAccesstoken, interaction, postret
						, serverUrl, lastinsertuserid, intMapper, interactiongetfaqidstr, intent
						, jsonObject, getexpintentid, getMapview);
			
			
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		
		if(retquestionid>0)
		{
			return postret;
		}
		else
		{
			return this.lastinsertuserid;
		}
		
	}


	
	public String Updateonlybak01072019(Interaction interaction) {
		/*
		 Just fot notes,untuk function Updateonly column expectedintentid dari icontek,bukan punya internal db.
		 Dapat value nya dari engine chat roocvthree
		 */
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			//interaction.getExpectedintentid() --> dapat dari andy,dimana sudah dalam id icontek
			/*Intent intent = ecMapper.findIntentiretquestionid(interaction.getExpectedintentid());
			
			int getexpintentid =-1;
			
			if(intent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent id.");
			}*/
			
			Interaction interactiongetfaqidstr = intMapper.findOnebyfaqidstr(interaction.getFaqidstr());
			
			if(interactiongetfaqidstr == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid faq id str.");
			}
			
			//int getexpreturnintentid =-1;
			int getexpintentid =-1;
			Intent intent = new Intent();
			if(interaction.getExpectedintentid()>0)
			{
				intent = ecMapper.findIntentiretquestionid(interaction.getExpectedintentid());
				//intent = ecMapper.findOne(interaction.getExpectedintentid());
				
				
				if(intent == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid eng expected intent id.");
				}
				getexpintentid = intent.getIntentid();
			}
			/*
			else
			{
				getexpintentid = intent.getIntentid();
			}*/
			
			
			
			interaction.setExpectedintentid(getexpintentid);
			interaction.setIsmanual(0);
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			String engAccesstoken ="";
			String hprret = "";
			
			try {
				hprret = hpr.ConnectGetToken(result.getApi()+"/oauth/token",result.getUsername(),result.getPassword());
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
	        System.out.println("token is :" + jsonObject.getString("access_token"));
	        
	        JSONObject postdata = new JSONObject();
	        
	        if(getexpintentid>0)
	        {
	        	postdata.put("access_token", engAccesstoken);
	        	if(interactiongetfaqidstr.getIretrespondid()>0)
	        	{
	        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	}
		        
		        postdata.put("responseId", interaction.getFaqidstr());
				postdata.put("expectedFaqid", interaction.getExpectedintentid());
	        }
	        else
	        {
	        	postdata.put("access_token", engAccesstoken);
	        	if(interactiongetfaqidstr.getIretrespondid()>0)
	        	{
	        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	}
		        //postdata.put("id", interactiongetfaqidstr.getIretrespondid());
		        postdata.put("responseId", interaction.getFaqidstr());
				postdata.put("expectedFaqid", "");
	        }
	        
	        JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			postret = hpr.setPostDataarray(result.getApi()+"/response/update/expectedFaqid",ja);
			
			
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
					//interaction.setIstrain(0);
					//lastinsertuserid =intMapper.Update(interaction);
					lastinsertuserid =intMapper.Updateonly(interaction);
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
			
			
			
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
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
		}finally{
			sqlSession.close();
		}
		return postret;
	}

	@Override
	public void Updateistrain() {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		//String postret = "";
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			
			intMapper.Updateistrain();
			
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return postret;
		
	}

	@Override
	public Interaction findOnebyfaqidstr(String faqidstr) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findOnebyfaqidstr(faqidstr);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}

	@Override
	public List<Interaction> findInteractionintentwithjoin() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findInteractionintentwithjoin();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Interaction> findInteractionexpectedintentwithjoin() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findInteractionexpectedintentwithjoin();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	
	public String Saveinternal(Interaction interaction) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		
		Intent intent = new Intent();
		Intent intentexp = new Intent();
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			//userrole = 
			
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			boolean isEmpty = interaction.getIntentname() == null || interaction.getIntentname().trim().length() == 0;
			
			if(isEmpty)
			{
				//intentname tidak di passing..query berdasarkan intentid..
				intent = ecMapper.findOne(interaction.getIntentid());
			}
			else
			{
				intent = ecMapper.findIntentquestion(interaction.getIntentname());
			}
			
			
			
			int getintentid = -1;
			int getreturnintentid =-1;
			String getintentquestion ="";
			
			int getexpintentid = -1;
			int getexpreturnintentid =-1;
			String getexpintentquestion ="";
			
			if(intent == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent name or id.");
			}
			else
			{
				getintentid = intent.getIntentid();
				getreturnintentid = intent.getIretquestionid();
				getintentquestion = intent.getQuestion();
			}
			
			
			
			interaction.setIntentid(getintentid);
			
			RoocConfigMapper rcMapper = sqlSession.getMapper(RoocConfigMapper.class);
			RoocConfig roocconfig = new RoocConfig();
			roocconfig = rcMapper.findByconfigkey("isEmailingchat");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
				System.out.print("System not using email");
	        }
			else
			{
				if(Integer.parseInt(roocconfig.getConfigvalue())>0)
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			        Date datefobj = new Date();
			        System.out.println("Date from : " +df.format(datefobj));
			        //Date datefrom=new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(df.format(datefobj));  
			        
			        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			        Date datetobj = new Date();
			        System.out.println("Date to : "+dt.format(datetobj));
			        //Date dateto=new SimpleDateFormat("yyyy-MM-dd 23:59:59").parse(dt.format(datetobj));
			        
			        DateFormat cd = new SimpleDateFormat("yyyy-MM-dd");
			        Date datecobj = new Date();
			        System.out.println("Chat Date : " +cd.format(datecobj));
			        
			        //boolean isEmpty = interaction.getCustomerchannelid() == null || interaction.getCustomerchannelid().trim().length() == 0;
					
			        if(interaction.getCustomerchannelid()>0)
			        {
			        	//Interaction interactiongetcountchat = intMapper.findCountinteractionperday(df.format(datefobj),dt.format(datetobj),interaction.getCustomerchannelid());
			        	
			        	//Date today = new Date();
			            //Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
			            
			            Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
			    		Date tomorrow = DateUtils.addDays(today, 1);
			        	Interaction interactiongetcountchat = intMapper.findCountinteractionperday(today,tomorrow,interaction.getCustomerchannelid());
			        	if(interactiongetcountchat.getCount()<=0)
			        	{
			        		//from user with customerchannelid,findphone number
			        		Interaction useranyid = intMapper.findAnyidbycustomerchannelid(interaction.getCustomerchannelid());
			        		
			        		if(useranyid == null)
			        		{
			        			System.out.print("User Any ID : "+interaction.getCustomerchannelid() +" : Not Found");
			        		}
			        		else
			        		{
			        			String phonenumber =useranyid.getAnyid();
			        			phonenumber = phonenumber.replace("whatsapp2", "");
			        			phonenumber = phonenumber.replace("whatsapp", "");
			        			phonenumber = phonenumber.replace("roocvthree", "");
			        			phonenumber = phonenumber.replace(":", "");
			        			
			        			//String subject = "Chatbot : "+interaction.getCustomerchannelid() + cd.format(datecobj);
			        			String subject = "Chatbot : "+phonenumber + " "+ cd.format(datecobj);
			        			
				        		//send email
				        		SendEmail sendEmail = new SendEmail();
				        		sendEmail.sendMailauth(roocconfig,rcMapper,subject, interaction.getQuestion());
			        		}
			        		
			    			
			        	}
			        
			        }
				}
			}
			
			boolean isEmptyexpintentname = interaction.getExpectedintentname() == null || interaction.getExpectedintentname().trim().length() == 0;
			
			if(isEmptyexpintentname)
			{
				//expected intentname null or not pass,use id
				intentexp = ecMapper.findOne(interaction.getExpectedintentid());
			}
			else
			{
				intentexp = ecMapper.findIntentquestion(interaction.getExpectedintentname());
			}
			
			
			if(intentexp == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid expected intent name or id.");
				/*interaction.setExpectedintentid(getintentid);
				
				getexpintentid = getintentid;
				getexpreturnintentid = getreturnintentid;
				getexpintentquestion = getintentquestion;*/
			}
			else
			{
				interaction.setExpectedintentid(intent.getIntentid());
				
				getexpintentid = intentexp.getIntentid();
				getexpreturnintentid = intentexp.getIretquestionid();
				getexpintentquestion = intentexp.getQuestion();
			}
			
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			int MaxIntent =0;
			if(interaction.getFaqidstr() == null || "".equals(interaction.getFaqidstr()))
			{
				
		        MaxIntent =result.getInitialincrement();
				Interaction resultintentmax = new Interaction();
				resultintentmax = intservice.getMaxinteraction(this.tenantIdentifier);
				
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}
				
				if(resultintentmax.getMaxinteractionid() <=0)
				{
					MaxIntent = MaxIntent + 1;
				}
				else
				{
					MaxIntent = resultintentmax.getMaxinteractionid() +1;
				}
				
				//interaction.setFaqidstr("RSPD" + String.valueOf(MaxIntent));
				long nowMillis = System.currentTimeMillis();
		        //Date now = new Date(nowMillis);
				interaction.setFaqidstr("RSPD" + String.valueOf(nowMillis));
			}
			
			boolean isEmptymessagetype = interaction.getMessagetype() == null || interaction.getMessagetype().trim().length() == 0;
			
			if(isEmptymessagetype)
			{
				interaction.setMessagetype("text");
			}
			
	    	interaction.setIretrespondid(MaxIntent);
	    	interaction.setConfidencelevel(interaction.getConfidencelevel());
	    	interaction.setQid(interaction.getQid());
	    	interaction.setIsmanual(1);
	    	lastinsertuserid =intMapper.Saveinternal(interaction);
				
			

			log.debug("insert id is : " + lastinsertuserid);
			
			Interaction interactiongetfaqidstr = new Interaction();
			this.saveTrainActivity("NEW",interaction,Integer.parseInt(lastinsertuserid),interactiongetfaqidstr
					,0);
			
			log.info("insertintent data");
			
			
			
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid.toString();
	}

	
	@Override
	public String Updateinternal(Interaction interaction) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			//interaction.getExpectedintentid() --> dapat dari andy,dimana sudah dalam id icontek
			Intent intent = ecMapper.findIntentiretquestionid(interaction.getExpectedintentid());
			
			int getexpintentid =-1;
			
			if(intent == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent id.");
			}
			
			Interaction interactiongetfaqidstr = intMapper.findOnebyfaqidstr(interaction.getFaqidstr());
			
			if(interactiongetfaqidstr == null)
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid faq id str.");
			}
			
			//getexpintentid = intent.getIntentid();
			
			interaction.setExpectedintentid(getexpintentid);
			interaction.setIsmanual(0);
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			
			lastinsertuserid =intMapper.Updateinternal(interaction);
				
			this.saveTrainActivity("EDIT",interaction,interactiongetfaqidstr.getInteractionid(),interactiongetfaqidstr
					,interaction.getExpectedintentid());
			
			
			
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public List<Interaction> extractInteractionintent() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.extractInteractionintent();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error interaction join intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Interaction> extractInteractionexpectedintentid() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.extractInteractionexpectedintentid();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error interaction join intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Interaction> deletebyIntent(Integer expectedintentid) {
		System.out.println("del interaction List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.deletebyIntent(expectedintentid);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error delete interaction by intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	
	private String mapView(int InitcatID,DirectoryMapper dirMapper)
	{
		Directory dirresult = dirMapper.findOne(InitcatID);
		//return result;
		//if(InitcatID>0)
		//{
			if(dirresult == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent data");
			}
			
			if(dirresult.getParentid() >0)
			{
				 //$this->createCatmapView($modelfindParentretID->ParentID);
				this.mapView(dirresult.getParentid(),dirMapper);
				this.retDataPath = this.retDataPath +"->"+ dirresult.getName();
			}
			else if(dirresult.getParentid() <=0)
			{
				//$this->retDataPath = $this->retDataPath .$modelfindParentretID->Name;
				this.retDataPath = this.retDataPath + dirresult.getName();
			}
		//}
		return this.retDataPath;
	}

	@Override
	public Interaction findCountalljoin() {
		System.out.println("ec count all : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findCountalljoin();
			log.info("get intr count all data");
		}catch(PersistenceException e){
			log.debug(e + "error get intr count all data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}

	@Override
	public List<Interaction> findGetalljoinpaging(Integer limit, Integer offset, Integer paging) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		Interaction eccount = null;
		int rec_limit=10;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			
			eccount = ecMapper.findCountalljoin();
			
			limit = rec_limit;
			if(eccount.getCountalldata()<=0)
			{
				//paging =1;
				offset = 0;
			}
			else
			{
				if(paging<=1)
				{
					//paging =1;
					offset = 0;
				}
				else
				{
					offset = rec_limit * (paging-1);
				}
			}
			
			ec = ecMapper.findGetalljoinpaging(limit,offset,paging);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Interaction findCountinteractionperday(Date datefrom, Date dateto, Integer customerchannelid) {
		System.out.println("findCountinteractionperday List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findCountinteractionperday(datefrom,dateto,customerchannelid);
			log.info("get findCountinteractionperday data");
		}catch(PersistenceException e){
			log.debug(e + "error get findCountinteractionperday data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}

	@Override
	public Interaction findAnyidbycustomerchannelid(Integer id) {
		System.out.println("findAnyidbycustomerchannelid List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAnyidbycustomerchannelid(id);
			log.info("get findAnyidbycustomerchannelid data");
		}catch(PersistenceException e){
			log.debug(e + "error get findAnyidbycustomerchannelid data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}

	@Override
	public List<Interaction> findAlljoin() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAlljoin();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get findAlljoin data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	
	@Override
	public List<InteractionChathistory> findAlljoindate(Date datefrom, Date dateto) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<InteractionChathistory> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAlljoindate(datefrom,dateto);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get findAlljoin data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<InteractionIndex> findAlljoinistraindate(Date datefrom, Date dateto) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<InteractionIndex> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAlljoinistraindate(datefrom,dateto);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get findAlljoin data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<InteractionIndex> findGetlistjoinpaging(Integer limit, Integer offset, Integer paging, String where,
			String sort,Integer countData) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<InteractionIndex> ec = null;
		InteractionIndex eccount = null;
		int rec_limit=limit;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			
			//eccount = ecMapper.findCountalljoinpaging(where);
			
			//limit = rec_limit;
			//if(eccount.getCountalldata()<=0)
			if(countData<=0)
			{
				//paging =1;
				offset = 0;
			}
			else
			{
				if(paging<=1)
				{
					//paging =1;
					offset = 0;
				}
				else
				{
					offset = rec_limit * (paging-1);
				}
			}
			
			ec = ecMapper.findGetlistjoinpaging(limit,offset,paging,where,sort,countData);
			log.info("get intr paging data");
		}catch(PersistenceException e){
			log.debug(e + "error get intr paging data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public InteractionIndex findCountalljoinpaging(String where) {
		System.out.println("ec count all : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		InteractionIndex ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findCountalljoinpaging(where);
			log.info("get intr count all data");
		}catch(PersistenceException e){
			log.debug(e + "error get intr count all data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (InteractionIndex) ec;
	}

	@Override
	public void Updateengineidonly(Interaction interaction) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		//String postret = "";
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			
			
			
			intMapper.Updateengineidonly(interaction);
			
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return postret;
		
	}

	@Override
	public String Updatedelete(Interaction interaction) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		String updatedid = "";
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			
			Interaction interactiongetfaqidstr = intMapper.findOne(interaction.getInteractionid());
			
			if(interactiongetfaqidstr == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid interaction id.");
			}
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			
			
			String engAccesstoken ="";
			String hprret = "";
			JSONObject jsonObject = null;
			
			String getMapview ="";
			this.retDataPath ="";


			getMapview = result.getRootcategory();
			interaction.setQid(result.getRootcategory());
			
			/*try {
				hprret = hpr.ConnectGetToken(result.getApi()+"/oauth/token",result.getUsername(),result.getPassword());
			}catch(Exception ex)
			{
				System.out.println(ex);
			}
			
			String jsonString = hprret;
	        
			
			jsonObject = new JSONObject(jsonString);
			if(jsonObject.getString("access_token") ==null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
			}
			engAccesstoken = jsonObject.getString("access_token");
	        System.out.println("token is :" + jsonObject.getString("access_token"));*/
	        
			this.saveTrainActivity("DELETE",interaction,interactiongetfaqidstr.getInteractionid(),interactiongetfaqidstr
					,interaction.getExpectedintentid());
			
			
	        JSONObject postdata = new JSONObject();
	        
	        
			
			String serverUrl =result.getApi();
			if(result.getIsusevoice()>0)
			{
				if(interactiongetfaqidstr.getMessagetype().equals("audio"))
				{
					serverUrl =result.getVoiceapi();
				}
				else
				{
					serverUrl =result.getApi();
				}
			}
			else
			{
				serverUrl =result.getApi();
			}
			
			
				updatedid = this.saveRoocengineupdatedelete(postdata, engAccesstoken, interaction
						, engAccesstoken, hprret, intMapper, interactiongetfaqidstr, result
						, jsonObject, serverUrl, updatedid, getMapview);
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return updatedid;
	}

	@Override
	public List<Interaction> findAlljointrain() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Interaction> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAlljointrain();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get findAlljoin data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<LinkedHashMap> findAlljointrainv3() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<LinkedHashMap> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAlljointrainv3();
			log.info("getint data LinkedHashMap");
		}catch(PersistenceException e){
			log.debug(e + "error get findAlljoin data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Interaction findCountjointrain() {
		System.out.println("findCountjointrain List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findCountjointrain();
			log.info("get findCountjointrain data");
		}catch(PersistenceException e){
			log.debug(e + "error get findCountjointrain data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}
	
	
	private Integer saveRoocenginecreate(JSONObject postdata,String engAccesstoken,String getMapview
			,Interaction interaction,int getreturnintentid,int getexpreturnintentid,String postret
			,String serverUrl,int lastinsertuserid,InteractionMapper intMapper)
	{
		try {
			postdata.put("Authorization", engAccesstoken);
	        //postdata.put("questionId", result.getRootcategory());
	        postdata.put("map", getMapview);
	        postdata.put("responseId", interaction.getFaqidstr());
			postdata.put("question", interaction.getQuestion());
			
			int retintentid =0;
			if(getreturnintentid<=0)
			{
				retintentid = interaction.getIntentid();
			}
			else
			{
				retintentid = getreturnintentid;
			}
			//postdata.put("faqid", getreturnintentid);
			postdata.put("faqid", retintentid);
			
			int retexpintentid =0;
			if(getexpreturnintentid<=0)
			{
				retexpintentid = interaction.getExpectedintentid();
			}
			else
			{
				retexpintentid = getexpreturnintentid;
			}
			//postdata.put("expectedFaqId", getexpreturnintentid);
			postdata.put("expectedFaqId", retexpintentid);

			//postdata.put("faqid", getreturnintentid);
			//postdata.put("expectedFaqId", getexpreturnintentid);
			//postdata.put("questionId", questionId);
			
			
			JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			postret = hpr.setPostData(serverUrl+"/question",postdata);
			
			
			System.out.println("post return is : "+ postret);
			 Object item = postret;//json.get("URL"); 
			
			 JSONArray jsonarray = new JSONArray(postret);
				for (int i = 0; i < jsonarray.length(); i++) {
				    JSONObject jsonobject = jsonarray.getJSONObject(i);
				    int respId = jsonobject.getInt("id");
				    //double cl = jsonobject.getDouble("confidence");
				    //String qid = jsonobject.getString("questionId");
				    if(respId>0)
				    {
				    	interaction.setIretrespondid(respId);
				    	//interaction.setConfidencelevel(cl);
				    	//System.out.println("qid from icontek is : " + qid);
				    	//interaction.setQid(qid);
				    	interaction.setIsmanual(1);
				    	lastinsertuserid =intMapper.Save(interaction);
				    }
				}
			/*}
			else
			{
				log.debug("post return is :" + postret);
			}*/
			

			log.debug("insert id is : " + lastinsertuserid);
			
			log.info("insertintent data");
			
			Interaction interactiongetfaqidstr = new Interaction();
			this.saveTrainActivity("NEW",interaction,lastinsertuserid,interactiongetfaqidstr
					,0);
			
			
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lastinsertuserid;
	}
	
	
	private Integer saveRoocengineupdate(JSONObject postdata,String engAccesstoken,String getMapview
			,Interaction interaction,int getreturnintentid,int getexpreturnintentid,String postret
			,String serverUrl,Integer lastinsertuserid,InteractionMapper intMapper
			,Interaction interactiongetfaqidstr,Directory dir,DirectoryMapper dirMapper,Intent intent
			,JSONObject jsonObject)
	{
		try {
			postdata.put("Authorization", engAccesstoken);
			postdata.put("map", getMapview);
	        if(getexpreturnintentid>0)
	        {
	        	/*String getMapview ="";
	        	
	        	System.out.println("expected intent id is : " + intent.getIntentid());
				System.out.println("expected intent directoryid is : " + intent.getDirectoryid());
				*/

				System.out.println("expected intent id is : " + intent.getIntentid());
				System.out.println("expected intent directoryid is : " + intent.getDirectoryid());
				dir = dirMapper.findOne(intent.getDirectoryid());
				
				if(dir == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
				}
				
				System.out.println("expected intent directory parentid is : " + dir.getParentid());
				

				
	        	//postdata.put("access_token", engAccesstoken);
		        //postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	if(interactiongetfaqidstr.getIretrespondid()>0)
	        	{
	        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	}
		        postdata.put("responseId", interactiongetfaqidstr.getFaqidstr());

				postdata.put("expectedFaqId", getexpreturnintentid);
	        }
	        else
	        {

		        //postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	if(interactiongetfaqidstr.getIretrespondid()>0)
	        	{
	        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	}
		        postdata.put("responseId", interactiongetfaqidstr.getFaqidstr());
				//postdata.put("map", getMapview);
				postdata.put("expectedFaqId", 0);
	        }
			
			
	        System.out.println("Post data is :" +postdata);
			
			JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			postret = hpr.setPostData(serverUrl+"/question/update",postdata);
			
			
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
				if(jsonObject.getBoolean("STATUS"))
				{
					//String idparse = obj.getJSONObject("MESSAGE").getString("id");
					interaction.setIretrespondid(jsonObject.getJSONObject("MESSAGE").getInt("id"));
					interaction.setIsmanual(1);
					lastinsertuserid =intMapper.Update(interaction);
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
			
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lastinsertuserid;
	}
	
	
	private String saveRoocengineupdateonly(JSONObject postdata,String engAccesstoken
			,Interaction interaction,String postret
			,String serverUrl,String lastinsertuserid,InteractionMapper intMapper
			,Interaction interactiongetfaqidstr,Intent intent
			,JSONObject jsonObject,int getexpintentid,String getMapview)
	{
		try {
			
			postdata.put("Authorization", engAccesstoken);
			postdata.put("map", getMapview);
	        if(getexpintentid>0)
	        {

	        	if(interactiongetfaqidstr.getIretrespondid()>0)
	        	{
	        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	}
		        
		        postdata.put("responseId", interaction.getFaqidstr());
				postdata.put("expectedFaqId", interaction.getExpectedintentid());
	        }
	        else
	        {
	        	//postdata.put("access_token", engAccesstoken);
	        	if(interactiongetfaqidstr.getIretrespondid()>0)
	        	{
	        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
	        	}
		        //postdata.put("id", interactiongetfaqidstr.getIretrespondid());
		        postdata.put("responseId", interaction.getFaqidstr());
				postdata.put("expectedFaqId", 0);
	        }
	        
	       
	        
	        JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			/*boolean isEmptymessagetype = interaction.getMessagetype() == null || interaction.getMessagetype().trim().length() == 0;
			if(isEmptymessagetype)
			{
				interaction.setMessagetype("text");
			}*/
			
			
			
			if(interactiongetfaqidstr.getIretrespondid()>0)
			{
				
				
				//postret = hpr.setPostData(serverUrl+"/intent/update",postdata);
				postret = hpr.setPostData(serverUrl+"/question/update",postdata);
				
				System.out.println("post return is : "+ postret);
				
				jsonObject = new JSONObject(postret);
				
				//error message from roocengine : ERROR
				String errMessage ="Something went wrong";
				if(jsonObject.has("ERROR"))
				{
					errMessage = jsonObject.getString("ERROR");
				}
				
				if(jsonObject.has("STATUS"))
				{
					if(jsonObject.getBoolean("STATUS"))
					{
						//interaction.setIstrain(0);
						//lastinsertuserid =intMapper.Update(interaction);
						lastinsertuserid =intMapper.Updateonly(interaction);
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
			else
			{
				if(interaction.getUserchannelid()>0)
				{
					UserProfileMapper usrpMapper = sqlSession.getMapper(UserProfileMapper.class);
					UserProfile userprofile = new UserProfile();
					userprofile = usrpMapper.findByuserchannelid(interaction.getUserchannelid());
					if (userprofile == null) {
			            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
						System.out.print("No User Channel is found");
			        }
					else
					{
						interaction.setUpdatedby(userprofile.getUserid());
					}
				}
				
				lastinsertuserid =intMapper.Updateonly(interaction);
			}
			
			//this.retquestionid = retquestionid;
			this.postret = postret;
			this.lastinsertuserid = lastinsertuserid;
			
			log.info("update data");
			
	        
			
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return postret;
	}
	
	

	private String saveRoocengineupdatedelete(JSONObject postdata,String engAccesstoken
			,Interaction interaction,String postret
			,String lastinsertuserid,InteractionMapper intMapper
			,Interaction interactiongetfaqidstr
			,EngineCredential result,JSONObject jsonObject,String serverUrl,String updatedid,String getMapview)
	{
		try {
			
			postdata.put("map", getMapview);
			postdata.put("Authorization", engAccesstoken);
	        //postdata.put("id", interactiongetfaqidstr.getIretrespondid());
        	if(interactiongetfaqidstr.getIretrespondid()>0)
        	{
        		postdata.put("id", interactiongetfaqidstr.getIretrespondid());
        	}
	        postdata.put("responseId", interactiongetfaqidstr.getFaqidstr());
	        postdata.put("expectedFaqId", 0);
			

			
	        JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			//String postret = hpr.setPostDataarray(serverUrl+"/response/update/expectedFaqid",ja);
			postret = hpr.setPostData(serverUrl+"/question/update",postdata);

			System.out.println("post return is : "+ postret);

			jsonObject = new JSONObject(postret);

			if(jsonObject.has("STATUS"))
			{
				if(jsonObject.getBoolean("STATUS"))
				{
					//interaction.setIstrain(0);
					//lastinsertuserid =intMapper.Update(interaction);
					interaction.setIsmanual(1);
					updatedid = intMapper.Updatedelete(interaction);
				}
				else
				{
					throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved");
				}
			}
			else
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved");
			}
			
			log.info("update data");
			
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updatedid;
	}

	@Override
	public void Updatesyncroocengineid(Interaction interaction) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		//String postret = "";
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			
			
			
			intMapper.Updatesyncroocengineid(interaction);
			
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return postret;
		
	}
	
	private void saveTrainActivity(String ActivityName,Interaction interaction,int lastinsertuserid,Interaction interactiongetfaqidstr
			,int getexpintentid)
	{
		TrainLogActivity tlasave = new TrainLogActivity();
		TrainLogActivity tla = new TrainLogActivity();
		tla.setCreatedby(interaction.getUpdatedby());
		tla.setUpdatedby(interaction.getUpdatedby());
		tla.setCreatedtime(interaction.getUpdatedtime());
		tla.setUpdatedtime(interaction.getUpdatedtime());
		if(ActivityName.equals("NEW"))
		{
			log.info("saving activity");
			//karena baru,maka di insert disini log activity nya
			
			tla.setInteractionid(lastinsertuserid);
			tla.setActivityname("NEW");
			//tla.setExpectedintentidbefore(interaction.getExpectedintentid());
			//tla.setExpectedintentidafter(0);
			tla.setExpectedintentidbefore(0);
			tla.setExpectedintentidafter(interaction.getExpectedintentid());
			tla.setConfidencelevel(interaction.getConfidencelevel());
			
		}
		else if(ActivityName.equals("EDIT"))
		{
			log.info("saving activity - update");
			//karena baru,maka di insert disini log activity nya
			
			tla.setInteractionid(interactiongetfaqidstr.getInteractionid());
			tla.setActivityname("EDIT");
			tla.setExpectedintentidbefore(interactiongetfaqidstr.getExpectedintentid());
			tla.setExpectedintentidafter(getexpintentid);
			//tla.setExpectedintentidafter(interaction.getExpectedintentid());
			tla.setConfidencelevel(interactiongetfaqidstr.getConfidencelevel());
			
		}
		else if(ActivityName.equals("DELETE"))
		{
			log.info("saving activity - update delete");
			//karena baru,maka di insert disini log activity nya
			
			tla.setInteractionid(interactiongetfaqidstr.getInteractionid());
			tla.setActivityname("DELETE");
			tla.setExpectedintentidbefore(interactiongetfaqidstr.getExpectedintentid());
			tla.setExpectedintentidafter(interaction.getExpectedintentid());
			tla.setConfidencelevel(interactiongetfaqidstr.getConfidencelevel());
			
			
		}
		tlasave = trainlogactivityservice.getCreate(this.tenantIdentifier, tla);
		System.out.print(tlasave);
		
	}

	@Override
	public Interaction findOnebyrespondid(Integer iretrespondid) {
		System.out.println("findOnebyrespondid List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findOnebyrespondid(iretrespondid);
			log.info("getfindOnebyrespondid data");
		}catch(PersistenceException e){
			log.debug(e + "error get findOnebyrespondid data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}


	@Override
	public List<LinkedHashMap> findAlljoindatev3(Date datefrom, Date dateto) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<LinkedHashMap> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findAlljoindatev3(datefrom, dateto);
			log.info("getint data v3");
		}catch(PersistenceException e){
			log.debug(e + "error get findAlljoindatev3");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
		return ec;
	}
	
	/*v3*/
	
	public Integer Save(Interaction interaction) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Integer lastinsertuserid=0;
		String postret = "";
		Intent intent = new Intent();
		Intent intentexp = new Intent();
		Directory dir = new Directory();
		try{
			InteractionMapper intMapper = sqlSession.getMapper(InteractionMapper.class);
			//userrole = 
			
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			DirectoryMapper dirMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			
			
			boolean isEmpty = interaction.getIntentname() == null || interaction.getIntentname().trim().length() == 0;
			
			if(isEmpty)
			{
				//intentname tidak di passing..query berdasarkan intentid..
				intent = ecMapper.findOne(interaction.getIntentid());
			}
			else
			{
				intent = ecMapper.findIntentquestion(interaction.getIntentname());
			}
			
			
			
			int getintentid = -1;
			int getreturnintentid =-1;
			String getintentquestion ="";
			
			int getexpintentid = -1;
			int getexpreturnintentid =-1;
			String getexpintentquestion ="";
			
			if(intent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intent name or id.");
			}
			else
			{
				getintentid = intent.getIntentid();
				getreturnintentid = intent.getIretquestionid();
				getintentquestion = intent.getQuestion();
			}
			
			
			
			interaction.setIntentid(getintentid);
			
			
			
			
			boolean isEmptyexpintentname = interaction.getExpectedintentname() == null || interaction.getExpectedintentname().trim().length() == 0;
			
			if(isEmptyexpintentname)
			{
				//expected intentname null or not pass,use id
				intentexp = ecMapper.findOne(interaction.getExpectedintentid());
			}
			else
			{
				intentexp = ecMapper.findIntentquestion(interaction.getExpectedintentname());
			}
			
			
			if(intentexp == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid expected intent name or id.");
				
			}
			else
			{
				interaction.setExpectedintentid(intentexp.getIntentid());
				
				getexpintentid = intentexp.getIntentid();
				getexpreturnintentid = intentexp.getIretquestionid();
				getexpintentquestion = intentexp.getQuestion();
			}
			
			String getMapview ="";
			System.out.println("expected intent id is : " + intentexp.getIntentid());
			System.out.println("expected intent directoryid is : " + intentexp.getDirectoryid());
			dir = dirMapper.findOne(intentexp.getDirectoryid());
			
			if(dir == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory.");
			}
			
			System.out.println("expected intent directory parentid is : " + dir.getParentid());
			
			
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if(result == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			if(dir.getParentid()>0)
			{
				
				Directory dirparent = dirMapper.findOne(dir.getParentid());
				
				
				
				if(dirparent.getCategorymode().equals("STANDARD"))
				{
					if(dir.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || dir.getCategorymode().equals("DRILLDOWN"))
					{
						getMapview = result.getRootcategory();
						interaction.setQid(result.getRootcategory());
					}
					else
					{
						getMapview = this.mapView(dir.getParentid(),dirMapper);
						System.out.println("QID is : " + getMapview);
						getMapview = getMapview.replaceAll("null", "");
						getMapview = getMapview.replaceAll(" null", "");
						System.out.println("QID after fix is : " + getMapview);
						
						interaction.setQid(getMapview);
					}
					
				}
				else
				{
					getMapview = this.mapView(dir.getParentid(),dirMapper);
					System.out.println("QID is : " + getMapview);
					getMapview = getMapview.replaceAll("null", "");
					getMapview = getMapview.replaceAll(" null", "");
					System.out.println("QID after fix is : " + getMapview);
					
					interaction.setQid(getMapview);
				}
	        	
			}
			else
			{
				getMapview = result.getRootcategory();
				interaction.setQid(result.getRootcategory());
			}
			
			
			int MaxIntent =0;
			if(interaction.getFaqidstr() == null || "".equals(interaction.getFaqidstr()))
			{
				
		        MaxIntent =result.getInitialincrement();
				Interaction resultintentmax = new Interaction();
				resultintentmax = intservice.getMaxinteraction(this.tenantIdentifier);
				
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}
				
				if(resultintentmax.getMaxinteractionid() <=0)
				{
					MaxIntent = MaxIntent + 1;
				}
				else
				{
					MaxIntent = resultintentmax.getMaxinteractionid() +1;
				}
				
				//interaction.setFaqidstr("RSPD" + String.valueOf(MaxIntent));
				long nowMillis = System.currentTimeMillis();
		        //Date now = new Date(nowMillis);
				interaction.setFaqidstr("RSPD" + String.valueOf(nowMillis));
			}
			
			
			
			String serverUrl =result.getApi();
			if(result.getIsusevoice()>0)
			{
				boolean isEmptymessagetype = interaction.getMessagetype() == null || interaction.getMessagetype().trim().length() == 0;
				if(isEmptymessagetype)
				{
					interaction.setMessagetype("text");
				}
				
				if(interaction.getMessagetype().equals("audio"))
				{
					serverUrl =result.getVoiceapi();
				}
				else
				{
					serverUrl =result.getApi();
				}
			}
			else
			{
				serverUrl =result.getApi();
			}
			
			String engAccesstoken ="";
			String hprret = "";
			
			
			
			
	        
	        JSONObject postdata = new JSONObject();
	        
	        
	        lastinsertuserid = this.saveRoocenginecreate(postdata, engAccesstoken, getMapview, interaction
	        			, getreturnintentid, getexpreturnintentid, postret, serverUrl, lastinsertuserid
	        			, intMapper);
	        
	        System.out.println("====postret : "+postret);
			
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public List<InteractionResponse> findlistquestionbyexpectedintentid(Integer expectedintentid) {
		System.out.println("int findlistquestionbyexpectedintentid : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<InteractionResponse> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findlistquestionbyexpectedintentid(expectedintentid);
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get findlistquestionbyexpectedintentid data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<InteractionResponse> findlistquestions() {
		System.out.println("int findlistquestions : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<InteractionResponse> ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findlistquestions();
			log.info("getint data");
		}catch(PersistenceException e){
			log.debug(e + "error get findlistquestions data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Integer Savequestion(Question question) {
		System.out.println("Question save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		int lastinsertuserid=0;
		try{
			InteractionMapper mlMapper = sqlSession.getMapper(InteractionMapper.class);
			//userrole = 
			lastinsertuserid =mlMapper.Savequestion(question);
			log.info("insert question data");
		}catch(PersistenceException e){
			log.debug(e + "error get question data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public Question findGetQuestion(String question, Integer intentid) {
		System.out.println("Question List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Question ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findGetQuestion(question,intentid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get findGetQuestion data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Question) ec;
	}

	@Override
	public Interaction findlistinteractionbyquestions(String question, Integer expectedintentid) {
		System.out.println("Question List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Interaction ec = null;
		try{
			InteractionMapper ecMapper = sqlSession.getMapper(InteractionMapper.class);
			ec = ecMapper.findlistinteractionbyquestions(question,expectedintentid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get findGetQuestion data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Interaction) ec;
	}

	@Override
	public Integer Updatequestionhasdetail(Question question) {
		System.out.println("Question save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		int lastinsertuserid=0;
		try{
			InteractionMapper mlMapper = sqlSession.getMapper(InteractionMapper.class);
			//userrole = 
			lastinsertuserid =mlMapper.Updatequestionhasdetail(question);
			log.info("insert Updatequestionhasdetail data");
		}catch(PersistenceException e){
			log.debug(e + "error get Updatequestionhasdetail data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}
	
	/*v3*/
	
}


