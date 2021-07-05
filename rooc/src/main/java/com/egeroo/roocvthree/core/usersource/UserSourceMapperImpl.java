package com.egeroo.roocvthree.core.usersource;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;



public class UserSourceMapperImpl extends BaseDAO implements UserSourceMapper{
	
	private static final Logger log = Logger.getLogger(UserSourceMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public UserSourceMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<UserSource> findAll() {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<UserSource> user = null;
		try{
			UserSourceMapper userMapper = sqlSession.getMapper(UserSourceMapper.class);
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
	public UserSource findByAuthkey(String authkey) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserSource user = null;
		try{
			UserSourceMapper userMapper = sqlSession.getMapper(UserSourceMapper.class);
			user = userMapper.findByAuthkey(authkey);
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
	public UserSource findByAuthkeyandUserid(String authkey, int userid) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserSource user = null;
		try{
			UserSourceMapper userMapper = sqlSession.getMapper(UserSourceMapper.class);
			user = userMapper.findByAuthkeyandUserid(authkey,userid);
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
	public String Save(UserSource user) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			UserSourceMapper userMapper = sqlSession.getMapper(UserSourceMapper.class);
			//userrole = 
			lastinsertuserid =userMapper.Save(user);
			log.debug("insert id is : " + lastinsertuserid);
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
	public UserSource Updatelogout(UserSource usersource) {
		System.out.println("UserSource update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserSource ec = null;
		try{
			UserSourceMapper UsersourceMapper = sqlSession.getMapper(UserSourceMapper.class);
			ec = UsersourceMapper.Updatelogout(usersource);
			log.info("updateUserSource data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
