package com.egeroo.roocvthree.changepasswordcycle;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class ChangePasswordCycleService {
	
	public List<ChangePasswordCycle> getIndex(String tenant) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public ChangePasswordCycle getView(String tenant,int ChangePasswordCycleid) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.findOne(ChangePasswordCycleid);	 
	}
	
	public ChangePasswordCycle getCreate(String tenant,ChangePasswordCycle ChangePasswordCycle) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.Save(ChangePasswordCycle);
	}
	
	public ChangePasswordCycle getUpdate(String tenant,ChangePasswordCycle ChangePasswordCycle) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.Update(ChangePasswordCycle);
	}
	
	public ChangePasswordCycle getCountdata(String tenant,int userid) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.findCountdata(userid);	 
	}
	
	public ChangePasswordCycle getCheckdata(String tenant,ChangePasswordCycle ChangePasswordCycle) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.findCheckdata(ChangePasswordCycle);
	}
	
	public List<ChangePasswordCycle> getDeletebyuser(String tenant,int userid) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.deleteAllbyuser(userid);	 
	}
	
	public boolean isUserPassword(String tenant,ChangePasswordCycle cpc) {
		if(getCheckdata(tenant,cpc) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public ChangePasswordCycle getViewlastpassword(String tenant,int userid) {
		ChangePasswordCycleMapper appMapper = new ChangePasswordCycleMapperImpl(tenant);
		return appMapper.findLastpassworddata(userid);	 
	}

}
