package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import work.model.dto.CoupleDTO;


public class CoupleDAO {
	/* ���丮���� ���� */
	private FactoryDao factory = FactoryDao.getInstance(); 

	private static CoupleDAO instance = new CoupleDAO();
	public static CoupleDAO getInstance() {
		return instance;
	}

	private CoupleDAO () {}
	/* ���丮���� �� */
	
	/*
	 * ��Ȳ1. (��)Ŀ�ù�ȣ�� ������ �ְ� (1) (��)Ŀ����.
	 *  - ������ �㰡�ϰ� Ŀ�ø��� ���ǿ� ��Ƽ� �ʿ��Ҷ����� ����Ѵ�.
	 *  
	 * ��Ȳ2. (��)Ŀ�ù�ȣ�� ������ �ְ� (2) (��)Ŀ���� �ƴ�.
	 *  -> ������ȣ�� �����Ϸ� �Ѵ�. => (��)������ ������ �ִ� ������ȣ ǥ��
	 *  -> ������ȣ�� �Է��Ϸ� �Ѵ�. 
	 *  	=> (��)�Է��� ������ȣ�� �� ������ȣ�� �ƴ��� Ȯ���Ѵ�.
	 *  	=> (��)�Է��� ������ȣ�� �̹� Ŀ������ �ƴ��� Ȯ���Ѵ�.
	 *  
	 *  ��Ȳ3. Ŀ�ù�ȣ�� ����.
	 *   -> ������ȣ�� �����Ϸ� �Ѵ�. => (��)������ȣ ����
	 *   -> ������ȣ�� �Է��Ϸ� �Ѵ�.
	 *   	=> (��)�Է��� ������ȣ�� �� ������ȣ�� �ƴ��� Ȯ���Ѵ�.
	 *  	=> (��)�Է��� ������ȣ�� �̹� Ŀ������ �ƴ��� Ȯ���Ѵ�.
	 *  
	 *  ������ȣ �Է��� ������ ���
	 *  1. Ŀ�ø��� �����Ѵ�.
	 *  2. �� ȸ�������� Ŀ�ù�ȣ�� �ִ´�.
	 *  
	 *  ����� ���
	 *  1. �� Ŀ�ù�ȣ Ȯ��
	 *  2. Ŀ�ù�ȣ�� Ŀ������ Ȯ��
	 *  3. ������ ������ �ִ� ������ȣ ǥ��
	 *  4. ������ȣ ����
	 *  5. Ŀ�ø� ����
	 *  6. Ŀ�ù�ȣ�� �� ȸ�������� �ݿ�
	 *  7. Ŀ�ø� ��ȸ
	 *  
	 *  �α��ν� �� Ŀ�ù�ȣ�� ���ǿ� ������ ������.
	 */
	
	/**
	 * �ش� ȸ���� Ŀ�ù�ȣ�� ������ �ִ��� Ȯ��.
	 * @param id ȸ�����̵�
	 * @return Ŀ�ù�ȣ�� ���� �� Ŀ�ù�ȣ�� ��ȯ�ϰ� ������ 0�� ��ȯ�Ѵ�.
	 */
	public int selectHaveCoupleNo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			
			StringBuilder temp = new StringBuilder();
			temp.append("select couple_no");
			temp.append(" from ");
			temp.append("member");
			temp.append(" where ");
			temp.append("id=?");
			String sql = temp.toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
				return 0;
		} catch(SQLException e) {
			System.out.println("ȸ���� Ŀ�ù�ȣ ���� ��ȸ > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
	
	/**
	 * Ŀ�ù�ȣ�� Ŀ���� �ξ��� Ŀ�ù�ȣ���� ��ȸ
	 * @param coupleNo Ŀ�ù�ȣ
	 * @return �ش� Ŀ�ù�ȣ�� Ŀ���� ���� ��� 2�� ��ȯ�ǰ�, �ƴ� ��� 1 �Ǵ� 0�� ��ȯ�ȴ�.
	 */
	public int selectAreYouCouple(int coupleNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			
			StringBuilder temp = new StringBuilder();
			temp.append("select count(*)");
			temp.append(" from ");
			temp.append("member");
			temp.append(" where ");
			temp.append("couple_no=?");
			String sql = temp.toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
				return 0;
		} catch(SQLException e) {
			System.out.println("��ȸ�� Ŀ�ù�ȣ�� Ŀ�ÿ��� ��ȸ > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
	
	/**
	 * Ŀ�ù�ȣ�� ������ �߱޵� ������ȣ�� Ȯ���Ѵ�.
	 * @param coupleNo Ŀ�ù�ȣ
	 * @return Ŀ�ù�ȣ�� ��Ī�Ǿ� �ִ� ������ȣ
	 */
	public String selectAlreadyNo(int coupleNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			String sql = "select confirm_no from couple where couple_no=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch(SQLException e) {
			System.out.println("Ŀ�ù�ȣ�� ������ȣ ��ȸ > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	
	/**
	 * Ŀ�ù�ȣ�� �����Ѵ�. Ŀ�ù�ȣ�� �������� ���� �ڵ������ϰ�, ������ȣ�� ������ �� �޾Ƽ� �μ�Ʈ �Ѵ�.
	 * @param confirmNo ������ȣ
	 * @return �ش� �ο��� ROWID
	 */
	public String insertCoupleKey(String confirmNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();

			/* SQL ������ String ���� */
			StringBuilder temp = new StringBuilder();
			temp.append("insert into couple (couple_no, confirm_no) ");
			temp.append(" values ");
			temp.append("(SEQ_COUPLE_NO.nextval, ?)");
			String sql = temp.toString();
			
			/* ���� ������ */
			pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, confirmNo);
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getString(1);	
			}
			return null;
			
		} catch (SQLException e) {
			System.out.println("Ŀ�ù�ȣ ���� �� > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return null;
	}
	
	/**
	 * ROWID�� �˻��Ͽ� �������� ������ Ŀ�ù�ȣ�� ��ȯ�Ѵ�.
	 * @param rowid ROWID
	 * @return Ŀ�ù�ȣ
	 */
	public int selectCoupleNoByROWID(String rowid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			
			String sql = "select couple_no from couple where rowid=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rowid);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
				return 0;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
	
	/**
	 * Ŀ�ø� ����
	 * @param dto CoupleDTO
	 * @return
	 */
	public int updateCoupleName(CoupleDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("update couple");
			temp.append(" set ");
			temp.append("couple_name=?");
			temp.append(" where ");
			temp.append("couple_no=? and confirm_no=?");
			
			String sql = temp.toString();
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getCoupleName());
			pstmt.setInt(2, dto.getCoupleNo());
			pstmt.setString(3, dto.getConfirmNo());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ŀ�� > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}
	 
	/**
	 * �ش� ���̵� ȸ�������� Ŀ�ù�ȣ�� �ݿ��Ѵ�
	 * @param coupleNo Ŀ�ù�ȣ
	 * @param id ���̵�
	 * @return ��������, ������ 1 ���д� 0
	 */
	public int updateMyCoupleNo (int coupleNo, String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("update member");
			temp.append(" set ");
			temp.append("couple_no=?");
			temp.append(" where ");
			temp.append("id=?");
			
			String sql = temp.toString();
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, coupleNo);
			pstmt.setString(2, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Ŀ�ù�ȣ�� �� ������ �ݿ� > SQLException");
			e.printStackTrace();
		} finally {
			//�ڿ�����
			factory.close(conn, pstmt);
		}
		return 0;
	}
	
	/**
	 * Ŀ�ø��� �ѷ��ֱ� ���ؼ� Ŀ���̸��� ��ȸ
	 * @param id ���̵�
	 * @return Ŀ���̸�
	 */
	public String selectCoupleName(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();
			StringBuilder temp = new StringBuilder();
			temp.append("select couple.couple_name");
			temp.append(" from ");
			temp.append("couple,member");
			temp.append(" where ");
			temp.append("couple.couple_no=member.couple_no and member.id=?");
			
			String sql = temp.toString();

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}

		} catch(SQLException e) {
			System.out.println("Ŀ�ø� ��ȸ > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
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
