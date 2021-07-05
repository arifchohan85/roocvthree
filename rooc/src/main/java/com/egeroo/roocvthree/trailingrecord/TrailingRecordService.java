package com.egeroo.roocvthree.trailingrecord;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class TrailingRecordService {
	
	public List<TrailingRecord> getIndex(String tenant) {
		TrailingRecordMapper appMapper = new TrailingRecordMapperImpl(tenant);
		return appMapper.findAll();	 
	}

}
