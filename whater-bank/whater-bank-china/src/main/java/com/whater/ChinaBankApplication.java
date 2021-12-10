package com.whater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
public class ChinaBankApplication {
	public static void main(String[] args) {
//		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(ChinaBankApplication.class, args);
	}
}