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
}
