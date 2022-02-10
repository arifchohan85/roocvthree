package com.egeroo.roocvthree.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;






public interface UserMapper {
	
	@Select("SELECT * FROM ms_app_user ORDER BY userid")
    public List<User> findAll();
		
	@Select("SELECT * FROM ms_app_user WHERE userid = #{userid}")
    public User findOne(Integer userid);
	
	@Select("SELECT username FROM ms_app_user WHERE username = #{username}")
    public User findByUsername(String username);
	
	@Select("SELECT username FROM ms_app_user WHERE username = #{username} AND password = #{password}")
    public User findByUsernameandPassword(@Param("username") String username, @Param("password") String password);
	
	@SelectKey(statement = "currval('userid')", keyProperty = "userid", before = true , resultType = int.class)
	@Select("Insert into ms_app_user(roleid,username,password,isactive,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ "VALUES (#{roleid},#{username},#{password},#{isactive},#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING userid") //,#{createdBy},#{updateBy}
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
	public String Save(User user);
	//@Options(useGeneratedKeys = true, keyProperty = "userid")
	//@Select("SELECT LAST_INSERT_ID()")
	//@SelectKey(statement = "currval('userid')", keyProperty = "userid", before = false , resultType = int.class)
	
	@Select("Update ms_app_user SET roleid=#{roleid}"
			+ " ,isactive=#{isactive}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userid=#{userid} "
			+ " RETURNING userid")
    public String Update(User user);
	
	@Select("Update ms_app_user SET roleid=#{roleid}"
			+ " ,isactive=#{isactive}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userid=#{userid} "
			+ " RETURNING userid")
    public String Updatefromprofile(User user);
	
	@Select("Update ms_app_user SET "
			+ " password=#{password}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userid=#{userid} "
			+ " RETURNING userid")
    public String Updatepassword(User user);
	
	@Select("Update ms_app_user SET failedloginattempt=#{failedloginattempt}"
			+ " WHERE userid=#{userid} "
			+ " RETURNING userid")
    public Integer Updatefailedattempt(User user);
	
	@Select("Update ms_app_user SET "
			+ " isactive=#{isactive}"
			+ " WHERE userid=#{userid} "
			+ " RETURNING userid")
    public Integer Updateinactive(User user);

}
