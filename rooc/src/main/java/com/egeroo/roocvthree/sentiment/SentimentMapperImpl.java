package com.egeroo.roocvthree.sentiment;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;




public class SentimentMapperImpl extends BaseDAO implements SentimentMapper {
	
	private static final Logger log = Logger.getLogger(SentimentMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public SentimentMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Sentiment> findAll() {
		System.out.println("Sentiment List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Sentiment> sentiment = null;
		try{
			SentimentMapper sentimentMapper = sqlSession.getMapper(SentimentMapper.class);
			sentiment = sentimentMapper.findAll();
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return sentiment;
	}

	@Override
	public Sentiment findOne(Integer sentimentid) {
		System.out.println("Sentiment List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Sentiment sentiment = null;
		try{
			SentimentMapper sentimentMapper = sqlSession.getMapper(SentimentMapper.class);
			sentiment = sentimentMapper.findOne(sentimentid);
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Sentiment) sentiment;
	}

	@Override
	public void Save(Sentiment sentiment) {
		System.out.println("sentiment save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			SentimentMapper sentimentMapper = sqlSession.getMapper(SentimentMapper.class);
			//userrole = 
			sentimentMapper.Save(sentiment);
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
	public void Update(Sentiment sentiment) {
		System.out.println("sentiment update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			SentimentMapper sentimentMapper = sqlSession.getMapper(SentimentMapper.class);
			//userrole = 
			sentimentMapper.Update(sentiment);
			log.info("insertUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
	}

}
