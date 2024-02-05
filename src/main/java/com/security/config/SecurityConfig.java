package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// Spring Security的配置文件
@Configuration
@EnableWebSecurity
public class SecurityConfig {


	// 定義一個Bean，用於對密碼進行加密。使用BCryptPasswordEncoder。
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 定義一個Bean，用於配置SecurityFilterChain，該Bean負責處理HTTP請求的安全性。
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http	// 啟用cors並禁用csrf
				.cors().and().csrf().disable()
				// 配置請求授權的規則
				.authorizeRequests(authz -> authz
						// 指定某些請求路徑不需要驗證，這裡將/login對所有用戶開放

						.antMatchers("/back_end/01/**","/back_end/07/**","/back_end/10/**","/back_end/16/**", "/back-end/emp_Index/**", "/front-end/01/**", "/front-end/07/**", "/front-end/16/**", "/front-end/zuo-huo/**").permitAll()

						.antMatchers("/**","/Zuo-Huo/**","/front_end/notify/**","/front_end/venue/**","/act/**","/type/**","/notify/**","/front_end/**").permitAll()
						.antMatchers("/login","/membership/**","/faq/**","/memberreport/**","/postreport/**", "/back_end/announcement/**", "/back_end/notify/**", "/back_end/ven-order/**", "/back_end/ven-closed/**","/forgotPassword","/act/**","/type/**","/membership/**","/faq/**","/memberreport/**","/postreport/**","/","/Zuo-Huo","/activity/**","/back_end/**","/mem/**","/member/**","/mem/**","/venOrder/**","/ven/**","/FriendWS/**","/memReport/**").permitAll()

						.antMatchers("/feedbackform","/**","/Zuo-Huo/**","/front_end/notify/**","/front_end/venue/**","/act/**","/type/**","/notify/**","/front_end/**").permitAll()
						.antMatchers("/login","/membership/**","/faq/**","/memberreport/**","/postreport/**", "/back_end/announcement/**", "/back_end/notify/**", "/back_end/ven-order/**", "/back_end/ven-closed/**","/forgotPassword","/act/**","/type/**","/membership/**","/faq/**","/memberreport/**","/postreport/**","/","/Zuo-Huo","/activity/**","/back_end/**","/mem/**","/member/**","/mem/**","/venOrder/**","/ven/**","/FriendWS/**").permitAll()

						.antMatchers("/activity/**","/actreg/**","/actregs/**","/activityreport/**","/comments/**", "/comment/**", "/commentreport/**", "/back_end/ven-order/**", "/acts/**","/weather/*").permitAll()
						
						// 除了上述指定的路徑外，其他所有請求都需要被認證
						.anyRequest().authenticated())
				// 配置表單登入
				.formLogin(form -> form
						// 自定義登入頁面的URL
						.loginPage("/login")
						// 登入失敗後的重定向URL，帶有錯誤參數
						.failureUrl("/login?error=true")
						// 登入成功後的預設重定向URL，true代表總是重定向到這個URL
						.defaultSuccessUrl("/emp_Index", true)
						// 允許所有用戶訪問自定義的登入頁面
						.permitAll())
				// 配置登出功能
				.logout(logout -> logout.logoutUrl("/logout") // 登出路徑
						.logoutSuccessUrl("/login") // 登出成功後的重定向路徑
						.invalidateHttpSession(true) // 使 session 無效
						// 用戶登出時應該刪除名為JSESSIONID的cookies。
						// 防止舊的會話ID被重用或者遭受會話固定攻擊（Session Fixation Attack）。
						.deleteCookies("JSESSIONID") // 刪除 cookies
				);

		return http.build();
	}

}
