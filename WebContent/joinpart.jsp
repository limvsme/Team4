<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>ȸ������ :: 2016 ��ī����</title>
<link type="text/css" rel="stylesheet" href="css/common.css">
<!-- ajax.js ���̺귯�� ���� -->
<script type="text/javascript" src="js/ajax.js"></script>
<style type="text/css">
#bgco{
	background : white;
	opacity: 0.9;
}
</style>
<!-- �ڹٽ�ũ��Ʈ���� -->
<script type="text/javascript">
	window.onload = start;
	function start() {
		document.getElementById("userId").focus();
		document.getElementById("btnJoin").setAttribute("disabled", true);
	}
	/*���̵� �ߺ� üũ �񵿱��*/
	function isUserId() {

		//web.xml
		var url = "Controller";
		var userId = document.getElementById("userId").value;
		//params:action , userId , responseDataType		
		//params: key=value&value&key=value
		var params = "";
		params += "action=idCheck";
		params += "&userId=" + userId;
		//���䵥���� Ÿ�� = plaintext
		//params +="&responseText=text";
		//console.log('params ->',params);

		//���䵥���� Ÿ�� = XML
		//params += "&responseText=xml"
		//console.log('params ->', params);
		
		//���䵥���� Ÿ�� =json
		params += "&responseText=json"
		var callback = responseJson;
		method = "GET";
		//js/ajax.js ��ũ��Ʈ�̿��ؼ� ajax ������û
		new ajax.xhr.Request(url, params, callback, method)
	}

	/* ���̵��ߺ� text Ÿ�� ���� �Ľ�ó���Լ�
	--xhr : ajax.js */
	function responseText(xhr) {
		var message = document.getElementById("idmessage");
		console.log('xhr:', xhr);
		if (xhr.readyState == 4 && xhr.status == 200) {
			var result = xhr.responseText;//������������
			console.log('result:', result);
			if (result == 'true') {
				//messageUserId �������ϰԲ�
				addClass(message, 'w3-text-red');
				message.innerHTML = "���Ұ����̵�";
			} else if (result == 'false') {
				removeClass(message, 'w3-text-red');
				addClass(message, 'w3-text-green');
				message.innerHTML = "��밡�ɾ��̵�";
			} else if (result == 'required') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "���Է�";
			} else if (result == 'deny') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "���źξ��̵�";
			}
		}
	}
	/**xml ���䵥���� ó�� callback �Լ�
		<valid>true</valid>
	 */
	function responseXmlDoc(xhr) {
		var message = document.getElementById("idmessage");
		console.log('xhr:', xhr);
		if (xhr.readyState == 4 && xhr.status == 200) {

			var resultXmlDoc = xhr.responseXML;//������������

			var result = resultXmlDoc.getElementsByTagName("valid").item(0).firstChild.nodeValue;

			console.log('result:', result);
			if (result == 'true') {
				//messageUserId �������ϰԲ�
				addClass(message, 'w3-text-red');
				message.innerHTML = "���Ұ����̵�";
			} else if (result == 'false') {
				removeClass(message, 'w3-text-red');
				addClass(message, 'w3-text-green');
				message.innerHTML = "��밡�ɾ��̵�";
			} else if (result == 'required') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "���Է�";
			} else if (result == 'deny') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "���źξ��̵�";
			}
		}
	}
	 /*
	 eval("("+json������ ���乮�ڿ�+")")
	 */
	function responseJson(xhr) {
		var message = document.getElementById("idmessage");
		console.log('xhr:', xhr);
		if (xhr.readyState == 4 && xhr.status == 200) {
			var resultJson =eval("("+xhr.responseText+")");// json������ ���ڿ��� json��ü�� �ٲ���
			console.log("resultJson:", resultJson);
			
			var result = resultJson.valid;
			console.log('result:', result);
			if (result == 'true') {
				//messageUserId �������ϰԲ�
				addClass(message, 'w3-text-red');
				message.innerHTML = "���Ұ����̵�";
				document.getElementById("btnJoin").setAttribute("disabled", true);
			} else if (result == 'false') {
				removeClass(message, 'w3-text-red');
				addClass(message, 'w3-text-green');
				message.innerHTML = "��밡�ɾ��̵�";
				document.getElementById("btnJoin").setAttribute("disabled");
			} else if (result == 'required') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "���Է�";
				document.getElementById("btnJoin").setAttribute("disabled", true);
			} else if (result == 'deny') {
				addClass(message, 'w3-text-red');
				message.innerHTML = "���źξ��̵�";
				document.getElementById("btnJoin").setAttribute("disabled", true);
			}
		}
	}
	/* ���̵�Ű�Է½� �ʱ�ȭ*/
	function clearUserId() {
		document.getElementById("idmessage").innerHTML = "";
	}

	function showHelp() {
		var help = "��й�ȣ�� ������,���� �������� 16�ڸ� �Է����ּ���"
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
			message.innerHTML = "��й�ȣ ��ġ";
			removeClass(message, 'w3-text-red');
			addClass(message, 'w3-text-green');

		} else {
			addClass(message, 'w3-text-red');
			message.innerHTML = "��й�ȣ ����ġ";

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
		<h2 class="w3-text-black">ȸ������</h2>

		<p>
			<label class="w3-text-black"><b>*���̵�</b></label> <input
				class="w3-input w3-border" name="userId" id="userId" type="text"
				autocomplete="off" onblur="isUserId()">
		<p id="idmessage" class="w3-text-blue"></p>

		<p>
         <label class="w3-text-black"><b>*��й�ȣ</b></label> <input
            class="w3-input w3-border" name="userPw" id="userPw" type="password"
             onkeyup="pwEqual()">
      </p>
      <p>
         <input class="w3-btn w3-black" type="checkbox" id="pwCheckbox"
            onclick="showUserPw()"><label class="w3-text-black">��й�ȣǥ��</label> <span id="pwmessage"
            class="w3-text-blue"></span>
      </p>
		<p>
			<label class="w3-text-black"><b>��й�ȣ��Ȯ��</b></label> <input
				class="w3-input w3-border" id="userPwCheck" type="password"
				onkeyup="pwEqual()">
		</p>
		<p id="pwCheckMessage" class="w3-text-blue"></p>
		<p>
			<label class="w3-text-black"><b>*�̸�</b></label> <input
				class="w3-input w3-border" name="userName" id="userName" type="text" 
				onfocus="addressFocus()" onkeyup="buttonEnable()">
		</p>

		<p>
			<button class="w3-btn w3-black" type="submit" id="btnJoin">���</button>
			<button class="w3-btn w3-black" id="btnReset">���</button>
		</p>
	</form>

</body>
</html>