package com.egeroo.roocvthree.intentgroup;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;




public class IntentGroupMapperImpl extends BaseDAO implements IntentGroupMapper {
	
	private static final Logger log = Logger.getLogger(IntentGroupMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public IntentGroupMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<IntentGroup> findAll() {
		System.out.println("IntentGroup List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<IntentGroup> Intentgroup = null;
		try{
			IntentGroupMapper IntentgroupMapper = sqlSession.getMapper(IntentGroupMapper.class);
			Intentgroup = IntentgroupMapper.findAll();
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return Intentgroup;
	}

	@Override
	public IntentGroup findOne(Integer intentgroupid) {
		System.out.println("IntentGroup List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		IntentGroup Intentgroup = null;
		try{
			IntentGroupMapper IntentgroupMapper = sqlSession.getMapper(IntentGroupMapper.class);
			Intentgroup = IntentgroupMapper.findOne(intentgroupid);
			log.info("getUserrole data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (IntentGroup) Intentgroup;
	}

	@Override
	public void Save(IntentGroup intentgroup) {
		System.out.println("IntentGroup save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			IntentGroupMapper intentgroupMapper = sqlSession.getMapper(IntentGroupMapper.class);
			//userrole = 
			intentgroupMapper.Save(intentgroup);
			log.info("insertintentgroup data");
		}catch(PersistenceException e){
			log.debug(e + "error insert intentgroup");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
	}

	@Override
	public void Update(IntentGroup intentgroup) {
		System.out.println("IntentGroup update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			IntentGroupMapper intentgroupMapper = sqlSession.getMapper(IntentGroupMapper.class);
			//userrole = 
			intentgroupMapper.Update(intentgroup);
			log.info("updateintentgroup data");
		}catch(PersistenceException e){
			log.debug(e + "error update intentgroup");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
	}

}
