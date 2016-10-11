package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import work.model.dto.BudgetPaperDTO;

public class MainDAO {
	/* 팩토리패턴 시작 */
	private FactoryDao factory = FactoryDao.getInstance(); 

	private static MainDAO instance = new MainDAO();
	public static MainDAO getInstance() {
		return instance;
	}

	private MainDAO () {}
	/* 팩토리패턴 끝 */
	
	public ArrayList<BudgetPaperDTO> selectBudgetPaper(int coupleNo) {
		ArrayList<BudgetPaperDTO> list = new ArrayList<BudgetPaperDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("select budget_paper_no, budget_paper_name ");
			temp.append("from budget_paper ");
			temp.append("where couple_no=? ");
			temp.append("ORDER BY CDATE desc");
			String sql = temp.toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new BudgetPaperDTO(rs.getInt(1), rs.getString(2)));
			}
				return list;
		} catch(SQLException e) {
			System.out.println("예산서 리스트 출력 selectBudgetPaper > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int selectAllSum(int budgetPaperNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			String sql = "select nvl(sum(budget_amount),0) from budget_list where budget_paper_no=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, budgetPaperNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch(SQLException e) {
			System.out.println("예산항목의 총합(전체) > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public int selectYSum(int budgetPaperNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			String sql = "select nvl(sum(budget_amount),0) from budget_list where budget_paper_no=? and budget_yn='Y'";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, budgetPaperNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch(SQLException e) {
			System.out.println("예산항목의 총합(전체) > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public String selectCoupleName(int coupleNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			String sql = "select couple_name from couple where COUPLE_NO=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch(SQLException e) {
			System.out.println("커플명 조회 > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public ArrayList<String> selectNameNname(int coupleNo) {
		ArrayList<String> result = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			String sql = "select member_name from couple,member where member.COUPLE_NO = couple.COUPLE_NO and member.couple_no=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result.add(rs.getString(1));
			}
				return result;
		} catch(SQLException e) {
			System.out.println("커플명 조회 > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
}
