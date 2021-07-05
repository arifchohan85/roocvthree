package com.egeroo.roocvthree.core.usersource;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;






public interface UserSourceMapper {
	
	@Select("SELECT a.*,u.username as username FROM ms_app_usersource a "
			+ " inner join ms_app_userprofile u\n"
			+ " on a.userid=u.userprofileid\n"  
			+ " ORDER BY usersourceid")
    public List<UserSource> findAll();
	
	@Select("SELECT * FROM ms_app_usersource WHERE authkey = #{authkey} Order by usersourceid desc limit 1")
    public UserSource findByAuthkey(String authkey);
	
	@Select("SELECT authkey FROM ms_app_usersource WHERE authkey = #{authkey} AND userid=#{userid} AND isactive=1  Order by usersourceid desc limit 1")
    public UserSource findByAuthkeyandUserid(@Param("authkey") String authkey,@Param("userid") int userid);
	
	@SelectKey(statement = "currval('usersourceid')", keyProperty = "usersourceid", before = true , resultType = int.class)
	@Select("Insert into ms_app_usersource(userid,logindate,expiredate,authkey,deviceid,isactive,localaddr,remoteaddr) " //,createdby,updatedby
			+ "VALUES (#{userid},#{logindate},#{expiredate},#{authkey},#{deviceid},#{isactive},#{localaddr},#{remoteaddr}) "
			+ " RETURNING usersourceid") //,#{createdBy},#{updateBy}
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
	public String Save(UserSource user);
	
	@Select("Update ms_app_usersource SET logouttime=#{logouttime}"
			+ " WHERE authkey=#{authkey} "
			+ " RETURNING *")
    public UserSource Updatelogout(UserSource usersource);

}
