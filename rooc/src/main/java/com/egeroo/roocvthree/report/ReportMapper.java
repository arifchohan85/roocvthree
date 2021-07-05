package com.egeroo.roocvthree.report;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;





public interface ReportMapper {
	
	@Select("select usrc.*,u.username from ms_app_usersource usrc "
			+ " inner join ms_app_user u "
			+ " on u.userid=usrc.userid "
			+ " where usrc.logindate BETWEEN #{datefrom} AND #{dateto} ;\n" + 
	" ")
	public List<Report> findRptuserlogin(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select intn.*,u.username,dir.name as dirname from ms_eng_intent intn "
			+ " inner join ms_app_user u "
			+ " on u.userid=intn.updatedby "
			+ " inner join ms_eng_directory dir "
			+ " on intn.directoryid=dir.directoryid "
			+ " where intn.updatedtime BETWEEN #{datefrom} AND #{dateto} "
			+ " ORDER BY intn.updatedtime ASC ;\n" + 
	" ")
	public List<Report> findRptupdateintent(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	/*@Select(" select intr.interactionid,intent.question as intentname,intr.updatedtime\n" + 
			" ,(select count(interactionid) from tr_eng_interaction eint \n" + 
			"  where eint.interactionid=intr.interactionid\n" + 
			"  and eint.answerby like '%bot%'\n" + 
			"  ) as answerbybot\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			"  where eint.interactionid=intr.interactionid\n" + 
			"  and (eint.answerby is not null and eint.answerby not ilike '%bot%' and eint.answerby <> '')\n" + 
			"  ) as answerbyagent\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			"  where eint.interactionid=intr.interactionid\n" + 
			"  and (eint.answerby is null or eint.answerby = '')\n" + 
			"  ) as notanswer\n" + 
			" from tr_eng_interaction intr\n" + 
			" inner join ms_eng_intent intent\n" + 
			" on intr.answerintentid=intent.intentid\n" + 
			" where (intr.createdtime BETWEEN #{datefrom} AND #{dateto})\n" + 
			" and (intr.answerintentid >0);" + 
	" ")
	public List<Report> findRptintentbycategory(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	*/
	/*@Select(" select answerintentid,intent.question as intentname\n" + 
			" ,(select count(interactionid) from tr_eng_interaction eint \n" + 
			"  where eint.answerintentid=intr.answerintentid\n" + 
			"  and \n" + 
			"  eint.answerby like '%bot%'\n" + 
			"  ) as answerbybot\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			"  where eint.answerintentid=intr.answerintentid\n" + 
			"  and \n" + 
			"  (eint.answerby is not null and eint.answerby not ilike '%bot%' and eint.answerby <> '')\n" + 
			"  ) as answerbyagent\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			"  where eint.answerintentid=intr.answerintentid\n" + 
			"  and \n" + 
			"  (eint.answerby is null or eint.answerby = '')\n" + 
			"  ) as notanswer\n" + 
			" from tr_eng_interaction intr\n" + 
			" inner join ms_eng_intent intent\n" + 
			" on intr.answerintentid=intent.intentid\n" + 
			" where (intr.createdtime BETWEEN #{datefrom} AND #{dateto})\n" + 
			" and (intr.answerintentid >0)\n" + 
			" group by answerintentid,intentname\n" + 
			" ; ") */
	/*@Select(" select \n" + 
			" dir.\"name\" as categoryname \n" + 
			" ,answerintentid\n" + 
			" ,intent.question as intentname\n" + 
			" ,(select count(interactionid) from tr_eng_interaction eint \n" + 
			" where eint.answerintentid=intr.answerintentid\n" + 
			" and \n" + 
			" eint.answerby like '%bot%'\n" + 
			" and eint.createdtime BETWEEN #{datefrom} AND #{dateto} \n" + 
			" ) as answerbybot\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			" where eint.answerintentid=intr.answerintentid\n" + 
			" and \n" + 
			" (eint.answerby is not null and eint.answerby not ilike '%bot%' and eint.answerby <> '')\n" + 
			" and eint.createdtime BETWEEN #{datefrom} AND #{dateto} \n" + 
			" ) as answerbyagent\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			" where eint.answerintentid=intr.answerintentid\n" + 
			" and \n" + 
			" (eint.answerby is null or eint.answerby = '')\n" + 
			" ) as notanswer\n" + 
			" from tr_eng_interaction intr\n" + 
			" inner join ms_eng_intent intent\n" + 
			" on intr.answerintentid=intent.intentid\n" + 
			" inner join ms_eng_directory dir\n" + 
			" on intent.directoryid=dir.directoryid\n" + 
			" where (intr.createdtime BETWEEN #{datefrom} AND #{dateto})\n" + 
			" and (intr.answerintentid >0)\n" + 
			" group by dir.\"name\",answerintentid,intentname ; ") */
	/*@Select(" select \n" + 
			" dir.\"name\" as categoryname \n" + 
			" ,answerintentid\n" + 
			" ,intent.question as intentname\n" + 
			" ,(select count(interactionid) from tr_eng_interaction eint \n" + 
			" where eint.answerintentid=intr.answerintentid\n" + 
			" and \n" + 
			" eint.answerby like '%bot%'\n" + 
			" and eint.createdtime BETWEEN #{datefrom} AND #{dateto} \n" + 
			" ) as answerbybot\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			" where eint.answerintentid=intr.answerintentid\n" + 
			" and \n" + 
			" (eint.answerby is not null and eint.answerby not ilike '%bot%' and eint.answerby <> '')\n" + 
			" and eint.createdtime BETWEEN #{datefrom} AND #{dateto} \n" + 
			" ) as answerbyagent\n" + 
			" ,(select count(*) from tr_eng_interaction eint \n" + 
			" where eint.answerintentid=intr.answerintentid\n" + 
			" and \n" + 
			" (eint.answerby is null or eint.answerby = '')\n" + 
			" ) as notanswer\n"
			+ " ,intr.channel " + 
			" from tr_eng_interaction intr\n" + 
			" inner join ms_eng_intent intent\n" + 
			" on intr.answerintentid=intent.intentid\n" + 
			" inner join ms_eng_directory dir\n" + 
			" on intent.directoryid=dir.directoryid\n" + 
			" where (intr.createdtime BETWEEN #{datefrom} AND #{dateto})\n" + 
			" and (intr.answerintentid >0)\n" + 
			" group by intr.channel,dir.\"name\",answerintentid,intentname ; ")
	public List<Report> findRptintentbycategory(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	*/
	
	@Select(" select * from \"fn_rpt_intentbycategory\"(#{datefrom},#{dateto});\n ")
	public List<Report> findRptintentbycategory(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select(" select * from \"fn_rpt_intentbycategorywithchannel\"(#{datefrom},#{dateto},#{channelparam});\n ")
	public List<Report> findRptintentbycategorywithchannel(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("channelparam") String channelparam);
	
	@Select("select * from \"fn_rpt_chatcount\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportChatCount> findRptchatcount(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatcountwithchannel\"(#{datefrom},#{dateto},#{channelparam});\n" + 
			"")
	public List<ReportChatCount> findRptchatcountwithchannel(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("channelparam") String channelparam);
	
	@Select("select * from \"fn_rpt_audittrail\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportTrail> findRpttrail(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_incomingchatwithdate\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportPeakHour> findRptincomingchatwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	@Select("select * from \"fn_dsb_clthreshold\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<Report> findDbclttresholdwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	@Select("select * from \"fn_rpt_incomingchatperformancewithdate\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportPeakHour> findRptincomingchatperformancewithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	/*@Select(" select usersa.*,u.username as usercreated from ms_app_usersa usersa\n" + 
			" inner join ms_app_user u\n" + 
			" on u.userid=usersa.createdby\n" + 
			" where usersa.createdtime BETWEEN #{datefrom} AND #{dateto} ")
	public List<ReportSA> findRptusersa(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);*/
	
	@Select(" select * from \"fn_rpt_usersa\"(#{datefrom},#{dateto}); ")
	public List<ReportSA> findRptusersa(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatcountbranch\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportChatCount> findRptchatcountbranch(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatcountbranchwithdate\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportChatCount> findRptchatcountbranchwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatcountbranchwithdatewithchannel\"(#{datefrom},#{dateto},#{channelparam});\n" + 
			"")
	public List<ReportChatCount> findRptchatcountbranchwithdatewithchannel(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("channelparam") String channelparam);
	
	@Select("select * from \"fn_rpt_chatinteractionhistory\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportInteraction> findRptChatinteractionhistory(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatinteractionhistorywithchannel\"(#{datefrom},#{dateto},#{channelparam});\n" + 
			"")
	public List<ReportInteraction> findRptChatinteractionhistorywithchannel(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("channelparam") String channelparam);
	
	@Select("select * from \"fn_rpt_chatbotrate\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportBotRate> findRptChatbotrate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatbotratewithchannel\"(#{datefrom},#{dateto},#{channelparam});\n" + 
			"")
	public List<ReportBotRate> findRptChatbotratewithchannel(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("channelparam") String channelparam);
	
	/*@Select(" select distinct code from channel.channel; ")
    public List<ReportChannel> findChannel();*/
	@Select(" select distinct type as code from channel.channel; ")
    public List<ReportChannel> findChannel();
	
	@Select("select * from \"fn_rpt_committrainlog\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportTrainLog> findRptTrainlog(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_committrainlogdata\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportTrainLog> findRptTrainlogdata(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_reportconfiglog\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportConfigLog> findRptconfiglog(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_committrainlogcustom\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportTrainLog> findRptTrainlogcustom(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_committrainactivity\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportTrainLog> findRptTrainactivity(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_agentproduction\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportAgentProduction> findRptAgentProduction(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_rpt_chatbotratedetail\"(#{datefrom},#{dateto});\n" + 
			"")
	public List<ReportBotRate> findRptChatbotratedetail(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
}
