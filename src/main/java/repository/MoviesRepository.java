package main.java.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import main.java.servlets.Movie;

public class MoviesRepository {

	Logger log = Logger.getLogger(MoviesRepository.class);

	public void callMysqlDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	public List<Movie> getMovies() throws SQLException{
		callMysqlDriver();
		List<Movie> movies = new ArrayList<Movie>();
		Connection con = getConnection();
		ResultSet rs = null;
		CallableStatement cstmt = con.prepareCall("{CALL GetAllMovies()}");

		rs = cstmt.executeQuery();

		while(rs.next()){
			Movie movie = new Movie();
			movie.setId(rs.getInt("id"));
			movie.setTitle(rs.getString("title"));
			movie.setDirector(rs.getString("director"));
			movie.setYear(rs.getString("year"));
			movie.setCategory(rs.getString("category"));

			movies.add(movie);
		}
		return movies;
	}


	public Movie getMovieById(int id) throws SQLException{
		callMysqlDriver();
		Movie movie = new Movie();
		Connection con = getConnection();
		ResultSet rs = null;
		CallableStatement cstmt = con.prepareCall("{CALL GetMovieById(?)}");

		cstmt.setInt(1,id);
		rs = cstmt.executeQuery();

		while(rs.next()){
			movie.setId(rs.getInt("id"));
			movie.setTitle(rs.getString("title"));
			movie.setDirector(rs.getString("director"));
			movie.setYear(rs.getString("year"));
			movie.setCategory(rs.getString("category"));
		}
		return movie;
	}


	public void updateMovie(Movie movie) throws SQLException{
		callMysqlDriver();
		Connection con = getConnection();
		PreparedStatement prepStmt = con.prepareStatement("update MOVIE set TITLE = ?, DIRECTOR = ?, YEAR = ?, CATEGORY = ? where ID = ?");
		con.setAutoCommit(false);

		try{
			prepStmt.setString(1, movie.getTitle());
			prepStmt.setString(2, movie.getDirector());
			prepStmt.setString(3, movie.getYear());
			prepStmt.setString(4, movie.getCategory());
			prepStmt.setInt(5, movie.getId());
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


	public void createMovie(Movie movie) throws SQLException{
		callMysqlDriver();
		Connection con = getConnection();
		PreparedStatement prepStmt = con.prepareStatement("insert into MOVIE (TITLE, DIRECTOR, YEAR, CATEGORY) values (?, ?, ?, ?)");
		con.setAutoCommit(false);

		try{
			prepStmt.setString(1, movie.getTitle());
			prepStmt.setString(2, movie.getDirector());
			prepStmt.setString(3, movie.getYear());
			prepStmt.setString(4, movie.getCategory());;
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


	public void deleteMovie(int id) throws SQLException{
		callMysqlDriver();
		Connection con = getConnection();
		Statement stmt = con.createStatement();

		String query = "delete from MOVIE where ID = " + id;

		stmt.executeQuery(query);

	}


	public Connection getConnection() throws SQLException{
		callMysqlDriver();
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
