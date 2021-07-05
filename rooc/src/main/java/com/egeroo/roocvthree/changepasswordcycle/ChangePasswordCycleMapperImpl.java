package com.egeroo.roocvthree.changepasswordcycle;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.IntentService;


public class ChangePasswordCycleMapperImpl  extends BaseDAO implements ChangePasswordCycleMapper {
	
	private static final Logger log = Logger.getLogger(ChangePasswordCycleMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    IntentService intservice = new IntentService();
    ValidationJson validatejson = new ValidationJson();
    
    public ChangePasswordCycleMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}
    
    @Override
	public List<ChangePasswordCycle> findAll() {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ChangePasswordCycle> ec = null;
		try{
			ChangePasswordCycleMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
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
	public ChangePasswordCycle findOne(Integer ChangePasswordCycleid) {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycle ec = null;
		try{
			ChangePasswordCycleMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			ec = ecMapper.findOne(ChangePasswordCycleid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (ChangePasswordCycle) ec;
	}

	@Override
	public ChangePasswordCycle Save(ChangePasswordCycle ChangePasswordCycle) {
		System.out.println("ChangePasswordCycle save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycle ec = null;
		try{
			ChangePasswordCycleMapper ChangePasswordCycleMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			//userrole = 
			ec = ChangePasswordCycleMapper.Save(ChangePasswordCycle);
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
	public ChangePasswordCycle Update(ChangePasswordCycle ChangePasswordCycle) {
		System.out.println("ChangePasswordCycle save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycle ec = null;
		try{
			ChangePasswordCycleMapper ChangePasswordCycleMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			//userrole = 
			ec = ChangePasswordCycleMapper.Update(ChangePasswordCycle);
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
	public ChangePasswordCycle findCountdata(Integer userid) {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycle ec = null;
		try{
			ChangePasswordCycleMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			ec = ecMapper.findCountdata(userid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (ChangePasswordCycle) ec;
	}

	@Override
	public ChangePasswordCycle findCheckdata(ChangePasswordCycle ChangePasswordCycle) {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycle ec = null;
		try{
			ChangePasswordCycleMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			ec = ecMapper.findCheckdata(ChangePasswordCycle);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (ChangePasswordCycle) ec;
	}

	@Override
	public List<ChangePasswordCycle> deleteAllbyuser(Integer userid) {
		System.out.println("del ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ChangePasswordCycle> ec = null;
		try{
			ChangePasswordCycleMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			ec = ecMapper.deleteAllbyuser(userid);
			log.info("delChangePasswordCycle data");
		}catch(PersistenceException e){
			log.debug(e + "error delete ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public ChangePasswordCycle findLastpassworddata(Integer userid) {
		System.out.println("ChangePasswordCycle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		ChangePasswordCycle ec = null;
		try{
			ChangePasswordCycleMapper ecMapper = sqlSession.getMapper(ChangePasswordCycleMapper.class);
			ec = ecMapper.findLastpassworddata(userid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ChangePasswordCycle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (ChangePasswordCycle) ec;
	}

	

}
