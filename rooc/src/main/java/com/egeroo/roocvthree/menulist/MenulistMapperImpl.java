package com.egeroo.roocvthree.menulist;


import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import com.egeroo.roocvthree.core.base.BaseDAO;




public class MenulistMapperImpl extends BaseDAO implements MenulistMapper {
	
	private static final Logger log = Logger.getLogger(MenulistMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public MenulistMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}
	

	@Override
	public List<Menulist> findAll() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Menulist> ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findAll();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public Menulist findOne(Integer menulistid) {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Menulist ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findOne(menulistid);
			log.info("getml data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Menulist) ec;
	}

	@Override
	public String Save(Menulist ml) {
		System.out.println("ml save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			MenulistMapper mlMapper = sqlSession.getMapper(MenulistMapper.class);
			//userrole = 
			lastinsertuserid =mlMapper.Save(ml);
			log.info("insertcompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public String Update(Menulist ml) {
		System.out.println("ml update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			MenulistMapper mlMapper = sqlSession.getMapper(MenulistMapper.class);
			//userrole = 
			lastinsertuserid =mlMapper.Update(ml);
			log.info("updateml data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
		return lastinsertuserid;
	}


	@Override
	public List<Menulist> findAllmenu() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Menulist> ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findAllmenu();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	/*
	 public Menulistall findAllmenu() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Menulistall ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findAllmenu();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}
	*/


	@Override
	public List<Menulist> findParent() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Menulist> ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findParent();
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}


	@Override
	public List<Menulist> findChild(Integer parentid) {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Menulist> ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findChild(parentid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}


	@Override
	public Menulist deleteOne(Integer menulistid) {
		System.out.println("menulist delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Menulist ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.deleteOne(menulistid);
			log.info("delete menulist data");
		}catch(PersistenceException e){
			log.debug(e + "error delete menulist data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}


	@Override
	public List<Menulist> findAllmenuwithrole(Integer roleid) {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Menulist> ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findAllmenuwithrole(roleid);
			log.info("getdir data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}


	@Override
	public Menulist findByRoute(String route) {
		System.out.println("findByRoute : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Menulist ec = null;
		try{
			MenulistMapper ecMapper = sqlSession.getMapper(MenulistMapper.class);
			ec = ecMapper.findByRoute(route);
			log.info("get findByRoute data");
		}catch(PersistenceException e){
			log.debug(e + "error get findByRoute data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Menulist) ec;
	}

}
