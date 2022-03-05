package com.egeroo.roocvthree.directory;

import java.io.IOException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import java.util.List;


import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

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

import com.egeroo.roocvthree.interaction.Interaction;
import com.egeroo.roocvthree.interaction.InteractionMapper;







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
		
		String lastinsertuserid ="";
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			lastinsertuserid =ecMapper.Save(directory);
			log.info("insertdir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public String Update(Directory directory) {
		System.out.println("directory ec : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			lastinsertuserid =ecMapper.Update(directory);
			log.info("updatedir data");
		}catch(PersistenceException e){
			log.debug(e + "error save dir data");
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return lastinsertuserid;
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
	public List<DirectoryTree> findAlldirectorytree() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DirectoryTree> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findAlldirectorytree();
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
							String postretintent ="";
							String postretint ="";
							
							String postretlocal="";
							try {
								
								
									//postretint = hpr.getUserchanneltoken(resgetApi+"/question/"+ item.getIretquestionid() +"/delete", engToken);
								postretint = hpr.getUserchanneltoken(resgetApi+"/clear/data/"+ item.getIretquestionid() +"", engToken);
								postretintent = hpr.getUserchanneltoken(resgetApi+"/intent/"+ item.getIretquestionid() +"/delete", engToken);
								
								
								
								//delete tempat nya ridwan
								postretlocal = hpr.setGetlocaldata(resgetApilocal+"/composer/intent/"+item.getIretquestionid()+"/delete",this.tenantIdentifier, Token);
								//sampai sini delete tempat ridwan
								System.out.println(postretlocal);
								System.out.println(postretvoice);
								System.out.println(postretintent);
								
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
								System.out.println(intentint);
								System.out.println(delintr);
								throw new CoreException(HttpStatus.NOT_MODIFIED, "delete intent engine with id : "+item.getIntentid()+" failed");
							}
							else
							{
								
									List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
									List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
									System.out.println(intentint);
									System.out.println(delintr);
							}
							
							
						});
					}
					//sampai sini mencari intent id recursive
					
					
					boolean allowDeletecat = false;
					//sekarang query yang category
					
					
					postret = hpr.getUserchanneltoken(result.getApi()+"/category/"+econe.getReticategoryid()+"/delete", engAccesstoken);
					
					allowDeletecat = true;
					
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
	public List<DirectoryExtract> extractDirectory() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DirectoryExtract> ec = null;
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
						
						System.out.println(intentint);
						System.out.println(delintr);
						
						throw new CoreException(HttpStatus.NOT_MODIFIED, "delete intent engine with id : "+item.getIntentid()+" failed");
					}
					else
					{
						if(jsonObjectupdateexpid.getBoolean("STATUS"))
						{
							List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
							List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
							
							System.out.println(intentint);
							System.out.println(delintr);
						}
						else
						{
							List<Intent> intentint = intMapper.deletebyIntent(item.getIntentid());
							List<Interaction> delintr = intrMapper.deletebyIntent(item.getIntentid());
							
							System.out.println(intentint);
							System.out.println(delintr);
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
	
	@SuppressWarnings("unused")
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

	
	/*start v3*/
	@Override
	public List<DirectoryIntentv3> findAlldirectoryintentv3() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DirectoryIntentv3> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findAlldirectoryintentv3();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	/*end v3*/

	@Override
	public List<IntentTree> findAllintenttree() {
		System.out.println("dir List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<IntentTree> ec = null;
		try{
			DirectoryMapper ecMapper = sqlSession.getMapper(DirectoryMapper.class);
			ec = ecMapper.findAllintenttree();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get dir data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
