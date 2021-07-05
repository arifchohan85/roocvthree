package com.egeroo.roocvthree.intentgroup;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface IntentGroupMapper {
	
	@Select("SELECT * FROM ms_eng_intentgroup ORDER BY intentgroupid")
    public List<IntentGroup> findAll();
	
	@Select("SELECT * FROM ms_eng_intentgroup WHERE intentgroupid = #{intentgroupid}")
    public IntentGroup findOne(Integer intentgroupid);
	
	@SelectKey(statement = "currval('intentgroupid')", keyProperty = "intentgroupid", before = true , resultType = int.class)
	@Select("Insert into ms_eng_intentgroup(intentgroupname) VALUES (#{intentgroupname}) ")
    public void Save(IntentGroup intentgroup);
	
	@Select("Update ms_eng_intentgroup SET intentgroupname=#{intentgroupname} WHERE intentgroupid=#{intentgroupid} ")
    public void Update(IntentGroup intentgroup);

}
