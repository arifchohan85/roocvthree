package com.egeroo.roocvthree.dashboard;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.monitor.*;




@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
    private DashboardService service;

	@Autowired
	private MonitorService mservice;
	
	
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbuser")							
	public DashboardUser getDbuser(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		//Dashboard obj = new Dashboard();
		/*Date nowfrom = new Date(datefrom);
		Date nowto = new Date(dateto);
		obj.setDateto(nowto);
		obj.setDatefrom(nowfrom);
		
		System.out.println("passing date from :"+datefrom);
		System.out.println("passing date to :"+dateto);
		System.out.println("generate date from :"+nowfrom);
		System.out.println("generate date to :"+nowto);*/
		
		DashboardUser result = service.findDbuser(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbuserint")
	public Dashboard getDbuserint(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		Dashboard result = service.findDbuserint(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbmessageuser")
	public Dashboard getDbmessageuser(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		Dashboard result = service.findDbmessageuser(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbclttreshold")
	public List<Dashboard> getDbclttreshold(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbclttreshold(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbtopquestion")
	public List<Dashboard> getDbtopquestion(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbtopquestion(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbchannel")
	public List<Dashboard> getDbchannel(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbfnchannel(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbtrainedintent")
	public List<Dashboard> getDbintenttrained(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbintenttrained(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	
	/*@RequestMapping(method=RequestMethod.GET,value="/listdbintent")
	public Dashboard getDbintent(@RequestHeader HttpHeaders headers,HttpServletRequest request,long datefrom,long dateto) {
		Dashboard obj = new Dashboard();
		Date nowfrom = new Date(datefrom);
		Date nowto = new Date(dateto);
		obj.setDateto(nowto);
		obj.setDatefrom(nowfrom);
		
		System.out.println("passing date from :"+datefrom);
		System.out.println("passing date to :"+dateto);
		System.out.println("generate date from :"+nowfrom);
		System.out.println("generate date to :"+nowto);
		
		Dashboard result = service.findDbintent(headers.get("tenantID").get(0),obj.getDatefrom(),obj.getDateto());
		return result;
	}*/
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbintent")
	public Dashboard getDbintent(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		Dashboard result = service.findDbintent(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	/*@RequestMapping(method=RequestMethod.GET,value="/listdbtrained")
	public Dashboard getDbtrained(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		Dashboard obj = new Dashboard();
		Date nowfrom = new Date();
		Date nowto = new Date();
		obj.setDateto(dateto);
		obj.setDatefrom(datefrom);
		
		System.out.println("passing date from :"+datefrom);
		System.out.println("passing date to :"+dateto);
		System.out.println("generate date from :"+nowfrom);
		System.out.println("generate date to :"+nowto);
		
		Dashboard result = service.findDbtrained(headers.get("tenantID").get(0),obj.getDatefrom(),obj.getDateto());
		return result;
	}
	*/
	
	/*@RequestMapping(method=RequestMethod.GET,value="/listdbincomingmessage")
	public Dashboard getDbincomingmessage(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		Dashboard obj = new Dashboard();
		Date nowfrom = new Date();
		Date nowto = new Date();
		obj.setDateto(dateto);
		obj.setDatefrom(datefrom);
		
		System.out.println("passing date from :"+datefrom);
		System.out.println("passing date to :"+dateto);
		System.out.println("generate date from :"+nowfrom);
		System.out.println("generate date to :"+nowto);
		
		Dashboard result = service.findDbincomingmessage(headers.get("tenantID").get(0),obj.getDatefrom(),obj.getDateto());
		return result;
	}
	*/
	@RequestMapping(method=RequestMethod.GET,value="/listdbtrained")
	public Dashboard getDbtrained(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if(setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period from in request url");
		}
		
		
		//Dashboard obj = new Dashboard();
		Date nowfrom = new Date();
		Date nowto = new Date();
		//obj.setDateto(dateto);
		//obj.setDatefrom(datefrom);
		
		//System.out.println("passing date from :"+datefrom);
		//System.out.println("passing date to :"+dateto);
		System.out.println("generate date from :"+nowfrom);
		System.out.println("generate date to :"+nowto);
		
		Dashboard result = service.findDbtrained(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbincomingmessage")
	public Dashboard getDbincomingmessage(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if(setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period from in request url");
		}
		
		
		//Dashboard obj = new Dashboard();
		Date nowfrom = new Date();
		Date nowto = new Date();
		//obj.setDateto(dateto);
		//obj.setDatefrom(datefrom);
		
		//System.out.println("passing date from :"+datefrom);
		//System.out.println("passing date to :"+dateto);
		System.out.println("generate date from :"+nowfrom);
		System.out.println("generate date to :"+nowto);
		
		Dashboard result = service.findDbincomingmessage(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/incomingcustomerbychannel")
	public List<Dashboard> getincomingcustomerbychannel(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbfnincomingcustomerbychannel(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/incomingchat")
	public List<Dashboard> getincomingchat(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbfnincomingchat(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbagentperformance")
	public List<Dashboard> getDbagentperformance(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbagentperformance(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbkpi")
	public List<Dashboard> getDbkpi(@RequestHeader HttpHeaders headers,HttpServletRequest request,Integer setperiod) {
		if (setperiod == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected period in request url");
		}
		List<Dashboard> result = service.findDbkpi(headers.get("tenantID").get(0),setperiod);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbkanwillist")
	public List<Dashboardbca> getDbkanwillist(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		List<Dashboardbca> result = service.findDblistkanwil(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbkanwilwithdate")
	public List<Dashboardbca> getDbkanwilwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<Dashboardbca> result = service.findDbchatcountkanwilwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbkanwilwithdateandfilter")
	public List<Dashboardbca> getDbkanwilwithdateandfilter(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String kanwil) {
		List<Dashboardbca> result = service.findDbchatcountkanwilwithdateandfilter(headers.get("tenantID").get(0),datefrom,dateto,kanwil);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbbranchlist")
	public List<Dashboardbca> getDbbranchlist(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		List<Dashboardbca> result = service.findDblistbranch(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbbranchwithdate")
	public List<Dashboardbca> getDbbranchwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<Dashboardbca> result = service.findDbchatcountbranchwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbbranchwithdateandfilter")
	public List<Dashboardbca> getDbbranchwithdateandfilter(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String branch) {
		List<Dashboardbca> result = service.findDbchatcountbranchwithdateandfilter(headers.get("tenantID").get(0),datefrom,dateto,branch);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbagentperformancewithdate")
	public List<DashboardAgentPerfomance> getDbagentperformancewithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<DashboardAgentPerfomance> result = service.findDbagentperformancewithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbjobtitlewithdate")
	public List<Dashboardbca> getDbjobtitlewithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<Dashboardbca> result = service.findDbchatcountjobtitlewithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbhourly")
	public Dashboardhourly getDbincomingchathourly(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		Dashboardhourly result = service.findDbincomingchathourly(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbchannelwithdate")
	public List<DashboardChannel> findDbfnchannelwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<DashboardChannel> result = service.findDbfnchannelwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbintentwithdate")
	public DashboardTotalIntent findDbintentwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		DashboardTotalIntent result = service.findDbintentwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbtopquestionwithdate")
	public List<DashboardTopQuestion> findDbfntopquestionwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<DashboardTopQuestion> result = service.findDbfntopquestionwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbmessageuserwithdate")
	public DashboardMessageUser findDbfnmessageuserwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		DashboardMessageUser result = service.findDbfnmessageuserwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbintenttrainedwithdate")
	public List<DashboardIntentTrained> findDbintenttrainedwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<DashboardIntentTrained> result = service.findDbintenttrainedwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbincomingmessagewithdate")
	public DashboardIncomingMessage findDbincomingmessagewithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		DashboardIncomingMessage result = service.findDbincomingmessagewithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbtrainedwithdate")
	public DashboardTrain findDbtrainedwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		DashboardTrain result = service.findDbtrainedwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbkpiwithdate")
	public List<DashboardKPI> findDbkpiwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<DashboardKPI> result = service.findDbkpiwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	/*@RequestMapping(method=RequestMethod.GET,value="/listdbchatbotrate")
	public List<DashboardRating> findDbchatbotrate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		List<DashboardRating> result = service.findDbchatbotrate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}*/
	@RequestMapping(method=RequestMethod.GET,value="/listdbchatbotrate")
	public DashboardRating findDbchatbotrate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		DashboardRating result = service.findDbchatbotrate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdbchattreshold")
	public List<DashboardCltTreshold> getRptchattreshold(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<DashboardCltTreshold> result = service.findDbclttresholdwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listdball")							
	public DashboardData getDball(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		DashboardData response = new DashboardData ();
		
		JSONObject returnresult = new JSONObject();
		returnresult.put("Dashboard","DASHBOARD");
		
		System.out.println("==== DONE PUT ====");
		
		DashboardUser result = service.findDbuser(headers.get("tenantID").get(0),datefrom,dateto);
		/*returnresult.put("dbuser", result);
		response.setDatauser(result);*/
		response.setDataUser(result.getTotaluser());
		
		System.out.println("==== DONE APPEND ====");
		
		DashboardTotalIntent resulti = service.findDbintentwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		//returnresult.put("dbtotalintent", resulti);
		response.setDataTotalintent(resulti.getTotalintent());
		response.setDataTotalintentnew(resulti.getTotalintentnew());
		
		System.out.println("==== DONE APPEND ====");
		
		List<DashboardTopQuestion> resulttq = service.findDbfntopquestionwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbtopquestion", resulttq);
		response.setDatatopquestion(resulttq);
		
		System.out.println("==== DONE APPEND ====");
		
		List<DashboardCltTreshold> resultdct = service.findDbclttresholdwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbclttreshold", resultdct);
		response.setDataclttreshold(resultdct);
		
		System.out.println("==== DONE APPEND ====");
		
		DashboardMessageUser resultms = service.findDbfnmessageuserwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		//returnresult.put("dbmessageuser", resultms);
		response.setDataMessageuser(resultms.getTotalchat());
		
		System.out.println("==== DONE APPEND ====");
		
		List<DashboardChannel> resultchannel = service.findDbfnchannelwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbchannel", resultchannel);
		response.setDatachannel(resultchannel);
		
		System.out.println("==== DONE APPEND ====");
		
		List<DashboardIntentTrained> resultit = service.findDbintenttrainedwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbintenttrained", resultit);
		response.setDataintenttrained(resultit);
		
		System.out.println("==== DONE APPEND ====");
		
		DashboardIncomingMessage resultim = service.findDbincomingmessagewithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbincomingmessage", resultim);
		response.setDataincomingmessage(resultim);
		
		System.out.println("==== DONE APPEND ====");
		
		DashboardTrain resultt = service.findDbtrainedwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		//returnresult.put("dbtrained", resultt);
		response.setDataTrain(resultt.getTotalchat());
		
		System.out.println("==== DONE APPEND ====");
		
		List<DashboardAgentPerfomance> resultap = service.findDbagentperformancewithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbagentperfomance", resultap);
		response.setDataagentperfomance(resultap);
		
		System.out.println("==== DONE APPEND ====");
		
		List<DashboardKPI> resultkpi = service.findDbkpiwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		returnresult.put("dbkpi", resultkpi);
		response.setDatakpi(resultkpi);
		
		System.out.println("==== DONE APPEND ====");

		System.out.println("==== APPEND MONITOR ====");

		Monitor resultmon = mservice.getQueue(headers.get("tenantID").get(0));
		response.setDataQueue(resultmon.getCount());

		System.out.println("==== APPEND HANDLE ====");

		Monitor resulthandle = mservice.getHandle(headers.get("tenantID").get(0));
		response.setDataHandle(resulthandle.getCount());

		System.out.println("==== DONE LAST APPEND ====");

		
		
	    //response.setData(returnresult);
	    System.out.println("==== FINISH SETTING DATA ====");
		
		return response;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Dashboardv3 getDballV3(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		
		return service.getAllDashboard(headers.get("tenantID").get(0), datefrom, dateto, mservice);
		
	}

}
