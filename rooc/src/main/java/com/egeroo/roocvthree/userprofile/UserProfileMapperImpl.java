package com.egeroo.roocvthree.userprofile;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.user.User;
import com.egeroo.roocvthree.user.UserMapper;
import com.egeroo.roocvthree.userrole.UserRole;
import com.egeroo.roocvthree.userrole.UserRoleMapper;
import com.egeroo.roocvthree.userrole.UserRoleService;
import com.egeroo.roocvthree.usersa.Usersa;
import com.egeroo.roocvthree.usersa.UsersaMapper;

public class UserProfileMapperImpl extends BaseDAO implements UserProfileMapper{
	
	private static final Logger log = Logger.getLogger(UserProfileMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	HttpPostReq hpr = new HttpPostReq();
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    UserRoleService urservice = new UserRoleService(); 
	
	public UserProfileMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<UserProfileIndex> findAll() {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<UserProfileIndex> userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findAll();
			log.info("getuserprofile data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return userprofile;
	}
	
	@Override
	public List<LinkedHashMap> findAllv3() {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<LinkedHashMap> userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findAllv3();
			log.info("getuserprofile data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return userprofile;
	}
	@Override
	public List<UserProfile> findAllother() {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<UserProfile> userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findAllother();
			log.info("getuserprofile other data");
		}catch(PersistenceException e){
			log.debug(e + "error get userother data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return userprofile;
	}

	@Override
	public UserProfileView findOne(Integer userprofileid) {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserProfileView userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findOne(userprofileid);
			log.info("getCompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserProfileView) userprofile;
	}

	@Override
	public UserProfile Save(UserProfile userprofile) {
		System.out.println("userprofile save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		UserProfile userprofsave = null;
		try{
			
			
			
			
			
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			
			UsersaMapper usersaMapper = sqlSession.getMapper(UsersaMapper.class);
			
			UserRole userrole = new UserRole();
			UserRoleMapper urMapper = sqlSession.getMapper(UserRoleMapper.class);
			
			userrole = urMapper.findOne(userprofile.getRoleid());
			
			if(userrole == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid userrole id or name.");
			}
			
			
			boolean isEmpty = userprofile.getUsername() == null || userprofile.getUsername().trim().length() == 0;
			
			if(isEmpty)
			{
				userprofile.setUsername(userprofile.getEmailaddress());
			}
			/*else if(userprofile.getUsername().isEmpty())
			{
				userprofile.setUsername(userprofile.getEmailaddress());
			}*/
			
			
			
			Usersa usersa = new Usersa();
			usersa.setAction("Create");
			usersa.setName(userprofile.getName());
			usersa.setUserid(userprofile.getEmailaddress());
			usersa.setUsername(userprofile.getUsername());
			usersa.setNewvalue(userrole.getRolename());
			usersa.setOldvalue("-");
			usersa.setRole(userrole.getRolename());
			usersa.setCreatedby(userprofile.getCreatedby());
			usersa.setCreatedtime(userprofile.getCreatedtime());
			usersa.setUpdatedby(userprofile.getUpdatedby());
			usersa.setUpdatedtime(userprofile.getUpdatedtime());
			usersaMapper.Save(usersa);
			
			User user = new User();
			/*if(userprofile.getIsactive()==0)
			{
				user.setIsactive(0);
			}
			else if(userprofile.getIsactive()==1)
			{
				user.setIsactive(1);
			}
			else
			{
				user.setIsactive(0);
			}*/
			user.setIsactive(userprofile.getIsactive());
			user.setRoleid(userprofile.getRoleid());
			user.setUsername(userprofile.getUsername());
			user.setPasswordfromprofile(userprofile.getPassword());
			
			user.setCreatedby(userprofile.getCreatedby());
			user.setCreatedtime(userprofile.getCreatedtime());
			user.setUpdatedby(userprofile.getUpdatedby());
			user.setUpdatedtime(userprofile.getUpdatedtime());
			lastinsertuserid =userMapper.Save(user);
			
			userprofile.setUserid(Integer.parseInt(lastinsertuserid));
			//userprofile.setUsername(userprofile.getUsername());
			userprofsave =userprofileMapper.Save(userprofile);
			
			
			
			
			
			
			log.info("insertuser profile data");
		}catch(PersistenceException e){
			log.debug(e + "error insert userprofile data");
			sqlSession.rollback();
			e.printStackTrace();
		} 
		finally{
			sqlSession.close();
		}
		
		return userprofsave;
		
	}

	@Override
	public UserProfile Update(UserProfile userprofile) {
		System.out.println("userprofile update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		UserProfile userprofsave=null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			Usersa usersa = new Usersa();
			
			
			UsersaMapper usersaMapper = sqlSession.getMapper(UsersaMapper.class);

			UserProfileView userprofileold = new UserProfileView();
			userprofileold = userprofileMapper.findOne(userprofile.getUserprofileid());
			
			if(userprofileold == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid user profile id.");
			}
			
			UserRole userrole = new UserRole();
			UserRole userroleold = new UserRole();
			UserRoleMapper urMapper = sqlSession.getMapper(UserRoleMapper.class);
			
			userroleold = urMapper.findOne(userprofileold.getRoleId());
			
			
			if(userroleold == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid userrole id or name.");
			}
			
			usersa.setOldvalue(userroleold.getRolename());
			
			userrole = urMapper.findOne(userprofile.getRoleid());
			
			if(userrole == null)
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "invalid userrole id or name.");
			}
			
			
			boolean isEmpty = userprofile.getUsername() == null || userprofile.getUsername().trim().length() == 0;
			
			if(isEmpty)
			{
				userprofile.setUsername(userprofile.getEmailaddress());
			}
			/*else if(userprofile.getUsername().isEmpty())
			{
				userprofile.setUsername(userprofile.getEmailaddress());
			}*/
			
			usersa.setAction("Update");
			usersa.setName(userprofile.getName());
			usersa.setUserid(userprofile.getEmailaddress());
			usersa.setUsername(userprofile.getUsername());
			usersa.setNewvalue(userrole.getRolename());
			
			usersa.setRole(userrole.getRolename());
			usersa.setCreatedby(userprofile.getCreatedby());
			usersa.setCreatedtime(userprofile.getCreatedtime());
			usersa.setUpdatedby(userprofile.getUpdatedby());
			usersa.setUpdatedtime(userprofile.getUpdatedtime());
			usersaMapper.Save(usersa);
			
			User user = new User();
			//user.setUserid(userprofile.getUserid());
			user.setUserid(userprofileold.getUserId());
			user.setUsername(userprofile.getUsername());
			user.setPasswordfromprofile(userprofile.getPassword());
			//userprof.setName(" ")
			//userprof.setAddress(" ");
			//userprof.setEmailaddress(" ");
			/*if(userprofile.getIsactive()==0)
			{
				user.setIsactive(0);
			}
			else if(userprofile.getIsactive()==1)
			{
				user.setIsactive(1);
			}
			else
			{
				user.setIsactive(0);
			}*/
			user.setIsactive(userprofile.getIsactive());
			user.setRoleid(userprofile.getRoleid());
			
			user.setUpdatedby(userprofile.getUpdatedby());
			user.setUpdatedtime(userprofile.getUpdatedtime());
			UserMapper userpMapper = sqlSession.getMapper(UserMapper.class);
			//sqlSession.insert("Save",userprof);
			userpMapper.Updatefromprofile(user);
			
			userprofsave = userprofileMapper.Update(userprofile);
			
			log.info("profile data");
		}catch(PersistenceException e){
			log.debug(e + "error insert userprofile data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return userprofsave;
	}

	@Override
	public UserProfile findByEmailaddress(String email) {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserProfile userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findByEmailaddress(email);
			log.info("profile data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserProfile) userprofile;
	}
	
	

	@Override
	public void Updatefromuser(UserProfile userprofile) {
		System.out.println("userprofile update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			//userrole = 
			userprofileMapper.Updatefromuser(userprofile);
			log.info("profile data");
		}catch(PersistenceException e){
			log.debug(e + "error insert userprofile data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
	}

	@Override
	public UserProfile findByuserid(Integer userid) {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserProfile userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findByuserid(userid);
			log.info("getCompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserProfile) userprofile;
	}

	@Override
	public HashMap findByuseridv3(Integer userid) {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		HashMap userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findByuseridv3(userid);
			log.info("getCompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return userprofile;
	}

	@Override
	public void Updatepassword(UserProfile userprofile) {
		System.out.println("userprofile update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			//userrole = 
			userprofileMapper.Updatepassword(userprofile);
			log.info("profile data");
		}catch(PersistenceException e){
			log.debug(e + "error insert userprofile data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
		
	}

	@Override
	public UserProfile findByUsername(String username) {
		System.out.println("userprofile List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserProfile userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findByUsername(username);
			log.info("profile data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserProfile) userprofile;
	}

	@Override
	public UserProfile findByuserchannelid(Integer userchannelid) {
		System.out.println("userprofile by findByuserchannelid List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserProfile userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findByuserchannelid(userchannelid);
			log.info("getfindByuserchannelid data");
		}catch(PersistenceException e){
			log.debug(e + "error get findByuserchannelid data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserProfile) userprofile;
	}

	@Override
	public UserProfile findpasswordByuserid(Integer userid) {
		System.out.println("userprofile List find password : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserProfile userprofile = null;
		try{
			UserProfileMapper userprofileMapper = sqlSession.getMapper(UserProfileMapper.class);
			userprofile = userprofileMapper.findpasswordByuserid(userid);
			log.info("get password data data");
		}catch(PersistenceException e){
			log.debug(e + "error get user password data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserProfile) userprofile;
	}

}