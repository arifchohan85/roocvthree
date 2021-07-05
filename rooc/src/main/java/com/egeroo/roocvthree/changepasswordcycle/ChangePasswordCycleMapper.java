package com.egeroo.roocvthree.changepasswordcycle;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;


public interface ChangePasswordCycleMapper {
	
	@Select("SELECT * FROM ms_app_changepasswordcycle "
			+ " ORDER BY changepasswordcycleid")
    public List<ChangePasswordCycle> findAll();
	
	@Select("SELECT * FROM ms_app_changepasswordcycle"
			+ " WHERE changepasswordcycleid = #{changepasswordcycleid}")
    public ChangePasswordCycle findOne(Integer changepasswordcycleid);
	
	@Select("SELECT COUNT(*) as countdata FROM ms_app_changepasswordcycle"
			+ " WHERE userid = #{userid}")
    public ChangePasswordCycle findCountdata(Integer userid);
	
	@Select("SELECT * FROM ms_app_changepasswordcycle"
			+ " WHERE userid = #{userid} AND password=#{password} LIMIT 1")
    public ChangePasswordCycle findCheckdata(ChangePasswordCycle ChangePasswordCycle);
	
	@Select("DELETE FROM ms_app_changepasswordcycle "
			+ " WHERE userid = #{userid}"
			+ " ;")
    public List<ChangePasswordCycle> deleteAllbyuser(Integer userid);
	
	@Select("SELECT * FROM ms_app_changepasswordcycle"
			+ " WHERE userid = #{userid} ORDER BY changepasswordcycleid desc limit 1")
    public ChangePasswordCycle findLastpassworddata(Integer userid);
	
	@SelectKey(statement = "currval('changepasswordcycleid')", keyProperty = "changepasswordcycleid", before = true , resultType = int.class)
	@Select("Insert into ms_app_ChangePasswordCycle(userid,password"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{userid},#{password}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public ChangePasswordCycle Save(ChangePasswordCycle ChangePasswordCycle);
	
	@Select("Update ms_app_ChangePasswordCycle SET userid=#{userid}"
			+ " ,password=#{password}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE changepasswordcycleid=#{changepasswordcycleid} "
			+ " RETURNING *")
    public ChangePasswordCycle Update(ChangePasswordCycle ChangePasswordCycle);

}
