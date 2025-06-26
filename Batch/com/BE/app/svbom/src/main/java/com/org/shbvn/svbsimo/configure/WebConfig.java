/**
 * 
 */
package com.org.shbvn.svbsimo.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	SimoInterceptor omsInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(omsInterceptor);
	}
	
	/*
	 * @Bean public Jackson2ObjectMapperBuilderCustomizer init() { return new
	 * Jackson2ObjectMapperBuilderCustomizer() {
	 * 
	 * @Override public void customize(Jackson2ObjectMapperBuilder builder) {
	 * builder.simpleDateFormat("dd/MM/yyyy");
	 * builder.timeZone(TimeZone.getTimeZone("GMT+07:00")); } }; }
	 */

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
			.allowedMethods("GET, POST, PATCH, DELETE, OPTIONS").allowedHeaders("*")
			.allowCredentials(false).maxAge(3600);
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*")
//					.allowedMethods("GET, POST, PUT, DELETE, OPTIONS").allowedHeaders("*");
//			}
//		};
//	}

}
