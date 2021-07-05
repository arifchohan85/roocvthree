package com.egeroo.roocvthree.rolewebroute;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;


public interface RoleWebRouteMapper {
	
	@Select("SELECT * FROM ms_app_rolewebroute ORDER BY rolewebrouteid")
    public List<RoleWebRoute> findAll();
	
	@Select("SELECT * FROM ms_app_rolewebroute WHERE rolewebrouteid = #{rolewebrouteid}")
    public RoleWebRoute findOne(Integer rolewebrouteid);
	
	@SelectKey(statement = "currval('rolewebrouteid')", keyProperty = "rolewebrouteid", before = true , resultType = int.class)
	@Select("Insert into ms_app_rolewebroute(roleid,route,menulistid"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{roleid},#{route},#{menulistid}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public RoleWebRoute Save(RoleWebRoute rolewebroute);
	
	@Select("Update ms_app_rolewebroute SET roleid=#{roleid}"
			+ " ,route=#{route}"
			+ " ,menulistid=#{menulistid}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE rolewebrouteid=#{rolewebrouteid} "
			+ " RETURNING *")
    public RoleWebRoute Update(RoleWebRoute rolewebroute);
	
	@Select("delete from ms_app_rolewebroute WHERE roleid = #{roleid} RETURNING * ")
    public List<RoleWebRoute> deleteByroleid(Integer roleid);
	
	@Select("delete from ms_app_rolewebroute WHERE rolewebrouteid = #{rolewebrouteid} RETURNING * ")
    public RoleWebRoute deleteOne(Integer rolewebrouteid);
	
	/*@Select("SELECT * FROM ms_app_rolewebroute WHERE route = #{route} and roleid = #{roleid} LIMIT 1")
    public RoleWebRoute findByroleandroute(@Param("route") String route,@Param("roleid") Integer roleid);*/
	
	/*@Select("select route,roleid "
			+ " from ms_app_menulist ml"
			+ " inner join ms_app_menulistrole mlr \n"
			+ " on ml.menulistid=mlr.menulistid "
			+ " WHERE route = #{route} "
			+ " and roleid = #{roleid} LIMIT 1")
    public RoleWebRoute findByroleandroute(@Param("route") String route,@Param("roleid") Integer roleid);
	*/
	
	@Select("select route,roleid,menulistid "
			+ " from ms_app_rolewebroute ml"
			+ " WHERE route = #{route} "
			+ " and roleid = #{roleid} LIMIT 1")
    public RoleWebRoute findByroleandroute(@Param("route") String route,@Param("roleid") Integer roleid);

}
