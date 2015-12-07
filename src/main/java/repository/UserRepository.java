package main.java.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import main.java.servlets.User;

public class UserRepository {

	public void callMySQLDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public User getUserById(int id) throws SQLException{
		callMySQLDriver();
		User user = new User();
		Connection con = getConnection();
		ResultSet rs = null;

		CallableStatement cstmt = con.prepareCall("{CALL GetUserById(?)}");
		cstmt.setInt(1, id);
		rs = cstmt.executeQuery();

		while(rs.next()){
			user.setId(rs.getInt("id"));
			user.setUserName(rs.getString("userName"));
			user.setPassword(rs.getString("password"));
			user.setSecret(rs.getString("secret"));
		}
		return user;
	}

	public User getUserByName(String userName) throws SQLException {
		callMySQLDriver();

		User user = new User();
		Connection conn = getConnection();
		ResultSet rs = null;
		CallableStatement statement = conn.prepareCall("{CALL GetUserByName(?)}");
		statement.setString(1, userName);
		rs = statement.executeQuery();

		while (rs.next()) {
			if(userName.equalsIgnoreCase(rs.getString("userName"))){
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setSecret(rs.getString("secret"));
			}
		}

		return user;
	}


	public void updateUser(User user) throws SQLException{
		callMySQLDriver();
		Connection con = getConnection();
		PreparedStatement prepStmt = con.prepareStatement("update USER set USERNAME = ?, PASSWORD = ?, SECRET = ? where ID = ?");
		con.setAutoCommit(false);

		try{
			prepStmt.setString(1, user.getUserName());
			prepStmt.setString(2, user.getPassword());
			prepStmt.setString(3, user.getSecret());
			prepStmt.setLong(4, user.getId());
			prepStmt.executeUpdate();
			con.commit();
		}
		catch (SQLException e) {
			if (con != null) {
				System.err.println("Transaction is being rolled back: Exception: " + e.getMessage());
				con.rollback();
			}
			throw e;
		}
		finally {
			prepStmt.close();
			con.close();
		}
	}


	public void createUser(User user) throws SQLException{
		callMySQLDriver();
		Connection con = getConnection();
		PreparedStatement prepStmt = con.prepareStatement("insert into USER (USERNAME, PASSWORD) values (?, ?)");
		con.setAutoCommit(false);

		try{
			prepStmt.setString(1, user.getUserName());
			prepStmt.setString(2, user.getPassword());
			prepStmt.executeUpdate();
			con.commit();
		}
		catch (SQLException e) {
			if (con != null) {
				System.err.println("Transaction is being rolled back: Exception: " + e.getMessage());
				con.rollback();
			}
			throw e;
		}
		finally {
			prepStmt.close();
			con.close();
		}
	}


	public void deleteUser(int id) throws SQLException{
		callMySQLDriver();
		Connection con = getConnection();
		Statement stmt = con.createStatement();

		String query = "delete from USER where ID = " + id;

		stmt.executeQuery(query);
	}


	public Connection getConnection() throws SQLException{
		callMySQLDriver();
		String userName = "DBUsername";
		String password = "DBPassword";
		String serverName = "localhost";
		String schemaName = "MovieDB";
		String ssl = "useSSL=true";
		String schemaInfo = "useInformationSchema=true";
		int port = 3306;

		String connectionUrl = "jdbc:mysql://" + serverName + ":" + port + "/" + schemaName + "?" + schemaInfo + "&" + ssl;

		Connection con = null;
		Properties prop = new Properties();
		prop.put("user", userName);
		prop.put("password", password);

		con = DriverManager.getConnection(connectionUrl, prop);

		return con;
	}
}
