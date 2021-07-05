package com.egeroo.roocvthree.monitor;

import org.springframework.stereotype.Service;


@Service
public class MonitorService {
	
	public Monitor getQueue(String tenant) {
		MonitorMapper appMapper = new MonitorMapperImpl(tenant);
		return appMapper.findQueue();
	}
	
	public Monitor getHandle(String tenant) {
		MonitorMapper appMapper = new MonitorMapperImpl(tenant);
		return appMapper.findHandle();
	}

}
