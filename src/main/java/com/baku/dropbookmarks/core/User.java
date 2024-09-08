package com.baku.dropbookmarks.core;

import java.security.Principal;

public class User implements Principal{
	
	private String name;
	private String password;
	/*public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}*/
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
