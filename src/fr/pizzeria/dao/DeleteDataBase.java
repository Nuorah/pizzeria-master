package fr.pizzeria.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DeleteDataBase {

	public static void main(String[] args) {

		String JDBC_DRIVER = "";
		String DB_URL = "";
		String USER = "";
		String PASS = "";

		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
			JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
			DB_URL = prop.getProperty("DB_URL");
			USER = prop.getProperty("USER");
			PASS = prop.getProperty("PASS");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Deleting database...");
			stmt = conn.createStatement();

			String sql = "DROP DATABASE PIZZAS";
			stmt.executeUpdate(sql);
			System.out.println("Database deleted successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null){
					conn.close();
					System.out.println("Connection closed.");
				}
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		System.out.println("Goodbye!");
	}
}


