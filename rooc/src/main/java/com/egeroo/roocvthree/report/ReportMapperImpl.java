package com.egeroo.roocvthree.report;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;





public class ReportMapperImpl  extends BaseDAO implements ReportMapper {
	
	private static final Logger log = Logger.getLogger(ReportMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public ReportMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Report> findRptuserlogin(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Report> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptuserlogin(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get clt data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Report> findRptupdateintent(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Report> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptupdateintent(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get clt data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Report> findRptintentbycategory(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Report> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptintentbycategory(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get clt data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportChatCount> findRptchatcount(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportChatCount> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptchatcount(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get clt data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportTrail> findRpttrail(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportTrail> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRpttrail(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get clt data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportPeakHour> findRptincomingchatwithdate(Date datefrom, Date dateto) {
		System.out.println("db findRptincomingchatwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportPeakHour> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptincomingchatwithdate(datefrom,dateto);
			log.info("getfindRptincomingchatwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptincomingchatwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Report> findDbclttresholdwithdate(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Report> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findDbclttresholdwithdate(datefrom,dateto);
			log.info("getclt data");
		}catch(PersistenceException e){
			log.debug(e + "error get clt data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportPeakHour> findRptincomingchatperformancewithdate(Date datefrom, Date dateto) {
		System.out.println("db findRptincomingchatperformancewithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportPeakHour> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptincomingchatperformancewithdate(datefrom,dateto);
			log.info("get findRptincomingchatperformancewithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptincomingchatperformancewithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportSA> findRptusersa(Date datefrom, Date dateto) {
		System.out.println("db findRptusersa List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportSA> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptusersa(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptusersa data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportChatCount> findRptchatcountbranch(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportChatCount> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptchatcountbranch(datefrom,dateto);
			log.info("findRptchatcountbranch data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptchatcountbranch data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportChatCount> findRptchatcountbranchwithdate(Date datefrom, Date dateto) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportChatCount> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptchatcountbranchwithdate(datefrom,dateto);
			log.info("findRptchatcountbranchwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptchatcountbranchwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportInteraction> findRptChatinteractionhistory(Date datefrom, Date dateto) {
		System.out.println("db findRptChatinteractionhistory List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportInteraction> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptChatinteractionhistory(datefrom,dateto);
			log.info("findRptChatinteractionhistory data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptChatinteractionhistory data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportBotRate> findRptChatbotrate(Date datefrom, Date dateto) {
		System.out.println("db findRptChatbotrate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportBotRate> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptChatbotrate(datefrom,dateto);
			log.info("findRptChatbotrate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptChatbotrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportChannel> findChannel() {
		System.out.println("ReportChannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportChannel> mm = null;
		try{
			ReportMapper mmMapper = sqlSession.getMapper(ReportMapper.class);
			mm = mmMapper.findChannel();
			log.info("getReportChannel data");
		}catch(PersistenceException e){
			log.debug(e + "error get ReportChannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return mm;
	}

	@Override
	public List<ReportBotRate> findRptChatbotratewithchannel(Date datefrom, Date dateto, String channelparam) {
		System.out.println("db findRptChatbotratewithchannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportBotRate> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptChatbotratewithchannel(datefrom,dateto,channelparam);
			log.info("findRptChatbotratewithchannel data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptChatbotratewithchannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportChatCount> findRptchatcountwithchannel(Date datefrom, Date dateto, String channelparam) {
		System.out.println("db findRptchatcountwithchannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportChatCount> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptchatcountwithchannel(datefrom,dateto,channelparam);
			log.info("findRptchatcountwithchannel data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptchatcountwithchannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportChatCount> findRptchatcountbranchwithdatewithchannel(Date datefrom, Date dateto,
			String channelparam) {
		System.out.println("db findRptchatcountbranchwithdatewithchannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportChatCount> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptchatcountbranchwithdatewithchannel(datefrom,dateto,channelparam);
			log.info("findRptchatcountbranchwithdatewithchannel data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptchatcountbranchwithdatewithchannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Report> findRptintentbycategorywithchannel(Date datefrom, Date dateto, String channelparam) {
		System.out.println("db findRptintentbycategorywithchannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Report> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptintentbycategorywithchannel(datefrom,dateto,channelparam);
			log.info("findRptintentbycategorywithchannel data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptintentbycategorywithchannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportInteraction> findRptChatinteractionhistorywithchannel(Date datefrom, Date dateto,
			String channelparam) {
		System.out.println("db findRptChatinteractionhistorywithchannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportInteraction> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptChatinteractionhistorywithchannel(datefrom,dateto,channelparam);
			log.info("findRptChatinteractionhistorywithchannel data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptChatinteractionhistorywithchannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportTrainLog> findRptTrainlog(Date datefrom, Date dateto) {
		System.out.println("db ReportTrainLog List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportTrainLog> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptTrainlog(datefrom,dateto);
			log.info("ReportTrainLog data");
		}catch(PersistenceException e){
			log.debug(e + "error get ReportTrainLog data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportTrainLog> findRptTrainlogdata(Date datefrom, Date dateto) {
		System.out.println("db findRptTrainlogdata List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportTrainLog> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptTrainlogdata(datefrom,dateto);
			log.info("findRptTrainlogdata data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptTrainlogdata data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportConfigLog> findRptconfiglog(Date datefrom, Date dateto) {
		System.out.println("db ReportConfigLog List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportConfigLog> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptconfiglog(datefrom,dateto);
			log.info("findRptconfiglog data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptconfiglog data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportTrainLog> findRptTrainlogcustom(Date datefrom, Date dateto) {
		System.out.println("db findRptTrainlogcustom List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportTrainLog> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptTrainlogcustom(datefrom,dateto);
			log.info("findRptTrainlogcustom data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptTrainlogcustom data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportTrainLog> findRptTrainactivity(Date datefrom, Date dateto) {
		System.out.println("db findRptTrainactivity List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportTrainLog> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptTrainactivity(datefrom,dateto);
			log.info("findRptTrainactivity data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptTrainactivity data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportAgentProduction> findRptAgentProduction(Date datefrom, Date dateto) {
		System.out.println("db ReportAgentProduction List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportAgentProduction> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptAgentProduction(datefrom,dateto);
			log.info("ReportAgentProduction data");
		}catch(PersistenceException e){
			log.debug(e + "error get ReportAgentProduction data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<ReportBotRate> findRptChatbotratedetail(Date datefrom, Date dateto) {
		System.out.println("db findRptChatbotratedetail List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<ReportBotRate> db = null;
		try{
			ReportMapper dbMapper = sqlSession.getMapper(ReportMapper.class);
			db = dbMapper.findRptChatbotratedetail(datefrom,dateto);
			log.info("findRptChatbotratedetail data");
		}catch(PersistenceException e){
			log.debug(e + "error get findRptChatbotratedetail data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

}
