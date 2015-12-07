package main.java.servlets;

import com.sun.istack.internal.NotNull;

public class User {
	
	private int id;
	@NotNull
	private String userName;
	@NotNull
	private String password;
	@NotNull
	private String secret;
	
	public User(int id, String userName, String password, String secret){
		setId(id);
		setUserName(userName);
		setPassword(password);
		setSecret(secret);
	}
	
	public User(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + "]";
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
