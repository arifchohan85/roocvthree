package com.egeroo.roocvthree.nodesource;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class NodeSourceService {
	
	public List<NodeSource> getIndex(String tenant) {
		NodeSourceMapper appMapper = new NodeSourceMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public NodeSource getView(String tenant,int nodesourceid) {
		NodeSourceMapper appMapper = new NodeSourceMapperImpl(tenant);
		return appMapper.findOne(nodesourceid);
    }
	
	public Integer getCreate(String tenant,NodeSource user) {
		NodeSourceMapper appMapper = new NodeSourceMapperImpl(tenant);
		return appMapper.Save(user); 
	}
	
	public NodeSource getBynodeid(String tenant,String nodeid) {
		NodeSourceMapper appMapper = new NodeSourceMapperImpl(tenant);
		return appMapper.findOnebyNodeid(nodeid);
    }

}
