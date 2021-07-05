package com.egeroo.roocvthree.roocconfig;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface RoocConfigMapper {
	
	@Select("SELECT * FROM ms_cfg_roocconfig ORDER BY roocconfigid")
    public List<RoocConfig> findAll();
	
	@Select("SELECT * FROM ms_cfg_roocconfig WHERE roocconfigid = #{roocconfigid}")
    public RoocConfig findOne(Integer roocconfigid);
	
	@SelectKey(statement = "currval('roocconfigid')", keyProperty = "roocconfigid", before = true , resultType = int.class)
	@Select("Insert into ms_cfg_roocconfig(configkey,configvalue"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{configkey},#{configvalue}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public RoocConfig Save(RoocConfig roocconfig);
	
	@Select("Update ms_cfg_roocconfig SET roleid=#{roleid}"
			+ " ,configkey=#{configkey}"
			+ " ,configvalue=#{configvalue}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE roocconfigid=#{roocconfigid} "
			+ " RETURNING *")
    public RoocConfig Update(RoocConfig roocconfig);
	
	@Select("delete from ms_cfg_roocconfig WHERE roocconfigid = #{roocconfigid} RETURNING * ")
    public RoocConfig deleteOne(Integer roocconfigid);
	
	@Select("SELECT * FROM ms_cfg_roocconfig WHERE configkey = #{configkey} LIMIT 1")
    public RoocConfig findByconfigkey(@Param("configkey") String configkey);


}
