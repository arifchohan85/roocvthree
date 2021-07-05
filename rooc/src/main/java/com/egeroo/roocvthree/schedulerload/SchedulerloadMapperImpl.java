package com.egeroo.roocvthree.schedulerload;

import java.io.IOException;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.core.util.Util;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigMapper;

public class SchedulerloadMapperImpl   extends BaseDAO implements SchedulerloadMapper {
	
	private static final Logger log = Logger.getLogger(SchedulerloadMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	ValidationJson validatejson = new ValidationJson();
	Util util = new Util();
	
	public SchedulerloadMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Schedulerload> findAll() {
		System.out.println("SchedulerMapperImpl List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Schedulerload> ec = null;
		try{
			SchedulerloadMapper ecMapper = sqlSession.getMapper(SchedulerloadMapper.class);
			ec = ecMapper.findAll();
			log.info("get Scheduler data");
		}catch(PersistenceException e){
			log.debug(e + "error get Scheduler data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Schedulerload findOne(Integer schedulerid) {
		System.out.println("Scheduler List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Schedulerload ec = null;
		try{
			SchedulerloadMapper ecMapper = sqlSession.getMapper(SchedulerloadMapper.class);
			ec = ecMapper.findOne(schedulerid);
			log.info("get Botrate data");
		}catch(PersistenceException e){
			log.debug(e + "error get Botrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Schedulerload) ec;
	}

	@Override
	public Schedulerload Save(Schedulerload scheduler) {
		System.out.println("Scheduler save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Schedulerload ec = null;
		try{
			SchedulerloadMapper ecMapper = sqlSession.getMapper(SchedulerloadMapper.class);
			
			RoocConfigMapper rcMapper = sqlSession.getMapper(RoocConfigMapper.class);
			RoocConfig roocconfig = new RoocConfig();
			roocconfig = rcMapper.findByconfigkey("SCHEDULERAPI");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
				System.out.print("Scheduler API is not yet set up");
	        }
			
			JSONObject jsonObject;
			String postret="";
			
			JSONObject postdata = new JSONObject();
			
			
			//Date now = new Date();
	        Instant current = scheduler.getDatetime().toInstant();
	        LocalDateTime ldt = LocalDateTime.ofInstant(current, 
	                                 ZoneId.systemDefault());

	        System.out.println("value of Date: " + scheduler.getDatetime());
	        System.out.println("value of LocalDateTime: " + ldt);
			
			System.out.print("LocalDateTime : " + scheduler.getDatetime());
			
			//System.out.print("ZoneLocalDateTime : " + scheduler.getDatetime());

			postdata.put("name", scheduler.getName());
			postdata.put("code", scheduler.getCode());
			postdata.put("description", scheduler.getDescription());
			postdata.put("url", scheduler.getUrl());
			postdata.put("method", scheduler.getMethod());
			postdata.put("type", scheduler.getType());
			postdata.put("parameter", scheduler.getParameter());
			postdata.put("method", scheduler.getMethod());
			postdata.put("tenantid", scheduler.getTenantid());
			postdata.put("token", scheduler.getToken());
			postdata.put("dataparameter", scheduler.getDataparameterreq());
			postdata.put("dataheader", scheduler.getDataheaderreq());
			//postdata.put("datetime", scheduler.getDatetime());
			postdata.put("datetime", ldt);
			if(scheduler.getEnddatetime() != null)
			{
				Instant currentend = scheduler.getEnddatetime().toInstant();
		        LocalDateTime ldtend = LocalDateTime.ofInstant(currentend, 
		                                 ZoneId.systemDefault());

		        System.out.println("value of End Date: " + scheduler.getEnddatetime());
		        System.out.println("value of LocalDateTime: " + ldtend);
				
				System.out.print("End LocalDateTime : " + scheduler.getEnddatetime());
				
				postdata.put("enddatetime", ldtend);
			}
			else
			{
				postdata.put("enddatetime", org.json.JSONObject.NULL);
			}
			
			postdata.put("timezone", scheduler.getTimezone());
			postdata.put("noenddate", scheduler.isNoenddate());
			postdata.put("isrecurring", scheduler.isIsrecurring());
			postdata.put("occurance", scheduler.getOccurance());
			postdata.put("occurancefrequency", scheduler.getOccurancefrequency());
			
			try {
				postret = hpr.setPostData(roocconfig.getConfigvalue()+"/scheduleApi",postdata);
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("post return is : "+ postret);
			
			//JSONObject jsonObjectretcatID;
		
			if(validatejson.isJSONValidstandard(postret))
			{
				jsonObject = new JSONObject(postret);
				
				if(jsonObject.getBoolean("success"))
				{
					ec = ecMapper.Save(scheduler);
					log.info("insert Scheduler data");
				}
				else
				{
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Couldn't Set Schedule." + jsonObject.getString("message"));
				}
			}
			else
			{
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
			}
			
			
		}catch(PersistenceException e){
			log.debug(e + "error insert Scheduler data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Schedulerload Update(Schedulerload scheduler) {
		System.out.println("Scheduler save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Schedulerload ec = null;
		try{
			SchedulerloadMapper ecMapper = sqlSession.getMapper(SchedulerloadMapper.class);
			//userrole = 
			ec = ecMapper.Update(scheduler);
			log.info("updateScheduler data");
		}catch(PersistenceException e){
			log.debug(e + "error update Scheduler data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Schedulerstatusload Updatestatus(Schedulerstatusload scheduler) {
		System.out.println("Scheduler save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Schedulerstatusload ec = null;
		try{
			SchedulerloadMapper ecMapper = sqlSession.getMapper(SchedulerloadMapper.class);
			//type status : PAUSE,STOP,RESUME,START,UNSCHEDULE,DELETE
			
			RoocConfigMapper rcMapper = sqlSession.getMapper(RoocConfigMapper.class);
			RoocConfig roocconfig = new RoocConfig();
			roocconfig = rcMapper.findByconfigkey("SCHEDULERAPI");
			if (roocconfig == null) {
	            //throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
				System.out.print("Scheduler API is not yet set up");
	        }
			
			String urlStatus = "";
			if(scheduler.getStatus().equals("PAUSE"))
			{
				urlStatus = "/schedulepause";
			}
			else if(scheduler.getStatus().equals("STOP"))
			{
				urlStatus = "/schedulestop";
			}
			else if(scheduler.getStatus().equals("RESUME"))
			{
				urlStatus = "/scheduleresume";
			}
			else if(scheduler.getStatus().equals("START"))
			{
				urlStatus = "/schedulestart";
			}
			else if(scheduler.getStatus().equals("DELETE"))
			{
				urlStatus = "/scheduledelete";
			}
			else if(scheduler.getStatus().equals("UNSCHEDULE"))
			{
				urlStatus = "/unschedule";
			}
			
			
			String hprret = "";
			boolean isSetScheduler = false;
			if(scheduler.getStatus().equals("UNSCHEDULE"))
			{
				try {
					String validUri = util.validateUri(roocconfig.getConfigvalue()+""+urlStatus+"?jobName="+scheduler.getName());
					System.out.print("validUri :" +validUri);
					hprret = hpr.ConnectGetnoparam(validUri);
				
					System.out.print("GET Return is :" + hprret);
					ec = ecMapper.Updatestatus(scheduler);
					
					isSetScheduler = true;
					
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KeyStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				
				
				try {
					URIBuilder ub = new URIBuilder(roocconfig.getConfigvalue()+""+urlStatus);
					ub.addParameter("jobName", scheduler.getName());
					String url = ub.toString();
					
					//hprret = hpr.ConnectGetnoparam(roocconfig.getConfigvalue()+""+urlStatus+"?jobName="+URLEncoder.encode(scheduler.getName(), StandardCharsets.UTF_8));
					hprret = hpr.ConnectGetnoparam(url);
					
					String jsonString = hprret;
			        JSONObject jsonObject;
			        
			        if(validatejson.isJSONValidstandard(jsonString))
					{
						jsonObject = new JSONObject(jsonString);
						
						if(jsonObject.getBoolean("success"))
						{
							isSetScheduler = true;
						}
						else
						{
							isSetScheduler = false;
							throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Couldn't Set Schedule." + jsonObject.getString("message"));
						}
					}
					else
					{
						isSetScheduler = false;
						throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Something when wrong.");
					}
				
				}catch(Exception ex)
				{
					isSetScheduler = false;
					System.out.println(ex);
				}
			}
			
			if(isSetScheduler)
			{
				ec = ecMapper.Updatestatus(scheduler);
				log.info("update Scheduler data");
			}
			log.info("updateScheduler data");
		}catch(PersistenceException e){
			log.debug(e + "error update Scheduler data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
