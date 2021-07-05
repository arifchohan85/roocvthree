package com.egeroo.roocvthree.directory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import com.egeroo.roocvthree.core.util.Util;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.Intent;
import com.egeroo.roocvthree.intent.IntentMapper;
import com.egeroo.roocvthree.intent.IntentService;
import com.egeroo.roocvthree.intent.MaxIntent;
import com.egeroo.roocvthree.interaction.Interaction;
import com.egeroo.roocvthree.interaction.InteractionMapper;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;






public class DirectoryMapperImpl extends BaseDAO implements DirectoryMapper{
	
	private static final Logger log = Logger.getLogger(DirectoryMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	private int ECID = 1;
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    IntentService intservice = new IntentService();
    ValidationJson validatejson = new ValidationJson();
    private String retDataPath;
    Util utility = new Util();
	
	public DirectoryMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Directory> findAll() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Directory> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findAll();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Directory> findDirectory() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Directory> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findDirectory();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Directory> findDirectoryenum() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Directory> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findDirectoryenum();
			log.info("getdir enum data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Directory findOne(Integer dirid) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findOne(dirid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}

	@Override
	public String Save(Directory directory) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		String postretvoice = "";
		String postretml = "";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		Directory dirparent = new Directory();
		Directory dirswitching =  new Directory();
		Directory dirswitchingparent = new Directory();
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			DirectoryMapper dirmapMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			String engAccesstoken ="";
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
	        }
			
			String hprret = "";
			String jsonString = "";
	        JSONObject jsonObject = null;
			
			/*
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
		    
			*/
	        
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
			
			
			
			
			//userrole = 
			//Map enumstring = new HashMap();
			//enumstring = null;
			//Map postdata = new HashMap();
			//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			JSONObject postdata = new JSONObject();
			JSONObject enumstring = new JSONObject();
			JSONObject faqstring = new JSONObject();
			int MaxIntent =0;
			if(directory.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = ecMapper.findOne(directory.getExtendenumcategoryid());
				if(direnum == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid enum data.");
				}
				int retEnumID = direnum.getReticategoryid();
				String enumName = direnum.getName();
				String enumDescription = direnum.getDescription();
				String enumCatmode = direnum.getCategorymode();
				
				Directory direnumparent = null;
				direnumparent = ecMapper.findOne(direnum.getParentid());
				int retEnumparID = direnumparent.getReticategoryid();
				
				enumstring.put("id", retEnumID);
				enumstring.put("name", enumName);
				enumstring.put("parentid", retEnumparID);
				enumstring.put("description", enumDescription);
				enumstring.put("faq", directory.getFaq());
				enumstring.put("mode", enumCatmode);
				enumstring.put("switching", directory.getSwitching());
				enumstring.put("extendsEnumCategory", "null");
			}
			
			if(directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || directory.getCategorymode().equals("DRILLDOWN") || directory.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			{
				enumstring = null;
			}
			
			
			//Directory dirparent = new Directory();
			
			Directory dirparentcount = ecMapper.findCountrootparent();
			int countrootcategory =0;
			
			countrootcategory = dirparentcount.getCountparent();
			
			
			
			
			int retparID = 0;
			int retParvoiceID=0;
			int retMLID=0;
			if(countrootcategory>0)
			{
				
				boolean isEmptymap = directory.getDirectorymap() == null || directory.getDirectorymap().trim().length() == 0;
				boolean isEmpty = directory.getParentname() == null || directory.getParentname().trim().length() == 0;
				
				if(!isEmptymap)
				{
					//dirparent = ecMapper.findParentbyname(directory.getParentname());
					String strMap = directory.getDirectorymap();
					String[] split = strMap.split("->");
					int getparentID =0 ;
					for (int i = 0; i < split.length; i++) {
						if(i==0)
						{
							//the first one query without parentid only name
							dirparent = ecMapper.findParentbyname(split[i].trim());
						}
						else
						{
							//not the first one query with parentid and name
							dirparent = ecMapper.findDirectorybynameandparentid(split[i],getparentID);
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
					dirparent = ecMapper.findParentbyname(directory.getParentname().trim());
				}
				else
				{
					dirparent = ecMapper.findOne(directory.getParentid());
				}
				
				
				if(dirparent == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
				}
				
				retparID = dirparent.getReticategoryid();
				retParvoiceID = dirparent.getRetvoiceid();
				retMLID = dirparent.getRetmlid();
				
				if(!isEmptymap)
				{
					directory.setParentid(dirparent.getDirectoryid());
				}
				else if(!isEmpty)
				{
					directory.setParentid(dirparent.getDirectoryid());
				}
			}
			
			
			
			
			
			
			System.out.println("extend enum is :" + directory.getExtendenumcategoryid());
			System.out.println("cat mode is :" + directory.getCategorymode());
			System.out.println("parent is :" + directory.getParentid());
			System.out.println("ret parent is :" + retparID);
			System.out.println("ret voice parent is :" + retParvoiceID);
			System.out.println("ret ml parent is :" + retMLID);
			
			if(directory.getExtendenumcategoryid()<=0)
			{
				enumstring = null;
			}
			
			
			MaxIntent =result.getInitialincrement();
			Intent resultintentmax = new Intent();
			resultintentmax = intservice.getMaxintent(this.tenantIdentifier);
			if(resultintentmax == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}

			if(resultintentmax.getMaxintentid() <=0)
			{
				//MaxIntent = MaxIntent;
			}
			else
			{
				MaxIntent = resultintentmax.getMaxintentid() +1;
			}
			
			
			this.retDataPath="";
			String Switching = "";
			String Switchingsingle = "";
			String getmapView = "";
			
			
			//boolean isEmptyswitching = directory.getSwitching() == null || directory.getSwitching().trim().length() == 0;
			//if(!isEmptyswitching)
			//{
				
			//}
			
			if(directory.getIntentid()>0 && directory.getSwitchingid()>0)
			{
				System.out.println("Querying switching parent");
				dirswitching = ecMapper.findOne(directory.getSwitchingid());
				
				dirswitchingparent = ecMapper.findOne(dirswitching.getDirectoryid());
				
				getmapView = this.mapView(directory.getSwitchingid(),dirmapMapper);
				getmapView = getmapView.replaceAll("null", "");
				getmapView = getmapView.replaceAll(" null", "");
				System.out.println("MapView is : " + getmapView);
				
				
				
				Switching = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
				
				Switchingsingle = "\""+directory.getIntentid()+"\" : \""+getmapView+"\"";
				
				
				directory.setSwitching(""+Switching);
				
				//process switching parent,new code,new method
				System.out.println("Switching is : " + Switching);
				System.out.println("Switching single is : " + Switchingsingle);
				
				System.out.print("Original Switching is :" + directory.getSwitching());
				System.out.print("Finding Parent ID for new Switching,for directory :" + dirswitching.getName());
				System.out.print("We Find only if parentid is > 0 , parentid is :" + dirswitching.getParentid());
				System.out.print("Parent Name is  : " + dirswitchingparent.getName() + " Parent id is : " +dirswitchingparent.getParentid());
				
				System.out.print("Reseting retDataPatah with current value   : " + this.retDataPath + " ");
				this.retDataPath="";
				System.out.print("Reseting getmapView with current value   : " + getmapView + " ");
				getmapView ="";
				String Switchingparent ="";
				String Switchingparentsingle ="";
				String Switchingcombine ="";
				
				boolean isEmptyswitching = Switching == null || Switching.trim().length() == 0;
				if(!isEmptyswitching)
				{
					if(dirswitching.getParentid()>0)
					{
						getmapView = this.mapView(dirswitching.getParentid(),dirmapMapper);
						getmapView = getmapView.replaceAll("null", "");
						getmapView = getmapView.replaceAll(" null", "");
						System.out.println("MapView is : " + getmapView);
						
						Switchingparent = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
						
						Switchingparentsingle = "\""+directory.getIntentid()+"\" : \""+getmapView+"\"";
						
						System.out.print("Parent Switching value is    : " + Switchingparent + " ");
						
						System.out.print("Parent Switching single value is    : " + Switchingparentsingle + " ");
					}
				}
				
				boolean isEmptyswitchingsingle = Switchingsingle == null || Switchingsingle.trim().length() == 0;
				boolean isEmptyswitchingparentsingle = Switchingparentsingle == null || Switchingparentsingle.trim().length() == 0;
				if(!isEmptyswitchingsingle)
				{
					if(!isEmptyswitchingparentsingle)
					{
						Switchingcombine = "{"+Switchingsingle+" , "+Switchingparentsingle+"}";
					}
					else
					{
						Switchingcombine = "{"+Switchingsingle+" "+"}";
					}
					
					directory.setSwitching(""+Switchingcombine);
					System.out.println("Switching after combine is : " + Switchingcombine);
				}
				//till here process switching parent,new code,new method
			}
			
			boolean isSBD = false;
			
			boolean isUpdate = true;
			Directory dirlocal = null;
			if(isUseicontek)
			{
				prettyJsonString = this.saveiContekCreate(result,isSBD , MaxIntent, ecMapper, dirmapMapper, postdata, directory
						, dirlocal, retparID, enumstring, faqstring, dirswitching, dirswitchingparent, isUpdate
						, postret, postretvoice, jsonObject, postmessage, retParvoiceID
						, retMLID, postretml, lastinsertuserid, prettyJsonString, gson, jp);
			}
			else
			{
				prettyJsonString = this.saveRoocengineCreate( result, isSBD , MaxIntent, ecMapper, dirmapMapper, postdata, directory
						, dirlocal, retparID, enumstring, faqstring, dirswitching, dirswitchingparent, isUpdate
						, postret, postretvoice, jsonObject, postmessage, retParvoiceID
						, retMLID, postretml, lastinsertuserid, prettyJsonString, gson, jp
						, engAccesstoken, dirparent);
			}
			
				
				
			//lastinsertuserid =ecMapper.Save(directory);
			
			//log.debug("insert id is : " + lastinsertuserid);
			
			log.info("insertdir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}

	@Override
	public String Update(Directory directory) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		String postretvoice = "";
		Intent saveintentnew = new Intent();
		String postmessage ="";
		String prettyJsonString ="";
		String postretml="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		Directory dirparent = new Directory();
		Directory dirupdatekey = new Directory();
		Directory dircurrent = new Directory();
		Directory dirswitching = new Directory();
		Directory dirswitchingparent = new Directory();
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			DirectoryMapper dirmapMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			String engAccesstoken ="";
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
	        }
			
			
			
			String hprret = "";
			String jsonString = "";
	        JSONObject jsonObject = null;
			/*
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
		    */
	        
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
			
			//userrole = 
			//Map enumstring = new HashMap();
			//enumstring = null;
			//Map postdata = new HashMap();
			//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			JSONObject postdata = new JSONObject();
			JSONObject enumstring = new JSONObject();
			JSONObject faqstring = new JSONObject();
			int MaxIntent =0;
			if(directory.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = ecMapper.findOne(directory.getExtendenumcategoryid());
				if(direnum == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid enum data.");
				}
				int retEnumID = direnum.getReticategoryid();
				String enumName = direnum.getName();
				String enumDescription = direnum.getDescription();
				String enumCatmode = direnum.getCategorymode();
				
				Directory direnumparent = null;
				direnumparent = ecMapper.findOne(direnum.getParentid());
				int retEnumparID = direnumparent.getReticategoryid();
				
				enumstring.put("id", retEnumID);
				enumstring.put("name", enumName);
				enumstring.put("parentid", retEnumparID);
				enumstring.put("description", enumDescription);
				enumstring.put("faq", directory.getFaq());
				enumstring.put("mode", enumCatmode);
				enumstring.put("switching", directory.getSwitching());
				enumstring.put("extendsEnumCategory", "null");
			}
			
			if(directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
			{
				enumstring = null;
			}
			
			
			//Directory dirparent = new Directory();
			boolean isEmptymap = directory.getDirectorymap() == null || directory.getDirectorymap().trim().length() == 0;
			boolean isEmpty = directory.getParentname() == null || directory.getParentname().trim().length() == 0;
			
			if(!isEmptymap)
			{
				//dirparent = ecMapper.findParentbyname(directory.getParentname());
				String strMap = directory.getDirectorymap();
				String[] split = strMap.split("->");
				int getparentID =0 ;
				for (int i = 0; i < split.length; i++) {
					if(i==0)
					{
						//the first one query without parentid only name
						dirparent = ecMapper.findParentbyname(split[i].trim());
					}
					else
					{
						//not the first one query with parentid and name
						dirparent = ecMapper.findDirectorybynameandparentid(split[i],getparentID);
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
				dirparent = ecMapper.findParentbyname(directory.getParentname().trim());
			}
			else
			{
				dirparent = ecMapper.findOne(directory.getParentid());
			}
			
			
			if(dirparent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
			}
			
			int retparID = dirparent.getReticategoryid();
			int retParvoiceID = dirparent.getRetvoiceid();
			int retMLID = dirparent.getRetmlid();
			
			if(!isEmptymap)
			{
				directory.setParentid(dirparent.getDirectoryid());
				directory.setReticategoryid(dirparent.getReticategoryid());
				//directory.setRetvoiceid(dirparent.getRetvoiceid());
			}
			else if(!isEmpty)
			{
				directory.setParentid(dirparent.getDirectoryid());
				directory.setReticategoryid(dirparent.getReticategoryid());
				//directory.setRetvoiceid(dirparent.getRetvoiceid());
			}
			
			boolean isEmptyupdatekey = directory.getUpdatekey() == null || directory.getUpdatekey().trim().length() == 0;
			if(!isEmptyupdatekey)
			{
				//dirparent = ecMapper.findParentbyname(directory.getParentname());
				String strMap = directory.getUpdatekey();
				String[] split = strMap.split("->");
				int getparentID =0 ;
				for (int i = 0; i < split.length; i++) {
					if(i==0)
					{
						//the first one query without parentid only name
						dirupdatekey = ecMapper.findParentbyname(split[i].trim());
					}
					else
					{
						//not the first one query with parentid and name
						dirupdatekey = ecMapper.findDirectorybynameandparentid(split[i],getparentID);
					}
					
					if(dirupdatekey==null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid directory update key.");
					}
					else
					{
						getparentID = dirupdatekey.getDirectoryid();
						directory.setDirectoryid(dirupdatekey.getDirectoryid());
						directory.setReticategoryid(dirupdatekey.getReticategoryid());
						directory.setRetvoiceid(dirupdatekey.getRetvoiceid());
						directory.setRetmlid(dirupdatekey.getRetmlid());
					}
					
					System.out.println("Voice id is :" + dirupdatekey.getRetvoiceid() + " \n");
				}
			}
			
			/*dircurrent = ecMapper.findOne(directory.getDirectoryid());
			if(dircurrent == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid current directory to update.");
			}*/
			
			
			
			
			System.out.println("extend enum is :" + directory.getExtendenumcategoryid());
			System.out.println("cat mode is :" + directory.getCategorymode());
			System.out.println("parent is :" + directory.getParentid());
			System.out.println("ret parent is :" + retparID);
			System.out.println("ret parent voice is :" + retParvoiceID);
			System.out.print("ML ID is :"+ retMLID);
			
			if(directory.getExtendenumcategoryid()<=0)
			{
				enumstring = null;
			}
			
			boolean isSBD = false;
			boolean isUpdate = true;
			Directory dirlocal = ecMapper.findOne(directory.getDirectoryid());
			
			if(dirlocal == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid data.");
			}
			
			if(directory.getReticategoryid() <=0 )
			{
				directory.setReticategoryid(dirlocal.getReticategoryid());
			}
			
			if(directory.getRetvoiceid()<=0)
			{
				directory.setRetvoiceid(dirlocal.getRetvoiceid());
			}
			
			if(directory.getRetmlid()<=0)
			{
				directory.setRetmlid(dirlocal.getRetmlid());
			}
			
			if(isUseicontek)
			{
				prettyJsonString = this.saveiContekUpdate(result, isSBD, MaxIntent, ecMapper, dirmapMapper, postdata, directory, dirlocal, retparID, enumstring, faqstring, dirswitching, dirswitchingparent
						, isUpdate, postret, postretvoice, jsonObject, postmessage, retParvoiceID, retMLID, postretml
						, lastinsertuserid, prettyJsonString, gson, jp, saveintentnew);
			}
			else
			{
				prettyJsonString = this.saveRoocengineUpdate(result, isSBD, MaxIntent, ecMapper, dirmapMapper, postdata, directory, dirlocal
						, retparID, enumstring, faqstring, dirswitching, dirswitchingparent, isUpdate
						, postret, postretvoice, jsonObject, postmessage, retParvoiceID, retMLID
						, postretml, lastinsertuserid, prettyJsonString, gson, jp
						, engAccesstoken, dirparent, saveintentnew);
			}
				
			
			log.info("updatedir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}

	@Override
	public Directorylistall findAlldirectory() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directorylistall ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findAlldirectory();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<DirectoryIntent> findAlldirectoryintent() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DirectoryIntent> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findAlldirectoryintent();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Directory findCountrootparent() {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findCountrootparent();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}

	@Override
	public List<Directory> deleteOne(Integer directoryid,String Token)
	{
		//1st query the faq
				System.out.println("dir List : " + this.tenantIdentifier);
				sqlSession = super.getInstance(this.tenantIdentifier).openSession();
				//List<Intent> intentdir = null;
				List<Intent> intentdirrecursive = null;
				List<Directory> deldir = null;
				Directory econe = null;
				String postret="";
				String postretvoice="";
				try{
					DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
					IntentMapper intMapper = sqlSession.getMapper(IntentMapper.class);
					InteractionMapper intrMapper = sqlSession.getMapper(InteractionMapper.class);
					String engAccesstoken ="";
					
					econe = ecMapper.findOne(directoryid);
					if(econe == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "directory id not valid");
					}
					
					EngineCredential result = new EngineCredential();
					result = ecservice.getView(this.tenantIdentifier,this.ECID);
					
					if (result == null) {
			            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
			        }
					
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
							//isUseicontek.parseBoolean("yes");
						}
						/*else
						{
							isUseicontek = false;
						}*/
					}
					
					String hprret = "";
					String jsonString = "";
			        JSONObject jsonObject;
					//ambil engine token
					/*try {
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
					*/
			        //mulai dari sini mencari intent id recursive
			        //1. query intent recursive
			        //2. tampung dalam list
			        //3. loop list nya,untuk ambil data nya 1 per 1.
			        //4. ke icontek buang intent tersebut,dalam respond
			        //5. jika success ,maka delete intent di db internal
			        //6. dan delete correspond intent pada interaction tersebut
			        //7. langsung delete recursive correspondent category
			        //8. panggil icontek untuk delete recursive
			        
			        //cari data intent secara recursive
					intentdirrecursive = intMapper.findIntentdirrecursive(directoryid);
					
					if(intentdirrecursive == null)
					{
						
					}
					else
					{
						String resgetApi = result.getApi();
						String resgetApivoice = result.getVoiceapi();
						String resgetApilocal = result.getLocalapi();
						String resgetApiml = result.getMlapi();
						String engToken = engAccesstoken;
						int isuseVoice = result.getIsusevoice();
						int isuseML = result.getIsusertsaml();
						boolean isuseicontekeng = isUseicontek;
						intentdirrecursive.forEach(item->{
							//List<Intent> intentdir = intMapper.deletebyDirectory(directoryid);
							String postretintent ="";
							String postretint ="";
							String postretintvoice ="";
							String postretlocal="";
							try {
								
								if(isuseicontekeng)
								{
									postretint = hpr.getUserchanneltoken(resgetApi+"/api/response/del/faqid/"+ item.getIretquestionid() +"", engToken);
									
									if(isuseVoice==1)
									{
										if(item.getRetvoiceid()<=0)
										{
											postretintvoice = hpr.getUserchanneltoken(resgetApivoice+"/api/response/del/faqid/"+ item.getIretquestionid() +"", engToken);
										}
										else
										{
											postretintvoice = hpr.getUserchanneltoken(resgetApivoice+"/api/response/del/faqid/"+ item.getRetvoiceid() +"", engToken);
										}
										
									}
									
									if(isuseML==1)
									{
										if(item.getRetmlid()<=0)
										{
											postretintvoice = hpr.getUserchanneltoken(resgetApiml+"/api/response/del/faqid/"+ item.getIretquestionid() +"", engToken);
										}
										else
										{
											postretintvoice = hpr.getUserchanneltoken(resgetApiml+"/api/response/del/faqid/"+ item.getRetmlid() +"", engToken);
										}
										
									}
								}
								else
								{
									//postretint = hpr.getUserchanneltoken(resgetApi+"/question/"+ item.getIretquestionid() +"/delete", engToken);
									postretint = hpr.getUserchanneltoken(resgetApi+"/clear/data/"+ item.getIretquestionid() +"", engToken);
									postretintent = hpr.getUserchanneltoken(resgetApi+"/intent/"+ item.getIretquestionid() +"/delete", engToken);
								}
								
								
								//delete tempat nya ridwan
								postretlocal = hpr.setGetlocaldata(resgetApilocal+"/composer/intent/"+item.getIretquestionid()+"/delete",this.tenantIdentifier, Token);
								//sampai sini delete tempat ridwan
								System.out.println(postretlocal);
								
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
							//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
							
							System.out.println("post return is : "+ postretint);
							
							JSONObject jsonObjectupdateexpid;
							
							jsonObjectupdateexpid = new JSONObject(postretint);
							
							if(!jsonObjectupdateexpid.has("STATUS"))
							{
								List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
								List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
								throw new CoreException(HttpStatus.NOT_MODIFIED, "delete intent engine with id : "+item.getIntentid()+" failed");
							}
							else
							{
								//if(jsonObjectupdateexpid.getBoolean("STATUS"))
								//{
									//List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
									//List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
								//}
								//else
								//{
									List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
									List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
								//}
								
							}
							
						});
					}
					//sampai sini mencari intent id recursive
					
					
					boolean allowDeletecat = false;
					//sekarang query yang category
					if(isUseicontek)
					{
						postret = hpr.getUserchanneltoken(result.getApi()+"/api/category/del/"+econe.getReticategoryid()+"/recursive", engAccesstoken);
						
						JSONObject jsonObjectdelcatrecursive;
						
						jsonObjectdelcatrecursive = new JSONObject(postret);
						
						if(!jsonObjectdelcatrecursive.has("id"))
						{
							throw new CoreException(HttpStatus.NOT_MODIFIED, "delete category engine failed");
						}
						else
						{
							allowDeletecat = true;
							
							if(isUseicontek)
							{
								if(result.getIsusevoice()==1)
								{
									if(econe.getRetvoiceid()>0)
									{
										postretvoice = hpr.getUserchanneltoken(result.getVoiceapi()+"/api/category/del/"+econe.getRetvoiceid()+"/recursive", engAccesstoken);
									}
									
								}
								
								if(result.getIsusertsaml()==1)
								{
									if(econe.getRetmlid()>0)
									{
										postretvoice = hpr.getUserchanneltoken(result.getMlapi()+"/api/category/del/"+econe.getRetmlid()+"/recursive", engAccesstoken);
									}
									
								}
							}
						}
					
					}
					else
					{
						//postret = hpr.getUserchanneltoken(result.getApi()+"/category/"+econe.getReticategoryid()+"/delete", engAccesstoken);
						//deldir = ecMapper.deleteRecursive(directoryid,Token);
						if(econe.getCategorymode().equals("STANDARD"))
						{
							postret = hpr.getUserchanneltoken(result.getApi()+"/category/"+econe.getReticategoryid()+"/delete", engAccesstoken);
						}
						allowDeletecat = true;
					}
					//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
					
					System.out.println("delete request return is : "+ postret);
					if(allowDeletecat)
					{
						deldir = ecMapper.deleteRecursive(directoryid,Token);
					}
					
					
					
					
					
					
					
					log.info("getdir data");
				}catch(PersistenceException e){
					log.debug(e + "error get dir data");
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
		return deldir;
	}
	
	public Directory deleteOneBak(Integer directoryid) {
		System.out.println("intent delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		Directory econe = null;
		List<Intent> intentdir = null;
		String postret = "";
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			IntentMapper intMapper = sqlSession.getMapper(IntentMapper.class);
			econe = ecMapper.findOne(directoryid);
			if(econe == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "directory id not valid");
			}
			
			if(econe.getParentid()<=0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "directory id not valid");
			}
			
			Directory dirchildcount = ecMapper.findCountchild(directoryid);
			int countchildcategory =0;
			
			countchildcategory = dirchildcount.getCountchild();
			if(countchildcategory > 0)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "directory child still exists");
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
	        
	        
			
			postret = hpr.getUserchanneltoken(result.getApi()+"/api/category/del/"+econe.getReticategoryid()+"/recursive", engAccesstoken);
			//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
			
			
			System.out.println("post return is : "+ postret);
			
			JSONObject jsonObjectretcatID;
			
			jsonObject = new JSONObject(postret);
			
			if(!jsonObject.has("id"))
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "delete category engine failed");
			}
			else
			{
				//ec = ecMapper.deleteOne(directoryid);
				
				intentdir = intMapper.findIntentdir(directoryid);
				if(intentdir ==null)
				{
					
				}
				else
				{
					intentdir = intMapper.deletebyDirectory(directoryid);
				}
			}
			
			
			
			
			log.info("delete dir data");
		}catch(PersistenceException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e){
			log.debug(e + "error delete dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Directory findDirectorybyname(String directoryname) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findDirectorybyname(directoryname);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}

	@Override
	public Directory findParentbyname(String parentname) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findParentbyname(parentname);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}

	@Override
	public Directory findDirectorybynameandparentid(String directoryname, Integer parentid) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findDirectorybynameandparentid(directoryname,parentid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}

	@Override
	public List<Directory> extractDirectory() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Directory> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.extractDirectory();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<Directory> deleteRecursive(Integer directoryid,String Token) {
		//1st query the faq
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		//List<Intent> intentdir = null;
		List<Intent> intentdirrecursive = null;
		List<Directory> deldir = null;
		Directory econe = null;
		String postret="";
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			IntentMapper intMapper = sqlSession.getMapper(IntentMapper.class);
			InteractionMapper intrMapper = sqlSession.getMapper(InteractionMapper.class);
			String engAccesstoken ="";
			
			econe = ecMapper.findOne(directoryid);
			if(econe == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "directory id not valid");
			}
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
	        }
			
			String hprret = "";
			
			//ambil engine token
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
			
	        //mulai dari sini mencari intent id recursive
	        //1. query intent recursive
	        //2. tampung dalam list
	        //3. loop list nya,untuk ambil data nya 1 per 1.
	        //4. ke icontek buang intent tersebut,dalam respond
	        //5. jika success ,maka delete intent di db internal
	        //6. dan delete correspond intent pada interaction tersebut
	        //7. langsung delete recursive correspondent category
	        //8. panggil icontek untuk delete recursive
	        
	        //cari data intent secara recursive
			intentdirrecursive = intMapper.findIntentdirrecursive(directoryid);
			
			if(intentdirrecursive == null)
			{
				
			}
			else
			{
				String resgetApi = result.getApi();
				String resgetApilocal = result.getLocalapi();
				String engToken = engAccesstoken;
				intentdirrecursive.forEach(item->{
					//List<Intent> intentdir = intMapper.deletebyDirectory(directoryid);
					String postretint ="";
					String postretlocal ="";
					try {
						postretint = hpr.getUserchanneltoken(resgetApi+"/api/response/del/faqid/"+ item.getIretquestionid() +"", engToken);
						
						//delete tempat nya ridwan
						postretlocal = hpr.setGetlocaldata(resgetApilocal+"/composer/intent/"+item.getIretquestionid()+"/delete",this.tenantIdentifier, Token);
						//sampai sini delete tempat ridwan
						
						System.out.println(postretlocal);
						
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
					//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
					
					System.out.println("post return is : "+ postretint);
					
					JSONObject jsonObjectupdateexpid;
					
					jsonObjectupdateexpid = new JSONObject(postretint);
					
					/*if(!jsonObjectupdateexpid.has("STATUS"))
					{
						throw new CoreException(HttpStatus.NOT_MODIFIED, "delete intent engine with id : "+item.getIntentid()+" failed");
					}
					else
					{
						if(jsonObjectupdateexpid.getBoolean("STATUS"))
						{
							List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
							List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
						}
						
					}*/
					if(!jsonObjectupdateexpid.has("STATUS"))
					{
						List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
						List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
						throw new CoreException(HttpStatus.NOT_MODIFIED, "delete intent engine with id : "+item.getIntentid()+" failed");
					}
					else
					{
						if(jsonObjectupdateexpid.getBoolean("STATUS"))
						{
							List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
							List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
						}
						else
						{
							List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
							List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
						}
						
					}
					
				});
			}
			//sampai sini mencari intent id recursive
			
			//sekarang query yang category
			postret = hpr.getUserchanneltoken(result.getApi()+"/api/category/del/"+econe.getReticategoryid()+"/recursive", engAccesstoken);
			//.setPostData(result.getApi()+"/api/category/del/"+econe.getReticategoryid(),postdata);
			
			
			System.out.println("post return is : "+ postret);
			
			JSONObject jsonObjectdelcatrecursive;
			
			jsonObjectdelcatrecursive = new JSONObject(postret);
			
			if(!jsonObjectdelcatrecursive.has("id"))
			{
				throw new CoreException(HttpStatus.NOT_MODIFIED, "delete category engine failed");
			}
			else
			{
				deldir = ecMapper.deleteRecursive(directoryid,Token);
			}
			
			
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
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
		return deldir;
	}

	@Override
	public Directory findCountchild(Integer directoryid) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findCountchild(directoryid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}
	
	private String mapView(int InitcatID,DirectoryMapper dirmapMapper)
	{
		Directory dirresult = dirmapMapper.findOne(InitcatID);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent data");
		}
		
		if(dirresult.getParentid() >0)
		{
			 //$this->createCatmapView($modelfindParentretID->ParentID);
			this.mapView(dirresult.getParentid(),dirmapMapper);
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
	public Directory findOnebyfaq(String faq) {
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Directory ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findOnebyfaq(faq);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Directory) ec;
	}

	@Override
	public List<Directory> findDirectorynotgenvoice() {
		System.out.println("dir findDirectorynotgenvoice : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Directory> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findDirectorynotgenvoice();
			log.info("get findDirectorynotgenvoice data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDirectorynotgenvoice data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public String Updatevoiceonly(Directory dir) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		try{
			
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			lastinsertuserid =ecMapper.Updatevoiceonly(dir);
			
			postmessage = "{ 'categorymode' : '"+ dir.getCategorymode() +"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
				
			
			log.info("updatedir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}
	
	
	
	private String validateUri(String link)
	{
		URL url = null;
		try {
			   url = new URL(link);
			   System.out.println(link);
			} catch(MalformedURLException e) {
			   e.printStackTrace();
			}

			URI uri = null;
			try{
			   uri = new URI(url.toString());
			   System.out.println("uri :" + uri);
			} catch(URISyntaxException e) {
			   try {
			        uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
			                      url.getPort(), url.getPath(), url.getQuery(), 
			                      url.getRef());
			   } catch(URISyntaxException e1) {
			        e1.printStackTrace();
			   }
			}
			
			try {
			   url = uri.toURL();
			   System.out.println("uri to url :" + url.toString());
			} catch(MalformedURLException e) {
			   e.printStackTrace();
			}
		return url.toString();
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

	@Override
	public String Updatemlrtsaonly(Directory dir) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		try{
			
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			lastinsertuserid =ecMapper.Updatemlrtsaonly(dir);
			
			postmessage = "{ 'categorymode' : '"+ dir.getCategorymode() +"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
				
			
			log.info("updatedir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}

	@Override
	public List<Directory> findDirectorynotgenml() {
		System.out.println("dir findDirectorynotgenml : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Directory> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findDirectorynotgenml();
			log.info("get findDirectorynotgenml data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDirectorynotgenml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public String Updatertsaonly(Directory dir) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		try{
			
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			lastinsertuserid =ecMapper.Updatertsaonly(dir);
			
			postmessage = "{ 'categorymode' : '"+ dir.getCategorymode() +"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
	    	JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
				
			
			log.info("Updatertsaonly data");
		}catch(PersistenceException e){
			log.debug(e + "error save Updatertsaonly data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}
	
	private String saveiContekCreate(EngineCredential result,boolean isSBD ,int MaxIntent,DirectoryMapper ecMapper,DirectoryMapper dirmapMapper,JSONObject postdata,Directory directory
			,Directory dirlocal,int retparID,JSONObject enumstring,JSONObject faqstring,Directory dirswitching,Directory dirswitchingparent,boolean isUpdate
			,String postret,String postretvoice,JSONObject jsonObject,String postmessage,int retParvoiceID
			,int retMLID,String postretml,String lastinsertuserid,String prettyJsonString,Gson gson,JsonParser jp)
	{
		try {
			if(directory.getCategorymode().equals("STANDARD") || directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("MEMORY"))
			{	
				
				//postdata.put("access_token", engAccesstoken);
				postdata.put("name", directory.getName());
				
				postdata.put("parentid", retparID);
				postdata.put("description", directory.getDescription());
				postdata.put("faq", directory.getFaq());
				postdata.put("mode", directory.getCategorymode());
				postdata.put("switching", directory.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);

			}
			else
			{
				isSBD = true;
				MaxIntent =result.getInitialincrement();
				Intent resultintentmax = new Intent();
				resultintentmax = intservice.getMaxintent(this.tenantIdentifier);
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}
				
				if(resultintentmax.getMaxintentid() <=0)
				{
					//MaxIntent = MaxIntent;
				}
				else
				{
					MaxIntent = resultintentmax.getMaxintentid() +1;
				}
				
				MaxIntent maxintent = new MaxIntent();
				maxintent.setIntentid(MaxIntent);
				maxintent.setCreatedby(directory.getCreatedby());
				maxintent.setUpdatedby(directory.getUpdatedby());
				maxintent.setCreatedtime(directory.getCreatedtime());
				maxintent.setUpdatedtime(directory.getUpdatedtime());
				String savemaxIntent = intservice.getCreatemax(this.tenantIdentifier,maxintent);
				System.out.println("Save Max Intent record is :" + savemaxIntent+ " , Max intent is :"+MaxIntent);
				
				directory.setFaq(String.valueOf(MaxIntent));
				
				boolean isEmptyquestion = directory.getQuestion() == null || directory.getQuestion().trim().length() == 0;
				if(isEmptyquestion)
				{
					directory.setQuestion(directory.getName());
					//directory.setAnswer(".");
					directory.setAnswer(directory.getDescription());
				}
				
				
				faqstring.put("id", MaxIntent);
				faqstring.put("question", directory.getQuestion());
				//faqstring.put("answer", directory.getAnswer());
				faqstring.put("answer", ".");
				//faqstring.put("attachments", enumDescription);
				
				
				//postdata.put("access_token", engAccesstoken);
				postdata.put("name", directory.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", directory.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", directory.getCategorymode());
				postdata.put("switching", directory.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);
			}
		
		System.out.println("enumstring :" + enumstring);
		System.out.println("faqstring :" + faqstring);
		
		
		try {
			postret = hpr.setPostData(result.getApi()+"/api/category/add",postdata);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("post return is : "+ postret);
		
			//JSONObject jsonObjectretcatID;
		
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
				directory.setReticategoryid(jsonObject.getInt("MESSAGE"));
			}
			else
		    {
		    	throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
		    }
			
			if(directory.getReticategoryid()>0)
			{
				if(result.getIsusevoice()==1)
				{
					
					postdata.put("parentid", retParvoiceID);
					postretvoice = hpr.setPostData(result.getVoiceapi()+"/api/category/add",postdata);
					
					if(validatejson.isJSONValidstandard(postretvoice))
					{
						System.out.println("post return is : "+ postretvoice);
						JSONObject jsonObjectvoice = new JSONObject(postretvoice);
						
						if(!jsonObjectvoice.getBoolean("STATUS"))
						{
							//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
							directory.setIsvoicegenerated(0);
							directory.setRetvoiceid(0);
						}
						else
						{
							directory.setIsvoicegenerated(1);
							directory.setRetvoiceid(jsonObjectvoice.getInt("MESSAGE"));
						}
					}
					else
					{
						directory.setIsvoicegenerated(0);
						directory.setRetvoiceid(0);
					}
					
					
				}
				else
				{
					directory.setIsvoicegenerated(0);
					directory.setRetvoiceid(0);
				}
				
				if(result.getIsusertsaml()==1)
				{
					if(isSBD && retMLID<=0)
					{
						//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+directory.getName()+" directory");
					}
					else
					{
						postdata.put("parentid", retMLID);
						postretml = hpr.setPostData(result.getMlapi()+"/api/category/add",postdata);
						
						if(validatejson.isJSONValidstandard(postretml))
						{
							System.out.println("post return is : "+ retMLID);
							JSONObject jsonObjectvoice = new JSONObject(postretml);
							
							if(!jsonObjectvoice.getBoolean("STATUS"))
							{
								//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
								directory.setIsmlgenerated(0);
								directory.setRetmlid(0);
							}
							else
							{
								directory.setIsmlgenerated(1);
								directory.setRetmlid(jsonObjectvoice.getInt("MESSAGE"));
							}
						}
						else
						{
							directory.setIsmlgenerated(0);
							directory.setRetmlid(0);
						}
					}
				}
				else
				{
					directory.setIsmlgenerated(0);
					directory.setRetmlid(0);
				}
				
				
				lastinsertuserid =ecMapper.Save(directory);
				if(isSBD)
				{
					
					Intent saveintentnew = new Intent();
					saveintentnew.setIntentid(MaxIntent);
					saveintentnew.setActive(1);
					saveintentnew.setAnswer(directory.getAnswer());
					saveintentnew.setQuestion(directory.getQuestion());
					saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
					saveintentnew.setIretquestionid(MaxIntent);
					
					saveintentnew.setIntentgroupid(directory.getIntentgroupid());
					saveintentnew.setSentimentid(directory.getSentimentid());
					saveintentnew.setDescription(directory.getDescription());
					
					saveintentnew.setCreatedby(directory.getCreatedby());
					saveintentnew.setUpdatedby(directory.getUpdatedby());
					saveintentnew.setCreatedtime(directory.getCreatedtime());
					saveintentnew.setUpdatedtime(directory.getUpdatedtime());
					
					JSONObject pdcomposer = new JSONObject();
			        
			    	pdcomposer.put("access_token", directory.getToken());
			    	pdcomposer.put("externalId", MaxIntent);
			    	pdcomposer.put("directoryId", lastinsertuserid);
			    	pdcomposer.put("directoryParentId", directory.getParentid());
			    	pdcomposer.put("question", saveintentnew.getQuestion());
			    	pdcomposer.put("answer", saveintentnew.getAnswer());
			    	
			    	String locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/composer/intent",pdcomposer,this.tenantIdentifier,directory.getToken());
					
					
					System.out.println("composer post return is : "+ locpostret);
					
					if(validatejson.isJSONValidstandard(locpostret))
					{
						JSONObject jsonObjectlocal = new JSONObject(locpostret);
						
						if(jsonObjectlocal.has("status"))
						{
							if(jsonObjectlocal.getString("status").equals("failed."))
							{
								saveintentnew.setIsgenerated(0);
							}
							else
							{
								saveintentnew.setIsgenerated(1);
							}
						}
						else
						{
							saveintentnew.setIsgenerated(0);
						}
					}
					else
					{
						saveintentnew.setIsgenerated(0);
					}
					
					
					intservice.getCreate(this.tenantIdentifier,saveintentnew);
				}
				
				postmessage = "{ 'categorymode' : '"+directory.getCategorymode()+"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
		    	JsonElement je = jp.parse(postmessage);
				prettyJsonString = gson.toJson(je);
			}
			else
		    {
		    	throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
		    }
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return prettyJsonString;
		
	}
	
	private String saveRoocengineCreate(EngineCredential result,boolean isSBD ,int MaxIntent,DirectoryMapper ecMapper,DirectoryMapper dirmapMapper,JSONObject postdata,Directory directory
			,Directory dirlocal,int retparID,JSONObject enumstring,JSONObject faqstring,Directory dirswitching,Directory dirswitchingparent,boolean isUpdate
			,String postret,String postretvoice,JSONObject jsonObject,String postmessage,int retParvoiceID
			,int retMLID,String postretml,String lastinsertuserid,String prettyJsonString,Gson gson,JsonParser jp
			,String engAccesstoken,Directory dirparent)
	{
		try {
			
			this.retDataPath ="";
			String getmap = "";
			if(directory.getParentid()>0)
			{
				getmap = this.mapView(directory.getParentid(),ecMapper);
			}
			else
			{
				//default ROOT
				getmap = "";//directory.getName();
				//getmap = "ROOT";
			}
			getmap = getmap.replaceAll("null", "");
			getmap = getmap.replaceAll(" null", "");
			System.out.println("MapView is : " + getmap);

			postdata.put("map",getmap);

			postdata.put("Authorization", engAccesstoken);
			//postdata.put("parent_id", retparID);
			

			postdata.put("name", directory.getName());

			//postdata.put("parentid", retparID);
			postdata.put("description", directory.getDescription());

			postdata.put("mode", directory.getCategorymode());
			//postdata.put("switching", directory.getSwitching());
			postdata.put("goTo", directory.getSwitching());
			postdata.put("extendsEnumCategory", enumstring);
			
			int retdirectoryid =0;
			boolean isAllowSave= false;
			Intent saveintentnew = new Intent();
			if(directory.getCategorymode().equals("STANDARD") || directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("MEMORY"))
			{
				isSBD = false;
				postret = hpr.setPostData(result.getApi()+"/category",postdata);


				System.out.println("post return is : "+ postret);

				//JSONObject jsonObjectretcatID;

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
					//directory.setReticategoryid(jsonObject.getInt("MESSAGE"));
					retdirectoryid = jsonObject.getInt("MESSAGE");
					isAllowSave= true;
				}
				else {
					throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
				}
			}
			else
			{
				isSBD = true;
				directory.setFaq(String.valueOf(MaxIntent));
			}
			directory.setReticategoryid(retdirectoryid);
			
			
			

			System.out.println("enumstring :" + enumstring);
			System.out.println("faqstring :" + faqstring);
			
			if(isSBD)
			{


				MaxIntent maxintent = new MaxIntent();
				maxintent.setIntentid(MaxIntent);
				maxintent.setCreatedby(directory.getCreatedby());
				maxintent.setUpdatedby(directory.getUpdatedby());
				maxintent.setCreatedtime(directory.getCreatedtime());
				maxintent.setUpdatedtime(directory.getUpdatedtime());
				String savemaxIntent = intservice.getCreatemax(this.tenantIdentifier,maxintent);
				System.out.println("Save Max Intent record is :" + savemaxIntent+ " , Max intent is :"+MaxIntent);



				boolean isEmptyquestion = directory.getQuestion() == null || directory.getQuestion().trim().length() == 0;
				if(isEmptyquestion)
				{
					directory.setQuestion(directory.getName());
					directory.setAnswer(directory.getDescription());
				}

				directory.setFaq(String.valueOf(MaxIntent));
				postdata.put("faq", MaxIntent);

				faqstring.put("id", MaxIntent);
				faqstring.put("question", directory.getQuestion());
				faqstring.put("answer", directory.getAnswer());

				directory.setReticategoryid(0);

				
				saveintentnew.setIntentid(MaxIntent);
				saveintentnew.setActive(1);
				saveintentnew.setAnswer(directory.getAnswer());
				saveintentnew.setQuestion(directory.getQuestion());
				//saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
				saveintentnew.setIretquestionid(MaxIntent);

				saveintentnew.setIntentgroupid(directory.getIntentgroupid());
				saveintentnew.setSentimentid(directory.getSentimentid());
				saveintentnew.setDescription(directory.getDescription());

				saveintentnew.setCreatedby(directory.getCreatedby());
				saveintentnew.setUpdatedby(directory.getUpdatedby());
				saveintentnew.setCreatedtime(directory.getCreatedtime());
				saveintentnew.setUpdatedtime(directory.getUpdatedtime());



				JSONObject postdataintent = new JSONObject();

				postdataintent.put("Authorization", engAccesstoken);
				postdataintent.put("id", MaxIntent);
				//postdata.put("categoryid", retparID);
				postdataintent.put("name", directory.getQuestion());
				postdataintent.put("answer", directory.getAnswer());
				//postdata.put("answer", ".");
				//postdataintent.put("categoryId", retparID);
				//postdataintent.put("parentId", 0);
				postdataintent.put("map", getmap);
				//postdata.put("category", result.getRootcategory());
				//postdataintent.put("switching", directory.getSwitching());
				postdataintent.put("goTo", directory.getSwitching());
				
				int parentintentid=0;
				boolean isEmptyfaqParent = dirparent.getFaq() == null || dirparent.getFaq().trim().length() == 0;
				if(isEmptyfaqParent)
				{
					//postdataintent.put("parentId", 0);
					parentintentid=0;
				}
				else
				{
					//postdataintent.put("parentId", Integer.parseInt(dirparent.getFaq()));
					parentintentid=Integer.parseInt(dirparent.getFaq());
				}
				//faqstring.put("attachments", enumDescription);

				postdataintent.put("parentId", parentintentid);
				saveintentnew.setParentid(parentintentid);

				/*if(directory.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
				{
					//postdataintent.put("visibleFromRoot",false);
					if(dirparent.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
					{

					}
				}
				else if(directory.getCategorymode().equals("DRILLDOWN"))
				{
					postdataintent.put("visibleFromRoot",true);
				}*/

				if(dirparent.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
				{
					postdataintent.put("visibleFromRoot",false);
				}
				else if(dirparent.getCategorymode().equals("STANDARD"))
				{
					if(directory.getCategorymode().equals("DRILLDOWN"))
					{
						postdataintent.put("visibleFromRoot",true);
					}
					else
					{
						postdataintent.put("visibleFromRoot",false);
					}
				}
				else if(dirparent.getCategorymode().equals("DRILLDOWN"))
				{
					postdataintent.put("visibleFromRoot",true);
				}

				JSONArray ja = new JSONArray();
				ja.put(postdataintent);

				postret = hpr.setPostData(result.getApi()+"/intent",postdataintent);

				System.out.println("Post return for intent branching is : \n" + postret);
				
				 JSONArray jsonarray = new JSONArray(postret);
					for (int i = 0; i < jsonarray.length(); i++) {
					    JSONObject jsonobject = jsonarray.getJSONObject(i);
					    if(jsonobject.has("status")) 
					    {
					    	if(jsonobject.getBoolean("status"))
						    {
						    	isAllowSave = true;
						    }
						    else
						    {
						    	isAllowSave = false;
						    }
					    }
					   
					}

				/*if(isAllowSave)
				{
					lastinsertuserid =ecMapper.Save(directory);
					saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
					intservice.getCreate(this.tenantIdentifier,saveintentnew);
				}*/
				
			}
			
			if(isAllowSave)
			{
				lastinsertuserid =ecMapper.Save(directory);
				if(isSBD)
				{
					//lastinsertuserid =ecMapper.Save(directory);
					saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
					
					JSONObject pdcomposer = new JSONObject();

					pdcomposer.put("access_token", directory.getToken());
					pdcomposer.put("externalId", MaxIntent);
					pdcomposer.put("directoryId", lastinsertuserid);
					pdcomposer.put("directoryParentId", directory.getParentid());
					pdcomposer.put("question", saveintentnew.getQuestion());
					pdcomposer.put("answer", saveintentnew.getAnswer());

					String locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/composer/intent",pdcomposer,this.tenantIdentifier,directory.getToken());


					System.out.println("composer post return is : "+ locpostret);

					if(validatejson.isJSONValidstandard(locpostret))
					{
						JSONObject jsonObjectlocal = new JSONObject(locpostret);

						if(jsonObjectlocal.has("status"))
						{
							if(jsonObjectlocal.getString("status").equals("failed."))
							{
								saveintentnew.setIsgenerated(0);
							}
							else
							{
								saveintentnew.setIsgenerated(1);
							}
						}
						else
						{
							saveintentnew.setIsgenerated(0);
						}
					}
					else
					{
						saveintentnew.setIsgenerated(0);
					}
					
					intservice.getCreate(this.tenantIdentifier,saveintentnew);
				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Cannot save directory or Intent Branching/Drilldown");
			}
				


			postmessage = "{ 'categorymode' : '"+directory.getCategorymode()+"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
			JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
			
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}/*finally{
			sqlSession.close();
		}*/
		return prettyJsonString;
	}
	
	private String saveiContekUpdate(EngineCredential result,boolean isSBD ,int MaxIntent,DirectoryMapper ecMapper,DirectoryMapper dirmapMapper,JSONObject postdata,Directory directory
			,Directory dirlocal,int retparID,JSONObject enumstring,JSONObject faqstring,Directory dirswitching,Directory dirswitchingparent,boolean isUpdate
			,String postret,String postretvoice,JSONObject jsonObject,String postmessage,int retParvoiceID
			,int retMLID,String postretml,String lastinsertuserid,String prettyJsonString,Gson gson,JsonParser jp,Intent saveintentnew)
	{
		try {
			
			if(directory.getCategorymode().equals("STANDARD") || directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("MEMORY"))
			{	
				isSBD = false;
				//postdata.put("access_token", engAccesstoken);
				postdata.put("id", directory.getReticategoryid());
				postdata.put("name", directory.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", directory.getDescription());
				postdata.put("faq", directory.getFaq());
				postdata.put("mode", directory.getCategorymode());
				postdata.put("switching", directory.getSwitching());
				postdata.put("extendsEnumCategory", enumstring);

			}
			else
			{
				isSBD = true;
				MaxIntent =result.getInitialincrement();
				Intent resultintentmax = new Intent();
				resultintentmax = intservice.getMaxintent(this.tenantIdentifier);
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}
				
				if(resultintentmax.getMaxintentid() <=0)
				{
					//MaxIntent = MaxIntent;
				}
				else
				{
					MaxIntent = resultintentmax.getMaxintentid() +1;
				}
				
				this.retDataPath="";
				String Switching = "";
				String getmapView = "";
				String Switchingsingle = "";
				String Switchingcombine ="";
				if(directory.getIntentid()>0 && directory.getSwitchingid()>0)
				{
					System.out.println("Querying switching parent");
					dirswitching = ecMapper.findOne(directory.getSwitchingid());
					dirswitchingparent = ecMapper.findOne(dirswitching.getDirectoryid());
					
					getmapView = this.mapView(directory.getSwitchingid(),dirmapMapper);
					getmapView = getmapView.replaceAll("null", "");
					getmapView = getmapView.replaceAll(" null", "");
					System.out.println("MapView is : " + getmapView);
					
					//String Switching = "'"+directory.getIntentid()+"' : '"+getmapView+"'";
					
					//String key = " \""+directory.getIntentid()+"\" ";
					//String value = " \""+getmapView+"\" ";
					
					//String getmapView = "Root->Child";
					//String key = "27001";
					
					Switching = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
					//System.out.println("Switching is :" +Switching);
					Switchingsingle = "\""+directory.getIntentid()+"\" : \""+getmapView+"\"";
					
					//JSONObject switching = new JSONObject();
					//switching.put(""+Integer.toString(directory.getIntentid()), getmapView);
					
					directory.setSwitching(""+Switching);
					System.out.println("Switching is : " + Switching);
					
					//process switching parent,new code,new method
					System.out.println("Switching is : " + Switching);
					System.out.println("Switching single is : " + Switchingsingle);
					
					System.out.print("Original Switching is :" + directory.getSwitching());
					System.out.print("Finding Parent ID for new Switching,for directory :" + dirswitching.getName());
					System.out.print("We Find only if parentid is > 0 , parentid is :" + directory.getParentid());
					System.out.print("Parent  is  : " + dirswitchingparent.getName() + " Parent Name is : " +dirswitchingparent.getParentid());
					
					System.out.print("Reseting retDataPatah with current value   : " + this.retDataPath + " ");
					this.retDataPath="";
					System.out.print("Reseting getmapView with current value   : " + getmapView + " ");
					getmapView ="";
					String Switchingparent ="";
					String Switchingparentsingle ="";
					
					
					boolean isEmptyswitching = Switching == null || Switching.trim().length() == 0;
					if(!isEmptyswitching)
					{
						if(dirswitching.getParentid()>0)
						{
							getmapView = this.mapView(dirswitching.getParentid(),dirmapMapper);
							getmapView = getmapView.replaceAll("null", "");
							getmapView = getmapView.replaceAll(" null", "");
							System.out.println("MapView is : " + getmapView);
							
							Switchingparent = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
							
							Switchingparentsingle = "\""+directory.getIntentid()+"\" : \""+getmapView+"\"";
							
							System.out.print("Parent Switching value is    : " + Switchingparent + " ");
							
							System.out.print("Parent Switching single value is    : " + Switchingparentsingle + " ");
						}
					}
					
					boolean isEmptyswitchingsingle = Switchingsingle == null || Switchingsingle.trim().length() == 0;
					boolean isEmptyswitchingparentsingle = Switchingparentsingle == null || Switchingparentsingle.trim().length() == 0;
					if(!isEmptyswitchingsingle)
					{
						if(!isEmptyswitchingparentsingle)
						{
							Switchingcombine = "{"+Switchingsingle+" , "+Switchingparentsingle+"}";
						}
						else
						{
							Switchingcombine = "{"+Switchingsingle+"}";
						}
						
						directory.setSwitching(""+Switchingcombine);
						System.out.println("Switching after combine is : " + Switchingcombine);
					}
					//till here process switching parent,new code,new method
				}
				
				
				
				
				
				
				boolean isEmptyfaq = dirlocal.getFaq() == null || dirlocal.getFaq().trim().length() == 0;
				
				if(!isEmptyfaq)
				{
					if(Integer.parseInt(dirlocal.getFaq())>0)
					{
						Intent modelFaq =  intservice.getView(this.tenantIdentifier, Integer.parseInt(dirlocal.getFaq()));
						System.out.println("intent id is : " + dirlocal.getFaq());
						if(modelFaq == null)
						{
							isUpdate=false;
							saveintentnew.setIntentid(MaxIntent);
							saveintentnew.setIretquestionid(MaxIntent);
							saveintentnew.setCreatedby(directory.getCreatedby());
							saveintentnew.setCreatedtime(directory.getUpdatedtime());
							//saveintentnew.setUpdatedby(directory.getCreatedby());
							//saveintentnew.setUpdatedtime(directory.getUpdatedtime());
							
						}
						else
						{
							isUpdate=true;
							MaxIntent = modelFaq.getIntentid();
							directory.setFaq(String.valueOf(MaxIntent));
							saveintentnew.setIntentid(modelFaq.getIntentid());
							saveintentnew.setIretquestionid(modelFaq.getIntentid());
						}
					}
					else
					{
						isUpdate=false;
						saveintentnew.setIntentid(MaxIntent);
						saveintentnew.setIretquestionid(MaxIntent);
						saveintentnew.setCreatedby(directory.getCreatedby());
						saveintentnew.setCreatedtime(directory.getUpdatedtime());
						
					}
				}
				else
				{
					isUpdate=false;
					saveintentnew.setIntentid(MaxIntent);
					saveintentnew.setIretquestionid(MaxIntent);
					saveintentnew.setCreatedby(directory.getCreatedby());
					saveintentnew.setCreatedtime(directory.getUpdatedtime());
					
					//saveintentnew.setUpdatedby(directory.getCreatedby());
					//saveintentnew.setUpdatedtime(directory.getUpdatedtime());
				}
				
				
				boolean isEmptyquestion = directory.getQuestion() == null || directory.getQuestion().trim().length() == 0;
				if(isEmptyquestion)
				{
					directory.setQuestion(directory.getName());
					directory.setAnswer(".");
				}
				
				
				//directory.setFaq(String.valueOf(MaxIntent));
				
				faqstring.put("id", MaxIntent);
				faqstring.put("question", directory.getQuestion());
				//faqstring.put("answer", directory.getAnswer());
				faqstring.put("answer", ".");
				//faqstring.put("attachments", enumDescription);
				
				
				//postdata.put("access_token", engAccesstoken);
				postdata.put("id", directory.getReticategoryid());
				postdata.put("name", directory.getName());
				postdata.put("parentid", retparID);
				postdata.put("description", directory.getDescription());
				postdata.put("faq", faqstring);
				postdata.put("mode", directory.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				//postdata.put("switching", Switching);
				postdata.put("switching", Switchingcombine);
				postdata.put("extendsEnumCategory", enumstring);
			}
			
			System.out.println("enumstring :" + enumstring);
			System.out.println("faqstring :" + faqstring);
			
			
			postret = hpr.setPostData(result.getApi()+"/api/category/update",postdata);
			
			
			System.out.println("post return is : "+ postret);
			
			//JSONObject jsonObjectretcatID;
			
				jsonObject = new JSONObject(postret);
				
				if(!jsonObject.getBoolean("STATUS"))
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "update engine failed");
				}
				
				JSONObject jsonChildObject = (JSONObject)jsonObject.get("MESSAGE");
				
				
				
				System.out.println("post return is : "+ postret);
				
				
				if(jsonChildObject.getInt("id") <=0 )
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, postret);
				}
				
				
				
				
				if(jsonObject.getBoolean("STATUS"))
				{
					//directory.setReticategoryid(jsonObject.getInt("MESSAGE"));
					directory.setReticategoryid(jsonChildObject.getInt("id"));
				}
				else
			    {
			    	throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
			    }
				
				if(directory.getReticategoryid()>0)
				{
					
					if(result.getIsusevoice()==1)
					{
						
						postdata.put("parentid", retParvoiceID);
						System.out.print("parent voice icontek voice is : " + retParvoiceID);
						//check jika data memang ada
						String hprretcheckdataexist = "";
						String postretvoicecheck = "";
						//String voiceapi = result.getVoiceapi();
						this.retDataPath ="";
						String getmap = this.mapView(directory.getDirectoryid(),dirmapMapper);
						String validUri = validateUri(result.getVoiceapi()+"/api/category/get/"+getmap);
						System.out.print("validUri :" +validUri);
						try {
							hprretcheckdataexist = hpr.ConnectGetnoparam(validUri);
						}catch(Exception ex)
						{
							System.out.println(ex);
						}
						System.out.print("get return :" +hprretcheckdataexist);
						String jsonStringcheckdataexist = hprretcheckdataexist;
				        JSONObject jsonObjectcheckdataexist;
				        JSONObject jsonObjectaddcheck;
				        if(validatejson.isJSONValidstandard(jsonStringcheckdataexist))
				        {
				        	jsonObjectcheckdataexist = new JSONObject(jsonStringcheckdataexist);
							if(jsonObjectcheckdataexist.getString("name") ==null)
							{
								//data tidak ada maka harus insert lagi baru
								postretvoicecheck = hpr.setPostData(result.getVoiceapi()+"/api/category/add",postdata);
								System.out.println("post return is : "+ postretvoicecheck);
								
								if(validatejson.isJSONValidstandard(postretvoicecheck))
						        {
									jsonObjectaddcheck = new JSONObject(postretvoicecheck);
									
									
									
									if(jsonObjectaddcheck.getBoolean("STATUS"))
									{
										directory.setRetvoiceid(jsonObject.getInt("MESSAGE"));
										directory.setIsvoicegenerated(1);
									}
									else
									{
										directory.setRetvoiceid(0);
										directory.setIsvoicegenerated(0);
									}
						        }
								else
								{
									directory.setRetvoiceid(0);
									directory.setIsvoicegenerated(0);
								}
							}
							else
							{
								
								postdata.put("id", directory.getRetvoiceid());
								System.out.print("updating voice for id is :"+ directory.getRetvoiceid());
								postretvoice = hpr.setPostData(result.getVoiceapi()+"/api/category/update",postdata);
								
								
								System.out.println("post return is : "+ postretvoice);
								
								
								if(validatejson.isJSONValidstandard(postretvoice))
						        {
									JSONObject jsonObjectvoice = new JSONObject(postretvoice);
									if(!jsonObjectvoice.getBoolean("STATUS"))
									{
										//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
										if(directory.getIsvoicegenerated()<=0)
										{
											directory.setIsvoicegenerated(0);
											directory.setRetvoiceid(0);
										}
									}
									else
									{
										directory.setIsvoicegenerated(1);
										if(jsonObjectvoice.has("MESSAGE"))
										{
											JSONObject insmessage = (JSONObject)jsonObjectvoice.getJSONObject("MESSAGE");
											if(insmessage.has("id"))
											{
												if(insmessage.getInt("id")<=0)
												{
													//directory.setRetvoiceid(0);
													//directory.setIsvoicegenerated(0);
												}
												else
												{
													directory.setRetvoiceid(insmessage.getInt("id"));
													directory.setIsvoicegenerated(1);
												}
												
												
											}
											//else
											//{
												//directory.setRetvoiceid(jsonObjectvoice.getInt("MESSAGE"));
											//}
										}
										
									}
						        }
							}
				        }
				        else
				        {
				        	postretvoicecheck = hpr.setPostData(result.getVoiceapi()+"/api/category/add",postdata);
							System.out.println("post return is : "+ postretvoicecheck);
							
							if(validatejson.isJSONValidstandard(postretvoicecheck))
					        {
								jsonObjectaddcheck = new JSONObject(postretvoicecheck);
								
								
								
								if(jsonObjectaddcheck.getBoolean("STATUS"))
								{
									directory.setRetvoiceid(jsonObject.getInt("MESSAGE"));
									directory.setIsvoicegenerated(1);
								}
								else
								{
									directory.setRetvoiceid(0);
									directory.setIsvoicegenerated(0);
								}
					        }
				        }
				        //sampai sini check data
						
						
					}
					else
					{
						directory.setIsvoicegenerated(0);
						directory.setRetvoiceid(0);
					}
					
					
					if(result.getIsusertsaml()==1)
					{
						
						
						postdata.put("parentid", retMLID);
						System.out.print("parent ML icontek is : " + retMLID);
						//check jika data memang ada
						String hprretcheckdataexist = "";
						String postretvoicecheck = "";
						//String voiceapi = result.getVoiceapi();
						this.retDataPath ="";
						String getmap = this.mapView(directory.getDirectoryid(),dirmapMapper);
						String validUri = validateUri(result.getMlapi()+"/api/category/get/"+getmap);
						System.out.print("validUri :" +validUri);
						try {
							hprretcheckdataexist = hpr.ConnectGetnoparam(validUri);
						}catch(Exception ex)
						{
							System.out.println(ex);
						}
						System.out.print("get return :" +hprretcheckdataexist);
						String jsonStringcheckdataexist = hprretcheckdataexist;
				        JSONObject jsonObjectcheckdataexist;
				        JSONObject jsonObjectaddcheck;
				        
				        directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
				        directory.setRetmlid(dirlocal.getRetmlid());
				        
				        if(validatejson.isJSONValidstandard(jsonStringcheckdataexist))
				        {
				        	//postdata.put("id", directory.getRetmlid());
							//postdata.put("id", dirlocal.getRetmlid());
				        	jsonObjectcheckdataexist = new JSONObject(jsonStringcheckdataexist);
							if(jsonObjectcheckdataexist.getString("name") ==null)
							{
								if(isSBD && retMLID<=0)
								{
									//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+directory.getName()+" directory");
									System.out.print("cannot update : "+directory.getName()+" directory");
								}
								else
								{
									//data tidak ada maka harus insert lagi baru
									postretvoicecheck = hpr.setPostData(result.getMlapi()+"/api/category/add",postdata);
									System.out.println("post return is : "+ postretvoicecheck);
									
									if(validatejson.isJSONValidstandard(postretvoicecheck))
							        {
										jsonObjectaddcheck = new JSONObject(postretvoicecheck);
										
										
										
										if(jsonObjectaddcheck.getBoolean("STATUS"))
										{
											directory.setRetmlid(jsonObject.getInt("MESSAGE"));
											directory.setIsmlgenerated(1);
										}
										else
										{
											//directory.setRetmlid(0);
											//directory.setIsmlgenerated(0);
											if(dirlocal.getRetmlid()>0)
											{
												directory.setRetmlid(dirlocal.getRetmlid());
												directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
											}
											else
											{
												directory.setIsmlgenerated(0);
												directory.setRetmlid(0);
											}
											
										}
							        }
									else
									{
										//directory.setRetmlid(0);
										//directory.setIsmlgenerated(0);
										if(dirlocal.getRetmlid()>0)
										{
											directory.setRetmlid(dirlocal.getRetmlid());
											directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
										}
										else
										{
											directory.setIsmlgenerated(0);
											directory.setRetmlid(0);
										}
									}
								}
								
							}
							else
							{
								directory.setRetmlid(jsonObjectcheckdataexist.getInt("id"));
								directory.setIsmlgenerated(1);
								//postdata.put("id", directory.getRetmlid());
								postdata.put("id", directory.getRetmlid());
								//System.out.print("updating ML for id is :"+ directory.getRetmlid());
								System.out.print("updating ML for id is :"+ directory.getRetmlid());
								
								if(isSBD && retMLID<=0)
								{
									//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+directory.getName()+" directory");
									System.out.print("cannot update : "+directory.getName()+" directory");
								}
								else
								{
									postretvoice = hpr.setPostData(result.getMlapi()+"/api/category/update",postdata);
									
									System.out.println("post return is : "+ postretvoice);
									if(validatejson.isJSONValidstandard(postretvoice))
							        {
										JSONObject jsonObjectvoice = new JSONObject(postretvoice);
										
										if(!jsonObjectvoice.getBoolean("STATUS"))
										{
											//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
											/*if(directory.getIsmlgenerated()<=0)
											{
												directory.setIsmlgenerated(0);
												directory.setRetmlid(0);
											}*/
											
											//edit on 30 june 2020 from here
											/*if(dirlocal.getRetmlid()>0)
											{
												directory.setRetmlid(dirlocal.getRetmlid());
												directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
											}
											else
											{
												directory.setIsmlgenerated(0);
												directory.setRetmlid(0);
											}*/
											//edit on 30 june 2020 till here
											
										}
										else
										{
											directory.setIsmlgenerated(1);
											//directory.setRetvoiceid(jsonObjectvoice.getInt("MESSAGE"));
											
											//JSONObject jsonChildObjectupdatevoice = (JSONObject)jsonObjectvoice.get("MESSAGE");
											//System.out.println("post return is : "+ postret);

											
											if(jsonObjectvoice.has("MESSAGE"))
											{
												
												JSONObject jsonChildObjectml = (JSONObject)jsonObjectvoice.get("MESSAGE");
												if(jsonChildObjectml.has("id"))
												{
													if(jsonChildObjectml.getInt("id") <=0 )
													{
														if(dirlocal.getRetmlid()>0)
														{
															directory.setRetmlid(dirlocal.getRetmlid());
															directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
														}
														else
														{
															directory.setIsmlgenerated(0);
															directory.setRetmlid(0);
														}
													}
													else
													{
														directory.setRetmlid(jsonChildObjectml.getInt("id"));
													}
												}
												
												
												/*if(utility.isNumeric(jsonObjectvoice.getString("MESSAGE")))
												{
													directory.setRetmlid(jsonObjectvoice.getInt("MESSAGE"));
													//directory.setIsmlgenerated(1);
												}
												else
												{
													if(dirlocal.getRetmlid()>0)
													{
														directory.setRetmlid(dirlocal.getRetmlid());
														directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
													}
													else
													{
														directory.setIsmlgenerated(0);
														directory.setRetmlid(0);
													}
												}*/
												
												/*if(jsonChildObject.getInt("id") <=0 )
												{
													directory.setRetvoiceid(0);
													directory.setIsvoicegenerated(0);
												}*/
												
												/*JSONObject insmessage = jsonObjectvoice.getJSONObject("MESSAGE");
												if(insmessage.has("id"))
												{
													if(insmessage.getInt("id")<=0)
													{
														//directory.setRetvoiceid(0);
														//directory.setIsvoicegenerated(0);
													}
													else
													{
														directory.setRetmlid(insmessage.getInt("id"));
														directory.setIsmlgenerated(1);
													}
													
													
												}*/
												//else
												//{
													//directory.setRetvoiceid(jsonObjectvoice.getInt("MESSAGE"));
												//}
											}
											
										}
							        }
								}	
							}
				        }
				        else
				        {
				        	if(isSBD && retMLID<=0)
							{
								//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "cannot update : "+directory.getName()+" directory");
				        		System.out.print("cannot update : "+directory.getName()+" directory");
							}
				        	else
				        	{
				        		postretvoicecheck = hpr.setPostData(result.getMlapi()+"/api/category/add",postdata);
								System.out.println("post return is : "+ postretvoicecheck);
								
								if(validatejson.isJSONValidstandard(postretvoicecheck))
						        {
									jsonObjectaddcheck = new JSONObject(postretvoicecheck);
									
									
									
									if(jsonObjectaddcheck.getBoolean("STATUS"))
									{
										directory.setRetmlid(jsonObject.getInt("MESSAGE"));
										directory.setIsmlgenerated(1);
									}
									else
									{
										//directory.setRetmlid(0);
										//directory.setIsmlgenerated(0);
										if(dirlocal.getRetmlid()>0)
										{
											directory.setRetmlid(dirlocal.getRetmlid());
											directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
										}
										else
										{
											directory.setIsmlgenerated(0);
											directory.setRetmlid(0);
										}
									}
						        }
				        	}
				        }
				        //sampai sini check data
					}
					else
					{
						//directory.setIsmlgenerated(0);
						//directory.setRetmlid(0);
						if(dirlocal.getRetmlid()>0)
						{
							directory.setRetmlid(dirlocal.getRetmlid());
							directory.setIsmlgenerated(dirlocal.getIsmlgenerated());
						}
						else
						{
							directory.setIsmlgenerated(0);
							directory.setRetmlid(0);
						}
					}
					
					
					lastinsertuserid =ecMapper.Update(directory);
					if(isSBD)
					{
						saveintentnew.setActive(1);
						saveintentnew.setAnswer(directory.getAnswer());
						saveintentnew.setQuestion(directory.getQuestion());
						saveintentnew.setDirectoryid(directory.getDirectoryid());
						
						saveintentnew.setIntentgroupid(directory.getIntentgroupid());
						saveintentnew.setSentimentid(directory.getSentimentid());
						saveintentnew.setDescription(directory.getDescription());
						
						saveintentnew.setUpdatedby(directory.getUpdatedby());
						
						saveintentnew.setUpdatedtime(directory.getUpdatedtime());
						
						if(isUpdate)
						{
							intservice.getUpdateinternal(this.tenantIdentifier,saveintentnew);
						}
						else
						{
							intservice.getCreate(this.tenantIdentifier,saveintentnew);
						}
						
					}
					
					postmessage = "{ 'categorymode' : '"+ directory.getCategorymode() +"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
			    	JsonElement je = jp.parse(postmessage);
					prettyJsonString = gson.toJson(je);
				}
				else
			    {
			    	throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
			    }
				
			
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return prettyJsonString;
		
	}
	
	private String saveRoocengineUpdate(EngineCredential result,boolean isSBD ,int MaxIntent,DirectoryMapper ecMapper,DirectoryMapper dirmapMapper,JSONObject postdata,Directory directory
			,Directory dirlocal,int retparID,JSONObject enumstring,JSONObject faqstring,Directory dirswitching,Directory dirswitchingparent,boolean isUpdate
			,String postret,String postretvoice,JSONObject jsonObject,String postmessage,int retParvoiceID
			,int retMLID,String postretml,String lastinsertuserid,String prettyJsonString,Gson gson,JsonParser jp
			,String engAccesstoken,Directory dirparent,Intent saveintentnew)
	{
		try {
			
			this.retDataPath ="";
			String getmapmap = "";
			String Switching = "";
			if(directory.getParentid()>0)
			{
				getmapmap = this.mapView(directory.getParentid(),ecMapper);
			}
			else
			{
				//default ROOT
				getmapmap = directory.getName();
				//getmapmap = "ROOT";
			}
			getmapmap = getmapmap.replaceAll("null", "");
			getmapmap = getmapmap.replaceAll(" null", "");
			System.out.println("MapView is : " + getmapmap);
			//postdata.put("map",getmapmap);

			postdata.put("Authorization", engAccesstoken);
			//postdata.put("parentId", retparID);
			postdata.put("id", directory.getReticategoryid());
			postdata.put("name", directory.getName());
			postdata.put("description", directory.getDescription());
			
			if(directory.getCategorymode().equals("STANDARD") || directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("MEMORY"))
			{
				isSBD = false;
			}
			else
			{
				//if(directory.getIntentparentid()<=0)
				//{
				//throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Intent Parent cannot be null");
				//}

				isSBD = true;
				MaxIntent =result.getInitialincrement();
				Intent resultintentmax = new Intent();
				resultintentmax = intservice.getMaxintent(this.tenantIdentifier);
				if(resultintentmax == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
				}

				if(resultintentmax.getMaxintentid() <=0)
				{
					//MaxIntent = MaxIntent;
				}
				else
				{
					MaxIntent = resultintentmax.getMaxintentid() +1;
				}

				this.retDataPath="";

				String getmapView = "";
				if(directory.getIntentid()>0 && directory.getSwitchingid()>0)
				{

					getmapView = this.mapView(directory.getSwitchingid(),dirmapMapper);
					getmapView = getmapView.replaceAll("null", "");
					getmapView = getmapView.replaceAll(" null", "");
					System.out.println("MapView is : " + getmapView);

					//String Switching = "'"+directory.getIntentid()+"' : '"+getmapView+"'";

					//String key = " \""+directory.getIntentid()+"\" ";
					//String value = " \""+getmapView+"\" ";

					//String getmapView = "Root->Child";
					//String key = "27001";

					//Switching = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
					Switching = ""+getmapView+"";
					//System.out.println("Switching is :" +Switching);

					//JSONObject switching = new JSONObject();
					//switching.put(""+Integer.toString(directory.getIntentid()), getmapView);

					directory.setSwitching(""+Switching);
				}
				System.out.println("Switching is : " + Switching);




				System.out.println("Current Directory FAQ IS :" + dirlocal.getFaq());
				boolean isEmptyfaq = dirlocal.getFaq() == null || dirlocal.getFaq().trim().length() == 0;

				if(!isEmptyfaq)
				{
					if(Integer.parseInt(dirlocal.getFaq())>0)
					{
						Intent modelFaq =  intservice.getView(this.tenantIdentifier, Integer.parseInt(dirlocal.getFaq()));
						System.out.println("intent id is : " + dirlocal.getFaq());
						if(modelFaq == null)
						{
							isUpdate=false;
							saveintentnew.setIntentid(MaxIntent);
							saveintentnew.setIretquestionid(MaxIntent);
							saveintentnew.setCreatedby(directory.getCreatedby());
							saveintentnew.setCreatedtime(directory.getUpdatedtime());
							saveintentnew.setParentid(directory.getIntentparentid());
							//saveintentnew.setUpdatedby(directory.getCreatedby());
							//saveintentnew.setUpdatedtime(directory.getUpdatedtime());

						}
						else
						{
							isUpdate=true;
							MaxIntent = modelFaq.getIntentid();
							directory.setFaq(String.valueOf(MaxIntent));
							saveintentnew.setIntentid(modelFaq.getIntentid());
							saveintentnew.setIretquestionid(modelFaq.getIntentid());
							saveintentnew.setParentid(modelFaq.getParentid());
						}
					}
					else
					{
						isUpdate=false;
						saveintentnew.setIntentid(MaxIntent);
						saveintentnew.setIretquestionid(MaxIntent);
						saveintentnew.setCreatedby(directory.getCreatedby());
						saveintentnew.setCreatedtime(directory.getUpdatedtime());
						saveintentnew.setParentid(directory.getIntentparentid());

					}
				}
				else
				{
					isUpdate=false;
					saveintentnew.setIntentid(MaxIntent);
					saveintentnew.setIretquestionid(MaxIntent);
					saveintentnew.setCreatedby(directory.getCreatedby());
					saveintentnew.setCreatedtime(directory.getUpdatedtime());
					saveintentnew.setParentid(directory.getIntentparentid());
				}


				boolean isEmptyquestion = directory.getQuestion() == null || directory.getQuestion().trim().length() == 0;
				if(isEmptyquestion)
				{
					directory.setQuestion(directory.getName());
					directory.setAnswer(".");
				}


				//directory.setFaq(String.valueOf(MaxIntent));

				faqstring.put("id", MaxIntent);
				faqstring.put("name", directory.getQuestion());
				faqstring.put("answer", directory.getAnswer());

				postdata.put("faq", faqstring);
				postdata.put("mode", directory.getCategorymode());
				//postdata.put("switching", directory.getSwitching());
				//postdata.put("switching", Switching);
				postdata.put("goTo", Switching);
				postdata.put("extendsEnumCategory", enumstring);
			}

			System.out.println("enumstring :" + enumstring);
			System.out.println("faqstring :" + faqstring);
			
			if(!isSBD)
			{
				postret = hpr.setPostData(result.getApi()+"/category/update",postdata);
				System.out.println("post return is : "+ postret);

				//JSONObject jsonObjectretcatID;

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
					directory.setReticategoryid(jsonObject.getInt("MESSAGE"));
				}
				else
				{
					throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
				}
			}
			
			lastinsertuserid =ecMapper.Update(directory);

			if(isSBD)
			{
				saveintentnew.setActive(1);
				saveintentnew.setAnswer(directory.getAnswer());
				saveintentnew.setQuestion(directory.getQuestion());
				saveintentnew.setDirectoryid(directory.getDirectoryid());

				saveintentnew.setIntentgroupid(directory.getIntentgroupid());
				saveintentnew.setSentimentid(directory.getSentimentid());
				saveintentnew.setDescription(directory.getDescription());

				saveintentnew.setUpdatedby(directory.getUpdatedby());

				saveintentnew.setUpdatedtime(directory.getUpdatedtime());


				JSONObject postdataintent = new JSONObject();

				postdataintent.put("Authorization", engAccesstoken);
				postdataintent.put("id", MaxIntent);
				//postdata.put("categoryid", retparID);
				postdataintent.put("name", directory.getQuestion());
				postdataintent.put("answer", directory.getAnswer());
				//postdata.put("answer", ".");
				//postdataintent.put("categoryId", retparID);

				boolean isEmptySwitching = Switching == null || Switching.trim().length() == 0;
				if(!isEmptySwitching)
				{
					postdataintent.put("goTo", Switching);
				}

				//postdataintent.put("parentId", 0);
				postdataintent.put("map", getmapmap);
				//postdata.put("category", result.getRootcategory());

				int parentintentid=0;
				boolean isEmptyfaqParent = dirparent.getFaq() == null || dirparent.getFaq().trim().length() == 0;
				if(isEmptyfaqParent)
				{
					//postdataintent.put("parentId", 0);
					parentintentid=0;
				}
				else
				{
					//postdataintent.put("parentId", Integer.parseInt(dirparent.getFaq()));
					parentintentid=Integer.parseInt(dirparent.getFaq());
				}
				//faqstring.put("attachments", enumDescription);

				postdataintent.put("parentId", parentintentid);
				saveintentnew.setParentid(parentintentid);

				/*if(directory.getCategorymode().equals("DRILLDOWN"))
				{
					postdataintent.put("visibleFromRoot",true);
				}*/
				if(dirparent.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
				{
					postdataintent.put("visibleFromRoot",false);
				}
				else if(dirparent.getCategorymode().equals("STANDARD"))
				{
					if(directory.getCategorymode().equals("DRILLDOWN"))
					{
						postdataintent.put("visibleFromRoot",true);
					}
					else
					{
						postdataintent.put("visibleFromRoot",false);
					}
				}
				else if(dirparent.getCategorymode().equals("DRILLDOWN"))
				{
					postdataintent.put("visibleFromRoot",true);
				}

				JSONArray ja = new JSONArray();
				ja.put(postdataintent);


				if(isUpdate)
				{
					postret = hpr.setPostData(result.getApi()+"/intent/update",postdataintent);
					intservice.getUpdateinternal(this.tenantIdentifier,saveintentnew);

				}
				else
				{
					postret = hpr.setPostData(result.getApi()+"/intent",postdataintent);
					intservice.getCreate(this.tenantIdentifier,saveintentnew);

				}
				System.out.println("Post return for intent branching is : \n" + postret);

			}

			postmessage = "{ 'categorymode' : '"+ directory.getCategorymode() +"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
			JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);


			
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return prettyJsonString;
	}

	private String saveRoocengineCreatesync(EngineCredential result,boolean isSBD ,int MaxIntent,DirectoryMapper ecMapper,DirectoryMapper dirmapMapper,JSONObject postdata,Directory directory
			,Directory dirlocal,int retparID,JSONObject enumstring,JSONObject faqstring,Directory dirswitching,Directory dirswitchingparent,boolean isUpdate
			,String postret,String postretvoice,JSONObject jsonObject,String postmessage,int retParvoiceID
			,int retMLID,String postretml,String lastinsertuserid,String prettyJsonString,Gson gson,JsonParser jp
			,String engAccesstoken,Directory dirparent)
	{
		try {
			
			
			
			
			this.retDataPath ="";
			String getmap = "";
			if(directory.getParentid()>0)
			{
				getmap = this.mapView(directory.getParentid(),ecMapper);
			}
			else
			{
				//default ROOT
				getmap = "";//directory.getName();
				//getmap = "ROOT";
			}
			getmap = getmap.replaceAll("null", "");
			getmap = getmap.replaceAll(" null", "");
			System.out.println("MapView is : " + getmap);

			postdata.put("map",getmap);

			postdata.put("Authorization", engAccesstoken);
			//postdata.put("parent_id", retparID);
			

			postdata.put("name", directory.getName());

			//postdata.put("parentid", retparID);
			postdata.put("description", directory.getDescription());

			postdata.put("mode", directory.getCategorymode());
			postdata.put("switching", directory.getSwitching());
			postdata.put("extendsEnumCategory", enumstring);
			
			int retdirectoryid =0;
			if(directory.getCategorymode().equals("STANDARD") || directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("MEMORY"))
			{
				isSBD = false;
				postret = hpr.setPostData(result.getApi()+"/category",postdata);


				System.out.println("post return is : "+ postret);

				//JSONObject jsonObjectretcatID;

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
					//directory.setReticategoryid(jsonObject.getInt("MESSAGE"));
					retdirectoryid = jsonObject.getInt("MESSAGE");
				}
				else {
					throw new CoreException(HttpStatus.NOT_MODIFIED, "Not Saved.");
				}
			}
			else
			{
				isSBD = true;
				directory.setFaq(String.valueOf(MaxIntent));
			}
			directory.setReticategoryid(retdirectoryid);
			lastinsertuserid =ecMapper.Createroocenginesync(directory);

			System.out.println("enumstring :" + enumstring);
			System.out.println("faqstring :" + faqstring);
			
			if(isSBD)
			{


				/*MaxIntent maxintent = new MaxIntent();
				maxintent.setIntentid(MaxIntent);
				maxintent.setCreatedby(directory.getCreatedby());
				maxintent.setUpdatedby(directory.getUpdatedby());
				maxintent.setCreatedtime(directory.getCreatedtime());
				maxintent.setUpdatedtime(directory.getUpdatedtime());
				String savemaxIntent = intservice.getCreatemax(this.tenantIdentifier,maxintent);
				System.out.println("Save Max Intent record is :" + savemaxIntent+ " , Max intent is :"+MaxIntent);
				*/
				
				boolean isEmptyfaq = directory.getFaq() == null || directory.getFaq().trim().length() == 0;
				
				if(!isEmptyfaq)
				{
					if(Integer.parseInt(directory.getFaq())>0)
					{
						Intent modelFaq =  intservice.getView(this.tenantIdentifier, Integer.parseInt(directory.getFaq()));
						System.out.println("branching intent id is : " + directory.getFaq());
						if(modelFaq == null)
						{
							isUpdate=false;
							//MaxIntent = MaxIntent;
						}
						else
						{
							isUpdate=true;
							MaxIntent = modelFaq.getIntentid();
							directory.setFaq(String.valueOf(MaxIntent));
						}
					}
					else
					{
						isUpdate=false;
					}
				}
				else
				{
					isUpdate=false;
				}


				boolean isEmptyquestion = directory.getQuestion() == null || directory.getQuestion().trim().length() == 0;
				if(isEmptyquestion)
				{
					directory.setQuestion(directory.getName());
					directory.setAnswer(directory.getDescription());
				}

				directory.setFaq(String.valueOf(MaxIntent));
				postdata.put("faq", MaxIntent);

				faqstring.put("id", MaxIntent);
				faqstring.put("question", directory.getQuestion());
				faqstring.put("answer", directory.getAnswer());

				//directory.setReticategoryid(0);

				/*Intent saveintentnew = new Intent();
				saveintentnew.setIntentid(MaxIntent);
				saveintentnew.setActive(1);
				saveintentnew.setAnswer(directory.getAnswer());
				saveintentnew.setQuestion(directory.getQuestion());
				saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
				saveintentnew.setIretquestionid(MaxIntent);

				saveintentnew.setIntentgroupid(directory.getIntentgroupid());
				saveintentnew.setSentimentid(directory.getSentimentid());
				saveintentnew.setDescription(directory.getDescription());

				saveintentnew.setCreatedby(directory.getCreatedby());
				saveintentnew.setUpdatedby(directory.getUpdatedby());
				saveintentnew.setCreatedtime(directory.getCreatedtime());
				saveintentnew.setUpdatedtime(directory.getUpdatedtime());*/



				JSONObject postdataintent = new JSONObject();

				postdataintent.put("Authorization", engAccesstoken);
				postdataintent.put("id", MaxIntent);
				//postdata.put("categoryid", retparID);
				postdataintent.put("name", directory.getQuestion());
				postdataintent.put("answer", directory.getAnswer());
				//postdata.put("answer", ".");
				//postdataintent.put("categoryId", retparID);
				//postdataintent.put("parentId", 0);
				postdataintent.put("map", getmap);
				//postdata.put("category", result.getRootcategory());
				int parentintentid=0;
				boolean isEmptyfaqParent = dirparent.getFaq() == null || dirparent.getFaq().trim().length() == 0;
				if(isEmptyfaqParent)
				{
					//postdataintent.put("parentId", 0);
					parentintentid=0;
				}
				else
				{
					//postdataintent.put("parentId", Integer.parseInt(dirparent.getFaq()));
					parentintentid=Integer.parseInt(dirparent.getFaq());
				}
				//faqstring.put("attachments", enumDescription);

				postdataintent.put("parentId", parentintentid);
				
				//saveintentnew.setParentid(parentintentid);

				/*if(directory.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
				{
					//postdataintent.put("visibleFromRoot",false);
					if(dirparent.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
					{

					}
				}
				else if(directory.getCategorymode().equals("DRILLDOWN"))
				{
					postdataintent.put("visibleFromRoot",true);
				}*/

				if(dirparent.getCategorymode().equals("QUESTIONNAIRE_BRANCHING"))
				{
					postdataintent.put("visibleFromRoot",false);
				}
				else if(dirparent.getCategorymode().equals("STANDARD"))
				{
					if(directory.getCategorymode().equals("DRILLDOWN"))
					{
						postdataintent.put("visibleFromRoot",true);
					}
					else
					{
						postdataintent.put("visibleFromRoot",false);
					}
				}
				else if(dirparent.getCategorymode().equals("DRILLDOWN"))
				{
					postdataintent.put("visibleFromRoot",true);
				}

				JSONArray ja = new JSONArray();
				ja.put(postdataintent);

				postret = hpr.setPostData(result.getApi()+"/intent",postdataintent);

				System.out.println("Post return for intent branching is : \n" + postret);

				JSONObject pdcomposer = new JSONObject();

				/*
				pdcomposer.put("access_token", directory.getToken());
				pdcomposer.put("externalId", MaxIntent);
				pdcomposer.put("directoryId", lastinsertuserid);
				pdcomposer.put("directoryParentId", directory.getParentid());
				pdcomposer.put("question", directory.getQuestion());
				pdcomposer.put("answer", directory.getAnswer());

				String locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/composer/intent",pdcomposer,this.tenantIdentifier,directory.getToken());
				*/

				/*System.out.println("composer post return is : "+ locpostret);

				if(validatejson.isJSONValidstandard(locpostret))
				{
					JSONObject jsonObjectlocal = new JSONObject(locpostret);

					if(jsonObjectlocal.has("status"))
					{
						if(jsonObjectlocal.getString("status").equals("failed."))
						{
							//saveintentnew.setIsgenerated(0);
						}
						else
						{
							//saveintentnew.setIsgenerated(1);
						}
					}
					else
					{
						//saveintentnew.setIsgenerated(0);
					}
				}
				else
				{
					//saveintentnew.setIsgenerated(0);
				}*/

				//intservice.getCreate(this.tenantIdentifier,saveintentnew);
			}


			postmessage = "{ 'categorymode' : '"+directory.getCategorymode()+"' , 'type' : 'directory' ,'directoryid' : " + lastinsertuserid + " }";
			JsonElement je = jp.parse(postmessage);
			prettyJsonString = gson.toJson(je);
			
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}/*finally{
			sqlSession.close();
		}*/
		return prettyJsonString;
	}
	
	
	
	@Override
	public String Createroocenginesync(Directory directory) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		String postret = "";
		String postretvoice = "";
		String postretml = "";
		String postmessage ="";
		String prettyJsonString ="";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		Directory dirparent = new Directory();
		Directory dirswitching =  new Directory();
		Directory dirswitchingparent = new Directory();
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			DirectoryMapper dirmapMapper = sqlSession.getMapper(DirectoryMapper.class);
			
			String engAccesstoken ="";
			
			EngineCredential result = new EngineCredential();
			result = ecservice.getView(this.tenantIdentifier,this.ECID);
			
			if (result == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
	        }
			
			String hprret = "";
			String jsonString = "";
	        JSONObject jsonObject = null;
			
			/*
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
		    
			*/
	        
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
			
			
			
			
			//userrole = 
			//Map enumstring = new HashMap();
			//enumstring = null;
			//Map postdata = new HashMap();
			//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			JSONObject postdata = new JSONObject();
			JSONObject enumstring = new JSONObject();
			JSONObject faqstring = new JSONObject();
			int MaxIntent =0;
			if(directory.getExtendenumcategoryid()>0)
			{
				Directory direnum = null; 
				direnum = ecMapper.findOne(directory.getExtendenumcategoryid());
				if(direnum == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid enum data.");
				}
				int retEnumID = direnum.getReticategoryid();
				String enumName = direnum.getName();
				String enumDescription = direnum.getDescription();
				String enumCatmode = direnum.getCategorymode();
				
				Directory direnumparent = null;
				direnumparent = ecMapper.findOne(direnum.getParentid());
				int retEnumparID = direnumparent.getReticategoryid();
				
				enumstring.put("id", retEnumID);
				enumstring.put("name", enumName);
				enumstring.put("parentid", retEnumparID);
				enumstring.put("description", enumDescription);
				enumstring.put("faq", directory.getFaq());
				enumstring.put("mode", enumCatmode);
				enumstring.put("switching", directory.getSwitching());
				enumstring.put("extendsEnumCategory", "null");
			}
			
			if(directory.getCategorymode().equals("ENUM") || directory.getCategorymode().equals("QUESTIONNAIRE_BRANCHING") || directory.getCategorymode().equals("DRILLDOWN") || directory.getCategorymode().equals("QUESTIONNAIRE_STEPPING"))
			{
				enumstring = null;
			}
			
			
			//Directory dirparent = new Directory();
			
			Directory dirparentcount = ecMapper.findCountrootparent();
			int countrootcategory =0;
			
			countrootcategory = dirparentcount.getCountparent();
			
			
			
			
			int retparID = 0;
			int retParvoiceID=0;
			int retMLID=0;
			if(countrootcategory>0)
			{
				
				boolean isEmptymap = directory.getDirectorymap() == null || directory.getDirectorymap().trim().length() == 0;
				boolean isEmpty = directory.getParentname() == null || directory.getParentname().trim().length() == 0;
				
				if(!isEmptymap)
				{
					//dirparent = ecMapper.findParentbyname(directory.getParentname());
					String strMap = directory.getDirectorymap();
					String[] split = strMap.split("->");
					int getparentID =0 ;
					for (int i = 0; i < split.length; i++) {
						if(i==0)
						{
							//the first one query without parentid only name
							dirparent = ecMapper.findParentbyname(split[i].trim());
						}
						else
						{
							//not the first one query with parentid and name
							dirparent = ecMapper.findDirectorybynameandparentid(split[i],getparentID);
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
					dirparent = ecMapper.findParentbyname(directory.getParentname().trim());
				}
				else
				{
					dirparent = ecMapper.findOne(directory.getParentid());
				}
				
				if(directory.getParentid()>0)
				{
					if(dirparent == null)
					{
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent id or name.");
					}
					
					retparID = dirparent.getReticategoryid();
					retParvoiceID = dirparent.getRetvoiceid();
					retMLID = dirparent.getRetmlid();
					
					if(!isEmptymap)
					{
						directory.setParentid(dirparent.getDirectoryid());
					}
					else if(!isEmpty)
					{
						directory.setParentid(dirparent.getDirectoryid());
					}
				}
				else
				{
					directory.setParentid(0);
				}
				
			}
			
			
			
			
			
			
			
			System.out.println("extend enum is :" + directory.getExtendenumcategoryid());
			System.out.println("cat mode is :" + directory.getCategorymode());
			System.out.println("parent is :" + directory.getParentid());
			System.out.println("ret parent is :" + retparID);
			System.out.println("ret voice parent is :" + retParvoiceID);
			System.out.println("ret ml parent is :" + retMLID);
			
			if(directory.getExtendenumcategoryid()<=0)
			{
				enumstring = null;
			}
			
			
			MaxIntent =result.getInitialincrement();
			Intent resultintentmax = new Intent();
			resultintentmax = intservice.getMaxintent(this.tenantIdentifier);
			if(resultintentmax == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}

			if(resultintentmax.getMaxintentid() <=0)
			{
				//MaxIntent = MaxIntent;
			}
			else
			{
				MaxIntent = resultintentmax.getMaxintentid() +1;
			}
			
			boolean isEmptyfaq = directory.getFaq() == null || directory.getFaq().trim().length() == 0;
			
			if(!isEmptyfaq)
			{
				if(Integer.parseInt(directory.getFaq())>0)
				{
					Intent modelFaq =  intservice.getView(this.tenantIdentifier, Integer.parseInt(directory.getFaq()));
					System.out.println("branching intent id is : " + directory.getFaq());
					if(modelFaq == null)
					{
						//isUpdate=false;
						//MaxIntent = MaxIntent;
					}
					else
					{
						//isUpdate=true;
						MaxIntent = modelFaq.getIntentid();
						//directory.setFaq(String.valueOf(MaxIntent));
					}
				}
				else
				{
					//isUpdate=false;
				}
			}
			else
			{
				//isUpdate=false;
			}
			
			
			this.retDataPath="";
			String Switching = "";
			String Switchingsingle = "";
			String getmapView = "";
			
			
			//boolean isEmptyswitching = directory.getSwitching() == null || directory.getSwitching().trim().length() == 0;
			//if(!isEmptyswitching)
			//{
				
			//}
			
			if(directory.getIntentid()>0 && directory.getSwitchingid()>0)
			{
				System.out.println("Querying switching parent");
				dirswitching = ecMapper.findOne(directory.getSwitchingid());
				
				dirswitchingparent = ecMapper.findOne(dirswitching.getDirectoryid());
				
				getmapView = this.mapView(directory.getSwitchingid(),dirmapMapper);
				getmapView = getmapView.replaceAll("null", "");
				getmapView = getmapView.replaceAll(" null", "");
				System.out.println("MapView is : " + getmapView);
				
				
				
				Switching = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
				
				Switchingsingle = "\""+directory.getIntentid()+"\" : \""+getmapView+"\"";
				
				
				directory.setSwitching(""+Switching);
				
				//process switching parent,new code,new method
				System.out.println("Switching is : " + Switching);
				System.out.println("Switching single is : " + Switchingsingle);
				
				System.out.print("Original Switching is :" + directory.getSwitching());
				System.out.print("Finding Parent ID for new Switching,for directory :" + dirswitching.getName());
				System.out.print("We Find only if parentid is > 0 , parentid is :" + dirswitching.getParentid());
				System.out.print("Parent Name is  : " + dirswitchingparent.getName() + " Parent id is : " +dirswitchingparent.getParentid());
				
				System.out.print("Reseting retDataPatah with current value   : " + this.retDataPath + " ");
				this.retDataPath="";
				System.out.print("Reseting getmapView with current value   : " + getmapView + " ");
				getmapView ="";
				String Switchingparent ="";
				String Switchingparentsingle ="";
				String Switchingcombine ="";
				
				boolean isEmptyswitching = Switching == null || Switching.trim().length() == 0;
				if(!isEmptyswitching)
				{
					if(dirswitching.getParentid()>0)
					{
						getmapView = this.mapView(dirswitching.getParentid(),dirmapMapper);
						getmapView = getmapView.replaceAll("null", "");
						getmapView = getmapView.replaceAll(" null", "");
						System.out.println("MapView is : " + getmapView);
						
						Switchingparent = "{\""+directory.getIntentid()+"\" : \""+getmapView+"\"}";
						
						Switchingparentsingle = "\""+directory.getIntentid()+"\" : \""+getmapView+"\"";
						
						System.out.print("Parent Switching value is    : " + Switchingparent + " ");
						
						System.out.print("Parent Switching single value is    : " + Switchingparentsingle + " ");
					}
				}
				
				boolean isEmptyswitchingsingle = Switchingsingle == null || Switchingsingle.trim().length() == 0;
				boolean isEmptyswitchingparentsingle = Switchingparentsingle == null || Switchingparentsingle.trim().length() == 0;
				if(!isEmptyswitchingsingle)
				{
					if(!isEmptyswitchingparentsingle)
					{
						Switchingcombine = "{"+Switchingsingle+" , "+Switchingparentsingle+"}";
					}
					else
					{
						Switchingcombine = "{"+Switchingsingle+" "+"}";
					}
					
					directory.setSwitching(""+Switchingcombine);
					System.out.println("Switching after combine is : " + Switchingcombine);
				}
				//till here process switching parent,new code,new method
			}
			
			boolean isSBD = false;
			
			boolean isUpdate = true;
			Directory dirlocal = null;
			if(isUseicontek)
			{
				prettyJsonString = this.saveiContekCreate(result,isSBD , MaxIntent, ecMapper, dirmapMapper, postdata, directory
						, dirlocal, retparID, enumstring, faqstring, dirswitching, dirswitchingparent, isUpdate
						, postret, postretvoice, jsonObject, postmessage, retParvoiceID
						, retMLID, postretml, lastinsertuserid, prettyJsonString, gson, jp);
			}
			else
			{
				prettyJsonString = this.saveRoocengineCreatesync( result, isSBD , MaxIntent, ecMapper, dirmapMapper, postdata, directory
						, dirlocal, retparID, enumstring, faqstring, dirswitching, dirswitchingparent, isUpdate
						, postret, postretvoice, jsonObject, postmessage, retParvoiceID
						, retMLID, postretml, lastinsertuserid, prettyJsonString, gson, jp
						, engAccesstoken, dirparent);
			}
			
				
				
			//lastinsertuserid =ecMapper.Save(directory);
			
			//log.debug("insert id is : " + lastinsertuserid);
			
			log.info("insertdir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return prettyJsonString;
	}

}
