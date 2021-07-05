package com.egeroo.roocvthree.userchannellink;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;



public class UserChannelLinkMapperImpl   extends BaseDAO implements UserChannelLinkMapper {
	
	private static final Logger log = Logger.getLogger(UserChannelLinkMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public UserChannelLinkMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<UserChannelLink> findAll() {
		System.out.println("UserChannelLink List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<UserChannelLink> ec = null;
		try{
			UserChannelLinkMapper ecMapper = sqlSession.getMapper(UserChannelLinkMapper.class);
			ec = ecMapper.findAll();
			log.info("get UserChannelLink data");
		}catch(PersistenceException e){
			log.debug(e + "error get UserChannelLink data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public UserChannelLink findOne(Integer id) {
		System.out.println("UserChannelLink List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserChannelLink ec = null;
		try{
			UserChannelLinkMapper ecMapper = sqlSession.getMapper(UserChannelLinkMapper.class);
			ec = ecMapper.findOne(id);
			log.info("get UserChannelLink data");
		}catch(PersistenceException e){
			log.debug(e + "error get UserChannelLink data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (UserChannelLink) ec;
	}

	@Override
	public List<UserChannelLink> findByUser(Integer userid) {
		System.out.println("UserChannelLink by user List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<UserChannelLink> ec = null;
		try{
			UserChannelLinkMapper ecMapper = sqlSession.getMapper(UserChannelLinkMapper.class);
			ec = ecMapper.findByUser(userid);
			log.info("get UserChannelLink by user data");
		}catch(PersistenceException e){
			log.debug(e + "error get UserChannelLink by user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public UserChannelLink Save(UserChannelLink userchannellink) {
		System.out.println("UserChannelLink save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserChannelLink ec = null;
		try{
			UserChannelLinkMapper ecMapper = sqlSession.getMapper(UserChannelLinkMapper.class);
			//userrole = 
			ec = ecMapper.Save(userchannellink);
			log.info("insert UserChannelLink data");
		}catch(PersistenceException e){
			log.debug(e + "error insert UserChannelLink data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public UserChannelLink Update(UserChannelLink userchannellink) {
		System.out.println("UserChannelLink save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserChannelLink ec = null;
		try{
			UserChannelLinkMapper ecMapper = sqlSession.getMapper(UserChannelLinkMapper.class);
			//userrole = 
			ec = ecMapper.Update(userchannellink);
			log.info("updateUserChannelLink data");
		}catch(PersistenceException e){
			log.debug(e + "error update UserChannelLink data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

	@Override
	public UserChannelLink deleteOne(Integer id) {
		System.out.println("UserChannelLink delete : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		UserChannelLink ec = null;
		try{
			UserChannelLinkMapper ecMapper = sqlSession.getMapper(UserChannelLinkMapper.class);
			ec = ecMapper.deleteOne(id);
			log.info("delete UserChannelLink data");
		}catch(PersistenceException e){
			log.debug(e + "error delete UserChannelLink data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ec;
	}

}
