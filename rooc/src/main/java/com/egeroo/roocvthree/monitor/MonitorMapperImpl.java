package com.egeroo.roocvthree.monitor;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;



public class MonitorMapperImpl  extends BaseDAO implements MonitorMapper{
	
	private static final Logger log = Logger.getLogger(MonitorMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public MonitorMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public Monitor findQueue() {
		System.out.println("Monitor Queue List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Monitor mm = null;
		try{
			MonitorMapper mmMapper = sqlSession.getMapper(MonitorMapper.class);
			mm = mmMapper.findQueue();
			log.info("getQueue data");
		}catch(PersistenceException e){
			log.debug(e + "error get Queue data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Monitor) mm;
	}

	@Override
	public Monitor findHandle() {
		System.out.println("Monitor Handle List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Monitor mm = null;
		try{
			MonitorMapper mmMapper = sqlSession.getMapper(MonitorMapper.class);
			mm = mmMapper.findHandle();
			log.info("getfindHandle data");
		}catch(PersistenceException e){
			log.debug(e + "error get findHandle data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Monitor) mm;
	}

}
