package com.egeroo.roocvthree.userrole;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;


public class UserRoleMapperImpl extends BaseDAO implements UserRoleMapper{
	
	private static final Logger log = Logger.getLogger(UserRoleMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public UserRoleMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}
	
	@Override
	public List<UserRole> findAll() {
		System.out.println("role List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<UserRole> userrole = null;
		try{
			UserRoleMapper userroleMapper = sqlSession.getMapper(UserRoleMapper.class);
			userrole = userroleMapper.findAll();
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return userrole;
	}

	@Override
	public UserRole findOne(Integer roleid) {
		System.out.println("role List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserRole userrole = null;
		try{
			UserRoleMapper userroleMapper = sqlSession.getMapper(UserRoleMapper.class);
			userrole = userroleMapper.findOne(roleid);
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserRole) userrole;
	}

	@Override
	public void Save(UserRole userrole) {
		System.out.println("role save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			UserRoleMapper userroleMapper = sqlSession.getMapper(UserRoleMapper.class);
			//userrole = 
			userroleMapper.Save(userrole);
			log.info("insertUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return userrole;
	}
	
	@Override
	public void Update(UserRole userrole) {
		System.out.println("role update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			UserRoleMapper userroleMapper = sqlSession.getMapper(UserRoleMapper.class);
			//userrole = 
			userroleMapper.Update(userrole);
			log.info("insertUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		//return userrole;
	}

	@Override
	public UserRole findOnebyname(String rolename) {
		System.out.println("role List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserRole userrole = null;
		try{
			UserRoleMapper userroleMapper = sqlSession.getMapper(UserRoleMapper.class);
			userrole = userroleMapper.findOnebyname(rolename);
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get userrole by name data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserRole) userrole;
	}

}
