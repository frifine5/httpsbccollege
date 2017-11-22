<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>welcome page</title>

<style type="text/css">

a{
	color: red;
	background-color: blue;
}



</style>
<link rel="stylesheet" href="<%=basePath %>static/css/test.css" type="text/css" />

</head>
<body>
<h1>hello jsp</h1>
<br>
<hr>
<h1>这是主页</h1>
<a href="./login">登录页</a>
</body>
</html>