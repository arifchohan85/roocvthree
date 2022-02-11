package com.egeroo.roocvthree;


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


@SpringBootApplication
@ComponentScan({"com.egeroo.roocvthree"})
@EnableAutoConfiguration (exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })

public class ComposerApplication extends SpringBootServletInitializer{
	


	@SuppressWarnings("unused")
	public static void main(String[] args)throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(ComposerApplication.class, args);
		System.out.println("Version : 10_02_2022.2338.01.RooCEngine");	
	}
	
	
	
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
