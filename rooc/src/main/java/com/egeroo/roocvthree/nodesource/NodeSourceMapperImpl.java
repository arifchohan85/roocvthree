package com.egeroo.roocvthree.nodesource;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;


public class NodeSourceMapperImpl extends BaseDAO implements NodeSourceMapper{
	
	private static final Logger log = Logger.getLogger(NodeSourceMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public NodeSourceMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<NodeSource> findAll() {
		System.out.println("NodeSource List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<NodeSource> ns = null;
		try{
			NodeSourceMapper nodeSourceMapper = sqlSession.getMapper(NodeSourceMapper.class);
			ns = nodeSourceMapper.findAll();
			log.info("getNodeSource data");
		}catch(PersistenceException e){
			log.debug(e + "error get NodeSource data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ns;
	}

	@Override
	public NodeSource findOne(Integer nodesourceid) {
		System.out.println("NodeSource List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		NodeSource ns = null;
		try{
			NodeSourceMapper userMapper = sqlSession.getMapper(NodeSourceMapper.class);
			ns = userMapper.findOne(nodesourceid);
			log.info("getNodeSource data");
		}catch(PersistenceException e){
			log.debug(e + "error get NodeSource data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ns;
	}

	@Override
	public NodeSource findOnebyNodeid(String nodeid) {
		System.out.println("NodeSource List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		NodeSource ns = null;
		try{
			NodeSourceMapper userMapper = sqlSession.getMapper(NodeSourceMapper.class);
			ns = userMapper.findOnebyNodeid(nodeid);
			log.info("getNodeSource data");
		}catch(PersistenceException e){
			log.debug(e + "error get NodeSource data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return ns;
	}

	@Override
	public Integer Save(NodeSource nodesource) {
		System.out.println("NodeSource save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		int lastinsertuserid=0;
		try{
			NodeSourceMapper nsMapper = sqlSession.getMapper(NodeSourceMapper.class);
			//userrole = 
			lastinsertuserid =nsMapper.Save(nodesource);
			log.debug("insert id is : " + lastinsertuserid);
			log.info("insertNodeSource data");
		}catch(PersistenceException e){
			log.debug(e + "error get NodeSource data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return lastinsertuserid;
	}

}
