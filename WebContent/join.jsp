<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>W3.CSS Template</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script>
/** checkbox 클릭시 암호 보이기 기능 */
   function isShowUserPw() {
      if (document.getElementById("isShow").checked) {
         document.getElementById("userPw").type = "text";
      } else {
         document.getElementById("userPw").type = "password";
      }
   }
</script>
<style>
body, h1, h2, h3, h4, h5, h6 {
   font-family: "Lato", sans-serif;
}

body, html {
   height: 115%;
   color: #777;
   line-height: 1.8;
}

/* Create a Parallax Effect */
.bgimg-1, .bgimg-2, .bgimg-3 {
   opacity: 0.7;
   background-attachment: fixed;
   background-position: center;
   background-repeat: no-repeat;
   background-size: cover;
}

/* First image (Logo. Full height) */
.bgimg-1 {
   background-image: url("images/images (3).jpg");
   min-height: 100%;
}
</style>
<body>
   <!-- First Parallax Image zwith Logo Text -->
   <div class="bgimg-1 w3-opacity w3-display-container">
   <center>
      <a href ="Index.jsp">
      <img src="images/logo.png" class="w3-margin w3-circle" 
      alt="logo" style="width:180px"></a>
    </center>
   <div class="w3-display-middle" style="white-space: nowrap; width: 1500px">

   
   <!-- 회원가입 조각파일 -->
   <%@ include file="joinpart.jsp" %>
         
   </div>
   </div>
</body>
</html>