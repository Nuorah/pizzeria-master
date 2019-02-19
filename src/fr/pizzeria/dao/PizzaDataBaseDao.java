package fr.pizzeria.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import fr.pizzeria.model.Pizza;

public class PizzaDataBaseDao implements IPizzaDao {

	String JDBC_DRIVER;
	String DB_URL;
	String USER;
	String PASS;
	Connection conn;

	public PizzaDataBaseDao() {		
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config.properties");
			prop.load(input);
			this.JDBC_DRIVER = prop.getProperty("JDBC_DRIVER");
			this.DB_URL = prop.getProperty("DB_URL");
			this.USER = prop.getProperty("USER");
			this.PASS = prop.getProperty("PASS");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void openConnection(){
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	private void closeConnection(){
		try{
			if(this.conn!=null)
				this.conn.close();
			System.out.println("Connection closed.");
		}catch(SQLException se){
			se.printStackTrace();
		}
	}

	@Override
	public List<Pizza> findAllPizzas() {

		List<Pizza> listPizza = new ArrayList<Pizza>();

		this.openConnection();

		Statement stmt = null;
		String sql = "";

		try {
			stmt = this.conn.createStatement();
			sql = "USE PIZZAS";
			stmt.executeUpdate(sql);

			sql = "SELECT * FROM PIZZAS";
			ResultSet resultats = stmt.executeQuery(sql);

			while(resultats.next()){
				int id = resultats.getInt(1);
				String code = resultats.getString("Code");
				String libelle = resultats.getString("Libelle");
				double prix = resultats.getDouble("Prix");
				listPizza.add(new Pizza(id, code, libelle, prix));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			if(stmt!=null)
				stmt.close();
		}catch(SQLException se2){
			se2.printStackTrace();
		}
		this.closeConnection();
		return listPizza;
	}

	@Override
	public void saveNewPizza(Pizza pizza) {
		this.openConnection();

		Statement stmt = null;
		String sql = "";

		try {
			stmt = this.conn.createStatement();
			sql = "USE PIZZAS";
			stmt.executeUpdate(sql);
			
			
			sql = "INSERT INTO PIZZAS (ID, Code, Libelle, Prix)"
					+ "VALUES(?, ?, ?, ?);";
			
			PreparedStatement saveNewPizza = this.conn.prepareStatement(sql);
			saveNewPizza.setInt(1, pizza.getId());
			saveNewPizza.setString(2, pizza.getCode());
			saveNewPizza.setString(3, pizza.getLibelle());
			saveNewPizza.setDouble(4,  pizza.getPrix());
			saveNewPizza.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			if(stmt!=null)
				stmt.close();
		}catch(SQLException se2){
			se2.printStackTrace();
		}
		this.closeConnection();

	}

	@Override
	public void updatePizza(String codePizza, Pizza pizza) {
		this.openConnection();

		Statement stmt = null;
		String sql = "";

		try {
			stmt = this.conn.createStatement();
			sql = "USE PIZZAS";
			stmt.executeUpdate(sql);
			
			
			sql = "UPDATE PIZZAS "
					+ "SET  ID = ?, Code = ?, Libelle = ?, Prix = ? "
					+ "WHERE Code = ?;";			
			PreparedStatement updatePizza = this.conn.prepareStatement(sql);
			updatePizza.setInt(1, pizza.getId());
			updatePizza.setString(2, pizza.getCode());
			updatePizza.setString(3, pizza.getLibelle());
			updatePizza.setDouble(4,  pizza.getPrix());
			updatePizza.setString(5,  codePizza);
			updatePizza.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			if(stmt!=null)
				stmt.close();
		}catch(SQLException se2){
			se2.printStackTrace();
		}
		this.closeConnection();

	}

	@Override
	public void deletePizza(String codePizza) {
		this.openConnection();

		Statement stmt = null;
		String sql = "";

		try {
			stmt = this.conn.createStatement();
			sql = "USE PIZZAS";
			stmt.executeUpdate(sql);
			
			
			sql = "DELETE FROM PIZZAS "
					+ "WHERE Code = ?;";			
			PreparedStatement deletePizza = this.conn.prepareStatement(sql);
			deletePizza.setString(1,  codePizza);
			deletePizza.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			if(stmt!=null)
				stmt.close();
		}catch(SQLException se2){
			se2.printStackTrace();
		}
		this.closeConnection();

	}

	@Override
	public Pizza findPizzaByCode(String codePizza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean pizzaExists(String codePizza) {
		// TODO Auto-generated method stub
		return false;
	}

}
