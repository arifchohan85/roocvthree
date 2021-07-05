package com.egeroo.roocvthree.botrate;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;




public class BotrateMapperImpl   extends BaseDAO implements BotrateMapper {
	
	private static final Logger log = Logger.getLogger(BotrateMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public BotrateMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Botrate> findAll() {
		System.out.println("Botrate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Botrate> ec = null;
		try{
			BotrateMapper ecMapper = sqlSession.getMapper(BotrateMapper.class);
			ec = ecMapper.findAll();
			log.info("get Botrate data");
		}catch(PersistenceException e){
			log.debug(e + "error get Botrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Botrate findOne(Integer botrateid) {
		System.out.println("Botrate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Botrate ec = null;
		try{
			BotrateMapper ecMapper = sqlSession.getMapper(BotrateMapper.class);
			ec = ecMapper.findOne(botrateid);
			log.info("get Botrate data");
		}catch(PersistenceException e){
			log.debug(e + "error get Botrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Botrate) ec;
	}

	@Override
	public Botrate Save(Botrate botrate) {
		System.out.println("Botrate save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Botrate ec = null;
		try{
			BotrateMapper ecMapper = sqlSession.getMapper(BotrateMapper.class);
			//userrole = 
			ec = ecMapper.Save(botrate);
			log.info("insert Botrate data");
		}catch(PersistenceException e){
			log.debug(e + "error insert Botrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Botrate Update(Botrate botrate) {
		System.out.println("Botrate save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Botrate ec = null;
		try{
			BotrateMapper ecMapper = sqlSession.getMapper(BotrateMapper.class);
			//userrole = 
			ec = ecMapper.Update(botrate);
			log.info("updateBotrate data");
		}catch(PersistenceException e){
			log.debug(e + "error update Botrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
