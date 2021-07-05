package com.egeroo.roocvthree.trainlog;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;






public class TrainLogMapperImpl extends BaseDAO implements TrainLogMapper{
	
	private static final Logger log = Logger.getLogger(TrainLogMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public TrainLogMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<TrainLog> findAll() {
		System.out.println("TrainLog List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<TrainLog> ec = null;
		try{
			TrainLogMapper ecMapper = sqlSession.getMapper(TrainLogMapper.class);
			ec = ecMapper.findAll();
			log.info("get TrainLog data");
		}catch(PersistenceException e){
			log.debug(e + "error get TrainLog data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public TrainLog Save(TrainLog trainlog) {
		System.out.println("TrainLog save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		TrainLog ec = null;
		try{
			TrainLogMapper ecMapper = sqlSession.getMapper(TrainLogMapper.class);
			//userrole = 
			ec = ecMapper.Save(trainlog);
			log.info("insert TrainLog data");
		}catch(PersistenceException e){
			log.debug(e + "error insert TrainLog data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public TrainLogData Savedetaildata(TrainLogData trainlogdata) {
		System.out.println("TrainLogData save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		TrainLogData ec = null;
		try{
			TrainLogMapper ecMapper = sqlSession.getMapper(TrainLogMapper.class);
			//userrole = 
			ec = ecMapper.Savedetaildata(trainlogdata);
			log.info("insert TrainLogData data");
		}catch(PersistenceException e){
			log.debug(e + "error insert TrainLogData data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public TrainLogData findOne(Integer trainlogdataid) {
		System.out.println("TrainLogData List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		TrainLogData ec = null;
		try{
			TrainLogMapper ecMapper = sqlSession.getMapper(TrainLogMapper.class);
			ec = ecMapper.findOne(trainlogdataid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get TrainLogData data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (TrainLogData) ec;
	}

	@Override
	public TrainLogData Updateconfidenceafter(TrainLogData trainlogdata) {
		System.out.println("Updateconfidenceafter save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		TrainLogData ec = null;
		try{
			TrainLogMapper ecMapper = sqlSession.getMapper(TrainLogMapper.class);
			//userrole = 
			ec = ecMapper.Updateconfidenceafter(trainlogdata);
			log.info("insert Updateconfidenceafter data");
		}catch(PersistenceException e){
			log.debug(e + "error insert Updateconfidenceafter data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public TrainLogData findOnebyInteractionid(Integer interactionid) {
		System.out.println("findOnebyInteractionid List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		TrainLogData ec = null;
		try{
			TrainLogMapper ecMapper = sqlSession.getMapper(TrainLogMapper.class);
			ec = ecMapper.findOnebyInteractionid(interactionid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get findOnebyInteractionid data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (TrainLogData) ec;
	}

	

}
