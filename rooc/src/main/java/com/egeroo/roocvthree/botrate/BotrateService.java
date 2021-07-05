package com.egeroo.roocvthree.botrate;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class BotrateService {
	
	public List<Botrate> getIndex(String tenant) {
		BotrateMapper appMapper = new BotrateMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public Botrate getView(String tenant,int id) {
		BotrateMapper appMapper = new BotrateMapperImpl(tenant);
		return appMapper.findOne(id);
	}
	
	public Botrate getCreate(String tenant,Botrate botrate) {
		BotrateMapper appMapper = new BotrateMapperImpl(tenant);
		return appMapper.Save(botrate);
	}
	
	public Botrate getUpdate(String tenant,Botrate botrate) {
		BotrateMapper appMapper = new BotrateMapperImpl(tenant);
		return appMapper.Update(botrate);
	}

}