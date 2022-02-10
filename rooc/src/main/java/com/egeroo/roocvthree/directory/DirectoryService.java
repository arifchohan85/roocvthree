package com.egeroo.roocvthree.directory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.core.error.CoreException;




@Service
public class DirectoryService {
	
	public String retDataPath;
	
	public List<Directory> getIndex(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public List<Directory> getDirectoryextract(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.extractDirectory();	 
	}
	
	public Directorylistall getListdirectory(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findAlldirectory();	 
	}
	
	public List<DirectoryIntent> getDirectoryintent(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findAlldirectoryintent();	 
	}
	
	public List<Directory> getDirectory(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findDirectory();	 
	}
	
	public List<Directory> getDirectoryenum(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findDirectoryenum();	 
	}
	
	public Directory getView(String tenant,int ecid) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findOne(ecid); 
	}
	
	public Directory getViewbyfaq(String tenant,String faq) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findOnebyfaq(faq); 
	}
	
	public Directory getViewparentbyname(String tenant,String parentname) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findParentbyname(parentname); 
	}
	
	public Directory getViewdirectorybyname(String tenant,String directoryname) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findDirectorybyname(directoryname); 
	}
	
	public Directory getViewdirectorybynameandparentid(String tenant,String directoryname,int parentid) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findDirectorybynameandparentid(directoryname,parentid); 
	}
	
	/*
	 public Directory getDelete(String tenant,int directoryid) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.deleteOne(directoryid);
	}
	*/
	public List<Directory> getDelete(String tenant,int directoryid,String Token) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.deleteOne(directoryid,Token);
	}
	
	public List<Directory> getDeleterecursive(String tenant,int directoryid,String Token) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.deleteRecursive(directoryid,Token);
	}
	
	public String getCreate(String tenant,Directory directory) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.Save(directory); 
	}
	
	public String getUpdate(String tenant,Directory directory) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.Update(directory);
	}
	
	
	

	/*start v3*/
	public List<DirectoryIntentv3> getDirectoryintentv3(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findAlldirectoryintentv3();
	}
	
	public String mapView(int InitcatID,String tenant)
	{
		//Directory dirresult = dirmapMapper.findOne(InitcatID);
		Directory dirresult = this.getView(tenant,InitcatID);
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
	
	/*end v3*/

}
