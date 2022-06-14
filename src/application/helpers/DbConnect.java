package application.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
 * This class is the connection between the sqlite database and the java classes
 * using the JDBC library
 */
public class DbConnect {
	
//Constructor	
	private DbConnect() {
		
	}
	//connection method
	public static DbConnect getInstance() {
		return new DbConnect();
		
	}
	//Name of the database
	public Connection getConnection() throws SQLException {
		String connect_string = "jdbc:sqlite:signupUsers.db";
				
		
		Connection connection = null;
		try {
			//runtime class descriptor
			Class.forName("org.sqlite.JDBC");
			//jdbc driver manager service
			connection = DriverManager.getConnection(connect_string);
		//exception handling
		
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return connection;
	}

}
