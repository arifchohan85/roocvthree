package com.egeroo.roocvthree.generic;

import org.springframework.stereotype.Service;

@Service
public class GeneralApiChannelService {

	public GeneralApiChannel findOne(String tenant, String key) {
		GeneralApiChannelMapper apiMapper = new GeneralApiChannelMapperImpl(tenant);
		return apiMapper.findOne(key);
	}
}
