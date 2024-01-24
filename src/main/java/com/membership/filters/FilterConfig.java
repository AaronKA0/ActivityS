package com.membership.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter(){
		FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new LoginFilter());
		registrationBean.addUrlPatterns("/membership/select_page");
		registrationBean.addUrlPatterns("/membership/listAllMembership");
		return registrationBean;
		
	}
}


//1.@Configuration 為註解該類別包含的bean
//2.@Bean 註解的方法將返回一個bean 並添加到上下文
//3.FilterRegistrationBean<LoginFilter> -->FilterRegistrationBean 是Spring提供的註冊過濾器的工具
//，允許他配置過濾器，並添加到URL
//4.loginFilter()方法 這個方法返回一個FilterRegistrationBean，其中包含配置的LoginFilter實例。
//addUrlPatterns方法設置過濾器將應用於的URL模式。