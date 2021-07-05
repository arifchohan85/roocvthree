package com.egeroo.roocvthree.trainlog;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;






public interface TrainLogMapper {
	
	@Select("SELECT * FROM tr_eng_trainlog ORDER BY trainlogid")
    public List<TrainLog> findAll();
	
	
	@Select("SELECT * FROM tr_eng_trainlogdata WHERE trainlogdataid=#{trainlogdataid}")
    public TrainLogData findOne(Integer trainlogdataid);
	
	@Select("SELECT * FROM tr_eng_trainlogdata WHERE interactionid=#{interactionid} ORDER BY trainlogdataid desc limit 1")
    public TrainLogData findOnebyInteractionid(Integer interactionid);
	
	@SelectKey(statement = "currval('trainlogid')", keyProperty = "trainlogid", before = true , resultType = int.class)
	@Select("Insert into tr_eng_trainlog(totaldata"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{totaldata}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public TrainLog Save(TrainLog trainlog);
	
	@SelectKey(statement = "currval('trainlogdataid')", keyProperty = "trainlogdataid", before = true , resultType = int.class)
	@Select("Insert into tr_eng_trainlogdata(trainlogid,interactionid,intentname,answerintentname"
			+ ",confidencelevelbefore,confidencelevelafter,status"
			+ " ,createdby,updatedby,createdtime,updatedtime) " //,createdby,updatedby
			+ " VALUES (#{trainlogid},#{interactionid},#{intentname},#{answerintentname}"
			+ " ,#{confidencelevelbefore},#{confidencelevelafter},#{status}"
			+ " ,#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING *") //,#{createdBy},#{updateBy}
	public TrainLogData Savedetaildata(TrainLogData trainlog);
	
	@Select("Update tr_eng_trainlogdata SET confidencelevelafter=#{confidencelevelafter}"
			+ " ,status=#{status}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE trainlogdataid=#{trainlogdataid} "
			+ " RETURNING *")
    public TrainLogData Updateconfidenceafter(TrainLogData trainlogdata);
	
}
