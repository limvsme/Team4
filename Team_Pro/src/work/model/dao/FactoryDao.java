package work.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * ##Factory Pattern
 * --특정한 공통 기능을 제공하는 공장 클래스
 * 
 * ## FactoryDao Pattern
 * -- DAO 클래스의 공통기능 : Connection 생성, 자원 해제
 * -- Singleton Pattern 적용 설계
 * -- 하나의 클래스에 대해서 단일 객체 서비스
 * --  설계 규칙
 * -- 1.private 생성자 (){}
 *
 * 
 * @author 임채현
 * ## DAO 공통기능
 *0. jdbc driver 로딩 : 생성자
 *1. Connection 생성
 *2. 자원해제
 *
 *##dbserver관련 property 파일 외부 자원파일 사용
 *--java.io.*
 *--java.util.ResourceBundle : 규칙 준수, 사용 편리
 *
 *## ResourceBundle 사용규칙
 *1. 외부 자원파일 위치 : classpath(output dir)기준 상대 경로
 *2. 파일 확장자 : .properties
 *3. 파일 property 기술 : key = value
 *4. 주의사항 : key 와 value 사이의 = 기호간에공백불가
 *
 */
public class FactoryDao {

	//private String baseName = "resources/dbserver";
//	private ResourceBundle dbBundle;
	
	// jdbc property
	//private String driver; 		// = "oracle.jdbc.driver.OracleDriver";
	//private String url;    		// = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	//private String user;   		// = "hr";
	//private String password;	// = "tiger" ;
	private String dsName ="java:comp/env/jdbc/Oracle";
	private DataSource ds;
	private static FactoryDao instance = new FactoryDao();
		
	

	private FactoryDao(){
	     /* try{
	    	  dbBundle = ResourceBundle.getBundle(baseName);
	    	  driver = dbBundle.getString("oracle.driver");
	    	  url = dbBundle.getString("oracle.url");
	    	  user = dbBundle.getString("oracle.user");
	    	  password = dbBundle.getString("oracle.password");
	    	  
	         Class.forName(driver);//class.ForName으로 jdbc 드라이버 로딩 
	      }catch(ClassNotFoundException e ){
	         System.out.println("ERROR : driver loading 오류");
	         e.printStackTrace();
	        
	      } */
		try {
		ds= (DataSource)new InitialContext().lookup(dsName);
		} catch (NamingException e){
			System.out.println("ds 연결객체 가져오기 오류");
			e.printStackTrace();
		}
	   }
	
	public static FactoryDao getInstance() {
		   return instance;
		} 
   
	   // 공통기능 Connection 생성 반환
	public Connection getConnection() {
	/**	try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Error : DB 연결 오류");
			e.printStackTrace();
		}
		return null;*/
		try{
			if(ds == null) {
				System.out.println("ds is null");
			} else {
				return ds.getConnection();
			}
		}catch (SQLException e){
			System.out.println("connection 연결객체 가져오기 오류");
			e.printStackTrace();
		}
		return null;
	}
	
	// 공통기능 2  자원해제
	
	public void close(Connection conn, Statement stmt, ResultSet rs) {
		 try {
	            if (rs != null) {
	               rs.close();
	            }
	            if (stmt != null) {
	               stmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            System.out.println("자원해제 오류");
	            e.printStackTrace();
	         }
	}
	
	public void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}

	   
}
