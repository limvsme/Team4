<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Ÿ��Ʋ�� �Է��ϼ���</title>
</head>
<body>
<%= session.getAttribute("userId") %>
<%= request.getAttribute("coupleNo") %>
<%= request.getAttribute("confirmNo") %>
 
</body>
</html>