<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Ÿ��Ʋ�� �Է��ϼ���</title>
</head>
<body>
<script type="text/javascript">
alert('<%= request.getAttribute("message") %>'); location.href='javascript:history.back(-1);';

</script>

</body>
</html>