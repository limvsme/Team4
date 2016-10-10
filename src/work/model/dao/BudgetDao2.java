package work.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import work.model.dto.Budget;
import work.util.Utility;

/**
 * 회원 관리 기능
 * @author choi
 *
 */

public class BudgetDao2 {

	private FactoryDao factory = FactoryDao.getInstance();
	private static BudgetDao2 instance = new BudgetDao2();

	private BudgetDao2() {
		// 지우면안됨 싱글톤 패턴위배되지않게 남겨줘야됨
	}

	public static BudgetDao2 getInstance() {
		return instance;
	}

	/** 회원전체조회
	 * @return ArrayList<User> list  리스트 로 회원전체반환
	 */
	public ArrayList<Budget> selectBudget(int budgetPaperNo) {
		System.out.println("dao budgetPaperNo"+budgetPaperNo);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String id = null;
		
		String budgetName = null;
		int budgetAmount = 0;
	
		int budgetNo=0;
		String categoryName = null;
		String budgetYN= null;
		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			StringBuilder stringbd = new StringBuilder();
			stringbd.append("select b.id, b.budget_name, b.budget_amount, c.category_name , b.budget_yn, b.budget_no ");
			stringbd.append("from budget_list b,category c ");
			stringbd.append("where b.category_no=c.category_no and b.budget_paper_no = ? ");
		
			String sql = stringbd.toString();// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setInt(1, budgetPaperNo); 
			rs = pstmt.executeQuery();

			ArrayList<Budget> list = new ArrayList<Budget>();
			System.out.println("dao sql"+sql);
			while (rs.next()) {
				id = rs.getString(1);
				budgetName = rs.getString(2);
				budgetAmount = rs.getInt(3);
				categoryName =rs.getString(4);
				budgetYN = rs.getString(5);
				budgetNo = rs.getInt(6);
				System.out.println("dao rs.next"+categoryName);
				Budget dto=new Budget( id,budgetPaperNo,budgetName, budgetAmount,categoryName ,budgetNo,budgetYN);
				System.out.println(dto.toString());
				list.add(dto);

			}
			return list;
		} catch (SQLException e) {
			System.out.println("error : 전체조회 오류");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int insertBudget(String id,int categoryNo,String budgetName,int budgetAmount,int budgetPaperNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		
		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			
			
			String sql1 = "insert into budget_list values(? ,SEQ_BUDGET_NO.nextval, ? , ? ,'N', ? , sysdate , ? ) ";// ?에맵핑되는 전달값설정
			
			pstmt = conn.prepareStatement(sql1); // 통로만들어줘
			pstmt.setInt(1, budgetPaperNo);
			pstmt.setString(2, budgetName); 
			pstmt.setInt(3, budgetAmount); 
			pstmt.setInt(4, categoryNo);
			pstmt.setString(5, id); 
			
			return pstmt.executeUpdate();

			
		} catch (SQLException e) {
			System.out.println("error : 오류");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
}