package work.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import work.model.dto.CoupleDTO;


public class CoupleDAO {
	/* 팩토리패턴 시작 */
	private FactoryDao factory = FactoryDao.getInstance(); 

	private static CoupleDAO instance = new CoupleDAO();
	public static CoupleDAO getInstance() {
		return instance;
	}

	private CoupleDAO () {}
	/* 팩토리패턴 끝 */
	
	/*
	 * 상황1. (일)커플번호를 가지고 있고 (1) (이)커플임.
	 *  - 접속을 허가하고 커플명을 세션에 담아서 필요할때마다 써야한다.
	 *  
	 * 상황2. (일)커플번호를 가지고 있고 (2) (이)커플이 아님.
	 *  -> 인증번호를 발행하려 한다. => (삼)기존에 가지고 있던 인증번호 표출
	 *  -> 인증번호를 입력하려 한다. 
	 *  	=> (일)입력한 인증번호가 내 인증번호가 아닌지 확인한다.
	 *  	=> (이)입력한 인증번호가 이미 커플인지 아닌지 확인한다.
	 *  
	 *  상황3. 커플번호가 없음.
	 *   -> 인증번호를 발행하려 한다. => (사)인증번호 발행
	 *   -> 인증번호를 입력하려 한다.
	 *   	=> (일)입력한 인증번호가 내 인증번호가 아닌지 확인한다.
	 *  	=> (이)입력한 인증번호가 이미 커플인지 아닌지 확인한다.
	 *  
	 *  인증번호 입력을 성공할 경우
	 *  1. 커플명을 설정한다.
	 *  2. 내 회원정보에 커플번호를 넣는다.
	 *  
	 *  도출된 기능
	 *  1. 내 커플번호 확인
	 *  2. 커플번호가 커플인지 확인
	 *  3. 기존에 가지고 있는 인증번호 표출
	 *  4. 인증번호 발행
	 *  5. 커플명 설정
	 *  6. 커플번호를 내 회원정보에 반영
	 *  7. 커플명 조회
	 *  
	 *  로그인시 내 커플번호를 세션에 담으면 좋을듯.
	 */
	
	/**
	 * 해당 회원이 커플번호를 가지고 있는지 확인.
	 * @param id 회원아이디
	 * @return 커플번호가 있을 시 커플번호를 반환하고 없으면 0을 반환한다.
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
			System.out.println("회원의 커플번호 보유 조회 > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
	
	/**
	 * 커플번호로 커플이 맺어진 커플번호인지 조회
	 * @param coupleNo 커플번호
	 * @return 해당 커플번호로 커플이 있을 경우 2가 반환되고, 아닐 경우 1 또는 0이 반환된다.
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
			System.out.println("조회된 커플번호의 커플여부 조회 > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return 0;
	}
	
	/**
	 * 커플번호로 기존에 발급된 인증번호를 확인한다.
	 * @param coupleNo 커플번호
	 * @return 커플번호에 매칭되어 있는 인증번호
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
			System.out.println("커플번호로 인증번호 조회 > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
	}
	
	/**
	 * 커플번호를 발행한다. 커플번호는 시퀀스를 통해 자동생성하고, 인증번호는 생성된 걸 받아서 인서트 한다.
	 * @param confirmNo 인증번호
	 * @return 해당 로우의 ROWID
	 */
	public String insertCoupleKey(String confirmNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = factory.getConnection();

			/* SQL 쿼리문 String 생성 */
			StringBuilder temp = new StringBuilder();
			temp.append("insert into couple (couple_no, confirm_no) ");
			temp.append(" values ");
			temp.append("(SEQ_COUPLE_NO.nextval, ?)");
			String sql = temp.toString();
			
			/* 쿼리 날리기 */
			pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, confirmNo);
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getString(1);	
			}
			return null;
			
		} catch (SQLException e) {
			System.out.println("커플번호 생성 후 > SQLException");
			e.printStackTrace();
		} finally {
			//자원해제
			factory.close(conn, pstmt);
		}
		return null;
	}
	
	/**
	 * ROWID로 검색하여 시퀀스로 생성된 커플번호를 반환한다.
	 * @param rowid ROWID
	 * @return 커플번호
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
	 * 커플명 설정
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
			System.out.println("커플 > SQLException");
			e.printStackTrace();
		} finally {
			//자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}
	 
	/**
	 * 해당 아이디 회원정보에 커플번호를 반영한다
	 * @param coupleNo 커플번호
	 * @param id 아이디
	 * @return 성공여부, 성공은 1 실패는 0
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
			System.out.println("커플번호를 내 계정에 반영 > SQLException");
			e.printStackTrace();
		} finally {
			//자원해제
			factory.close(conn, pstmt);
		}
		return 0;
	}
	
	/**
	 * 커플명을 뿌려주기 위해서 커플이름을 조회
	 * @param id 아이디
	 * @return 커플이름
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
			System.out.println("커플명 조회 > SQLException");
			e.printStackTrace();
		} finally {
			factory.close(conn, pstmt, rs);
		}
		return null;
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
