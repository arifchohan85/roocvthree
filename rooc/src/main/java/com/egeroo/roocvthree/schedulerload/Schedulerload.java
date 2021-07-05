package com.egeroo.roocvthree.schedulerload;

import java.util.Date;
import java.util.Map;

import com.egeroo.roocvthree.core.base.Base;



@SuppressWarnings("serial")
//@Entity
//@Table( name = "tr_app_scheduler" )
public class Schedulerload extends Base{
	
	private int schedulerid;
	
	//@NotEmpty
    private String name;
    
    //@NotEmpty
    private String code;
    
    //@NotEmpty
    private String description;
    
    private String type;

    //@NotEmpty
    private String url;

    //@NotEmpty
    private String parameter;
    
    //@NotEmpty
    private String method;
    
    
    private String tenantid;
    
    
    private String token;

    //@NotNull
    //@Column(name = "begin_date")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;
    
    //@NotNull
    private Date enddatetime;

    //@NotNull
    private String timezone;
    
    private Map<String, Object> template;
    
    private Object data;
    
    private String dataparameter;
    
    private String dataheader;
    
    private boolean noenddate;
    
    private boolean isrecurring;
    
    private String occurance;
    private int occurancefrequency;
    private String status;
    
    private Object dataparameterreq;
    
    private Object dataheaderreq;
    
    
	public int getSchedulerid() {
		return schedulerid;
	}
	public void setSchdulerid(int schedulerid) {
		this.schedulerid = schedulerid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getTenantid() {
		return tenantid;
	}
	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Date getEnddatetime() {
		return enddatetime;
	}
	public void setEnddatetime(Date enddatetime) {
		this.enddatetime = enddatetime;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public Map<String, Object> getTemplate() {
		return template;
	}
	public void setTemplate(Map<String, Object> template) {
		this.template = template;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getDataparameter() {
		return dataparameter;
	}
	public void setDataparameter(String dataparameter) {
		this.dataparameter = dataparameter;
	}
	public String getDataheader() {
		return dataheader;
	}
	public void setDataheader(String dataheader) {
		this.dataheader = dataheader;
	}
	public boolean isNoenddate() {
		return noenddate;
	}
	public void setNoenddate(boolean noenddate) {
		this.noenddate = noenddate;
	}
	public boolean isIsrecurring() {
		return isrecurring;
	}
	public void setIsrecurring(boolean isrecurring) {
		this.isrecurring = isrecurring;
	}
	public String getOccurance() {
		return occurance;
	}
	public void setOccurance(String occurance) {
		this.occurance = occurance;
	}
	public int getOccurancefrequency() {
		return occurancefrequency;
	}
	public void setOccurancefrequency(int occurancefrequency) {
		this.occurancefrequency = occurancefrequency;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getDataparameterreq() {
		return dataparameterreq;
	}
	public void setDataparameterreq(Object dataparameterreq) {
		this.dataparameterreq = dataparameterreq;
	}
	public Object getDataheaderreq() {
		return dataheaderreq;
	}
	public void setDataheaderreq(Object dataheaderreq) {
		this.dataheaderreq = dataheaderreq;
	}
    
    
    


}
