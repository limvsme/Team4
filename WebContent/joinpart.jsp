<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>회원가입 :: 2016 아카데미</title>
<link type="text/css" rel="stylesheet" href="css/common.css">
<!-- ajax.js 라이브러리 설정 -->
<script type="text/javascript" src="js/ajax.js"></script>
<style type="text/css">
#bgco{
	background : white;
	opacity: 0.9;
}
</style>
<!-- 자바스크립트구현 -->
<script type="text/javascript">
	window.onload = start;
	function start() {
		document.getElementById("userId").focus();
		document.getElementById("btnJoin").setAttribute("disabled", true);
	}
	/*아이디 중복 체크 비동기식*/
	function isUserId() {

		//web.xml
		var url = "Controller";
		var userId = document.getElementById("userId").value;
		//params:action , userId , responseDataType		
		//params: key=value&value&key=value
		var params = "";
		params += "action=idCheck";
		params += "&userId=" + userId;
		//응답데이터 타입 = plaintext
		//params +="&responseText=text";
		//console.log('params ->',params);

		//응답데이터 타입 = XML
		//params += "&responseText=xml"
		//console.log('params ->', params);
		
		//응답데이터 타입 =json
		params += "&responseText=json"
		var callback = responseJson;
		method = "GET";
		//js/ajax.js 스크립트이용해서 ajax 서버요청
		new ajax.xhr.Request(url, params, callback, method)
	}

	/* 아이디중복 text 타입 응답 파싱처리함수
	--xhr : ajax.js */
	function responseText(xhr) {
		var message = document.getElementById("idmessage");
		console.log('xhr:', xhr);
		if (xhr.readyState == 4 && xhr.status == 200) {
			var result = xhr.responseText;//응답결과가져옴
			console.log('result:', result);
			if (result == 'true') {
				//messageUserId 결과출력하게끔
				addClass(message, 'w3-text-red');
				message.innerHTML = "사용불가아이디";
			} else if (result == 'false') {
				removeClass(message, 'w3-text-red');
				addClass(message, 'w3-text-green');
				message.innerHTML = "사용가능아이디";
			} else if (result == 'required') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "미입력";
			} else if (result == 'deny') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "사용거부아이디";
			}
		}
	}
	/**xml 응답데이터 처리 callback 함수
		<valid>true</valid>
	 */
	function responseXmlDoc(xhr) {
		var message = document.getElementById("idmessage");
		console.log('xhr:', xhr);
		if (xhr.readyState == 4 && xhr.status == 200) {

			var resultXmlDoc = xhr.responseXML;//응답결과가져옴

			var result = resultXmlDoc.getElementsByTagName("valid").item(0).firstChild.nodeValue;

			console.log('result:', result);
			if (result == 'true') {
				//messageUserId 결과출력하게끔
				addClass(message, 'w3-text-red');
				message.innerHTML = "사용불가아이디";
			} else if (result == 'false') {
				removeClass(message, 'w3-text-red');
				addClass(message, 'w3-text-green');
				message.innerHTML = "사용가능아이디";
			} else if (result == 'required') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "미입력";
			} else if (result == 'deny') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "사용거부아이디";
			}
		}
	}
	 /*
	 eval("("+json형식의 응답문자열+")")
	 */
	function responseJson(xhr) {
		var message = document.getElementById("idmessage");
		console.log('xhr:', xhr);
		if (xhr.readyState == 4 && xhr.status == 200) {
			var resultJson =eval("("+xhr.responseText+")");// json형식의 문자열을 json객체로 바꿔줘
			console.log("resultJson:", resultJson);
			
			var result = resultJson.valid;
			console.log('result:', result);
			if (result == 'true') {
				//messageUserId 결과출력하게끔
				addClass(message, 'w3-text-red');
				message.innerHTML = "사용불가아이디";
				document.getElementById("btnJoin").setAttribute("disabled", true);
			} else if (result == 'false') {
				removeClass(message, 'w3-text-red');
				addClass(message, 'w3-text-green');
				message.innerHTML = "사용가능아이디";
				document.getElementById("btnJoin").setAttribute("disabled");
			} else if (result == 'required') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "미입력";
				document.getElementById("btnJoin").setAttribute("disabled", true);
			} else if (result == 'deny') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "사용거부아이디";
				document.getElementById("btnJoin").setAttribute("disabled", true);
			}
		}
	}
	/* 아이디키입력시 초기화*/
	function clearUserId() {
		document.getElementById("idmessage").innerHTML = "";
	}

	function showHelp() {
		var help = "비밀번호는 영문자,숫자 조합으로 16자리 입력해주세요"
		document.getElementById("pwmessage").innerHTML = help;
	}

	function showUserPw() {
		if (document.getElementById("pwCheckbox").checked) {
			document.getElementById("userPw").setAttribute("type", "text");
			document.getElementById("userPwCheck").setAttribute("type", "text");
		} else {
			document.getElementById("userPw").setAttribute("type", "password");
			document.getElementById("userPwCheck").setAttribute("type",
					"password");
		}
	}

	/* function addressFocus() {
		var id = document.getElementById("userId");
		var pw = document.getElementById("userPw");
		var pw2 = document.getElementById("userPwCheck");
		var userName = document.getElementById("userName");
		if (id.value == "") {
			id.focus();
			addClass(id, 'w3-border-red');
		} else if (pw.value == "") {
			pw.focus();
		} else if (pw2.value == "") {
			pw2.focus();
		} else if (userName.value == "") {
			userName.focus();
		}
	} */
	function buttonEnable() {
		if (document.getElementById("userName").value.length > 1) {
			document.getElementById("btnJoin").removeAttribute("disabled");
		} else if (document.getElementById("userName").value.length < 1) {
			document.getElementById("btnJoin").setAttribute("disabled", true);
		}
	}
	function pwEqual() {
		var pw1 = document.getElementById("userPw");
		var pw2 = document.getElementById("userPwCheck");
		var message = document.getElementById("pwCheckMessage");
		if (pw1.value == pw2.value) {
			message.innerHTML = "비밀번호 일치";
			removeClass(message, 'w3-text-red');
			addClass(message, 'w3-text-green');

		} else {
			addClass(message, 'w3-text-red');
			message.innerHTML = "비밀번호 불일치";

		}
	}

	function hasClass(el, className) {
		if (el.classList)
			return el.classList.contains(className)
		else
			return !!el.className.match(new RegExp('(\\s|^)' + className
					+ '(\\s|$)'))
	}

	function addClass(el, className) {
		if (el.classList)
			el.classList.add(className)
		else if (!hasClass(el, className))
			el.className += " " + className
	}

	function removeClass(el, className) {
		if (el.classList)
			el.classList.remove(className)
		else if (hasClass(el, className)) {
			var reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
			el.className = el.className.replace(reg, ' ')
		}
	}
	
</script>
</head>
<body>

	<form id="bgco" class="w3-container w3-card-4" method="post"
		action="Controller?action=join" style="width: 55%; margin: 0 auto;">
		<h2 class="w3-text-black">회원가입</h2>

		<p>
			<label class="w3-text-black"><b>*아이디</b></label> <input
				class="w3-input w3-border" name="userId" id="userId" type="text"
				autocomplete="off" onblur="isUserId()">
		<p id="idmessage" class="w3-text-blue"></p>

		<p>
         <label class="w3-text-black"><b>*비밀번호</b></label> <input
            class="w3-input w3-border" name="userPw" id="userPw" type="password"
             onkeyup="pwEqual()">
      </p>
      <p>
         <input class="w3-btn w3-black" type="checkbox" id="pwCheckbox"
            onclick="showUserPw()"><label class="w3-text-black">비밀번호표시</label> <span id="pwmessage"
            class="w3-text-blue"></span>
      </p>
		<p>
			<label class="w3-text-black"><b>비밀번호재확인</b></label> <input
				class="w3-input w3-border" id="userPwCheck" type="password"
				onkeyup="pwEqual()">
		</p>
		<p id="pwCheckMessage" class="w3-text-blue"></p>
		<p>
			<label class="w3-text-black"><b>*이름</b></label> <input
				class="w3-input w3-border" name="userName" id="userName" type="text" 
				onfocus="addressFocus()" onkeyup="buttonEnable()">
		</p>

		<p>
			<button class="w3-btn w3-black" type="submit" id="btnJoin">등록</button>
			<button class="w3-btn w3-black" id="btnReset">취소</button>
		</p>
	</form>

</body>
</html>