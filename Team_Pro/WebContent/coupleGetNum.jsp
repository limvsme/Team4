<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>타이틀을 입력하세요</title>
</head>
<body>
<%= session.getAttribute("userId") %>
<%= request.getAttribute("coupleNo") %>
<%= request.getAttribute("confirmNo") %>
 
</body>
</html>