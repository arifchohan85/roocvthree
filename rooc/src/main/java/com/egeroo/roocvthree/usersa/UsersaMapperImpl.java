package com.egeroo.roocvthree.usersa;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.userprofile.UserProfileMapperImpl;
import com.egeroo.roocvthree.userrole.UserRoleService;


public class UsersaMapperImpl  extends BaseDAO implements UsersaMapper{
	
	private static final Logger log = Logger.getLogger(UserProfileMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	HttpPostReq hpr = new HttpPostReq();
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    UserRoleService urservice = new UserRoleService(); 
	
	public UsersaMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Usersa> findAll() {
		System.out.println("usersa List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Usersa> usersa = null;
		try{
			UsersaMapper usersaMapper = sqlSession.getMapper(UsersaMapper.class);
			usersa = usersaMapper.findAll();
			log.info("getUsersa data");
		}catch(PersistenceException e){
			log.debug(e + "error get Usersa data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return usersa;
	}

	@Override
	public Usersa findOne(Integer usersaid) {
		System.out.println("Usersa List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Usersa usersa = null;
		try{
			UsersaMapper usersaMapper = sqlSession.getMapper(UsersaMapper.class);
			usersa = usersaMapper.findOne(usersaid);
			log.info("getUsersa data");
		}catch(PersistenceException e){
			log.debug(e + "error get Usersa data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return usersa;
	}

	@Override
	public String Save(Usersa usersa) {
		System.out.println("Usersa save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			UsersaMapper usersaMapper = sqlSession.getMapper(UsersaMapper.class);
			lastinsertuserid =usersaMapper.Save(usersa);
			log.info("insert Usersa data");
		}catch(PersistenceException e){
			log.debug(e + "error insert Usersa data");
			sqlSession.rollback();
			e.printStackTrace();
		} 
		finally{
			sqlSession.close();
		}
		
		return lastinsertuserid;
	}

}
