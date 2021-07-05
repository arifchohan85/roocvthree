package com.egeroo.roocvthree.enginecredential;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;




public class EngineCredentialMapperImpl extends BaseDAO implements EngineCredentialMapper{
	
	private static final Logger log = Logger.getLogger(EngineCredentialMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public EngineCredentialMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<EngineCredential> findAll() {
		System.out.println("Engine Creds Find ALL() ");
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<EngineCredential> ec = null;
		try{
			EngineCredentialMapper ecMapper = sqlSession.getMapper(EngineCredentialMapper.class);
			ec = ecMapper.findAll();
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public EngineCredential findOne(Integer enginecredentialid) {
		System.out.println("Engine Creds Find One() ");
		System.out.println("ec List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		EngineCredential ec = null;
		try{
			EngineCredentialMapper ecMapper = sqlSession.getMapper(EngineCredentialMapper.class);
			ec = ecMapper.findOne(enginecredentialid);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (EngineCredential) ec;
	}

	@Override
	public String Save(EngineCredential enginecredential) {
		System.out.println("Engine Creds SAVE() ");
		System.out.println("ec save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			EngineCredentialMapper ecMapper = sqlSession.getMapper(EngineCredentialMapper.class);
			//userrole = 
			lastinsertuserid =ecMapper.Save(enginecredential);
			
			log.debug("insert id is : " + lastinsertuserid);
			
			log.info("insertec data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public String Update(EngineCredential enginecredential) {
		System.out.println("Engine Creds UPDATE() ");
		System.out.println("ec update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			EngineCredentialMapper ecMapper = sqlSession.getMapper(EngineCredentialMapper.class);
			//userrole = 
			lastinsertuserid =ecMapper.Update(enginecredential);
			
			log.info("update data");
		}catch(PersistenceException e){
			log.debug(e + "error get ec data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

}
