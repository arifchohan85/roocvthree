package com.egeroo.roocvthree.core.base;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.egeroo.roocvthree.core.tenant.TenantConnection;
import com.egeroo.roocvthree.core.tenant.TenantMapper;
import com.egeroo.roocvthree.core.tenant.TenantMapperImpl;

@Component
public class BaseDAO {
	private static final Logger LOG = Logger.getLogger(BaseDAO.class);
	private static SqlSessionFactory sqlSession = null;	
	private static Map<String, SqlSessionFactory> sqlSessionTenant = new HashMap<>();	
	
	static{
		try{
			String aResource = "mybatis-config.xml";
	        Reader reader = Resources.getResourceAsReader(aResource); 
	        LOG.info("reader from roocvthree = " + reader);
	        System.out.print("reader from roocvthree = " + reader);
	        LOG.debug("reader from roocvthree = " + reader);
	        if(sqlSession==null){
	        	sqlSession = new SqlSessionFactoryBuilder().build(reader);
	        }
	        reader.close();
	        
	        TenantMapper tn = new TenantMapperImpl();	
	        List<TenantConnection> lst = new ArrayList<>();
	        lst = tn.getTenantList();
	        sqlSessionTenant = new HashMap<>();	        	
			
	        for (TenantConnection tenantConnection : lst) {
				addNewTenant(tenantConnection);
			}
		}catch(Exception e){
			LOG.error(e);
		}
	}

	public static SqlSessionFactory getInstance(){
		return sqlSession;
	}
	public static SqlSessionFactory getInstance(String tenantId){		
		return sqlSessionTenant.get(tenantId);
	}
	
	public static void addNewTenant(TenantConnection tenantConnection){		
		if(!sqlSessionTenant.containsKey(tenantConnection.getSchema_name())){
			
			
			PooledDataSource ds = getDataSourceTenant(tenantConnection);
			
			TransactionFactory trxFactory = new JdbcTransactionFactory();
			Environment env = new Environment("postgre", trxFactory, ds);			
			Configuration config = new Configuration(env);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
			
			System.out.println(factory);
			BaseDAO dsd = new BaseDAO();
			dsd.registerMappers(config);
//			config.addMapper(ClientMapper.class);
			sqlSessionTenant.put(tenantConnection.getIdentifier(), factory);			
		}
	}
	public static PooledDataSource getDataSourceTenant(TenantConnection tenant){
		PooledDataSource ds = new PooledDataSource();
		PooledDataSource temp = (PooledDataSource) sqlSession.getConfiguration().getEnvironment().getDataSource();

		try{
			ds.setDriver("org.postgresql.Driver");
			ds.setUrl("jdbc:postgresql://"+tenant.getSchema_server()+":"+tenant.getSchema_server_port()+"/"+tenant.getSchema_name()+"?autoReconnect=true");
			ds.setUsername(tenant.getSchema_username());
			ds.setPassword(tenant.getSchema_password());	
			//ds.setUsername("roocapi");
			//ds.setPassword(tenant.getSchema_password());
			
			ds.setDefaultAutoCommit(true);
			ds.setPoolMaximumActiveConnections(temp.getPoolMaximumActiveConnections());
			ds.setPoolMaximumIdleConnections(temp.getPoolMaximumIdleConnections());
			ds.setPoolMaximumCheckoutTime(temp.getPoolMaximumCheckoutTime());
			ds.setPoolTimeToWait(temp.getPoolTimeToWait());
			ds.setPoolPingQuery(temp.getPoolPingQuery());
			ds.setPoolPingEnabled(temp.isPoolPingEnabled());
			ds.setPoolPingConnectionsNotUsedFor(temp.getPoolPingConnectionsNotUsedFor());
			
			
			//ds.setLoginTimeout(20000);
			
		}catch(Exception e){
			LOG.error(e);
		}
		return ds;
	}	
	public static DataSource getDataSourceTenantbackup(TenantConnection tenant){
		BasicDataSource ds = new BasicDataSource();
		try{
			ds.setDriverClassName("org.postgresql.Driver");
			ds.setUrl("jdbc:postgresql://"+tenant.getSchema_server()+":"+tenant.getSchema_server_port()+"/"+tenant.getSchema_name());
			ds.setUsername(tenant.getSchema_username());
			ds.setPassword(tenant.getSchema_password());	
			//ds.setUsername("roocapi");
			//ds.setPassword(tenant.getSchema_password());
			ds.setInitialSize(30);
			ds.setMaxActive(300);
			ds.setMaxIdle(2000);
			//additional
			ds.setMinIdle(100);
			ds.setDefaultAutoCommit(true);
			ds.setMaxWait(45000);
			ds.setAccessToUnderlyingConnectionAllowed(true);
			//ds.setLoginTimeout(20000);
			
		}catch(Exception e){
			LOG.error(e);
		}
		return ds;
	}	
	
	/**
	 * ini untuk daftarin mappers yang akan dipanggil untuk SQL nya
	 * penambahan mappers dengan cara scan package
	 * @param config
	 */
	protected void registerMappers(Configuration config){
		config.addMappers("com.egeroo.roocvthree");
	}
}
