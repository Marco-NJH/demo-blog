package com.yc.springboot.C71S3Pnjh.Blog.web;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yc.springboot.C71S3Pnjh.Blog.bean.User;
import com.yc.springboot.C71S3Pnjh.Blog.biz.BizException;
import com.yc.springboot.C71S3Pnjh.Blog.biz.UserBiz;
import com.yc.springboot.C71S3Pnjh.Blog.vo.Result;

@RestController
@SessionAttributes("loginedUser")
public class UserAction {

	@Resource
	private UserBiz ubiz;
	
	
	@PostMapping("login")
	public Result login(@Valid User user, Errors errors,Model m){
		try {
			if(errors.hasErrors()){
				return new Result(2,"表单验证错误",errors.getFieldErrors());
			}
			 user=ubiz.login(user);
			m.addAttribute("loginedUser", user);
			return new Result(1,"登陆成功",user);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(0,e.getMessage());
		}catch(RuntimeException e){
			e.printStackTrace();
			return new Result(0, "业务繁忙");
			
		}
	}
	
	@Autowired
	private JavaMailSender mailSender;
	@Value("${mail.fromMail.addr}")
	private String from;
	
	@PostMapping("sendEmail")
	public String sendSimpleMail(String to,String subject,String content){
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
		return "success";
		
		
	}
}
