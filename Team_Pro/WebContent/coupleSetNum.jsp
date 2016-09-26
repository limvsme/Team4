<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>타이틀을 입력하세요</title>
</head>
<body>
<form action="Controller" method="post">
<input type="hidden" name="action" value="coupleSetNum">
<input type="text" name="coupleNo" placeholder="커플번호">
<input type="text" name="confirmNo" placeholder="인증번호">
<input type="text" name="coupleName" placeholder="커플 이름">
<button>전송</button>
</form>
</body>
</html>