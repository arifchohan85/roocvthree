package com.egeroo.roocvthree.roocconfig;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class RoocConfigService {
	
	public List<RoocConfig> getIndex(String tenant) {
		RoocConfigMapper appMapper = new RoocConfigMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public RoocConfig getView(String tenant,int roocconfigid) {
		RoocConfigMapper appMapper = new RoocConfigMapperImpl(tenant);
		return appMapper.findOne(roocconfigid);
	}
	
	public RoocConfig getCreate(String tenant,RoocConfig roocconfig) {
		RoocConfigMapper appMapper = new RoocConfigMapperImpl(tenant);
		return appMapper.Save(roocconfig);
	}
	
	public RoocConfig getUpdate(String tenant,RoocConfig roocconfig) {
		RoocConfigMapper appMapper = new RoocConfigMapperImpl(tenant);
		return appMapper.Update(roocconfig);
	}
	
	public RoocConfig getDelete(String tenant,int roocconfigid) {
		RoocConfigMapper appMapper = new RoocConfigMapperImpl(tenant);
		return appMapper.deleteOne(roocconfigid);
	}
	
	public RoocConfig findByconfigkey(String tenant,String configkey) {
		RoocConfigMapper appMapper = new RoocConfigMapperImpl(tenant);
		return appMapper.findByconfigkey(configkey);  
    }

}
