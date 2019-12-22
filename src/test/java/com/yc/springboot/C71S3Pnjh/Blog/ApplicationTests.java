package com.yc.springboot.C71S3Pnjh.Blog;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.yc.springboot.C71S3Pnjh.Blog.dao.UserMapper;


@SpringBootTest
class ApplicationTests {

	@Resource
	private UserMapper um;

	
	@Test
	void contextLoads() {
		System.out.println(um.selectByExample(null));
	}

}
