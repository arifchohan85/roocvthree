package com.egeroo.roocvthree.intent;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;




@Service
public class IntentService {
	
	public List<Intent> getIndex(String tenant) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getIndexV3(String tenant, boolean lite) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		int active = 0;
		if(lite) {active=1;} 
		List<Intent> intentList = appMapper.findAllV3();
		List<Map> result = new ArrayList<Map>();
		for (Intent intent : intentList) {
			Map inputMap = new LinkedHashMap();
			int intentId = intent.getIntentid();
			String intentName = intent.getQuestion();
			inputMap.put("intentId", intentId);
			inputMap.put("intentName", intentName);
			result.add(inputMap);
		}
		return result;	 
	}
	
	public List<Intent> getExtractintent(String tenant) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.extractIntent();	 
	}
	
	public List<Intent> getIntent(String tenant) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findIntent();	 
	}
	
	public List<Intent> getIntentict(String tenant) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findIntentict();	 
	}
	
	public List<Intent> getIntentdir(String tenant,int directoryid) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findIntentdir(directoryid);	 
	}
	
	public List<Intent> getIntentdirrecursive(String tenant,int directoryid) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findIntentdirrecursive(directoryid);	 
	}
	
	public Intent getView(String tenant,int ecid) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findOne(ecid);
	}
	
	public Intent getDelete(String tenant,int intentid) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.deleteOne(intentid);
	}
	
	public List<Intent> getDeleteintentbydirectory(String tenant,int directoryid) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.deletebyDirectory(directoryid);
	}
	
	public Intent getViewquestion(String tenant,String question) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findIntentquestion(question);
	}
	
	public Intent getMaxintent(String tenant) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.findMaxintentid();
	}
	
	public String getCreate(String tenant,Intent Intent) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.Save(Intent); 
	}
	
	public String getCreateengine(String tenant,Intent Intent) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.Saveengine(Intent); 
	}
	
	public String getUpdate(String tenant,Intent Intent) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.Update(Intent);
	}
	
	public String getUpdateinternal(String tenant,Intent Intent) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.Updateinternal(Intent);
	}
	
	
	
	public String getCreatemax(String tenant,MaxIntent Intent) {
		IntentMapper appMapper = new IntentMapperImpl(tenant);
		return appMapper.Savemaxintent(Intent); 
	}
	
	
}
