package com.egeroo.roocvthree.menulistrole;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface MenuListRoleMapper {
	@Select("SELECT * FROM ms_app_menulistrole ORDER BY MenuListRoleID")
    public List<MenuListRole> findAll();
	
	@Select("SELECT a.*"
			+ " ,b.title"
			+ " ,r.rolename"
			+ "FROM ms_app_menulistrole a "
			+ " inner join ms_app_menulist b "
			+ " on a.MenuListID=b.MenuListID "
			+ " inner join ms_app_userrole r "
			+ " on a.RoleID=r.RoleID "
			+ "ORDER BY a.MenuListRoleID")
    public List<MenuListRole> findJoinall();
	
	@Select("SELECT * FROM ms_app_menulistrole WHERE MenuListRoleID = #{menulistroleid}")
    public MenuListRole findOne(Integer menulistroleid);
	
	@SelectKey(statement = "currval('menulistroleid')", keyProperty = "menulistroleid", before = true , resultType = int.class)
	@Select("Insert into ms_app_menulistrole(roleid,menulistid"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ "VALUES (#{roleid},#{menulistid}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING menulistroleid") //,#{createdBy},#{updateBy}
	public String Save(MenuListRole mlr);
	
	@Select("Update ms_app_menulistrole SET roleid=#{roleid}"
			+ " ,menulistid=#{menulistid}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE menulistroleid=#{menulistroleid} "
			+ " RETURNING menulistroleid")
    public String Update(MenuListRole mlr);
	
	@Select("delete from ms_app_menulistrole WHERE menulistroleid = #{menulistroleid} RETURNING * ")
    public MenuListRole deleteOne(Integer menulistroleid);
	
	@Select("delete from ms_app_menulistrole WHERE menulistid = #{menulistid} RETURNING * ")
    public List<MenuListRole> deleteBymenulistid(Integer menulistid);
	
	@Select("SELECT * FROM ms_app_menulistrole WHERE menulistid = #{menulistid} and roleid = #{roleid}")
    public MenuListRole findByroleandmenu(@Param("menulistid") Integer menulistid,@Param("roleid") Integer roleid);

}
