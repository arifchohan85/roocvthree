package com.egeroo.roocvthree.sentiment;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class SentimentService {
	
	public List<Sentiment> getIndex(String tenant) {
		SentimentMapper appMapper = new SentimentMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public Sentiment getView(String tenant,int sentimentid) {
		SentimentMapper appMapper = new SentimentMapperImpl(tenant);
		return appMapper.findOne(sentimentid);
	}
	
	public void getCreate(String tenant,Sentiment sentiment) {
		SentimentMapper appMapper = new SentimentMapperImpl(tenant);
		//return 
		appMapper.Save(sentiment);
	}
	
	public void getUpdate(String tenant,Sentiment sentiment) {
		SentimentMapper appMapper = new SentimentMapperImpl(tenant);
		//return 
		appMapper.Update(sentiment);
	}

}
