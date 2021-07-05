package com.egeroo.roocvthree.answer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
	private static final Logger LOG = Logger.getLogger(AnswerService.class);
	private AnswerMapper answerMapper;
	
	public List<Answer> getAllAnswers(String tenantIdentifier) {
		List<Answer> result = new ArrayList<>();
		try {
			answerMapper = new AnswerMapperImpl(tenantIdentifier);
			result = answerMapper.getAnswers();
		} catch (Exception e) {
			LOG.error("ERROR");
			e.printStackTrace();
		}
		return result;
	}
}
