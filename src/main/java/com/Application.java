package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 啟用排程器
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
