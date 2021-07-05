package com.egeroo.roocvthree.menulistrole;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;







public class MenuListRoleMapperImpl  extends BaseDAO implements MenuListRoleMapper {
	
	private static final Logger log = Logger.getLogger(MenuListRoleMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public MenuListRoleMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<MenuListRole> findAll() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<MenuListRole> ec = null;
		try{
			MenuListRoleMapper ecMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			ec = ecMapper.findAll();
			log.info("getmenurole data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public List<MenuListRole> findJoinall() {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<MenuListRole> ec = null;
		try{
			MenuListRoleMapper ecMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			ec = ecMapper.findJoinall();
			log.info("getmenurole data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public MenuListRole findOne(Integer menulistroleid) {
		System.out.println("ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		MenuListRole ec = null;
		try{
			MenuListRoleMapper ecMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			ec = ecMapper.findOne(menulistroleid);
			log.info("getml data");
		}catch(PersistenceException e){
			log.debug(e + "error get ml data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (MenuListRole) ec;
	}

	@Override
	public String Save(MenuListRole mlr) {
		System.out.println("ml save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			MenuListRoleMapper mlMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			//userrole = 
			lastinsertuserid =mlMapper.Save(mlr);
			log.info("insert menu role data");
		}catch(PersistenceException e){
			log.debug(e + "error get menu role data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

	@Override
	public String Update(MenuListRole mlr) {
		System.out.println("ml update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		String lastinsertuserid="0";
		try{
			MenuListRoleMapper mlMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			//userrole = 
			lastinsertuserid =mlMapper.Update(mlr);
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
	public MenuListRole deleteOne(Integer menulistroleid) {
		System.out.println("menulist delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		MenuListRole ec = null;
		try{
			MenuListRoleMapper ecMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			ec = ecMapper.deleteOne(menulistroleid);
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
	public MenuListRole findByroleandmenu(Integer menulistid, Integer roleid) {
		System.out.println("menurole List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		MenuListRole mr = null;
		try{
			MenuListRoleMapper userMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			mr = userMapper.findByroleandmenu(menulistid,roleid);
			log.info("getUser data");
		}catch(PersistenceException e){
			log.debug(e + "error get menurole data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (MenuListRole) mr;
	}

	@Override
	public List<MenuListRole> deleteBymenulistid(Integer menulistid) {
		System.out.println("delete ml List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<MenuListRole> ec = null;
		try{
			MenuListRoleMapper ecMapper = sqlSession.getMapper(MenuListRoleMapper.class);
			ec = ecMapper.deleteBymenulistid(menulistid);
			log.info("getmenurole data");
		}catch(PersistenceException e){
			log.debug(e + "error del ml list data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
