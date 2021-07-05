package com.egeroo.roocvthree.schedulerload;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface SchedulerloadMapper {
	
	@Select("SELECT * FROM tr_app_scheduler ORDER BY schedulerid")
    public List<Schedulerload> findAll();
	
	@Select("SELECT * FROM tr_app_scheduler WHERE schedulerid = #{schedulerid}")
    public Schedulerload findOne(Integer schedulerid);
	
	@SelectKey(statement = "currval('schedulerid')", keyProperty = "schedulerid", before = true , resultType = int.class)
	@Select("Insert into tr_app_scheduler(name,code,description,type,url,method,datetime,enddatetime,timezone,dataparameter,dataheader"
			+ ",noenddate,isrecurring,occurance,occurancefrequency,status,createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{name},#{code},#{description},#{type},#{url},#{method},#{datetime},#{enddatetime},#{timezone},#{dataparameter},#{dataheader} " 
			+ " ,#{noenddate},#{isrecurring},#{occurance},#{occurancefrequency},#{status},#{createdby},#{updatedby},#{createdtime},#{updatedtime} )"
			+ " RETURNING * ")
    public Schedulerload Save(Schedulerload scheduler);
	
	@Select("Update tr_app_scheduler SET name=#{name}"
			+ " ,code=#{code}"
			+ " ,description=#{description}"
			+ " ,type=#{type}"
			+ " ,url=#{url}"
			+ " ,method=#{method}"
			+ " ,datetime=#{datetime}"
			+ " ,enddatetime=#{enddatetime}"
			+ " ,timezone=#{timezone}"
			+ " ,dataparameter=#{dataparameter}"
			+ " ,dataheader=#{dataheader}"
			+ " ,noenddate=#{noenddate}"
			+ " ,isrecurring=#{isrecurring}"
			+ " ,occurance=#{occurance}"
			+ " ,occurancefrequency=#{occurancefrequency}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE schedulerid=#{schedulerid} "
			+ " RETURNING * ")
    public Schedulerload Update(Schedulerload scheduler);
	
	@Select("Update tr_app_scheduler SET status=#{status}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE schedulerid=#{schedulerid} "
			+ " RETURNING * ")
    public Schedulerstatusload Updatestatus(Schedulerstatusload scheduler);

}
