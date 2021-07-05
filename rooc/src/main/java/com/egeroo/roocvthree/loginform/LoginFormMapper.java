package com.egeroo.roocvthree.loginform;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



public interface LoginFormMapper {
	
	@Select("SELECT userid,username,password FROM ms_app_user WHERE username = #{username} and password = #{password} AND isactive=1")
    public LoginForm findByUsernameandPassword(@Param("username") String username,@Param("password") String password);
	
	@Select("SELECT userid,username,password FROM ms_app_user WHERE username = #{username} and password = #{password} AND isactive=1")
    public LoginFormldap findByUsernameandPasswordldap(@Param("username") String username,@Param("password") String password);
	
	@Select("SELECT userid,username,password FROM ms_app_user WHERE userid = #{userid} and password = #{password} AND isactive=1")
    public LoginForm findByUseridandPassword(@Param("userid") Integer userid,@Param("password") String password);
	
	@Select("SELECT userid,username,password FROM ms_app_user WHERE username = #{username} AND isactive=1")
    public LoginFormldap findByUsernameldap(@Param("username") String username);
	
	@Select("SELECT userid,username,password FROM ms_app_user WHERE username = #{username} AND isactive=1 ")
    public LoginForm findByUsername(@Param("username") String username);

}
