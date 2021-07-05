package com.egeroo.roocvthree.changepasswordcyclelimit;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class ChangePasswordCycleLimitService {
	
	public List<ChangePasswordCycleLimit> getIndex(String tenant) {
		ChangePasswordCycleLimitMapper appMapper = new ChangePasswordCycleLimitMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public ChangePasswordCycleLimit getView(String tenant,int ChangePasswordCycleLimitid) {
		ChangePasswordCycleLimitMapper appMapper = new ChangePasswordCycleLimitMapperImpl(tenant);
		return appMapper.findOne(ChangePasswordCycleLimitid);	 
	}
	
	public ChangePasswordCycleLimit getCreate(String tenant,ChangePasswordCycleLimit ChangePasswordCycleLimit) {
		ChangePasswordCycleLimitMapper appMapper = new ChangePasswordCycleLimitMapperImpl(tenant);
		return appMapper.Save(ChangePasswordCycleLimit);
	}
	
	public ChangePasswordCycleLimit getUpdate(String tenant,ChangePasswordCycleLimit ChangePasswordCycleLimit) {
		ChangePasswordCycleLimitMapper appMapper = new ChangePasswordCycleLimitMapperImpl(tenant);
		return appMapper.Update(ChangePasswordCycleLimit);
	}
	
	public ChangePasswordCycleLimit getViewlimit(String tenant) {
		ChangePasswordCycleLimitMapper appMapper = new ChangePasswordCycleLimitMapperImpl(tenant);
		return appMapper.findOnelimit();	 
	}

}
