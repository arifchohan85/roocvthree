package com.egeroo.roocvthree.report;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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




@RestController
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
    private ReportService service;
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptuserlogin")
	public List<Report> getRptuserlogin(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<Report> result = service.findRptuserlogin(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptupdateintent")
	public List<Report> getRptupdateintent(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<Report> result = service.findRptupdateintent(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptintentbycategory")
	public List<Report> getRptintentbycategory(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String channelparam) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<Report> result =null;
		boolean isEmpty = channelparam == null || channelparam.trim().length() == 0;
		if(isEmpty)
		{
			System.out.print("Empty Param Channel");
			result = service.findRptintentbycategory(headers.get("tenantID").get(0),datefrom,dateto);
		}
		else
		{
			System.out.print("Not Empty Param Channel");
			result = service.findRptintentbycategorywithchannel(headers.get("tenantID").get(0),datefrom,dateto,channelparam);
		}
		
		
		//List<Report> result = service.findRptintentbycategory(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchatcount")
	public List<ReportChatCount> getRptchatcount(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String channelparam) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportChatCount> result = null;
		boolean isEmpty = channelparam == null || channelparam.trim().length() == 0;
		if(isEmpty)
		{
			result = service.findRptchatcount(headers.get("tenantID").get(0),datefrom,dateto);
		}
		else
		{
			result = service.findRptchatcountwithchannel(headers.get("tenantID").get(0),datefrom,dateto,channelparam);
		}
		
		return result;
	}

	@RequestMapping(method=RequestMethod.GET,value="/listrptaudittrail")
	public List<ReportTrail> getRptaudittrail(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportTrail> result = service.findRpttrail(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptpeakhour")
	public List<ReportPeakHour> getRptincomingchatwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportPeakHour> result = service.findRptincomingchatwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchattreshold")
	public List<Report> getRptchattreshold(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<Report> result = service.findDbclttresholdwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptperformancehour")
	public List<ReportPeakHour> getRptperformancehour(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportPeakHour> result = service.findRptincomingchatperformancewithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptusersa")
	public List<ReportSA> getRptusersa(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportSA> result = service.findRptusersa(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchatcountbranch")
	public List<ReportChatCount> getRptchatcountbranch(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportChatCount> result = service.findRptchatcountbranch(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchatcountbranchwithdate")
	public List<ReportChatCount> getRptchatcountbranchwithdate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String channelparam) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportChatCount> result =null;
		boolean isEmpty = channelparam == null || channelparam.trim().length() == 0;
		if(isEmpty)
		{
			System.out.print("Empty Param Channel");
			result = service.findRptchatcountbranchwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		}
		else
		{
			System.out.print("Not Empty Param Channel");
			result = service.findRptchatcountbranchwithdatewithchannel(headers.get("tenantID").get(0),datefrom,dateto,channelparam);
		}
		
		//List<ReportChatCount> result = service.findRptchatcountbranchwithdate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchatinteractionhistory")
	public List<ReportInteraction> getRptinteractionchathistory(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String channelparam) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportInteraction> result =null;
		boolean isEmpty = channelparam == null || channelparam.trim().length() == 0;
		if(isEmpty)
		{
			System.out.print("Empty Param Channel");
			result = service.findRptChatinteractionhistory(headers.get("tenantID").get(0),datefrom,dateto);
		}
		else
		{
			System.out.print("Not Empty Param Channel");
			result = service.findRptChatinteractionhistorywithchannel(headers.get("tenantID").get(0),datefrom,dateto,channelparam);
		}
		
		//List<ReportInteraction> result = service.findRptChatinteractionhistory(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchatbotrate")
	public List<ReportBotRate> getRptchatbotrate(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto,String channelparam) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportBotRate> result =null;
		boolean isEmpty = channelparam == null || channelparam.trim().length() == 0;
		if(isEmpty)
		{
			System.out.print("Empty Param Channel");
			result = service.findRptChatbotrate(headers.get("tenantID").get(0),datefrom,dateto);
		}
		else
		{
			System.out.print("Not Empty Param Channel");
			result = service.findRptChatbotratewithchannel(headers.get("tenantID").get(0),datefrom,dateto,channelparam);
		}
		
		//List<ReportBotRate> result = service.findRptChatbotrate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listchannel")
	public List<ReportChannel> getChannel(@RequestHeader HttpHeaders headers,HttpServletRequest request) {
		List<ReportChannel> result = service.getfindChannel(headers.get("tenantID").get(0));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrpttrainlog")
	public List<ReportTrainLog> getRpttrainlog(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportTrainLog> result =null;
		
		result = service.findRptTrainlog(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrpttrainlogdata")
	public List<ReportTrainLog> getRpttrainlogdata(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportTrainLog> result =null;
		
		result = service.findRptTrainlogdata(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptconfiglog")
	public List<ReportConfigLog> getRptconfiglog(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportConfigLog> result =null;
		
		result = service.findRptconfiglog(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrpttrainlogcustom")
	public List<ReportTrainLog> getRpttrainlogcustom(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportTrainLog> result =null;
		
		result = service.findRptTrainlogcustom(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrpttrainactivity")
	public List<ReportTrainLog> getRpttrainactivity(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportTrainLog> result =null;
		
		result = service.findRptTrainactivity(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptagentproduction")
	public List<ReportAgentProduction> getRptagentproduction(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportAgentProduction> result =null;
		
		result = service.findRptAgentProduction(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listrptchatbotratedetail")
	public List<ReportBotRate> getRptchatbotratedetail(@RequestHeader HttpHeaders headers,HttpServletRequest request,@RequestParam("datefrom") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date datefrom,@RequestParam("dateto") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateto) {
		if(datefrom == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date from in request url");
		}
		else if(dateto == null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "expected date to in request url");
		}
		
		List<ReportBotRate> result =null;
		result = service.findRptChatbotratedetail(headers.get("tenantID").get(0),datefrom,dateto);
		
		
		//List<ReportBotRate> result = service.findRptChatbotrate(headers.get("tenantID").get(0),datefrom,dateto);
		return result;
	}
	
}
