package com.egeroo.roocvthree.user;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	
	public List<User> getIndex(String tenant) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public User getView(String tenant,int userid) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		return appMapper.findOne(userid);
		 
	}
	
	public String getCreate(String tenant,User user) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		return appMapper.Save(user); 
		//appMapper.Save(user);
		//return user.getUserid();
	}
	
	public String getUpdate(String tenant,User user) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		return appMapper.Update(user);
	}
	
	public String getUpdatepassword(String tenant,User user) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		return appMapper.Updatepassword(user);
	}
	
	public User findByUsername(String tenant,String uname) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		//return 
		return appMapper.findByUsername(uname);
        /*for(User user : users){
            if(user.getUsername().equalsIgnoreCase(uname)){
                return user;
            }
        }
        return null;*/
    }
	
	public User findByUsernameandPassword(String tenant,String uname,String password) {
		UserMapper appMapper = new UserMapperImpl(tenant);
		//return 
		return appMapper.findByUsernameandPassword(uname,password);
        /*for(User user : users){
            if(user.getUsername().equalsIgnoreCase(uname)){
                return user;
            }
        }
        return null;*/
    }
	
	public boolean isUserExist(String tenant,User user) {
        return findByUsername(tenant,user.getUsername())!=null;
    }
	
	public boolean isLoginExist(String tenant,User user) {
        return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }

}
