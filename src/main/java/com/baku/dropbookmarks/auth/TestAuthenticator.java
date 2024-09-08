package com.baku.dropbookmarks.auth;

import java.util.Optional;

import com.baku.dropbookmarks.core.User;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class TestAuthenticator implements Authenticator<BasicCredentials, User>
{

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		 if("baku".equals(credentials.getPassword()))
		 return Optional.of(new User());
		 else
	     return Optional.empty();
			
	}

}
