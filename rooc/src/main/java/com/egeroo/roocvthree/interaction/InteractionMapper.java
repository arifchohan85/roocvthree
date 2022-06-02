package com.egeroo.roocvthree.interaction;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;



public interface InteractionMapper {
	
	@Select("SELECT * FROM tr_eng_interaction ORDER BY interactionid")
    public List<Interaction> findAll();
	
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_userprofile u\n" + 
			 " on a.createdby=u.userprofileid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid" +
			 " ORDER BY a.interactionid;")
    public List<Interaction> findAlljoin();
	*/
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_user u\n" + 
			 " on a.createdby=u.userid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid" +
			 " ORDER BY a.interactionid;")
   public List<Interaction> findAlljoin();
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customerame,intict.question as answerintentname " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_user u\n" + 
			 " on a.createdby=u.userid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid" +
			 " WHERE a.istrain=0 AND a.expectedintentid > 0 " +
			 " ORDER BY a.interactionid;")
	public List<Interaction> findAlljointrain();

	@Select("SELECT a.interactionid \"interactionId\", a.createdtime \"createdTime\", \r\n" + 
			"a.question , \r\n" + 
			"b.question as intent, \r\n" + 
			"a.expectedintentid \"expectedIntent\", \r\n" + 
			"a.confidencelevel confidence   \r\n" + 
			"FROM tr_eng_interaction a   \r\n" + 
			"left join ms_eng_intent b on a.expectedintentid=b.intentid   \r\n" + 
			"left join ms_app_user u on a.createdby=u.userid   \r\n" + 
			"left join channel.user ch on a.customerchannelid=ch.id  \r\n" + 
			"left join ms_eng_intent intict on a.answerintentid=intict.iretquestionid  \r\n" + 
			"WHERE a.active=1 and a.istrain=0 AND a.expectedintentid > 0 \r\n" + 
			"ORDER BY a.interactionid;")
	public List<LinkedHashMap> findAlljointrainv3();

	@Select("SELECT count(a.*) as countalldata " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_user u\n" + 
			 " on a.createdby=u.userid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid" +
			 " WHERE a.istrain=0 AND a.expectedintentid > 0 " +
			 " ;")
	public Interaction findCountjointrain();
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname "
			+ " ,coalesce(ch.anyid, '')  as anyid " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_user u\n" + 
			 " on a.createdby=u.userid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid "
			 + " where a.createdtime between #{datefrom} and #{dateto}  " +
			 " ORDER BY a.interactionid;")
  public List<InteractionChathistory> findAlljoindate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("SELECT a.chatid \"chatId\",a.roomid \"roomId\",a.interactionid \"interactionId\", \r\n" + 
			"a.expectedintentid \"expectedIntent\",a.question question, \r\n" + 
			"intict.question \"intentAnswer\",a.answerby \"answerBy\", \r\n" + 
			"a.channel,a.confidencelevel confidence,a.minconfidence \"minConfidence\", \r\n" + 
			"coalesce(ch.name, '') as \"customerName\", \r\n" + 
			"a.createdtime createdTime \r\n" + 
			"FROM tr_eng_interaction a \r\n" + 
			"left join ms_eng_intent b on a.expectedintentid=b.intentid \r\n" + 
			"left join ms_app_user u on a.createdby=u.userid \r\n" + 
			"left join channel.user ch on a.customerchannelid=ch.id \r\n" + 
			"left join ms_eng_intent intict on a.answerintentid=intict.iretquestionid \r\n" + 
			"where ismanual=0 and a.createdtime between #{datefrom} and #{dateto}    \r\n" + 
			"ORDER BY a.interactionid;")
  public List<LinkedHashMap> findAlljoindatev3(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname "
			+ " ,coalesce(ch.anyid, '')  as anyid " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_user u\n" + 
			 " on a.createdby=u.userid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid "
			 + " where a.createdtime between #{datefrom} AND #{dateto} "
			 + " AND a.isupdated=1 "
			 + " AND a.istrain=0 " +
			 " ORDER BY a.interactionid;")
  public List<InteractionIndex> findAlljoinistraindate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	
	@Select("SELECT coalesce(COUNT(a.*),0) as countalldata " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_userprofile u\n" + 
			 " on a.createdby=u.userprofileid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid" +
			 " ;")
   public Interaction findCountalljoin();
	
	@Select("SELECT coalesce(COUNT(a.*),0) as countalldata " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_userprofile u\n" + 
			 " on a.createdby=u.userprofileid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid " +
			 " ${where}  " +
			 " ;")
  public InteractionIndex findCountalljoinpaging(@Param("where") String where);
	
	@Select(" select count(*) from tr_eng_interaction\n" + 
			" where createdtime between #{datefrom} and #{dateto}"
			+ " and customerchannelid=#{customerchannelid}; ")
	public Interaction findCountinteractionperday(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("customerchannelid") Integer customerchannelid);
	
	@Select("  select anyid from channel.user " + 
			" where "
			+ " id=#{id} limit 1;")
	public Interaction findAnyidbycustomerchannelid(@Param("id") Integer id);
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_userprofile u\n" + 
			 " on a.createdby=u.userprofileid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid" +
			 " ORDER BY a.interactionid" +
			 " LIMIT #{limit} OFFSET #{offset} ;")
   public List<Interaction> findGetalljoinpaging(@Param("limit") Integer limit,@Param("offset") Integer offset,@Param("paging") Integer paging);
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname " +
			 " FROM tr_eng_interaction a " +
			 " left join ms_eng_intent b \n" + 
			 " on a.expectedintentid=b.intentid\n" + 
			 " left join ms_app_userprofile u\n" + 
			 " on a.createdby=u.userprofileid\n" + 
			 " left join channel.user ch " +
			 " on a.customerchannelid=ch.id" +
			 " left join ms_eng_intent intict \n" + 
			 " on a.answerintentid=intict.iretquestionid " +
			 " ${where} " +
			 " ${sort} " +
			 " LIMIT #{limit} OFFSET #{offset} ;")
  public List<InteractionIndex> findGetlistjoinpaging(@Param("limit") Integer limit,@Param("offset") Integer offset,@Param("paging") Integer paging,@Param("where") String where,@Param("sort") String sort,@Param("countData") Integer countData);
	
	@Select("SELECT interactionid,question FROM tr_eng_interaction ORDER BY interactionid")
    public List<Interaction> findInteraction();
	
	@Select("SELECT * FROM tr_eng_interaction WHERE interactionid = #{interactionid}")
    public Interaction findOne(Integer interactionid);
	
	@Select("SELECT * FROM tr_eng_interaction WHERE faqidstr = #{faqidstr} ORDER BY interactionid desc limit 1")
    public Interaction findOnebyfaqidstr(String faqidstr);
	
	@Select("SELECT * FROM tr_eng_interaction WHERE iretrespondid = #{iretrespondid} ORDER BY interactionid desc limit 1")
    public Interaction findOnebyrespondid(Integer iretrespondid);
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.intentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" inner join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " WHERE (a.istrain=1 OR a.istrain=0) AND a.intentid = #{intentid}")
    public List<Interaction> findInteractionintent(Integer intentid);
	
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname \n" + 
			" FROM tr_eng_interaction a \n" + 
			" inner join ms_eng_intent b \n" + 
			" on a.intentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" inner join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " WHERE a.istrain=1 AND a.intentid>0 ")*/
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname \n" + 
			" FROM tr_eng_interaction a \n" + 
			" inner join ms_eng_intent b \n" + 
			" on a.intentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" left join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " WHERE a.istrain=1 "
			+ " ;")
    public List<Interaction> extractInteractionintent();
	
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,ch.name as customername,intict.question as answerintentname  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" inner join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" inner join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " WHERE a.istrain=1 AND a.expectedintentid = #{expectedintentid}")
    public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid);
    */
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" 
			+ " WHERE a.istrain=1 AND a.expectedintentid = #{expectedintentid}")
    public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid);*/
	
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" left join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" 
			+ " WHERE a.istrain=1 AND a.expectedintentid = #{expectedintentid}")
    public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid);
	*/
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" left join ms_app_userprofile u\n" + 
			" on a.updatedby=u.userid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" 
			+ " WHERE a.istrain=1 AND a.expectedintentid = #{expectedintentid}")
    public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid);
	*/
	/*
	 @Select(" SELECT a.*,b.question as intentname\n " + 
			" ,u.username as username\n " + 
			" ,coalesce(ch.name, '') as customername\n " + 
			" ,(\n " + 
			"	select utld.username\n " + 
			"	from ms_app_userprofile utld\n " + 
			"	inner join tr_eng_trainlogdata tld\n " + 
			"	on utld.userid = tld.updatedby\n " + 
			"	where tld.interactionid= a.interactionid\n " + 
			"	order by trainlogdataid desc limit 1\n " + 
			" ) as commitedby\n " + 
			" FROM tr_eng_interaction a\n " + 
			" left join ms_eng_intent b\n " + 
			" on a.expectedintentid=b.intentid\n " + 
			" left join ms_app_userprofile u\n " + 
			" on a.updatedby=u.userid\n " + 
			" left join channel.user ch \n " + 
			" on a.customerchannelid=ch.id\n " + 
			" left join tr_eng_trainlogdata tld\n " + 
			" on a.interactionid=tld.interactionid\n " + 
			" left join ms_app_userprofile utld\n " + 
			" on tld.updatedby=utld.userid\n " + 
			" WHERE a.istrain=1 AND a.expectedintentid =#{expectedintentid} " + 
			" AND tld.STATUS='REPROJECT'\n " + 
			" ; ")
    public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid);
    */
	@Select(" SELECT a.*,b.question as intentname\n " + 
			" ,u.username as username\n " + 
			" ,coalesce(ch.name, '') as customername\n " + 
			" ,(\n " + 
			"	select utld.username\n " + 
			"	from ms_app_userprofile utld\n " + 
			"	inner join tr_eng_trainlogdata tld\n " + 
			"	on utld.userid = tld.updatedby\n " + 
			"	where tld.interactionid= a.interactionid\n " + 
			"   AND tld.STATUS='REPROJECT' " + 
			"	order by trainlogdataid desc limit 1\n " + 
			" ) as commitedby\n " + 
			" FROM tr_eng_interaction a\n " + 
			" left join ms_eng_intent b\n " + 
			" on a.expectedintentid=b.intentid\n " + 
			" left join ms_app_userprofile u\n " + 
			" on a.updatedby=u.userid\n " + 
			" left join channel.user ch \n " + 
			" on a.customerchannelid=ch.id\n " + 
			" WHERE (a.istrain=1 OR a.istrain=0) AND a.expectedintentid =#{expectedintentid} " + 
			" ; ")
    public List<Interaction> findInteractionexpectedintentid(Integer expectedintentid);
	
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" inner join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" inner join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " WHERE a.istrain=1 AND a.expectedintentid>0 ")*/
	/*@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" inner join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" left join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " ; ")
    public List<Interaction> extractInteractionexpectedintentid();*/
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" inner join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" left join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " WHERE (a.istrain=1 OR a.istrain=0) "
			+ " ; ")
    public List<Interaction> extractInteractionexpectedintentid();
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.intentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" left join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " ")
    public List<Interaction> findInteractionintentwithjoin();
	
	@Select("SELECT a.*,b.question as intentname,u.username as username,coalesce(ch.name, '') as customername,intict.question as answerintentname  \n" + 
			" FROM tr_eng_interaction a \n" + 
			" left join ms_eng_intent b \n" + 
			" on a.expectedintentid=b.intentid\n" + 
			" inner join ms_app_userprofile u\n" + 
			" on a.createdby=u.userprofileid\n" + 
			" left join channel.user ch\n" + 
			" on a.customerchannelid=ch.id" +
			" left join ms_eng_intent intict \n" + 
			" on a.answerintentid=intict.iretquestionid"
			+ " ")
    public List<Interaction> findInteractionexpectedintentwithjoin();
	
	@Select("SELECT COALESCE(max(interactionid),0) as maxinteractionid FROM tr_eng_interaction")
    public Interaction findMaxinteractionid();
	
	@SelectKey(statement = "currval('interactionid')", keyProperty = "interactionid", before = false , resultType = int.class)
	@Select("Insert into tr_eng_interaction(intentid,faqidstr,expectedintentid,question,minconfidence,maxconfidence"
			+ ",active,iretrespondid,isupdated,istrain,confidencelevel,qid,ismanual,messagetype,questionid"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{intentid},#{faqidstr},#{expectedintentid},#{question},#{minconfidence},#{maxconfidence}"
			+ ",#{active},#{iretrespondid},#{isupdated},#{istrain},#{confidencelevel},#{qid},#{ismanual},#{messagetype},#{questionid}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING interactionid") //,#{createdBy},#{updateBy}
	public Integer Save(Interaction interaction);
	
	@SelectKey(statement = "currval('interactionid')", keyProperty = "interactionid", before = false , resultType = int.class)
	@Select("Insert into tr_eng_interaction(intentid,faqidstr,expectedintentid,question,minconfidence,maxconfidence"
			+ ",active,iretrespondid,isupdated,istrain,confidencelevel,qid"
			+ ",channel,answerby,idcustomerchannel,roomid,chatid,userchannelid"
			+ ",customerchannelid,responsechatid,startchattime,responsechattime,ismanual,answerintentid,messagetype"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{intentid},#{faqidstr},#{expectedintentid},#{question},#{minconfidence},#{maxconfidence}"
			+ ",#{active},#{iretrespondid},#{isupdated},#{istrain},#{confidencelevel},#{qid}"
			+ ",#{channel},#{answerby},#{idcustomerchannel},#{roomid},#{chatid},#{userchannelid}"
			+ ",#{customerchannelid},#{responsechatid},#{startchattime},#{responsechattime},#{ismanual},#{answerintentid},#{messagetype}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING interactionid") //,#{createdBy},#{updateBy}
	public String Saveonly(Interaction interaction);
	
	@SelectKey(statement = "currval('interactionid')", keyProperty = "interactionid", before = false , resultType = int.class)
	@Select("Insert into tr_eng_interaction(intentid,faqidstr,expectedintentid,question,minconfidence,maxconfidence"
			+ ",active,iretrespondid,isupdated,istrain,confidencelevel,qid"
			+ ",channel,answerby,idcustomerchannel,roomid,chatid,userchannelid"
			+ ",customerchannelid,responsechatid,startchattime,responsechattime,ismanual,answerintentid,messagetype"
			+ ",createdby,updatedby,createdtime,updatedtime) "
			+ "VALUES (#{intentid},#{faqidstr},#{expectedintentid},#{question},#{minconfidence},#{maxconfidence}"
			+ ",#{active},#{iretrespondid},#{isupdated},#{istrain},#{confidencelevel},#{qid}"
			+ ",#{channel},#{answerby},#{idcustomerchannel},#{roomid},#{chatid},#{userchannelid}"
			+ ",#{customerchannelid},#{responsechatid},#{startchattime},#{responsechattime},#{ismanual},#{answerintentid},#{messagetype}"
			+ ",#{createdby},#{updatedby},#{createdtime},#{updatedtime}) "
			+ " RETURNING interactionid") //,#{createdBy},#{updateBy}
	public String Saveinternal(Interaction interaction);
	
	@Select("Update tr_eng_interaction SET expectedintentid=#{expectedintentid}"
			+ " ,isupdated=#{isupdated}"
			+ " ,istrain=#{istrain}"
			+ " ,questionid=#{questionid}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE interactionid=#{interactionid} "
			+ " RETURNING interactionid")
    public Integer Update(Interaction interaction);
	
	@Select("Update tr_eng_interaction SET expectedintentid=#{expectedintentid}"
			+ " ,isupdated=#{isupdated}"
			+ " ,istrain=#{istrain}"
			+ " ,answerby=#{answerby}"
			+ " ,userchannelid=#{userchannelid}"
			+ " ,responsechatid=#{responsechatid}"
			+ " ,responsechattime=#{responsechattime}"
			+ " ,answerintentid=#{answerintentid}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE faqidstr=#{faqidstr} "
			+ " RETURNING interactionid")
    public String Updateonly(Interaction interaction);
	
	@Select("Update tr_eng_interaction SET expectedintentid=#{expectedintentid}"
			+ " ,isupdated=#{isupdated}"
			+ " ,istrain=#{istrain}"
			+ " ,answerby=#{answerby}"
			+ " ,userchannelid=#{userchannelid}"
			+ " ,responsechatid=#{responsechatid}"
			+ " ,responsechattime=#{responsechattime}"
			+ " ,answerintentid=#{answerintentid}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE faqidstr=#{faqidstr} "
			+ " RETURNING interactionid")
    public String Updateinternal(Interaction interaction);
	
	@Select("Update tr_eng_interaction SET "
			+ " istrain=1"
			+ " ,isupdated=0"
			+ " WHERE istrain=0 ")
    public void Updateistrain();
	
	@Select("Update tr_eng_interaction SET "
			+ " istrain=0"
			+ " ,isupdated=1"
			+ " ,iretrespondid=#{iretrespondid}"
			+ " WHERE faqidstr=#{faqidstr} ")
    public void Updateengineidonly(Interaction interaction);
	
	@Select("Update tr_eng_interaction SET "
			+ " iretrespondid=#{iretrespondid}"
			+ " WHERE interactionid=#{interactionid} ")
    public void Updatesyncroocengineid(Interaction interaction);
	
	@Select("delete from tr_eng_interaction WHERE expectedintentid = #{expectedintentid} RETURNING * ")
    public List<Interaction> deletebyIntent(Integer expectedintentid);
	
	@Select("Update tr_eng_interaction SET expectedintentid=#{expectedintentid} "
			+ " ,isupdated=#{isupdated}"
			+ " ,updatedby=#{updatedby}"
			+ " ,updatedtime=#{updatedtime}"
			+ " WHERE interactionid=#{interactionid} "
			+ " RETURNING interactionid")
    public String Updatedelete(Interaction interaction);
	
	/* v3 */
	
	@Select(" SELECT a.interactionid as id,a.intentid as intentId" +
			" ,a.question as question,a.createdtime as createdOn" + 
			" ,u.name as createdBy\n " + 
			" FROM tr_eng_interaction a\n " + 
			" left join ms_app_userprofile u\n " + 
			" on a.createdby=u.userid\n " + 
			" WHERE  a.expectedintentid =#{expectedintentid} " + 
			" ; ")
    public List<InteractionResponse> findlistquestionbyexpectedintentid(Integer expectedintentid);
	
	@Select(" SELECT a.interactionid as id,a.intentid as intentId" +
			" ,a.question as question,a.createdtime as createdOn" + 
			" ,u.name as createdBy\n " + 
			" FROM tr_eng_interaction a\n " + 
			" left join ms_app_userprofile u\n " + 
			" on a.createdby=u.userid\n " + 
			"   " + 
			" ; ")
    public List<InteractionResponse> findlistquestions();
	
	@SelectKey(statement = "currval('questionid')", keyProperty = "questionid", before = false , resultType = int.class)
	@Select("Insert into ms_mst_question(question,intentid,hasdetail "
			+ ") "
			+ "VALUES (#{question},#{intentid},#{hasdetail}) "
			+ " RETURNING questionid") //,#{createdBy},#{updateBy}
	public Integer Savequestion(Question question);
	
	@Select("SELECT * " +
			 " FROM ms_mst_question " +
			 " WHERE question =#{question} AND intentid =#{intentid} ORDER BY questionid asc limit 1" + 
			 " ;")
 public Question findGetQuestion(@Param("question") String question,@Param("intentid") Integer intentid);
	
	@Select(" SELECT *" +
			" FROM tr_eng_interaction  " + 
			" WHERE question =#{question} AND expectedintentid =#{expectedintentid} ORDER BY interactionid asc limit 1" + 
			"   " + 
			" ; ")
    public Interaction findlistinteractionbyquestions(@Param("question") String question,@Param("expectedintentid") Integer expectedintentid);
	
	@Select("Update ms_mst_question SET hasdetail=#{hasdetail} "
			+ " WHERE questionid=#{questionid} "
			+ " RETURNING questionid")
    public Integer Updatequestionhasdetail(Question question);
	
	/* v3 */
	

}
