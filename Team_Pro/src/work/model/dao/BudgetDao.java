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

public class BudgetDao {

	private FactoryDao factory = FactoryDao.getInstance();
	private static BudgetDao instance = new BudgetDao();

	private BudgetDao() {
		// 지우면안됨 싱글톤 패턴위배되지않게 남겨줘야됨
	}

	public static BudgetDao getInstance() {
		return instance;
	}

	/** 회원전체조회
	 * @return ArrayList<User> list  리스트 로 회원전체반환
	 */
	public ArrayList<Budget> selectBudget(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String budgetName = null;
		int budgetAmount = 0;
		

		try {
			conn = factory.getConnection(); // 연결시켜줘 전용통로개설
			String sql = "select * from budget_list where id = ? ";// ?에맵핑되는 전달값설정
			pstmt = conn.prepareStatement(sql); // 통로만들어줘
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
			System.out.println("error : 전체조회 오류");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
}