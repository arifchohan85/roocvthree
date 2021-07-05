package com.egeroo.roocvthree.core.base;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class BaseMapperImpl extends BaseDAO implements BaseMapper {
	protected SqlSession sqlSession = null;
	protected String tenantIdentifier = "";
	protected static final Logger LOG = Logger.getLogger(BaseMapperImpl.class);
	
	public SqlSession getSqlSession() {
		return this.sqlSession;
	}
	
	public void setSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void commit() {
		this.sqlSession.commit();
	}
	
	public void closeConnection() {
		this.sqlSession.close();
	}
}
