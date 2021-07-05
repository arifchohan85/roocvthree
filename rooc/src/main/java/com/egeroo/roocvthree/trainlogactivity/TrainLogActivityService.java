package com.egeroo.roocvthree.trainlogactivity;

import org.springframework.stereotype.Service;



@Service
public class TrainLogActivityService {
	
	public TrainLogActivity getCreate(String tenant,TrainLogActivity trainlogactivity) {
		TrainLogActivityMapper appMapper = new TrainLogActivityMapperImpl(tenant);
		return appMapper.Save(trainlogactivity);
	}

}
