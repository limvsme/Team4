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
	 * ȸ������ Service ��ü ����
	 */
	private static final long serialVersionUID = 1L;

	private MemberService userService = new MemberService();
	private MatchingService matching = new MatchingService();
	private BudgetService budget = new BudgetService();

	/**
	 * ��������ȸ ��û ���� �޼��� -- �α��� ȸ���� ������ ��ȸ -- session ������ �α��� ȸ���� ���̵�
	 * @param request ��û
	 * @param response ����
	 * @throws ServletException ����
	 * @throws IOException ����ó��
	 */
	protected void myInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �α��� ��������� ���� : �α��� �����ÿ� ���ǰ�ü �����ؼ� userId, grade, name ����
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null) {
			String userId = (String) session.getAttribute("userId");
			Member dto = userService.getUser(userId);
			System.out.println(dto);
			if (dto != null) {
				// ��������ȸ ����
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("myinfo.jsp").forward(request, response);

			} else {
				// ������ ����� : ����ó��
				request.setAttribute("message", "�α��� �� ���񽺸� ����Ͻñ� �ٶ��ϴ�.");
				request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
			}

		} else {
			// ������ ����� : ����ó��
			request.setAttribute("message", "�α��� �� ���񽺸� ����Ͻñ� �ٶ��ϴ�.");
			request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
		}

	}

	/**
	 * �α��� ��û ���� �޼���
	 * @param request ��û
	 * @param response ����
	 * @throws ServletException ����
	 * @throws IOException ����ó��
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��û������ ���� : �α��ο�ûview login.jsp
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");

		System.out.println("userId : " + userId);
		System.out.println("userPw : " + userPw);

		// ��û������ ���� : �ʼ� �Է��׸�
		if (userId == null || userId.trim().length() == 0 || userPw == null || userPw.trim().length() == 0) {
			// �������� �������
			request.setAttribute("message", "�α��� ������ �Է��Ͻñ� �ٶ��ϴ�.");

			// ���Է½� ���� ������ �̵� ó��
			RequestDispatcher nextView = request.getRequestDispatcher("error/loginError.jsp");
			nextView.forward(request, response);
		}

		// Model ��û �Ƿ�
		HashMap<String, String> loginMap = userService.login(userId, userPw);
		System.out.println("\n## controller result : " + loginMap);

		// ��û����޾Ƽ� �������� ����
		if (loginMap != null) {
			// ���������� �̵� : ����
			// �α��� ���� => HttpSession ���� ���� ����
			request.setAttribute("loginMap", loginMap);

			// �α��� ���� : ����� ���� ����
			// HttpSession : �α��� ~ �α׾ƿ�(Ÿ�Ӿƿ�) �Ҷ����� �������� ����(����)
			HttpSession session = request.getSession(); // �⺻ : true
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
			// loginMap�� null : ���̵� ������, ��ȣ Ʋ�����
			// ���������� �̵� : ����
			StringBuilder error = new StringBuilder();
			error.append("���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ���ϼ���.");
			error.append("<br>");
			error.append("��ϵ��� ���� ���̵��̰ų�, ���̵� �Ǵ� ��й�ȣ�� �߸� �Է��ϼ̽��ϴ�.");

			request.setAttribute("message", error.toString());
			request.getRequestDispatcher("error/loginError.jsp").forward(request, response);
		}
	}

	/**
	 * ȸ������ ��û ���� �޼���
	 * @param request ��û
	 * @param response ����
	 * @throws ServletException ����
	 * @throws IOException ����ó��
	 */
	protected void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");

		if (userId==null || userId.trim().length() == 0 ||
				userPw.trim().length() == 0 ||
				userName.trim().length() == 0) {
			request.setAttribute("message", "ȸ������ �ʼ� �׸��� ��� �Է��Ͻñ� �ٶ��ϴ�.");
			request.getRequestDispatcher("error/error.jsp").forward(request, response);
		} else {
			Member dto = new Member();
			int result = userService.join(userId, userPw, userName);
			System.out.println("userId = "+ userId + ", userPw =" + userPw + ", userName = "+ userName);
			if (result == 1) {
				// ���� ����
				StringBuilder message = new StringBuilder();
				message.append(userName);
				message.append("(");
				message.append(Utility.convertSecureCode(userId, 3));
				message.append(")");
				message.append("�� ȸ�����ԿϷ�Ǿ����ϴ�.");
				message.append("<br>");
				message.append("�α����� ���񽺸� �̿��Ͻñ� �ٶ��ϴ�.");
				request.setAttribute("message", message);

				// request.setAttribute("message", name + "��
				// ȸ�����ԿϷ�Ǿ����ϴ�.<br>�α����� ���񽺸� �̿��Ͻñ� �ٶ��ϴ�.");
				System.out.println("ȸ�� ���� �Ϸ�");
				request.getRequestDispatcher("Index.html").forward(request, response);
			} else {
				// ���� ����
				System.out.println("ȸ�� ���� ����");
				request.setAttribute("message", "ȸ�������� ���������� ������� �ʾҽ��ϴ�.");
				request.getRequestDispatcher("error/JoinError.jsp").forward(request, response);
			}
		}

	}   

	/**
	 * �α׾ƿ� ��û ���� �޼���
	 * 
	 * 1. �������ǰ�ü��������
	 * 2. �������Ǽ��� �Ӽ� ����Ȯ�� - userId, userName, coupleNo
	 * 3. �����Ӽ� ����
	 * 4. �������� ����
	 * 5. �����������̵� - ���� : index.jsp - ���� : LogoutError.jsp
	 */
	/**
	 * @param request ��û
	 * @param response ����
	 * @throws ServletException ����
	 * @throws IOException ����ó��
	 */
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �α��� ��������� ���� : �α��� �����ÿ� ���ǰ�ü �����ؼ� userId, grade, name ����
		// ���� �α��� ����ڴ� �������ǰ�ü ��ȯ
		// �������� ���� ����ڴ� null ��ȯ
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("userId") != null) {
			// �������� �α��� ���� ���� �����
			// �α��� �����ÿ� �����س��� userId, grade, name ���� ����
			session.removeAttribute("userId");
			session.removeAttribute("userName");
			session.removeAttribute("coupleNo");

			// ���ǰ�ü ����
			session.invalidate();

			// �α׾ƿ� ��û ���� : ���������� �̵�
			response.sendRedirect("Index.jsp");
		} else {
			// ������ ����� : ����ó��
			request.setAttribute("message", "�α��� �� ���񽺸� ����Ͻñ� �ٶ��ϴ�.");
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
			//System.out.println("��ϼ���");
			response.sendRedirect("main.jsp");
		} else {
			System.out.println("��Ͻ���");
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
	 * get, post ��û�� ó���ϴ� ���� �޼���
	 * @param request ��û
	 * @param response ����
	 * @throws ServletException ����
	 * @throws IOException ����ó��
	 */
	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��û �ľ� : ��û�����Ϳ��� ��û�� ���� key ������ ��������
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
				// �������� �ʴ� ��û ���� ������ �̵�
			}
		} else {
			// �߸��� ��û��� ���� ������ �̵�
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
		// ��û��ü�� ���� �ѱ� ���ڵ� ����
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		process(request, response);
	}
}