package com.egeroo.roocvthree.intentgroup;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class IntentGroupService {
	
	public List<IntentGroup> getIndex(String tenant) {
		IntentGroupMapper appMapper = new IntentGroupMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public IntentGroup getView(String tenant,int IntentGroupid) {
		IntentGroupMapper appMapper = new IntentGroupMapperImpl(tenant);
		return appMapper.findOne(IntentGroupid);
	}
	
	public void getCreate(String tenant,IntentGroup Intentgroup) {
		IntentGroupMapper appMapper = new IntentGroupMapperImpl(tenant);
		//return 
		appMapper.Save(Intentgroup);
	}
	
	public void getUpdate(String tenant,IntentGroup Intentgroup) {
		IntentGroupMapper appMapper = new IntentGroupMapperImpl(tenant);
		//return 
		appMapper.Update(Intentgroup);
	}

}
