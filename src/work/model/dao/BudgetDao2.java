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
			conn = factory.getConnection(); // ��������� ������ΰ���
			StringBuilder stringbd = new StringBuilder();
			stringbd.append("select b.id, b.budget_name, b.budget_amount, c.category_name , b.budget_yn, b.budget_no ");
			stringbd.append("from budget_list b,category c ");
			stringbd.append("where b.category_no=c.category_no and b.budget_paper_no = ? ");
		
			String sql = stringbd.toString();// ?�����εǴ� ���ް�����
			pstmt = conn.prepareStatement(sql); // ��θ������
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
			System.out.println("error : ��ü��ȸ ����");
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
			conn = factory.getConnection(); // ��������� ������ΰ���
			
			
			String sql1 = "insert into budget_list values(? ,SEQ_BUDGET_NO.nextval, ? , ? ,'N', ? , sysdate , ? ) ";// ?�����εǴ� ���ް�����
			
			pstmt = conn.prepareStatement(sql1); // ��θ������
			pstmt.setInt(1, budgetPaperNo);
			pstmt.setString(2, budgetName); 
			pstmt.setInt(3, budgetAmount); 
			pstmt.setInt(4, categoryNo);
			pstmt.setString(5, id); 
			
			return pstmt.executeUpdate();

			
		} catch (SQLException e) {
			System.out.println("error : ����");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
}