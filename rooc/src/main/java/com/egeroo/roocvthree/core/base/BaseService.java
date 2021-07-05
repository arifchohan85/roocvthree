package com.egeroo.roocvthree.core.base;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.error.CoreException;



public class BaseService {
	private static final Logger LOG = Logger.getLogger(BaseService.class);
	
	public void closeMapper(BaseMapper mapper) {
		if (mapper != null) {
			mapper.closeConnection();
		}
	}
	
	public void throwException(Exception e) {
		LOG.error("ERROR");
		e.printStackTrace();
		throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
