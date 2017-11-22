<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%  String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
		+ request.getServerPort() + path + "/";
%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>welcome page</title>


<link rel="stylesheet" href="<%=basePath %>static/bootstrap/css/bootstrap.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>static/bootstrap/css/bootstrap-responsive.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>static/bootstrap/js/bootstrap.js"></script>

<link rel="stylesheet" href="<%=basePath %>static/css/auth.css" type="text/css" />




</head>
<body >
<div class="container" >
<div style="width:30%; text-align: center; margin: 0 auto;">
	<form class="form-signin" action="./registerCommit">       
      <h1 class="form-signin-heading">Please Register</h1>
      <input type="text" class="form-control" name="username" placeholder="Login Name"  autofocus=""/>
      <input type="password" class="form-control" name="password" placeholder="Password" />  
      <input type="password" class="form-control" name="repwd" placeholder="Confirm Password" />  
      <button class="btn btn-lg btn-primary btn-block" type="submit" style="width: 80px; margin: 0 auto; " >Submit</button> 
    </form>
</div>

</div>
</body>
</html>