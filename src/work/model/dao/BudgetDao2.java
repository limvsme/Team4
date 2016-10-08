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
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String id = null;
		
		String budgetName = null;
		int budgetAmount = 0;
		int categoryNo=0;
		int budgetNo=0;
		String categoryName = null;
		String budgetYN= null;
		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from budget_list where budget_paper_no = ? ";// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
			pstmt.setInt(1, budgetPaperNo); 
			rs = pstmt.executeQuery();

			ArrayList<Budget> list = new ArrayList<Budget>();

			while (rs.next()) {
				id = rs.getString("id");
				budgetName = rs.getString("budget_name");
				budgetAmount = rs.getInt("budget_amount");
				categoryNo =rs.getInt("category_no");
				budgetYN = rs.getString("budget_yn");
				budgetNo = rs.getInt("budget_no");
				list.add(new Budget( id,budgetPaperNo,budgetName, budgetAmount,categoryNo ,budgetNo,budgetYN));

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
	
	public int insertBudget(String id,String categoryName,String budgetName,int budgetAmount) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int categoryNo=0;
		int budgetPaperNo =0;
		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql1="select * from category where category_name=?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, categoryName);
			rs=pstmt.executeQuery();
			if(rs.next()){
				categoryNo =rs.getInt("category_no");
			}
			
			rs.close();
			pstmt.close();
			
			String sql2 = "insert into budget_list values(SEQ_LIST_NO.nextval,SEQ_BUDGET_NO.nextval, ? , ? ,'N', ? , sysdate , ? ) ";// ?에맵핑되는 전달값설정
			
			pstmt = conn.prepareStatement(sql2); // 통로만들어줘
			pstmt.setString(1, budgetName); 
			pstmt.setInt(2, budgetAmount); 
			pstmt.setInt(3, categoryNo);
			pstmt.setString(4, id); 
			pstmt.executeUpdate();
			pstmt.close();
			
			String sql3 = "select SEQ_LIST_NO.currval from dual";
			pstmt = conn.prepareStatement(sql3); 
			rs= pstmt.executeQuery();
		
			if(rs.next()){
				budgetPaperNo =rs.getInt("currval");
			}
			pstmt.executeQuery();
			
			
			
			return budgetPaperNo;

			
		} catch (SQLException e) {
			System.out.println("error : 오류");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
}