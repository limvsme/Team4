<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<title>W3.CSS Template</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet"href="https://fonts.googleapis.com/css?family=Raleway">
<style>
body, h1 {
	font-family: "Raleway", sans-serif
}
body, html {
	height: 100%
}
.bgimg-1 {
	background-image: url("images/images (4).jpg");
	min-height: 100%;
	background-position: center;
	background-size: cover;
}
</style>
<body>
	<div class="bgimg-1 w3-display-container w3-animate-opacity w3-text-white">
	<div class="w3-display-topleft w3-padding-large w3-xlarge"></div>
	<center>
		<a href="Index.jsp">
		<img src="images/logo.png" 
		class="w3-margin w3-circle" alt="logo" style="width: 180px"></a>
	</center>
	<div class="w3-display-middle">
		<p class="w3-large w3-center">
	<div class=" w3-teal w3-container" style="height: 0px; width: 500px">
	<form class="w3-container w3-card-2 w3-padding-32 w3-white" 
		style="max-width: 560px; margin: auto; opacity: 0.8;" 
		name="loginForm" method="post" action="Controller">
		<input type="hidden" name="action" value="login">
		<div class="w3-group">
			<label>ID</label>
			<input class="w3-input" style="width: 100%;" 
			type="text" name="userId" placeholder=" 아이디를 입력해 주십시오." 
			required autofocus>
		</div>
		<div class="w3-group">
			<label>Password</label>
			<input class="w3-input" style="width: 100%;"
			 type="password" name="userPw" id="userPw" placeholder=" 암호를 입력해 주십시오." 
			 required>
		</div>
		<button type="submit" class="w3-btn w3-right w3-theme">로그인</button>
	</form> 
	</div>
	</div>
	<div class="w3-display-bottomleft w3-padding-large"></div>
	</div>
</body>
</html>