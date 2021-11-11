package com.egeroo.roocvthree.interaction;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class InteractionService {
	
	public List<Interaction> getIndex(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public List<InteractionChathistory> getIndexjoindate(String tenant,Date datefrom,Date dateto) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljoindate(datefrom,dateto);	 
	}

	public List<LinkedHashMap> getIndexjoindatev3(String tenant,Date datefrom,Date dateto) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljoindatev3(datefrom,dateto);	 
	}

	public List<InteractionIndex> getIndexjoinistraindate(String tenant,Date datefrom,Date dateto) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljoinistraindate(datefrom,dateto);	 
	}
	
	public List<Interaction> getIndexjoin(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljoin();	 
	}
	
	public List<Interaction> getIndexjointrain(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljointrain();	 
	}
	
	public List<LinkedHashMap> getIndexjointrainv3(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljointrainv3();	 
	}

	public Interaction getIndexjoincount(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findCountalljoin();	 
	}
	
	public List<Interaction> getIndexjoinpaging(String tenant,int paging) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findGetalljoinpaging(10,0,paging);	 
	}
	
	public List<Interaction> getInteraction(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findInteraction();	 
	}
	
	public List<Interaction> getInteractionintent(String tenant,int intentid) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findInteractionintent(intentid);	 
	}
	
	public List<Interaction> extractInteractionintent(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.extractInteractionintent();	 
	}
	
	public List<Interaction> getInteractionintentwithjoin(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findInteractionintentwithjoin();	 
	}
	
	public List<Interaction> getInteractionexpectedintentid(String tenant,int expectedintentid) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findInteractionexpectedintentid(expectedintentid);	 
	}
	
	public List<Interaction> extractInteractionexpectedintentid(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.extractInteractionexpectedintentid();	 
	}
	
	public List<Interaction> getInteractionexpectedintentwithjoin(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findInteractionexpectedintentwithjoin();	 
	}
	
	public Interaction getView(String tenant,int ecid) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findOne(ecid);
	}
	
	public Interaction getViewbyfaqidstr(String tenant,String faqidstr) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findOnebyfaqidstr(faqidstr);
	}
	
	public Interaction getViewbyrespondid(String tenant,int iretrespondid) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findOnebyrespondid(iretrespondid);
	}
	
	public Interaction getMaxinteraction(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findMaxinteractionid();
	}
	
	public String getCreate(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Save(interaction); 
	}
	
	public String getCreateonly(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Saveonly(interaction); 
	}
	
	public String getCreateinternal(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Saveinternal(interaction); 
	}
	
	public String getUpdate(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Update(interaction);
	}
	
	public String getUpdateonly(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Updateonly(interaction);
	}
	
	public String getUpdateinternal(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Updateinternal(interaction);
	}
	
	public void getUpdateistrain(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		//return 
		appMapper.Updateistrain();
	}
	
	public void getUpdateengineidonly(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		//return 
		appMapper.Updateengineidonly(interaction);
	}
	
	public void Updatesyncroocengineid(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		//return 
		appMapper.Updatesyncroocengineid(interaction);
	}
	
	
	
	public Interaction getfindCountinteractionperday(String tenant,Date datefrom, Date dateto, Integer customerchannelid) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findCountinteractionperday(datefrom,dateto,customerchannelid);
	}
	
	public InteractionIndex getIndexjoincount(String tenant,String where) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findCountalljoinpaging(where);	 
	}
	
	public List<InteractionIndex> getIndexlistjoinpaging(String tenant,int paging,int limit,String where,String sort,int countData) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findGetlistjoinpaging(limit,0,paging,where,sort,countData);	 
	}
	
	public String getUpdatedelete(String tenant,Interaction interaction) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.Updatedelete(interaction);
	}
	
	public Interaction getIndexcountjointrain(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findCountjointrain();	 
	}

}
