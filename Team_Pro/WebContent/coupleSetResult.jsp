<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Ÿ��Ʋ�� �Է��ϼ���</title>
</head>
<body>
<% if(request.getParameter("state").equals("1")) { %>
��Ī�� �����Ͽ����ϴ�.
<%} else {%>
��Ī�� �����Ͽ����ϴ�. 
<%} %>
</body>
</html>