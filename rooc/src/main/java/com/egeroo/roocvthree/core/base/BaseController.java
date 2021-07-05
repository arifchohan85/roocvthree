package com.egeroo.roocvthree.core.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.google.gson.Gson;

public class BaseController {	
	@Autowired
	Validator validator;
	
	public String buildResponse(Object o) {
		if (o instanceof String) {
			return (String) o;
		} else {
			Gson gson = new Gson();
			return gson.toJson(o);
		}
	}
	
	public String buildResponse(String key, Object value) {
		Gson gson = new Gson();
		
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return gson.toJson(map);
	}
	
	public <T> Errors validateList(List<T> list, Errors errors) {
		for (int i = 0; i < list.size(); i++) {
	        Object o = list.get(i);
	        BeanPropertyBindingResult beanBindingResult = new BeanPropertyBindingResult(o, errors.getObjectName());
	        validator.validate(o, beanBindingResult);
	        if (beanBindingResult.hasErrors()) {
	        	System.out.println(beanBindingResult.getObjectName());
	        	errors.addAllErrors(beanBindingResult);
	        }
	    }
		return errors;
	}
}
