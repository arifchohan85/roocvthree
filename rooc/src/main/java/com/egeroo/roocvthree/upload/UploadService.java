package com.egeroo.roocvthree.upload;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class UploadService {
	public List<Upload> getIndex(String tenant) {
		UploadMapper appMapper = new UploadMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public Upload getView(String tenant,int uploadid) {
		UploadMapper appMapper = new UploadMapperImpl(tenant);
		return appMapper.findOne(uploadid);
		 
	}
	
	public Upload getCreate(String tenant,Upload upload) {
		UploadMapper appMapper = new UploadMapperImpl(tenant);
		return appMapper.Save(upload);
	}
	
	public Upload getUpdate(String tenant,Upload upload) {
		UploadMapper appMapper = new UploadMapperImpl(tenant);
		return appMapper.Update(upload);
	}

}
