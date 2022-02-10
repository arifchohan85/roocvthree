package com.egeroo.roocvthree.intent;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;






public interface IntentMapper {
	
	@Select("SELECT * FROM ms_eng_intent ORDER BY intentid")
    public List<Intent> findAll();

	@Select("SELECT * FROM ms_eng_intent where active=${active} ORDER BY intentid")
    public List<Intent> findAllV3(@Param("active") int active);

	@Select("SELECT intentid,question FROM ms_eng_intent ORDER BY intentid")
    public List<Intent> findIntent();
	
	@Select("SELECT iretquestionid,question FROM ms_eng_intent ORDER BY iretquestionid")
    public List<Intent> findIntentict();
	
	@Select("SELECT * FROM ms_eng_intent WHERE intentid = #{intentid}")
    public Intent findOne(Integer intentid);
	
	@Select("delete from ms_eng_intent WHERE intentid = #{intentid} RETURNING * ")
    public Intent deleteOne(Integer intentid);
	
	@Select("delete from ms_eng_intent WHERE directoryid = #{directoryid} RETURNING * ")
    public List<Intent> deletebyDirectory(Integer directoryid);
	
	@Select("delete from ms_eng_intent WHERE intentid = #{intentid} RETURNING * ")
    public List<Intent> deletebyIntent(Integer intentid);
	
	@Select("SELECT * FROM ms_eng_intent WHERE directoryid = #{directoryid}")
    public List<Intent> findIntentdir(Integer directoryid);
	
	@Select("SELECT * FROM ms_eng_intent WHERE question = #{question} ORDER BY intentid desc limit 1")
    public Intent findIntentquestion(String question);
	
	@Select("SELECT * FROM ms_eng_intent WHERE iretquestionid = #{iretquestionid} ORDER BY intentid desc limit 1")
    public Intent findIntentiretquestionid(Integer iretquestionid);
	
	@Select("SELECT * FROM ms_eng_intent WHERE directoryid = #{directoryid} AND question=#{question} ORDER BY intentid asc limit 1")
    public Intent findBydirectoryidandintentname(@Param("directoryid") Integer directoryid,@Param("question") String question);
	
	/*
		@Select("SELECT COALESCE(max(intentid),0) as maxintentid FROM ms_eng_intent")
    	public Intent findMaxintentid();
	*/
	@Select("SELECT COALESCE(max(intentid),0) as maxintentid FROM tr_eng_maxintent")
	public Intent findMaxintentid();
	
	@SelectKey(statement = "currval('intentid')", keyProperty = "intentid", before = false , resultType = int.class)
	@Select("Insert into ms_eng_intent(intentid,directoryid,question,answer,iretquestionid"
			+ ",isgenerated,intentparentid"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{intentid},#{directoryid},#{question},#{answer}"
			+ ",#{iretquestionid}"
			+ ",#{isgenerated},#{intentparentid}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING intentid") //,#{createdBy},#{updateBy}
	public String Save(Intent intent);

	@SelectKey(statement = "currval('intentid')", keyProperty = "intentid", before = false , resultType = int.class)
	@Select("Insert into ms_eng_intent(intentid,directoryid,question,answer,iretquestionid"
			+ ",isgenerated,intentparentid"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{intentid},#{directoryid},#{question},#{answer}"
			+ ",#{iretquestionid}"
			+ ",#{isgenerated},#{intentparentid}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING intentid") //,#{createdBy},#{updateBy}
	public String Saveengine(Intent intent);
	
	@Select("Update ms_eng_intent SET directoryid=#{directoryid}"
			+ " ,question=#{question}"
			+ " ,answer=#{answer}"
			+ " ,iretquestionid=#{iretquestionid}"
			+ " ,description=#{description}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE intentid=#{intentid} "
			+ " RETURNING intentid")
    public String Updateinternal(Intent intent);
	
	@Select("Update ms_eng_intent SET directoryid=#{directoryid}"
			+ " ,intentparentid=#{intentparentid}"
			+ " ,question=#{question}"
			+ " ,answer=#{answer}"
			+ " ,iretquestionid=#{iretquestionid}"
			+ " ,description=#{description}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE intentid=#{intentid} "
			+ " RETURNING intentid")
    public String Update(Intent intent);
	
	
	
	
	@SelectKey(statement = "currval('recordid')", keyProperty = "recordid", before = false , resultType = int.class)
	@Select("Insert into tr_eng_maxintent(intentid"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{intentid}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING recordid") //,#{createdBy},#{updateBy}
	public String Savemaxintent(MaxIntent maxintent);
	
	
	@Select(" WITH RECURSIVE dirmaptree AS\n" + 
			" (SELECT *, CAST(name As varchar(1000)) As directorymap\n" + 
			" FROM ms_eng_directory\n" + 
			" WHERE (parentid IS null or parentid<=0)\n" + 
			" UNION ALL\n" + 
			" SELECT si.*,\n" + 
			"	CAST(sp.directorymap || '->' || si.name As varchar(1000)) As directorymap\n" + 
			" FROM ms_eng_directory As si\n" + 
			"	INNER JOIN dirmaptree AS sp\n" + 
			"	ON (si.parentid = sp.directoryid)\n" + 
			" )\n" + 
			" SELECT i.intentid\n" + 
			" ,i.directoryid\n" + 
			" ,i.question\n" + 
			" ,'.' as answer\n" + 
			" ,i.description\n" + 
			" ,directorymap  " + 
			" FROM dirmaptree\n" + 
			" inner join ms_eng_intent i\n" + 
			" on dirmaptree.directoryid=i.directoryid\n" + 
			" ORDER BY i.intentid asc; ")
    public List<Intent> extractIntent();
	
	
	@Select(" WITH RECURSIVE subordinates AS (\n" + 
			" SELECT\n" + 
			" parentid as parentId,\n" + 
			" directoryid as theId,\n" + 
			" name as title,\n" + 
			" categorymode,\n" + 
			" 'directory' as type\n" + 
			" FROM\n" + 
			" ms_eng_directory\n" + 
			" WHERE\n" + 
			" " + 
			" (directoryid=#{directoryid} or parentid=#{directoryid})\n" + 
			" UNION\n" + 
			" SELECT\n" + 
			" e.parentid as parentId,\n" + 
			" e.directoryid as theId,\n" + 
			" e.name as title,\n" + 
			" e.categorymode,\n" + 
			" 'directory' as type\n" + 
			" FROM\n" + 
			" ms_eng_directory e\n" + 
			" INNER JOIN subordinates s ON s.theId = e.parentId\n" + 
			" ) \n" + 
			" SELECT\n " + 
			" distinct b.intentid,b.directoryid,b.iretquestionid \n" + 
			" FROM\n" + 
			" ms_eng_intent b\n" + 
			" inner join subordinates so \n" + 
			" on so.theId= b.directoryid\n" + 
			" ; ")
    public List<Intent> findIntentdirrecursive(Integer directoryid);
	

	

}
