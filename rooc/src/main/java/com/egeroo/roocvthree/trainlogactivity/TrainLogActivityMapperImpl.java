package com.egeroo.roocvthree.trainlogactivity;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;




public class TrainLogActivityMapperImpl extends BaseDAO implements TrainLogActivityMapper{
	
	private static final Logger log = Logger.getLogger(TrainLogActivityMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public TrainLogActivityMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public TrainLogActivity Save(TrainLogActivity trainlogactivity) {
		System.out.println("TrainLogActivity save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		TrainLogActivity ec = null;
		try{
			TrainLogActivityMapper ecMapper = sqlSession.getMapper(TrainLogActivityMapper.class);
			//userrole = 
			ec = ecMapper.Save(trainlogactivity);
			log.info("insert TrainLogActivity data");
		}catch(PersistenceException e){
			log.debug(e + "error insert TrainLogActivity data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
