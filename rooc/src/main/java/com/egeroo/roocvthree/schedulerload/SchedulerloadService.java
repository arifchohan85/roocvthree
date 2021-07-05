package com.egeroo.roocvthree.schedulerload;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class SchedulerloadService {
	
	public List<Schedulerload> getIndex(String tenant) {
		SchedulerloadMapper appMapper = new SchedulerloadMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public Schedulerload getView(String tenant,int schedulerid) {
		SchedulerloadMapper appMapper = new SchedulerloadMapperImpl(tenant);
		return appMapper.findOne(schedulerid);
	}
	
	public Schedulerload getCreate(String tenant,Schedulerload scheduler) {
		SchedulerloadMapper appMapper = new SchedulerloadMapperImpl(tenant);
		return appMapper.Save(scheduler);
	}
	
	public Schedulerload getUpdate(String tenant,Schedulerload scheduler) {
		SchedulerloadMapper appMapper = new SchedulerloadMapperImpl(tenant);
		return appMapper.Update(scheduler);
	}
	
	public Schedulerstatusload getUpdatestatus(String tenant,Schedulerstatusload scheduler) {
		SchedulerloadMapper appMapper = new SchedulerloadMapperImpl(tenant);
		return appMapper.Updatestatus(scheduler);
	}

}
