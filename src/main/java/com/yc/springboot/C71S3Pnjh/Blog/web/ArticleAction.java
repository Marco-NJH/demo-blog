package com.yc.springboot.C71S3Pnjh.Blog.web;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yc.springboot.C71S3Pnjh.Blog.bean.Article;
import com.yc.springboot.C71S3Pnjh.Blog.bean.User;
import com.yc.springboot.C71S3Pnjh.Blog.biz.ArticleBiz;
import com.yc.springboot.C71S3Pnjh.Blog.biz.UserBiz;
import com.yc.springboot.C71S3Pnjh.Blog.vo.Result;

@RestController
public class ArticleAction {

	@Value("${spring.resources.staticLocations}")
	private String uploadDir;
	@Resource
	private ArticleBiz abiz;

	@PostMapping("addArticle")
	public ModelAndView add(Article a,
			@SessionAttribute("loginedUser") User user,
			Errors errors,Model m) {
		a.setAuthor(user.getName());
		abiz.addArticle(a);
		//响应重定向
		return new ModelAndView("redirect:article?id="+a.getId());

	}

	@GetMapping("toaddArticle")
	public ModelAndView toadd() {
		return new ModelAndView("article_add");

	}
	
	@PostMapping("uploadImg")
	public String uploadImg(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException{
		String path=uploadDir.substring("file:/".length());
		File diskfile=new File(path+"/"+file.getOriginalFilename());
		file.transferTo(diskfile);
		return "success";
	}
}
