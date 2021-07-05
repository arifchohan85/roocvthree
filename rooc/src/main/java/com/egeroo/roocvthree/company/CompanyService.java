package com.egeroo.roocvthree.company;

import java.util.List;

import org.springframework.stereotype.Service;




@Service
public class CompanyService {
	
	public List<Company> getIndex(String tenant) {
		CompanyMapper appMapper = new CompanyMapperImpl(tenant);
		return appMapper.findAll();	 
	}
	
	public Company getView(String tenant,int companyid) {
		CompanyMapper appMapper = new CompanyMapperImpl(tenant);
		return appMapper.findOne(companyid);
		 
	}
	
	public void getCreate(String tenant,Company company) {
		CompanyMapper appMapper = new CompanyMapperImpl(tenant);
		//return 
		appMapper.Save(company);
	}
	
	public void getUpdate(String tenant,Company company) {
		CompanyMapper appMapper = new CompanyMapperImpl(tenant);
		//return 
		appMapper.Update(company);
	}

}
