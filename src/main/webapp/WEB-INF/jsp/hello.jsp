<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>welcome page</title>
<link rel="stylesheet" type="text/css" href="/css/test.css">
<!-- <script type="text/javascript" src="/js/index.js"></script> -->


 <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap.min.js"></script>
<style type="text/css">

a{
	color: red;
	background-color: blue;
}

</style>

</head>
<body>
<h1>你好，世界！</h1>
<br>
<hr>
${hello }

<br>
<a href="./ok">push it</a>
<br/>

<form action="./put2" method="post">
	键：<input name="key" type="text" /><br/>
	值：<input name="value" type= "text"/><br/>
	<input type="submit" value="submit"/>
</form>
<hr>
${key }-${value }
<br/>
<a class="test" >Test for COLOR</a>
<form action="./put3" method="post">
	键：<input name="key3" type="text" /><br/>
	值：<input name="value3" type= "text"/><br/>
	<input type="submit" value="submit"/>
</form>
${key3 }-${value3 }
</body>
</html>