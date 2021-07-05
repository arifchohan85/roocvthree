package com.egeroo.roocvthree.botrate;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;


public interface BotrateMapper {
	
	@Select("SELECT * FROM tr_eng_botrate ORDER BY botrateid")
    public List<Botrate> findAll();
	
	@Select("SELECT * FROM tr_eng_botrate WHERE botrateid = #{botrateid}")
    public Botrate findOne(Integer botrateid);
	
	@SelectKey(statement = "currval('botrateid')", keyProperty = "botrateid", before = true , resultType = int.class)
	@Select("Insert into tr_eng_botrate(userid,customerchannelid,rate,channel,createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{userid},#{customerchannelid},#{rate},#{channel},#{createdby},#{updatedby},#{createdtime},#{updatedtime} ) "
			+ " RETURNING * ")
	//@Select("Insert into ms_app_userrole(roleid,rolename) VALUES (#{roleid},#{rolename}) ")
	//@Options(useGeneratedKeys=true, keyProperty="roleid")
    public Botrate Save(Botrate botrate);
	
	@Select("Update tr_eng_botrate SET userid=#{userid}"
			+ " ,customerchannelid=#{customerchannelid}"
			+ " ,rate=#{rate}"
			+ " ,channel=#{channel}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE botrateid=#{botrateid} "
			+ " RETURNING * ")
    public Botrate Update(Botrate botrate);

}
