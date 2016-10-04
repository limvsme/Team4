package work.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import work.util.Utility;
import work.model.dto.Budget;
import work.model.dto.CoupleDTO;
import work.model.dto.Member;
import work.model.service.BudgetService;
import work.model.service.MatchingService;
import work.model.service.MemberService;
/*import work.util.Utility;*/


public class FrontController extends HttpServlet {
	/**
	 * 회원관리 Service 객체 생성
	 */
	private static final long serialVersionUID = 1L;

	private MemberService userService = new MemberService();
	private MatchingService matching = new MatchingService();
	private BudgetService budget = new BudgetService();

	/**
	 * 내정보조회 요청 서비스 메서드 -- 로그인 회원의 내정보 조회 -- session 설정된 로그인 회원의 아이디
	 * @param request 요청
	 * @param response 응답
	 * @throws ServletException 서블릿
	 * @throws IOException 예외처리
	 */
	protected void myInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 사용자인지 검증 : 로그인 인증시에 세션객체 생성해서 userId, grade, name 설정
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null) {
			String userId = (String) session.getAttribute("userId");
			Member dto = userService.getUser(userId);
			System.out.println(dto);
			if (dto != null) {
				// 내정보조회 성공
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("myinfo.jsp").forward(request, response);

			} else {
				// 비정상 사용자 : 오류처리
				request.setAttribute("message", "로그인 후 서비스를 사용하시기 바랍니다.");
				request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
			}

		} else {
			// 비정상 사용자 : 오류처리
			request.setAttribute("message", "로그인 후 서비스를 사용하시기 바랍니다.");
			request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
		}

	}

	/**
	 * 로그인 요청 서비스 메서드
	 * @param request 요청
	 * @param response 응답
	 * @throws ServletException 서블릿
	 * @throws IOException 예외처리
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청데이터 추출 : 로그인요청view login.jsp
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");

		System.out.println("userId : " + userId);
		System.out.println("userPw : " + userPw);

		// 요청데이터 검증 : 필수 입력항목
		if (userId == null || userId.trim().length() == 0 || userPw == null || userPw.trim().length() == 0) {
			// 응답위한 결과설정
			request.setAttribute("message", "로그인 정보를 입력하시기 바랍니다.");

			// 미입력시 오류 페이지 이동 처리
			RequestDispatcher nextView = request.getRequestDispatcher("error/loginError.jsp");
			nextView.forward(request, response);
		}

		// Model 요청 의뢰
		HashMap<String, String> loginMap = userService.login(userId, userPw);
		System.out.println("\n## controller result : " + loginMap);

		// 요청결과받아서 응답위한 설정
		if (loginMap != null) {
			// 응답페이지 이동 : 성공
			// 로그인 성공 => HttpSession 으로 변경 예정
			request.setAttribute("loginMap", loginMap);

			// 로그인 성공 : 사용자 인증 성공
			// HttpSession : 로그인 ~ 로그아웃(타임아웃) 할때까지 상태정보 설정(유지)
			HttpSession session = request.getSession(); // 기본 : true
			session.setAttribute("userId", userId);
			session.setAttribute("userName", loginMap.get("userName"));
			session.setAttribute("coupleNo", loginMap.get("coupleNo"));

			if(!matching.coupleYN(Integer.parseInt((String)session.getAttribute("coupleNo")))){
				session.setAttribute("coupleYN", "Y");
				response.sendRedirect("coupleGetSet.html"); 
			} else {
				response.sendRedirect("main.jsp");
			}

			//request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
		} else {
			// loginMap이 null : 아이디 미존재, 암호 틀린경우
			// 응답페이지 이동 : 실패
			StringBuilder error = new StringBuilder();
			error.append("아이디 또는 비밀번호를 다시 확인하세요.");
			error.append("<br>");
			error.append("등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.");

			request.setAttribute("message", error.toString());
			request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
		}
	}

	/**
	 * 회원가입 요청 서비스 메서드
	 * @param request 요청
	 * @param response 응답
	 * @throws ServletException 서블릿
	 * @throws IOException 예외처리
	 */
	protected void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");

		if (userId==null || userId.trim().length() == 0 ||
				userPw.trim().length() == 0 ||
				userName.trim().length() == 0) {
			request.setAttribute("message", "회원가입 필수 항목을 모두 입력하시기 바랍니다.");
			request.getRequestDispatcher("error/error.jsp").forward(request, response);
		} else {
			Member dto = new Member();
			int result = userService.join(userId, userPw, userName);
			System.out.println("userId = "+ userId + ", userPw =" + userPw + ", userName = "+ userName);
			if (result == 1) {
				// 가입 성공
				StringBuilder message = new StringBuilder();
				message.append(userName);
				message.append("(");
				message.append(Utility.convertSecureCode(userId, 3));
				message.append(")");
				message.append("님 회원가입완료되었습니다.");
				message.append("<br>");
				message.append("로그인후 서비스를 이용하시기 바랍니다.");
				request.setAttribute("message", message);

				// request.setAttribute("message", name + "님
				// 회원가입완료되었습니다.<br>로그인후 서비스를 이용하시기 바랍니다.");
				System.out.println("회원 가입 완료");
				request.getRequestDispatcher("Index.html").forward(request, response);
			} else {
				// 가입 실패
				System.out.println("회원 가입 실패");
				request.setAttribute("message", "회원가입이 정상적으로 진행되지 않았습니다.");
				request.getRequestDispatcher("error/JoinError.jsp").forward(request, response);
			}
		}

	}   

	/**
	 * 로그아웃 요청 서비스 메서드
	 * 
	 * 1. 기존세션객체가져오기
	 * 2. 기존세션설정 속성 유무확인 - userId, userName, coupleNo
	 * 3. 설정속성 삭제
	 * 4. 기존세션 삭제
	 * 5. 응답페이지이동 - 성공 : index.jsp - 실패 : LogoutError.jsp
	 */
	/**
	 * @param request 요청
	 * @param response 응답
	 * @throws ServletException 서블릿
	 * @throws IOException 예외처리
	 */
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 로그인 사용자인지 검증 : 로그인 인증시에 세션객체 생성해서 userId, grade, name 설정
		// 기존 로그인 사용자는 기존세션객체 반환
		// 인증받지 않은 사용자는 null 반환
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("userId") != null) {
			// 정상으로 로그인 인증 받은 사용자
			// 로그인 인증시에 설정해놓은 userId, grade, name 정보 삭제
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			session.removeAttribute("coupleNo");

			// 세션객체 종료
			session.invalidate();

			// 로그아웃 요청 성공 : 시작페이지 이동
			response.sendRedirect("Index.jsp");
		} else {
			// 비정상 사용자 : 오류처리
			request.setAttribute("message", "로그인 후 서비스를 사용하시기 바랍니다.");
			request.getRequestDispatcher("error/LogoutError.jsp").forward(request, response);
		}
	}

	protected void coupleGetNum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession(false);
		String te = (String)session.getAttribute("coupleNo");
		HashMap<String, Object> temp = matching.makeCoupleKey((String)session.getAttribute("userId"), Integer.parseInt(te));
		System.out.println("coupleNo "+temp.get("coupleNo"));
		System.out.println("confirmNo "+temp.get("confirmNo"));

		request.setAttribute("coupleNo", temp.get("coupleNo"));
		session.setAttribute("coupleNo", temp.get("coupleNo").toString());
		request.setAttribute("confirmNo", temp.get("confirmNo"));
		
			request.getRequestDispatcher("coupleGetNum.jsp").forward(request, response);

	}

	protected void budgetRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		int coupleNo = Integer.parseInt((String)session.getAttribute("coupleNo"));
		String budgetPaperName = request.getParameter("budgetPaperName");

		if(budget.addBudget(coupleNo, budgetPaperName)){
			//System.out.println("등록성공");
			response.sendRedirect("main.jsp");
		} else {
			System.out.println("등록실패");
		}

	}

	protected void coupleSetNum(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String coupleNo = request.getParameter("coupleNo");
		String confirmNo = request.getParameter("confirmNo");
		String coupleName = request.getParameter("coupleName");

		CoupleDTO dto = new CoupleDTO(Integer.parseInt(coupleNo), confirmNo, coupleName);

		if((String)session.getAttribute("coupleNo") == coupleNo) {
			try {
				response.sendRedirect("coupleSetResult.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (matching.connectCoupleKey((String)session.getAttribute("userId"), dto)){
				try {
					response.sendRedirect("coupleSetResult.jsp?state=1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					response.sendRedirect("coupleSetResult.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void budgetIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int budgetPaperNo = Integer.parseInt(request.getParameter("budgetPaperNo"));
		String responseText = request.getParameter("responseText");
		System.out.println("budgetIndex");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		ArrayList<Budget> budgetlist =  budget.selectBudget(budgetPaperNo);
		System.out.println(budgetlist);
		String json="";
		
		if(budgetlist!=null){
			json +="{budgets:[";
			for(int i=0; i < budgetlist.size();i++){
			if(i == budgetlist.size()-1) {
				json += "{'budgetName':'"+budgetlist.get(i).getBudgetName()+
						"','length':'"+budgetlist.size()+
						"','budgetNo':'"+budgetlist.get(i).getBudgetNo()+
						"','categoryNo':'"+budgetlist.get(i).getCategoryNo()+
						"','id':'"+budgetlist.get(i).getId()+
						"','budgetAmount':'"+budgetlist.get(i).getBudgetAmount()+
						"'}";
				} else {
					json += "{'budgetName':'"+budgetlist.get(i).getBudgetName()+
							"','length':'"+budgetlist.size()+
							"','budgetNo':'"+budgetlist.get(i).getBudgetNo()+
							"','categoryNo':'"+budgetlist.get(i).getCategoryNo()+
							"','id':'"+budgetlist.get(i).getId()+
							"','budgetAmount':'"+budgetlist.get(i).getBudgetAmount()+
							"'},";
				}
			}
			json += "]}";
		}
		System.out.println("json :" + json);
			out.write(json);
		}


	/**
	 * get, post 요청을 처리하는 서비스 메서드
	 * @param request 요청
	 * @param response 응답
	 * @throws ServletException 서블릿
	 * @throws IOException 예외처리
	 */
	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청 파악 : 요청데이터에서 요청을 위한 key 데이터 가져오기
		String action = request.getParameter("action");

		System.out.println("\n### action : " + action);

		if (action != null) {
			switch (action) {
			case "login":
				login(request, response);
				break;
			case "join":
				join(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			case "myInfo":
				myInfo(request, response);
				break;
			case "coupleGetNum":
				coupleGetNum(request, response);
				break;
			case "coupleSetNum":
				coupleSetNum(request, response);
				break;
			case "budgetRegister":
				budgetRegister(request, response);
				break;
			case "budgetIndex":
				budgetIndex(request,response);
				break;

			default:
				// 지원하지 않는 요청 오류 페이지 이동
			}
		} else {
			// 잘못된 요청방식 오류 페이지 이동
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 요청객체에 대한 한글 인코딩 설정
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		process(request, response);
	}
}