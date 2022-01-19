package com.egeroo.roocvthree.generic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.core.usersource.UserSource;
import com.egeroo.roocvthree.core.usersource.UserSourceService;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;

@RestController
@RequestMapping("/gen")
public class GenericController {
	
	private EngineCredentialService ecservice = new EngineCredentialService();
	private int eCid = 1;
	
	private int timeoutrequest =36000000;

	@Autowired
	private UserSourceService usservice;
	
	@Autowired
	private GeneralApiChannelService apiService;

	
	@PostMapping(value = "/channel")
	public String genMethod(@RequestHeader HttpHeaders headers, HttpServletRequest request, @Valid @RequestBody GeneralChannelParam params) {
		
		String tenantId = headers.get("tenantID").get(0);
		String token = headers.get("Authorization").get(0);
		System.out.println("tenant : " + tenantId);
		
		String output = "";
		if(tenantId!=null && token!=null ) {
			EngineCredential result = ecservice.getView(tenantId, this.eCid);
			
			if (result == null) {
				throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
			}		
			
			try {
				
				UserSource ussrc = usservice.findByAuthkey(tenantId, token);
				String chnlToken = ussrc.getChanneltoken();				
				if(params.getKey()!=null || !params.getKey().isEmpty()) {
					GeneralApiChannel apiChannel = apiService.findOne(tenantId, params.getKey());
					String url = result.getChannelapi() + apiChannel.getUrlvalue() + params.getParams();
					if(apiChannel.getMethodvalue().equals("GET")) {
						output = getMethod(url, tenantId, chnlToken);						
					}else {
						output = postMethod(url, tenantId,token, params.getParams());
					}					
				}				
			}catch(Exception e) {
				e.printStackTrace();
			}			
		}
		return output;
	}
	
	public String getMethod(String restUrl, String tenantID, String token)
			throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

		HttpGet request = new HttpGet(restUrl);

		request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		request.setHeader("Accept", "application/json;charset=UTF-8");
		request.setHeader("X-Stream", "true");
		request.setHeader("tenantID", tenantID);
		request.setHeader("Authorization", "Bearer " + token);
		HttpResponse response = httpclient.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + restUrl);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 404) {
			throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}
	
	public String postMethod(String restUrl,String tenantID,String channelToken,String postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.timeoutrequest).build();
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).setDefaultRequestConfig(requestConfig).build();
		    
        HttpPost post = new HttpPost(restUrl);
        
        String authHeader = "Bearer " + channelToken;
		post.setHeader("AUTHORIZATION", authHeader);
		post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		post.setHeader("tenantID", tenantID);
		post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
        	//StringEntity params = new StringEntity(postdata.toString());
	        StringEntity params = new StringEntity(postdata,"UTF-8");
	        post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            CloseableHttpResponse response = null;
            StringBuffer result = new StringBuffer();
			try {
				response = httpclient.execute(post);
				
				System.out.println("Response Code : " + 
			            response.getStatusLine().getStatusCode());
				
				System.out.println("response is : " + response);
				
				System.out.println("response content is : " + response.getEntity().getContent().toString());
				
	    		

	    		BufferedReader rd = null;
				try {
					rd = new BufferedReader(
					 new InputStreamReader(response.getEntity().getContent()));
				} catch (UnsupportedOperationException | IOException e) {
					e.printStackTrace();
				}
				
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
}
