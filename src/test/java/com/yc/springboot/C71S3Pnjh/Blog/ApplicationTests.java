package com.yc.springboot.C71S3Pnjh.Blog;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import com.yc.springboot.C71S3Pnjh.Blog.bean.User;
import com.yc.springboot.C71S3Pnjh.Blog.bean.UserExample;
import com.yc.springboot.C71S3Pnjh.Blog.biz.BizException;
import com.yc.springboot.C71S3Pnjh.Blog.biz.LogBiz;
import com.yc.springboot.C71S3Pnjh.Blog.biz.UserBiz;
import com.yc.springboot.C71S3Pnjh.Blog.dao.LogMapper;
import com.yc.springboot.C71S3Pnjh.Blog.dao.UserMapper;

@SpringBootTest
class ApplicationTests {

	@Resource
	private UserMapper um;
	@Resource
	private UserBiz ubiz;
	@Resource
	private LogBiz lbiz;

	@Test
	void contextLoads() {
		User user=new User();
		user.setAccount("root");
		user.setPwd("123");
		//模拟查询参数
		/*UserExample example=new UserExample();
		example.createCriteria().andAccountEqualTo(user.getAccount())
	.andPwdEqualTo(user.getPwd());*/
		//模拟返回结果
		List<User> ret=new ArrayList<>();
		ret.add(user);
		//打桩(模拟方法的返回值)
		//当执行该方法时，mock对象将会返回ret
		Mockito.when(um.selectByExample(Mockito.any())).thenReturn(ret);
		
		//模拟异常抛出
		Mockito.when(lbiz.log2(null)).thenThrow(new NullPointerException("空值异常"));
		//执行测试方法
		try {
			User dbuser=ubiz.login(user);
			Assert.isTrue(dbuser!=null,"用户对象为空");
			Assert.isTrue(dbuser.getAccount().equals("root"),"用户名不正确");
			Assert.isTrue(dbuser.getPwd().equals("123"),"密码不正确");
			
			//行为判断
			//判断 um 对象一定执行过一次selectByExample
			Mockito.verify(um).selectByExample(Mockito.any());
			//验证方法调用2次
			//最少不能低于5次
			Mockito.verify(lbiz, Mockito.atLeast(5)).log(Mockito.any());
			//最多不能超过8次
			Mockito.verify(lbiz, Mockito.atMost(8)).log1(Mockito.any());
			try{
			ubiz.login(null);	
			
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}catch (BizException e){
			e.printStackTrace();
		}
	}
	
	
	//springBoot 将会在测试方法运行时，将Mock虚拟的LogMapper 注入到LogBiz中
	/*@MockBean
	private LogMapper logMapper;
	
	@Resource
	private LogBiz logBiz;
	
	@Test
	void mockTest(){
		logBiz.log(new Object());
	}*/


}
