package com.egeroo.roocvthree.company;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface CompanyMapper {

	@Select("SELECT * FROM ms_app_company ORDER BY companyid")
    public List<Company> findAll();
	
	@Select("SELECT * FROM ms_app_company WHERE companyid = #{companyid}")
    public Company findOne(Integer companyid);
	
	@SelectKey(statement = "currval('companyid')", keyProperty = "companyid", before = true , resultType = int.class)
	@Select("Insert into ms_app_company(companyname,address,emailaddress,phoneno,ext,createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{companyname},#{address},#{emailaddress},#{phoneno},#{ext},#{createdby},#{updatedby},#{createdtime},#{updatedtime} ")
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
    public void Save(Company company);
	
	@Select("Update ms_app_company SET companyname=#{companyname}"
			+ " ,address=#{address}"
			+ " ,emailaddress=#{emailaddress}"
			+ " ,phoneno=#{phoneno}"
			+ " ,ext=#{ext}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE companyid=#{companyid} ")
    public void Update(Company company);
}
