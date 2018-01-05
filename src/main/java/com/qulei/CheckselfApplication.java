package com.qulei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.qulei.dao")
public class CheckselfApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckselfApplication.class, args);
	}
}
