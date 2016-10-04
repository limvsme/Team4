<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>회원가입 - 2016 아카데미</title>
<style type="text/css">
input.normal {
   margin: 10px;
   height: 30px;
   width: 200px;
   font-size: 20px;
}
span.noraml {
   font-color: red;
   color: red;
}
</style>
<!-- ajax.js 자바스크립트 라이브러리 설정 -->
<script type="text/javascript" src="js/ajax.js"></script>

<!-- 자바스크립트 구현 -->
<script type="text/javascript">
   /* 1. 페이지 로드:
      --회원가입버튼 비활성화
      --아이디 포커스
      
    * 
    */
   window.onload = init;

   /* 화면 초기화 함수 */
   function init() {
      document.getElementById("userId").focus();
      document.getElementById("btnJoin").disabled = false;
   }

   /* 2 아이디 포커스 잃었을 때
   아이디 중복조회(비동기통신)
   아이디 중복오류, 아이디 사용가능, 아이디 필수입력오류
   아이디 사용가능 => 회원가입버튼 활성화
   (강희환고객님의 의견 : 필수항목 입력 후) 
   
   ajax.xhr.Request = function(url, params, callback, method){}
   new ajax.xhr.Request(url, params, callback, method)
   responseType=text 또는 xml 또는 json
   
    */
   function isUserid() {
      console.log('isUserid call')

      //web.xml
      var url = "Controller";

      // params : action, userid, responseDataType
      // params : key=value&key=value&key=value
      // responseDataType : text, xml, json
      var params = "";
      params += "action=idCheck";

      var userId = document.getElementById("userId").value;
      params += "&userId=" + userId;

      /*       // => 응답데이터 : plain text
       params += "&responseDataType=text";
       console.log("params : ", params);
       //응답위한 콜백함수 바인딩
       var callback = responseText; */

      // => 응답데이터 : xml
      params += "&responseDataType=json";
      console.log("params : " + params);
      //응답위한 콜백함수 바인딩
      var callback = responseText;
      // => 응답데이터 : json

      // 아규먼트 : js/ajax.js 스크립트에서 callbaack 메서드
      var method = "GET";

      new ajax.xhr.Request(url, params, callback, method);
   }

   /* 아이디 중복 text 타입 응답 파싱 처리 함수
      xhr : ajax.js에서 해당 요청을 처리를  위해 생성한 ajax 객체 */
   function responseText(xhr) {
      if (xhr.readyState == 4 && xhr.status == 200) {
         var result = xhr.responseText;
         console.log("result : " + result)
         if (result == 'true') {
            // messageUserid 결과메세지 출력
            document.getElementById("messageUserid").innerHTML = "중복 아이디"
         } else if (result == 'false') {
            document.getElementById("messageUserid").innerHTML = "사용가능 아이디"
         } else if (result == 'required') {
            document.getElementById("messageUserid").innerHTML = "미입력 아이디"
         } else if (result == 'deny') {
            document.getElementById("messageUserid").innerHTML = "허용불가 아이디"
         }
      }
   }
   /**
   eval() 문자열 수식 변환함수
   json 형식의 문자열을 json 객체 변환
   eval( "("+ json형식의 응답 문자열 +")" )
    */
   function responseJson(xhr) {
      if (xhr.readyState == 4 && xhr.status == 200) {
         var resultJson = eval("(" + xhr.responseText + ")");
         console.log("resultJson : " + resultJson)

         var result = resultJson.valid;
         console.log("result :" + result)

         if (result == 'true') {
            // messageUserid 결과메세지 출력
            document.getElementById("messageUserid").innerHTML = "중복 아이디"
         } else if (result == 'false') {
            document.getElementById("messageUserid").innerHTML = "사용가능 아이디"
         } else if (result == 'required') {
            document.getElementById("messageUserid").innerHTML = "미입력 아이디"
         } else if (result == 'deny') {
            document.getElementById("messageUserid").innerHTML = "허용불가 아이디"
         }
      }
   }

   function responseXmlDoc(xhr) {
      if (xhr.readyState == 4 && xhr.status == 200) {
         var resultXmlDoc = xhr.responseXML;
         console.log("resultXmlDoc : " + resultXmlDoc);

         var result = resultXmlDoc.getElementsByTagName("valid").item(0).childNodes[0].nodeValue;

         console.log("result : " + result)
         if (result == 'true') {
            // messageUserid 결과메세지 출력
            document.getElementById("messageUserid").innerHTML = "중복 아이디"
         } else if (result == 'false') {
            document.getElementById("messageUserid").innerHTML = "사용가능 아이디"
         } else if (result == 'required') {
            document.getElementById("messageUserid").innerHTML = "미입력 아이디"
         } else if (result == 'deny') {
            document.getElementById("messageUserid").innerHTML = "허용불가 아이디"
         }
      }
   }
   /* 3. 아이디 키입력시
   -아이디 중복메세지 초기화 */
   function clearUserid() {
      document.getElementById("messageUserid").innerHTML = "";
   }

   /*4. 비밀번호 포커스 왔을 떄
      --비밀번호 도움말 메세지 출력 */
   function showUserpw() {
      var help = "비밀번호는 영문자 시작, 영문/숫자/특수문자 조합 16자리 사용";
      document.getElementById('messageUserpw1').innerHTML = help;
   }

   /* 5 비밀번호 키 입력 시
   비밀번호 패턴 검증
   보안등급 : 취약/보통/우수/매우우수등... */

   /* 6 비밀번호 확인
   비밀번호와 비밀번호확인 일치/불일치 메세지 */
   function checkUserpw() {
      if (document.getElementById("userpw") != 0 && document.getElementById("userpw").value 
            == document.getElementById("userpwConfirm").value){
         document.getElementById('messageUserpw').innerHTML = '일치합니다.'
         
      } else {
         document.getElementById('messageUserpw').innerHTML = '불일치합니다.'
      }
         
   }

   /* 7. 비밀번호 표시
         비밀번호와 비밀번호 확인 보이이기/ 감추기 */
   function checkboxUserpw() {
      if (document.getElementById("isShow").checked) {
         document.getElementById("userPw").type = "text";
         document.getElementById("userpwConfirm").type = "text";
      } else {
         document.getElementById("userPw").type = "password";
         document.getElementById("userpwConfirm").type = "password";
      }
   }
   

   /*8. 주소 포커스 왔을 때
    필수항목입력 여부 검증 : 아이디, 비밀번호(비밀번호 확인), 이름
         필수항목 미입력시에는 해당 미입력항목 포커스 */
   function nameFocus() {
      if (document.getElementById("userId").value == "") {
         document.getElementById('messageName').innerHTML = "아이디는 필수입력항목입니다";
         document.getElementById("userId").focus();
      } else if (document.getElementById("userPw").value == "") {
         document.getElementById('messageName').innerHTML = "비밀번호는 필수입력항목입니다";
         document.getElementById("userPw").focus();
      } else if (document.getElementById("userpwConfirm").value == "") {
         document.getElementById('messageName').innerHTML = "비밀번호 확인은 필수입력항목입니다";
         document.getElementById("userpwConfirm").focus();
      } else if (document.getElementById("userName").value == "") {
         document.getElementById('messageName').innerHTML = "이름은 필수입력항목입니다";
         document.getElementById("userName").focus();
      } else {
         console.log(document.getElementById("userId").value);
         console.log(document.getElementById("userpw").value);
         document.getElementById("btnJoin").disabled = false;
      }
   }
</script>
</head>
<body>
   <h1>회원가입</h1>
   <hr>
   <br>
   <form name="inputForm" method="post" action="Controller?action=join">
      <div>
         <table>
            <tr>
               <td><span class="normal" style="color: red;">*</span>아이디</td>
               <td><input type="text" id="userId" name="userId"
                  class="normal" onkeyup="clearUserid()" onblur="isUserid()"></td>
               <td><span id="messageUserid"></span><br /></td>
            </tr>
            <tr>
               <td><span class="normal" style="color: red;">*</span>비밀번호</td>
               <td><input type="password" id="userpw" onchange="checkUserpw()" name="userpw"
                  class="normal" onfocus="showUserpw()"><br></td>
               <td><span id="messageUserpw1"></span><br /></td>
            </tr>
            <tr>
               <td colspan="3"><input type="checkbox" id="isShow"
                  name="isShow" onclick="checkboxUserpw()">비밀번호표시<br></td>
            </tr>
            <tr>
               <td><span class="normal" style="color: red;">*</span>비밀번호확인</td>
               <td><input type="password" id="userpwConfirm" onchange="checkUserpw()" name="userpwConfirm"
                  class="normal"></td>
               <td><span id="messageUserpw">비밀번호 입력</span><br /> <br /></td>
            </tr>
            <tr>
               <td><span class="normal" style="color: red;">*</span>이름</td>
               <td colspan="2"><input type="text" id="name" name="name"
                  class="normal" onfocus="nameFocus()"><br /></td>
            </tr>
                        <tr>
               <td colspan="3"><span id="messageName" style="color: red"><b></b></span><br />
                  <br /></td>
            </tr>
            <tr>
               <td colspan="3"><input type="submit" value="가입" id="btnJoin"
                  onclick="document.inputForm.submit()"> <input type="reset"
                  value="취소"></td>
            </tr>
         </table>
      </div>
   </form>
</body>
</html>