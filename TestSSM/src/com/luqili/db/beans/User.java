package com.luqili.db.beans;


public class User {
	private Integer id;
	private String username;
	private String password;
	private Integer page;
	private Character sex;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Character getSex() {
		return sex;
	}
	public void setSex(Character sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User{id="+this.id+";username="+this.username+";password="+this.password+";page="+this.page+";sex="+this.sex+"}";
	}
	
}
