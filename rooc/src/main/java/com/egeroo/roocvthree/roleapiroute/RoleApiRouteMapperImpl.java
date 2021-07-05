package com.egeroo.roocvthree.roleapiroute;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;
import com.egeroo.roocvthree.core.curl.HttpPostReq;
import com.egeroo.roocvthree.core.error.ValidationJson;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.intent.IntentService;


public class RoleApiRouteMapperImpl   extends BaseDAO implements RoleApiRouteMapper {
	
	private static final Logger log = Logger.getLogger(RoleApiRouteMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	HttpPostReq hpr = new HttpPostReq();
	
	
	//@Autowired
    EngineCredentialService ecservice = new EngineCredentialService(); 
    IntentService intservice = new IntentService();
    ValidationJson validatejson = new ValidationJson();
    
    
    public RoleApiRouteMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<RoleApiRoute> findAll() {
		System.out.println("web api List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<RoleApiRoute> ec = null;
		try{
			RoleApiRouteMapper ecMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			ec = ecMapper.findAll();
			log.info("getweb route data");
		}catch(PersistenceException e){
			log.debug(e + "error get web api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleApiRoute findOne(Integer roleapirouteid) {
		System.out.println("RoleApiRoute List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleApiRoute ec = null;
		try{
			RoleApiRouteMapper ecMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			ec = ecMapper.findOne(roleapirouteid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get RoleApiRoute data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (RoleApiRoute) ec;
	}

	@Override
	public RoleApiRoute Save(RoleApiRoute roleapiroute) {
		System.out.println("RoleApiRoute save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleApiRoute ec = null;
		try{
			RoleApiRouteMapper RoleApiRouteMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			//userrole = 
			ec = RoleApiRouteMapper.Save(roleapiroute);
			log.info("insertRoleApiRoute data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleApiRoute Update(RoleApiRoute roleapiroute) {
		System.out.println("RoleApiRoute save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleApiRoute ec = null;
		try{
			RoleApiRouteMapper RoleApiRouteMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			//userrole = 
			ec = RoleApiRouteMapper.Update(roleapiroute);
			log.info("updateRoleApiRoute data");
		}catch(PersistenceException e){
			log.debug(e + "error get api data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<RoleApiRoute> deleteByroleid(Integer roleid) {
		System.out.println("delete webapi List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<RoleApiRoute> ec = null;
		try{
			RoleApiRouteMapper ecMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			ec = ecMapper.deleteByroleid(roleid);
			log.info("getwebapirole data");
		}catch(PersistenceException e){
			log.debug(e + "error del webapi list data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleApiRoute deleteOne(Integer roleapirouteid) {
		System.out.println("RoleApiRoute delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleApiRoute ec = null;
		try{
			RoleApiRouteMapper ecMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			ec = ecMapper.deleteOne(roleapirouteid);
			log.info("delete RoleApiRoute data");
		}catch(PersistenceException e){
			log.debug(e + "error delete RoleApiRoute data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public RoleApiRoute findByroleandroute(String route, Integer roleid) {
		System.out.println("webapi List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		RoleApiRoute mr = null;
		try{
			RoleApiRouteMapper userMapper = sqlSession.getMapper(RoleApiRouteMapper.class);
			mr = userMapper.findByroleandroute(route,roleid);
			log.info("getwebapi data");
		}catch(PersistenceException e){
			log.debug(e + "error get webapi data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (RoleApiRoute) mr;
	}

}
