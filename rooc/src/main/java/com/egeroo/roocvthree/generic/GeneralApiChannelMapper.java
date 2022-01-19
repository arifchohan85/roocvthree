package com.egeroo.roocvthree.generic;

import org.apache.ibatis.annotations.Select;

public interface GeneralApiChannelMapper {

	@Select("select * from ms_api_list where keyvalue=#{key}")
	public GeneralApiChannel findOne(String key);
	
}
