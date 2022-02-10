package com.egeroo.roocvthree.nodesource;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;





public interface NodeSourceMapper {
	
	@Select("SELECT * FROM ms_eng_nodesource ORDER BY nodesourceid")
    public List<NodeSource> findAll();
	
	@Select("SELECT * FROM ms_eng_nodesource WHERE nodesourceid = #{nodesourceid}")
    public NodeSource findOne(Integer nodesourceid);
	
	@Select("SELECT * FROM ms_eng_nodesource WHERE nodeid = #{nodeid}")
    public NodeSource findOnebyNodeid(String nodeid);
	
	@SelectKey(statement = "currval('nodesourceid')", keyProperty = "nodesourceid", before = false , resultType = int.class)
	@Select("Insert into ms_eng_nodesource(type,nodesourceid,directoryid,nodeid,parentnodeid,previousnodeid"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{type},#{intentid},#{directoryid},#{nodeid},#{parentnodeid},#{previousnodeid}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING nodesourceid") //,#{createdBy},#{updateBy}
	public Integer Save(NodeSource nodesource);

}
