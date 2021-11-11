package com.egeroo.roocvthree.userprofile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface UserProfileMapper {
	
	/*@Select("SELECT * FROM ms_app_userprofile ORDER BY userprofileid asc")
    public List<UserProfile> findAll();*/
	@Select("select "
			+ " userprofileid,p.roleid,username,name,emailaddress,r.rolename\n" +
			" ,p.createdby,p.updatedby,p.createdtime,p.updatedtime,isactive"
			+ " from ms_app_userprofile p " +
			" left join ms_app_userrole  r " +
			" on p.roleid=r.roleid " +
			" where source='rooc' "
			+ " ORDER BY userprofileid asc")
    public List<UserProfileIndex> findAll();
	
	@Select("select p2.\"name\" \"createdBy\",p.createdtime \"createdTime\",p.isactive \"isActive\",\r\n" + 
			"p.userid \"userId\",p.\"name\",p.roleid \"roleId\",r.rolename \"roleName\",p.username \"userName\"\r\n" + 
			"from ms_app_userprofile p \r\n" + 
			"left join ms_app_userrole r\r\n" + 
			"on p.roleid=r.roleid\r\n" + 
			"left join ms_app_userprofile p2 on p.createdby = p2.userid\r\n" + 
			"where p.\"source\"='rooc'\r\n" + 
			"ORDER BY p.userprofileid asc")
    public List<LinkedHashMap> findAllv3();

	@Select("select "
			+ " userprofileid,userid,roleid,companyid,username,name,address,emailaddress\n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,whatsappnum,isactive,source,department,area "
			+ " from ms_app_userprofile " + 
			" where source <> 'rooc' "
			+ " ORDER BY userprofileid asc")
    public List<UserProfile> findAllother();
	
	@Select("SELECT "
			+ " userprofileid,userid,roleid,companyid,username,name,address,emailaddress\n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,whatsappnum,isactive,source,department,area "
			+ " FROM ms_app_userprofile WHERE userprofileid = #{userprofileid}")
    public UserProfileView findOne(Integer userprofileid);
	
	@Select("SELECT  "
			+ " userprofileid,userid,roleid,companyid,username,name,address,emailaddress\n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,whatsappnum,isactive,source,department,area "
			+ "  FROM ms_app_userprofile WHERE userid = #{userid} limit 1")
    public UserProfile findByuserid(Integer userid);

	@Select("SELECT  "
			+ " name,roleid,userid,username"
			+ "  FROM ms_app_userprofile WHERE userid = #{userid} limit 1")
    public HashMap findByuseridv3(Integer userid);

	@Select("SELECT  "
			+ " userprofileid,userid,roleid,companyid,username,name,address,emailaddress,password \n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,whatsappnum,isactive,source,department,area "
			+ "  FROM ms_app_userprofile WHERE userid = #{userid} limit 1")
    public UserProfile findpasswordByuserid(Integer userid);
	
	@Select("SELECT  "
			+ " userprofileid,userid,userchannelid,roleid,companyid,username,name,address,emailaddress\n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,whatsappnum,isactive,source,department,area "
			+ "  FROM ms_app_userprofile WHERE userchannelid = #{userchannelid} limit 1")
    public UserProfile findByuserchannelid(Integer userchannelid);
	
	@Select("SELECT username FROM ms_app_userprofile WHERE emailaddress = #{emailaddress}")
    public UserProfile findByEmailaddress(String emailaddress);
	
	@Select("SELECT username FROM ms_app_userprofile WHERE username = #{username}")
    public UserProfile findByUsername(String username);
	
	@SelectKey(statement = "currval('userprofileid')", keyProperty = "userprofileid", before = true , resultType = int.class)
	@Select("Insert into ms_app_userprofile(userid,roleid,username,password,name,address,emailaddress,userchannelid"
			+ ",source"
			+ ",isactive"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{userid},#{roleid},#{username},#{password},#{name},#{address},#{emailaddress},#{userchannelid}"
			+ ",#{source}"
			+ ",#{isactive}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING "
			+ " userprofileid,userid,roleid,username,name,address,emailaddress\n" +
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,isactive,source "
			+ "  ") //,#{createdBy},#{updateBy}
    public UserProfile Save(UserProfile userprofile);
	/*@SelectKey(statement = "currval('userprofileid')", keyProperty = "userprofileid", before = true , resultType = int.class)
	@Select("Insert into ms_app_userprofile(userid,roleid,companyid,username,password,name,address,emailaddress,userchannelid"
			+ ",domain_id,display_name,job_title,company_code,kanwil_name,branch_name,kcp_name,source"
			+ ",whatsappnum,isactive"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{userid},#{roleid},#{companyid},#{username},#{password},#{name},#{address},#{emailaddress},#{userchannelid}"
			+ ",#{domain_id},#{display_name},#{job_title},#{company_code},#{kanwil_name},#{branch_name},#{kcp_name},#{source}"
			+ ",#{whatsappnum},#{isactive}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING "
			+ " userprofileid,userid,roleid,companyid,username,name,address,emailaddress\n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,whatsappnum,isactive,source "
			+ "  ") //,#{createdBy},#{updateBy}
    public UserProfile Save(UserProfile userprofile);*/
	/*@Select("Insert into ms_app_userprofile(userid,roleid,companyid,username,password,name,address,emailaddress,userchannelid"
			+ ",domain_id,display_name,job_title,company_code,kanwil_name,branch_name,kcp_name,source,isactive"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{userid},#{roleid},#{companyid},#{username},#{password},#{name},#{address},#{emailaddress},#{userchannelid}"
			+ ",#{domain_id},#{display_name},#{job_title},#{company_code},#{kanwil_name},#{branch_name},#{kcp_name},#{source},#{isactive}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING userprofileid") //,#{createdBy},#{updateBy}
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
    public String Save(UserProfile userprofile);*/
	
	/*@Select("Update ms_app_userprofile SET companyid=#{companyid}"
			+ " ,roleid=#{roleid}"
			+ " ,username=#{username}"
			+ " ,name=#{name}"
			+ " ,address=#{address}"
			+ " ,emailaddress=#{emailaddress}"
			+ " ,domain_id=#{domain_id}"
			+ " ,display_name=#{display_name}"
			+ " ,job_title=#{job_title}"
			+ " ,company_code=#{company_code}"
			+ " ,kanwil_name=#{kanwil_name}"
			+ " ,branch_name=#{branch_name}"
			+ " ,kcp_name=#{kcp_name}"
			+ " ,source=#{source}"
			+ " ,isactive=#{isactive}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userprofileid=#{userprofileid} "
			+ " RETURNING userprofileid")
    public String Update(UserProfile userprofile);*/
	/*@Select("Update ms_app_userprofile SET companyid=#{companyid}"
			+ " ,roleid=#{roleid}"
			+ " ,username=#{username}"
			+ " ,name=#{name}"
			+ " ,address=#{address}"
			+ " ,emailaddress=#{emailaddress}"
			+ " ,domain_id=#{domain_id}"
			+ " ,display_name=#{display_name}"
			+ " ,job_title=#{job_title}"
			+ " ,company_code=#{company_code}"
			+ " ,kanwil_name=#{kanwil_name}"
			+ " ,branch_name=#{branch_name}"
			+ " ,kcp_name=#{kcp_name}"
			+ " ,source=#{source}"
			+ " ,isactive=#{isactive}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userprofileid=#{userprofileid} "
			+ " RETURNING  "
			+ " userprofileid,userid,roleid,companyid,username,name,address,emailaddress\n" + 
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,domain_id,job_title,company_code,kanwil_name,branch_name,kcp_name,isactive,source "
			+ " ")
    public UserProfile Update(UserProfile userprofile);*/
	@Select("Update ms_app_userprofile SET "
			+ " roleid=#{roleid}"
			+ " ,username=#{username}"
			+ " ,name=#{name}"
			+ " ,address=#{address}"
			+ " ,emailaddress=#{emailaddress}"
			+ " ,source=#{source}"

			+ " ,isactive=#{isactive}"

			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userprofileid=#{userprofileid} "
			+ " RETURNING  "
			+ " userprofileid,userid,roleid,username,name,address,emailaddress\n" +
			" ,createdby,updatedby,createdtime,updatedtime,userchannelid\n" + 
			" ,isactive,source"
			+ " ")
    public UserProfile Update(UserProfile userprofile);
	
	@Select("Update ms_app_userprofile SET "
			+ " roleid=#{roleid}"
			+ " ,isactive=#{isactive}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userid=#{userid} ")
    public void Updatefromuser(UserProfile userprofile);
	
	@Select("Update ms_app_userprofile SET "
			+ " password=#{password}"
			+ " ,isactive=#{isactive}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE userid=#{userid} ")
    public void Updatepassword(UserProfile userprofile);

}
