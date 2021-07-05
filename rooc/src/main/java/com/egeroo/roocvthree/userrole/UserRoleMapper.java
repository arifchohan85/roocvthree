package com.egeroo.roocvthree.userrole;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRoleMapper{// extends JpaRepository < UserRole, Long >{
	
	

	//public List<UserRole> getUserrolelist();
	//public List<UserRole> getUserrolelist();
	@Select("SELECT * FROM ms_app_userrole ORDER BY roleid")
    public List<UserRole> findAll();
	
	@Select("SELECT * FROM ms_app_userrole WHERE roleid = #{roleid}")
    public UserRole findOne(Integer roleid);
	
	@Select("SELECT * FROM ms_app_userrole WHERE rolename = #{rolename} LIMIT 1")
    public UserRole findOnebyname(String rolename);
	
	@SelectKey(statement = "currval('roleid')", keyProperty = "roleid", before = true , resultType = int.class)
	@Select("Insert into ms_app_userrole(rolename) VALUES (#{rolename}) ")
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
    public void Save(UserRole userrole);
	
	@Select("Update ms_app_userrole SET rolename=#{rolename} WHERE roleid=#{roleid} ")
    public void Update(UserRole userrole);
}
