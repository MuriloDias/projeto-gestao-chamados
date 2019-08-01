package core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {
	
	public static Connection getConnection() 
			throws ClassNotFoundException, 
		SQLException{
		//DriverManager.registerDriver(new org.postgresql.Driver());
//		String url = "jdbc:postgresql://localhost:5432/calling";
//		String user = "postgres";
//		String password = "1234";
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/chamaditos", "postgres", "123");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
}
