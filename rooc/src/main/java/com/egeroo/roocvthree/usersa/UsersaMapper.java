package com.egeroo.roocvthree.usersa;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface UsersaMapper {
	
	@Select("SELECT * FROM ms_app_usersa ORDER BY usersaid")
    public List<Usersa> findAll();
	
	@Select("SELECT * FROM ms_app_usersa WHERE usersaid = #{usersaid}")
    public Usersa findOne(Integer usersaid);
	
	@SelectKey(statement = "currval('usersaid')", keyProperty = "usersaid", before = true , resultType = int.class)
	@Select("Insert into ms_app_usersa(username,name,action,role,oldvalue,newvalue,createdby,updatedby,createdtime,updatedtime) " 
			+ " VALUES (#{username},#{name},#{action},#{role},#{oldvalue},#{newvalue},#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING usersaid") 
    public String Save(Usersa usersa);

}
