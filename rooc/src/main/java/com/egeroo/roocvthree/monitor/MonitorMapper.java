package com.egeroo.roocvthree.monitor;

import org.apache.ibatis.annotations.Select;



public interface MonitorMapper {
	
	@Select(" select count(*) from channel.room\n" + 
			" where (handledby_id is null or handledby_id<=0)\n" + 
			" and (confidence < minimumconfidence); ")
    public Monitor findQueue();
	
	@Select("select count(*) from channel.room\n" + 
			" where (handledby_id>=0);")
    public Monitor findHandle();

}
