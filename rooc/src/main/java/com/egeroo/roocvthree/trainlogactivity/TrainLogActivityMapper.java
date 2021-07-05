package com.egeroo.roocvthree.trainlogactivity;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface TrainLogActivityMapper {
	
	@SelectKey(statement = "currval('trainlogactivityid')", keyProperty = "trainlogactivityid", before = true , resultType = int.class)
	@Select("Insert into tr_eng_trainlogactivity(interactionid,activityname,expectedintentidbefore"
			+ ",expectedintentidafter,confidencelevel"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{interactionid},#{activityname},#{expectedintentidbefore}"
			+ " ,#{expectedintentidafter},#{confidencelevel}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public TrainLogActivity Save(TrainLogActivity trainlogactivity);

}
