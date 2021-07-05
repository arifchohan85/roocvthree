package com.egeroo.roocvthree.trainlog;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class TrainLogService {
	
	public List<TrainLog> getIndex(String tenant) {
		TrainLogMapper appMapper = new TrainLogMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public TrainLog getCreate(String tenant,TrainLog trainlog) {
		TrainLogMapper appMapper = new TrainLogMapperImpl(tenant);
		return appMapper.Save(trainlog);
	}
	
	public TrainLogData getCreatedetail(String tenant,TrainLogData trainlogdata) {
		TrainLogMapper appMapper = new TrainLogMapperImpl(tenant);
		return appMapper.Savedetaildata(trainlogdata);
	}
	
	public TrainLogData getViewlogdata(String tenant,int trainlogdataid) {
		TrainLogMapper appMapper = new TrainLogMapperImpl(tenant);
		return appMapper.findOne(trainlogdataid);
	}
	
	public TrainLogData getUpdateconfidenceafter(String tenant,TrainLogData trainlogdata) {
		TrainLogMapper appMapper = new TrainLogMapperImpl(tenant);
		return appMapper.Updateconfidenceafter(trainlogdata);
	}
	
	public TrainLogData getViewlogdatabyinteractionid(String tenant,int interactionid) {
		TrainLogMapper appMapper = new TrainLogMapperImpl(tenant);
		return appMapper.findOnebyInteractionid(interactionid);
	}

}
