package com.egeroo.roocvthree.dashboard;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;



public class DashboardMapperImpl extends BaseDAO implements DashboardMapper {
	
	private static final Logger log = Logger.getLogger(DashboardMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public DashboardMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public DashboardUser findDbuser(Date datefrom, Date dateto) {
		System.out.println("db user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		DashboardUser db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbuser(datefrom,dateto);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (DashboardUser) db;
	}

	@Override
	public Dashboard findDbintent(Integer setperiod) {
		System.out.println("db intent List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Dashboard db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbintent(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Dashboard) db;
	}

	@Override
	public Dashboard findDbuserint(Integer setperiod) {
		System.out.println("db user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Dashboard db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbuserint(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Dashboard) db;
	}

	@Override
	public List<Dashboard> findDbclttreshold(Integer setperiod) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbclttreshold(setperiod);
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
	public List<Dashboard> findDbtopquestion(Integer setperiod) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbtopquestion(setperiod);
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
	public Dashboard findDbmessageuser(Integer setperiod) {
		System.out.println("db user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Dashboard db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbmessageuser(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Dashboard) db;
	}

	@Override
	public List<Dashboard> findDbfnchannel(Integer setperiod) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbfnchannel(setperiod);
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
	public List<Dashboard> findDbintenttrained(Integer setperiod) {
		System.out.println("db clt List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbintenttrained(setperiod);
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
	public Dashboard findDbtrained(Integer setperiod) {
		System.out.println("db user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Dashboard db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbtrained(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Dashboard) db;
	}

	@Override
	public Dashboard findDbincomingmessage(Integer setperiod) {
		System.out.println("db user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Dashboard db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbincomingmessage(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Dashboard) db;
	}

	@Override
	public List<Dashboard> findDbfnincomingcustomerbychannel(Integer setperiod) {
		System.out.println("db findDbfnincomingcustomerbychannel List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbfnincomingcustomerbychannel(setperiod);
			log.info("getUser findDbfnincomingcustomerbychannel");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbfnincomingcustomerbychannel data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboard> findDbfnincomingchat(Integer setperiod) {
		System.out.println("db findDbfnincomingchat List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbfnincomingchat(setperiod);
			log.info("getUser findDbfnincomingchat");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbfnincomingchat data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboard> findDbagentperformance(Integer setperiod) {
		System.out.println("db agt perf List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbagentperformance(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get agt perf data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboard> findDbkpi(Integer setperiod) {
		System.out.println("db agt perf List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboard> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbkpi(setperiod);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get agt perf data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}
	
	@Override
	public List<Dashboardbca> findDblistkanwil() {
		System.out.println("db findDblistkanwil List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDblistkanwil();
			log.info("findDblistkanwil data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDblistkanwil data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboardbca> findDbchatcountkanwilwithdate(Date datefrom, Date dateto) {
		System.out.println("db chatcountkanwilwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatcountkanwilwithdate(datefrom,dateto);
			log.info("getchatcountkanwilwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get chatcountkanwilwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}


	@Override
	public List<Dashboardbca> findDbchatcountkanwilwithdateandfilter(Date datefrom, Date dateto, String kanwil) {
		System.out.println("db findDbchatcountkanwilwithdateandfilter List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatcountkanwilwithdateandfilter(datefrom,dateto,kanwil);
			log.info("findDbchatcountkanwilwithdateandfilter data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbchatcountkanwilwithdateandfilter data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboardbca> findDblistbranch() {
		System.out.println("db findDblistbranch List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDblistbranch();
			log.info("findDblistbranch data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDblistbranch data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboardbca> findDbchatcountbranchwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbchatcountbranchwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatcountbranchwithdate(datefrom,dateto);
			log.info("findDbchatcountbranchwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbchatcountbranchwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboardbca> findDbchatcountbranchwithdateandfilter(Date datefrom, Date dateto, String branch) {
		System.out.println("db findDbchatcountbranchwithdateandfilter List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatcountbranchwithdateandfilter(datefrom,dateto,branch);
			log.info("findDbchatcountbranchwithdateandfilter data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbchatcountbranchwithdateandfilter data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<DashboardAgentPerfomance> findDbagentperformancewithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbagentperformancewithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardAgentPerfomance> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbagentperformancewithdate(datefrom,dateto);
			log.info("findDbagentperformancewithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbagentperformancewithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<Dashboardbca> findDbchatcountjobtitlewithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbchatcountjobtitlewithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Dashboardbca> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatcountjobtitlewithdate(datefrom,dateto);
			log.info("findDbchatcountjobtitlewithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbchatcountjobtitlewithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public Dashboardhourly findDbincomingchathourly(Date datefrom, Date dateto) {
		System.out.println("db findDbincomingchathourly List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Dashboardhourly db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbincomingchathourly(datefrom,dateto);
			log.info("getfindDbincomingchathourly data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbincomingchathourly data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Dashboardhourly) db;
	}

	@Override
	public List<DashboardChannel> findDbfnchannelwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbfnchannelwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardChannel> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbfnchannelwithdate(datefrom,dateto);
			log.info("findDbfnchannelwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbfnchannelwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public DashboardTotalIntent findDbintentwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbintentwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		DashboardTotalIntent db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbintentwithdate(datefrom,dateto);
			log.info("findDbintentwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbintentwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (DashboardTotalIntent) db;
	}

	@Override
	public List<DashboardTopQuestion> findDbfntopquestionwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbfntopquestionwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardTopQuestion> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbfntopquestionwithdate(datefrom,dateto);
			log.info("findDbfntopquestionwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbfntopquestionwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public DashboardMessageUser findDbfnmessageuserwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbfnmessageuserwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		DashboardMessageUser db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbfnmessageuserwithdate(datefrom,dateto);
			log.info("findDbfnmessageuserwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbfnmessageuserwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (DashboardMessageUser) db;
	}

	@Override
	public List<DashboardIntentTrained> findDbintenttrainedwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbintenttrainedwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardIntentTrained> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbintenttrainedwithdate(datefrom,dateto);
			log.info("findDbintenttrainedwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbintenttrainedwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public DashboardIncomingMessage findDbincomingmessagewithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbincomingmessagewithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		DashboardIncomingMessage db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbincomingmessagewithdate(datefrom,dateto);
			log.info("findDbincomingmessagewithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbincomingmessagewithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (DashboardIncomingMessage) db;
	}

	@Override
	public DashboardTrain findDbtrainedwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbtrainedwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		DashboardTrain db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbtrainedwithdate(datefrom,dateto);
			log.info("findDbtrainedwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbtrainedwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (DashboardTrain) db;
	}

	@Override
	public List<DashboardKPI> findDbkpiwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbkpiwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardKPI> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbkpiwithdate(datefrom,dateto);
			log.info("findDbkpiwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbkpiwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	/*@Override
	public List<DashboardRating> findDbchatbotrate(Date datefrom, Date dateto) {
		System.out.println("db findDbchatbotrate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardRating> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatbotrate(datefrom,dateto);
			log.info("findDbchatbotrate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbchatbotrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}*/
	@Override
	public  DashboardRating findDbchatbotrate(Date datefrom, Date dateto) {
		System.out.println("db findDbchatbotrate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		DashboardRating db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbchatbotrate(datefrom,dateto);
			log.info("findDbchatbotrate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbchatbotrate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	@Override
	public List<DashboardCltTreshold> findDbclttresholdwithdate(Date datefrom, Date dateto) {
		System.out.println("db findDbclttresholdwithdate List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<DashboardCltTreshold> db = null;
		try{
			DashboardMapper dbMapper = sqlSession.getMapper(DashboardMapper.class);
			db = dbMapper.findDbclttresholdwithdate(datefrom,dateto);
			log.info("findDbkpiwithdate data");
		}catch(PersistenceException e){
			log.debug(e + "error get findDbkpiwithdate data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return db;
	}

	

}
