package com.egeroo.roocvthree.userchannellink;

import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class UserChannelLinkService {
	
	public List<UserChannelLink> getIndex(String tenant) {
		UserChannelLinkMapper appMapper = new UserChannelLinkMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public List<UserChannelLink> getViewByUser(String tenant,int userid) {
		UserChannelLinkMapper appMapper = new UserChannelLinkMapperImpl(tenant);
		return appMapper.findByUser(userid);	 
	}
	
	public UserChannelLink getView(String tenant,int id) {
		UserChannelLinkMapper appMapper = new UserChannelLinkMapperImpl(tenant);
		return appMapper.findOne(id);
	}
	
	public UserChannelLink getCreate(String tenant,UserChannelLink userchannellink) {
		UserChannelLinkMapper appMapper = new UserChannelLinkMapperImpl(tenant);
		return appMapper.Save(userchannellink);
	}
	
	public UserChannelLink getUpdate(String tenant,UserChannelLink userchannellink) {
		UserChannelLinkMapper appMapper = new UserChannelLinkMapperImpl(tenant);
		return appMapper.Update(userchannellink);
	}
	
	public UserChannelLink getDelete(String tenant,int id) {
		UserChannelLinkMapper appMapper = new UserChannelLinkMapperImpl(tenant);
		return appMapper.deleteOne(id);
	}

}
