package com.egeroo.roocvthree.intent;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
import com.egeroo.roocvthree.directory.Directory;
import com.egeroo.roocvthree.directory.DirectoryMapper;
import com.egeroo.roocvthree.directory.DirectoryService;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class IntentMapperImpl extends BaseDAO implements IntentMapper{
	
	private static final Logger log = Logger.getLogger(IntentMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	private int ECID = 1;
	IntentService intservice = new IntentService();
	private String retDataPath;
	ValidationJson validatejson = new ValidationJson(); 
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService();
    DirectoryService dirservice = new DirectoryService();
	
	public IntentMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Intent> findAll() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
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
	public List<Intent> findAllV3() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findAllV3();
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
	public List<Intent> findIntent() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findIntent();
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
	public Intent findOne(Integer intentid) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findOne(intentid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Intent) ec;
	}

	@Override
	public Intent findMaxintentid() {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findMaxintentid();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Intent) ec;
	}

	@Override
	public String Save(Intent intent) {
		System.out.println("intent internal save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			IntentMapper userMapper = sqlSession.getMapper(IntentMapper.class);
			//userrole = 
			//intent.setIsgenerated(0);
			lastinsertuserid =userMapper.Save(intent);
			
			log.debug("insert id is : " + lastinsertuserid);
			
			log.info("insertintent data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}
	
	@SuppressWarnings("unused")
	private String mapView(DirectoryMapper dirMapper,int InitcatID)
	{
		Directory dirresult = dirMapper.findOne(InitcatID);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent data");
		}
		
		if(dirresult.getParentid() >0)
		{
			 //$this->createCatmapView($modelfindParentretID->ParentID);
			this.mapView(dirMapper, dirresult.getParentid());
			this.retDataPath = this.retDataPath +"->"+ dirresult.getName();
		}
		else if(dirresult.getParentid() <=0)
		{
			//$this->retDataPath = $this->retDataPath .$modelfindParentretID->Name;
			this.retDataPath = this.retDataPath + dirresult.getName();
		}
		
		return this.retDataPath;
	}
	
	@Override
	public String Saveengine(Intent intent) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		String postretvoice = "";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		this.retDataPath ="";
		Directory dirparent = new Directory();
		
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			DirectoryMapper dirMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			
			
			
			
			
			
			boolean isEmptymap = intent.getDirectorymap() == null || intent.getDirectorymap().trim().length() == 0;
			boolean isEmpty = intent.getDirectoryname() == null || intent.getDirectoryname().trim().length() == 0;
			
			if(!isEmptymap)
			{
				String strMap = intent.getDirectorymap().trim();
				String[] split = strMap.split("->");
				
				int getparentID =0 ;
				for (int i = 0; i < split.length; i++) {
					if(i==0)
					{
						//the first one query without parentid only name
						dirparent = dirMapper.findParentbyname(split[i].trim());
					}
					else
					{
						//not the first one query with parentid and name
						dirparent = dirMapper.findDirectorybynameandparentid(split[i],getparentID);
					}
					
					if(dirparent==null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
					}
					else
					{
						getparentID = dirparent.getDirectoryid();
					}
					
					System.out.println(split[i] + " \n");
				}
			}
			else if(!isEmpty)
			{
				dirparent = dirMapper.findDirectorybyname(intent.getDirectoryname().trim());
				
			}
			else
			{
				dirparent = dirMapper.findOne(intent.getDirectoryid());
			}
			
			
			if(dirparent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
			}
			
			//block add intent jika category nya QUESTIONNAIRE_BRANCHING
			if(dirparent.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
			{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Can't create intent on Branching Category.");
				System.out.print("Intent Branching or DrillDown,cannot do double save");
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Intent Branching or DrillDown,cannot do double save");
				//return;
			}
			
			if(dirparent.getName().equals(intent.getQuestion()))
    		{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Can't create intent on Branching Category.");
				System.out.print("Intent Branching or DrillDown,cannot do double Intent");
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Intent Branching or DrillDown,cannot do double Intent");
				//return;
    		}
			
			
			if(!isEmptymap)
			{
				intent.setDirectoryid(dirparent.getDirectoryid());
			}
			else if(!isEmpty)
			{
				intent.setDirectoryid(dirparent.getDirectoryid());
			}
			
			int retparID = dirparent.getReticategoryid();
			
			//String retparName = dirparent.getName();
			
			String MapView = this.mapView(dirMapper, intent.getDirectoryid());
			
			System.out.println("mapview is :" + MapView);
			
			String engAccesstoken ="";
			//String localAccesstoken =Token;
			
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
	        }
			
			int MaxIntent =0;
	        //MaxIntent =result.getInitialincrement();
			Intent resultintentmax = new Intent();
			resultintentmax = intservice.getMaxintent(this.tenantIdentifier);
			
			if(resultintentmax == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			if(resultintentmax.getMaxintentid() <=0)
			{
				MaxIntent = result.getInitialincrement();
			}
			else
			{
				MaxIntent = resultintentmax.getMaxintentid() +1;
			}
			
			intent.setIntentid(MaxIntent);
			
	        
			MaxIntent maxintent = new MaxIntent();
			maxintent.setIntentid(MaxIntent);
			maxintent.setCreatedby(intent.getCreatedby());
			maxintent.setUpdatedby(intent.getUpdatedby());
			maxintent.setCreatedtime(intent.getCreatedtime());
			maxintent.setUpdatedtime(intent.getUpdatedtime());
			String savemaxIntent = ecMapper.Savemaxintent(maxintent);
			System.out.println("Save Max Intent record is :" + savemaxIntent+ " , Max intent is :"+MaxIntent);
			System.out.println("MaxIntent + 1 : "+ MaxIntent);
			System.out.println("MaxIntent is : "+ resultintentmax.getMaxintentid());
			
			
			
			
			
			
			prettyJsonString = this.saveRoocenginecreate(MaxIntent, intent, result, postret, retparID
					, 0, postretvoice, lastinsertuserid, ecMapper
					, 0, postmessage, prettyJsonString, gson, jp
					, engAccesstoken, dirMapper, dirparent,resultintentmax,MapView);
			
		
			
			log.info("insertintent data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
		return prettyJsonString;
	}

	@Override
	public String Update(Intent intent) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		String postretvoice = "";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		Directory dirparent = new Directory();
		Directory dirupdatekey = new Directory();
		try{
			IntentMapper intentMapper = sqlSession.getMapper(IntentMapper.class);
			DirectoryMapper dirMapper = sqlSession.getMapper(DirectoryMapper.class);
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			
			
			
			
			boolean isEmptymap = intent.getDirectorymap() == null || intent.getDirectorymap().trim().length() == 0;
			boolean isEmpty = intent.getDirectoryname() == null || intent.getDirectoryname().trim().length() == 0;
			
			//for insert purpose
			if(!isEmptymap)
			{
				String strMap = intent.getDirectorymap().trim();
				String[] split = strMap.split("->");
				int getparentID =0 ;
				for (int i = 0; i < split.length; i++) {
					if(i==0)
					{
						//the first one query without parentid only name
						dirparent = dirMapper.findParentbyname(split[i].trim());
					}
					else
					{
						//not the first one query with parentid and name
						dirparent = dirMapper.findDirectorybynameandparentid(split[i],getparentID);
					}
					
					if(dirparent==null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
					}
					else
					{
						getparentID = dirparent.getDirectoryid();
					}
					
					System.out.println(split[i] + " \n");
				}
			}
			else if(!isEmpty)
			{
				dirparent = dirMapper.findDirectorybyname(intent.getDirectoryname().trim());
			}
			else
			{
				dirparent = dirMapper.findOne(intent.getDirectoryid());
			}
			//Directory dirparent = dirMapper.findOne(intent.getDirectoryid());
			
			if(dirparent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory id or name.");
			}
			
			
			
			if(!isEmptymap)
			{
				intent.setDirectoryid(dirparent.getDirectoryid());
			}
			else if(!isEmpty)
			{
				intent.setDirectoryid(dirparent.getDirectoryid());
			}
			
			int retparID = dirparent.getReticategoryid();
			
			//String retparName = dirparent.getName();
			
			
			boolean isEmptyupdatekey = intent.getUpdatekey() == null || intent.getUpdatekey().trim().length() == 0;
			boolean isEmptyoldintentname = intent.getOldintentname() == null || intent.getOldintentname().trim().length() == 0;
			
			String MapView = this.mapView(dirMapper, intent.getDirectoryid());
			System.out.println("mapview is :" + MapView);
			
			
			
			
			//for updating purpose
			if(!isEmptyupdatekey && !isEmptyoldintentname)
			{
				if(!isEmptyupdatekey)
				{
					//dirparent = ecMapper.findParentbyname(directory.getParentname());
					String strMap = intent.getUpdatekey().trim();
					String[] split = strMap.split("->");
					int getparentID =0 ;
					for (int i = 0; i < split.length; i++) {
						if(i==0)
						{
							//the first one query without parentid only name
							dirupdatekey = dirMapper.findParentbyname(split[i].trim());
						}
						else
						{
							//not the first one query with parentid and name
							dirupdatekey = dirMapper.findDirectorybynameandparentid(split[i],getparentID);
						}
						
						if(dirupdatekey==null)
						{
							throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory update key.");
						}
						else
						{
							getparentID = dirupdatekey.getDirectoryid();
							//intent.setDirectoryid(dirupdatekey.getDirectoryid());
							//directory.setReticategoryid(dirupdatekey.getReticategoryid());
						}
						
						System.out.println(split[i] + " \n");
					}
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "null directory update key.");
				}
				
				
				if(!isEmptyoldintentname)
				{
					Intent intentold = intentMapper.findBydirectoryidandintentname(dirupdatekey.getDirectoryid(),intent.getOldintentname().trim());
					if(intentold == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid old intent update key.");
					}
					else
					{
						intent.setIntentid(intentold.getIntentid());
						intent.setIretquestionid(intentold.getIretquestionid());
					}
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "null old intent name/question update key.");
				}
			}
			
			
			String engAccesstoken ="";
			
			
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no engine data found.");
	        }
			
			String hprret = "";
			
			String jsonString = "";
			
			
			JSONObject postdata = new JSONObject();
			
			Intent intentlocal = intentMapper.findOne(intent.getIntentid());
			
			if(intentlocal == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid data.");
			}
			
			if(intent.getIretquestionid() <=0)
			{
				intent.setIretquestionid(intentlocal.getIretquestionid());
			}
	        
			
			
			
			int MaxIntent=0;
			
			prettyJsonString = this.saveRoocengineupdate(MaxIntent, intent, result, postret
					, retparID, 0, postretvoice, lastinsertuserid, ecMapper, 0
					, postmessage, prettyJsonString, gson, jp, postdata, 0
					, hprret, jsonString, intentMapper, engAccesstoken, MapView, dirparent);
			
	        
			
			
			log.info("updateintent data");
		}catch(PersistenceException  e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}

	@Override
	public List<Intent> findIntentdir(Integer directoryid) {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findIntentdir(directoryid);
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
	public Intent findIntentquestion(String question) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findIntentquestion(question);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Intent) ec;
	}

	
	public Intent deleteOnebak(Integer intentid) {
		System.out.println("intent delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		Intent econe = null;
		String postret = "";
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			econe = ecMapper.findOne(intentid);
			if(econe == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "intentid not valid");
			}
			
			String engAccesstoken ="";
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
	        }
			
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
			else if(!jsonObject.has("access_token"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
			}
			engAccesstoken = jsonObject.getString("access_token");
	        System.out.println("token is :" + jsonObject.getString("access_token"));
	        
	        
			
			postret = hpr.getUserchanneltoken(result.getApi()+"/api/faq/del/"+econe.getIretquestionid(), engAccesstoken);
			//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
			
			
			System.out.println("post return is : "+ postret);
			
			
			
			jsonObject = new JSONObject(postret);
			
			if(!jsonObject.has("STATUS"))
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "delete faq engine failed");
			}
			
			if(jsonObject.getBoolean("STATUS"))
			{
				ec = ecMapper.deleteOne(intentid);
			}
			else
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "delete faq engine failed");
			}
			
			
			log.info("delete intent data");
		}catch(PersistenceException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e){
			log.debug(e + "error delete intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	
	public Intent deleteOnebak030502019(Integer intentid) {
		System.out.println("intent delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		Intent econe = null;
		String postret = "";
		String postretlocal ="";
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			econe = ecMapper.findOne(intentid);
			if(econe == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "intentid not valid");
			}
			
			String engAccesstoken ="";
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
	        }
			
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
			else if(!jsonObject.has("access_token"))
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "engine access token not found.");
			}
			engAccesstoken = jsonObject.getString("access_token");
	        System.out.println("token is :" + jsonObject.getString("access_token"));
	        
	        
			
			postret = hpr.getUserchanneltoken(result.getApi()+"/api/faq/del/"+econe.getIretquestionid(), engAccesstoken);
			//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
			
			//delete tempat nya ridwan
			postretlocal = hpr.getUserchanneltoken(result.getLocalapi()+"/composer/intent/"+econe.getIretquestionid()+"/delete", engAccesstoken);
			//sampai sini delete tempat ridwan
			
			
			System.out.println("post return is : "+ postret);
			
			System.out.println("local post return is : "+ postretlocal);
			
			
			
			jsonObject = new JSONObject(postret);
			
			if(!jsonObject.has("STATUS"))
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "delete faq engine failed");
			}
			
			if(jsonObject.getBoolean("STATUS"))
			{
				ec = ecMapper.deleteOne(intentid);
			}
			else
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "delete faq engine failed");
			}
			
			
			log.info("delete intent data");
		}catch(PersistenceException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e){
			log.debug(e + "error delete intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	
	@Override
	public Intent deleteOne(Integer intentid) {
		System.out.println("intent delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		Intent econe = null;
		String postret = "";
		
		String postretlocal ="";
		String postretint="";
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			econe = ecMapper.findOne(intentid);
			if(econe == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "intentid not valid");
			}
			
			String engAccesstoken ="";
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
	        }
			
			String hprret = "";
			String jsonString = hprret;
	        JSONObject jsonObject;
			
			System.out.println(jsonString);
	        
	        
	        
	        Directory dirresult = new Directory();
			dirresult = dirservice.getViewbyfaq(this.tenantIdentifier,Integer.toString(intentid));
	        
			if(dirresult == null)
			{
				
				postretint = hpr.getUserchanneltoken(result.getApi()+"/clear/data/"+ econe.getIretquestionid() +"", engAccesstoken);
				postret = hpr.getUserchanneltoken(result.getApi()+"/intent/"+econe.getIretquestionid()+"/delete", engAccesstoken);
				
				//delete tempat nya ridwan
				postretlocal = hpr.getUserchanneltokenwithtenantid(result.getLocalapi()+"/composer/intent/"+econe.getIretquestionid()+"/delete", engAccesstoken,this.tenantIdentifier);
				//sampai sini delete tempat ridwan
				
				
				System.out.println("post return is : "+ postret);
				
				System.out.println("local post return is : "+ postretlocal);
				
				System.out.println(postretint);
				
				jsonObject = new JSONObject(postret);
				
				if(!jsonObject.has("STATUS"))
				{
					throw new CoreException(HttpStatus.NOT_MODIFIED, "delete faq engine failed");
				}
				
				if(jsonObject.getBoolean("STATUS"))
				{
					ec = ecMapper.deleteOne(intentid);
				}
				else
				{
					throw new CoreException(HttpStatus.NOT_MODIFIED, "delete faq engine failed");
				}
			}
			else
			{
				throw new CoreException(HttpStatus.FORBIDDEN, "delete intent not allowed");
			}
			
			
			
			
			log.info("delete intent data");
		}catch(PersistenceException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e){
			log.debug(e + "error delete intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	
	@Override
	public List<Intent> deletebyDirectory(Integer directoryid) {
		System.out.println("del int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		List<Intent> ecall = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ecall = ecMapper.findIntentdir(directoryid);
			if(ecall == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directoryid");
			}
			ec = ecMapper.deletebyDirectory(directoryid);
			log.info("del int data");
		}catch(PersistenceException e){
			log.debug(e + "error del int data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Intent> findIntentict() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findIntentict();
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
	public Intent findIntentiretquestionid(Integer iretquestionid) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findIntentiretquestionid(iretquestionid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Intent) ec;
	}

	@Override
	public Intent findBydirectoryidandintentname(Integer directoryid, String question) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Intent ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findBydirectoryidandintentname(directoryid,question);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Intent) ec;
	}

	@Override
	public String Savemaxintent(MaxIntent maxintent) {
		System.out.println("max intent save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			IntentMapper mlMapper = sqlSession.getMapper(IntentMapper.class);
			lastinsertuserid =mlMapper.Savemaxintent(maxintent);
			log.info("insertmax intent data");
		}catch(PersistenceException e){
			log.debug(e + "error get max intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public List<Intent> extractIntent() {
		System.out.println("int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.extractIntent();
			log.info("extractint data");
		}catch(PersistenceException e){
			log.debug(e + "error get intent data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public String Updateinternal(Intent intent) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			IntentMapper userMapper = sqlSession.getMapper(IntentMapper.class);
			//userrole = 
			//intent.setIsgenerated(0);
			lastinsertuserid =userMapper.Updateinternal(intent);
			
			log.debug("update id is : " + lastinsertuserid);
			
			log.info("updateintent data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public List<Intent> findIntentdirrecursive(Integer directoryid) {
		System.out.println("int List by dir recursive : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ec = ecMapper.findIntentdirrecursive(directoryid);
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
	public List<Intent> deletebyIntent(Integer intentid) {
		System.out.println("del int List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Intent> ec = null;
		Intent ecall = null;
		try{
			IntentMapper ecMapper = sqlSession.getMapper(IntentMapper.class);
			ecall = ecMapper.findOne(intentid);
			if(ecall == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid intentid");
			}
			
			ec = ecMapper.deletebyIntent(intentid);
			log.info("del int data");
		}catch(PersistenceException e){
			log.debug(e + "error del int data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	

	
	private String saveRoocenginecreate(int MaxIntent,Intent intent,EngineCredential result,String postret,int retparID
			,int retparVoiceID ,String postretvoice ,String lastinsertuserid,IntentMapper ecMapper
			,int retparMLID,String postmessage,String prettyJsonString,Gson gson,JsonParser jp
			,String engAccesstoken,DirectoryMapper dirMapper,Directory dirparent
			,Intent resultintentmax,String MapView)
	{
		try {
			
			
			
			JSONObject postdata = new JSONObject();
	        
			postdata.put("Authorization", engAccesstoken);
			postdata.put("id", MaxIntent);
			postdata.put("categoryId", retparID);
			postdata.put("name", intent.getQuestion());
			postdata.put("answer", intent.getAnswer());
			//postdata.put("category", retparID);
			//postdata.put("parentId", 0);
			postdata.put("map", MapView);
			//postdata.put("category", result.getRootcategory());

			if(dirparent.getCategorymode().equals("DRILLDOWN"))
			{
				postdata.put("visibleFromRoot",true);
			}
			else
			{
				postdata.put("visibleFromRoot",false);
			}
			
			JSONArray ja = new JSONArray();
			ja.put(postdata);
			
			postret = hpr.setPostData(result.getApi()+"/intent",postdata);
			
			System.out.println("internal directory id is : "+ intent.getDirectoryid());
			System.out.println("external directory id is : "+ retparID);
			
			System.out.println("post return is : "+ postret);
			
			int cntErr =0;
			String errMessage="";
			if(validatejson.isJSONValidarray(postret))
			{
				JSONArray jsonarray = new JSONArray(postret);
				
				
				for (int i = 0; i < jsonarray.length(); i++) {
				    JSONObject jsonobject = jsonarray.getJSONObject(i);
				    
				    if(!jsonobject.has("faqId"))
					{
				    	cntErr++;
				    	errMessage = jsonobject.getString("STATUS").toUpperCase();
				    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. - " + jsonobject.getString("STATUS"));
					}
				    
				    
				    if(jsonobject.has("faqId"))
				    {
				    	String faqId = jsonobject.getString("faqId");
					    //String url = jsonobject.getString("url");
						intent.setIretquestionid(Integer.parseInt(faqId));
					    if(Integer.parseInt(faqId)>0)
					    {


					    	JSONObject pdcomposer = new JSONObject();
					        
					    	pdcomposer.put("access_token", "");
					    	pdcomposer.put("externalId", faqId);
					    	pdcomposer.put("question", intent.getQuestion());
					    	pdcomposer.put("directoryId", intent.getDirectoryid());
					    	//pdcomposer.put("directoryParentId", dirparent.getParentid());
					    	pdcomposer.put("answer", intent.getAnswer());
					    	
					    	String locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/composer/intent",pdcomposer,this.tenantIdentifier,"");
							
							
							System.out.println("composer post return is : "+ locpostret);
							
							if(validatejson.isJSONValidstandard(locpostret))
							{
								JSONObject jsonObjectlocal = new JSONObject(locpostret);
								
								if(jsonObjectlocal.has("status"))
								{
									if(jsonObjectlocal.getString("status").equals("failed."))
									{
										intent.setIsgenerated(0);
									}
									else
									{
										intent.setIsgenerated(1);
									}
								}
								else
								{
									intent.setIsgenerated(0);
								}
							}
							else
							{
								intent.setIsgenerated(0);
							}
							
							
							intent.setIretquestionid(Integer.parseInt(faqId));

					    	lastinsertuserid =ecMapper.Saveengine(intent);
					    	
					    	postmessage = "{ 'type' : 'intent' ,'intentid' : " + lastinsertuserid + " }";
					    	JsonElement je = jp.parse(postmessage);
							prettyJsonString = gson.toJson(je);
					    	
					    	
					    }
					    else
					    {
					    	throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved.");
					    }
				    }
				    
				}
			}
			else
			{
				throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Something went wrong." );
			}
			
			if(cntErr>0)
			{
				throw new CoreException(HttpStatus.FAILED_DEPENDENCY, "Not Saved. - " + errMessage);
			}
			
		}catch(PersistenceException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}
		
		return prettyJsonString;
		
	}

	

	private String saveRoocengineupdate(int MaxIntent,Intent intent,EngineCredential result,String postret,int retparID
			,int retparVoiceID ,String postretvoice ,String lastinsertuserid,IntentMapper ecMapper
			,int retparMLID,String postmessage,String prettyJsonString,Gson gson,JsonParser jp
			,JSONObject postdata,int retparvoiceID,String hprret,String jsonString
			,IntentMapper intentMapper,String engAccesstoken,String MapView,Directory dirparent)
	{
		try {
			postdata.put("Authorization", engAccesstoken);
			postdata.put("id", intent.getIretquestionid());
			//postdata.put("categoryId", retparID);
			postdata.put("name", intent.getQuestion());
			postdata.put("answer", intent.getAnswer());
			//postdata.put("category", retparID);
			//postdata.put("parentId", 0);
			postdata.put("map", MapView);
			//postdata.put("category", result.getRootcategory());

			if(dirparent.getCategorymode().equals("DRILLDOWN"))
			{
				postdata.put("visibleFromRoot",true);
			}
			else
			{
				postdata.put("visibleFromRoot",false);
			}
			
			postret = hpr.setPostData(result.getApi()+"/intent/update",postdata);
			
			System.out.println("internal directory id is : "+ intent.getDirectoryid());
			System.out.println("external directory id is : "+ retparID);
			System.out.println("post return is : "+ postret);
			
			if(validatejson.isJSONValidstandard(postret))
			{
				JSONObject obj = new JSONObject(postret);
				if(obj.has("STATUS"))
				{
					System.out.println("STATUS is : "+obj.getBoolean("STATUS"));
					boolean isTrue = obj.getBoolean("STATUS");
					if(isTrue)
					{
						System.out.println("STATUS is : TRUE");
						String idparse = obj.getJSONObject("MESSAGE").getString("id");
						//boolean isTrue = obj.getBoolean("STATUS");
						
						//if(isTrue)
						//{
							if(Integer.parseInt(idparse)>0)
							{
								lastinsertuserid =intentMapper.Update(intent);
								
								postmessage = "{ 'type' : 'intent' ,'intentid' : " + lastinsertuserid + " }";
						    	JsonElement je = jp.parse(postmessage);
								prettyJsonString = gson.toJson(je);
							}
							else
						    {
						    	//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
								//postmessage = "{ 'message' : 'Not Saved' ,'type' : 'intent' ,'intentid' : " + intent.getIntentid() + " }";
								//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
								throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid data.");
						    }
						/*}
						else
					    {
					    	throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
					    }*/
					}
					else
					{
						System.out.println("STATUS is : False and message is :"+obj.getString("MESSAGE"));
						//postmessage = "{ 'message' : '"+obj.getString("MESSAGE")+"' ,'type' : 'intent' ,'intentid' : " + intent.getIntentid() + " }";
						//throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved. "+obj.getString("MESSAGE") +" ");
						String errMessage = obj.getString("MESSAGE");
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, errMessage);
					}
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Not Saved.");
				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Not Saved.");
			}
			
			//JsonElement je = jp.parse(postmessage);
			//prettyJsonString = gson.toJson(je);
			
			
			log.debug("update id is : " + lastinsertuserid);
			
			
		}catch(PersistenceException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}
		
		return prettyJsonString;
		
	}

	

	
}
