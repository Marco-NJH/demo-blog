package com.yc.springboot.C71S3Pnjh.Blog.biz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.springboot.C71S3Pnjh.Blog.bean.User;
import com.yc.springboot.C71S3Pnjh.Blog.bean.UserExample;
import com.yc.springboot.C71S3Pnjh.Blog.dao.UserMapper;

@Service
public class UserBiz {
	
	@Resource
	private UserMapper um;

	@Resource
	private LogBiz lbiz;
	
	public User login(User user) throws BizException {
		lbiz.log2(user);
		lbiz.log(user);
		UserExample example=new UserExample();
		example.createCriteria().andAccountEqualTo(user.getAccount())
	.andPwdEqualTo(user.getPwd());
		List<User> list=um.selectByExample(example);
		
		lbiz.log(user);
		lbiz.log(user);
		lbiz.log(user);
		lbiz.log(user);
		lbiz.log(user);
		
		lbiz.log1(user);
		lbiz.log1(user);
		lbiz.log1(user);
		lbiz.log1(user);
		lbiz.log1(user);
		lbiz.log1(user);
		lbiz.log1(user);
		if(list.size()==0){
			throw new BizException("用户名或密码错误");
		}else{
			return list.get(0);
		}
	}
	
	public User send(User user) throws BizException {
		UserExample example=new UserExample();
		example.createCriteria().andAccountEqualTo(user.getAccount())
		.andEmailEqualTo(user.getEmail());
		List<User> list2=um.selectByExample(example);
		if(list2.size()==0){
			throw new BizException("用户名或密码错误");
		}else{
			return list2.get(0);
		}
	}
	
	/*public String find(User user) throws BizException{
		UserExample example=new UserExample();
		example.createCriteria().andAccountEqualTo(user.getAccount());
	
		
	}*/

}
