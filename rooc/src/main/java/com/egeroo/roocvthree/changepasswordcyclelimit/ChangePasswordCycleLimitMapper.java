package com.egeroo.roocvthree.changepasswordcyclelimit;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;


public interface ChangePasswordCycleLimitMapper {
	
	@Select("SELECT * FROM ms_app_changepasswordcyclelimit "
			+ " ORDER BY changepasswordcyclelimitid asc")
    public List<ChangePasswordCycleLimit> findAll();
	
	@Select("SELECT * FROM ms_app_changepasswordcyclelimit"
			+ " WHERE changepasswordcyclelimitid = #{changepasswordcyclelimitid}")
    public ChangePasswordCycleLimit findOne(Integer changepasswordcyclelimitid);
	
	@Select("SELECT * FROM ms_app_changepasswordcyclelimit"
			+ " order by changepasswordcyclelimitid asc LIMIT 1")
    public ChangePasswordCycleLimit findOnelimit();
	
	@SelectKey(statement = "currval('changepasswordcyclelimitid')", keyProperty = "changepasswordcyclelimitid", before = true , resultType = int.class)
	@Select("Insert into ms_app_changepasswordcyclelimit(limitdata"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{limitdata}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public ChangePasswordCycleLimit Save(ChangePasswordCycleLimit ChangePasswordCycleLimit);
	
	@Select("Update ms_app_changepasswordcyclelimit SET limitdata=#{limitdata}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE changepasswordcyclelimitid=#{changepasswordcyclelimitid} "
			+ " RETURNING *")
    public ChangePasswordCycleLimit Update(ChangePasswordCycleLimit ChangePasswordCycleLimit);

}
