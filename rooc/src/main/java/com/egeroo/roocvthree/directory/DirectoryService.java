package com.egeroo.roocvthree.directory;

import java.util.List;

import org.springframework.stereotype.Service;




@Service
public class DirectoryService {
	
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
	
	public String getSaveroocenginecreatesync(String tenant,Directory directory) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.Createroocenginesync(directory); 
	}
	
	
	public List<Directory> getDirectoryvoicenotgenerated(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findDirectorynotgenvoice();	 
	}
	
	public List<Directory> getDirectorynotgenml(String tenant) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.findDirectorynotgenml();	 
	}
	
	public String getUpdatevoiceonly(String tenant,Directory directory) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.Updatevoiceonly(directory);
	}
	
	public String getUpdatemlrtsaonly(String tenant,Directory directory) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.Updatemlrtsaonly(directory);
	}
	
	public String getUpdatertsaonly(String tenant,Directory directory) {
		DirectoryMapper appMapper = new DirectoryMapperImpl(tenant);
		return appMapper.Updatertsaonly(directory);
	}

}
