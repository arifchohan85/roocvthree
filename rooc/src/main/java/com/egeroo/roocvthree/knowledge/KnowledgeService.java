package com.egeroo.roocvthree.knowledge;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.directory.Directory;
import com.egeroo.roocvthree.directory.DirectoryMapper;
import com.egeroo.roocvthree.directory.DirectoryMapperImpl;
import com.egeroo.roocvthree.directory.DirectoryService;
import com.egeroo.roocvthree.directory.DirectoryTree;
import com.egeroo.roocvthree.directory.IntentTree;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.Intent;
import com.egeroo.roocvthree.intent.IntentService;
import com.egeroo.roocvthree.intent.MaxIntent;
import com.egeroo.roocvthree.interaction.Interaction;
import com.egeroo.roocvthree.interaction.InteractionService;
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
	
	@Autowired
	private InteractionService intservice;
	
	HttpPostReq hpr = new HttpPostReq();
	
	ValidationJson validatejson = new ValidationJson();
	
	public String retDataPath;
	
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
		
		NodeSource nscheckAvailable= nsservice.getBynodeid(tenant, knowledge.getId());
		if (nscheckAvailable == null) {
            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no node found.");
        }
		else {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Please Change Node ID,id has been taken.");
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
			
			directoryparentid = dirparent.getDirectoryid();
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
		
		this.retDataPath="";
		String getmap = "";
		if(dirparent.getParentid()>0)
		{
			//getmap = this.mapView(dirparent.getParentid(),tenant);
			getmap = this.mapView(dirparent.getDirectoryid(),tenant);
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
		
		
		boolean isEmptyfoldername = knowledge.getFolderName() == null || knowledge.getFolderName().trim().length() == 0;
		
		directory.setParentid(directoryparentid);
		
		
		if(isEmptyfoldername)
		{
			directory.setName(knowledge.getIntentName());
		}
		else
		{
			directory.setName(knowledge.getFolderName());
		}
		
		
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
				//dirservice.retDataPath ="";
				
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
				//directory.setName(knowledge.getIntentName());
				
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
				
				//directoryparentid = ns.getDirectoryid();
				//intentparentid = ns.getIntentid();
				
				postdataintent.put("parentId", intentparentid);
				
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
				boolean isEmptypreviousnode = knowledge.getPreviousId() == null || knowledge.getPreviousId().trim().length() == 0;
				if(!isEmptypreviousnode)
				{
					nssave.setPreviousnodeid(knowledge.getPreviousId());
				}
				
				nssave.setDirectoryid(Integer.parseInt(lastinsertuserid));
				nssave.setCreatedby(directory.getCreatedby());
				nssave.setCreatedtime(directory.getCreatedtime());
				nssave.setUpdatedby(directory.getUpdatedby());
				nssave.setUpdatedtime(directory.getUpdatedtime());
				
				if(isSaveintent)
				{
					nssave.setIntentid(MaxIntent);
					
					saveintentnew.setDirectoryid(Integer.parseInt(lastinsertuserid));
					saveintentnew.setIntentparentid(intentparentid);
					saveintentnew.setQuestion(directory.getName());
					
					saveintentnew.setCreatedby(directory.getCreatedby());
					saveintentnew.setCreatedtime(directory.getCreatedtime());
					saveintentnew.setUpdatedby(directory.getUpdatedby());
					saveintentnew.setUpdatedtime(directory.getUpdatedtime());
					
					JSONObject pdcomposer = new JSONObject();

					pdcomposer.put("access_token", token);
					pdcomposer.put("externalId", MaxIntent);
					pdcomposer.put("directoryId", lastinsertuserid);
					pdcomposer.put("directoryParentId", dirparent.getDirectoryid());
					pdcomposer.put("question", saveintentnew.getQuestion());
					pdcomposer.put("answer", saveintentnew.getAnswer());

//					String locpostret = hpr.setPostDatalocal(result.getLocalapi()+"/intent",pdcomposer,tenant,token);
//
//
//					System.out.println("composer post return is : "+ locpostret);
//					
//					if(validatejson.isJSONValidstandard(locpostret))
//					{
//						JSONObject jsonObjectlocal = new JSONObject(locpostret);
//
//						if(jsonObjectlocal.has("status"))
//						{
//							if(jsonObjectlocal.getString("status").equals("failed."))
//							{
//								saveintentnew.setIsgenerated(0);
//							}
//							else
//							{
//								saveintentnew.setIsgenerated(1);
//							}
//						}
//						else
//						{
//							saveintentnew.setIsgenerated(0);
//						}
//					}
//					else
//					{
//						saveintentnew.setIsgenerated(0);
//					}
					saveintentnew.setIsgenerated(0);
					
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
		
		this.retDataPath="";
		String getmap = "";
		if(dirparent.getParentid()>0)
		{
			//getmap = this.mapView(dirparent.getParentid(),tenant);
			getmap = this.mapView(dirparent.getDirectoryid(),tenant);
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
		
		

		
		boolean isEmptyfoldername = knowledge.getFolderName() == null || knowledge.getFolderName().trim().length() == 0;
		
		directory.setParentid(directoryparentid);
		
		
		String dirname ="";
		if(isEmptyfoldername)
		{
			directory.setName(knowledge.getIntentName());
		}
		else
		{
			directory.setName(knowledge.getFolderName());
		}
		
		dirname = directory.getName();
		
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
				postdata.put("name", dirname);
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
				
				directory.setName(dirname);
				directory.setDescription(dirname);
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
				saveintentnew.setQuestion(dirname);
				saveintentnew.setDirectoryid(directoryparentid);
				saveintentnew.setIntentparentid(intentparentid);
				
				

				saveintentnew.setUpdatedby(directory.getUpdatedby());
				saveintentnew.setUpdatedtime(directory.getUpdatedtime());

				postdataintent.put("Authorization", token);
				postdataintent.put("id", nsupdatekey.getIntentid());
				postdata.put("question", dirname);
				postdataintent.put("name", dirname);
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
	
	public List<DirectoryTree> getDirectorytree(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findAlldirectorytree();	 
	}
	
	public List<IntentTree> getIntenttree(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findAllintenttree();	 
	}
	
	public JSONArray getAlltree(String tenant,String token)
	{
		System.out.println("====GETTING COMPOSER API ADDR====");
		EngineCredential result = new EngineCredential();
		result = engcredsservice.getView(tenant,this.ecId);
		
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no eng credential found.");
        }
		
		List<DirectoryTree> resultdirtree = this.getDirectorytree(tenant);
		List<IntentTree> resultintenttree = this.getIntenttree(tenant);
		
		System.out.println("====CREATING JSONARRAY====");
		
		JSONArray ja = new JSONArray();
		System.out.println("====Lopping Directory====");
		if(resultdirtree == null)
		{
			System.out.println("No Folder Data");
		}
		else
		{
//			resultdirtree.forEach(item->{
//				System.out.println("====CREATING JSONObject====");
//				JSONObject treedata = new JSONObject();
//				
//				System.out.println("====PUT JSONObject====");
//				treedata.put("folderName", item.getFolderName());
//				treedata.put("id", item.getId());
//				treedata.put("parent", item.getParent());
//				treedata.put("previousId", item.getPreviousId());
//				treedata.put("type", item.getType());
//				
//				System.out.println("====PUT JSONArray====");
//				ja.put(treedata);
//			});
			int size = resultdirtree.size();
			System.out.println(size);
			for (int i=0; i<size; i++)
			{
				System.out.println("====CREATING JSONObject===="+resultdirtree.get(i).getFolderName());
				JSONObject treedata = new JSONObject();
				System.out.println("====PUT JSONObject====");
				treedata.put("folderName", resultdirtree.get(i).getFolderName());
				treedata.put("id", resultdirtree.get(i).getId());
				treedata.put("parent", resultdirtree.get(i).getParent());
				treedata.put("previousId", resultdirtree.get(i).getPreviousId());
				treedata.put("type", resultdirtree.get(i).getType());
				treedata.put("folderId", resultdirtree.get(i).getFolderId());
				
				System.out.println("====PUT JSONArray DIREC====");
				ja.put(treedata);
			}
		}
		
		System.out.println("====FINISH JSONArray DIRECTORY====");
		System.out.println(ja);
		
		if(resultintenttree == null)
		{
			System.out.println("No Intent Data");
		}
		else
		{
			System.out.println(resultintenttree.size());
			String localApi = result.getLocalapi();
			resultintenttree.forEach(item->{
				System.out.println("====CREATING JSONObject Intent====");
				JSONObject treedata = new JSONObject();
				
				System.out.println("====PUT JSONObject Intent===="+item.getIntentName());
				treedata.put("intentName", item.getIntentName());
				treedata.put("id", item.getId());
				treedata.put("parent", item.getParent());
				treedata.put("previousId", item.getPreviousId());
				treedata.put("type", item.getType());
				treedata.put("multipleCondition", item.getMultipleCondition());
				treedata.put("intentId", item.getIntentId());
				
				List<Interaction> intres = intservice.getInteractionexpectedintentid(tenant,item.getIntentId()); 
				System.out.println("question size : "+ intres.size());
				int questionSize  = intres.size();
				JSONArray jaQuestion = new JSONArray();
				if(questionSize>0)
				{
					intres.forEach(itemQuestion->{
						System.out.println("====CREATING JSONObject Interaction====");
						JSONObject treedataintr = new JSONObject();
						treedataintr.put("id", itemQuestion.getInteractionid());
						treedataintr.put("question", itemQuestion.getQuestion());
						treedataintr.put("createdOn", itemQuestion.getCreatedtime());
						treedataintr.put("createdBy", itemQuestion.getUsername());
						
						System.out.println("====PUT JSONArray question====");
						jaQuestion.put(treedataintr);
					});
				}
				
				System.out.println("====PUTTING Question to Intent====");
				treedata.put("questions", jaQuestion);
				
				System.out.println("====REQUESTING Composer output to Intent====");
				String hprret ="";
				try {
					hprret = hpr.getUserchanneltokenwithtenantid(localApi+"/response/"+item.getIntentId(),token,tenant);
					//hprret = hpr.ConnectGetTokenchannel(serverChannel+"/auth/usertoken/"+uname,chnlToken);
				}catch(Exception ex)
				{
					System.out.println(ex);
				}
				
				
				System.out.println("composer return is : "+ hprret);
				JSONArray jsonarraycomposer;
				try {
					if(validatejson.isJSONValidarray(hprret))
					{
						
						System.out.println("====PUTTING JSONArray composer====");
						jsonarraycomposer = new JSONArray(hprret);
						//if(jsonarray)
					}
					else
					{
						System.out.println("====PUTTING EMPTY JSONArray composer====");
						jsonarraycomposer = new JSONArray();
					}
				}catch(Exception ex) {
					ex.printStackTrace();
					System.out.println("====PUTTING EMPTY JSONArray composer====");
					jsonarraycomposer = new JSONArray();
				}
				
				System.out.println("====PUTTING JSONArray composer to output====");
				treedata.put("output", jsonarraycomposer);
				
				
				System.out.println("====CREATING JSONArray intent====");
				ja.put(treedata);
			});
		}
		
		System.out.println("====FINISH JSONArray Intent====");
		System.out.println(ja);
		
		
		return ja;
	}
	
	public String mapView(int InitcatID,String tenant)
	{
		//Directory dirresult = dirmapMapper.findOne(InitcatID);
		Directory dirresult = dirservice.getView(tenant,InitcatID);
		//return result;
		if(dirresult == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid parent data");
		}
		
		if(dirresult.getParentid() >0)
		{
			 //$this->createCatmapView($modelfindParentretID->ParentID);
			this.mapView(dirresult.getParentid(),tenant);
			this.retDataPath = this.retDataPath +"->"+ dirresult.getName();
		}
		else if(dirresult.getParentid() <=0)
		{
			//$this->retDataPath = $this->retDataPath .$modelfindParentretID->Name;
			this.retDataPath = this.retDataPath + dirresult.getName();
		}
		
		return this.retDataPath;
	}

}
