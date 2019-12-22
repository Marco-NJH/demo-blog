package com.yc.springboot.C71S3Pnjh.Blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yc.springboot.C71S3Pnjh")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
