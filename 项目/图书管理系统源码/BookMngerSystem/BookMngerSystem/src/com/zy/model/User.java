package com.zy.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String userid;// 用户类型标志符 0 --管理员 1---表示用户

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String username, String password, String userid) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userid = userid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String userid) {
		super();
		this.username = username;
		this.password = password;
		this.userid = userid;
	}

	public User(String username, String password, int id) {
		this.username = username;
		this.password = password;
		this.id = id;
	}

	public User(String username2) {
		this.username = username;
	}

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", userid=" + userid + "]";
	}

}
