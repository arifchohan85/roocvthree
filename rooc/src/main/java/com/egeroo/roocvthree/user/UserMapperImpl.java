package com.egeroo.roocvthree.user;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileMapper;



public class UserMapperImpl extends BaseDAO implements UserMapper{
	
	private static final Logger log = Logger.getLogger(UserMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public UserMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<User> findAll() {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<User> user = null;
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.findAll();
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return user;
	}

	@Override
	public User findOne(Integer userid) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		User user = null;
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.findOne(userid);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (User) user;
	}
	
	@Override
	public String Save(User user) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//userrole = 
			lastinsertuserid =userMapper.Save(user);
			
			log.debug("insert id is : " + user.getUserid());
			
			//lastinsertuserid = user.getUserid();
			UserProfile userprof = new UserProfile();
			
			if(user.getIsactive()==0)
			{
				userprof.setIsactive(0);
			}
			else if(user.getIsactive()==1)
			{
				userprof.setIsactive(1);
			}
			else
			{
				userprof.setIsactive(0);
			}
			userprof.setUserid(Integer.parseInt(lastinsertuserid));
			userprof.setUsername(user.getUsername());
			userprof.setPasswordfromuser(user.getPassword());
			userprof.setName(" ");
			userprof.setAddress(" ");
			userprof.setEmailaddress(" ");
			userprof.setRoleid(user.getRoleid());
			userprof.setCreatedby(user.getCreatedby());
			userprof.setUpdatedby(user.getUpdatedby());
			userprof.setCreatedtime(user.getCreatedtime());
			userprof.setUpdatedtime(user.getUpdatedtime());
			UserProfileMapper userpMapper = sqlSession.getMapper(UserProfileMapper.class);
			//sqlSession.insert("Save",userprof);
			userpMapper.Save(userprof);
			
			log.info("insertUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
		
	}

	@Override
	public String Update(User user) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//userrole = 
			lastinsertuserid =userMapper.Update(user);
			
			UserProfile userprof = new UserProfile();
			if(user.getIsactive()==0)
			{
				userprof.setIsactive(0);
			}
			else if(user.getIsactive()==1)
			{
				userprof.setIsactive(1);
			}
			else
			{
				userprof.setIsactive(0);
			}
			userprof.setUserid(Integer.parseInt(lastinsertuserid));
			userprof.setUsername(user.getUsername());
			userprof.setPasswordfromuser(user.getPassword());
			//userprof.setName(" ");
			//userprof.setAddress(" ");
			//userprof.setEmailaddress(" ");
			userprof.setRoleid(user.getRoleid());
			
			userprof.setUpdatedby(user.getUpdatedby());
			
			userprof.setUpdatedtime(user.getUpdatedtime());
			UserProfileMapper userpMapper = sqlSession.getMapper(UserProfileMapper.class);
			//sqlSession.insert("Save",userprof);
			userpMapper.Updatefromuser(userprof);
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public User findByUsername(String username) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		User user = null;
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.findByUsername(username);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (User) user;
	}
	
	@Override
	public User findByUsernameandPassword(String username,String password) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		User user = null;
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.findByUsernameandPassword(username,password);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (User) user;
	}

	@Override
	public String Updatefromprofile(User user) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//userrole = 
			lastinsertuserid = userMapper.Updatefromprofile(user);
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return lastinsertuserid;
		return lastinsertuserid;
	}

	@Override
	public String Updatepassword(User user) {
		System.out.println("user update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//userrole = 
			lastinsertuserid = userMapper.Updatepassword(user);
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return lastinsertuserid;
		return lastinsertuserid;
	}

}
