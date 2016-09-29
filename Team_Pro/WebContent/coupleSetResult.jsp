<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>타이틀을 입력하세요</title>
</head>
<body>
<% String temp = request.getParameter("state");
if(temp != null && temp.equals("1")) { session.setAttribute("coupleYN", "Y"); %>
매칭에 성공하였습니다. <a href="main.jsp">시작하기</a>
<%} else {%>
매칭에 실패하였습니다. 
<%} %>
</body>
</html>