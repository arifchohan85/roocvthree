package com.egeroo.roocvthree.changepasswordcyclelimit;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.IntentService;

public class ChangePasswordCycleLimitMapperImpl  extends BaseDAO implements ChangePasswordCycleLimitMapper  {

	private static final Logger log = Logger.getLogger(ChangePasswordCycleLimitMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    IntentService intservice = new IntentService();
    ValidationJson validatejson = new ValidationJson();
    
    public ChangePasswordCycleLimitMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}
	
	@Override
	public List<ChangePasswordCycleLimit> findAll() {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ChangePasswordCycleLimit> ec = null;
		try{
			ChangePasswordCycleLimitMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleLimitMapper.class);
			ec = ecMapper.findAll();
			log.info("getChangePasswordCycle data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public ChangePasswordCycleLimit findOne(Integer changepasswordcyclelimitid) {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycleLimit ec = null;
		try{
			ChangePasswordCycleLimitMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleLimitMapper.class);
			ec = ecMapper.findOne(changepasswordcyclelimitid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (ChangePasswordCycleLimit) ec;
	}

	@Override
	public ChangePasswordCycleLimit Save(ChangePasswordCycleLimit ChangePasswordCycleLimit) {
		System.out.println("ChangePasswordCycle save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycleLimit ec = null;
		try{
			ChangePasswordCycleLimitMapper ChangePasswordCycleLimitMapper = sqlSession.getMapper(ChangePasswordCycleLimitMapper.class);
			//userrole = 
			ec = ChangePasswordCycleLimitMapper.Save(ChangePasswordCycleLimit);
			log.info("insertChangePasswordCycle data");
		}catch(PersistenceException e){
			log.debug(e + "error insert ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public ChangePasswordCycleLimit Update(ChangePasswordCycleLimit ChangePasswordCycleLimit) {
		System.out.println("ChangePasswordCycle save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycleLimit ec = null;
		try{
			ChangePasswordCycleLimitMapper ChangePasswordCycleMapper = sqlSession.getMapper(ChangePasswordCycleLimitMapper.class);
			//userrole = 
			ec = ChangePasswordCycleMapper.Update(ChangePasswordCycleLimit);
			log.info("insertChangePasswordCycle data");
		}catch(PersistenceException e){
			log.debug(e + "error update ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public ChangePasswordCycleLimit findOnelimit() {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycleLimit ec = null;
		try{
			ChangePasswordCycleLimitMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleLimitMapper.class);
			ec = ecMapper.findOnelimit();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (ChangePasswordCycleLimit) ec;
	}

}
