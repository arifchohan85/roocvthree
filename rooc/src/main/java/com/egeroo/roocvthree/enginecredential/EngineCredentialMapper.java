package com.egeroo.roocvthree.enginecredential;

import java.util.List;


import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface EngineCredentialMapper {
	
	@Select("SELECT * FROM ms_eng_enginecredential ORDER BY enginecredentialid")
    public List<EngineCredential> findAll();
	
	@Select("SELECT * FROM ms_eng_enginecredential WHERE enginecredentialid = #{enginecredentialid}")
    public EngineCredential findOne(Integer enginecredentialid);
	
	@SelectKey(statement = "currval('enginecredentialid')", keyProperty = "enginecredentialid", before = true , resultType = int.class)
	@Select("Insert into ms_eng_enginecredential(api,username,password"
			+ ",channelapi,localapi,channelusername,channelpassword,initialincrement,rootcategory,rtsaapi,mlapi"
			+ ",clientid,applicationid,passwordencryptionkey,bearerkey,voiceapi,isusevoice"
			+ ",createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ "VALUES (#{api},#{username},#{password}"
			+ ",#{channelapi},#{localapi},#{channelusername},#{channelpassword},#{initialincrement},#{rootcategory},#{rtsaapi},#{mlapi}"
			+ ",#{clientid},#{applicationid},#{passwordencryptionkey},#{bearerkey},#{voiceapi},#{isusevoice}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING enginecredentialid") //,#{createdBy},#{updateBy}
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
	public String Save(EngineCredential enginecredential);
	//@Options(useGeneratedKeys = true, keyProperty = "enginecredentialid")
	
	@Select("Update ms_eng_enginecredential SET api=#{api}"
			+ " ,username=#{username}"
			+ " ,password=#{password}"
			+ " ,channelapi=#{channelapi}"
			+ " ,localapi=#{localapi}"
			+ " ,channelusername=#{channelusername}"
			+ " ,channelpassword=#{channelpassword}"
			+ " ,initialincrement=#{initialincrement}"
			+ " ,rootcategory=#{rootcategory}"
			+ " ,rtsaapi=#{rtsaapi}"
			+ " ,mlapi=#{mlapi}"
			+ " ,clientid=#{clientid}"
			+ " ,applicationid=#{applicationid}"
			+ " ,passwordencryptionkey=#{passwordencryptionkey}"
			+ " ,bearerkey=#{bearerkey}"
			+ " ,voiceapi=#{voiceapi}"
			+ " ,isusevoice=#{isusevoice}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE enginecredentialid=#{enginecredentialid} "
			+ " RETURNING enginecredentialid")
    public String Update(EngineCredential enginecredential);

}
