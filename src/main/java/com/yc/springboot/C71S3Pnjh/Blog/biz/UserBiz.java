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

	public User login(User user) throws BizException {
		UserExample example=new UserExample();
		example.createCriteria().andAccountEqualTo(user.getAccount())
	.andPwdEqualTo(user.getPwd());
		List<User> list=um.selectByExample(example);
		if(list.size()==0){
			throw new BizException("用户名或密码错误");
		}else{
			return list.get(0);
		}
	}

}
