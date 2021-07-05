package com.egeroo.roocvthree.trailingrecord;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;



public class TrailingRecordMapperImpl extends BaseDAO implements TrailingRecordMapper{
	
	private static final Logger log = Logger.getLogger(TrailingRecordMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public TrailingRecordMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<TrailingRecord> findAll() {
		System.out.println("trMapper List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<TrailingRecord> tr = null;
		try{
			TrailingRecordMapper trMapper = sqlSession.getMapper(TrailingRecordMapper.class);
			tr = trMapper.findAll();
			log.info("trMapper data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return tr;
	}

	@Override
	public String Save(TrailingRecord trailingrecord) {
		System.out.println("user save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			TrailingRecordMapper trMapper = sqlSession.getMapper(TrailingRecordMapper.class);
			//userrole = 
			lastinsertuserid =trMapper.Save(trailingrecord);
			log.debug("insert id is : " + trailingrecord.getTrailingrecordid());
			log.info("insert trMapper data");
		}catch(PersistenceException e){
			log.debug(e + "error get trMapper data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

}
