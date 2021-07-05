package com.egeroo.roocvthree.upload;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.directory.DirectoryMapperImpl;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.IntentService;


public class UploadMapperImpl extends BaseDAO implements UploadMapper {
	
	private static final Logger log = Logger.getLogger(DirectoryMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    IntentService intservice = new IntentService();
    ValidationJson validatejson = new ValidationJson();
    
    
    public UploadMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Upload> findAll() {
		System.out.println("upload List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Upload> ec = null;
		try{
			UploadMapper ecMapper = sqlSession.getMapper(UploadMapper.class);
			ec = ecMapper.findAll();
			log.info("getupload data");
		}catch(PersistenceException e){
			log.debug(e + "error get upload data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Upload findOne(Integer uploadid) {
		System.out.println("upload List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Upload ec = null;
		try{
			UploadMapper ecMapper = sqlSession.getMapper(UploadMapper.class);
			ec = ecMapper.findOne(uploadid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get upload data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Upload) ec;
	}

	@Override
	public Upload Save(Upload upload) {
		System.out.println("upload save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Upload ec = null;
		try{
			UploadMapper uploadMapper = sqlSession.getMapper(UploadMapper.class);
			//userrole = 
			ec = uploadMapper.Save(upload);
			log.info("insertupload data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
		
	}

	@Override
	public Upload Update(Upload upload) {
		System.out.println("upload save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Upload ec = null;
		try{
			UploadMapper uploadMapper = sqlSession.getMapper(UploadMapper.class);
			//userrole = 
			ec = uploadMapper.Update(upload);
			log.info("insertupload data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
