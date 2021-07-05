package com.egeroo.roocvthree;










/*import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;*/

/*import org.json.JSONArray;
import org.json.JSONObject;*/
/*import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import com.egeroo.roocvthree.core.util.PasswordGenerator;





@SpringBootApplication
@ComponentScan({"com.egeroo.roocvthree"})
@EnableAutoConfiguration (exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })

public class ComposerApplication extends SpringBootServletInitializer{
	
	//@Autowired
   // private static InteractionService service;

	public static void main(String[] args)throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(ComposerApplication.class, args);
		System.out.println("Version : 20_03_2021.1018.01.RooCEngine");	
		
		
		
		/*
		List<Integer> temps = new ArrayList<Integer>();
		
		temps.add(1);
		temps.add(10);
		temps.add(123);
		temps.add(321);
		temps.add(11);
		
		
		Integer[] tempsArray = temps.toArray(new Integer[0]);
		if(tempsArray.length>5)
			System.out.print("temp length is : "+tempsArray.length);
		
		JSONObject data= new JSONObject();
		for (Integer s : tempsArray) {
	      System.out.println("Data is : " + s);
	      data.append("id", s);
	    }
		System.out.print(data);
		
		*/
		
		
		/*
		Date date = new Date();
		String formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);
		//Timestamp timestamp = new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(formattedDate).getTime());
		//Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
		//System.out.print("ts : "+timestamp);
		
		String text = "2020-06-16 22:22:00";
		Timestamp ts = Timestamp.valueOf(text);
		System.out.println(ts);
		
		String utcTimestamp = "2020-05-25 08:03:24";
	    // parse the datetime as it is to an object that only knows date and time (no zone)
	    LocalDateTime datetimeWithoutZone = LocalDateTime.parse(utcTimestamp,
	                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    // convert it to a zone-aware datetime object by adding a zone
	    ZonedDateTime utcZdt = datetimeWithoutZone.atZone(ZoneId.of("UTC"));
	    // print the datetime in utc once
	    System.out.println(utcZdt);
	    // then convert the zoned datetime to a different time zone
	    ZonedDateTime asiaJakartaZdt = utcZdt.withZoneSameInstant(ZoneId.of("Asia/Jakarta"));
	    // and print the result
	    System.out.println(asiaJakartaZdt);
		*/
		
		
		//System.out.format("String for password \t\t\t: %s%n", PASSWORD_ALLOW_BASE);
        //System.out.format("String for password (shuffle) \t: %s%n%n", PASSWORD_ALLOW);

        // generate 5 random password
		//PasswordGenerator pg = new PasswordGenerator();
        //for (int i = 0; i < 1; i++) {
            //System.out.println("password : " + pg.generateRandomPassword(8));
            //System.out.println("\n");
        //}
		
		/*Util util = new Util();
		System.out.print("Number is : "+ util.isInteger("123abc"));
		*/
		
		/*JSONObject postdata = new JSONObject();
        
		postdata.put("category", 1);
		
        postdata.put("access_token", "abc");
		postdata.put("id", 2000);
		//postdata.put("categoryid", retparID);
		postdata.put("question", "hallo");
		postdata.put("answer", "hallo apa kabar?");
		//postdata.put("answer", ".");
		
		postdata.put("category", 2);
		
		System.out.print("postdata is : " + postdata);
		// result is : postdata is : look down
		// {"access_token":"abc","question":"hallo","answer":"hallo apa kabar?","id":2000,"category":2}
		
		JSONArray jaml = new JSONArray();
		jaml.put(postdata);
		
		System.out.print("ja postdata 1 is : " + jaml);
		
		postdata.put("category", 3);
		jaml.put(postdata);
		System.out.print("ja postdata 2 is : " + jaml);*/
		
		// result is : ja postdata is : : look down
		// [{"access_token":"abc","question":"hallo","answer":"hallo apa kabar?","id":2000,"category":2}]
		
		
		
		
		/*String expire = "2019-10-01 21:57:27";
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
		String inputString1 = "23 01 1997";
		String inputString2 = "2019-10-01 18:46:27";

		try {
		    Date date1 = (Date) myFormat.parse(expire);
		    Date date2 = new Date(System.currentTimeMillis());//(Date) myFormat.parse(inputString2);
		    System.out.println("Expire date is : " + date1);
		    System.out.println("Current date is : " + myFormat.format(date2));
		    long diff = date1.getTime() - date2.getTime();
		    
		    long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
		    
		    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		*/
		
		/*String phonenumber ="whatsapp2:+628121091616";
		phonenumber = phonenumber.replace("whatsapp2", "");
		phonenumber = phonenumber.replace("whatsapp", "");
		phonenumber = phonenumber.replace("roocvthree", "");
		phonenumber = phonenumber.replace(":", "");
		
		System.out.print("phone number is :" + phonenumber);*/
		
		
		/*JSONObject jo = new JSONObject();
		jo.put("firstname", "jack");
		jo.put("lastname", "sparrow");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("firstname", "sparrow");
		jo2.put("lastname", "jack");
		
		JSONArray ja = new JSONArray();
		ja.put(jo);
		ja.put(jo2);
		
		
		JSONObject mo = new JSONObject();
		mo.put("people", ja);
		
		System.out.print(mo);
		*/
		
		/*
		//this works from here
		Util td= new Util();

        String target="Bca123!";
        //byte[] myEncryptionKey = "123456789013245678901234".getBytes();
        byte[] encrypted=td.doEncryption(target,"123456789013245678901234");
        String byte2hex = td.bytesToHex(encrypted);
        byte[] hex2Byte = td.hexToBytes(byte2hex);
        String decrypted=td.doDecryption(encrypted);
        String decrypted_byte=td.doDecryption(hex2Byte);

        //String e3d = td.encrypt3Des(target);
        //String d3d = td.decrypt3Des(e3d);
        
        //byte[] enc3d = td.encTripleDes(target,myEncryptionKey);
        //byte[] dec3d = td.uncTripleDes(enc3d,myEncryptionKey);
        //String byte2hex_2 = td.bytesToHex(enc3d);
        //byte[] hex2Byte_2 = td.hexToBytes(byte2hex_2);
        
        //byte[] decbytefromhex = td.uncTripleDes(hex2Byte,myEncryptionKey);
        
        System.out.println("String To Encrypt: "+ target);
        System.out.println("Encrypted String:" + encrypted);
        System.out.println("Byte to hex _1:" + byte2hex);
        System.out.println("hex to byte _1:" + hex2Byte);
        System.out.println("Decrypted String:" + decrypted);
        System.out.println("Decrypted Byte:" + decrypted_byte);
        
        String byteshex = "0549c92a7e218d69";
        byte[] hex2Byte_2 = td.hexToBytes(byteshex);
        String decrypted_byte_only=td.doDecryptiononly(hex2Byte_2,"123456789013245678901234");
        System.out.println("Decrypted Byte only:" + decrypted_byte_only);
        //this works to here
        */
        
        
        //System.out.println("3DEnc Byte:" + enc3d);
        //System.out.println("Byte to hex _2:" + byte2hex_2);
        //System.out.println("3Ddecr Byte:" + dec3d);
        
        //System.out.println("Hex to Byte_2:" + hex2Byte_2);
        //System.out.println("3Ddecr byte from hex:" + decbytefromhex);
        //System.out.println("hex 3DDecrypted String:" + td.bytesToHex(enc3d));
        //System.out.println("3DEncrypted String:" + e3d);
        //System.out.println("3DEncrypted String to hex :" + td.bytesToHex(e3d.getBytes("UTF-8")));
        //System.out.println("3DDecrypted String:" + d3d);
        
        //System.out.println("enc3d String:" + td.bytesToHex(enc3d));
        //System.out.println("dec3d String:" + td.hexToBytes(d3d));
        
        /*DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date datefobj = new Date();
        System.out.println("Date from : " +df.format(datefobj));
        Date datefrom=new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(df.format(datefobj));  
        System.out.println("Date from X : " +datefrom);
        
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Date datetobj = new Date();
        System.out.println("Date to : "+dt.format(datetobj));
        Date dateto=dt.parse(dt.format(datetobj));
        System.out.println("Date to X : " +dateto);
        
        String sDate6 = "2019-12-31 23:37:50";  
        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date6=formatter6.parse(sDate6);  
        System.out.println(sDate6+"\t"+date6);  
        
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        System.out.println("Date today : " +today);
        System.out.println("Date tomorrow : " +tomorrow);*/
        
        //service.getfindCountinteractionperday("roocvthree",today,tomorrow,1);
		
		//String link = "http://103.108.126.167:25000/api/category/get/ROOC2->Janji - Bayar";
		
		

		
		
		/* String hprret = " {“ErrorSchema” : \n" + 
				"		{“ErrorCode”:”ESB-00-000”,\n" + 
				"		”ErrorMessage”:\n" + 
				"			{“Indonesian”:”Berhasil”,”English”:”Success”},\n" + 
				"		},\n" + 
				"		”OutputSchema”:{“Status”:”0”}\n" + 
				"	} "; */
		/*
		String hprret = " { \n" + 
				"	'ErrorSchema' :  {	\n" + 
				"				'ErrorCode':'ESB-00-000',\n" + 
				"				'ErrorMessage': {'Indonesian':'Berhasil','English':'Success'},\n" + 
				"		},\n" + 
				"		'OutputSchema':{'Status':'0'}\n" + 
				" }  ";
		
		System.out.println(hprret);
		
		//String prettyJsonString ="";
		//JsonParser jp = new JsonParser();
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//JsonElement je = jp.parse(hprret);
		//prettyJsonString = gson.toJson(je);
		//System.out.println(prettyJsonString);
		
		
		//String jsonString = prettyJsonString;
	    JSONObject jsonObject = new JSONObject(hprret);
	    
	    
	    
	    //System.out.println(jsonString);
	    
	    if(!jsonObject.has("OutputSchema"))
		{
			System.out.println("! Schema");
		}
	    else
	    {
	    	System.out.println("Has Schema");
	    	JSONObject jsonObjectins = jsonObject.getJSONObject("OutputSchema");
	    	if(jsonObjectins.has("Status"))
	    	{
	    		System.out.println("Has Status");
	    		System.out.println(jsonObjectins.getString("Status"));
	    	}
	    	else
	    	{
	    		System.out.println("No Status");
	    	}
	    }
	    */
		
		/*
		 ini contoh get
		String hppret =" {\n" + 
				"	\"output_schema\" : [\n" + 
				"		{\n" + 
				"			\"domain_id\" : U052368,\n" + 
				"			\"display_name\" : \"Albert Cony Pramudita\"\n" + 
				"		}\n" + 
				"	]\n" + 
				"} " ;
		System.out.println(hppret);
		
		JSONObject jsonObject = new JSONObject(hppret);
	    
	    
	    
	    //System.out.println(jsonString);
	    
	    if(!jsonObject.has("output_schema"))
		{
			System.out.println("! Schema");
		}
	    else
	    {
	    	System.out.println("Has Schema");
	    	
	    	JSONArray jsonarray = jsonObject.getJSONArray("output_schema");
	    	for(int i = 0; i < jsonarray .length(); i++)
	    	{
	    	   JSONObject object3 = jsonarray.getJSONObject(i);
	    	   String domain_id = object3.getString("domain_id");
	    	  
	    	   System.out.println(domain_id);	
	    	}
	    }
	    */
		
		/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date datefobj = new Date();
        System.out.println("Date from : " +df.format(datefobj));
        Date datefrom=new SimpleDateFormat("yyyy-MM-dd 00:00:00").parse(df.format(datefobj));  
        System.out.println("Date from X : " +datefrom);
        
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Date datetobj = new Date();
        System.out.println("Date to : "+dt.format(datetobj));
        Date dateto=new SimpleDateFormat("yyyy-MM-dd 23:59:59").parse(dt.format(datetobj));  
        System.out.println("Date to X : " +dateto);
        
        Date start = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		Date end = DateUtils.addDays(start, 1);
		System.out.print("Start : "+start);
		System.out.print("End : "+end);*/
		
		//Creating user defined class objects  
		/*
		TrainID s1=new TrainID(1,4,13);  
		TrainID s2=new TrainID(2,5,14);  
		TrainID s3=new TrainID(3,6,15); 

        ArrayList<TrainID> al=new ArrayList<TrainID>();
        al.add(s1);
        al.add(s2);  
        al.add(s3);  

        Iterator itr=al.iterator();  

        //traverse elements of ArrayList object  
        while(itr.hasNext()){  
        	TrainID st=(TrainID)itr.next();  
            System.out.println(st.logdataid+" "+st.intrid+" "+st.iretresid);  
        }  */
	    
	}
	
	/*class TrainID{  
	    int logdataid;  
	    int intrid;  
	    int iretresid;  
	    TrainID(int logdataid,int intrid,int iretresid){  
	        this.logdataid=logdataid;  
	        this.intrid=intrid;  
	        this.iretresid=iretresid;  
	    }  
	} */
	
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
	    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
	    validatorFactoryBean.setValidationMessageSource(messageSource);
	    return validatorFactoryBean;
	}
	
	
	
	
	/*@Bean
	 public MethodValidationPostProcessor methodValidationPostProcessor() {
	      return new MethodValidationPostProcessor();
	 }
	*/
	
	/*
	@Bean
	public FilterRegistrationBean corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	    bean.setOrder(0); 
	    return bean;
	}
	*/
	
	/*
		 @Bean
		 public WebMvcConfigurer corsConfigurer() {
		    return new WebMvcConfigurerAdapter() {
		        @Override
		        public void addCorsMappings(CorsRegistry registry) {
		            registry.addMapping("/**").allowedOrigins("http://localhost:3000");
		        }
		    };
		 }
	 
	 */
	
	
}
//{"roleid":1,"username":"arif.chohan","password":"123456","companyid":1,"name":"arif","address":"MARS","emailaddress":"arif.chohan85@gmail.com","createdBy":"1","updateBy":"1"}