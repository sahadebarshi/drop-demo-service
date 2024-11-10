package com.baku.dropbookmarks.core;

import lombok.Getter;

import java.security.Principal;

public class User implements Principal{
	
	private String name;
	@Getter
    private String password;

    @Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

}
