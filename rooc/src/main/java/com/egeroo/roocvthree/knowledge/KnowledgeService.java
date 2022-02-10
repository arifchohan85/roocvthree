package com.egeroo.roocvthree.knowledge;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.directory.Directory;
import com.egeroo.roocvthree.directory.DirectoryService;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.Intent;
import com.egeroo.roocvthree.intent.IntentService;
import com.egeroo.roocvthree.intent.MaxIntent;
import com.egeroo.roocvthree.nodesource.NodeSource;
import com.egeroo.roocvthree.nodesource.NodeSourceService;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;



@Service
public class KnowledgeService {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	private int ecId = 1;
	
	@Autowired
    private DirectoryService dirservice;
	
	@Autowired
	private IntentService intentservice;
	
	@Autowired
	private EngineCredentialService engcredsservice;
	
	@Autowired
	private NodeSourceService nsservice;
	
	HttpPostReq hpr = new HttpPostReq();
	
	ValidationJson validatejson = new ValidationJson();
	
	public KnowledgeResponse getCreate(String tenant,KnowledgeRequest knowledge,String token) 
	{
		
		Directory directory = new Directory();
		Directory dirparent = new Directory();
		NodeSource ns = new NodeSource();
		
		trb.SetTrailRecord(token,directory);
		
		EngineCredential result = new EngineCredential();
		result = engcredsservice.getView(tenant,this.ecId);
		
		int directoryparentid =0;
		int intentparentid=0;
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		boolean isEmptyparent = knowledge.getParentId() == null || knowledge.getParentId().trim().length() == 0;
		
		if(!isEmptyparent)
		{
			ns= nsservice.getBynodeid(tenant, knowledge.getParentId());
			if (ns == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no node parent found.");
	        }
			
			directoryparentid = ns.getDirectoryid();
			intentparentid = ns.getIntentid();
			
			dirparent = dirservice.getView(tenant,ns.getDirectoryid());		
		}
		else
		{
			//get root category
			dirparent = dirservice.getViewdirectorybyname(tenant,result.getRootcategory());
		}

		System.out.println(dirparent);
		
		int MaxIntent =result.getInitialincrement();
		Intent resultintentmax = new Intent();
		resultintentmax = intentservice.getMaxintent(tenant);
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
			
		//CompanyMapper appMapper = new CompanyMapperImpl(tenant);
		//return 
		//appMapper.Save(company);
		/* 
		   INGAT SEKARANG TIDAK ADA INTENT STANDARD
		   ,INTENT BRANCHING
		   ,INTENT DRILLDOWN
		   SEMUA INTENT KITA TREAT SAMA SAJA
		   YAH NANTI TINGGAL UNDER PARENT MANA SAJA
		   JADI KALAU BUAT DIRECTORY/FOLDER,COBA CREATE 1 INTENT STANDARD
		   ATAU KALAU NGGA KITA NGGA USAH BUAT INTENT STANDARD DULU
		*/
		
		String getmap = "";
		if(dirparent.getParentid()>0)
		{
			getmap = dirservice.mapView(dirparent.getParentid(),tenant);
		}
		else
		{
			//default ROOT
			getmap = result.getRootcategory();//directory.getName();
			//getmap = "ROOT";
		}
		getmap = getmap.replaceAll("null", "");
		getmap = getmap.replaceAll(" null", "");
		System.out.println("MapView is : " + getmap);
		
		
		directory.setParentid(directoryparentid);
		directory.setName(knowledge.getFolderName());
		
		JSONObject postdata = new JSONObject();
		JSONObject jsonObject;
		String postret = "";
		int retdirectoryid =0;
		boolean isAllowSave= false;
		boolean isSaveintent= false;
		Intent saveintentnew = new Intent();
		String lastinsertintentid ="";
		String lastinsertuserid = "";
		try 
		{
			NodeSource nssave = new NodeSource();
			
			if(knowledge.getType().toUpperCase().equals("FOLDER"))
			{
				//directory biasa
				dirservice.retDataPath ="";
				
				directory.setCategorymode("STANDARD");
				
				postdata.put("map",getmap);
				postdata.put("Authorization", token);
				postdata.put("name", knowledge.getFolderName());
				postdata.put("description", knowledge.getFolderName());
	
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
				
			}
			else if(knowledge.getType().toUpperCase().equals("STANDARD"))
			{
				directory.setCategorymode("QUESTIONNAIRE_BRANCHING");
				
				
				//intent
				isSaveintent = true;
				MaxIntent maxintent = new MaxIntent();
				maxintent.setIntentid(MaxIntent);
				maxintent.setCreatedby(directory.getCreatedby());
				maxintent.setUpdatedby(directory.getUpdatedby());
				maxintent.setCreatedtime(directory.getCreatedtime());
				maxintent.setUpdatedtime(directory.getUpdatedtime());
				String savemaxIntent = intentservice.getCreatemax(tenant,maxintent);
				System.out.println("Save Max Intent record is :" + savemaxIntent+ " , Max intent is :"+MaxIntent);
				
				saveintentnew.setIntentid(MaxIntent);
				
				nssave.setIntentid(MaxIntent);
				
				JSONObject postdataintent = new JSONObject();

				postdataintent.put("Authorization", token);
				postdataintent.put("id", MaxIntent);
				postdataintent.put("name", knowledge.getIntentName());
				postdataintent.put("answer", ".");
				postdataintent.put("map", getmap);
				
				postdataintent.put("parentId", dirparent.getDirectoryid());
				
				//saveintentnew.setParentid(dirparent.getDirectoryid());
				
				JSONArray ja = new JSONArray();
				ja.put(postdataintent);

				postret = hpr.setPostData(result.getApi()+"/intent",postdataintent);

				System.out.println("Post return for intent  is : \n" + postret);
				
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
				
				
				
			}
			
			if(isAllowSave)
			{
				
				directory.setReticategoryid(retdirectoryid);
				lastinsertuserid =dirservice.getCreate(tenant,directory);
				
				nssave.setType(knowledge.getType());
				nssave.setNodeid(knowledge.getId());
				nssave.setParentnodeid(knowledge.getParentId());
				nssave.setDirectoryid(Integer.parseInt(lastinsertuserid));
				
				
				if(isSaveintent)
				{
					nssave.setIntentid(MaxIntent);
					
					saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
					saveintentnew.setIntentparentid(intentparentid);
					
					JSONObject pdcomposer = new JSONObject();

					pdcomposer.put("access_token", token);
					pdcomposer.put("externalId", MaxIntent);
					pdcomposer.put("directoryId", lastinsertuserid);
					pdcomposer.put("directoryParentId", dirparent.getDirectoryid());
					pdcomposer.put("question", saveintentnew.getQuestion());
					pdcomposer.put("answer", saveintentnew.getAnswer());

					String locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/composer/intent",pdcomposer,tenant,token);


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
					
					lastinsertintentid = intentservice.getCreate(tenant,saveintentnew);
					
				}
				
				int nssaveres = nsservice.getCreate(tenant, nssave);
				if(nssaveres<=0)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Node is not save");
				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
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
		
		KnowledgeResponse kres = new KnowledgeResponse();
		if(isSaveintent)
		{
			kres.setIntentId(Integer.parseInt(lastinsertintentid));
			kres.setKnowledgeId(Integer.parseInt(lastinsertintentid));
			kres.setId(knowledge.getId());
		}
		else
		{
			kres.setFolderId(Integer.parseInt(lastinsertuserid));
			kres.setKnowledgeId(Integer.parseInt(lastinsertuserid));
			kres.setId(knowledge.getId());
		}
		
		return kres;
	}
	
	public KnowledgeResponse getUpdate(String tenant,KnowledgeRequest knowledge,String token) {
		Directory directory = new Directory();
		Directory dirparent = new Directory();
		Directory dircurrent = new Directory();
		NodeSource ns = new NodeSource();
		NodeSource nsupdatekey = new NodeSource();
		boolean isSaveintent = false;
		
		int directoryparentid =0;
		int intentparentid=0;
		
		trb.SetTrailRecord(token,directory);
		
		EngineCredential result = new EngineCredential();
		result = engcredsservice.getView(tenant,this.ecId);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		boolean isEmptyparent = knowledge.getParentId() == null || knowledge.getParentId().trim().length() == 0;
		
		if(!isEmptyparent)
		{
			ns= nsservice.getBynodeid(tenant, knowledge.getParentId());
			if (ns == null) {
	            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no node parent found.");
	        }
			
			directoryparentid = ns.getDirectoryid();
			intentparentid = ns.getIntentid();
			
			dirparent = dirservice.getView(tenant,ns.getDirectoryid());		
		}
		else
		{
			//get root category
			dirparent = dirservice.getViewdirectorybyname(tenant,result.getRootcategory());
		}

		System.out.println(dirparent);
		
		
			
		//CompanyMapper appMapper = new CompanyMapperImpl(tenant);
		//return 
		//appMapper.Save(company);
		/* 
		   INGAT SEKARANG TIDAK ADA INTENT STANDARD
		   ,INTENT BRANCHING
		   ,INTENT DRILLDOWN
		   SEMUA INTENT KITA TREAT SAMA SAJA
		   YAH NANTI TINGGAL UNDER PARENT MANA SAJA
		   JADI KALAU BUAT DIRECTORY/FOLDER,COBA CREATE 1 INTENT STANDARD
		   ATAU KALAU NGGA KITA NGGA USAH BUAT INTENT STANDARD DULU
		*/
		
		String getmap = "";
		if(dirparent.getParentid()>0)
		{
			getmap = dirservice.mapView(dirparent.getParentid(),tenant);
		}
		else
		{
			//default ROOT
			getmap = result.getRootcategory();//directory.getName();
			//getmap = "ROOT";
		}
		getmap = getmap.replaceAll("null", "");
		getmap = getmap.replaceAll(" null", "");
		System.out.println("MapView is : " + getmap);
		
		
		directory.setParentid(directoryparentid);
		directory.setName(knowledge.getFolderName());
		
		JSONObject postdata = new JSONObject();
		JSONObject jsonObject;
		String postret = "";
		
		Intent saveintentnew = new Intent();
		String lastinsertintentid ="";
		String lastinsertuserid = "";
		
		try
		{
			boolean isEmptycurrentupdatekey = knowledge.getId() == null || knowledge.getId().trim().length() == 0;
			
			if(!isEmptycurrentupdatekey)
			{
				nsupdatekey= nsservice.getBynodeid(tenant, knowledge.getId());
				if (nsupdatekey == null) {
		            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no node found.");
		        }		
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no update key found.");
			}
			
			if(knowledge.getType().toUpperCase().equals("FOLDER"))
			{
				directory.setCategorymode("STANDARD");
				
				if(nsupdatekey.getDirectoryid()>0)
				{
					dircurrent = dirservice.getView(tenant,nsupdatekey.getDirectoryid());
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no directory node found.");
				}
				
				
				if(dircurrent == null)
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid data.");
				}
				
				if(directory.getReticategoryid() <=0 )
				{
					directory.setReticategoryid(dircurrent.getReticategoryid());
				}
				
				postdata.put("Authorization", token);
				//postdata.put("parentId", retparID);
				postdata.put("id", directory.getReticategoryid());
				postdata.put("name", knowledge.getFolderName());
				postdata.put("description", knowledge.getFolderName());
				
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
				
				directory.setName(knowledge.getFolderName());
				directory.setDescription(knowledge.getFolderName());
				directory.setDirectoryid(dircurrent.getDirectoryid());
				
				lastinsertuserid =dirservice.getUpdate(tenant,directory);
			}
			else if(knowledge.getType().toUpperCase().equals("STANDARD"))
			{
				//intent
				directory.setCategorymode("QUESTIONNAIRE_BRANCHING");
				
				isSaveintent = true;
				JSONObject postdataintent = new JSONObject();
				
				saveintentnew.setIntentid(nsupdatekey.getIntentid());
				saveintentnew.setAnswer(".");
				saveintentnew.setQuestion(knowledge.getIntentName());
				saveintentnew.setDirectoryid(directoryparentid);
				saveintentnew.setIntentparentid(intentparentid);
				
				

				saveintentnew.setUpdatedby(directory.getUpdatedby());

				saveintentnew.setUpdatedtime(directory.getUpdatedtime());

				postdataintent.put("Authorization", token);
				postdataintent.put("id", nsupdatekey.getIntentid());
				//postdata.put("categoryid", retparID);
				postdataintent.put("name", knowledge.getIntentName());
				postdataintent.put("answer", ".");
				
				postret = hpr.setPostData(result.getApi()+"/intent/update",postdataintent);
				
				lastinsertintentid = intentservice.getUpdateinternal(tenant,saveintentnew);
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
		
		KnowledgeResponse kres = new KnowledgeResponse();
		if(isSaveintent)
		{
			kres.setIntentId(Integer.parseInt(lastinsertintentid));
			kres.setKnowledgeId(Integer.parseInt(lastinsertintentid));
			kres.setId(nsupdatekey.getNodeid());
		}
		else
		{
			kres.setFolderId(Integer.parseInt(lastinsertuserid));
			kres.setKnowledgeId(Integer.parseInt(lastinsertuserid));
			kres.setId(nsupdatekey.getNodeid());
		}
		
		return kres;
	}

}
