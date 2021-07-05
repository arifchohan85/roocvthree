package com.egeroo.roocvthree.loginform;

import org.springframework.stereotype.Service;






@Service
public class LoginFormService {
	
	public LoginForm findByUsernameandPassword(String tenant,String uname,String pswd) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsernameandPassword(uname,pswd);  
    }
	
	public LoginFormldap findByUsernameandPasswordldap(String tenant,String uname,String pswd) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsernameandPasswordldap(uname,pswd);  
    }
	
	public LoginFormldap findByUsernameldap(String tenant,String uname) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsernameldap(uname);  
    }
	
	public LoginForm findByUsername(String tenant,String uname) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUsername(uname);  
    }
	
	public LoginForm findByUseridandPassword(String tenant,int userid,String pswd) {
		LoginFormMapper appMapper = new LoginFormMapperImpl(tenant);
		return appMapper.findByUseridandPassword(userid,pswd);  
    }
	
	public boolean isUserExist(String tenant,LoginForm user) {
		if(findByUsernameandPassword(tenant,user.getUsername(),user.getPassword()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistid(String tenant,LoginForm user) {
		if(findByUseridandPassword(tenant,user.getUserid(),user.getPassword()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistun(String tenant,LoginFormldap user) {
		if(findByUsername(tenant,user.getUsername()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistunldap(String tenant,LoginFormldap user) {
		if(findByUsernameldap(tenant,user.getUsername()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }
	
	public boolean isUserExistunpwdldap(String tenant,LoginFormldap user) {
		if(findByUsernameandPasswordldap(tenant,user.getUsername(),user.getPassword()) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
        //return findByUsernameandPassword(tenant,user.getUsername(),user.getPassword())!=null;
    }

}
