//alert('ajax.js load');

var ajax = {};		// top package 선언
ajax.xhr = {};		// top.sub package 선언

/*  ajax.xhr 패키지 Request 클래스 생성자 정의
	객체생성후 send() 메서드 호출 
	
	1. 	이벤트 핸들러함수에서 ajax 요청처리 객체 생성의뢰
		new ajax.xhr.Request(url, params, callback, method);
	2. function callback(xhr) {	// xhr => 현재 요청처리를 담당하는 ajax 객체
			if(xhr.readyState == 4 && xhr.status == 200) {
				// 응답 가져오기
				var responseData = xhr.responseText;
				var xmlData = xhr.responseXML;

				//응답처리
 
			}
		}
	*/
ajax.xhr.Request = function(url, params, callback, method) {
	this.url = url;
	this.params = params;
	this.callback = callback;
	this.method = method;
	this.send();
}
/*  ajax.xhr 패키지 Request 클래스 메서드 정의 */
ajax.xhr.Request.prototype = {
	getXMLHttpRequest: function() {
		if (window.ActiveXObject) {
			try {
				return new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				try {
					return new ActiveXObject("Microsoft.XMLHTTP");
				} catch(e1) { return null; }
			}
		} else if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else {
			return null;
		}		
	},
	send: function() {
		this.req = this.getXMLHttpRequest();
		
		var httpMethod = this.method ? this.method : 'GET';
		if (httpMethod != 'GET' && httpMethod != 'POST') {
			httpMethod = 'GET';
		}
		var httpParams = (this.params == null || this.params == '') ? null : this.params;
		var httpUrl = this.url;
		if (httpMethod == 'GET' && httpParams != null) {
			httpUrl = encodeURI(httpUrl + "?" + httpParams);
		}
		this.req.open(httpMethod, httpUrl, true);
		this.req.setRequestHeader(
			'Content-Type', 'application/x-www-form-urlencoded');
		
		// XMLHttpRequest 객체의 readyState 값이 변경될때 this request 객체의 onStateChange() 함수 호출
		var request = this;
		this.req.onreadystatechange = function() {
			// 함수명.call()  해당함수를 호출함을 의미
			// request 인자는 호출하는 함수인 onStateChange() 함수에서의 this가 될 객체를 의미
			request.onStateChange.call(request);		
		}
		this.req.send(httpMethod == 'POST' ? httpParams : null);
	},
	onStateChange: function() {
		this.callback(this.req);	// this.callback() => ajax.xhr.Request , this.req => XMLHttpRequest
	}
}
