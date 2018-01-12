package com.qulei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.qulei.dao")
public class CheckselfApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckselfApplication.class, args);
	}
}
