package com.egeroo.roocvthree.roocconfig;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;



public class RoocConfigMapperImpl   extends BaseDAO implements RoocConfigMapper{
	
	private static final Logger log = Logger.getLogger(RoocConfigMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();

    public RoocConfigMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<RoocConfig> findAll() {
		System.out.println("RoocConfig List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<RoocConfig> ec = null;
		try{
			RoocConfigMapper ecMapper = sqlSession.getMapper(RoocConfigMapper.class);
			ec = ecMapper.findAll();
			log.info("getRoocConfigdata");
		}catch(PersistenceException e){
			log.debug(e + "error get RoocConfig data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoocConfig findOne(Integer roocconfigid) {
		System.out.println("RoocConfig List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoocConfig ec = null;
		try{
			RoocConfigMapper ecMapper = sqlSession.getMapper(RoocConfigMapper.class);
			ec = ecMapper.findOne(roocconfigid);
			log.info("getRoocConfig data");
		}catch(PersistenceException e){
			log.debug(e + "error get RoocConfig data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (RoocConfig) ec;
	}

	@Override
	public RoocConfig Save(RoocConfig roocconfig) {
		System.out.println("RoocConfig save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoocConfig ec = null;
		try{
			RoocConfigMapper RoocConfig = sqlSession.getMapper(RoocConfigMapper.class);
			//userrole = 
			ec = RoocConfig.Save(roocconfig);
			log.info("insertRoocConfig data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoocConfig Update(RoocConfig roocconfig) {
		System.out.println("RoocConfig save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoocConfig ec = null;
		try{
			RoocConfigMapper RoocConfigMapper = sqlSession.getMapper(RoocConfigMapper.class);
			//userrole = 
			ec = RoocConfigMapper.Update(roocconfig);
			log.info("updateRoocConfig data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoocConfig deleteOne(Integer roocconfigid) {
		System.out.println("RoocConfig delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoocConfig ec = null;
		try{
			RoocConfigMapper ecMapper = sqlSession.getMapper(RoocConfigMapper.class);
			ec = ecMapper.deleteOne(roocconfigid);
			log.info("delete RoocConfig data");
		}catch(PersistenceException e){
			log.debug(e + "error delete RoocConfig data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoocConfig findByconfigkey(String configkey) {
		System.out.println("RoocConfig List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoocConfig mr = null;
		try{
			RoocConfigMapper userMapper = sqlSession.getMapper(RoocConfigMapper.class);
			mr = userMapper.findByconfigkey(configkey);
			log.info("getRoocConfig data");
		}catch(PersistenceException e){
			log.debug(e + "error get RoocConfig data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (RoocConfig) mr;
	}

}
