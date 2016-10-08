package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import work.model.dto.BudgetPaperDTO;

public class BudgetDAO {
	/* 팩토리패턴 시작 */
	private FactoryDao factory = FactoryDao.getInstance(); 

	private static BudgetDAO instance = new BudgetDAO();
	public static BudgetDAO getInstance() {
		return instance;
	}

	private BudgetDAO () {}
	/* 팩토리패턴 끝 */
	
	public int insertBudget(int coupleNo, String budgetPaperName) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("insert into budget_paper ");
			temp.append("(couple_no, budget_paper_no, budget_paper_name, cdate)");
			temp.append(" values ");
			temp.append("(?, SEQ_LIST_NO.nextval, ?, sysdate)");
			  
			String sql = temp.toString();
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);
			pstmt.setString(2, budgetPaperName);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("예산서명 추가 insertBudget > SQLException");
			e.printStackTrace();
		} finally {
			//자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}
	

	

	
	
	//인서트
/*	
 * public int insertBlabla(parameter) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			
			 SQL 쿼리문 String 생성 
			String sql = "insert blabla ?";
			
			 쿼리 날리기 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getIsbn());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("??? > SQLException");
			e.printStackTrace();
		} finally {
			//자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}
	*/
	
	//셀렉트
/*	public String selectUserId(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			String sql = "select ??";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("user_id");
			}

		} catch(SQLException e) {
			System.out.println("??? > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	*/
}
