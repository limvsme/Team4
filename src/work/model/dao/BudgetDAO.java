package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import work.model.dto.BudgetPaperDTO;

public class BudgetDAO {
	/* ���丮���� ���� */
	private FactoryDao factory = FactoryDao.getInstance(); 

	private static BudgetDAO instance = new BudgetDAO();
	public static BudgetDAO getInstance() {
		return instance;
	}

	private BudgetDAO () {}
	/* ���丮���� �� */
	
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
			System.out.println("���꼭�� �߰� insertBudget > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}
	

	public int updateListY(int budgetNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("update budget_list");
			temp.append(" set ");
			temp.append("budget_yn='Y'");
			temp.append(" where ");
			temp.append("budget_no=?");
			  
			String sql = temp.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, budgetNo);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���꼭 �׸� ���� updateListY > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}
	
	public int deleteList(int budgetNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("delete from budget_list");
			temp.append(" where ");
			temp.append("budget_no=?");
			  
			String sql = temp.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, budgetNo);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("���꼭 �׸� ���� deleteList > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}

	
	
	//�μ�Ʈ
/*	
 * public int insertBlabla(parameter) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			
			 SQL ������ String ���� 
			String sql = "insert blabla ?";
			
			 ���� ������ 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getIsbn());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("??? > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}
	*/
	
	//����Ʈ
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
