package com.yc.springboot.C71S3Pnjh.Blog.web;

import java.util.Random;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import com.yc.springboot.C71S3Pnjh.Blog.bean.User;
import com.yc.springboot.C71S3Pnjh.Blog.bean.UserExample;
import com.yc.springboot.C71S3Pnjh.Blog.biz.BizException;
import com.yc.springboot.C71S3Pnjh.Blog.biz.UserBiz;
import com.yc.springboot.C71S3Pnjh.Blog.dao.UserMapper;
import com.yc.springboot.C71S3Pnjh.Blog.vo.Result;

@RestController
@SessionAttributes({"loginedUser","yanz"})
public class UserAction {

	@Resource
	private UserBiz ubiz;
	@Resource
	private UserMapper um;

	@PostMapping("login")
	public Result login(@Valid User user, Errors errors, Model m) {
		try {
			if (errors.hasErrors()) {
				return new Result(2, "表单验证错误", errors.getFieldErrors());
			}
			user = ubiz.login(user);
			m.addAttribute("loginedUser", user);
			return new Result(1, "登陆成功", user);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(0, e.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
			return new Result(0, "业务繁忙");

		}
	}

	@Autowired
	private JavaMailSender mailSender;
	@Value("${mail.fromMail.addr}")
	private String from;

	@PostMapping("sendEmail")
	public String sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
		return "success";

	}

	
	private String code;
	@PostMapping("send")
	public Result send2(User user,Model m) throws BizException {
		 User user1 = ubiz.send(user);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(user1.getEmail());
		message.setSubject("您的验证码");
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		code=sb.toString();
		//System.out.println();
		message.setText(code);
		mailSender.send(message);
		return new Result(1,"验证码已发送");
	}
	
	public String yanz(){
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	@PostMapping("send1")
	public String send1( String email)  {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(email);
		message.setSubject("您的验证码");
		String ma=yanz();
		message.setText(ma);
		mailSender.send(message);
		return "success";
	}
	
	@PostMapping("find")
	public Result findpwd(User user,String account,String show,String pwd,Model m) throws BizException{
		user.setPwd(pwd);
		UserExample ue=new UserExample();
		com.yc.springboot.C71S3Pnjh.Blog.bean.UserExample.Criteria ct=ue.createCriteria();
		ct.andAccountEqualTo(account);
		System.out.println(pwd);
		System.out.println(account);
		if(show.equals(code)){
			um.updateByExampleSelective(user, ue);
			return new Result(1, "修改成功");
		}else{
			return new Result(0, "修改失败");
		}
		
	}
}
