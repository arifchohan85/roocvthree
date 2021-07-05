package com.egeroo.roocvthree.roleapiroute;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface RoleApiRouteMapper {
	
	@Select("SELECT * FROM ms_app_roleapiroute ORDER BY roleapirouteid")
    public List<RoleApiRoute> findAll();
	
	@Select("SELECT * FROM ms_app_roleapiroute WHERE roleapirouteid = #{roleapirouteid}")
    public RoleApiRoute findOne(Integer roleapirouteid);
	
	@SelectKey(statement = "currval('roleapirouteid')", keyProperty = "roleapirouteid", before = true , resultType = int.class)
	@Select("Insert into ms_app_roleapiroute(roleid,route"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{roleid},#{route}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public RoleApiRoute Save(RoleApiRoute roleapiroute);
	
	@Select("Update ms_app_roleapiroute SET roleid=#{roleid}"
			+ " ,route=#{route}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE roleapirouteid=#{roleapirouteid} "
			+ " RETURNING *")
    public RoleApiRoute Update(RoleApiRoute roleapiroute);
	
	@Select("delete from ms_app_roleapiroute WHERE roleid = #{roleid} RETURNING * ")
    public List<RoleApiRoute> deleteByroleid(Integer roleid);
	
	@Select("delete from ms_app_roleapiroute WHERE roleapirouteid = #{roleapirouteid} RETURNING * ")
    public RoleApiRoute deleteOne(Integer roleapirouteid);
	
	@Select("SELECT * FROM ms_app_roleapiroute WHERE route = #{route} and roleid = #{roleid} LIMIT 1")
    public RoleApiRoute findByroleandroute(@Param("route") String route,@Param("roleid") Integer roleid);


}
