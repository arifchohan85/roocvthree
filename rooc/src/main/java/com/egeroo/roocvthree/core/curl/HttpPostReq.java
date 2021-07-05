package com.egeroo.roocvthree.core.curl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.codec.binary.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.enginecredential.EngineCredential;
import com.egeroo.roocvthree.enginecredential.EngineCredentialService;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigService;

import org.json.JSONArray;
import org.json.JSONObject;



public class HttpPostReq {
	
	@Autowired
    private EngineCredentialService service;
	
	
	private String username="myusername";
	private String password="mypassword";

	RoocConfigService rcservice = new RoocConfigService();
	private int timeoutrequest =36000000;
	
	public String ConnectGetToken(String API,String Username,String Password) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		
		String restUrl=API;
		this.username = Username;
		this.password = Password;
        
        
        HttpPostReq httpPostReq=new HttpPostReq();
        String httpPost=httpPostReq.getEnginetoken(restUrl , this.username, this.password);
        
        System.out.println("post return : " + httpPost);
		return httpPost;
        
       
	}
	
	public String ConnectGetnoparam(String API) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		String restUrl=API;
		/*String restUrl=API;
		//this.username = Username;
		//this.password = Password;
        System.out.print("Sending Request to :" + restUrl);
        
        HttpPostReq httpPostReq=new HttpPostReq();
        String httpPost=httpPostReq.getEnginetoken(restUrl , this.username, this.password);
        
        System.out.println("post return : " + httpPost);
		return httpPost;*/
		
		SSLContextBuilder builder = new SSLContextBuilder();
	    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	            builder.build());
	    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
	            sslsf).build();

	    //HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(restUrl);

		// add request header
		//request.addHeader("User-Agent", USER_AGENT);
		//post.setHeader("User-Agent", USER_AGENT);
		//request.setHeader("AUTHORIZATION", token);
		//request.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
		//request.setHeader("Accept", "application/json;charset=UTF-8");
		// request.setHeader("X-Stream" , "true");
		HttpResponse response = httpclient.execute(request);
		
		System.out.println("\nSending 'GET' request to URL : " + restUrl);

		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() == 404)
		{
			throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
		}

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

        
		return result.toString();
        
       
	}
	
	
	
	public String ConnectGetTokenchannel(String API,String token) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		String restUrl=API;
        HttpPostReq httpPostReq=new HttpPostReq();
        String httpPost=httpPostReq.getUserchanneltoken(restUrl,token);
        
        System.out.println("post return : " + httpPost);
		return httpPost;
	}
	
	
	public String getUserchanneltoken(String restUrl,String token) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    //HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(restUrl);

			// add request header
			//request.addHeader("User-Agent", USER_AGENT);
			//post.setHeader("User-Agent", USER_AGENT);
			boolean isEmptytoken = token == null || token.trim().length() == 0;
			
			if(!isEmptytoken)
			{
				request.setHeader("AUTHORIZATION", token);
			}
			
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
			request.setHeader("Accept", "application/json;charset=UTF-8");
			request.setHeader("X-Stream" , "true");
			HttpResponse response = httpclient.execute(request);
			
			System.out.println("\nSending 'GET' request to URL : " + restUrl);

			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == 404)
			{
				throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
			}

			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

            
        return result.toString();
    }


	public String getUserchanneltokenwithtenantid(String restUrl,String token,String tenantID) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    //HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(restUrl);

			// add request header
			//request.addHeader("User-Agent", USER_AGENT);
			//post.setHeader("User-Agent", USER_AGENT);
			boolean isEmptytoken = token == null || token.trim().length() == 0;
			
			if(!isEmptytoken)
			{
				request.setHeader("AUTHORIZATION", token);
			}
			
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
			request.setHeader("Accept", "application/json;charset=UTF-8");
			request.setHeader("X-Stream" , "true");
			request.setHeader("tenantID",tenantID);
			HttpResponse response = httpclient.execute(request);
			
			System.out.println("\nSending 'GET' request to URL : " + restUrl);

			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == 404)
			{
				throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
			}

			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

            
        return result.toString();
    }


	
	
	public void connectenginecredential(String tenantID,String API,int recID) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		EngineCredential result = service.getView(tenantID,recID);
		if (result == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no data found.");
        }
		
		String restUrl=result.getApi()+"/auth/login";
		this.username = result.getUsername();
		this.password = result.getPassword();
        
        /*JSONObject user=new JSONObject();
        user.put("name", "davy jones");
        user.put("email", "davy@gmail.com");
        String jsonData=user.toString();*/
        HttpPostReq httpPostReq=new HttpPostReq();
        String httpPost=httpPostReq.createConnectivity(restUrl , this.username, this.password);
        
        System.out.println("post return : " + httpPost);
        
        //httpPostReq.executeReq( jsonData, httpPost);
	}
	
	public void connect(String tenantID,String API,String EngineToken) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
		String restUrl="https://myApp.com/api/v1/json";
        String username="myusername";
        String password="mypassword";
        /*JSONObject user=new JSONObject();
        user.put("name", "davy jones");
        user.put("email", "davy@gmail.com");
        String jsonData=user.toString();*/
        HttpPostReq httpPostReq=new HttpPostReq();
        String httpPost=httpPostReq.createConnectivity(restUrl , username, password);
        System.out.print(httpPost);
        //httpPostReq.executeReq( jsonData, httpPost);
	}
	
	public String getEnginetoken(String restUrl, String username, String password) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("AUTHORIZATION", authHeader);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
		
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));
        
     	
            try {
				post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	
	
	public String ConnectPostData(String API,JSONObject postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
	{
		
		String restUrl=API;
		
        
        
        HttpPostReq httpPostReq=new HttpPostReq();
        String httpPost=httpPostReq.setPostData(restUrl,postdata);
        
        System.out.println("post return : " + httpPost);
		return httpPost;
        
       
	}
	
	
	public String setPostData(String restUrl,JSONObject postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		//RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.timeoutrequest).build();
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    //CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		    //        sslsf).build();
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).setDefaultRequestConfig(requestConfig).build();
		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		/*String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);*/
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		//post.setHeader("AUTHORIZATION", authHeader);
        //post.addHeader("content-type", "application/json");
        //post.setHeader("Content-Type", "application/json");//application/x-www-form-urlencoded // application/json
        
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
        post.addHeader("Expires", "-1");
            
		
       /* List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));*/
        
        	StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
            post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		System.out.println("Post data is : " + postdata);
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	
	
	

	public String setPostDatachannel(String restUrl,String channel_token,JSONObject postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 /*SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();*/
		//RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.timeoutrequest).build();
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    //CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		    //        sslsf).build();
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).setDefaultRequestConfig(requestConfig).build();
		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		/*String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        
        
        System.out.println(auth);*/
        String authHeader = "Bearer " + new String(channel_token);
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("AUTHORIZATION", authHeader);
        //post.addHeader("content-type", "application/json");
        //post.setHeader("Content-Type", "application/json");//application/x-www-form-urlencoded // application/json
		//post.setHeader("Content-Type", "application/json;charset=UTF-8");
		post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
		
       /* List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));*/
        
        	//StringEntity params = new StringEntity(postdata.toString());
	        StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
	        post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	
	
	public String setPostDataarray(String restUrl,JSONArray postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		/* SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();
		*/
		//RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).build();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.timeoutrequest).build();
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    //CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		    //        sslsf).build();
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).setDefaultRequestConfig(requestConfig).build();

		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		/*String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);*/
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		//post.setHeader("AUTHORIZATION", authHeader);
        //post.addHeader("content-type", "application/json");
        //post.setHeader("Content-Type", "application/json");//application/x-www-form-urlencoded // application/json
        //post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
		
       /* List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));*/
        
        	//StringEntity params = new StringEntity(postdata.toString());
        	StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
            post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		System.out.println("Post Data is :" + postdata);
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	

	public String setPostDatalocal(String restUrl,JSONObject postdata,String tenantID,String Token) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		/*String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);*/
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		//post.setHeader("AUTHORIZATION", authHeader);
        //post.addHeader("content-type", "application/json");
        //post.setHeader("Content-Type", "application/json");//application/x-www-form-urlencoded // application/json
        //post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        //post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
        post.setHeader("tenantID" , tenantID);
        post.setHeader("Authorization" , Token);
            
		
       /* List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));*/
        
        	//StringEntity params = new StringEntity(postdata.toString());
        	StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
            post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	
	public String setGetlocaldata(String restUrl,String tenantID,String token) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    //HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(restUrl);

			// add request header
			//request.addHeader("User-Agent", USER_AGENT);
			//post.setHeader("User-Agent", USER_AGENT);
			/*request.setHeader("AUTHORIZATION", token);
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
			request.setHeader("Accept", "application/json;charset=UTF-8");
			request.setHeader("X-Stream" , "true");*/
			
			request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
			request.setHeader("Accept", "application/json;charset=UTF-8");
			request.setHeader("X-Stream" , "true");
			request.setHeader("tenantID" , tenantID);
			request.setHeader("Authorization" , token);
			HttpResponse response = httpclient.execute(request);
			
			System.out.println("\nSending 'GET' request to URL : " + restUrl);

			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == 404)
			{
				throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
			}

			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

            
        return result.toString();
    }


	
	
	
	

	public String setPostDataxml(String restUrl,String postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		/*String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);*/
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		//post.setHeader("AUTHORIZATION", authHeader);
        //post.addHeader("content-type", "application/json");
        //post.setHeader("Content-Type", "application/json");//application/x-www-form-urlencoded // application/json
        
        //post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
        //post.setHeader("Content-Type", "application/xml");
        //post.setHeader("Accept", "text/xml");
        //post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setHeader("X-Stream" , "true");
        post.addHeader("SOAPAction", "http://tempuri.org/Authenticate2");
            
		//"http://tempuri.org/Authenticate2"
       /* List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));*/
        
        	StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
        	params.setContentEncoding("UTF-8");
        	params.setContentType("text/xml");
        	
        	params.setChunked(true);
            post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	
	
	
	@SuppressWarnings("deprecation")
	public String createConnectivity(String restUrl, String username, String password) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("AUTHORIZATION", authHeader);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
		
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));
        
     	
            try {
				post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		System.out.println("Post parameters : " + post.getParams());
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	
	
	void executeReq(String jsonData, HttpPost httpPost)
    {
        try{
            executeHttpRequest(jsonData, httpPost);
        }
        catch (UnsupportedEncodingException e){
            System.out.println("error while encoding api url : "+e);
        }
        catch (IOException e){
            System.out.println("ioException occured while sending http request : "+e);
        }
        catch(Exception e){
            System.out.println("exception occured while sending http request : "+e);
        }
        finally{
            httpPost.releaseConnection();
        }
    }
	
	void executeHttpRequest(String jsonData,  HttpPost httpPost)  throws UnsupportedEncodingException, IOException
    {
        HttpResponse response=null;
        String line = "";
        StringBuffer result = new StringBuffer();
        httpPost.setEntity(new StringEntity(jsonData,"UTF-8"));
        HttpClient client = HttpClientBuilder.create().build();
        response = client.execute(httpPost);
        System.out.println("Post parameters : " + jsonData );
        System.out.println("Response Code : " +response.getStatusLine().getStatusCode());
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while ((line = reader.readLine()) != null){ result.append(line); }
        System.out.println(result.toString());
    }
	

	public String setPostDataldap(String restUrl,String ClientID,JSONObject postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    
        HttpPost post = new HttpPost(restUrl);
        
     	
     	
		
		/*String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        
        System.out.println(auth);*/
		
        // add header
     	//post.setHeader("User-Agent", USER_AGENT);
		//post.setHeader("AUTHORIZATION", authHeader);
        //post.addHeader("content-type", "application/json");
        //post.setHeader("Content-Type", "application/json");//application/x-www-form-urlencoded // application/json
        
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        post.setHeader("ClientID", ""+ClientID);
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
		
       /* List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
		urlParameters.add(new BasicNameValuePair("username", username));
		urlParameters.add(new BasicNameValuePair("password", password));
		urlParameters.add(new BasicNameValuePair("client_id", username));*/
        
        	StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
            post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	


	public String setPostDataldapbcabak19092019(String restUrl,String tenantID,JSONObject postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    
        
        
        RoocConfig resultappid = new RoocConfig();
		resultappid = rcservice.findByconfigkey(tenantID,"applicationid");
		
		
		if (resultappid == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
        }
		
		RoocConfig resultldappostapi = new RoocConfig();
		resultldappostapi = rcservice.findByconfigkey(tenantID,"ldapapipost");
		
		if (resultldappostapi == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post url found.");
        }
		
		RoocConfig resultldaploginposturl = new RoocConfig();
		resultldaploginposturl = rcservice.findByconfigkey(tenantID,"ldaploginposturl");
		
		if (resultldaploginposturl == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no post uri found.");
        }
		
		
		RoocConfig resultclientid = new RoocConfig();
		resultclientid = rcservice.findByconfigkey(tenantID,"clientid");
		
		if (resultclientid == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no clientid found.");
        }
		
		RoocConfig resultmybcaapp = new RoocConfig();
		resultmybcaapp = rcservice.findByconfigkey(tenantID,"X-MYBCA-APPLICATION");
		
		if (resultmybcaapp == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no mybcaapp found.");
        }
		
		RoocConfig resultencryptionkey = new RoocConfig();
		resultencryptionkey = rcservice.findByconfigkey(tenantID,"encryptionkey");
		
		if (resultencryptionkey == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no encryptionkey found.");
        }
		
		
		restUrl = resultldappostapi.getConfigvalue()+""+resultldaploginposturl.getConfigvalue();
		HttpPost post = new HttpPost(restUrl);
     	
		
		
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        post.setHeader("ClientID", ""+resultclientid.getConfigvalue());
        post.setHeader("ApplicationID", ""+resultappid.getConfigvalue());
        post.setHeader(""+resultmybcaapp.getConfigkey(), ""+resultmybcaapp.getConfigvalue());
        post.setHeader("AUTHORIZATION", "Beaerer " + ""+Base64.encodeBase64(resultencryptionkey.getConfigvalue().getBytes()));
        post.setHeader("Accept", "application/json;charset=UTF-8");
        post.setHeader("X-Stream" , "true");
            
		
       
        
        	StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
            post.setEntity(params);
            
            System.out.println("\nSending 'POST' request to URL : " + restUrl);
    		System.out.println("Post parameters : " + post.getEntity());
    		
            
            //HttpResponse responseser = null;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//StringBuffer result = new StringBuffer();
	    		String line = "";
	    		try {
					while ((line = rd.readLine()) != null) {
						result.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	    		System.out.println(result.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
		        response.close();
		    }
    	
            
        return result.toString();
    }
	

	public String setPostDataldapbca(String restUrl,String tenantID,JSONObject postdata) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
	{

		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
				sslsf).build();


		HttpPost post = new HttpPost(restUrl);

        /*RoocConfig resultappid = new RoocConfig();
		resultappid = rcservice.findByconfigkey(tenantID,"applicationid");


		if (resultappid == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
        }*/




		RoocConfig resultclientid = new RoocConfig();
		resultclientid = rcservice.findByconfigkey(tenantID,"clientid");

		if (resultclientid == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no clientid found.");
		}

        /*
		RoocConfig resultmybcaapp = new RoocConfig();
		resultmybcaapp = rcservice.findByconfigkey(tenantID,"X-MYBCA-APPLICATION");

		if (resultmybcaapp == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no mybcaapp found.");
        }

		RoocConfig resultencryptionkey = new RoocConfig();
		resultencryptionkey = rcservice.findByconfigkey(tenantID,"encryptionkey");

		if (resultencryptionkey == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no encryptionkey found.");
        }

		*/

		post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		post.setHeader("ClientID", ""+resultclientid.getConfigvalue());
		//post.setHeader("ApplicationID", ""+resultappid.getConfigvalue());
		//post.setHeader(""+resultmybcaapp.getConfigkey(), ""+resultmybcaapp.getConfigvalue());
		//post.setHeader("AUTHORIZATION", "Beaerer " + ""+Base64.encodeBase64(resultencryptionkey.getConfigvalue().getBytes()));
		//post.setHeader("Accept", "application/json;charset=UTF-8");
		//post.setHeader("X-Stream" , "true");




		StringEntity params = new StringEntity(postdata.toString(),"UTF-8");
		post.setEntity(params);

		System.out.println("\nSending 'POST' request to URL : " + restUrl);
		System.out.println("Post parameters : " + post.getEntity());


		//HttpResponse responseser = null;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//StringBuffer result = new StringBuffer();
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(result.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			response.close();
		}


		return result.toString();
	}
	
	
	public String getGetdataldap(String restUrl,String BearerKey,String mBCAApplication,String token) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
    {
		
		 SSLContextBuilder builder = new SSLContextBuilder();
		    builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		            builder.build());
		    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
		            sslsf).build();

		    //HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(restUrl);

			
			request.setHeader("AUTHORIZATION", "Beaerer " + BearerKey);
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded // application/json
			request.setHeader("Accept", "application/json;charset=UTF-8");
			request.setHeader("X-Stream" , "true");
			request.setHeader("X-MYBCA-APPLICATION", mBCAApplication);
			HttpResponse response = httpclient.execute(request);
			
			System.out.println("\nSending 'GET' request to URL : " + restUrl);

			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode() == 404)
			{
				throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
			}

			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

            
        return result.toString();
    }



	public String getGetdataldapbca(String Urlparam,String tenantID) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
	{

		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
				sslsf).build();

		RoocConfig resultldapgetapi = new RoocConfig();
		resultldapgetapi = rcservice.findByconfigkey(tenantID,"ldapgetapi");

		if (resultldapgetapi == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no get api uri found.");
		}

		RoocConfig resultgeturl = new RoocConfig();
		resultgeturl = rcservice.findByconfigkey(tenantID,"ldaplogingeturl");

		if (resultgeturl == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no get uri found.");
		}

		//HttpClient client = HttpClientBuilder.create().build();
		String url = resultldapgetapi.getConfigvalue()+""+resultgeturl.getConfigvalue()+""+Urlparam;
		HttpGet request = new HttpGet(url);

		RoocConfig resultappid = new RoocConfig();
		resultappid = rcservice.findByconfigkey(tenantID,"applicationid");


		if (resultappid == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no applicationid found.");
		}




		RoocConfig resultclientid = new RoocConfig();
		resultclientid = rcservice.findByconfigkey(tenantID,"clientid");

		if (resultclientid == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no clientid found.");
		}

		RoocConfig resultmybcaapp = new RoocConfig();
		resultmybcaapp = rcservice.findByconfigkey(tenantID,"X-MYBCA-APPLICATION");

		if (resultmybcaapp == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no mybcaapp found.");
		}

		RoocConfig resultencryptionkey = new RoocConfig();
		resultencryptionkey = rcservice.findByconfigkey(tenantID,"encryptionkey");

		if (resultencryptionkey == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no encryptionkey found.");
		}
		
		RoocConfig resultbearerkey = new RoocConfig();
		resultbearerkey = rcservice.findByconfigkey(tenantID,"bearerkey");

		if (resultbearerkey == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no bearerkey found.");
		}

		//request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		//request.setHeader("ClientID", ""+resultclientid.getConfigvalue());
		//request.setHeader("ApplicationID", ""+resultappid.getConfigvalue());
		//request.setHeader("Authorization", "Beaerer " + ""+Base64.encodeBase64(resultencryptionkey.getConfigvalue().getBytes()));
		request.setHeader("Authorization", "Bearer "+resultbearerkey.getConfigvalue());
		request.setHeader(""+resultmybcaapp.getConfigkey(), ""+resultmybcaapp.getConfigvalue());

		//request.setHeader("Accept", "application/json;charset=UTF-8");
		//request.setHeader("X-Stream" , "true");

		HttpResponse response = httpclient.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + url);

		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());
		
		if(response.getStatusLine().getStatusCode() == 400)
		{
			throw new CoreException(HttpStatus.NOT_FOUND, "no data found.");
		}
		
		if(response.getStatusLine().getStatusCode() == 404)
		{
			throw new CoreException(HttpStatus.NOT_FOUND, "no data found.");
		}

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}


		return result.toString();
	}


	public String getGetdatatokenbca(String Urlparam,String tenantID) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException
	{

		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build());
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
				sslsf).build();

		RoocConfig resultldapgetapi = new RoocConfig();
		resultldapgetapi = rcservice.findByconfigkey(tenantID,"tokenbcaurlip");

		if (resultldapgetapi == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no get tokenbcaurlip found.");
		}

		RoocConfig resultgeturl = new RoocConfig();
		resultgeturl = rcservice.findByconfigkey(tenantID,"tokenbcaurl");

		if (resultgeturl == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no get tokenbcaurl found.");
		}

		//HttpClient client = HttpClientBuilder.create().build();
		String url = resultldapgetapi.getConfigvalue()+""+resultgeturl.getConfigvalue()+""+Urlparam;
		HttpGet request = new HttpGet(url);


		RoocConfig resultmybcaapp = new RoocConfig();
		resultmybcaapp = rcservice.findByconfigkey(tenantID,"X-MYBCA-APPLICATION");

		if (resultmybcaapp == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no mybcaapp found.");
		}

		
		
		RoocConfig resultbearerkey = new RoocConfig();
		resultbearerkey = rcservice.findByconfigkey(tenantID,"bearerkeytoken");

		if (resultbearerkey == null) {
			throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no bearerkey found.");
		}

		//request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		//request.setHeader("ClientID", ""+resultclientid.getConfigvalue());
		//request.setHeader("ApplicationID", ""+resultappid.getConfigvalue());
		//request.setHeader("Authorization", "Beaerer " + ""+Base64.encodeBase64(resultencryptionkey.getConfigvalue().getBytes()));
		request.setHeader("Authorization", "Bearer "+resultbearerkey.getConfigvalue());
		request.setHeader(""+resultmybcaapp.getConfigkey(), ""+resultmybcaapp.getConfigvalue());

		//request.setHeader("Accept", "application/json;charset=UTF-8");
		//request.setHeader("X-Stream" , "true");

		HttpResponse response = httpclient.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + url);

		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() == 404)
		{
			throw new CoreException(HttpStatus.NOT_FOUND, "url/data not found.");
		}

		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}


		return result.toString();
	}


	

}
