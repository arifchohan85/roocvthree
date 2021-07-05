package com.egeroo.roocvthree.trailingrecord;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface TrailingRecordMapper {
	@Select("SELECT a.*,u.username as username FROM ms_app_trailingrecord a "
			+ " inner join ms_app_userprofile u\n"
			+ " on a.createdby::integer=u.userprofileid\n"  
			+ " ORDER BY a.trailingrecordid"
			+ " ")
    public List<TrailingRecord> findAll();
	
	@SelectKey(statement = "currval('trailingrecordid')", keyProperty = "trailingrecordid", before = true , resultType = int.class)
	@Select("Insert into ms_app_trailingrecord(createdby,recordid,classname,actionfor,createddate,requestbody,requestquerystring) " //,createdby,updatedby
			+ "VALUES (#{createdby},#{recordid},#{classname},#{actionfor},#{createddate},#{requestbody},#{requestquerystring}) "
			+ " RETURNING trailingrecordid") //,#{createdBy},#{updateBy}
	public String Save(TrailingRecord trailingrecord);
}
