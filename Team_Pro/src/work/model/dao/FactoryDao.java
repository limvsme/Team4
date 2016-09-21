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
 * --Ư���� ���� ����� �����ϴ� ���� Ŭ����
 * 
 * ## FactoryDao Pattern
 * -- DAO Ŭ������ ������ : Connection ����, �ڿ� ����
 * -- Singleton Pattern ���� ����
 * -- �ϳ��� Ŭ������ ���ؼ� ���� ��ü ����
 * --  ���� ��Ģ
 * -- 1.private ������ (){}
 *
 * 
 * @author ��ä��
 * ## DAO ������
 *0. jdbc driver �ε� : ������
 *1. Connection ����
 *2. �ڿ�����
 *
 *##dbserver���� property ���� �ܺ� �ڿ����� ���
 *--java.io.*
 *--java.util.ResourceBundle : ��Ģ �ؼ�, ��� ��
 *
 *## ResourceBundle ����Ģ
 *1. �ܺ� �ڿ����� ��ġ : classpath(output dir)���� ��� ���
 *2. ���� Ȯ���� : .properties
 *3. ���� property ��� : key = value
 *4. ���ǻ��� : key �� value ������ = ��ȣ��������Ұ�
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
	    	  
	         Class.forName(driver);//class.ForName���� jdbc ����̹� �ε� 
	      }catch(ClassNotFoundException e ){
	         System.out.println("ERROR : driver loading ����");
	         e.printStackTrace();
	        
	      } */
		try {
		ds= (DataSource)new InitialContext().lookup(dsName);
		} catch (NamingException e){
			System.out.println("ds ���ᰴü �������� ����");
			e.printStackTrace();
		}
	   }
	
	public static FactoryDao getInstance() {
		   return instance;
		} 
   
	   // ������ Connection ���� ��ȯ
	public Connection getConnection() {
	/**	try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Error : DB ���� ����");
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
			System.out.println("connection ���ᰴü �������� ����");
			e.printStackTrace();
		}
		return null;
	}
	
	// ������ 2  �ڿ�����
	
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
	            System.out.println("�ڿ����� ����");
	            e.printStackTrace();
	         }
	}
	
	public void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}

	   
}
