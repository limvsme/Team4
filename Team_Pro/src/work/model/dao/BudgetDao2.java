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
 * ȸ�� ���� ���
 * @author choi
 *
 */

public class BudgetDao2 {

	private FactoryDao factory = FactoryDao.getInstance();
	private static BudgetDao2 instance = new BudgetDao2();

	private BudgetDao2() {
		// �����ȵ� �̱��� ������������ʰ� ������ߵ�
	}

	public static BudgetDao2 getInstance() {
		return instance;
	}

	/** ȸ����ü��ȸ
	 * @return ArrayList<User> list  ����Ʈ �� ȸ����ü��ȯ
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

		try {
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select * from budget_list where budget_paper_no = ? ";// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setInt(1, budgetPaperNo); 
			rs = pstmt.executeQuery();

			ArrayList<Budget> list = new ArrayList<Budget>();

			while (rs.next()) {
				id = rs.getString("id");
				budgetName = rs.getString("budget_name");
				budgetAmount = rs.getInt("budget_amount");
				categoryNo =rs.getInt("category_no");
				budgetNo = rs.getInt("budget_no");
				list.add(new Budget( id,budgetPaperNo,budgetName, budgetAmount,categoryNo ,budgetNo));

			}
			return list;
		} catch (SQLException e) {
			System.out.println("error : ��ü��ȸ ����");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
}