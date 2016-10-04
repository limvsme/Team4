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

public class BudgetDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static BudgetDao instance = new BudgetDao();

	private BudgetDao() {
		// �����ȵ� �̱��� ������������ʰ� ������ߵ�
	}

	public static BudgetDao getInstance() {
		return instance;
	}

	/** ȸ����ü��ȸ
	 * @return ArrayList<User> list  ����Ʈ �� ȸ����ü��ȯ
	 */
	public ArrayList<Budget> selectBudget(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String budgetName = null;
		int budgetAmount = 0;
		

		try {
			conn = factory.getConnection(); // ��������� ������ΰ���
			String sql = "select * from budget_list where id = ? ";// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
			pstmt.setString(1, id); 
			rs = pstmt.executeQuery();

			ArrayList<Budget> list = new ArrayList<Budget>();

			while (rs.next()) {

				budgetName = rs.getString("budget_name");
				budgetAmount = rs.getInt("budget_amount");
				
				list.add(new Budget( id,budgetName, budgetAmount));

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