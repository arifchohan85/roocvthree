package com.egeroo.roocvthree.dashboard;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;





public interface DashboardMapper {
	
	@Select("select * from \"fn_dsb_user\"(#{datefrom},#{dateto});\n" + 
			"")
    public DashboardUser findDbuser(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_intent\"(#{setperiod});\n" + 
			"")
    public Dashboard findDbintent(Integer setperiod);
	
	@Select("select * from \"fn_dsb_intent\"(#{datefrom},#{dateto});\n" + 
			"")
    public DashboardTotalIntent findDbintentwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_user\"(#{setperiod});\n" + 
			"")
    public Dashboard findDbuserint(Integer setperiod);
	
	@Select("select * from \"fn_clthreshold\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbclttreshold(Integer setperiod);
	
	@Select("select * from \"fn_topquestion\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbtopquestion(Integer setperiod);
	
	@Select("select * from \"fn_dsb_topquestion\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardTopQuestion> findDbfntopquestionwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_messageuser\"(#{setperiod});\n" + 
			"")
    public Dashboard findDbmessageuser(Integer setperiod);
	
	@Select("select * from \"fn_dsb_messageuser\"(#{datefrom},#{dateto});\n" + 
			"")
    public DashboardMessageUser findDbfnmessageuserwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_channel\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbfnchannel(Integer setperiod);
	
	@Select("select * from \"fn_dsb_channel\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardChannel> findDbfnchannelwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_intenttrained\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbintenttrained(Integer setperiod);
	
	@Select("select * from \"fn_dsb_intenttrained\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardIntentTrained> findDbintenttrainedwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_dsb_trained\"(#{datefrom},#{dateto});\n" + 
			"")
    public DashboardTrain findDbtrainedwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_trained\"(#{setperiod});\n" + 
			"")
    public Dashboard findDbtrained(Integer setperiod);
	
	@Select("select * from \"fn_dsb_incomingmessage\"(#{datefrom},#{dateto});\n" + 
			"")
    public DashboardIncomingMessage findDbincomingmessagewithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_incomingmessage\"(#{setperiod});\n" + 
			"")
    public Dashboard findDbincomingmessage(Integer setperiod);
	
	@Select("select * from \"fn_rpt_incomingcustomerbychannel\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbfnincomingcustomerbychannel(Integer setperiod);
	
	@Select("select * from \"fn_rpt_incomingchat\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbfnincomingchat(Integer setperiod);
	
	@Select("select * from \"fn_agentperformance\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbagentperformance(Integer setperiod);
	
	@Select("select * from \"fn_kpi\"(#{setperiod});\n" + 
			"")
    public List<Dashboard> findDbkpi(Integer setperiod);
	
	@Select("select * from \"fn_dsb_kpi\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardKPI> findDbkpiwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select distinct COALESCE(kanwil_name,'-') as kanwil from ms_app_userprofile;" + 
			" ")
    public List<Dashboardbca> findDblistkanwil();
	
	@Select("select * from \"fn_dsb_chatcountkanwilwithdate\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<Dashboardbca> findDbchatcountkanwilwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_dsb_chatcountkanwilwithdateandfilter\"(#{datefrom},#{dateto},#{kanwil});\n" + 
			"")
    public List<Dashboardbca> findDbchatcountkanwilwithdateandfilter(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("kanwil") String kanwil);
	
	@Select("select distinct COALESCE(branch_name,'-') as branch from ms_app_userprofile;" + 
			" ")
    public List<Dashboardbca> findDblistbranch();
	
	@Select("select * from \"fn_dsb_chatcountbranchwithdate\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<Dashboardbca> findDbchatcountbranchwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_dsb_chatcountbranchwithdateandfilter\"(#{datefrom},#{dateto},#{branch});\n" + 
			"")
    public List<Dashboardbca> findDbchatcountbranchwithdateandfilter(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto,@Param("branch") String branch);

	@Select("select * from \"fn_dsb_agentperformance\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardAgentPerfomance> findDbagentperformancewithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_dsb_chatcountjobtitlewithdate\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<Dashboardbca> findDbchatcountjobtitlewithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_dsb_incomingchatperformancewithdate\"(#{datefrom},#{dateto});\n" + 
			"")
    public Dashboardhourly findDbincomingchathourly(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	/*@Select("select * from \"fn_dsb_chatbotrate\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardRating> findDbchatbotrate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	*/
	@Select("select * from \"fn_dsb_chatbotrate\"(#{datefrom},#{dateto});\n" + 
			"")
    public DashboardRating findDbchatbotrate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select * from \"fn_dsb_clthreshold\"(#{datefrom},#{dateto});\n" + 
			"")
    public List<DashboardCltTreshold> findDbclttresholdwithdate(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	
	@Select("select fn_dsb_user(#{datefrom},#{dateto}) dataMessage")
	public int getDataUser(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@Select("select fn_dsb_messageuser(#{datefrom},#{dateto}) dataMessage")
	public int getMessage(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@SuppressWarnings("rawtypes")
	@Select("select coalesce(totalintentnew,0) \"dataTotalNewIntent\",coalesce(totalintent,0) \"dataTotalIntent\" from fn_dsb_intent(#{datefrom},#{dateto}) tgl\r\n")
	public Map getIntent(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	@SuppressWarnings("rawtypes")
	@Select("SELECT unnest('{Total Answer,Answer By Bot,Answer By Agent,Not Answer}'::text[]) AS labels,\r\n" + 
			"unnest(ARRAY[totalAnswer::int,totalAnswerByBot::int,totalAnswerByAgent::int,totalNotAnswer::int]) AS series \r\n" + 
			"from (\r\n" + 
			"select * from fn_dsb_incomingmessage(#{datefrom},#{dateto}) tbl\r\n" + 
			") tbl")
	public List<Map> getIncoming(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
	
	@SuppressWarnings("rawtypes")
	@Select("select * from fn_dsb_channel(#{datefrom},#{dateto}) tgl union all select 'rooc' channelname,0 totalchat where not exists (select * from fn_dsb_channel(#{datefrom},#{dateto}))")
	public List<Map> getListChannel(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);

	@SuppressWarnings("rawtypes")
	@Select("select * from fn_dsb_kpi(#{datefrom},#{dateto}) tgl")
	public List<Map> getListKPI(@Param("datefrom") Date datefrom,@Param("dateto") Date dateto);
}
