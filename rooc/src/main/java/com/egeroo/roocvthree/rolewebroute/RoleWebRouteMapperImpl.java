package com.egeroo.roocvthree.rolewebroute;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.IntentService;




public class RoleWebRouteMapperImpl  extends BaseDAO implements RoleWebRouteMapper {
	
	private static final Logger log = Logger.getLogger(RoleWebRouteMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    IntentService intservice = new IntentService();
    ValidationJson validatejson = new ValidationJson();
    
    
    public RoleWebRouteMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<RoleWebRoute> findAll() {
		System.out.println("web route List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<RoleWebRoute> ec = null;
		try{
			RoleWebRouteMapper ecMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			ec = ecMapper.findAll();
			log.info("getweb route data");
		}catch(PersistenceException e){
			log.debug(e + "error get web route data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleWebRoute findOne(Integer rolewebrouteid) {
		System.out.println("RoleWebRoute List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleWebRoute ec = null;
		try{
			RoleWebRouteMapper ecMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			ec = ecMapper.findOne(rolewebrouteid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get RoleWebRoute data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (RoleWebRoute) ec;
	}

	@Override
	public RoleWebRoute Save(RoleWebRoute rolewebroute) {
		System.out.println("RoleWebRoute save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleWebRoute ec = null;
		try{
			RoleWebRouteMapper RoleWebRouteMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			//userrole = 
			ec = RoleWebRouteMapper.Save(rolewebroute);
			log.info("insertRoleWebRoute data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleWebRoute Update(RoleWebRoute RoleWebRoute) {
		System.out.println("RoleWebRoute save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleWebRoute ec = null;
		try{
			RoleWebRouteMapper RoleWebRouteMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			//userrole = 
			ec = RoleWebRouteMapper.Update(RoleWebRoute);
			log.info("updateRoleWebRoute data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleWebRoute deleteOne(Integer rolewebrouteid) {
		System.out.println("rolewebroute delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleWebRoute ec = null;
		try{
			RoleWebRouteMapper ecMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			ec = ecMapper.deleteOne(rolewebrouteid);
			log.info("delete rolewebroute data");
		}catch(PersistenceException e){
			log.debug(e + "error delete rolewebroute data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<RoleWebRoute> deleteByroleid(Integer roleid) {
		System.out.println("delete webroute List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<RoleWebRoute> ec = null;
		try{
			RoleWebRouteMapper ecMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			ec = ecMapper.deleteByroleid(roleid);
			log.info("getwebrouterole data");
		}catch(PersistenceException e){
			log.debug(e + "error del webroute list data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleWebRoute findByroleandroute(String route, Integer roleid) {
		System.out.println("webroute List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleWebRoute mr = null;
		try{
			RoleWebRouteMapper userMapper = sqlSession.getMapper(RoleWebRouteMapper.class);
			mr = userMapper.findByroleandroute(route,roleid);
			log.info("getwebroute data");
		}catch(PersistenceException e){
			log.debug(e + "error get webroute data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (RoleWebRoute) mr;
	}

}
