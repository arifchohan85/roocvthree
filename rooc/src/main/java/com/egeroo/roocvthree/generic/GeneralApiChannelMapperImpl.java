package com.egeroo.roocvthree.generic;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;

public class GeneralApiChannelMapperImpl extends BaseDAO implements GeneralApiChannelMapper{
	private static final Logger log = Logger.getLogger(GeneralApiChannelMapperImpl.class);
	private SqlSession sqlSession = null;
	private String tenantIdentifier = "";

	public GeneralApiChannelMapperImpl(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}

	@Override
	public GeneralApiChannel findOne(String key) {
		sqlSession = BaseDAO.getInstance(this.tenantIdentifier).openSession();
		GeneralApiChannel ec = null;
		try {
			GeneralApiChannelMapper ecMapper = sqlSession.getMapper(GeneralApiChannelMapper.class);
			ec = ecMapper.findOne(key);
			log.info("getUser data");
		} catch (PersistenceException e) {
			log.debug(e + "error get user data");
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return ec;	
		}

}
