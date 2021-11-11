package com.egeroo.roocvthree.dashboard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.core.util.ResultData;
import com.egeroo.roocvthree.monitor.MonitorService;


@Service
public class DashboardService {
	
	public DashboardUser findDbuser(String tenant,Date datefrom,Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbuser(datefrom,dateto);  
    }
	
	public Dashboard findDbuserint(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbuserint(setperiod);  
    }
	
	public Dashboard findDbmessageuser(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbmessageuser(setperiod);  
    }
	
	public List<Dashboard> findDbclttreshold(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbclttreshold(setperiod);  
    }
	
	public List<Dashboard> findDbtopquestion(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbtopquestion(setperiod);  
    }
	
	public Dashboard findDbintent(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbintent(setperiod);  
    }
	
	public List<Dashboard> findDbfnchannel(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbfnchannel(setperiod);  
    }
	
	public List<Dashboard> findDbintenttrained(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbintenttrained(setperiod);  
    }
	
	public Dashboard findDbtrained(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbtrained(setperiod);  
    }
	
	public Dashboard findDbincomingmessage(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbincomingmessage(setperiod);  
    }
	
	public List<Dashboard> findDbfnincomingcustomerbychannel(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbfnincomingcustomerbychannel(setperiod);  
    }
	
	public List<Dashboard> findDbfnincomingchat(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbfnincomingchat(setperiod);  
    }
	
	public List<Dashboard> findDbagentperformance(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbagentperformance(setperiod);  
    }
	
	public List<Dashboard> findDbkpi(String tenant,Integer setperiod) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbkpi(setperiod);  
    }
	
	public List<Dashboardbca> findDblistkanwil(String tenant) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDblistkanwil();  
    }
	
	public List<Dashboardbca> findDbchatcountkanwilwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatcountkanwilwithdate(datefrom,dateto);  
    }
	
	public List<Dashboardbca> findDbchatcountkanwilwithdateandfilter(String tenant,Date datefrom, Date dateto,String kanwil) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatcountkanwilwithdateandfilter(datefrom,dateto,kanwil);  
    }
	
	public List<Dashboardbca> findDblistbranch(String tenant) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDblistbranch();  
    }
	
	public List<Dashboardbca> findDbchatcountbranchwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatcountbranchwithdate(datefrom,dateto);  
    }
	
	public List<Dashboardbca> findDbchatcountbranchwithdateandfilter(String tenant,Date datefrom, Date dateto,String kanwil) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatcountbranchwithdateandfilter(datefrom,dateto,kanwil);  
    }
	
	public List<DashboardAgentPerfomance> findDbagentperformancewithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbagentperformancewithdate(datefrom,dateto);  
    }
	
	public List<Dashboardbca> findDbchatcountjobtitlewithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatcountjobtitlewithdate(datefrom,dateto);  
    }
	
	public Dashboardhourly findDbincomingchathourly(String tenant,Date datefrom,Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbincomingchathourly(datefrom,dateto);  
    }
	
	public List<DashboardChannel> findDbfnchannelwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbfnchannelwithdate(datefrom,dateto);  
    }
	
	public DashboardTotalIntent findDbintentwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbintentwithdate(datefrom,dateto);  
    }
	
	public List<DashboardTopQuestion> findDbfntopquestionwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbfntopquestionwithdate(datefrom,dateto);  
    }
	
	public DashboardMessageUser findDbfnmessageuserwithdate(String tenant,Date datefrom,Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbfnmessageuserwithdate(datefrom,dateto);  
    }
	
	public List<DashboardIntentTrained> findDbintenttrainedwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbintenttrainedwithdate(datefrom,dateto);  
    }
	
	public DashboardIncomingMessage findDbincomingmessagewithdate(String tenant,Date datefrom,Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbincomingmessagewithdate(datefrom,dateto);  
    }
	
	public DashboardTrain findDbtrainedwithdate(String tenant,Date datefrom,Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbtrainedwithdate(datefrom,dateto);  
    }
	
	public List<DashboardKPI> findDbkpiwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbkpiwithdate(datefrom,dateto);  
    }
	
	/*public List<DashboardRating> findDbchatbotrate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatbotrate(datefrom,dateto);  
    }*/
	public DashboardRating findDbchatbotrate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbchatbotrate(datefrom,dateto);  
	}
	
	public List<DashboardCltTreshold> findDbclttresholdwithdate(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.findDbclttresholdwithdate(datefrom,dateto);  
    }

	
	/**
	 * 
	 * Start Dashboard v3
	 */
	public Map getIntent(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);		
		return appMapper.getIntent(datefrom,dateto);  
    }
	public List<Map> getListChannel(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.getListChannel(datefrom,dateto);  
    }
	public List<Map> getIncoming(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.getIncoming(datefrom,dateto);  
    }
	public List<Map> getListKPI(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.getListKPI(datefrom,dateto);  
    }
	public int getDataUser(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.getDataUser(datefrom,dateto);  
    }
	public int getMessage(String tenant,Date datefrom, Date dateto) {
		DashboardMapper appMapper = new DashboardMapperImpl(tenant);
		return appMapper.getMessage(datefrom,dateto);  
    }

	
	public ResultData getResult(List<Map> dataList) {
		ResultData results = new ResultData();
		String[] label = new String[dataList.size()];
		Double[] series = new Double[dataList.size()];
		for(int i=0;i<dataList.size();i++) {
			Integer totalChat = (Integer) dataList.get(i).get("series");
			label[i] = (String) dataList.get(i).get("labels");
			series[i] = totalChat.doubleValue();
		}
		results.setLabel(label);
		results.setSeries(series);		
		return results;
	}

	public ResultData getResultChannel(List<Map> dataList) {
		ResultData results = new ResultData();
		String[] label = new String[dataList.size()];
		Double[] series = new Double[dataList.size()];
		for(int i=0;i<dataList.size();i++) {
			Integer totalChat = (Integer) dataList.get(i).get("totalchat");
			label[i] = (String) dataList.get(i).get("channelname");
			series[i] = totalChat.doubleValue();
		}
		results.setLabel(label);
		results.setSeries(series);		
		return results;
	}
	
	public ResultData getResultKPI(List<Map> dataList) {
		ResultData results = new ResultData();
		String[] label = new String[dataList.size()];
		Double[] series = new Double[dataList.size()];
		for(int i=0;i<dataList.size();i++) {
			BigDecimal percentage = (BigDecimal) dataList.get(i).get("percentage");
			label[i] = (String) dataList.get(i).get("kpi");
			series[i] = percentage.doubleValue();
		}
		results.setLabel(label);
		results.setSeries(series);		
		return results;
	}
	public Dashboardv3 getAllDashboard(String tenant,Date datefrom, Date dateto, MonitorService mservice){
		Dashboardv3 dataAll = new Dashboardv3();
		try {			
			Map intentMap = getIntent(tenant, datefrom, dateto);
			dataAll.setDataUser(getDataUser(tenant, datefrom, dateto));
			dataAll.setDataMessage(getMessage(tenant, datefrom, dateto));
			dataAll.setDataChannels(getResultChannel(getListChannel(tenant, datefrom, dateto)));
			dataAll.setDataTotalNewIntent((int)intentMap.get("dataTotalNewIntent"));
			dataAll.setDataTotalIntent((int)intentMap.get("dataTotalIntent"));
			dataAll.setDataIncomingMessage(getResult(getIncoming(tenant, datefrom, dateto)));
			dataAll.setDataKpi(getResultKPI(getListKPI(tenant, datefrom, dateto)));
			dataAll.setDataHandle(mservice.getHandle(tenant).getCount());
			dataAll.setDataQueue(mservice.getQueue(tenant).getCount());
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("dataAll : " + dataAll);
		return dataAll;
	}

	/**
	 * 
	 * End Dashboard v3
	 */
}
