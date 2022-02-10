package com.egeroo.roocvthree.loginform;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


import com.egeroo.roocvthree.core.base.BaseDAO;


public class LoginFormMapperImpl extends BaseDAO implements LoginFormMapper{
	
	private static final Logger log = Logger.getLogger(LoginFormMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public LoginFormMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public LoginForm findByUsernameandPassword(String username, String password) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		LoginForm user = null;
		try{
			LoginFormMapper userMapper = sqlSession.getMapper(LoginFormMapper.class);
			user = userMapper.findByUsernameandPassword(username,password);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (LoginForm) user;
	}

	@Override
	public LoginForm findByUseridandPassword(Integer userid, String password) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		LoginForm user = null;
		try{
			LoginFormMapper userMapper = sqlSession.getMapper(LoginFormMapper.class);
			user = userMapper.findByUseridandPassword(userid,password);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (LoginForm) user;
	}

	@Override
	public LoginFormldap findByUsernameldap(String username) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		LoginFormldap user = null;
		try{
			LoginFormMapper userMapper = sqlSession.getMapper(LoginFormMapper.class);
			user = userMapper.findByUsernameldap(username);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (LoginFormldap) user;
	}

	@Override
	public LoginFormldap findByUsernameandPasswordldap(String username, String password) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		LoginFormldap user = null;
		try{
			LoginFormMapper userMapper = sqlSession.getMapper(LoginFormMapper.class);
			user = userMapper.findByUsernameandPasswordldap(username,password);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (LoginFormldap) user;
	}

	@Override
	public LoginForm findByUsername(String username) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		LoginForm user = null;
		try{
			LoginFormMapper userMapper = sqlSession.getMapper(LoginFormMapper.class);
			user = userMapper.findByUsername(username);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (LoginForm) user;
	}

	@Override
	public LoginForm findByUserid(int userid) {
		System.out.println("user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		LoginForm user = null;
		try{
			LoginFormMapper userMapper = sqlSession.getMapper(LoginFormMapper.class);
			user = userMapper.findByUserid(userid);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (LoginForm) user;
	}

}
