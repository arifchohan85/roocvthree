package com.egeroo.roocvthree.core.base;

import org.apache.ibatis.session.SqlSession;

public interface BaseMapper {
	public SqlSession getSqlSession();
	public void setSession(SqlSession sqlSession);
	public void commit();
	public void closeConnection();
}
