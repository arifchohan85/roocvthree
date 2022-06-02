package com.egeroo.roocvthree.userprofile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.stereotype.Service;




@Service
public class UserProfileService {
	
	public List<UserProfileIndex> getIndex(String tenant) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	@SuppressWarnings("rawtypes")
	public List<LinkedHashMap> getIndexv3(String tenant) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findAllv3();	 
	}

	public List<UserProfile> getIndexother(String tenant) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findAllother();	 
	}
	
	public UserProfileView getView(String tenant,int userprofileid) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findOne(userprofileid);
	}
	
	public UserProfile getViewbyuserid(String tenant,int userid) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findByuserid(userid);
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap getViewbyuseridv3(String tenant,int userid) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findByuseridv3(userid);
	}

	public UserProfile getPasswordviewbyuserid(String tenant,int userid) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findpasswordByuserid(userid);
	}
	
	public UserProfile getViewbyuserchannelid(String tenant,int userchannelid) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.findByuserchannelid(userchannelid);
	}
	
	public UserProfile getCreate(String tenant,UserProfile userprofile) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.Save(userprofile);
	}
	
	public UserProfile getUpdate(String tenant,UserProfile userprofile) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.Update(userprofile);
	}
	
	public void getUpdatepassword(String tenant,UserProfile userprofile) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		//return 
		appMapper.Updatepassword(userprofile);
	}
	
	public UserProfile findByEmailaddress(String tenant,String email) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		//return 
		return appMapper.findByEmailaddress(email);
        /*for(User user : users){
            if(user.getUsername().equalsIgnoreCase(uname)){
                return user;
            }
        }
        return null;*/
    }
	
	public boolean isUserExist(String tenant,String email) {
        //return findByEmailaddress(tenant,email)!=null;
		if(findByEmailaddress(tenant,email) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
    }
	
	public UserProfile findByUsername(String tenant,String username) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		//return 
		return appMapper.findByUsername(username);
        /*for(User user : users){
            if(user.getUsername().equalsIgnoreCase(uname)){
                return user;
            }
        }
        return null;*/
    }
	
	public boolean isUserExistun(String tenant,String username) {
        //return findByEmailaddress(tenant,email)!=null;
		if(findByUsername(tenant,username) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
    }
	
	public void getUpdatefromuserinactive(String tenant,UserProfile userprofile) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		//return 
		appMapper.Updatefromuserinactive(userprofile);
	}
	
	public UserProfileAttribute getCreateattr(String tenant,UserProfileAttribute userprofileattribute) {
		UserProfileMapper appMapper = new UserProfileMapperImpl(tenant);
		return appMapper.Saveattr(userprofileattribute);
	}

}
