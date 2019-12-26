<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<h3>找回密码</h3>
<form action="find" method="post">
 请输入用户名:<input type="text" name="account">
 请输入邮箱:<input type="text" id="email" name="to"><input type="button" value="获取验证码" onclick="send()">
请输入验证码:<input type="text" name="show">
请输入重置密码:<input type="text" name="newpwd">
<input type="submit" value="确定">
</form>
</body>

<script src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
  function send(){
	  var email=$("#email").text();
	  console.log(email);
  }
</script>
</html>