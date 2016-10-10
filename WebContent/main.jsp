<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="work.model.service.MainService"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="work.model.dto.BudgetPaperDTO"%>
<%
	MainService service = new MainService();
	ArrayList<BudgetPaperDTO> list = service
			.getBudget(Integer.parseInt((String) session.getAttribute("coupleNo")));
%>
<!DOCTYPE html>
<html>
<title>W3.CSS</title>
<meta charset="UTF-8">
<script type="text/javascript" src="http://code.jquery.com/jquery-3.1.0.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript" src="js/ajax.js"></script>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-green.css">
<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<style>
html, body, h1, h2, h3, h4, h5 {
	font-family: "Open Sans", sans-serif
}
</style>
<body class="w3-theme-l5">
	<script type="text/javascript">
		window.onload = start;
		function start() {
			var allTable = document.getElementsByTagName('table');
			for (i = 0; i < allTable.length; i++) {
				allTable[i].style.display = "none";
			}
			var commonIndex = 0;
		}
		function showDetail(index) {
			commonIndex = index;
			var table = document.getElementById("detailTable" + index);
			if (table.style.display == "none") {
				budgetRequest(index);
				table.style.display = "block";

			} else {
				table.style.display = "none";

			}
		}
		function showModal() {
			document.getElementById('id01').style.display = 'block';
		}

		function budgetRequest(index) {
			console.log('budgetRequest');
			var url = "Controller";
			var params = "";
			params += "action=budgetIndex&";
			params += "budgetPaperNo=" + index;
			//응답데이터 타입 =json
			params += "&responseText=json"
			var callback = responseJson;
			method = "GET";
			//js/ajax.js 스크립트이용해서 ajax 서버요청
			new ajax.xhr.Request(url, params, callback, method)
		}
		function responseJson(xhr) {
			console.log('xhr:', xhr);
			if (xhr.readyState == 4 && xhr.status == 200) {
				var table = document
						.getElementById("detailTable" + commonIndex);

				var result = eval("(" + xhr.responseText + ")");// json형식의 문자열을 json객체로 바꿔줘
				console.log("result:", result);
				if(typeof result.budgets[0] != "undefined"){
				var length = Number(result.budgets[0].length);
				console.log(result.budgets[0].id);
				} else {var length = 0;}
				console.log("result.length", length);
				
				if (length != 0) {
					var html = '';
					var addHtml = '<tr><td></td><td></td><td></td><td><button onclick="showModal()" class="w3-btn w3-theme">댓글</button></td></tr>'

					if (result != null) {
						for (var i = 0; i < length; i++) {
							html += addHtml;
							document.getElementById('tbody' + commonIndex).innerHTML = html;
						}
					}

					if (result != null) {
						for (var i = 0; i < length; i++) {
							table.rows[i + 1].cells[0].innerHTML = result.budgets[i].categoryName;
							table.rows[i + 1].cells[1].innerHTML = result.budgets[i].budgetName;
							table.rows[i + 1].cells[2].innerHTML = result.budgets[i].budgetAmount;
						}
					} else {
						alert('오류');
					}
				} else {
					document.getElementById('tbody'+commonIndex).innerHTML = '<tr><td colspan="4">검색된 내용 없음.</td></tr>'
				}
			}
		}
		function insertBudget(index) {
			console.log('insertBudget');
			var url = "Controller";
			var budgetPaperNo = index;
			var categoryNo = document.getElementById("categoryNo"+index).value;
			var budgetName = document.getElementById("budgetName"+index).value;
			var budgetAmount = document.getElementById("budgetAmount"+index).value;
			
			var params = "";
			params += "action=insertBudget";
			params += "&budgetPaperNo=" + budgetPaperNo;
			params += "&categoryNo=" + categoryNo;
			params += "&budgetName=" + budgetName;
			params += "&budgetAmount="+budgetAmount
			//응답데이터 타입 =json
			params += "&responseText=json"
			var callback = responseJson2;
			method = "GET";
			//js/ajax.js 스크립트이용해서 ajax 서버요청
			new ajax.xhr.Request(url, params, callback, method)
		}
		function responseJson2(xhr) {
			console.log('responseJson2');
			console.log('xhr:', xhr);
			if (xhr.readyState == 4 && xhr.status == 200) {
				var table = document
						.getElementById("detailTable" + commonIndex);

				var result = eval("(" + xhr.responseText + ")");// json형식의 문자열을 json객체로 바꿔줘
				console.log("result:", result);
				if(typeof result.budgets[0] != "undefined"){
				var length = Number(result.budgets[0].length);
				console.log(result.budgets[0].id);
				} else {var length = 0;}
				console.log("result.length", length);
				
				if (length != 0) {
					var html = '';
					var addHtml = '<tr><td></td><td></td><td></td><td><button onclick="showModal()" class="w3-btn w3-theme">댓글</button></td></tr>'

					if (result != null) {
						for (var i = 0; i < length; i++) {
							html += addHtml;
							document.getElementById('tbody' + commonIndex).innerHTML = html;
						}
					}

					if (result != null) {
						for (var i = 0; i < length; i++) {
							table.rows[i + 1].cells[0].innerHTML = result.budgets[i].categoryName;
							table.rows[i + 1].cells[1].innerHTML = result.budgets[i].budgetName;
							table.rows[i + 1].cells[2].innerHTML = result.budgets[i].budgetAmount;
						}
					} else {
						alert('오류');
					}
				} else {
					document.getElementById('tbody'+commonIndex).innerHTML = '<tr><td colspan="4">검색된 내용 없음.</td></tr>'
				}
			}
		}
		
	</script>

	<!-- Navbar -->
	<div class="w3-top">
		<ul class="w3-navbar w3-theme-d2 w3-left-align w3-large">
			<li class="w3-hide-medium w3-hide-large w3-opennav w3-right"><a class="w3-padding-large w3-hover-white w3-large w3-theme-d2" href="javascript:void(0);" onclick="openNav()"><i class="fa fa-bars"></i></a></li>
			<li><a href="#" class="w3-padding-large w3-theme-d4"><i class="fa fa-home w3-margin-right"></i>Logo</a></li>
			<li class="w3-hide-small"><a href="#" class="w3-padding-large w3-hover-white" title="News"><i class="fa fa-globe"></i></a></li>
			<li class="w3-hide-small"><a href="#" class="w3-padding-large w3-hover-white" title="Account Settings"><i class="fa fa-user"></i></a></li>
			<li class="w3-hide-small"><a href="#" class="w3-padding-large w3-hover-white" title="Messages"><i class="fa fa-envelope"></i></a></li>
			<li class="w3-hide-small w3-dropdown-hover"><a href="#" class="w3-padding-large w3-hover-white" title="Notifications"><i class="fa fa-bell"></i><span class="w3-badge w3-right w3-small w3-green">3</span></a>
				<div class="w3-dropdown-content w3-white w3-card-4">
					<a href="#">One new friend request</a> <a href="#">John Doe posted on your wall</a> <a href="#">Jane likes your post</a>
				</div></li>
			<li class="w3-hide-small w3-right"><a href="#" class="w3-padding-large w3-hover-white" title="My Account"></a></li>
		</ul>
	</div>

	<!-- Navbar on small screens -->
	<div id="navDemo" class="w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top: 51px">
		<ul class="w3-navbar w3-left-align w3-large w3-theme">
			<li><a class="w3-padding-large" href="#">Link 1</a></li>
			<li><a class="w3-padding-large" href="#">Link 2</a></li>
			<li><a class="w3-padding-large" href="#">Link 3</a></li>
			<li><a class="w3-padding-large" href="#">My Profile</a></li>
		</ul>
	</div>

	<!-- Page Container -->
	<div class="w3-container w3-content" style="max-width: 1400px; margin-top: 80px">
		<!-- The Grid -->
		<div class="w3-row">
			<!-- Left Column -->
			<div class="w3-col m3">
				<!-- Profile -->
				<div class="w3-card-2 w3-round w3-white">
					<div class="w3-container">
						<h4 class="w3-center"><%=session.getAttribute("userId")%>님
						</h4>
						<p class="w3-center"></p>
						<hr>
						<p>
							<i class="fa fa-pencil fa-fw w3-margin-right w3-text-theme"></i> Designer, UI
						</p>
						<p>
							<i class="fa fa-home fa-fw w3-margin-right w3-text-theme"></i> London, UK
						</p>
						<p>
							<i class="fa fa-birthday-cake fa-fw w3-margin-right w3-text-theme"></i> April 1, 1988
						</p>
					</div>
				</div>
				<br>

				<!-- Accordion -->
				<div class="w3-card-2 w3-round">
					<div class="w3-accordion w3-white">
						<button onclick="myFunction('Demo1')" class="w3-btn-block w3-theme-l1 w3-left-align">
							<i class="fa fa-circle-o-notch fa-fw w3-margin-right"></i> My Groups
						</button>
						<div id="Demo1" class="w3-accordion-content w3-container">
							<p>Some text..</p>
						</div>
						<button onclick="myFunction('Demo2')" class="w3-btn-block w3-theme-l1 w3-left-align">
							<i class="fa fa-calendar-check-o fa-fw w3-margin-right"></i> My Events
						</button>
						<div id="Demo2" class="w3-accordion-content w3-container">
							<p>Some other text..</p>
						</div>
						<button onclick="myFunction('Demo3')" class="w3-btn-block w3-theme-l1 w3-left-align">
							<i class="fa fa-users fa-fw w3-margin-right"></i> My Photos
						</button>
						<div id="Demo3" class="w3-accordion-content w3-container">
							<div class="w3-row-padding">
								<br>
								<div class="w3-half"></div>
								<div class="w3-half"></div>
								<div class="w3-half"></div>
								<div class="w3-half"></div>
								<div class="w3-half"></div>
								<div class="w3-half"></div>
							</div>
						</div>
					</div>
				</div>
				<br>

				<!-- Interests -->


				<!-- End Left Column -->
			</div>

			<!-- Middle Column -->
			<div class="w3-col m9">

				<div class="w3-row-padding">
					<div class="w3-col m12">
						<div class="w3-card-2 w3-round w3-white">
							<div class="w3-container w3-padding">
								<h6 class="w3-opacity">예산 제목 추가하기</h6>
								<form action="Controller">
									<input type="hidden" name="action" value="budgetRegister">
									<input name="budgetPaperName" class="w3-input w3-border w3-padding w3-round-large w3-margin-bottom" placeholder="예)이번 주말 데이트 예산">
									<button type="submit" class="w3-btn w3-theme">
										<i class="fa fa-pencil"></i>추가
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>
				<%
					int tempNo;
					for (int i = 0; i < list.size(); i++) {
						tempNo = list.get(i).getBudgetPaperNo();
				%>
				<div class="w3-container w3-card-2 w3-white w3-round w3-margin">
					<br>
					<h4 class="w3-left debitstitle" onclick="showDetail('<%=tempNo%>')"><%=list.get(i).getBudgetPaperName()%></h4>
					<br>
					<span class="w3-right">예산 총액 <%=list.get(i).getAmountSum()%>원
					</span>
					<br>
					<span class="w3-right">수락 총액 <%=list.get(i).getAmountYSum()%>원
					</span>
					<hr class="w3-clear">
					<span class="debits">
						<form  id="insertBudget">
						<table class="w3-table-all w3-margin-bottom" id="detailTable<%=tempNo%>">
							<thead>
								<tr>
									<th>분류</th>
									<th>내용</th>
									<th>금액</th>
									<th></th>

								</tr>
							</thead>
							<tbody id="tbody<%=tempNo%>">
								<tr>
									<td colspan="4">로딩중입니다. </td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
								<td><select class="w3-select" id="categoryNo<%=tempNo %>">
  									<option value="1">쇼핑</option>
  									<option value="2">음식</option>
  									<option value="3">이동</option>
  									<option value="4">기타</option>
									</select>
									</td>
									
									<td><input class="w3-input" id="budgetName<%=tempNo %>" placeholder="신규항목 내용입력"></td>
									<td><input class="w3-input" id="budgetAmount<%=tempNo %>" placeholder="신규항목 금액입력"></td>
									<td><button type="button" onclick="insertBudget('<%=tempNo %>')"class="w3-btn w3-theme">저장</button></td>
								</tr>
							</tfoot>
						</table>
						</form>
					</span>
				</div>
				<%
					}
				%>
				<!-- End Middle Column -->
			</div>


		</div>

		<!-- End Grid -->
	</div>

	<!-- End Page Container -->
	</div>
	<br>

	<!-- Footer -->
	<footer class="w3-container w3-theme-d3 w3-padding-16">
		<h5>Footer</h5>
	</footer>

	<footer class="w3-container w3-theme-d5">
		<p>
			Powered by <a href="http://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a>
		</p>
	</footer>

	<script>
		// Accordion
		function myFunction(id) {
			var x = document.getElementById(id);
			if (x.className.indexOf("w3-show") == -1) {
				x.className += " w3-show";
				x.previousElementSibling.className += " w3-theme-d1";
			} else {
				x.className = x.className.replace("w3-show", "");
				x.previousElementSibling.className = x.previousElementSibling.className
						.replace(" w3-theme-d1", "");
			}
		}

		// Used to toggle the menu on smaller screens when clicking on the menu button
		function openNav() {
			var x = document.getElementById("navDemo");
			if (x.className.indexOf("w3-show") == -1) {
				x.className += " w3-show";
			} else {
				x.className = x.className.replace(" w3-show", "");
			}
		}
	</script>


	<script type="text/javascript">
		//		$(".debits").hide();
		//		$(".debitstitle").click(function() {
		//			$(this).next().next().next().next().next().next().toggle();
		//		});
	</script>
</body>
</html>
