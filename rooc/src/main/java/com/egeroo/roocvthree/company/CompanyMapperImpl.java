package com.egeroo.roocvthree.company;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.egeroo.roocvthree.core.base.BaseDAO;





public class CompanyMapperImpl extends BaseDAO implements CompanyMapper{
	
	private static final Logger log = Logger.getLogger(CompanyMapperImpl.class);	
	private SqlSession sqlSession = null;	
	private String tenantIdentifier="";
	
	public CompanyMapperImpl(String tenantIdentifier){
		this.tenantIdentifier=tenantIdentifier;
	}

	@Override
	public List<Company> findAll() {
		System.out.println("company List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		List<Company> company = null;
		try{
			CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
			company = companyMapper.findAll();
			log.info("getcompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return company;
	}

	@Override
	public Company findOne(Integer companyid) {
		System.out.println("company List : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		Company company = null;
		try{
			CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
			company = companyMapper.findOne(companyid);
			log.info("getCompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return (Company) company;
	}

	@Override
	public void Save(Company company) {
		System.out.println("company save : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
			//userrole = 
			companyMapper.Save(company);
			log.info("insertcompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
	}

	@Override
	public void Update(Company company) {
		System.out.println("company update : " + this.tenantIdentifier);
		sqlSession = super.getInstance(this.tenantIdentifier).openSession();
		
		try{
			CompanyMapper companyMapper = sqlSession.getMapper(CompanyMapper.class);
			//userrole = 
			companyMapper.Update(company);
			log.info("updatecompany data");
		}catch(PersistenceException e){
			log.debug(e + "error get user data");
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		
	}

}
