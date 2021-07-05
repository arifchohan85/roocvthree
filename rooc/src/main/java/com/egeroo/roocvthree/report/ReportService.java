package com.egeroo.roocvthree.report;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;




@Service
public class ReportService {
	
	public List<Report> findRptuserlogin(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptuserlogin(datefrom,dateto);  
    }
	
	public List<Report> findRptupdateintent(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptupdateintent(datefrom,dateto);  
    }
	
	public List<Report> findRptintentbycategory(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptintentbycategory(datefrom,dateto);  
    }
	
	public List<ReportChatCount> findRptchatcount(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptchatcount(datefrom,dateto);  
    }
	
	public List<ReportTrail> findRpttrail(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRpttrail(datefrom,dateto);  
    }
	
	public List<ReportPeakHour> findRptincomingchatwithdate(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptincomingchatwithdate(datefrom,dateto);  
    }
	
	public List<Report> findDbclttresholdwithdate(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findDbclttresholdwithdate(datefrom,dateto);  
    }
	
	public List<ReportPeakHour> findRptincomingchatperformancewithdate(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptincomingchatperformancewithdate(datefrom,dateto);  
    }
	
	public List<ReportSA> findRptusersa(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptusersa(datefrom,dateto);  
    }
	
	public List<ReportChatCount> findRptchatcountbranch(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptchatcountbranch(datefrom,dateto);  
    }
	
	public List<ReportChatCount> findRptchatcountbranchwithdate(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptchatcountbranchwithdate(datefrom,dateto);  
    }
	
	public List<ReportInteraction> findRptChatinteractionhistory(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptChatinteractionhistory(datefrom,dateto);  
    }
	
	public List<ReportBotRate> findRptChatbotrate(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptChatbotrate(datefrom,dateto);  
    }
	
	public List<ReportBotRate> findRptChatbotratewithchannel(String tenant,Date datefrom,Date dateto,String channelparam) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptChatbotratewithchannel(datefrom,dateto,channelparam);  
    }
	
	public List<ReportChatCount> findRptchatcountwithchannel(String tenant,Date datefrom,Date dateto,String channelparam) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptchatcountwithchannel(datefrom,dateto,channelparam);  
    }
	
	public List<ReportChatCount> findRptchatcountbranchwithdatewithchannel(String tenant,Date datefrom,Date dateto,String channelparam) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptchatcountbranchwithdatewithchannel(datefrom,dateto,channelparam);  
    }
	
	public List<Report> findRptintentbycategorywithchannel(String tenant,Date datefrom,Date dateto,String channelparam) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptintentbycategorywithchannel(datefrom,dateto,channelparam);  
    }
	
	public List<ReportInteraction> findRptChatinteractionhistorywithchannel(String tenant,Date datefrom,Date dateto,String channelparam) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptChatinteractionhistorywithchannel(datefrom,dateto,channelparam);  
    }
	
	public List<ReportChannel> getfindChannel(String tenant) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findChannel();
	}
	
	public List<ReportTrainLog> findRptTrainlog(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptTrainlog(datefrom,dateto);  
    }
	
	public List<ReportTrainLog> findRptTrainlogdata(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptTrainlogdata(datefrom,dateto);  
    }
	
	public List<ReportConfigLog> findRptconfiglog(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptconfiglog(datefrom,dateto);  
    }
	
	public List<ReportTrainLog> findRptTrainlogcustom(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptTrainlogcustom(datefrom,dateto);  
    }
	
	public List<ReportTrainLog> findRptTrainactivity(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptTrainactivity(datefrom,dateto);  
    }
	
	public List<ReportAgentProduction> findRptAgentProduction(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptAgentProduction(datefrom,dateto);  
    }
	
	public List<ReportBotRate> findRptChatbotratedetail(String tenant,Date datefrom,Date dateto) {
		ReportMapper appMapper = new ReportMapperImpl(tenant);
		return appMapper.findRptChatbotratedetail(datefrom,dateto);  
    }

}
