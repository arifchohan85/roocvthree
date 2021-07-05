package com.egeroo.roocvthree.answer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;

public class AnswerMapperImpl extends BaseDAO implements AnswerMapper{
	private static final Logger LOG = Logger.getLogger(AnswerMapperImpl.class);
	private SqlSession sqlSession = null;
	String tenantIdentifier = "";

	public AnswerMapperImpl(String tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}
	
	public List<Answer> getAnswers() {
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Answer> answers = null;
		try{
			AnswerMapper answerMapper = sqlSession.getMapper(AnswerMapper.class);
			answers = answerMapper.getAnswers();
		}catch(Exception e){	
			System.out.println(e.getMessage());
			LOG.debug(e);
		}
		return answers;			
	}
}
