package com.egeroo.roocvthree.interaction;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.trailingrecord.TrailingRecordBase;
import com.egeroo.roocvthree.userprofile.UserProfile;
import com.egeroo.roocvthree.userprofile.UserProfileService;
import com.egeroo.roocvthree.userprofile.UserProfileView;



@Service
public class InteractionService {
	
	TrailingRecordBase trb = new TrailingRecordBase();
	
	@Autowired private UserProfileService profService;
	
	public List<Interaction> getIndex(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public List<InteractionChathistory> getIndexjoindate(String tenant,Date datefrom,Date dateto) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findAlljoindate(datefrom,dateto);	 
	}

	@SuppressWarnings("rawtypes")
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
	
	@SuppressWarnings("rawtypes")
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
	
	public String getCreatev2(String tenant,Interaction interaction) {
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
	
	public String getUpdatev2(String tenant,Interaction interaction) {
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

	
	/* v3 */
	
	public InteractionResponse getCreate(String tenant,InteractionRequest intr,String token) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		
		Interaction interaction = new Interaction();
		interaction.setIntentid(intr.getIntentId());
		interaction.setExpectedintentid(intr.getIntentId());
		interaction.setQuestion(intr.getQuestion());
		interaction.setFaqidstr("");
		interaction.setMinconfidence(10);
		interaction.setMaxconfidence(100);
		interaction.setActive(1);
		interaction.setIsupdated(1);
		interaction.setIstrain(0);
		interaction.setConfidencelevel(0);
		
		trb.SetTrailRecord(token,interaction);
		int userid = trb.Parseuserid(token);
		
		String intrid =  appMapper.Save(interaction); 
		
		String upname = "";
		UserProfile upresult = profService.getViewbyuserid(tenant,userid);
		
		if(upresult==null)
		{
			
		}
		else
		{
			upname = upresult.getName();
		}
		
		if(Integer.parseInt(intrid)>0)
		{
			InteractionResponse intrres = new InteractionResponse();
			intrres.setId(Integer.parseInt(intrid));
			intrres.setQuestion(intr.getQuestion());
			intrres.setIntentId(intr.getIntentId());
			intrres.setCreatedOn(interaction.getCreatedtime());
			intrres.setCreatedBy(upname);
			
			return intrres;
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Data not saved");
		}
		
	}
	
	public InteractionResponse getUpdate(String tenant,InteractionRequest intr,String token) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		
		int expintentid =0;
		if(intr.getIntentId()>0)
		{
			expintentid = intr.getIntentId();
		}
		
		Interaction interaction = new Interaction();
		interaction.setInteractionid(intr.getId());
		interaction.setIntentid(intr.getIntentId());
		
		interaction.setExpectedintentid(expintentid);
		interaction.setQuestion(intr.getQuestion());
		//interaction.setFaqidstr("");
		interaction.setMinconfidence(10);
		interaction.setMaxconfidence(100);
		interaction.setActive(1);
		interaction.setIsupdated(1);
		interaction.setIstrain(0);
		interaction.setConfidencelevel(0);
		
		trb.SetTrailRecord(token,interaction);
		int userid = trb.Parseuserid(token);
		
		String intrid = appMapper.Update(interaction);
		
		String upname = "";
		UserProfile upresult = profService.getViewbyuserid(tenant,userid);
		
		if(upresult==null)
		{
			
		}
		else
		{
			upname = upresult.getName();
		}
		
		if(Integer.parseInt(intrid)>0)
		{
			InteractionResponse intrres = new InteractionResponse();
			intrres.setId(Integer.parseInt(intrid));
			intrres.setQuestion(intr.getQuestion());
			intrres.setIntentId(intr.getIntentId());
			intrres.setCreatedOn(interaction.getCreatedtime());
			intrres.setCreatedBy(upname);
			
			return intrres;
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Data not saved");
		}
	}
	
	public InteractionResponse getDelete(String tenant,int questionId,String token) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		
		
		Interaction interaction = this.getView(tenant,questionId);
		
		if(interaction==null)
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "No Data found");
		}
		
		int expintentid =0;
		
		
		//interaction.setInteractionid(intr.getId());
		//interaction.setIntentid(intr.getIntentId());
		
		interaction.setExpectedintentid(expintentid);
//		interaction.setQuestion(intr.getQuestion());
//		//interaction.setFaqidstr("");
//		interaction.setMinconfidence(10);
//		interaction.setMaxconfidence(100);
		interaction.setActive(1);
		interaction.setIsupdated(1);
		interaction.setIstrain(0);
//		interaction.setConfidencelevel(0);
		
		trb.SetTrailRecord(token,interaction);
		//int userid = trb.Parseuserid(token);
		
		String intrid = appMapper.Update(interaction);
		
		String upname = "";
		UserProfileView upresult = profService.getView(tenant,interaction.getCreatedby());
		
		if(upresult==null)
		{
			
		}
		else
		{
			upname = upresult.getName();
		}
		
		if(Integer.parseInt(intrid)>0)
		{
			InteractionResponse intrres = new InteractionResponse();
			intrres.setId(Integer.parseInt(intrid));
			intrres.setQuestion(interaction.getQuestion());
			intrres.setIntentId(interaction.getIntentid());
			intrres.setCreatedOn(interaction.getCreatedtime());
			intrres.setCreatedBy(upname);
			
			return intrres;
		}
		else
		{
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "Data not saved");
		}
	}
	
	public List<InteractionResponse> getListquestions(String tenant) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findlistquestions();	 
	}
	
	public List<InteractionResponse> getListquestionsbyexpectedintentid(String tenant,int expectedintentid) {
		InteractionMapper appMapper = new InteractionMapperImpl(tenant);
		return appMapper.findlistquestionbyexpectedintentid(expectedintentid);	 
	}
	
	/* v3 */
	
}
